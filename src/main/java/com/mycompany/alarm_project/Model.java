/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.alarm_project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lenovo
 */
public class Model {
    Connection c;
    void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(
                "jdbc:sqlite:C:\\sqlite\\alarm.db"
            );
            System.out.println("Opened database successfully");
        } catch (ClassNotFoundException ex) {
            System.out.println("Драйвер не найден");
        } catch (SQLException ex) {
            System.out.println("Не удалось подключиться к СУБД");
        }
    }
    
    Model() {
        connect();
    }
    
    ArrayList<Alarm> getAllAlarms() {
        ArrayList<Alarm> alarm_list = new ArrayList<>();
        
        if (c != null) {
            try {
                Statement st = c.createStatement();
                
                ResultSet r = st.executeQuery("select * from Alarm_list");
                
                while (r.next()) {
                    Alarm alrm = new Alarm(r.getInt("hours"), r.getInt("minutes"), r.getInt("seconds"), r.getString("event"), r.getString("status"));
                    alrm.setId(r.getInt("ID"));
                    alarm_list.add(alrm);
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return alarm_list;
    }

    void addAlarm(Alarm alrm) {
        if (c != null) {
            try {
                PreparedStatement pst = c.prepareStatement(
                        "Insert INTO Alarm_list(hours, minutes, seconds, event, status) VALUES(?,?,?,?,?)"
                );
                pst.setInt(1, alrm.getHours());
                pst.setInt(2, alrm.getMinutes());
                pst.setInt(3, alrm.getSeconds());
                pst.setString(4, alrm.getEvent());
                pst.setString(5, alrm.getStatus());
                pst.executeUpdate();
                
            } catch (SQLException ex) {
                Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    void updateStatus(Alarm alrm) {
        if (c != null) {
            try {
                PreparedStatement pst = c.prepareStatement(
                        "Update Alarm_list set status='Произошло' where ID=?"
                );
                pst.setInt(1, alrm.getId());
                pst.executeUpdate();
                
            } catch (SQLException ex) {
                Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
