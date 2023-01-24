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
import com.example.restservice.model.Parametre;
import com.example.restservice.token.Token;
import com.google.gson.Gson;

import io.jsonwebtoken.ExpiredJwtException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@RestController
@CrossOrigin
@RequestMapping("/encheres")
public class EnchereController {
    Gson g = new Gson();
        
    @PostMapping("/enchere")
    public String addEnchere(@RequestParam String token,@RequestParam String nomProduit,@RequestParam String description,double prixEnchere,double duree,@RequestParam int statut,@RequestParam int idClient,@RequestParam int idCategorie) throws Exception{
        Token tk = null;
        try {
            tk = Token.check(token);
        } catch (Exception e) {
            throw e;
        }
        Enchere enc = new Enchere();
        Response res = new Response();
        Parametre p = new Parametre("DureeEnchere");
        if(duree <= p.getParametreValue().getValeur()){
            Timestamp dt = Timestamp.valueOf(LocalDateTime.now());
            enc.setDateDebut(dt);
            enc.setNomProduit(nomProduit);
            enc.setDescription(description);
            enc.setPrixEnchere(prixEnchere);
            enc.setDuree(duree);
            enc.setStatut(11);
            enc.setIdClient(tk.getIdClient());
            enc.setIdCategorie(idCategorie);
            enc.insert();
            res.setData(new Success("Enchere ajouté avec succes"));
        }
        else{
            res.setError(new Error(1,"La duree d'un enchère ne peut depasser de " + p.getParametreValue().getValeur() + " heures"));
        }
        return g.toJson(res);
    }
    
    @GetMapping("/enchere/statut")
    public String getStatut(@RequestParam int statut) throws Exception {
        Enchere enc = new Enchere();
        enc.setStatut(statut);
        String lib = enc.getStatutLibelle();
        return g.toJson(lib);
    }

    @GetMapping
    public String allEnchere() throws Exception {
        Response res = new Response();
        try {
            ArrayList<Enchere> list = Enchere.allNonFini();
            res.setData(new Success("Liste des encheres"));
            res.addAttribute("listenchere", list);
        } catch (Exception e) {
            e.printStackTrace();
            res.setError(new Error(1, "Error : "+e));
        }
        return g.toJson(res);
    }

    @GetMapping("/{id}")
    public String getEnchere(@PathVariable("id") int id) throws Exception {
        Response res = new Response();
        try {
            Enchere enc = new Enchere();
            enc = Enchere.getEnchere(id);
            res.setData(new Success("Enchere"));
            res.addAttribute("enc", enc);
        } catch (Exception e) {
            e.printStackTrace();
            res.setError(new Error(1, "Error : "+e));
        }
        return g.toJson(res);
    }

    @GetMapping("/filtrer")
    public String filtrer(
        @RequestParam(defaultValue = "") String motcle,
        @RequestParam(defaultValue = "") String d1,
        @RequestParam(defaultValue = "") String d2,
        @RequestParam String[] categories,
        @RequestParam(defaultValue = "-1") int statut
    ) throws Exception {
        Response res = new Response();
        try {
            Date[] dates = new Date[2];
            try {
                if(!d1.isEmpty()) dates[0] = new Date(d1.replace("-", "/"));
                if(!d2.isEmpty()) dates[1] = new Date(d2.replace("-", "/"));
            } catch (Exception e) {
                // e.printStackTrace();
            }
            ArrayList<Enchere> list = Enchere.filtrer(motcle, dates, categories, statut);
            res.setData(new Success("Filtre reussir"));
            res.addAttribute("listenchere", list);
        } catch (Exception e) {
            e.printStackTrace();
            res.setError(new Error(1,"Error : "+e));
        }
        return g.toJson(res);
    }
}