/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monnyexchange;

import java.util.ArrayList;

/**
 *
 * @author KHAN
 */
public class CustomerAcounts {
    
    public ArrayList<String> showAcounts(){
        
        ArrayList users = new ArrayList();
        Helper.Connector.dbConnection();
        
        
        
        return users;
    }
    
    
}
