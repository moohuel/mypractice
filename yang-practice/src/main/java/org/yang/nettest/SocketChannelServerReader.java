package org.yang.nettest;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class SocketChannelServerReader extends Thread
{
	private Selector selector;
	private SocketChannel socket;
	
	public SocketChannelServerReader(SocketChannel socket)
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
                	Thread.sleep(5000);
                	reply(channel);
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
	
	private void reply(SocketChannel channel)
	{
		String msg = "Reply Hello";
		
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
