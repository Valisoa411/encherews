package com.example.restservice.controller;

import com.example.restservice.Response.Error;
import com.example.restservice.Response.Response;
import com.example.restservice.Response.Success;
import com.example.restservice.generic.Connexion;
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
import com.example.restservice.model.Parametre;
import com.example.restservice.model.Proposition;
import com.example.restservice.model.V_enchere;
import com.example.restservice.token.Token;
import com.google.gson.Gson;

import io.jsonwebtoken.ExpiredJwtException;

import java.sql.Connection;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@RestController
@CrossOrigin
@RequestMapping("/encheres")
public class EnchereController {
    Gson g = new Gson();

    @GetMapping("/v")
    public String encheres(){
        Response res = new Response();
        try (Connection con = Connexion.getConnexion()) {
            ArrayList<V_enchere> list = (ArrayList<V_enchere>) GenericDAO.all(new V_enchere(), con);
            res.setData(new Success("Liste des encheres"));
            res.addAttribute("listenchere", list);
        } catch (Exception e) {
            e.printStackTrace();
            res.setError(new Error(1, "Error : "+e));
        }
        return g.toJson(res);
    }

    @GetMapping("/v/{id}")
    public String enchere(@PathVariable("id") int id) throws Exception {
        Response res = new Response();
        try (Connection con = Connexion.getConnexion()) {
            V_enchere enc = (V_enchere) GenericDAO.findBySql(new V_enchere(), "SELECT * FROM V_ENCHERE WHERE ID="+id, con).get(0);
            res.setData(new Success("Enchere"));
            res.addAttribute("enc", enc);
        } catch (Exception e) {
            e.printStackTrace();
            res.setError(new Error(1, "Error : "+e));
        }
        return g.toJson(res);
    }

    @PostMapping("/enchere")
    public String addEnchere(@RequestParam String token,@RequestParam String nomProduit,@RequestParam String description,double prixEnchere,double duree,@RequestParam int idCategorie) throws Exception{
        Token tk = null;
        try {
            tk = Token.check(token);
        } catch (Exception e) {
            throw e;
        }
        Enchere enc = new Enchere();
        Response res = new Response();
        Parametre max = new Parametre("Duree Maximum");
        Parametre min = new Parametre("Duree Minimum");
        if(min.getParametreValue().getValeur()<=duree && duree<=max.getParametreValue().getValeur()){
            Timestamp dt = Timestamp.valueOf(LocalDateTime.now());
            enc.setDateDebut(dt);
            enc.setNomProduit(nomProduit);
            enc.setDescription(description);
            enc.setPrixEnchere(prixEnchere);
            enc.setDuree(duree);
            enc.setStatut(11);
            enc.setIdClient(tk.getIdClient());
            enc.setIdCategorie(idCategorie);
//            enc.insert();
            res.setData(new Success("Enchere ajouté avec succes"));
            res.addAttribute("idEnchere", enc.insert());
        }
        else{
            res.setError(new Error(1,"La duree d'un enchère inferieur a " + min.getParametreValue().getValeur() + " heures ou superieur a " + max.getParametreValue().getValeur() + " heures"));
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
        
        System.out.println("key : "+Token.keyToken);
        Response res = new Response();
        try {
            ArrayList<Enchere> list = Enchere.allNonFini();
            ArrayList<Enchere> vaovao = new ArrayList<Enchere>();
            Enchere e = new Enchere();
            EnchereImage ei = new EnchereImage();
            for(int i=0; i<list.size(); i++){
                e.setId(list.get(i).getId());
                e.setNomProduit(list.get(i).getNomProduit());
                e.setDescription(list.get(i).getDescription());
                e.setPrixEnchere(list.get(i).getPrixEnchere());
                e.setDuree(list.get(i).getDuree());
                e.setStatut(list.get(i).getStatut());
                e.setDateDebut(list.get(i).getDateDebut());
                e.setIdClient(list.get(i).getIdClient());
                e.setIdCategorie(list.get(i).getIdCategorie());
                
                ei.setIdEnchere(e.getId());
                e.setImages(ei.listeEnchereImage());
                vaovao.add(e);
            }
            res.setData(new Success("Liste des encheres"));
            res.addAttribute("listenchere", vaovao);
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
            EnchereImage ei = new EnchereImage();
            ei.setIdEnchere(enc.getId());
            Enchere vaovao = new Enchere();
            vaovao = enc;
            vaovao.setImages(ei.listeEnchereImage());
            res.setData(new Success("Enchere"));
            res.addAttribute("enc", vaovao);
        } catch (Exception e) {
            e.printStackTrace();
            res.setError(new Error(1, "Error : "+e));
        }
        return g.toJson(res);
    }

    @GetMapping("/{id}/images")
    public String getEnchereImage(@PathVariable("id") int id) throws Exception {
        Response res = new Response();
        try {
            EnchereImage ei = new EnchereImage();
            ei.setIdEnchere(id);
            ArrayList<EnchereImage> liste = ei.listeEnchereImage();
            res.setData(new Success("Image enchere"));
            res.addAttribute("image", liste);
        } catch (Exception e) {
            e.printStackTrace();
            res.setError(new Error(1, "Error : "+e));
        }
        return g.toJson(res);
    }

    @GetMapping("/{id}/propositions")
    public String getProposition(@PathVariable("id") int id) throws Exception {
        Response res = new Response();
        try {
            Proposition ei = new Proposition();
            ei.setIdEnchere(id);
            ArrayList<Proposition> liste = ei.listeProposition();
            res.setData(new Success("Proposition enchere"));
            res.addAttribute("proposition", liste);
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
            ArrayList<V_enchere> list = Enchere.filtrer(motcle, dates, categories, statut);
            res.setData(new Success("Filtre reussir"));
            res.addAttribute("listenchere", list);
        } catch (Exception e) {
            e.printStackTrace();
            res.setError(new Error(1,"Error : "+e));
        }
        return g.toJson(res);
    }
}