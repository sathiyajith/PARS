/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.satks.pasv;

import com.satks.pasv.Prometheus.Prometheus;
import java.awt.Desktop;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class UploadPageController implements Initializable {

    @FXML
    private GridPane uploadpage;
    private Stage stage;
    public String content = null;
    public Prometheus prometheus;
    
    @FXML
    private Label filePath;
    
    private Desktop desktop = Desktop.getDesktop();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        prometheus = Prometheus.getInstance();
    }    
    
    @FXML
    private void uploadFile(MouseEvent event) throws IOException
    {
        stage = (Stage)uploadpage.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("YAML", "*.yaml","*.yml")
            );
        File file = fileChooser.showOpenDialog(stage);
        if (file!=null)
        {
            openFile(file);
        }
    }
    
    private void openFile(File file) throws IOException 
    {   
       filePath.setText(file.getPath());
       readFile(file);
    }
    
    private void readFile(File file) throws IOException
    {
        FileReader reader = null;
        try {
            reader = new FileReader(file);
            char[] chars = new char[(int) file.length()];
            reader.read(chars);
            content = new String(chars);
            reader.close();
        } 
        catch (IOException e) {} 
        finally 
        {
            if(reader != null)
            {
                reader.close();
            }
        }
        //System.out.println(content);
        prometheus.setConfig(content);
    }
}
