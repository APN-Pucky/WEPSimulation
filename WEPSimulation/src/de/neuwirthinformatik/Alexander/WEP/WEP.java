package de.neuwirthinformatik.Alexander.WEP;

import java.util.Random;

import de.neuwirthinformatik.Alexander.Util.BAC;
import de.neuwirthinformatik.Alexander.Util.Util;
import de.neuwirthinformatik.Alexander.Util.XOR;

public class WEP 
{
	int key_size = 5;// 40-bit or 104 bit
	byte[] iv = new byte[3];// 24-bit
	byte[] key = new byte[key_size];// 40-bit
	byte[] seed = new byte[key_size+3];
	
	
	public WEP(byte[] key) throws Exception
	{
		if(key.length < key_size)throw new Exception();
		iv[0]=0;
		iv[1]=0;
		iv[2]=0;
		System.arraycopy(key, 0, this.key, 0, key_size);
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
	
	public byte[] encrypt(byte[] msg)
	{
		System.arraycopy(iv, 0, seed, 0, 3);
		System.arraycopy(key, 0, seed, 3, key_size);
		byte[] keystream = Util.toSignedByteArray(RC4.cipher(seed,msg.length));
		return XOR.xor(msg, keystream);
	}
	
	public String decrypt(String msg)
	{
		return BAC.toString(decrypt(BAC.toByteArray(msg)));
	}
	
	public byte[] decrypt(byte[] msg)
	{
		System.arraycopy(iv, 0, seed, 0, 3);
		System.arraycopy(key, 0, seed, 3, key_size);
		byte[] keystream = Util.toSignedByteArray(RC4.cipher(seed,msg.length));
		return XOR.xor(msg, keystream);
	}
	
	//returns iv and crypt 
	public String encryptIV(String msg)
	{
		return BAC.toString(encryptIV(BAC.toByteArray(msg)));
	}
	
	public byte[] encryptIV(byte[] msg)
	{
		byte[] crypt = encrypt(msg);
		byte[] r = new byte[crypt.length+3];
		System.arraycopy(iv, 0, r, 0, 3);
		System.arraycopy(crypt, 0, r, 3, crypt.length);
		return r;
	}
	
	//reads and sets IV
	public String decryptIV(String msg_iv)
	{
		return BAC.toString(decryptIV(BAC.toByteArray(msg_iv)));
	}
	
	public byte[] decryptIV(byte[] msg_iv)
	{
		byte[] msg = new byte[msg_iv.length-3];
		System.arraycopy(msg_iv, 0, iv, 0, 3);
		System.arraycopy(msg_iv, 3, msg, 0, msg.length);
		return decrypt(msg);
	}

	public void incIV()
	{
		if(iv[0] == 255)
		{
			iv[0]=0;
			if(iv[1]==255)
			{
				iv[1]=0;
				if(iv[2]==255)
				{
					iv[2]=0;
				}
			}
			else
			{
				iv[1]++;
			}
		}
		else
		{
			iv[0]++;
		}
	}
	
	public void decIV()
	{
		for(int i = 0; i < 255;i++)
		{
			incIV();
		}
	}
	
	public void setIV(byte[] iv)
	{
		this.iv = iv;
	}
	
	public byte[] getIV()
	{
		return iv;
	}
	
	public void randomIV()
	{
		int r = (int)(Math.random()*255);
		for(int i = 0 ; i < r;i++)
		{
			incIV();
		}
	}
}
