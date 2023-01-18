package com.example.restservice.controller;

import com.example.restservice.Response.Error;
import com.example.restservice.Response.Response;
import com.example.restservice.Response.Success;
import com.example.restservice.generic.GenericDAO;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;
import com.example.restservice.model.Client;
import com.example.restservice.model.Enchere;
import com.example.restservice.model.EnchereImage;
import com.example.restservice.model.RechargeCompte;
import com.example.restservice.token.Token;
import com.google.gson.Gson;

import io.jsonwebtoken.ExpiredJwtException;
import java.time.LocalDate;

@RestController
@CrossOrigin
@RequestMapping("/comptes")
public class RechargeCompteController {
    Gson g = new Gson();
        
    @PostMapping("/compte")
    public void doRechargeCompte(@RequestParam int idClient,@RequestParam double valeur) throws Exception{
        RechargeCompte rc = new RechargeCompte();
        java.sql.Date d = java.sql.Date.valueOf(LocalDate.now());
        rc.setDate(d);
        rc.setEtat(0);
        rc.setIdClient(idClient);
        rc.setValeur(valeur);
        rc.rechargerCompte();
    }
    
    @GetMapping
    public String getAllRecharge() throws Exception {
        RechargeCompte rc = new RechargeCompte();
        ArrayList<RechargeCompte> liste = rc.historiqueRecharge();
        return g.toJson(liste);
    }
    
    @GetMapping("/compte/etat")
    public String getEtatRecharge(@RequestParam int id) throws Exception {
        RechargeCompte rc = new RechargeCompte();
        rc.setId(id);
        String etat = rc.getEtatLibelle();
        return g.toJson(etat);
    }
}