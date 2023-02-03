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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
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

        // BufferedWriter out = new BufferedWriter(new FileWriter("test.test"));
        // out.write("Lorem50"+image);
        // out.close();
        EnchereImage ei = new EnchereImage();
        ei.setIdEnchere(idEnchere);
        ei.addImage(image);
        ei.insert();
    }

    // @PostMapping("/enchereImage")
    // public void addImage(@RequestHeader int idEnchere,@RequestBody String photo) throws Exception{
    //     Gson g = new Gson();
    //     Photo p = g.fromJson(photo, Photo.class);
    //     String img = p.getImage();
    //     EnchereImage ei = new EnchereImage();
    //     ei.setIdEnchere(idEnchere);
    //     ei.setImage(img);
    //     ei.insert();
    //     System.out.println("Tafiditra");
    // }
    
    @GetMapping("/{idEnchere}")
    public String getImages(@PathVariable int idEnchere) throws Exception {
        Response res = new Response();
        try (Connection con = Connexion.getConnexion()) {
            ArrayList<EnchereImage> ls = (ArrayList<EnchereImage>) GenericDAO.findBySql(new EnchereImage(), "SELECT * FROM ENCHEREIMAGE WHERE IDENCHERE="+idEnchere, con);
            res.setData(new Success("images"));
            res.addAttribute("images", ls);
        } catch (Exception e) {
            e.printStackTrace();
            res.setError(new Error(0,"EnchereImageController getImages error"));
        }
        return g.toJson(res);
    }
}