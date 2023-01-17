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
import com.example.restservice.token.Token;
import com.google.gson.Gson;

import io.jsonwebtoken.ExpiredJwtException;

@RestController
@CrossOrigin
@RequestMapping("/enchereImages")
public class EnchereImageController {
    Gson g = new Gson();
        
    @PostMapping("/enchereImage")
    public void addImage(@RequestParam int idEnchere,@RequestParam String image) throws Exception{
        EnchereImage ei = new EnchereImage();
        ei.setIdEnchere(idEnchere);
        ei.setImage(image);
        ei.insert();
    }
    
//    @GetMapping("/{idClient}")
//    public String listeEnchere(@RequestParam int idClient, @RequestParam String token) throws Exception {
//        Response res = new Response();
//        try {
//            Token.check(token);
//        } catch (ExpiredJwtException e) {
//            res.setError(new Error(500, "Session Expired"));
//            return g.toJson(res);
//        } catch (Exception e) {
//            res.setError(new Error(500, "Token Error; "+e.getMessage()));
//            return g.toJson(res);
//        }
//        Enchere enc = new Enchere();
//        vcl.setIdAvion(id);
//        vcl = vcl.getAvion();
//        res.setData(new Success("Avion"));
//        res.addAttribute("vcl", vcl);
//        return g.toJson(res);
//    }
}