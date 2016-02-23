/**
 * Shakkipeli
 * OOP Harjoitustyö
 * Samuel Lindqvist
 * Started. 21.2.2016
*/
package com.lindqvist.shakkipeli.shakkipeli;

import com.lindqvist.shakkipeli.controller.MainController;
import com.lindqvist.shakkipeli.controller.RootController;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import com.lindqvist.shakkipeli.web.SocketHandler;

/**
 * The main class
 * @author ASUS Omistaja
 */
public class ShakkiPeli extends Application {
    
    //True if this end is server, false otherwise
    private boolean isServer;
    //True if it's this client's turn
    private boolean myTurn;
    
    //Stages and scenes
    private Stage primaryStage;
    private Scene mainScene;
    
    //Root node
    private BorderPane root;
    
    //Controller
    private RootController rcontroller;
    private MainController controller;
    
    //For executing timed tasks
    private ScheduledExecutorService executor_sce;
    private ExecutorService executor;
    
    private SocketHandler socketHandler;
    
    //Streams for output and error
    volatile private BufferedReader reader   = null;
    volatile private BufferedReader reader2  = null;
    volatile private PipedOutputStream pOut  = null;
    volatile private PipedOutputStream pOut2 = null;
    
    /**
     * Shut down JavaFX stage and Java app
     */
    public void shutDown(){
        if(executor != null)        executor.shutdownNow();
        if(executor_sce != null)    executor_sce.shutdownNow();
        Platform.exit();
        System.exit(0);
    }
    
    /**
     * Direct system output to log window
     */
    private void initOutput(){
        
        //Set system output to log view
        try{
            pOut = new PipedOutputStream();
            System.setOut(new PrintStream(pOut));
            System.setErr(new PrintStream(pOut));
            PipedInputStream pIn = new PipedInputStream(pOut);
            reader = new BufferedReader(new InputStreamReader(pIn));
        }catch( IOException e ){
            System.err.println("Couldn't redirect outputs");
        }
        
        //Start thread to listen for outputs
        executor.submit( ()->{
            try{
                String tempstr = null;
                while(true){
                    tempstr = reader.readLine();
                    if( tempstr != null ){
                        controller.writeLog(tempstr);
                    }
                    tempstr = null;
                }
            }catch( IOException e ){
                e.printStackTrace();
            }
        });
        
        System.out.println("Logger initialized");
        
    }
    
    /**
     * Join as a client
     * @param connectString String which includes host and port
     */
    public void joinGame(String connectString){
        socketHandler = new SocketHandler(false, null);
        
        Pattern r = Pattern.compile("([a-z1-9]+):([1-9]+)");
        Matcher m = r.matcher( connectString );
        
        String host = null;
        int port = 2000;
        
        if( m.find() ){
            host = m.group(1);
            port = Integer.parseInt(m.group(2));
            if( host.length() > 0 ){
                System.out.println("Attempting connection to " + host + ":" + port);
                socketHandler.connectToServer(host, port);
                if( socketHandler.isConnected() )
                    System.out.println("Connection established");
                else
                    System.err.println("Failed to connect");
            }
        }
            
        isServer = false;
    }
    
    /**
     * Set up as host
     * @param port Port to listen
     */
    public void setServer(int port){
        
        System.out.println("Setting up server at " + port);
        
        socketHandler = new SocketHandler(true, null );
        
        socketHandler.initServer(port);
        socketHandler.listenToClients();
        
        isServer = true;
    }
    
    @Override
    public void start(Stage primaryStage) {
        
        this.primaryStage = primaryStage;
        
        //New threadpool
        executor = Executors.newFixedThreadPool(2);
        
        //Load FXML layouts and controllers
        try{
            
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation( ShakkiPeli.class.getClassLoader().getResource("view/root.fxml") );
            root = (BorderPane)loader.load();
            rcontroller = (RootController)loader.getController();
            rcontroller.setMainApp(this);
            
            loader = new FXMLLoader();
            loader.setLocation( ShakkiPeli.class.getClassLoader().getResource("view/main.fxml") );
            root.setCenter( (AnchorPane)loader.load() );
            controller = (MainController)loader.getController();
            controller.setMainApp(this);
            
            
        }catch( IOException e ){
            System.err.println("Couldn't fetch FXML");
            e.printStackTrace();
            shutDown();
        }
        
        //Initialize logger
        //initOutput();
        
        //Create main scene from root node
        mainScene = new Scene(root);
        
        
        //Set scene and make it visible
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Java Chess for OOP");
        
        primaryStage.setOnCloseRequest( (e) -> {shutDown();});
        primaryStage.setMinWidth(primaryStage.getWidth());
        primaryStage.setMinHeight(primaryStage.getHeight());
        
        primaryStage.show();
        
    }

    /**
     * Lauches JavaFX application
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
