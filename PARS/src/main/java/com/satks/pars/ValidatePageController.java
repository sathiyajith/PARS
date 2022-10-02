/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.satks.pars;

import com.satks.pars.Prometheus.AlertManager;
import com.satks.pars.Prometheus.ThanosRuleParser;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class ValidatePageController implements Initializable {
    
    private Boolean alertmanagerValidity;
    private File file;
    private HBox alertsValidityBox;
    private Label ruleLabel;
    
    @FXML
    private HBox alertmanagerFx;
    
    @FXML
    private VBox validityContentsFx;
    
    @FXML
    private ImageView alertmanagerImageFx;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        alertmanagerValidity = AlertManager.getInstance().getValidity();
        System.out.println(alertmanagerValidity);
                
        file = new File("D:\\code\\GITHUB_Projects\\PARS\\res\\correct.png");
        Image correctImage = new Image(file.toURI().toString());
        
        file = new File("D:\\code\\GITHUB_Projects\\PARS\\res\\wrong.png");
        Image wrongImage = new Image(file.toURI().toString());
        
        
        if(alertmanagerValidity)
        {
            alertmanagerFx.getStyleClass().add("correct_files");
            alertmanagerImageFx.setImage(correctImage);
            alertmanagerImageFx.setFitHeight(40);
            alertmanagerImageFx.setFitWidth(40);
        }
        else
        {
            alertmanagerFx.getStyleClass().add("wrong_files");
            alertmanagerImageFx.setImage(wrongImage);
            alertmanagerImageFx.setFitHeight(40);
            alertmanagerImageFx.setFitWidth(40);
        }
        
        for(int i=0;i<ThanosRuleParser.getValidity().size();i++)
        {
            alertsValidityBox = new HBox();
            ruleLabel = new Label("Alert Rule "+(i+1));
            ruleLabel.getStyleClass().add("titles");
            ImageView alertImageFx = new ImageView();
            alertImageFx.setFitHeight(40);
            alertImageFx.setFitWidth(40);
            alertsValidityBox.getChildren().add(ruleLabel);
            
            if(ThanosRuleParser.getValidity().get(i))
            {
                alertsValidityBox.getStyleClass().add("correct_files");
                alertImageFx.setImage(correctImage);
                alertsValidityBox.getChildren().add(alertImageFx);
            }
            else
            {
                alertsValidityBox.getStyleClass().add("wrong_files");
                alertImageFx.setImage(wrongImage);
                alertsValidityBox.getChildren().add(alertImageFx);
            }
            validityContentsFx.getChildren().add(alertsValidityBox);
        }
        
        
    }
    
}
