package de.neuwirthinformatik.Alexander.WEP;

import de.neuwirthinformatik.Alexander.Util.HexDump;

public class Test
{
	public static void main(String[] args)
	{
		/*WEP w = null;
		try
		{
			w = new WEP("Dies ist ein Key");
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		String msg = "HelloWorld!";
		System.out.println(msg);
		String cry = w.encrypt(msg);
		System.out.println(cry);
		msg = w.decrypt(cry);
		System.out.println(msg);
	*/
		HexDump.dump("ABCD\\abcd35†67\t\n567=)(/&%$§");
	}
}
