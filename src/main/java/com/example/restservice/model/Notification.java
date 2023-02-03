/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.restservice.model;

import com.example.restservice.generic.Attr;
import com.example.restservice.generic.ClassAnotation;
import com.example.restservice.generic.Connexion;
import com.example.restservice.generic.GenericDAO;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author P14_A_111_Dina
 */
@ClassAnotation(table="Notification")
public class Notification {
    @Attr(isPrimary=true)
    int id;
    @Attr
    Timestamp date;
    @Attr
    int idClient;
    @Attr
    String message;

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    public boolean ifExist() throws Exception{
        Connection connect = new Connexion().getConnexion();
        String requete = "SELECT * FROM Notification where message = '"+this.message+"'";
        ArrayList<Notification> liste = new ArrayList<Notification>();
        boolean b = false;
        try{
            liste = (ArrayList<Notification>)GenericDAO.findBySql(this,requete,connect);
            if(liste.size() > 0){
                b = true;
            }
            
        }
        catch(Exception e){
            throw e;
        }
        finally{
            connect.close();
        }
        return b;
    }
    
    public void insert() throws Exception{
        Connection connect = new Connexion().getConnexion();
	try{
            if(this.ifExist() == false){
                GenericDAO.save(this,connect);
            }            
        }
        catch(Exception e){
            throw e;
        }
        finally{
            connect.close();
        }
    }
    
    public ArrayList<Enchere> liste_enchere() throws Exception{
        Enchere e = new Enchere();
        e.setIdClient(this.idClient);
        ArrayList<Enchere> liste = e.listeEnchere();
        return liste;
    }

    public ArrayList<Notification> listeNotification() throws Exception{
        Connection connect = new Connexion().getConnexion();
        String requete = "SELECT * FROM Notification where idClient = "+this.idClient +" order by date desc";
        ArrayList<Notification> liste = new ArrayList<Notification>();
        try{
            liste = (ArrayList<Notification>)GenericDAO.findBySql(this,requete,connect);
            ArrayList<Enchere> enc = this.liste_enchere();
            for(int i=0; i<enc.size(); i++){
                System.out.println(i);
                enc.get(i).executeUpdateStatut();
            }
        }
        catch(Exception e){
            throw e;
        }
        finally{
            connect.close();
        }
        return liste;
    }
}
