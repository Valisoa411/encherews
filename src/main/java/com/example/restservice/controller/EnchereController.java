package com.example.restservice.controller;

import com.example.restservice.Response.Error;
import com.example.restservice.Response.Response;
import com.example.restservice.Response.Success;
import com.example.restservice.generic.GenericDAO;
import com.example.restservice.model.Kilometrage;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;
import com.example.restservice.model.Avion;
import com.example.restservice.model.Client;
import com.example.restservice.model.Enchere;
import com.example.restservice.model.Parametre;
import com.example.restservice.token.Token;
import com.google.gson.Gson;

import io.jsonwebtoken.ExpiredJwtException;

@RestController
@CrossOrigin
@RequestMapping("/encheres")
public class EnchereController {
    Gson g = new Gson();
        
    @PostMapping("/enchere")
    public String addEnchere(@RequestParam String nomProduit,@RequestParam String description,double prixEnchere,double duree,@RequestParam int statut,@RequestParam int idClient,@RequestParam int idCategorie) throws Exception{
        Enchere enc = new Enchere();
        Response res = new Response();
        Parametre p = new Parametre("DureeEnchere");
        if(duree <= p.getParametreValue().getValeur()){
            enc.setNomProduit(nomProduit);
            enc.setDescription(description);
            enc.setPrixEnchere(prixEnchere);
            enc.setDuree(duree);
            enc.setStatut(statut);
            enc.setIdClient(idClient);
            enc.setIdCategorie(idCategorie);
            enc.insert();
            res.setData(new Success("Enchere ajouté avec succes"));
        }
        else{
            res.setError(new Error(1,"La duree d'un enchère ne peut depasser de " + p.getParametreValue().getValeur() + " heures"));
        }
        return g.toJson(res);
    }
    
    @GetMapping("/{idClient}")
    public String getListeEnchere(@PathVariable("idClient") int idClient) throws Exception {
        Enchere enc = new Enchere();
        enc.setIdClient(idClient);
        ArrayList<Enchere> liste = enc.listeEnchere();
        return g.toJson(liste);
    }
    
    @GetMapping("/enchere/statut")
    public String getStatut(@RequestParam int statut) throws Exception {
        Enchere enc = new Enchere();
        enc.setStatut(statut);
        String lib = enc.getStatutLibelle();
        return g.toJson(lib);
    }
}