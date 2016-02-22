package web;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class which encloses information about move
 * JavaBean
 * Transferred through internet
 * @author Samuel
 */
public class Move implements Serializable{
    
    //Results of a move
    private ArrayList<Result> resultset;
    
    public Move(){
        System.out.println("Move instance created");
        resultset = new ArrayList<Result>();
    }
    
    public void setResultset( ArrayList<Result> results ){
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
    
    public ArrayList<Result> getResultset(){
        return resultset;
    }
    
}
