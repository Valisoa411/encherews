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
public class Response {
    //HashMap<String,Object> data;
    Success data;
    Error error;

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }
    
    public Response(){
    }
    
    public Response(Success s){
        this.data = s;
    }
    
    public Response(Error err){
        this.error = err;
    }

    public HashMap<String, Object> getData() {
        return data.getData();
    }

    public void setData(Success data) {
        this.data = data;
    }

    public void addAttribute(String key, Object value){
        if(data == null) return;
        data.getData().put(key, value);
    }

    public Object getAttribute(String key){
        return data.getData().get(key);
    }
    
//    public void addAttribute(String key, Object value){
//        if(data == null) data = new HashMap<String,Object>();
//        data.put(key, value);
//    }
}
