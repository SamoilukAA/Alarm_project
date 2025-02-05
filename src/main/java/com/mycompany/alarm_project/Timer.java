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
public class Timer {
    int hours = 0;
    int minutes = 0;
    int seconds = 0;
    
    public void tick() {
        seconds++;
        if (seconds == 60) {
            minutes++;
            seconds = 0;
            if (minutes == 60) {
                hours++;
                minutes = 0;
            }
        }
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

    public void reset() {
        hours = 0;
        minutes = 0;
        seconds = 0;
    }
}
