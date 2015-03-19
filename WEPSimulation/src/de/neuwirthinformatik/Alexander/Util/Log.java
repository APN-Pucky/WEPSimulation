package de.neuwirthinformatik.Alexander.Util;

public class Log 
{
	public static int level = 0;
	
	public synchronized static void print(String s)
	{
		s=s.replaceAll("\\\\","\\\\\\\\");
		s=s.replaceAll("\n","\\\\n");
		s=s.replaceAll("\t","\\\\t");
		s=s.replaceAll("\b","\\\\b");
		s=s.replaceAll("\r","\\\\r");
		s=s.replaceAll("\f","\\\\f");
		applyLevel();
		System.out.print(s);
	}
	
	public synchronized static void println(String s)
	{
		print(s);
		System.out.println();
	}
	
	public synchronized static void applyLevel()
	{
		for(int i = 0; i < level;i++)
		{
			System.out.print("\t");
		}
	}
	
	public synchronized static void incLevel()
	{
		level++;
	}
	
	public synchronized static void decLevel()
	{
		level--;
	}
}
