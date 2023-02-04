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
import java.util.List;
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
    ArrayList<EnchereImage> images;
    
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

    public Proposition getLast() {
        return last;
    }

    public void setLast(Proposition last) {
        this.last = last;
    }

    public ArrayList<EnchereImage> getImages() {
        return images;
    }

    public void setImages(ArrayList<EnchereImage> images) {
        this.images = images;
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
    
    public int insert() throws Exception{
        Connection connect = new Connexion().getConnexion();
	try{
            connect.setAutoCommit(false);
            GenericDAO.save(this,connect);
            String sqlGetLastRecordID = "SELECT * FROM enchere ORDER BY id DESC LIMIT 1";
            List<Enchere> listEnchere = GenericDAO.findBySql(this, sqlGetLastRecordID, connect);
            connect.commit();
            if(listEnchere.isEmpty())throw new Exception("There is no enchere recorded in the database");
            return listEnchere.get(0).getId();
            
        }
        catch(Exception e){
            connect.rollback();
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
        System.out.println("Year: "+newDate.getYear());
//        Timestamp fin = new Timestamp(newDate.getYear(),newDate.getMonthValue(),newDate.getDayOfMonth(),newDate.getHour(),newDate.getMinute(),newDate.getSecond(),newDate.getNano());
        Timestamp fin = Timestamp.valueOf(newDate);
        System.out.println("Timestamp "+fin);
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
//        DateFormat df2 = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss a");
        java.util.Date date = df.parse(tm.toString());
//        java.util.Date date2 = df2.parse(tm.toString());
        timer.schedule(t, date);
//        timer.schedule(t, date2);
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

    // public static ArrayList<Enchere> filtrer(String motcle,Date[] dates,String[] categories,int statut) throws Exception {
    //     String sql = "SELECT * FROM ENCHERE WHERE 1=1";
    //     if(motcle!=null && !motcle.isEmpty()) sql += " AND DESCRIPTION LIKE '%"+motcle+"%'";
    //     if(dates[0]!=null && dates[1]!=null) sql += " AND (DATEDEBUT BETWEEN '"+dates[0]+"' AND '"+dates[1]+"')";
    //     if(categories.length!=0) sql += " AND IDCATEGORIE IN ("+String.join(",",categories)+")";
    //     if(statut!=-1) sql += " AND STATUT="+statut;
    //     ArrayList<Enchere> val = new ArrayList<Enchere>();
    //     try (Connection con = new Connexion().getConnexion()) {
    //         val = (ArrayList<Enchere>) GenericDAO.findBySql(new Enchere(), sql, con);
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         throw e;
    //     }
    //     return val;
    // }

    // public void sendNotification(String token, String title, String message) throws FirebaseMessagingException {
    //     Notification notif = Notification.builder()
    //         .setTitle("Titre de la notification")
    //         .setBody("Corps de la notification")
    //         .build();
        
    //     Message notification = Message.builder()
    //         .setToken(token)
    //         .setNotification(notif)
    //         .build();

    //     String response = FirebaseMessaging.getInstance().send(notification);
    //     System.out.println("Notification sent successfully: " + response);
    // }
    
    public ArrayList<Enchere> searchEnchere(String mot) throws Exception{
        Connection connect = new Connexion().getConnexion();
        String requete = "SELECT * FROM Enchere WHERE idClient = " + this.idClient + " and (nomProduit like '%"+mot+"%' or description like '%"+mot+"%')";
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

    public static ArrayList<V_enchere> filtrer(String motcle,Date[] dates,String[] categories,int statut) throws Exception {
        String sql = "SELECT * FROM ENCHERE WHERE 1=1";
        System.out.println("111 : "+sql);
        if(motcle!=null && !motcle.isEmpty()) sql += " AND DESCRIPTION LIKE '%"+motcle+"%'";
        if(dates[0]!=null && dates[1]!=null) sql += " AND (DATEDEBUT BETWEEN '"+dates[0]+"' AND '"+dates[1]+"')";
        if(categories.length!=0) sql += " AND IDCATEGORIE IN ("+String.join(",",categories)+")";
        if(statut!=-1) sql += " AND STATUT="+statut;
        ArrayList<Enchere> val = new ArrayList<Enchere>();
        try (Connection con = new Connexion().getConnexion()) {
            val = (ArrayList<Enchere>) GenericDAO.findBySql(new Enchere(), sql, con);
            
            String cond = "(";
            
            if(val.isEmpty() || val==null)cond = cond+")";
            else if(val.size()==1) cond = cond+val.get(0).getId()+")";
            else{
                int i=0;
                cond = cond+val.get(0).getId();
                for (Enchere enchere : val) {
                    if(i==0){
                        i++;
                        continue;
                    }
                    String idTemp = String.valueOf(enchere.getId());
                    cond = cond+","+idTemp;
                }
                cond = cond+")";
            }
            Connection connection = Connexion.getConnexion(); //con closed
            String sql2 = "SELECT * FROM V_ENCHERE WHERE id IN "+cond;
            System.out.println("SQL "+sql2);
            System.out.println("filtrer() val: "+val.size());
            
            return (ArrayList<V_enchere>)GenericDAO.findBySql(new V_enchere(), sql2, connection);
            
            //return val;
         
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

}
