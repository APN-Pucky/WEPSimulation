package de.neuwirthinformatik.Alexander.WEP;

public class CRC32
{
	
	public static int[] getCS(int[] bytes)
	{
		int crc = 0xFFFFFFFF;
		int poly = 0xEDB88320;
		for (int b : bytes)
		{
			int temp = (crc ^ b) & 0xff;
			for (int i = 0; i < 8; i++)
			{
				if ((temp & 1) == 1)
					temp = (temp >>> 1) ^ poly;
				else
					temp = (temp >>> 1);
			}
			crc = (crc >>> 8) ^ temp;
		}
		//int -> 32-bits
		crc = crc ^ 0xffffffff;
		return new int[]{	
				(crc&0xFF000000)>>(4*6),
				(crc&0xFF0000)>>(4*4),
				(crc&0xFF00)>>(4*2),
				(crc&0xFF)
				};
	}
}
