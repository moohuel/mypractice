package org.yang.etc;

import org.junit.Test;

public class CalculTest
{
	@Test
	public void foo()
	{
		long currTime = System.currentTimeMillis();
		System.out.println(currTime);
		
		long targetTime = 1356486170510l;
		targetTime = targetTime / 1000;
		System.out.println(targetTime);
		
		//long second = targetTime % 60;
		//long minute = targetTime / (60*60);
		//System.out.println("year="+year+", day="+day+", hour="+hour+", minute="+minute+", second="+second);
	}
}
