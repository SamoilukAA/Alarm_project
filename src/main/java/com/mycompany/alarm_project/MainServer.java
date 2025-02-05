/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.alarm_project;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lenovo
 */
public class MainServer {
    int port = 3124;
    InetAddress ip;
    ServerSocket ss;
    
    int count = 0;
    
    Model m = new Model();
    
    ArrayList<Alarm> events = new ArrayList<>();
    ArrayList<ServerClient> allClients = new ArrayList<>();
    
    Timer timer = new Timer();
    
    Thread timer_tick;
    
    public void addEvent(Alarm alrm) {
        m.addAlarm(alrm);
        for (ServerClient serverClient: allClients) {
            serverClient.sendEvent(alrm);
        }
    }
    
    public void updateEvent() {
        for (ServerClient serverClient: allClients) {
            serverClient.sendAllEvents(1);
        }
    }
    
    public MainServer() {
        try {
            ip = InetAddress.getLocalHost();
            
            ss = new ServerSocket(port, 0, ip);
            System.out.println("Сервер запущен!");
            
            timer_tick = new Thread(
                ()->{
                    int t_time;
                    while (true) {
                        timer.tick();
                        for (ServerClient serverClient: allClients) {
                           serverClient.sendTime(timer.getHours(), timer.getMinutes(), timer.getSeconds());
                        }
                        events = m.getAllAlarms();
                        for(int i=0; i<events.size(); i++) {
                            if (events.get(i).getStatus().equals("Получено")) {
                                if (timer.getSeconds() == events.get(i).getSeconds())
                                    if (timer.getMinutes() == events.get(i).getMinutes())
                                        if (timer.getHours() == events.get(i).getHours()) {
                                            m.updateStatus(events.get(i));
                                            updateEvent();
                                        }                                            
                            }
                        }
                        try {
                            timer_tick.sleep(1000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(MainServer.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            );
            timer_tick.start();
            
            while(true) {
                Socket cs = ss.accept();
                count++;
                ServerClient sc = new ServerClient(count, cs, this);
                allClients.add(sc);
            }
            
        } catch (UnknownHostException ex) {
            Logger.getLogger(MainServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.out.println("Не могу запустить сервер!");
        }
    }
    
    public static void main(String[] args) {
        new MainServer();
    }
}