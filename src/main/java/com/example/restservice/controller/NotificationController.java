package com.example.restservice.controller;

import com.google.gson.Gson;
import com.example.restservice.Response.Response;
import com.example.restservice.Response.Success;
import com.example.restservice.Response.Error;
import com.example.restservice.generic.Connexion;
import com.example.restservice.generic.GenericDAO;
import com.example.restservice.model.Categorie;
import com.example.restservice.model.Notification;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author tahina
 */
@CrossOrigin
@RequestMapping("/notifications")
@RestController
public class NotificationController {
    Gson g = new Gson();
    
    @GetMapping("/clients/{id}")
    public String listeNotification(@PathVariable("id") int id) throws Exception{
        Notification n = new Notification();
        n.setIdClient(id);
        ArrayList<Notification> liste = n.listeNotification();
        return g.toJson(liste);
    }
    
}
