package de.neuwirthinformatik.Alexander.WEP;

import de.neuwirthinformatik.Alexander.Util.Util;

public class RC4 
{
	public static int[] cipher(int[] seed, int length)
	{
		return PRGA.getKeystream(KSA.getSbox(seed),length);
	}

}
