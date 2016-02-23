package com.lindqvist.shakkipeli.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.*;
import java.util.concurrent.*;
import javafx.collections.ObservableList;

/**
 * SocketHandler
 * This class handles communication through sockets
 * @author ASUS Omistaja
 * @since 21.2.2016
 */
public class SocketHandler {
    
    //Socket used for listening clients
    ServerSocket serverSocket;
    //Socket used to communicate with remote host
    Socket socket;
    
    //Streams
    InputStream in;
    OutputStream out;
    
    BufferedInputStream buf_in;
    BufferedOutputStream buf_out;
    
    //Streams for sending objects
    ObjectOutputStream obj_out;
    ObjectInputStream obj_in;
    
    //Thread pool executor
    ExecutorService executor;
    
    //Target into which messages will be pushed
    volatile ObservableList<Message> target;
    
    //Return whether socket is open
    public boolean isConnected(){
        return (socket != null);
    }
    
    public SocketHandler( boolean server, ObservableList<Message> target ){
        
        //Set target
        this.target = target;
        
        //Create thread pool
        executor = Executors.newFixedThreadPool(2);
        
    }
    
    /**
     * Initialize host
     * @param port Server port
     */
    public void initServer( int port ){
        System.out.println("Starting serversocket...");
        try{
            serverSocket = new ServerSocket(port);
            System.out.println("Server succesfully initialized");
        }catch( IOException e ){
            System.err.println("Server couldn't be initialized, check port");
            e.printStackTrace();
        }
    }
    
    /**
     * Listen to clients as server
     */
    public void listenToClients(){
        if( serverSocket == null || socket != null ) return;
        
        executor.submit( () -> {
                try{
                    
                    socket = serverSocket.accept();
                    in       = socket.getInputStream();
                    out      = socket.getOutputStream();
                    buf_in   = new BufferedInputStream( in );
                    buf_out  = new BufferedOutputStream( out );
                    obj_in   = new ObjectInputStream( buf_in );
                    obj_out  = new ObjectOutputStream( buf_out );
                    
                    System.out.println("Client " + socket.getInetAddress() + "connected");
                }catch( IOException e ){
                    e.printStackTrace();
                }
        } );
        
    }
    
    /**
     * Connect to remote host through TCP
     * @param host Server hostname
     * @param port Server port
     */
    public void connectToServer(String host, int port){
        
        if( serverSocket != null || socket != null ) return;
        
        try{
            
           socket = new Socket( host, port );
           in       = socket.getInputStream();
           out      = socket.getOutputStream();
           buf_in   = new BufferedInputStream( in );
           buf_out  = new BufferedOutputStream( out );
           obj_in   = new ObjectInputStream( buf_in );
           obj_out  = new ObjectOutputStream( buf_out );
           
           System.out.println("Connected to "  +
                   socket.getInetAddress()+":" +
                   socket.getPort() );
           
        }catch( UnknownHostException e ){
            System.err.println("Host not found");
        }catch( IOException e ){
            e.printStackTrace();
        }
        
    }
    
    /**
     * Listen to messages through socket
     */
    public void listenMessages(){
        
        if( socket != null && buf_in != null ){
            executor.submit( () -> {
                while(true){
                    try{
                        Message temp;
                        while( (temp = (Message)obj_in.readObject()) != null ){
                            target.add(temp);
                            System.out.println("Message received");
                        }
                    }catch( IOException e ){
                        e.printStackTrace();
                    }catch( ClassNotFoundException e ){
                        System.err.println("Invalid class in communication");
                        e.printStackTrace();
                    }
                }
            });
        }
        
    }
    
    /**
     * Send TCP message through socket
     * @param message Message to be sent to other player
     */
    public void sendMessage( String message ){
        
        if( socket == null || buf_out == null ) return;
        if( pri_out == null ) pri_out = new PrintWriter( buf_out );
        
        pri_out.println(message);
        pri_out.flush();
        
        System.out.println("Message sent");
        
    }
    
    @Override
    protected void finalize() throws Throwable{
        
        super.finalize();
        
        if( buf_in != null )    buf_in.close();
        if( buf_out != null )   buf_out.close();
        
        //Close executor
        try {
            executor.shutdown();
            executor.awaitTermination(5, TimeUnit.SECONDS);
        }
        finally {
            executor.shutdownNow();
        }
    }
    
}
