package de.neuwirthinformatik.Alexander.WEP.Simulation;

import de.neuwirthinformatik.Alexander.Util.Util;
import de.neuwirthinformatik.Alexander.WEP.KSA;

public class Attacker implements Listener
{
	private Router r;
	int a_byte = 0;
	//40-bit key
	int[] key = new int[]{0,0,0,0,0};
	int[] p_key = new int[100];
	int p_cur = 0;
	
	public Attacker(Router r)
	{
		this.r = r;
		this.r.addListener(this);
	}

	public void listen(byte[] msg, byte[] from, byte[] to)
	{
		int[] pm = Util.toUnsignedByteArray(msg);
		if(pm[0]==a_byte+3 && pm[1]==255)
		{
			int first_byte = pm[3]^0xAA;
			int[] seed = new int[8];
			System.arraycopy(pm, 0, seed, 0, 3);
			System.arraycopy(key, 0, seed, 3, 5);
			//--++KSA++--
			int[] sbox = new int[256];
			for(int i = 0; i< 256;i++)
			{
				sbox[i] = (int)i;
			}
			int j = 0;
			for(int i = 0; i < pm[0];i++)
			{
				j += seed[i%seed.length]+sbox[i];
				j %= 256;
				int tmp = sbox[i];
				sbox[i] = sbox[j];
				sbox[j] = tmp;
			}
			//--++-+-++--
			if(j<2)return;
			p_key[p_cur]=((first_byte-sbox[3]-j)%256+256)%256;
			p_cur++;
			if(p_cur == 100)
			{
				setKey();
				if(a_byte==5)
				{
					System.out.println("Cracked WEP!!!");
				}
				p_cur = 0; 
				//aha;
			}
		}
	}
	
	//CHECK!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!CHECK!!!!!!!!!!!!!!!!!!!!!!CHECK
	private void setKey()
	{
		//sort
		for(int i= 0; i < p_key.length;i++)
		{
			for(int j= p_key.length-1; j > i;j--)
			{
				if(p_key[i]<p_key[j])
				{
					int tmp = p_key[j];
					p_key[j] = p_key[i];
					p_key[i] = tmp;
				}
			}
		}
		int max_v = 0;
		int max_i = 0;
		for(int i= 0; i< p_key.length;i++)
		{
			int j;
			for(j = 0;(j+i<p_key.length)&&p_key[i]==p_key[j+i];j++);
			if(j>max_v)
			{
				max_v = j;
				max_i = i;
			}
			i+=j-1;
		}
		key[a_byte] = p_key[max_i];
		a_byte++;
	}
}
