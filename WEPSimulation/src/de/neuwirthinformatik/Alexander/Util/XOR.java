package de.neuwirthinformatik.Alexander.Util;

public class XOR
{
	public static byte[] xor(byte[] a, byte[] b)
	{
		byte[] c = new byte[a.length];
		for(int i =0;i < a.length;i++)
		{
			c[i] = (byte) (a[i] ^ b[i]);
		}
		return c;
	}
}
