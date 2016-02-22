package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import shakkipeli.Board;

import shakkipeli.ShakkiPeli;

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
    
    private ShakkiPeli mainApp;
    
    
    
    public MainController(){
        System.out.println("Controller object created");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Board.createBoard(gameView);
        
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
    
    
    
}
