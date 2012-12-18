package org.yang.parsingtest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

import org.junit.Test;

public class ParserWorkTest
{
	@Test
	public void work() throws Exception
	{
		MockFileLineReader readerMock = mock(MockFileLineReader.class);
		when(readerMock.getNextLine())
			.thenReturn("aa,b,c,5,1,2")
			.thenReturn("aa,b,c,6,1,2")
			.thenReturn(null);
		
		MockCommonParser parserMock = mock(MockCommonParser.class);
		when(parserMock.parse("aa,b,c,5,1,2", ","))
			.thenReturn(getTestElements());
		when(parserMock.parse("aa,b,c,6,1,2", ","))
		.thenReturn(getTestElements2());	
		
		ParserWork work = new ParserWork();
		work.setReader(readerMock);
		work.setParser(parserMock);

		int keywordPosition = 1;
		int valuePosition = 4;
		int sum = work.sumOfKeyword("aa", keywordPosition, valuePosition, ",");
		System.out.println("sum is "+sum);
		assertThat(sum, equalTo(11));
		
		verify(readerMock, atLeastOnce()).getNextLine();
	}
	
	public String[] getTestElements()
	{
		String[] tests = new String[6];
		tests[0] = "aa";
		tests[1] = "b";
		tests[2] = "c";
		tests[3] = "5";
		tests[4] = "1";
		tests[5] = "2";
		return tests;
	}
	
	public String[] getTestElements2()
	{
		String[] tests = new String[6];
		tests[0] = "aa";
		tests[1] = "b";
		tests[2] = "c";
		tests[3] = "6";
		tests[4] = "1";
		tests[5] = "2";
		return tests;
	}
}
