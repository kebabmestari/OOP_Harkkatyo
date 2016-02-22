/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shakkipeli;

import shakkipeli.exceptions.InvalidCoordinates;

/**
 * Class for each of the game pieces, their status, methods etc
 * @author Samuel
 */
public class Piece {
    
    //Type of the piece
    PieceType type;
    
    boolean valid;
    
    public Piece( PieceType type, int x, int y ) throws InvalidCoordinates{
        
        if( x < 0 || x > 7 || y < 0 || y > 7 )
            throw new InvalidCoordinates();
        
        this.type = type;
        
        System.out.println("Piece " + type.toString() + " created at " + x + "," + y);
        
    }
    
}
