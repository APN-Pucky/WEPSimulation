package de.neuwirthinformatik.Alexander.WEP.Simulation;

import de.neuwirthinformatik.Alexander.Util.BAC;
import de.neuwirthinformatik.Alexander.WEP.WEP;

public class Computer implements Listener
{
	private String name;
	private WEP wep;
	
	public Computer(Router r,String name)
	{
		this.name = name;
		try
		{
			wep = new WEP("daspasswort");
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		wep.randomIV();
		r.connnect(this,BAC.toByteArray(BAC.toString(wep.getIV()) + wep.encrypt(name+Router.ROUTER_NAME)));
	}

	public void listen(byte[] msg, byte[] from, byte[] to)
	{
		
	}

}
