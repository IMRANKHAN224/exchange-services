/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author KHAN
 */
public class Connector {
    public static String URL="jdbc:mysql://localhost/monyexchange";
    public static String UERNAME="root";
    public static String PASS="khan";
    public static Connection con;
    
    public static Connection dbConnection(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
             con = DriverManager.getConnection( URL,UERNAME,PASS);
          // JOptionPane.showMessageDialog(null,"db connectect");
             return con;
            
        }catch(Exception e){
            e.printStackTrace();
        }
       return null;
    }

   public static String isert( String sql) throws SQLException{
       dbConnection();
       Statement stmt = con.createStatement();
       ResultSet  rs = stmt.executeQuery(sql);
       rs.next();
       if(rs.getRow()==1){
           
       }
       
return sql;
   }
    
}
