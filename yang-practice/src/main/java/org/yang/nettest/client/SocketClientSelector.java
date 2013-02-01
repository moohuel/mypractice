package org.yang.nettest.client;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import org.yang.nettest.SocketChannelServerReader;

public class SocketClientSelector
{
	private SocketChannel channel;
	
	public SocketClientSelector()
	{
		
	}
	
	public void connect(String ip, int port, boolean isBlocking)
	{
		SocketClientConnector connector = new SocketClientConnector();
		channel = connector.connect(ip,  port, isBlocking);
		
		SocketChannelServerReader reader = new SocketChannelServerReader(channel);
		reader.start();
	}
	
	public void send(String msg)
	{
		ByteBuffer buffer = ByteBuffer.allocate(2048);
		buffer.put(msg.getBytes());
		buffer.flip();
		
		try
		{
			while(buffer.hasRemaining())
			{
				int writeSize = channel.write(buffer);
				buffer.compact();
				buffer.flip();
				System.out.println("write size:"+writeSize);
			}
			
			System.out.println("send msg:"+msg);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
