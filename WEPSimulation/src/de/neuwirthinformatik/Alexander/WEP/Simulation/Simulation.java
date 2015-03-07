package de.neuwirthinformatik.Alexander.WEP.Simulation;

public class Simulation
{
	public Simulation()
	{
		Router r = new Router();
		Attacker a = new Attacker(r);
		Computer c1 = new Computer(r,"Computer1");
		Computer c2 = new Computer(r,"Computer2");
		Computer c3 = new Computer(r,"Computer3");
		Computer c4 = new Computer(r,"Computer4");
		Computer c5 = new Computer(r,"Computer5");
		Computer c6 = new Computer(r,"Computer6");
		Computer c7 = new Computer(r,"Computer7");
		Computer c8 = new Computer(r,"Computer8");
		Computer c9 = new Computer(r,"Computer9");
		Computer c0 = new Computer(r,"Computer0");
		
		c1.send("Hallo, PC1-6", "Computer6");
		c2.send("Hallo, PC2-7", "Computer7");
		c3.send("Hallo, PC3-8", "Computer8");
		c4.send("Hallo, PC4-9", "Computer9");
		c5.send("Hallo, PC5-0", "Computer0");
		c1.send("Hallo, PC1-6", "Computer5");
		c2.send("Hallo, PC2-7", "Computer4");
		c3.send("Hallo, PC3-8", "Computer3");
		c4.send("Hallo, PC4-9", "Computer2");
		c5.send("Hallo, PC5-0", "Computer1");
	}
}
