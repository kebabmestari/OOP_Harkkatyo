package com.lindqvist.shakkipeli.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.*;
import java.util.concurrent.*;
import javafx.beans.value.ObservableBooleanValue;
import javafx.collections.ObservableList;
import javafx.util.Duration;

/**
 * SocketHandler
 * This class handles communication through sockets
 * @author ASUS Omistaja
 * @since 21.2.2016
 */
public class SocketHandler {
    
    //Socket used for listening clients
    private volatile ServerSocket serverSocket;
    //Socket used to communicate with remote host
    private volatile Socket socket;
    
    //Streams
    private InputStream in;
    private OutputStream out;
    
    ObservableBooleanValue isConnected;
    
    private BufferedInputStream buf_in;
    private BufferedOutputStream buf_out;
    
    //Streams for sending objects
    private ObjectOutputStream obj_out;
    private ObjectInputStream obj_in;
    
    //Thread pool executor
    private ExecutorService executor;
    
    //Target into which messages will be pushed
    private volatile ObservableList<Message> target;
    
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
                    
                    System.out.println("Listening to clients");
                    
                    socket = serverSocket.accept();
                    out      = socket.getOutputStream();
                    in       = socket.getInputStream();
                    obj_out  = new ObjectOutputStream( out );
                    obj_out.flush();
                    obj_in   = new ObjectInputStream( in );
                    
                    System.out.println("Client " + socket.getInetAddress() + "connected");
                    
                    listenMessages();
                    
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
            
           System.out.println("Attempting connection to " + host + ":" + port);
            
           socket = new Socket( host, port );
           
           System.out.println("Socket opened");
           
           in       = socket.getInputStream();
           out      = socket.getOutputStream();
           obj_out  = new ObjectOutputStream(out);
           obj_out.flush();
           obj_in   = new ObjectInputStream(in);
           
           System.out.println("Connected to "  +
                   socket.getInetAddress()+":" +
                   socket.getPort() );
        
           listenMessages();
           
           try{
            Thread.sleep(1000);
        }catch(Exception e){}
           
           //Send initial message
           Message msg = new Message();
           msg.setMessage("Hello partner");
           sendMessage(msg);
           
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

        if( socket != null && obj_in != null ){
            System.out.println("asd");
            executor.submit( () -> {
                while(true){
                    try{
                        Message temp;
                        System.out.println("aa");
                        temp = (Message)obj_in.readObject();
                            target.add(temp);
                            if( temp.getMessage() != null )
                                System.out.println("Network message received: " + temp.getMessage());
                            else
                                System.out.println("Network message received");
                        System.out.println("asdddd");
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
    public void sendMessage( Message message ){
        
        if( socket == null || obj_out == null ) return;
        
        try{
           obj_out.writeObject(message);
           obj_out.flush();
           System.out.println("Message sent");
        }catch(Exception e){
            System.err.println("Error sending message");
            e.printStackTrace();
        }
        
    }

    /**
     * Shut down everything related to socket
     */
    public void closeConnection(){
        try{
            if( socket != null)         socket.close();
            if( serverSocket != null )  serverSocket.close();
            if( obj_in != null )        obj_in.close();
            if( obj_out != null )       obj_out.close();
            if( buf_in != null )        buf_in.close();
            if( buf_out != null )       buf_out.close();
            if( in != null )            in.close();
            if( out != null )           out.close();
        }catch(IOException e){
            System.err.println("Error while closing socket");
            e.printStackTrace();
        }
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
