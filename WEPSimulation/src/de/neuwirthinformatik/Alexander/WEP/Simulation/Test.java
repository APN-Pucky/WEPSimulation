package de.neuwirthinformatik.Alexander.WEP.Simulation;

import de.neuwirthinformatik.Alexander.Util.HexDump;

public class Test
{
	public static void main(String[] args)
	{
		//Router r = new Router();
		//Computer c = new Computer(r,"Computer1");
		//System.out.println(Router.SNAP_HEADER);
		HexDump.dump(Router.SNAP_HEADER);
		HexDump.dump(new byte[]{255-0x80});
		//HexDump.dump(BAC.toByteArray(Router.SNAP_HEADER));
		
		
	}
}
