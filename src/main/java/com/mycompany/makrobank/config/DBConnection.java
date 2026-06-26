/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.makrobank.config;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

/**
 *
 * @author matheus
 */
public class DBConnection {
    public Connection getConnection() {
        var url = "jdbc:sqlite:makrobank.db";
        try{
            var conn = DriverManager.getConnection(url);
            return conn;
        }catch(SQLException e){
            System.out.println("A error happen when try to estabilish a connection: " + e.getMessage());
        }
        return null;
    }
}
