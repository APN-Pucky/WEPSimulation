package de.neuwirthinformatik.Alexander.WEP.Simulation;

public class Simulation
{
	public Simulation()
	{
		Router r = new Router();
		Attacker a = new Attacker(r);
		Computer c1 = new Computer(r,"Computer1");
		Computer c2 = new Computer(r,"Computer2");
		//Computer c3 = new Computer(r,"Computer3");
		//Computer c4 = new Computer(r,"Computer4");
		//Computer c5 = new Computer(r,"Computer5");
		Computer c6 = new Computer(r,"Computer6");
		Computer c7 = new Computer(r,"Computer7");
		//Computer c8 = new Computer(r,"Computer8");
		//Computer c9 = new Computer(r,"Computer9");
		//Computer c0 = new Computer(r,"Computer0");
		c1.send("HalloFromPC1-6", "Computer6");
		c2.send("HalloFromPC2-7", "Computer7");
		//c3.send("HalloFromAPC3-8", "Computer8");
		//c4.send("HalloFromAPC4-9", "Computer9");
		//c5.send("HalloFromAPC5-0", "Computer0");
	}
}
