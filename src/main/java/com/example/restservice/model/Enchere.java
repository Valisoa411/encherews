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
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author P14_A_111_Dina
 */
@ClassAnotation(table="Enchere")
public class Enchere {
    @Attr(isPrimary=true)
    int id;
    @Attr
    String nomProduit;
    @Attr
    String description;
    @Attr
    double prixEnchere;
    @Attr
    double duree;
    @Attr
    int statut;
    @Attr
    Timestamp dateDebut;
    @Attr
    int idClient;
    @Attr
    int idCategorie;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomProduit() {
        return nomProduit;
    }

    public void setNomProduit(String nomProduit) {
        this.nomProduit = nomProduit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrixEnchere() {
        return prixEnchere;
    }

    public void setPrixEnchere(double prixEnchere) {
        this.prixEnchere = prixEnchere;
    }

    public double getDuree() {
        return duree;
    }

    public void setDuree(double duree) {
        this.duree = duree;
    }

    public int getStatut() {
        return statut;
    }

    public void setStatut(int statut) {
        this.statut = statut;
    }

    public Timestamp getDateDebut() {
        Timestamp dt = Timestamp.valueOf(LocalDateTime.now());
        return dt;
    }

    public void setDateDebut(Timestamp dateDebut) {
        this.dateDebut = dateDebut;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public int getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(int idCategorie) {
        this.idCategorie = idCategorie;
    }

    public Enchere() {
    }

    public Enchere(String nomProduit, String description, double prixEnchere, double duree, int statut, Timestamp dateDebut, int idClient, int idCategorie) {
        this.nomProduit = nomProduit;
        this.description = description;
        this.prixEnchere = prixEnchere;
        this.duree = duree;
        this.statut = statut;
        this.dateDebut = dateDebut;
        this.idClient = idClient;
        this.idCategorie = idCategorie;
    }
    
    public String getStatutLibelle() {
        System.out.println("Statut: "+this.statut);
        if(this.statut == 1){
            return "En cours";
        }
        else if(this.statut == 5){
            return "Terminée";
        }
        else{
            return "Annulée";
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
    
    public ArrayList<Enchere> listeEnchere() throws Exception{
        Connection connect = new Connexion().getConnexion();
        String requete = "SELECT * FROM Enchere WHERE idClient = " + this.idClient;
        System.out.println(requete);
        ArrayList<Enchere> liste = new ArrayList<Enchere>();
        try{
            liste = (ArrayList<Enchere>)GenericDAO.findBySql(this,requete,connect);
        }
        catch(Exception e){
            throw e;
        }
        finally{
            connect.close();
        }
        return liste;
    }
    
    public Timestamp getFinEnchere(){
        Timestamp dt = this.getDateDebut();
        String str = dt.toString().replace(" ", "T");
        LocalDateTime date = LocalDateTime.parse(str);
        LocalDateTime newDate = date.plusHours((long) this.getDuree());
        Timestamp fin = Timestamp.valueOf(newDate.toString().replace("T", " "));
        return fin;
    }
    
    public boolean estTerminee(){
        Timestamp dt = Timestamp.valueOf(LocalDateTime.now());
        Timestamp tm = this.getFinEnchere();
        if(tm.before(dt) || tm.equals(dt)){
            return true;
        }
        return false;
    }

    public static ArrayList<Enchere> allNonFini() throws Exception {
        String sql = "SELECT * FROM ENCHERE WHERE AND STATUS=11";
        ArrayList<Enchere> list = new ArrayList<Enchere>();
        try (Connection con = new Connexion().getConnexion()) {
            list = (ArrayList<Enchere>)GenericDAO.findBySql(new Enchere(),sql,con);
        }
        catch(Exception e){
            throw e;
        }
        return list;
    }

    public Enchere getEnchere() throws Exception {
        ArrayList<Enchere> liste = new ArrayList<Enchere>();
        try (Connection con = new Connexion().getConnexion()) {
            return (Enchere)GenericDAO.get(this,con);
        }
        catch(Exception e){
            throw e;
        }
    }
}
