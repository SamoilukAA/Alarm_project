/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.alarm_project;

import com.google.gson.Gson;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lenovo
 */
public class ServerClient {
    int id;
    Socket cs;
    DataInputStream dis;
    DataOutputStream dos; 
    DataOutputStream dos_timer;
    Gson convert = new Gson();
    MainServer ms;
    
    Thread t;
    Thread check_t;
    
    public ServerClient(int id, Socket cs, MainServer ms) { 
        this.id = id;
        this.cs = cs;
        this.ms = ms;
        
        System.out.println("Подключился клиент " + id + "\n");
        
        try {
            dos = new DataOutputStream(cs.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(ServerClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        t = new Thread(
            ()->{
                try {
                    dis = new DataInputStream(cs.getInputStream());
                    while(true) {
                        String str;
                        str = dis.readUTF();
                        
                        Alarm alr = convert.fromJson(str, Alarm.class);
                        ms.addEvent(alr);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(ServerClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        );
        t.start();
       
        sendAllEvents(0);
    }
    
    void sendTime(int hours, int minutes, int seconds) {
        int time = seconds + minutes * 60 + hours * 60 * 60;
        String tmp = Integer.toString(time);
                    
        Message msg = new Message();
        msg.setId(1);
        msg.setMsg(tmp);
                    
        String sendStr = convert.toJson(msg);
        
        try {
            dos.writeUTF(sendStr);
        } catch (IOException ex) {
            Logger.getLogger(ServerClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void sendAllEvents(int v) {
        if (v == 0) {
            ArrayList<Alarm> fe = new ArrayList<>();
        
            fe.addAll(ms.m.getAllAlarms());
        
            for (Alarm al: fe) {
               sendEvent(al);
            }
        } else {
            Message msg = new Message();
            msg.setId(3);
            String sendStr = convert.toJson(msg);
            try {
                dos.writeUTF(sendStr);            
            } catch (IOException ex) {
                Logger.getLogger(ServerClient.class.getName()).log(Level.SEVERE, null, ex);
            }
            ArrayList<Alarm> fe = new ArrayList<>();
        
            fe.addAll(ms.m.getAllAlarms());
        
            for (Alarm al: fe) {
               sendEvent(al);
            }
        }
    }
    
    void sendEvent(Alarm alrm) {        
        String tmp = convert.toJson(alrm);
        
        Message msg = new Message();
        
        if (alrm.getStatus().equals("Получено")) {
            msg.setId(0);
        } else 
            msg.setId(2);
        
        msg.setMsg(tmp);
        
        String sendStr = convert.toJson(msg);
        
        try {
            dos.writeUTF(sendStr);            
        } catch (IOException ex) {
            Logger.getLogger(ServerClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
