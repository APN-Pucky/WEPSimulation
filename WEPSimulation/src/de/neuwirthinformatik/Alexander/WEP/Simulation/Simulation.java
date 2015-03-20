package de.neuwirthinformatik.Alexander.WEP.Simulation;

public class Simulation
{
	public Simulation()
	{
		Router r = new Router("ROUTER1","Info!");
		Attacker a = new Attacker(r,60);
		Computer c1 = new Computer(r,"Computer1","Info!",false);
		//Computer c2 = new Computer(r,"Computer2");
		//Computer c3 = new Computer(r,"Computer3");
		//Computer c4 = new Computer(r,"Computer4");
		//Computer c5 = new Computer(r,"Computer5");
		Computer c6 = new Computer(r,"Computer6","Info!",false);
		//Computer c7 = new Computer(r,"Computer7");
		//Computer c8 = new Computer(r,"Computer8");
		//Computer c9 = new Computer(r,"Computer9");
		//Computer c0 = new Computer(r,"Computer0");
		
		c1.send("Hallo, PC in der WEP-Simulation", "Computer6");
		/*c2.send("Hallo, PC2-7", "Computer7");
		c3.send("Hallo, PC3-8", "Computer8");
		c4.send("Hallo, PC4-9", "Computer9");
		c5.send("Hallo, PC5-0", "Computer0");
		c1.send("Hallo, PC1-6", "Computer5");
		c2.send("Hallo, PC2-7", "Computer4");
		c3.send("Hallo, PC3-8", "Computer3");
		c4.send("Hallo, PC4-9", "Computer2");
		c5.send("Hallo, PC5-0", "Computer1");
		
		c6.send("Hallo, PC1-6", "Computer7");
		c7.send("Hallo, PC2-7", "Computer8");
		c8.send("Hallo, PC3-8", "Computer9");
		c9.send("Hallo, PC4-9", "Computer0");
		c0.send("Hallo, PC5-0", "Computer1");
		c6.send("Hallo, PC1-6", "Computer2");
		c7.send("Hallo, PC2-7", "Computer3");
		c8.send("Hallo, PC3-8", "Computer4");
		c9.send("Hallo, PC4-9", "Computer5");
		c0.send("Hallo, PC5-0", "Computer6");*/
	}
}
