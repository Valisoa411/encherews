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
    public void doRechargeCompte(@RequestParam String token,@RequestParam double valeur) throws Exception{
        Token tk = null;
        try {
            tk = Token.check(token);
        } catch (Exception e) {
            throw e;
        }
        int idClient = tk.getIdClient();
        RechargeCompte rc = new RechargeCompte();
        java.sql.Date d = java.sql.Date.valueOf(LocalDate.now());
        rc.setDate(d);
        rc.setEtat(11);
        rc.setIdClient(idClient);
        rc.setValeur(valeur);
        rc.rechargerCompte();
    }
    
    @GetMapping("/compte/etat")
    public String getEtatRecharge(@RequestParam int id) throws Exception {
        RechargeCompte rc = new RechargeCompte();
        rc.setId(id);
        String etat = rc.getEtatLibelle();
        return g.toJson(etat);
    }
}