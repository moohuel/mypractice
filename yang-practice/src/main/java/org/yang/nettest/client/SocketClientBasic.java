package org.yang.nettest.client;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class SocketClientBasic
{
	private SocketChannel socketChannel;
	
	public SocketClientBasic()
	{
	}

	public void connect(String ip, int port, boolean isBlocking)
	{
		try
		{
			InetSocketAddress inet = new InetSocketAddress(ip, port);
			
			socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(isBlocking);
            socketChannel.socket().setSoTimeout(2000); //SocketChannel을 통해서는 안 먹네. Not works!
            socketChannel.connect(inet);
            
            Thread.sleep(100);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
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
				int writeSize = socketChannel.write(buffer);
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
	
	public void read()
	{
		ByteBuffer buffer = ByteBuffer.allocate(2048);
		
		try
		{
			int readSize = socketChannel.read(buffer);
			
			if(readSize <= 0)
			{
				System.out.println("ReadSize="+readSize);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			
			try{
				socketChannel.close();
			}catch(Exception ee) {}
			
			System.out.println("Read Fail. connection closed");
		}
		
		buffer.flip();
		System.out.println("ReceiveMsg:"+new String(buffer.array()));
	}
}
