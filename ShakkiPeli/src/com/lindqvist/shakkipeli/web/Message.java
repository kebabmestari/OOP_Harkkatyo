package com.lindqvist.shakkipeli.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Class which encloses information about move
 * JavaBean
 * Transferred through internet
 * @author Samuel
 */
public class Message implements Serializable{
    
    //Results of a move
    private LinkedList<Result> resultset;
    
    public Message(){
        System.out.println("Message instance created");
        resultset = new LinkedList<>();
    }
    
    public void setResultset( LinkedList<Result> results ){
        resultset = results;
    }
    
    /**
     * Adds a new result to list
     * @param result The Result object
     */
    public void addResult( Result result ){
        resultset.add(result);
    }
    
    /**
     * Pop last result
     * @return 
     */
    public Result getResult(){
        
        if( resultset.size() < 1 ) return null;
        
        Result temp = resultset.get(0);
        resultset.remove(0);
        return temp;
        
    }
    
    /**
     * 
     * @return true if there are results left in list
     */
    public boolean isLeft(){
        return (resultset.size() > 0);
    }
    
    public LinkedList<Result> getResultset(){
        return resultset;
    }
    
}
