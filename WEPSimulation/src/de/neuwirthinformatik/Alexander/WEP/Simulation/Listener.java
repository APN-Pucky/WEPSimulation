package de.neuwirthinformatik.Alexander.WEP.Simulation;

public interface Listener 
{
	public void listen(byte[] msg,byte[] from, byte[] to);
}
