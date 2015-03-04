package de.neuwirthinformatik.Alexander.WEP;

import de.neuwirthinformatik.Alexander.Util.BAC;
import de.neuwirthinformatik.Alexander.Util.HexDump;
import de.neuwirthinformatik.Alexander.Util.Util;
import de.neuwirthinformatik.Alexander.Util.XOR;

public class WEP
{
	int key_size = 5;// 40-bit or 104 bit
	int iv_size = 3;
	int crc32_size = 4;
	int[] iv = new int[iv_size];// 24-bit
	int[] key = new int[key_size];// 40-bit
	int[] seed = new int[key_size + iv_size];

	public WEP(byte[] key) throws Exception
	{
		if (key.length < key_size)
			throw new Exception();
		iv[0] = 0;
		iv[1] = 0;
		iv[2] = 0;
		System.arraycopy(Util.toUnsignedByteArray(key), 0, this.key, 0, key_size);
		randomIV();
	}

	public WEP(String key) throws Exception
	{
		this(BAC.toByteArray(key));
	}

	public String encrypt(String msg)
	{
		return BAC.toString(encrypt(BAC.toByteArray(msg)));
	}
	
	public int[] encrypt(int[] msg)
	{
		int[] pm = new int[msg.length+crc32_size];
		System.arraycopy(iv, 0, seed, 0, iv_size);
		System.arraycopy(key, 0, seed, iv_size, key_size);
		System.arraycopy(msg, 0, pm, 0, msg.length);
		System.arraycopy(CRC32.getCS(pm), 0, pm, msg.length, crc32_size);
		int[] keystream = RC4.cipher(seed, pm.length);
		return XOR.xor(pm, keystream);
	}

	public byte[] encrypt(byte[] msg)
	{
		return Util.toSignedByteArray(encrypt(Util.toUnsignedByteArray(msg)));
	}

	public String decrypt(String msg)
	{
		return BAC.toString(decrypt(BAC.toByteArray(msg)));
	}
	
	public int[] decrypt(int[] msg)
	{
		System.arraycopy(iv, 0, seed, 0, iv_size);
		System.arraycopy(key, 0, seed, iv_size, key_size);
		int[] keystream = RC4.cipher(seed, msg.length);
		int[] pm = XOR.xor(msg, keystream);
		int[] r = new int[pm.length-crc32_size];
		int[] crc = new int[crc32_size];
		System.arraycopy(pm, 0, r,0,r.length);
		System.arraycopy(pm, r.length, crc ,0,crc32_size);
		HexDump.dump(crc);
		HexDump.dump(CRC32.getCS(r));
		if(!Util.isZero(XOR.xor(CRC32.getCS(r),crc)))Util.throwException("Msgs are not equal!");
		return r;
	}

	public byte[] decrypt(byte[] msg)
	{
		return Util.toSignedByteArray(decrypt(Util.toUnsignedByteArray(msg)));
	}

	// returns iv and crypt
	public String encryptIV(String msg)
	{
		return BAC.toString(encryptIV(BAC.toByteArray(msg)));
	}

	public byte[] encryptIV(byte[] msg)
	{
		incIV();
		byte[] crypt = encrypt(msg);
		byte[] r = new byte[crypt.length + 3];
		System.arraycopy(Util.toSignedByteArray(iv), 0, r, 0, 3);
		System.arraycopy(crypt, 0, r, 3, crypt.length);
		return r;
	}

	// reads and sets IV
	public String decryptIV(String msg_iv)
	{
		return BAC.toString(decryptIV(BAC.toByteArray(msg_iv)));
	}

	public byte[] decryptIV(byte[] msg_iv)
	{
		if (msg_iv.length < 4)
			return new byte[] {};
		byte[] msg = new byte[msg_iv.length - 3];
		int[] s_iv = iv.clone();
		System.arraycopy(Util.toUnsignedByteArray(msg_iv), 0, iv, 0, 3);
		System.arraycopy(msg_iv, 3, msg, 0, msg.length);
		byte[] r = decrypt(msg);
		this.iv = s_iv;
		return r;
	}

	public void incIV()
	{
		if (iv[2] == 0xFF)
		{
			iv[2] = 0;
			if (iv[1] == 0xFF)
			{
				iv[1] = 0;
				if(iv[0] == 0xFF)
				{
					iv[0] = 0;
				}
				else
				{
					iv[0]++;
				}
			}
			else
			{
				iv[1]++;
			}
		}
		else
		{
			iv[2]++;
		}
	}

	public void setIV(byte[] iv)
	{
		this.iv = Util.toUnsignedByteArray(iv);
	}

	public byte[] getIV()
	{
		return Util.toSignedByteArray(iv);
	}

	public void randomIV()
	{
		int r = (int) (Math.random() * 256 * 256 * 256);
		for (int i = 0; i < r; i++)
		{
			incIV();
		}
	}
}
