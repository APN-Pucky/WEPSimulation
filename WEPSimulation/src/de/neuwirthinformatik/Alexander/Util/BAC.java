package de.neuwirthinformatik.Alexander.Util;

import java.nio.ByteBuffer;

//ByteArrayConverter
public class BAC 
{
	public static byte[] toByteArray(short v) 
	{
		byte[] bytes = new byte[2];
		ByteBuffer.wrap(bytes).putShort(v);
		return bytes;
	}
	
	public static byte[] toByteArray(int v) 
	{
		byte[] bytes = new byte[4];
		ByteBuffer.wrap(bytes).putInt(v);
		return bytes;
	}
	
	public static byte[] toByteArray(double v) 
	{
		byte[] bytes = new byte[8];
		ByteBuffer.wrap(bytes).putDouble(v);
		return bytes;
	}
	
	public static byte[] toByteArray(String v)
	{
		int len_s = v.length();
 		byte[] r = new byte[len_s];
 		for (int i=0;i<len_s;i++) 
 		{
 			r[i] =  (byte)v.charAt(i);
 		}
 		return r;
	}
		
	public static short toShort(byte[] bytes) 
	{
		return ByteBuffer.wrap(bytes).getShort();
	}
	
	public static int toInt(byte[] bytes) 
	{
		return ByteBuffer.wrap(bytes).getInt();
	}
	
	public static double toDouble(byte[] bytes) 
	{
		return ByteBuffer.wrap(bytes).getDouble();
	}
	
	public static String toString(byte[] byte_string)
	{
		String r=new String();
 		for(int i=0; i<byte_string.length;i++) 
 		{
 			r +=(char) byte_string[i];
 		}
 		return r;
	}
}
