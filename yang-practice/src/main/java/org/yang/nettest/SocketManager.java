package org.yang.nettest;

import java.nio.channels.SocketChannel;

public class SocketManager
{
	
	public SocketManager()
	{
		
	}
	
	public void addSocket(SocketChannel socket)
	{
		try
		{
			socket.configureBlocking(false);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		//case1
		SocketChannelServerReader reader = new SocketChannelServerReader(socket);
		reader.start();
	}
}
