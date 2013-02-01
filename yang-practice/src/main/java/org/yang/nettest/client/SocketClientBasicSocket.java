package org.yang.nettest.client;

import java.net.Socket;

public class SocketClientBasicSocket
{
	private Socket socket;
	
	public SocketClientBasicSocket()
	{
	}

	public void connect(String ip, int port, boolean isBlocking)
	{
		try
		{
			socket = new Socket(ip, port);
			socket.setSoTimeout(2000); //works well!. This is reading timeout when blocking state
			//socket.setSoLinger(true, 10000);
//			socket.setSendBufferSize(1024);
//			socket.setReceiveBufferSize(1024);
//			socket.setSoLinger(false, 0);
//			socket.setTcpNoDelay(true);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void send(String msg)
	{
		try
		{
			socket.getOutputStream().write(msg.getBytes());
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("SendFail. connection closed");
		}
	}
	
	public void read()
	{
		try
		{
			byte[] b = new byte[2048];
			int readSize = socket.getInputStream().read(b);
			
			if(readSize <= 0)
			{
				System.out.println("ReadSize="+readSize);
			}
			
			System.out.println("ReceiveMsg:"+new String(b));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Read Fail. connection closed");
		}
	}
	
	public void close()
	{
		try{
			socket.close();
		}catch(Exception e) {}
	}
	
	public void closeOutput()
	{
		try{
			socket.shutdownOutput();
		}catch(Exception e) {}
	}
	
	public void closeInput()
	{
		try{
			socket.shutdownInput();
		}catch(Exception e) {}
	}
}
