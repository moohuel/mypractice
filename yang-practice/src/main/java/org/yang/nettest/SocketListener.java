package org.yang.nettest;

import java.net.BindException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class SocketListener extends Thread
{
	private int listenPort = 0;
	private ServerSocketChannel serverSocket;
	private Selector selector;
	
	private SocketManager socketManager;
	
	public SocketListener(int listenPort)
	{
		this.listenPort = listenPort;
	}
	
	public void run()
	{
		ready();
		
		while(true)
		{
			try
			{
				accept();	
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			try{
				Thread.sleep(100);
			}catch(Exception e) {}
		}
	}
	
	private void ready()
	{
        try 
        {
            
            InetSocketAddress socketAddress = new InetSocketAddress(listenPort);
            
            serverSocket = ServerSocketChannel.open();
            serverSocket.socket().bind(socketAddress);
            serverSocket.configureBlocking(false);
            selector = Selector.open();
            serverSocket.register(selector, SelectionKey.OP_ACCEPT);
            
            System.out.println("Listen .."+listenPort);
        }
        catch(BindException eb)
        {
            eb.printStackTrace();
            System.exit(0);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.exit(0);
        }
	}
	
	private void accept() throws Exception
	{
        //selector.select(100);
    	selector.selectNow();
        Iterator<SelectionKey> it = selector.selectedKeys().iterator();
        
        while(it.hasNext())
        {
            SelectionKey key = it.next();
            
            try
            {
                if(key.isValid() && key.isAcceptable())
                {
                    ServerSocketChannel channel = (ServerSocketChannel)key.channel();
                    SocketChannel ch = channel.accept();
                    
                    int count = 0;
                    while(ch.finishConnect() == false && count < 3)
                    {
                    	System.out.println("wait finshing...");
                    	count++;
                    	Thread.sleep(10);
                    }
                    
                    System.out.println("accepting...");
                    
                    socketManager.addSocket(ch);
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            
            it.remove();
        }
	}
	
	public void setSocketManager(SocketManager socketManager)
	{
		this.socketManager = socketManager;
	}
}
