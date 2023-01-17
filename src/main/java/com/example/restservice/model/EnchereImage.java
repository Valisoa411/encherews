/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.restservice.model;

import com.example.restservice.generic.Attr;
import com.example.restservice.generic.ClassAnotation;
import com.example.restservice.generic.Connexion;
import com.example.restservice.generic.GenericDAO;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import org.apache.commons.codec.binary.Base64;
/**
 *
 * @author P14_A_111_Dina
 */
@ClassAnotation(table="EnchereImage")
public class EnchereImage {
    @Attr
    int idEnchere;
    @Attr
    String image;

    public int getIdEnchere() {
        return idEnchere;
    }

    public void setIdEnchere(int idEnchere) {
        this.idEnchere = idEnchere;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) throws IOException {
//        byte[] fileContent = FileUtils.readFileToByteArray(new File(image));
//        String encodedString = Base64.getEncoder().encodeToString(fileContent);
//        encodedString = new String(Base64.encodeBase64(fileContent), "UTF-8");
        File f = new File(image); //change path of image according to you
        FileInputStream fis = new FileInputStream(f);
        byte byteArray[] = new byte[(int)f.length()];
        fis.read(byteArray);
        String imageString = Base64.encodeBase64String(byteArray);
        
        this.image = imageString;
    }
    
    public void insert() throws Exception{
        Connection connect = new Connexion().getConnexion();
	try{
            GenericDAO.save(this,connect);
        }
        catch(Exception e){
            throw e;
        }
        finally{
            connect.close();
        }
    }
    
    public ArrayList<EnchereImage> listeEnchereImage() throws Exception{
        Connection connect = new Connexion().getConnexion();
        String requete = "SELECT * FROM EnchereImage WHERE idEnchere = " + this.idEnchere;
        ArrayList<EnchereImage> liste = new ArrayList<EnchereImage>();
        try{
            liste = (ArrayList<EnchereImage>)GenericDAO.findBySql(this,requete,connect);
        }
        catch(Exception e){
            throw e;
        }
        finally{
            connect.close();
        }
        return liste;
    }
}
