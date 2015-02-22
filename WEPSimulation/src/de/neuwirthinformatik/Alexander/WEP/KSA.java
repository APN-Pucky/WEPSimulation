package de.neuwirthinformatik.Alexander.WEP;

public class KSA
{
	public static int[] getSbox(int[] seed)
	{
		return getSbox(seed,256);
	}
	
	public static int[] getSbox(int[] seed, int steps)
	{
		int[] sbox = new int[256];
		for(int i = 0; i< 256;i++)
		{
			sbox[i] = (int)i;
		}
		int j = 0;
		for(int i = 0; i < steps;i++)
		{
			j += seed[i%seed.length]+sbox[i];
			j %= 256;
			int tmp = sbox[i];
			sbox[i] = sbox[j];
			sbox[j] = tmp;
		}
		return sbox;
	}
}
