/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.alarm_project;

/**
 *
 * @author Lenovo
 */
public class Alarm {
    int id;
    int hours;
    int minutes;
    int seconds;
    String status;
    String event;
    
    public Alarm() {
        hours = 0;
        minutes = 0;
        seconds = 0;
        status = "";
        event = "";
    }
    
    public Alarm (int hours, int minutes, int seconds, String event, String status) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
        this.event = event;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    @Override
    public String toString() {
        String str_hours, str_minutes, str_seconds;
        if (hours < 10) {
            str_hours = "0" + hours;
        } else {
            str_hours = Integer.toString(hours);
        }
        if (minutes < 10) {
            str_minutes = "0" + minutes;
        } else {
            str_minutes = Integer.toString(minutes);
        }
        if (seconds < 10) {
            str_seconds = "0" + seconds;
        } else {
            str_seconds = Integer.toString(seconds);
        }
        return "Alarm { " + "time=" + str_hours + ":" + str_minutes + ":" + str_seconds + " , event=" + event + " , ststus=" + status + " }";
    } 
}
