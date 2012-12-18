package org.yang.parsingtest;

import java.io.BufferedReader;
import java.io.FileReader;

public class FileLineReader
{
	private String filePath = "";
	private BufferedReader reader = null;
	
	public FileLineReader(String filePath)
	{
		this.filePath = filePath;
	}
	
	public String getNextLine() throws Exception
	{
		return getReader().readLine();
	}
	
	private BufferedReader getReader() throws Exception
	{
		if(reader == null)
		{
			reader = new BufferedReader(new FileReader(filePath));
		}
		
		return reader;
	}
	
	public void setReader(BufferedReader reader)
	{
		this.reader = reader;
	}
	
	public void closeReader()
	{
		try
		{
			if(reader != null)
				reader.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
