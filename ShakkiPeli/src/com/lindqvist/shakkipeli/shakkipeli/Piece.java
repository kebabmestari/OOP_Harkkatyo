/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lindqvist.shakkipeli.shakkipeli;

import com.lindqvist.shakkipeli.shakkipeli.exceptions.InvalidCoordinates;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Class for each of the game pieces, their status, methods etc
 * @author Samuel
 */
public class Piece {
    
    //Type of the piece
    PieceType type;
    
    /*
        Piece ID, consists of PIECETYPE+PLAYER+NUMBER
        eg. 
    */
    String pieceID;
    
    //Owner of this piece, 0 is server 1 is client
    private int owner;
    
    //ID to differentiate between same pieces
    private int id;
    
    private int x;
    private int y;
    
    public Piece( PieceType type, int x, int y, int owner ) throws InvalidCoordinates{
        
        System.out.println("Creating new gamepiece");
        
        if( x < 0 || x > 7 || y < 0 || y > 7 )
            throw new InvalidCoordinates();
        
        if( owner < 0 || owner > 1 )
            System.err.println("Invalid owner");
        this.owner = owner;
        
        pieceID = this.type.toString() + owner + getCount(this.owner, this.type);
        
        this.type = type;
        
        System.out.println("Piece " + pieceID + " created at " + x + "," + y);
        
    }
    
    /**
     * Calls the rendering methods
     */
    public void draw(){
        
    }
    
    /**
     * Generate all the pieces needed for the game
     * 
     */
    public static void createPieces(){
        
    }
    
    public int getOwner(){
        return owner;
    }
    
    public PieceType getType(){
        return type;
    }
    
    public int getID(){
        return id;
    }
    
    public static LinkedList<Piece> listofPieces;
    
    public static int getCount(int owner, PieceType type){
        
        int result = 0;
        
        ListIterator<Piece> it = listofPieces.listIterator();
        while( it.hasNext() ){
            Piece temp = null;
            if( (temp = (Piece)it.next()) != null ){
                if((temp.getOwner() == owner) && (temp.getType() == type))
                    result++;
            }
        }
        
        return result;
    }
            
    static{
        listofPieces = new LinkedList<>();
    }
    
}
