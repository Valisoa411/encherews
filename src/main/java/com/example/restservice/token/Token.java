/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.restservice.token;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.Base64.Encoder;

import javax.crypto.SecretKey;

import com.example.restservice.generic.Attr;
import com.example.restservice.generic.ClassAnotation;
import com.example.restservice.generic.Connexion;
import com.example.restservice.generic.GenericDAO;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import javafx.beans.binding.SetExpression;
import io.jsonwebtoken.Claims;

/**
 *
 * @author tsotsoa
 */
@ClassAnotation(table = "token")
public class Token {
    public static final long DateEXP = 300000;
    // public static final String keyToken = "Token22";
    public static final String keyToken = Token.generateSafeKey();

    int id;
    @Attr
    String token;
    Date dateexpiration;
    @Attr
    int idClient;

    public Token(){}

    public Token(String token){
        init(token);
    }

    public Token(int idclient){
        String token = Token.CreerToken(idclient);
        init(token);
    }

    public void init(String token){
        Claims cl = Jwts.parser().setSigningKey(Token.keyToken)
                .parseClaimsJws(token).getBody();
        setToken(token);
        setDateexpiration(cl.getExpiration());
        setIdClient((int)cl.get("idclient"));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getDateexpiration() {
        return dateexpiration;
    }

    public void setDateexpiration(Date dateexpiration) {
        this.dateexpiration = dateexpiration;
    }
    
    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public static String CreerToken(int clientid) {
        long now = System.currentTimeMillis();
        Date dt = new Date(now + Token.DateEXP);
        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, Token.keyToken)
                .setIssuedAt(new Date(now))
                .setExpiration(dt)
                .claim("idclient", clientid)
                .compact();
        return token;
    }

    private static String generateSafeKey() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[36]; // 36 bytes * 8 = 288 bits, a little bit more than
                                     // the 256 required bits 
        random.nextBytes(bytes);
        Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(bytes);
    }

    public boolean isExpired(){
        return dateexpiration.before(new Date());
    }

    public static Token check(String token) throws Exception {
        String bearer = "bearer ";
        if(!token.startsWith(bearer)) throw new Exception("Invalid token; no bearer");
        Token t = new Token(token.substring(bearer.length()));
        try (Connection con=Connexion.getConnexion()) {
            ArrayList<Token> list = (ArrayList<Token>) GenericDAO.findBySql(new Token(), "SELECT * FROM TOKEN WHERE IDCLIENT="+t.getIdClient()+" AND TOKEN='"+t.getToken()+"'", con);
            if(list.isEmpty()) throw new Exception("Not Connected");
            return (Token)list.get(0);
        } catch (Exception e) {
            throw e;
        }
    }

    public void kill(Connection con) throws Exception {
        try (Statement stm = con.createStatement()) {
            stm.executeUpdate("DELETE FROM TOKEN WHERE TOKEN='"+token+"'");
        } catch (Exception e) {
            throw e;
        }
    }
}
