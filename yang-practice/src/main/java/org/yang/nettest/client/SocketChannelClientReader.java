package org.yang.nettest.client;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class SocketChannelClientReader extends Thread
{
	private Selector selector;
	private SocketChannel socket;
	
	public SocketChannelClientReader(SocketChannel socket)
	{
		this.socket = socket;
	}
	
	public void run()
	{
		try
		{
			ready();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		while(true)
		{
			try
			{
				readScan();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			try{
				Thread.sleep(100);
			}catch(Exception e) {};
		}
	}
	
	private void ready() throws Exception
	{
		socket.configureBlocking(false);
		//socket.socket().setSendBufferSize(ClientConfig.SEND_BUFFER * 1024);
		//socket.socket().setReceiveBufferSize(ClientConfig.RECEIVE_BUFFER * 1024);
        selector = Selector.open();
        socket.finishConnect();
        socket.register(selector, SelectionKey.OP_READ);
	}
	
	private void readScan() throws Exception
	{
    	//selector.selectNow();
		selector.select(100);
        Iterator<SelectionKey> it = selector.selectedKeys().iterator();
        
        while(it.hasNext())
        {
            SelectionKey key = (SelectionKey)it.next();
            it.remove();
            
            if(key.isValid() && key.isReadable())
            {
                //connId = (Integer)key.attachment();
                SocketChannel channel = (SocketChannel)key.channel();
                
                try
                {
                	read(channel);
                }
                catch(Exception e)
                {
                	e.printStackTrace();
                	channel.close();
                	key.cancel();
                	
                	System.out.println("Read Fail. connection closed");
                }
            }
            else if(key.isValid() == false)
            {
            	key.cancel();
            }
        }
	}
	
	private void read(SocketChannel channel) throws Exception
	{
		ByteBuffer buffer = ByteBuffer.allocate(2048);
		channel.read(buffer);
		buffer.flip();
		
		System.out.println("receive msg:"+new String(buffer.array()));
	}
}
