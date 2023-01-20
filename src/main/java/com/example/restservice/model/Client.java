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
import com.example.restservice.generic.Utilitaire;
import java.sql.Connection;
import java.util.ArrayList;

/**
 *
 * @author P14_A_111_Dina
 */
@ClassAnotation(table="Client")
public class Client {
    @Attr(isPrimary=true)
    int id;
    @Attr
    String nom;
    @Attr
    String email;
    @Attr
    String password;
    @Attr
    double solde;

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        Utilitaire u = new Utilitaire();
        this.password = u.encrypt(password);
    }

    public Client(String nom, String email, String password) {
        this.nom = nom;
        this.email = email;
        this.password = password;
    }

    public Client() {
    }

    public Client(int id) {
        this.id = id;
    }
    
    public static Client getClient(int id) throws Exception {
        try (Connection con = new Connexion().getConnexion()) {
            return (Client)GenericDAO.get(new Client(id), con);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void insert() throws Exception{
        Connection connect = new Connexion().getConnexion();
	try{
            GenericDAO.save(this,connect);
        }
        catch(Exception e){
            throw e;
        }
        finally{
            connect.close();
        }
    }

    public boolean checkSolde(double montant){
        return montant<=solde;
    }

    public void blockSolde(double montant,Connection con) throws Exception {
        solde -= montant;
        GenericDAO.update(this,con);
    }
    
    public void unblockSolde(Proposition prop,Connection con) throws Exception {
        solde += prop.getMontant();
        GenericDAO.update(this, con);
    }
}
