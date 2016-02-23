package com.lindqvist.shakkipeli.web;

import java.io.Serializable;

/**
 * Beans class for storing results of moves
 *
 * @author Samuel
 */
public class Result implements Serializable {

    //Target piece of this result
    private String target;

    //Type of this action
    private ResultType type;
    
    //Move variables
    private int newX;
    private int newY;

    public Result() {
    }
    
    public Result( String target, ResultType type ){
        setTarget(target);
        setResultType(type);
        System.out.println("Created result instance of type " + type.toString() + " for " +  target);
    }
    
    public void setTarget( String target ){
        this.target = target;
    }
    
    public String getTarget(){
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
