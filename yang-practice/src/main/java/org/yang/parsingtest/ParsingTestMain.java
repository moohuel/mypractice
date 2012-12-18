package org.yang.parsingtest;

public class ParsingTestMain
{
	public static void main(String[] args)
	{
		if(args.length == 0)
		{
			System.out.println("Usage: [filepath]");
			return;
		}
		
		ParsingTestMain test = new ParsingTestMain();
		test.test(args[0]);
	}
	
	public void test(String filePath)
	{
		FileLineReader reader = new FileLineReader(filePath);
		CommonParser parser = new CommonParser();
		
		ParserWork worker = new ParserWork();
		worker.setReader(reader);
		worker.setParser(parser);
		
		long startTime = System.currentTimeMillis();
		int sum = worker.sumOfKeyword("korea", 1, 4, ",");
		long endTime = System.currentTimeMillis();
		System.out.println("sum is "+sum);
		System.out.println("takenTime is "+(endTime-startTime));
	}
}
