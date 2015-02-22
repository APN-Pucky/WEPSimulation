package de.neuwirthinformatik.Alexander.WEP.Simulation;

import java.util.ArrayList;

import de.neuwirthinformatik.Alexander.Util.BAC;
import de.neuwirthinformatik.Alexander.WEP.WEP;

public class Router implements Listener
{
	//SSID
	public static String ROUTER_NAME = "ROUTER1";
	public static String GET_CONNECTED_LIST = "GET_CONNECTED_LIST";
	private static String PASSWORD = "daspasswort";
	//public medium
	private ArrayList<Listener> listeners = new ArrayList<Listener>();
	private ArrayList<String> connected = new ArrayList<String>();
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
		wep.randomIV();
		listeners.add(this);
		connected.add(ROUTER_NAME);
	}
	
	public void listen(byte[] msg, byte[] from, byte[] to)
	{
		if(BAC.toString(wep.decryptIV(to)).equals(ROUTER_NAME))
		{
			disconnect(msg);
			if(BAC.toString(wep.decryptIV(msg)).equals(GET_CONNECTED_LIST))
			{
				String t="";
				for(String s : connected)
				{
					t += s+" ";
				}
				//to and from swaped to answer
				sendTo(wep.encryptIV(t),to,from);
			}
		}
	}
	
	public void sendTo(final String msg,final byte[] from,final byte[] to)
	{
		sendTo(BAC.toByteArray(msg),from,to);
	}
	
	public void sendTo(final byte[] msg,final byte[] from,final byte[] to)
	{
		new Thread(new Runnable(){
			public void run()
			{
				if(isConnected(wep.decryptIV(to)) && isConnected(wep.decryptIV(from)))
				{
					for(Listener r : listeners)
					{
						r.listen(msg.clone(),from,to);
					}
				}
				else if(connect(msg));
			}
		}).start();
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
	private boolean connect(byte[] connect)
	{
		if(connect.length >3)
		{
			String msg = BAC.toString(wep.decryptIV(connect));
			if(msg.length()>ROUTER_NAME.length())
			{
				String name = msg.substring(0,msg.length()-ROUTER_NAME.length());
				String router = msg.substring(msg.length()-ROUTER_NAME.length(),msg.length());
				if(!isConnected(name) && router.equals(ROUTER_NAME))
				{
					connected.add(name);
					return true;
				}
			}
		}
		return false;
	}
	//disconnect = verschlüsselt routername+name
	private boolean disconnect(byte[] disconnect)
	{
		if(disconnect.length >3)
		{
			String msg = BAC.toString(wep.decryptIV(disconnect));
			if(msg.length()>ROUTER_NAME.length())
			{
				String router = msg.substring(0,ROUTER_NAME.length());
				String name = msg.substring(ROUTER_NAME.length(),msg.length());
				if(isConnected(name) && router.equals(ROUTER_NAME))
				{
					removeConnection(name);
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean isConnected(String name)
	{
		for(String s : connected)
		{
			if(s.equals(name))return true;
		}
		return false;
	}
	
	private void removeConnection(String name)
	{
		String tmp = null;
		for(String s : connected)
		{
			if(s.equals(name))tmp = s;
		}
		connected.remove(tmp);
	}
	
	private boolean isConnected(byte[] name)
	{
		return isConnected(BAC.toString(name));
	}
}