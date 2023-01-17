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
import java.util.ArrayList;

/**
 *
 * @author P14_A_111_Dina
 */
@ClassAnotation(table="Parametre")
public class Parametre {
    @Attr(isPrimary=true)
    int id;
    @Attr
    String libelle;
    @Attr
    double valeur;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public double getValeur() {
        return valeur;
    }

    public void setValeur(double valeur) {
        this.valeur = valeur;
    }
    
    public Parametre(){
        
    }
    
    public Parametre(String libelle){
        this.libelle = libelle;
    }
    
    public Parametre getParametreValue() throws Exception{
        Connection connect = new Connexion().getConnexion();
        String requete = "SELECT * FROM Parametre WHERE libelle = '" + this.libelle + "'";
        ArrayList<Parametre> liste = new ArrayList<Parametre>();
        try{
            liste = (ArrayList<Parametre>)GenericDAO.findBySql(this,requete,connect);
        }
        catch(Exception e){
            throw e;
        }
        finally{
            connect.close();
        }
        return liste.get(0);
    }
}
