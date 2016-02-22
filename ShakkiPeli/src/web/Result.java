package web;

import java.io.Serializable;
import shakkipeli.Piece;

/**
 * Beans class for storing results of moves
 *
 * @author Samuel
 */
public class Result implements Serializable {

    //Target piece of this result
    private Piece target;

    //Type of this action
    private ResultType type;
    
    //Move variables
    private int newX;
    private int newY;

    public Result() {
    }
    
    public Result( Piece target, ResultType type ){
        setTargetPiece(target);
        setResultType(type);
    }
    
    public void setTargetPiece( Piece target ){
        this.target = target;
    }
    
    public Piece getTargetPiece(){
        return target;
    }
    

    public void setResultType(ResultType type){
        this.type = type;
        System.out.println("Result of type " + type.toString() + " created" );
    }
    
    public void setnewX( int newX ){
        this.newX = newX;
    }
    
    public void setnewY( int newY ){
        this.newY = newY;
    }
    
    public void setNewPos( int x, int y ){
        setnewX(x);
        setnewY(y);
    }
    
}
