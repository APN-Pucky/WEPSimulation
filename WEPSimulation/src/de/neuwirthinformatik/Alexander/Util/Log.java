package de.neuwirthinformatik.Alexander.Util;

public class Log 
{
	public static int level = 0;
	public static void print(String s)
	{
		s=s.replaceAll("\\\\","\\\\\\\\");
		s=s.replaceAll("\n","\\\\n");
		s=s.replaceAll("\t","\\\\t");
		s=s.replaceAll("\b","\\\\b");
		System.out.print(s);
	}
	
	public static void println(String s)
	{
		print(s);
		System.out.println();
		applyLevel();
	}
	
	public static void applyLevel()
	{
		for(int i = 0; i < level;i++)
		{
			System.out.print("\t");
		}
	}
	
	public static void incLevel()
	{
		level++;
	}
	
	public static void decLevel()
	{
		level--;
	}
}
