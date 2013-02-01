package org.yang.nettest;

public class ServerMain
{
	public static void main(String[] args)
	{
		SocketManager socketManager = new SocketManager();
		
		SocketListener listener = new SocketListener(9090);
		listener.setSocketManager(socketManager);
		
		listener.start();
	}
}
