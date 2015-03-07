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
		int[] r = new int[]{	
				(crc>>(4*6))&0xFF,
				(crc>>(4*4))&0xFF,
				(crc>>(4*2))&0xFF,
				((crc&0xFF))
				};
		return r;
	}
}
