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
}