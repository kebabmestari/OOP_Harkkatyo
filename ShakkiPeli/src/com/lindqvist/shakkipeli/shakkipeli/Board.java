package com.lindqvist.shakkipeli.shakkipeli;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;

/**
 *
 * @author Samuel
 */
public class Board {
    //Coordinates
    private int x;
    private int y;
    
    public static final int w = 60;
    public static final int h = 60;
    
    //Reference to the element
    private Pane element;
    
    public Board(int x, int y){
        this.x = x;
        this.y = y;
        element = new Pane();
        element.setPrefSize(w, h);
        attachEvents();
    }
    
    public Pane getElement(){
        return element;
    }
    
    private void attachEvents(){
        element.setOnMouseClicked( (e)->{System.out.println("Clicked at " + x + " " + y); });
    }
    
    /**
     * Creates the game board dynamically to the container and attaches events etc
     * @param parent The parent AnchorPane
     */
    public static void createBoard(AnchorPane parent){
        
        //Create board dynamically
        TilePane board = new TilePane(0, 0);
        board.setPrefColumns(8);
        board.setPrefRows(8);
        
        parent.getChildren().add(board);

        int i = 0;
        
        for( int y = 0; y < 8; y++){
            for( int x = 0; x < 8; x++ ){
                
                Board boardPiece = new Board(x, y);

                if( (i % 2) == 0 )
                    boardPiece.getElement().setStyle("-fx-background-color: black;");
                else
                    boardPiece.getElement().setStyle("-fx-background-color: white");
                
                board.getChildren().add(boardPiece.getElement());
                
                i++;
            }
            i++;
        }
        
        System.out.println("Game board created");
        
    }
    
}
