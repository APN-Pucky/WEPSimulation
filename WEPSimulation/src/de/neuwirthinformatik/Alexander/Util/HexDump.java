package de.neuwirthinformatik.Alexander.Util;

public class HexDump
{
	public static int bytes_per_row = 8;
	public static int bytes_per_colum = 1;
	public static String byte_sep = " ";
	public static String bytes_string_sep = " | ";
	public static String empty_bytes = "  "; 
	
	public static void dump(String s)
	{
		dump(BAC.toByteArray(s));
	}
	
	public static void dump(byte[] b)
	{
		for(int i = 0; i < b.length;i++)
		{
			Log.print(Util.toHex(b[i]));
			if((i+1)%bytes_per_colum==0)
			{
				Log.print(byte_sep);
			}
			if((i+1)%bytes_per_row==0)
			{
				Log.print(bytes_string_sep);
				Log.println(BAC.toString(b).substring(i+1-bytes_per_row, i+1));
			}
			else if(i+1==b.length)
			{
				for(int j = 0; j<bytes_per_row-((i+1)%bytes_per_row);j++)
				{
					Log.print(empty_bytes);
					Log.print(byte_sep);
				}
				Log.print(bytes_string_sep);
				Log.println(BAC.toString(b).substring(i+1-((i+1)%bytes_per_row), i+1));
			}
		}
	}
}
