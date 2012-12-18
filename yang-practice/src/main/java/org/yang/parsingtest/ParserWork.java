package org.yang.parsingtest;

public class ParserWork
{
	private FileLineReader reader = null;
	private CommonParser parser = null;
	
	public ParserWork()
	{
		
	}

	public void setReader(FileLineReader reader)
	{
		this.reader = reader;
	}
	
	public void setParser(CommonParser parser)
	{
		this.parser = parser;
	}

	public int sumOfKeyword(String keyword, int keywordPosition, int valuePosition, String delemeter)
	{
		int sum = 0;
		int foundCount = 0;
		
		try
		{
			String line = "";
			while((line = reader.getNextLine()) != null)
			{
				String[] elements = parser.parse(line, delemeter);
				if(elements.length <= keywordPosition || elements.length <= valuePosition)
					continue;
				
				if(elements[keywordPosition-1].compareTo(keyword) == 0)
				{
					int targetValue = convertInt(elements[valuePosition-1]);
					if(targetValue > 0)
					{
						sum += targetValue;
						foundCount++;
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			sum = 0;
		}
		
		if(reader != null)
			reader.closeReader();
		
		return sum;
	}
	
	private int convertInt(String str)
	{
		try
		{
			return Integer.parseInt(str);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return -1;
		}
	}
	
}
