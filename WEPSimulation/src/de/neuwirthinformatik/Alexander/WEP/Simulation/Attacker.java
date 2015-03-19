package de.neuwirthinformatik.Alexander.WEP.Simulation;

import java.util.ArrayList;

import de.neuwirthinformatik.Alexander.Util.BAC;
import de.neuwirthinformatik.Alexander.Util.Log;
import de.neuwirthinformatik.Alexander.Util.Util;

public class Attacker implements Listener
{
	private Router r;
	int p_key_size;
	int key_size = 5;
	int a_byte = 0;
	// 40-bit key
	//int[] key = new int[] { 'd' + 0x80, 'a' + 0x80, 's' + 0x80, 'p' + 0x80, 'a' + 0x80 };
	int[] key;
	int[] p_key;
	int[] used_iv_x;
	ArrayList<byte[]> p_good = new ArrayList<byte[]>();
	// filter X
	int p_cur = 0;

	public Attacker(Router r, int p_key_size)
	{
		this.r = r;
		this.p_key_size = p_key_size;
		p_key = new int[p_key_size];
		used_iv_x = new int[p_key_size];
		this.r.addListener(this);
		key = new int[key_size];
		for(int i = 0;i < key_size;i++)
		{
			key[i] = -1;
		}
		//Log
		Log.println("Potential "+ a_byte+ ". Key Bytes: ");
		Log.incLevel();
		//Log
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
		if (pm[0] > a_byte + 3 && pm[0] < key_size + 3 && pm[1] == 255)
		{
			//System.out.println("possible good packet!");
			p_good.add(msg);
		}
		if (pm[0] == a_byte + 3 && pm[0] < key_size + 3 && pm[1] == 255 && !Util.contains(used_iv_x, pm[2]))
		{
			int first_byte = pm[3] ^ 0xAA;//SNAP_HEADER
			int[] seed = new int[8];
			System.arraycopy(pm, 0, seed, 0, 3);
			System.arraycopy(key, 0, seed, 3, 5);
			// --++KSA++--
			int[] sbox = new int[256];
			for (int i = 0; i < 256; i++)
			{
				sbox[i] = (int) i;
			}
			int j = 0;
			for (int i = 0; i < pm[0]; i++)
			{
				j += seed[i % seed.length] + sbox[i];
				j %= 256;
				int tmp = sbox[i];
				sbox[i] = sbox[j];
				sbox[j] = tmp;
			}
			// --++-+-++--
			if (j < 2)
				return;
			p_key[p_cur] = ((first_byte - sbox[pm[0]] - j) % 256 + 256) % 256;
			used_iv_x[p_cur] = pm[2];
			//Log
			Log.print("'"+(char)(p_key[p_cur]-0x80) +"', ");
			if((p_cur+1)%8==0)Log.println("");
			//Log
			p_cur++;
			if (p_cur == p_key.length)
			{
				setKey();
				if (a_byte == key_size)
				{
					Log.decLevel();
					Log.println("------------------------------------");
					Log.print("Full WEP-Key: ");
					Log.println("'"+BAC.toString(Util.toSignedByteArray(key))+ "'");
					System.exit(0);
				}
			}
		}
	}

	private synchronized void setKey()
	{
		// sort
		for (int i = 0; i < p_key.length; i++)
		{
			for (int j = p_key.length - 1; j > i; j--)
			{
				if (p_key[i] < p_key[j])
				{
					int tmp = p_key[j];
					p_key[j] = p_key[i];
					p_key[i] = tmp;
				}
			}
		}
		int max_v = 0;
		int max_i = 0;
		for (int i = 0; i < p_key.length; i++)
		{
			int j;
			for (j = 0; (j + i < p_key.length) && p_key[i] == p_key[j + i]; j++)
				;
			if (j > max_v)
			{
				max_v = j;
				max_i = i;
			}
			i += j - 1;
		}
		key[a_byte] = p_key[max_i];
		//Log
		Log.incLevel();
		Log.println("");
		Log.println("|");
		Log.println("'-> Key-Byte: " + (char) (p_key[max_i] - 0x80));
		Log.println("");
		Log.decLevel();
		Log.decLevel();
		if((a_byte+1)<key_size)Log.println("Potential " + (a_byte+1) +". Key Bytes: ");
		Log.incLevel();
		//Log
		p_cur = 0;
		p_key = Util.setNegative(p_key);
		used_iv_x = Util.setNegative(used_iv_x);
		a_byte++;
		
		ArrayList<byte[]> s_good = (ArrayList<byte[]>) p_good.clone();
		p_good.clear();
		for (byte[] msg : s_good)
		{
			check(msg);
		}

		// tmp
		//System.exit(0);
	}
}
