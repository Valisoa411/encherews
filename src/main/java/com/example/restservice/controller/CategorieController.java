package com.example.restservice.controller;

import com.google.gson.Gson;
import com.example.restservice.Response.Response;
import com.example.restservice.Response.Success;
import com.example.restservice.Response.Error;
import com.example.restservice.generic.Connexion;
import com.example.restservice.generic.GenericDAO;
import com.example.restservice.model.Categorie;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author tahina
 */
@CrossOrigin
@RequestMapping("/categorie")
@RestController
public class CategorieController {
    Gson g = new Gson();
    
    @GetMapping
    public String getCategorie(){
        Response res = new Response();
        try {
            ArrayList<Categorie> listCategorie = GenericDAO.findBySql(new Categorie(),"select* from categorie", Connexion.getConnexion());
            res.setData(new Success("Filtre reussir"));
            res.addAttribute("listCategorie", listCategorie);
        } catch (Exception e) {
            e.printStackTrace();
            res.setError(new Error(1,"Error : "+e));
            
        }
        return g.toJson(res);
    }
    
}
