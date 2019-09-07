/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monnyexchange;

/**
 *
 * @author KHAN
 */
public class User {
    private String name;
    private int tazkira;
    
    public User( int tazkira, String name){
        this.name=name;
        this.tazkira=tazkira;
    }

  

 
    
    public double getTzkir(){
        return tazkira;
    }
    public String getName(){
        return name;
    }
}
