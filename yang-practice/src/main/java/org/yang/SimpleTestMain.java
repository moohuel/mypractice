package org.yang;

public class SimpleTestMain
{
	public static void main(String[] args)
	{
		String str = "감";
		byte[] bb = str.getBytes();
		char c = str.charAt(0);
		
		System.out.println(bb[0]);
	}
}
