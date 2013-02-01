package org.yang.nettest.client;

public class ClientMain
{
	public static void main(String[] args) throws Exception
	{
		/*
		SocketClientSelector selectorClient = new SocketClientSelector();
		boolean isBlocking = false;
		selectorClient.connect("127.0.0.1", 9090, isBlocking);
		selectorClient.send("Hello");
		*/
		
//		SocketClientBasic basicClient = new SocketClientBasic();
//		boolean isBlocking = true;
//		basicClient.connect("127.0.0.1", 9090, isBlocking);
//		basicClient.send("Hello. This is basic socket test1");
//		basicClient.read();
//		
//		Thread.sleep(5000);
//		basicClient.send("Hello. This is basic socket test2");
//		basicClient.read();
		
		
		SocketClientBasicSocket basicClient2 = new SocketClientBasicSocket();
		boolean isBlocking = true;
		basicClient2.connect("127.0.0.1", 9090, isBlocking);
//		basicClient2.send("Hello. This is basic socket test1");
//		basicClient2.read();
//		
//		Thread.sleep(5000);
//		basicClient2.send("Hello. This is basic socket test2");
//		basicClient2.read();
		
		for(int i=0; i<100; i++)
		{
			String msg = "Hello. This is basic socket test"+i+"\n";
			basicClient2.send(msg);
			System.out.println(msg);
		}
		
		basicClient2.closeOutput();
		
		Thread.sleep(200000);
	}
}
