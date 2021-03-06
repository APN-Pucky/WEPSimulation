package de.neuwirthinformatik.Alexander.WEP.Simulation;

import java.util.ArrayList;

import de.neuwirthinformatik.Alexander.Util.BAC;
import de.neuwirthinformatik.Alexander.WEP.WEP;

public class Router implements Listener
{
	//SSID
	public String ROUTER_NAME = "ROUTER1";
	public static String GET_CONNECTED_LIST = "GET_CONNECTED_LIST";
	//SNAP_HEADER (802.11b standard first byte)
	public static String SNAP_HEADER = BAC.toString(new byte[]{0x2A}); // = '*' -> 0xAA
	private String PASSWORD = "daspasswort";
	//public medium
	private ArrayList<Listener> listeners = new ArrayList<Listener>();
	private ArrayList<String> connected = new ArrayList<String>();
	private WEP wep = null;
	
	public Router(String rn, String pw)
	{
		this.ROUTER_NAME = rn;
		this.PASSWORD = pw;
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
	
	public synchronized void listen(byte[] msg, byte[] from, byte[] to)
	{
		if(BAC.toString(wep.decryptIV(to)).equals(SNAP_HEADER+ROUTER_NAME))
		{
			disconnect(msg);
			if(BAC.toString(wep.decryptIV(msg)).equals(SNAP_HEADER+GET_CONNECTED_LIST))
			{
				String t="";
				for(String s : connected)
				{
					t += s+" ";
				}
				//to and from swaped to answer
				sendTo(wep.encryptIV(SNAP_HEADER+t),to,from);
			}
		}
	}
	
	public synchronized void sendTo(final String msg,final byte[] from,final byte[] to)
	{
		sendTo(BAC.toByteArray(msg),from,to);
	}
	
	public synchronized void sendTo(final byte[] msg,final byte[] from,final byte[] to)
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
				else if(connect(msg))
				{
				}
			}
		}).start();
	}
	
	public synchronized void addListener(Listener l)
	{
		listeners.add(l);
	}
	
	public synchronized void removeListener(Listener l)
	{
		listeners.remove(l);
	}
	//connect = verschlüsselt name+routername
	private synchronized boolean connect(byte[] connect)
	{
		if(connect.length >4)
		{
			String msg = BAC.toString(wep.decryptIV(connect));
			msg = msg.substring(1, msg.length());
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
	private synchronized boolean disconnect(byte[] disconnect)
	{
		if(disconnect.length >4)
		{
			String msg = BAC.toString(wep.decryptIV(disconnect));
			msg = msg.substring(1, msg.length());
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
	
	private synchronized boolean isConnected(String name)
	{
		for(String s : connected)
		{
			if(s.equals(name))return true;
		}
		return false;
	}
	
	private synchronized void removeConnection(String name)
	{
		String tmp = null;
		for(String s : connected)
		{
			if(s.equals(name))tmp = s;
		}
		connected.remove(tmp);
	}
	
	private synchronized boolean isConnected(byte[] name)
	{
		String n = BAC.toString(name);
		n=n.substring(1, n.length());
		return isConnected(n);
	}
}