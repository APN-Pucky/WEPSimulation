package de.neuwirthinformatik.Alexander.WEP.Simulation;

import java.util.ArrayList;
import java.util.HashMap;

import de.neuwirthinformatik.Alexander.Util.BAC;
import de.neuwirthinformatik.Alexander.WEP.WEP;

public class Router
{
	public static String ROUTER_NAME = "ROUTER1";
	private static String PASSWORD = "daspasswort";
	private ArrayList<Listener> listeners = new ArrayList<Listener>();
	private HashMap<String,Integer> ip_table = new HashMap<String,Integer>();
	private WEP wep = null;
	
	public Router()
	{
		try
		{
			wep = new WEP(PASSWORD);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void sendTo(byte[] msg,byte[] from, byte[] to)
	{
		if(isConnected(wep.decryptIV(from)) && isConnected(wep.decryptIV(from)))
		{
			for(Listener r : listeners)
			{
				r.listen(msg.clone(),from,to);
			}
		}
	}
	
	public void addListener(Listener l)
	{
		listeners.add(l);
	}
	
	public void removeListener(Listener l)
	{
		listeners.remove(l);
	}
	//connect = verschlüsselt name+routername
	public void connnect(Listener l,byte[] connect)
	{
		if(!listeners.contains(l))
		{
			String msg = BAC.toString(wep.decryptIV(connect));
			String name = msg.substring(0,msg.length()-ROUTER_NAME.length());
			String router = msg.substring(msg.length()-ROUTER_NAME.length(),msg.length());
			if(!isConnected(name) && router.equals(ROUTER_NAME))
			{
				listeners.add(listeners.size(), l);
				ip_table.put(name, listeners.size()-1);
			}
		}
	}
	
	public void disconnect(Listener l,byte[] disconnect)
	{
		if(listeners.contains(l))
		{
			String msg = BAC.toString(wep.decryptIV(disconnect));
			String name = msg.substring(0,msg.length()-ROUTER_NAME.length());
			String router = msg.substring(msg.length()-ROUTER_NAME.length(),msg.length());
			if(isConnected(name) && router.equals(ROUTER_NAME))
			{
				listeners.remove(l);
				ip_table.remove(name);
			}
		}
	}
	
	private boolean isConnected(String name)
	{
		for(String s : ip_table.keySet())
		{
			if(s.equals(name))return true;
		}
		return false;
	}
	
	private boolean isConnected(byte[] name)
	{
		return isConnected(BAC.toString(name));
	}
}