package de.neuwirthinformatik.Alexander.WEP.Simulation;

import de.neuwirthinformatik.Alexander.Util.Util;

public class Attacker implements Listener
{
	private Router r;
	int a_byte = 3;
	//40-bit key
	int[] key = new int[]{'d'+0x80,'x'+0x80,'s'+0x80,'p'+0x80,'a'+0x80};
	int[] p_key = new int[250];
	int[] used_iv_x = new int[250];
	//filter X
	int p_cur = 0;
	
	public Attacker(Router r)
	{
		this.r = r;
		this.r.addListener(this);
	}

	public void listen(byte[] msg, byte[] from, byte[] to)
	{
		check(msg);
		check(from);
		check(to);
	}
	
	public synchronized void check(byte[] msg)
	{
		int[] pm = Util.toUnsignedByteArray(msg);
		if(pm[0]==a_byte+3 && pm[1]==255 && !Util.contains(used_iv_x, pm[2]))
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
			p_key[p_cur]=((first_byte-sbox[pm[0]]-j)%256+256)%256;
			used_iv_x[p_cur] = pm[2];
			//System.out.println("Guess: " + p_key[p_cur]);
			//System.out.println("Guess: " + (char)p_key[p_cur]);
			//System.out.println("Guess: " + (char)(p_key[p_cur]-0x80));
			p_cur++;
			if(p_cur == p_key.length)
			{
				setKey();
				if(a_byte==5)
				{
					System.out.println("Cracked WEP!!!");
				}
				p_cur = 0;
				p_key=Util.setNegative(p_key);
				used_iv_x=Util.setNegative(used_iv_x);
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
		System.out.println("Got Byte: " + (p_key[max_i]));
		System.out.println("Got Char: " + (char)(p_key[max_i]-0x80));
		a_byte++;
		//tmp
		System.exit(0);
	}
}
