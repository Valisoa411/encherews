/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.restservice.model;

import java.util.Date;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author P14_A_111_Dina
 */
public class Tache extends TimerTask {
    Enchere enc;

    public Enchere getEnc() {
        return enc;
    }

    public void setEnc(Enchere enc) {
        this.enc = enc;
    }
    
    @Override
    public void run() {
        System.out.println(new Date() + "Enchere terminee");
        try {
            enc.setStatut(5);
            enc.updateStatut();
            System.out.println("STATUT: "+enc.getStatut());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
