package com.lindqvist.shakkipeli.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import com.lindqvist.shakkipeli.shakkipeli.Board;
import com.lindqvist.shakkipeli.shakkipeli.ShakkiPeli;
import javafx.animation.FadeTransition;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;

/**
 * Controller of MVC architecture
 * Controls JavaFX elements and the game itself
 * @author Samuel
 */
public class MainController implements Initializable {

    @FXML
    private GridPane chessField;
    @FXML
    private TextArea logField;    
    @FXML
    private TextField connectHost;
    @FXML
    private TextField serverPort;
    @FXML
    private AnchorPane gameView;
    @FXML
    private Button hostButton;
    @FXML
    private Button joinButton;
    @FXML
    private Label gameLabel;
    
    private ShakkiPeli mainApp;
    
    
    
    public MainController(){
        System.out.println("Controller object created");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Board.createBoard(gameView);
        
        gameLabel.toFront();
        
    }      
    
    /**
     * Write log into log controller
     * @param str 
     */
    public void writeLog(String str){
        logField.setText(logField.getText() + "\n" + str);
        logField.positionCaret(logField.lengthProperty().get());
    }
    
    public void setMainApp( ShakkiPeli mainApp ){
        this.mainApp = mainApp;
    }
    
    /**
     * Open game hosting dialog
    */
    @FXML
    public void hostGame(){
        mainApp.setServer(Integer.parseInt(serverPort.getText()));
    }
    
    /**
     * Open join game dialog
    */
    @FXML
    public void joinGame(){
        mainApp.joinGame(connectHost.getText());
    }
    
    public void disableConnect(){
        connectHost.setDisable(true);
        serverPort.setDisable(true);
        joinButton.setDisable(true);
        hostButton.setDisable(true);
    }
    
    public void setLabel(String str){
        
        gameLabel.setOpacity(1);
        gameLabel.setText(str);
        
        //Fade out
        FadeTransition tr = new FadeTransition(Duration.seconds(1));
        tr.setDelay(Duration.seconds(2));
        tr.setToValue(0);
        tr.setNode(gameLabel);
        tr.play();
        
    }
    
    
    
}
