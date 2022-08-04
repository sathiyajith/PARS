/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.satks.pasv;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author kssat
 */
public class MainPageController implements Initializable {
   
    @FXML
    private StackPane textArea;
    
    @FXML
    private void loadUploadPage(MouseEvent event)
    {
        loadPage("UploadPage.fxml");
    }
    
    @FXML
    private void loadVisualizePage(MouseEvent event)
    {
        loadPage("VisualizePage.fxml");
    }
    
    @FXML
    private void loadFeedbackPage(MouseEvent event)
    {
        loadPage("FeedbackPage.fxml");
    }
    
    @FXML
    private void loadUpgradePage(MouseEvent event)
    {
        loadPage("UpgradePage.fxml");
    }
    
    private void loadPage(String page)
    {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(page));
            textArea.getChildren().setAll(root);
        } catch (IOException ex) {
            Logger.getLogger(MainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       loadPage("UploadPage.fxml");
    }
}
