package org.yang.parsingtest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.Test;

import java.io.BufferedReader;

public class FileLineReaderTest
{
	@Test
	public void getNextLineTest() throws Exception
	{
		FileLineReader lineReader = new FileLineReader("./test-resource/items.log");
		BufferedReader testLineReader = mock(BufferedReader.class);
		lineReader.setReader(testLineReader);
		
		when(testLineReader.readLine()).thenReturn("aaa");
		
		try
		{
			String line = lineReader.getNextLine();
			assertThat(line, equalTo("aaa"));
			
			line = lineReader.getNextLine();
			assertThat(line, equalTo("aaa"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			assertTrue(false);
		}
		
		lineReader.closeReader();
	}
	
	@Test
	public void endOfFile()
	{
		FileLineReader lineReader = new FileLineReader("./test-resource/items.log");
		
		try
		{
			String line = lineReader.getNextLine();
			assertNull(line);
			System.out.println("End of File");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			assertTrue(false);
		}
		
		lineReader.closeReader();
	}
}
