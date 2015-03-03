package de.neuwirthinformatik.Alexander.Util;

public class Util
{
	//int->u_byte
	public static int[] toUnsignedByteArray(byte[] b)
	{
		int[] r = new int[b.length];
		for(int i =0; i< b.length;i++)
		{
			r[i] = b[i]+0x80;
		}
		return r;
	}
	
	public static byte[] toSignedByteArray(int[] b)
	{
		byte[] r = new byte[b.length];
		for(int i =0; i< b.length;i++)
		{
			r[i] = (byte)(b[i]-0x80);
		}
		return r;
	}

	public static String toHex(byte b)
	{
		b +=0x80;
		int hb = (b&0xF0)>>4;
		int lb = b&0x0F;
		return toHex(hb)+toHex(lb);
	}
	
	public static String toHex(int half_byte)
	{
		if(half_byte>15||half_byte<0)return null;
		if(half_byte <10)
		{
			return String.valueOf(half_byte);
		}
		else
		{
			switch(half_byte)
			{
			case(10):return "A";
			case(11):return "B";
			case(12):return "C";
			case(13):return "D";
			case(14):return "E";
			case(15):return "F";
			default:return  null;
			}
		}
	}
	
	public static boolean isZero(byte[] arr_b)
	{
		for(byte b: arr_b)
		{
			if(b != 0)return false;
		}
		return true;
	}
	
	public static boolean isZero(int[] arr_i)
	{
		for(int i: arr_i)
		{
			if(i != 0)return false;
		}
		return true;
	}
	
	public static void throwException(String e)
	{
		throw new RuntimeException(e);
	}
}
