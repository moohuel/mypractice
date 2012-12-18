package org.yang.parsingtest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

public class CommonParserTest
{
	@Test
	public void parse()
	{
		CommonParser parser = new CommonParser();
		String[] elements = parser.parse("aa,bb,c,d,e", ",");
		
		assertThat(elements.length, equalTo(5));
		assertThat(elements[0], equalTo("aa"));
		assertThat(elements[1], equalTo("bb"));
		assertThat(elements[2], equalTo("c"));
		assertThat(elements[3], equalTo("d"));
		assertThat(elements[4], equalTo("e"));
	}
	
	@Test
	public void parseLinefeed()
	{
		CommonParser parser = new CommonParser();
		String[] elements = parser.parse("", ",");
		
		System.out.println("linefeed len="+elements.length+", result="+elements[0]+".");
	}
}
