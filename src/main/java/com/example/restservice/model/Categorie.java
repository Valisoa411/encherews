package com.example.restservice.model;

import com.example.restservice.generic.Attr;
import com.example.restservice.generic.ClassAnotation;

@ClassAnotation(table = "categorie")
public class Categorie {
    @Attr(isPrimary = true)
    int id;
    @Attr
    String libelle;
    @Attr
    int etat;
    public Categorie() {
    }
    
    public Categorie(int id, String libelle, int etat) {
        this.id = id;
        this.libelle = libelle;
        this.etat = etat;
    }

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

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }
    
}
