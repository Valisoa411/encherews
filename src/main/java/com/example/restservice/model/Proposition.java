/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.restservice.model;

import com.example.restservice.generic.Attr;
import com.example.restservice.generic.ClassAnotation;
import java.util.Date;

/**
 *
 * @author Karen
 */
@ClassAnotation(table = "proposition")
public class Proposition {
    @Attr(isPrimary = true)
    int id;
    @Attr
    int idClient;
    @Attr
    int idEnchere;
    @Attr
    double montant;
    @Attr
    Date date;

    public Proposition() {
    }

    public Proposition(int id) {
        this.id = id;
    }

    public Proposition(int id, int idClient, int idEnchere, double montant, Date date) {
        this.id = id;
        this.idClient = idClient;
        this.idEnchere = idEnchere;
        this.montant = montant;
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

    public int getIdEnchere() {
        return idEnchere;
    }

    public void setIdEnchere(int idEnchere) {
        this.idEnchere = idEnchere;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
}
