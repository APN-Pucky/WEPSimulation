package de.neuwirthinformatik.Alexander.WEP.Simulation;

import de.neuwirthinformatik.Alexander.Util.BAC;
import de.neuwirthinformatik.Alexander.Util.HexDump;
import de.neuwirthinformatik.Alexander.Util.Log;
import de.neuwirthinformatik.Alexander.WEP.WEP;

public class Computer extends AbstractSender implements Listener
{
	//private String name;
	//private WEP wep;
	//private Router r;
	
	public Computer(Router r,String name)
	{
		this.name = name;
		this.r = r;
		try
		{
			wep = new WEP("daspasswort");
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		wep.randomIV();
		r.addListener(this);
		byte[] tb = BAC.toByteArray(wep.encryptIV(name));
		byte[] rb = BAC.toByteArray(wep.encryptIV(Router.ROUTER_NAME));
		send(this.name+Router.ROUTER_NAME,Router.ROUTER_NAME);
	}

	public void listen(byte[] msg, byte[] from, byte[] to)
	{
		if(BAC.toString(wep.decryptIV(to)).equals(name))
		{
			byte[] msg_dc = wep.decryptIV(msg);
			byte[] to_dc = wep.decryptIV(to);
			byte[] from_dc = wep.decryptIV(from);
			
			//Log.incLevel();
			Log.println(name + " received (from "+ BAC.toString(from_dc) +"):");
			/*Log.incLevel();
			Log.println("raw:");
			Log.println("");
			HexDump.dump(msg);
			Log.decLevel();
			Log.println("");
			Log.incLevel();
			Log.println("decrypted:");
			HexDump.dump(wep.decryptIV(msg));
			Log.decLevel();
			Log.decLevel();
			Log.println("");
			*/
			try
			{
				Thread.sleep(5000);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			r.sendTo(wep.encryptIV(msg_dc),wep.encryptIV(to_dc),wep.encryptIV(from_dc));
		}
	}
}
