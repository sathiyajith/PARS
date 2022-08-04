/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.satks.pasv.Prometheus;

public class Prometheus {
    
    public final static Prometheus instance = new Prometheus();

    public String config;
    
    public static Prometheus getInstance() {
        return instance;
    }
    public void setConfig(String config)
    {
        this.config = config;
    }
      
    public String getConfig()
    {
        return this.config;
    }
}
