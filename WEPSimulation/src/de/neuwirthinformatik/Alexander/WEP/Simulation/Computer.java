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
		r.addListener(this);
		byte[] tb = BAC.toByteArray(wep.encryptIV(name));
		byte[] rb = BAC.toByteArray(wep.encryptIV(Router.ROUTER_NAME));
		r.sendTo(BAC.toByteArray(BAC.toString(wep.getIV()) + wep.encrypt(this.name+Router.ROUTER_NAME)),tb,rb);
		r.sendTo(wep.encryptIV(Router.GET_CONNECTED_LIST), tb, rb);
	}

	public void listen(byte[] msg, byte[] from, byte[] to)
	{
		if(BAC.toString(wep.decryptIV(to)).equals(name))System.out.println(BAC.toString(wep.decryptIV(msg)));
	}
}
