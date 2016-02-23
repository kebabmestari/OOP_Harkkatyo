package com.lindqvist.shakkipeli.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import com.lindqvist.shakkipeli.shakkipeli.ShakkiPeli;

/**
 * Controller of MVC architecture
 * Controls JavaFX elements and the game itself
 * @author Samuel
 */
public class RootController implements Initializable {

    private ShakkiPeli mainApp;

    public RootController(){
        System.out.println("Controller object created");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }      

    public void setMainApp( ShakkiPeli mainApp ){
        this.mainApp = mainApp;
    }
    
    @FXML
    public void closeGame(){
        mainApp.shutDown();
    }
    
    /**
     * Open help file
    */
    @FXML
    public void openHelp(){
        
    }
    
    /**
     * Open about dialog
    */
    @FXML
    public void openAbout(){
        
    }
    
    
    
}
