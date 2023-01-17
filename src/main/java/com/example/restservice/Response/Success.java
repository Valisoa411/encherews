/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.restservice.Response;

import java.util.HashMap;

/**
 *
 * @author P14_A_111_Dina
 */
public class Success {
    String message;
    HashMap<String,Object> data = new HashMap<String,Object>();

    public HashMap<String, Object> getData() {
        return data;
    }

    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }

//    public void setData(String data) {
//        this.data = data;
//    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    public Success(){}
    
    public Success(String m, HashMap<String,Object> data){
        this.message = m;
        this.data = data;
    }
    
    public Success(HashMap<String,Object> data){
        this.data = data;
    }
    
    public Success(String m){
        this.message = m;
    }
}
