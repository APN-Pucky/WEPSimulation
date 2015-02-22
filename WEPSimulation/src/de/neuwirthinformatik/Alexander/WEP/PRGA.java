package de.neuwirthinformatik.Alexander.WEP;

public class PRGA
{
	public static int[] getKeystream(int[] sbox, int length)
	{
		int[] keystream = new int[length];
		int k = 0;
		int j = 0;
		for(int i = 0; i < length;i++)
		{
			k = (++k)%256;
			j = (j+sbox[k])%256;
			int tmp = sbox[k];
			sbox[k] = sbox[j];
			sbox[j] = tmp;
			int r = (sbox[k]+sbox[j])%256;
			keystream[i] = sbox[r];
		}
		return keystream;
	}
}
