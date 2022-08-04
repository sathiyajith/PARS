/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.satks.pasv;

import com.satks.pasv.Prometheus.Prometheus;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author kssat
 */
public class VisualizePageController implements Initializable {

    @FXML
    private Label alertmanager_config;

    public Prometheus prometheus;
    public String config;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       prometheus = Prometheus.getInstance();
       config = prometheus.getConfig();
       alertmanager_config.setText(config);
    }    
    
}
