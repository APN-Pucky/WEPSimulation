package de.neuwirthinformatik.Alexander.WEPSimulation;

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
		byte[] keystream = RC4.cipher(seed);
		incIV();
		return XOR.xor(msg, keystream);
	}

	private void incIV()
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
}
