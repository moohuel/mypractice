package org.yang.parsingtest;

public class CommonParser
{
	public String[] parse(String line, String delemeter)
	{
		return line.split(delemeter);
	}
}
