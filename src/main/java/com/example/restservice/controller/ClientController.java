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

import java.sql.Connection;
import java.util.*;
import com.example.restservice.model.Client;
import com.example.restservice.model.Enchere;
import com.example.restservice.token.Token;
import com.google.gson.Gson;

import io.jsonwebtoken.ExpiredJwtException;

@RestController
@CrossOrigin
@RequestMapping("/clients")
public class ClientController {
    Gson g = new Gson();
        
    @PostMapping("/client")
    public void inscription(@RequestParam String nom,@RequestParam String email,@RequestParam String password) throws Exception{
        Client cl = new Client();
        cl.setNom(nom);
        cl.setEmail(email);
        cl.setPassword(password);
        cl.setSolde(0);
        cl.insert();
    }

    @GetMapping("/{idClient}/encheres")
    public String getListeEnchere(@PathVariable("idClient") int idClient) throws Exception {
        Enchere enc = new Enchere();
        enc.setIdClient(idClient);
        ArrayList<Enchere> liste = enc.listeEnchere();
        return g.toJson(liste);
    }
    
    @PostMapping("/login")
    public String login(@RequestParam String email,@RequestParam String mdp) throws Exception {
        Connection con=Connexion.getConnexion();
        ArrayList<Client> list = new ArrayList<Client>();
        String res = "";
        Response r = new  Response();
        try {
            list = GenericDAO.findBySql(new Client(), "SELECT * FROM CLIENT WHERE EMAIL='"+email+"' AND PASSWORD='"+mdp+"'", con);
            if(!list.isEmpty()){
                Client user = list.get(0);
                Token token = new Token(user.getId());
                GenericDAO.save(token, con);
                r.setData(new Success("Login Success"));
                r.addAttribute("token", "bearer "+token.getToken());
            }
            else{
                r.setError(new Error(1,"Authentification echouee"));
            }
            res = g.toJson(r);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(con!=null)con.close();
        }
        return res;
    }

    @GetMapping("/logout")
    public String logout(@RequestParam String token) throws Exception {
        Response res = new Response();
        try (Connection con = Connexion.getConnexion()) {
            Token t = Token.check(token);
            t.kill(con);
            res.setData(new Success("Deconnexion reussi"));
            return g.toJson(res);
        } catch (ExpiredJwtException e) {
            res.setData(new Success("Deconnexion reussi"));
            return g.toJson(res);
        } catch (Exception e) {
            throw e;
        }
    }
}