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
import java.util.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Timer;

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
    
    Proposition last;
    
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
        return this.dateDebut;
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

    public Enchere(int id) {
        this.id = id;
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
        ArrayList<Enchere> liste = new ArrayList<Enchere>();
        try{
            liste = (ArrayList<Enchere>)GenericDAO.findBySql(this,requete,connect);
            for(int i=0; i<liste.size(); i++){
                System.out.println(i);
                liste.get(i).executeUpdateStatut();
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
    
    public Timestamp getFinEnchere(){
        Timestamp dt = this.getDateDebut();
        String str = dt.toString().replace(" ", "T");
        LocalDateTime date = LocalDateTime.parse(str);
        System.out.println("DEBUT: "+date);
        LocalDateTime newDate = date.plusHours((long) this.getDuree());
        System.out.println("Fin Enchere : "+newDate.toString().replace("T", " "));
        Timestamp fin = new Timestamp(newDate.getYear(),newDate.getMonthValue(),newDate.getDayOfMonth(),newDate.getHour(),newDate.getMinute(),newDate.getSecond(),newDate.getNano());
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
    
    public void executeUpdateStatut() throws Exception{
        Tache t = new Tache();
        t.setEnc(this);
        Timer timer = new Timer();
        Timestamp tm = this.getFinEnchere();
        System.out.println("FIN: "+tm);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date date = df.parse(tm.toString());
        timer.schedule(t, date);
    }
    
    public void updateStatut() throws Exception{
        Connection connect = new Connexion().getConnexion();
	    try{
            GenericDAO.update(this,connect);
        }
        finally{
            connect.close();
        }
    }

    public static ArrayList<Enchere> allNonFini() throws Exception {
        String sql = "SELECT * FROM ENCHERE WHERE STATUT=11";
        ArrayList<Enchere> list = new ArrayList<Enchere>();
        try (Connection con = new Connexion().getConnexion()) {
            list = (ArrayList<Enchere>)GenericDAO.findBySql(new Enchere(),sql,con);
        }
        catch(Exception e){
            throw e;
        }
        return list;
    }

    public static Enchere getEnchere(int id) throws Exception {
        try (Connection con = new Connexion().getConnexion()) {
            return (Enchere)GenericDAO.get(new Enchere(id),con);
        }
        catch(Exception e){
            throw e;
        }
    }
    
    public Proposition lastProp() {
        if(last != null) return last;
        try (Connection con = new Connexion().getConnexion()) {
            String sql = "SELECT * FROM PROPOSITION WHERE IDENCHERE="+id+" ORDER BY MONTANT DESC LIMIT 1";
            ArrayList<Proposition> list = (ArrayList<Proposition>) GenericDAO.findBySql(new Proposition(), sql, con);
            if(list.isEmpty()) throw new Exception("No proposition");
            last = list.get(0);
        } catch (Exception e) {
            System.out.println("Exception : "+e.getMessage());
        }
        return last;
    }
    
    public void addProposition(int idClient,double montant,Connection con) throws Exception {
        lastProp();
        if(last != null)
            if(last.getMontant()>=montant)
                throw new Exception("Not enough value");
        Proposition newLast = new Proposition(0,idClient,id,montant,new Date());
        GenericDAO.save(newLast, con);
    }

    public static ArrayList<Enchere> filtrer(String motcle,Date[] dates,String[] categories,int statut) throws Exception {
        String sql = "SELECT * FROM ENCHERE WHERE 1=1";
        if(motcle!=null && !motcle.isEmpty()) sql += " AND DESCRIPTION LIKE '%"+motcle+"%'";
        if(dates[0]!=null && dates[1]!=null) sql += " AND (DATEDEBUT BETWEEN '"+dates[0]+"' AND '"+dates[1]+"')";
        if(categories.length!=0) sql += " AND IDCATEGORIE IN ("+String.join(",",categories)+")";
        if(statut!=-1) sql += " AND STATUT="+statut;
        ArrayList<Enchere> val = new ArrayList<Enchere>();
        try (Connection con = new Connexion().getConnexion()) {
            val = (ArrayList<Enchere>) GenericDAO.findBySql(new Enchere(), sql, con);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return val;
    }
}
