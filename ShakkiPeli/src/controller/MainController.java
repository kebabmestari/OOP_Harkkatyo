package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.TilePane;

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
        
        //Create board dynamically
        TilePane board = new TilePane(0, 0);
        board.setPrefColumns(8);
        board.setPrefRows(8);
        
        gameView.getChildren().add(board);

        int i = 0;
        
        for( int y = 0; y < 8; y++){
            for( int x = 0; x < 8; x++ ){
                Pane ruutu = new Pane();

                ruutu.setPrefSize((int)(gameView.getPrefWidth()/8), (int)(gameView.getPrefHeight()/8));

                if( (i % 2) == 0 )
                    ruutu.setStyle("-fx-background-color: black;");
                else
                    ruutu.setStyle("-fx-background-color: white");
                board.getChildren().add(ruutu);
                i++;
            }
            i++;
        }
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
