package com.example.restservice.model;

import java.sql.Timestamp;

import com.example.restservice.generic.Attr;
import com.example.restservice.generic.ClassAnotation;

@ClassAnotation(table = "v_enchere")
public class V_enchere {
    @Attr
    int id;
    @Attr
    String nomProduit;
    @Attr
    String description;
    @Attr
    String categorie;
    @Attr
    double montant;
    @Attr
    Timestamp dateFin;
    @Attr
    String image;
    @Attr
    String client;
    public V_enchere() {
    }
    
    public V_enchere(int id, String nomProduit, String description, String categorie, double montant, Timestamp dateFin,
            String image, String client) {
        this.id = id;
        this.nomProduit = nomProduit;
        this.description = description;
        this.categorie = categorie;
        this.montant = montant;
        this.dateFin = dateFin;
        this.image = image;
        this.client = client;
    }

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
    public String getCategorie() {
        return categorie;
    }
    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }
    public double getMontant() {
        return montant;
    }
    public void setMontant(double montant) {
        this.montant = montant;
    }
    public Timestamp getDateFin() {
        return dateFin;
    }
    public void setDateFin(Timestamp dateFin) {
        this.dateFin = dateFin;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }
    
}
