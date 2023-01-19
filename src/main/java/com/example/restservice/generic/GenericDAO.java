/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.restservice.generic;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.ResultSet;

/**
 *
 * @author Val
 */
public class GenericDAO {
    public static String upperStart(String str){
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
    
    public static void save (Object object, Connection con) throws Exception {
        Statement stm = null;
        if(object == null) throw new Exception("Object is null");
        try{
            stm = con.createStatement();
            Class clas = object.getClass();
            String table = object.getClass().getAnnotation(ClassAnotation.class).table();
            if(table.compareTo("") == 0) table = clas.getSimpleName();
            ArrayList<String> cols = new ArrayList<String>();
            ArrayList values = new ArrayList();
            Field[] fields = clas.getDeclaredFields();
            String val = "";
            String sql = "";
            String fieldName = "";
            for(Field field : fields){
                if(field.isAnnotationPresent(Attr.class)){
                    if(!field.getAnnotation(Attr.class).isPrimary()){
                        Method met = clas.getDeclaredMethod("get"+upperStart(field.getName())); // TANDREMO FA RA VO BOOLEAN DE isAttribut()
                        values.add(met.invoke(object));
                        String colname = field.getAnnotation(Attr.class).col();
                        if(colname.compareTo("") == 0){
                            colname = field.getName();
                        }
                        cols.add(colname);
                    }
                }
            }
            for(int i=0;i<cols.size();i++){
                fieldName += cols.get(i);
                if(i != cols.size()-1){
                    fieldName += ",";
                }
            }
            fieldName = "("+fieldName+")";
            for(int i=0;i<values.size();i++){
                Object v = values.get(i);
                if(v != null){
                    val += "'"+v.toString()+"'";
                }
                else{
                    val += "null";
                }
                if(i != values.size()-1){
                    val += ",";
                }
            }
            sql = "INSERT INTO "+table+""+fieldName+" VALUES ("+val+")";
            System.out.println(sql);
            stm.executeUpdate(sql);
            
            // con.commit();
        }
        catch(Exception e){
            // con.rollback();
            throw e;
        }
        finally{
            if(stm != null) stm.close();
        }
    }
    
    public static void update(Object object,Connection con) throws Exception{
        Statement stm = null;
        if(object == null) throw new Exception("Object is null");
        try {
            stm = con.createStatement();
            Class clas = object.getClass();
            String table = object.getClass().getAnnotation(ClassAnotation.class).table();
            if(table.compareTo("") == 0) table = clas.getSimpleName();
            ArrayList<String> cols = new ArrayList<String>();
            ArrayList values = new ArrayList();
            Field[] fields = clas.getDeclaredFields();
            Method met = null;
            String primCol = null;
            Object primValues = null;
            String val = "";
            String sql = "";
            
            for(Field field : fields){
                if(field.isAnnotationPresent(Attr.class)){
                    if(!field.getAnnotation(Attr.class).isPrimary()){
                        met = clas.getDeclaredMethod("get"+upperStart(field.getName()));
                        values.add(met.invoke(object));
                        String colname = field.getAnnotation(Attr.class).col();
                        if(colname.compareTo("") == 0){
                            colname = field.getName();
                        }
                        cols.add(colname);
                    }
                    else{
                        met = clas.getDeclaredMethod("get"+upperStart(field.getName()));
                        String colname = field.getAnnotation(Attr.class).col();
                        primCol = colname;
                        if(primCol.compareTo("") == 0){
                            primCol = field.getName();
                        }
                        primValues = met.invoke(object);
                        if(primValues == null) throw new Exception("Undefined primary key");
                    }
                }
            }
            for(int i=0;i<values.size();i++){
                String col = cols.get(i);
                Object v = values.get(i);
                if(v != null){
                    val += col+"='"+v.toString()+"'";
                }
                if(i != values.size()-1){
                    val += ",";
                }
            }
            sql = "UPDATE "+table+" SET "+val+" WHERE "+primCol+"='"+primValues.toString()+"'";
            System.out.println(sql);
            
            stm.executeUpdate(sql);
            
            // con.commit();
        }
        catch (Exception e) {
            // con.rollback();
            throw e;
        }
        finally{
            if(stm != null) stm.close();
        }
    }

    public static void delete(Object object,Connection con) throws Exception{
        Statement stm = null;
        if(object == null) throw new Exception("Object is null");
        try {
            stm = con.createStatement();
            Class clas = object.getClass();
            String table = object.getClass().getAnnotation(ClassAnotation.class).table();
            if(table.compareTo("") == 0) table = clas.getSimpleName();
            Field[] fields = clas.getDeclaredFields();
            String primCol = null;
            Object primValues = null;
            String sql = "";
            
            for(Field field : fields){
                if(field.isAnnotationPresent(Attr.class)){
                    if(field.getAnnotation(Attr.class).isPrimary()){
                        Method met = clas.getDeclaredMethod("get"+upperStart(field.getName()));
                        String colname = field.getAnnotation(Attr.class).col();
                        primCol = colname;
                        if(primCol.compareTo("") == 0){
                            primCol = field.getName();
                        }
                        primValues = met.invoke(object);
                        if(primValues == null) throw new Exception("Undefined primary key");
                        break;
                    }
                }
            }
            sql = "DELETE FROM "+table+" WHERE "+primCol+"='"+primValues.toString()+"'";
            System.out.println(sql);
            
            stm.executeUpdate(sql);
            
            // con.commit();
        }
        catch (Exception e) {
            // con.rollback();
            throw e;
        }
        finally{
            if(stm != null) stm.close();
        }
    }
    
    public static Object get(Object object,Connection con) throws Exception{
        Statement stm = null;
        ResultSet res = null;
        if(object == null) throw new Exception("Object is null");
        try {
            stm = con.createStatement();
            Class clas = object.getClass();
            String table = object.getClass().getAnnotation(ClassAnotation.class).table();
            if(table.compareTo("") == 0) table = clas.getSimpleName();
            ArrayList<String> cols = new ArrayList<String>();
            ArrayList<Field> flds = new ArrayList<Field>();
            ArrayList values = new ArrayList();
            Field[] fields = clas.getDeclaredFields();
            Method met = null;
            String primCol = null;
            Object primValues = null;
            String sql = "";
            
            for(Field field : fields){
                if(field.isAnnotationPresent(Attr.class)){
                    if(!field.getAnnotation(Attr.class).isPrimary()){
                        flds.add(field);
                        String colname = field.getAnnotation(Attr.class).col();
                        if(colname.compareTo("") == 0){
                            colname = field.getName();
                        }
                        cols.add(colname);
                    }
                    else{
                        met = clas.getDeclaredMethod("get"+upperStart(field.getName()));
                        String colname = field.getAnnotation(Attr.class).col();
                        primCol = colname;
                        if(primCol.compareTo("") == 0){
                            primCol = field.getName();
                        }
                        primValues = met.invoke(object);
                    }
                }
            }
            sql = "SELECT * FROM "+table+" WHERE "+primCol+"='"+primValues.toString()+"'";
            System.out.println(sql);
            
            res = stm.executeQuery(sql);
            while( res.next() ){
                for(int i=0;i<cols.size();i++){
                    Field f = flds.get(i);
                    String col = cols.get(i);
                    met = clas.getDeclaredMethod("set"+upperStart(f.getName()),f.getType());
                    met.invoke(object,res.getObject(col));
                }
            }
            return object;
        }
        catch (Exception e) {
            throw e;
        }
        finally{
            if(res != null) res.close();
            if(stm != null) stm.close();
        }
    }
    
    /**
     * mbola tsy mety tsara refa nombre ny type
     * @param object
     * @param con
     * @return
     * @throws Exception 
     */
    public static ArrayList find(Object object,Connection con) throws Exception{
        Statement stm = null;
        ResultSet res = null;
        ArrayList val = new ArrayList();
        if(object == null) throw new Exception("Object is null");
        try {
            stm = con.createStatement();
            Class clas = object.getClass();
            String table = object.getClass().getAnnotation(ClassAnotation.class).table();
            if(table.compareTo("") == 0) table = clas.getSimpleName();
            ArrayList<String> cols = new ArrayList<String>();
            ArrayList<Field> flds = new ArrayList<Field>();
            ArrayList values = new ArrayList();
            Field[] fields = clas.getDeclaredFields();
            Method met = null;
            Field primFld = null;
            String primCol = null;
            String va = "";
            String sql = "";
            
            for(Field field : fields){
                if(field.isAnnotationPresent(Attr.class)){
                    if(!field.getAnnotation(Attr.class).isPrimary()){
                        met = clas.getDeclaredMethod("get"+upperStart(field.getName()));
                        values.add(met.invoke(object));
                        flds.add(field);
                        String colname = field.getAnnotation(Attr.class).col();
                        if(colname.compareTo("") == 0){
                            colname = field.getName();
                        }
                        cols.add(colname);
                    }
                    else{
                        String colname = field.getAnnotation(Attr.class).col();
                        primFld = field;
                        primCol = colname;
                        if(primCol.compareTo("") == 0){
                            primCol = field.getName();
                        }
                    }
                }
            }
            for(int i=0;i<values.size();i++){
                String col = cols.get(i);
                Object v = values.get(i);
                if(v != null){
                    va += " AND "+col+"='"+v.toString()+"'";
                }
            }
            sql = "SELECT * FROM "+table+" WHERE 1=1 "+va;
            System.out.println(sql);
            
            res = stm.executeQuery(sql);
            while(res.next()){
                Object e = clas.newInstance();
                met = clas.getDeclaredMethod("set"+upperStart(primFld.getName()),primFld.getType());
                met.invoke(e, res.getObject(primCol));
                for(int i=0;i<cols.size();i++){
                    Field f = flds.get(i);
                    String col = cols.get(i);
                    met = clas.getDeclaredMethod("set"+upperStart(f.getName()),f.getType());
                    met.invoke(e,res.getObject(col));
                }
                val.add(e);
            }
            return val;
        }
        catch (Exception e) {
            throw e;
        }
        finally{
            if(res != null) res.close();
            if(stm != null) stm.close();
        }
    }
    
    public static ArrayList all(Object object,Connection con) throws Exception{
        Statement stm = null;
        ResultSet res = null;
        ArrayList val = new ArrayList();
        if(object == null) throw new Exception("Object is null");
        try {
            stm = con.createStatement();
            Class clas = object.getClass();
            String table = object.getClass().getAnnotation(ClassAnotation.class).table();
            if(table.compareTo("") == 0) table = clas.getSimpleName();
            ArrayList<String> cols = new ArrayList<String>();
            ArrayList<Field> flds = new ArrayList<Field>();
            ArrayList values = new ArrayList();
            Field[] fields = clas.getDeclaredFields();
            Method met = null;
            Field primFld = null;
            String primCol = null;
            String va = "";
            String sql = "";
            
            for(Field field : fields){
                if(field.isAnnotationPresent(Attr.class)){
                    if(!field.getAnnotation(Attr.class).isPrimary()){
                        flds.add(field);
                        String colname = field.getAnnotation(Attr.class).col();
                        if(colname.compareTo("") == 0){
                            colname = field.getName();
                        }
                        cols.add(colname);
                    }
                    else{
                        String colname = field.getAnnotation(Attr.class).col();
                        primFld = field;
                        primCol = colname;
                        if(primCol.compareTo("") == 0){
                            primCol = field.getName();
                        }
                    }
                }
            }
            sql = "SELECT * FROM "+table;
            System.out.println(sql);
            
            res = stm.executeQuery(sql);
            while(res.next()){
                Object e = clas.newInstance();
                met = clas.getDeclaredMethod("set"+upperStart(primFld.getName()),primFld.getType());
                met.invoke(e,res.getObject(primCol));
                for(int i=0;i<cols.size();i++){
                    Field f = flds.get(i);
                    String col = cols.get(i);
                    met = clas.getDeclaredMethod("set"+upperStart(f.getName()),f.getType());
                    met.invoke(e,res.getObject(col));
                }
                val.add(e);
            }
            return val;
        }
        catch (Exception e) {
            throw e;
        }
        finally{
            if(res != null) res.close();
            if(stm != null) stm.close();
        }
    }
    
    public static ArrayList findBySql(Object object,String sql,Connection con) throws Exception{
        Statement stm = null;
        ResultSet res = null;
        ArrayList val = new ArrayList();
        if(object == null) throw new Exception("Object is null");
        try {
            stm = con.createStatement();
            Class clas = object.getClass();
            ArrayList<String> cols = new ArrayList<String>();
            ArrayList<Field> flds = new ArrayList<Field>();
            ArrayList values = new ArrayList();
            Field[] fields = clas.getDeclaredFields();
            Method met = null;
            Field primFld = null;
            String primCol = null;
            
            for(Field field : fields){
                if(field.isAnnotationPresent(Attr.class)){
                    if(!field.getAnnotation(Attr.class).isPrimary()){
                        met = clas.getDeclaredMethod("get"+upperStart(field.getName()));
                        values.add(met.invoke(object));
                        flds.add(field);
                        String colname = field.getAnnotation(Attr.class).col();
                        if(colname.compareTo("") == 0){
                            colname = field.getName();
                        }
                        cols.add(colname);
                    }
                    else{
                        String colname = field.getAnnotation(Attr.class).col();
                        primFld = field;
                        primCol = colname;
                        if(primCol.compareTo("") == 0){
                            primCol = field.getName();
                        }
                    }
                }
            }
            System.out.println(sql);
            res = stm.executeQuery(sql);
            while(res.next()){
                Object e = clas.newInstance();
                if(primFld != null){
                    met = clas.getDeclaredMethod("set"+upperStart(primFld.getName()),primFld.getType());
                    met.invoke(e, res.getObject(primCol));
                }
                for(int i=0;i<cols.size();i++){
                    Field f = flds.get(i);
                    String col = cols.get(i);
                    met = clas.getDeclaredMethod("set"+upperStart(f.getName()),f.getType());
                    met.invoke(e,res.getObject(col));
                }
                val.add(e);
            }
            return val;
        }
        catch (Exception e) {
            throw e;
        }
        finally{
            if(res != null) res.close();
            if(stm != null) stm.close();
        }
    }
    
    public static Object getLastRecord(Object object,Connection con) throws Exception{
        Statement stm = null;
        ResultSet res = null;
        ArrayList val = new ArrayList();
        if(object == null) throw new Exception("Object is null");
        try {
            stm = con.createStatement();
            Class clas = object.getClass();
            String table = object.getClass().getAnnotation(ClassAnotation.class).table();
            if(table.compareTo("") == 0) table = clas.getSimpleName();
            Field[] fields = clas.getDeclaredFields();
            String primCol = null;
            String sql = "";
            
            for(Field field : fields){
                if(field.isAnnotationPresent(Attr.class)){
                    if(field.getAnnotation(Attr.class).isPrimary()){
                        String colname = field.getAnnotation(Attr.class).col();
                        primCol = colname;
                        if(primCol.compareTo("") == 0){
                            primCol = field.getName();
                        }
                        break;
                    }
                }
            }
            sql = "SELECT * FROM "+table+" ORDER BY "+primCol+" DESC";
            val = findBySql(object, sql, con);
            return val.get(0);
        }
        catch (Exception e) {
            throw e;
        }
        finally{
            if(res != null) res.close();
            if(stm != null) stm.close();
        }
    }
}
