package org.yang.nettest.client;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class SocketClientConnector
{
	public SocketChannel connect(String ip, int port, boolean isBlocking)
	{
		return connectPhysical(ip, port, isBlocking);
	}
	
	private SocketChannel connectPhysical(String ip, int port, boolean isBlocking)
	{
		SocketChannel socketChannel = null;
        Selector selector = null;
        
        try
        {
            socketChannel = SocketChannel.open();
//            socketChannel.socket().setReceiveBufferSize(ServerConfig.AGENT_RECEIVE_BUFFER_SIZE * 1024);
//            socketChannel.socket().setSendBufferSize(ServerConfig.AGENT_SEND_BUFFER_SIZE * 1024);
//            socketChannel.socket().setSoTimeout(timeoutMillisecond);
            socketChannel.socket().setTcpNoDelay(true);
            socketChannel.socket().setSoLinger(true, 0);
            
            InetSocketAddress inet = new InetSocketAddress(ip, port);
            socketChannel.configureBlocking(isBlocking);
            socketChannel.connect(inet);
            
            selector = Selector.open();
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
            
            int count = 0;
            while(count < 5000/1000)
            {
            	selector.select(100);
            	count++;
            	
	            Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
	            while(iter.hasNext())
	            {
		            SelectionKey key = (SelectionKey)iter.next();
		            iter.remove();
		            
		            if(key.isValid() && key.isConnectable())
		            {
		            	socketChannel.finishConnect();
		            	count = Integer.MAX_VALUE;	//for break
		            	break;
		            }
	            }
            }
            
            try{
            	selector.close();
            }catch(Exception e) { }
            
            if(socketChannel.isConnected() == true)
            {
            	System.out.println("conn success");
            }
            else
            {
            	System.out.println("conn fail");
            }
            
            return socketChannel;
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        	
            try{
            	if(socketChannel != null)
            		socketChannel.close();
            }catch(Exception ee) { }  
            
            try{
            	if(selector != null)
            		selector.close();
            }catch(Exception ee) {}
        }
        
        System.out.println("conn fail.. return null");
        return null;
	}
}
