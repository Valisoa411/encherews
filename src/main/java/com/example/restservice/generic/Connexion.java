package com.example.restservice.generic;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connexion {
    
    public static Connection getConnexion(){
        Connection connect = null;
        try{
            System.out.println("getConnexion Try to connect");
            Class.forName("org.postgresql.Driver");
            // connect = DriverManager.getConnection("jdbc:postgresql://localhost:5432/enchere","enchere","enchere");
            connect = DriverManager.getConnection("jdbc:postgresql://containers-us-west-185.railway.app:5634/railway","postgres","kUUSFdZsjYUxmWXuvtOz");
            System.out.println("getConnexion Connected");
        }
        catch(Exception e){
            
        }
        return connect;
    }
}
