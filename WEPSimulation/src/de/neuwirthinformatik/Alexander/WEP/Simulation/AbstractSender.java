package de.neuwirthinformatik.Alexander.WEP.Simulation;

import de.neuwirthinformatik.Alexander.Util.BAC;
import de.neuwirthinformatik.Alexander.WEP.WEP;

public abstract class AbstractSender implements Sender
{
	WEP wep;
	Router r;
	String name;
	
	public void send(String msg,String to)
	{
		r.sendTo(wep.encryptIV(Router.SNAP_HEADER+msg),BAC.toByteArray(wep.encryptIV(Router.SNAP_HEADER+name)),BAC.toByteArray(wep.encryptIV(Router.SNAP_HEADER+to)));
	}
}
