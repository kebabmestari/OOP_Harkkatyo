package web;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.*;

/**
 * SocketHandler
 * This class handles communation through sockets
 * @author ASUS Omistaja
 * @since 21.2.2016
 */
public class SocketHandler {
    
    //Socket used for listening clients
    ServerSocket serverSocket;
    //Socket used to communicate with remote host
    Socket socket;
    
    ExecutorService executor;
    
    SocketHandler( boolean server ){
        
        executor = Executors.newSingleThreadExecutor();
        
    }
    
    public void initServer( int port ){
        try{
            serverSocket = new ServerSocket(port);
        }catch( IOException e ){
            e.printStackTrace();
        }
    }
    
    public void listenToClients(){
        if( serverSocket == null || socket != null ) return;
        
        executor.submit( () -> {
                try{
                    socket = serverSocket.accept();
                }catch( IOException e ){
                    e.printStackTrace();
                }
        } );
        
    }
    
    public void connectToServer(){
        
    }
    
    public void getMessage(){
        
    }
    
    public void sendMessage(){
        
        
    }
    
}
