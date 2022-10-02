package com.satks.pars;

import com.satks.pars.Prometheus.AlertManager;
import com.satks.pars.Prometheus.ThanosRuleParser;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class UploadPageController implements Initializable {

    
    private Stage stage;
    private String content = null;
    private String configFilePath;
    private String rulePath;
    private AlertManager alertmanager;
    private ThanosRuleParser thanos;
    private File file;
    private FileChooser fileChooser;
    private FileReader reader;
    private char[] chars;
    private Label rulePathFx;
    private Label path;
    private Button browse;
    private Button submit;
    private HBox controlBox;
    
    @FXML
    private Label configFilePathFx;
    @FXML
    private GridPane uploadpageFx;
    @FXML
    private VBox ruleBoxFx;
    @FXML
    private Button plusButtonFx;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        
    }  
   
    private void uploadFile(MouseEvent event,String fileType) throws IOException
    {
        stage = (Stage)uploadpageFx.getScene().getWindow();
        fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("YAML", "*.yaml","*.yml")
            );
        file = fileChooser.showOpenDialog(stage);
        if (file!=null)
        {
            readFile(file,fileType);
        }
    }
        
    private void readFile(File file,String fileType) throws IOException
    {
        reader = null;
        try {
            reader = new FileReader(file);
            chars = new char[(int) file.length()];
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
    }
    
    
    @FXML
    private void UploadAlertmanagerFile(MouseEvent event) throws IOException
    {
        uploadFile(event, "alertmanager");
    }
    
    @FXML
    private void UploadThanosFile(MouseEvent event) throws IOException
    {
        uploadFile(event, "thanos");
    }
    
    @FXML
    private void SubmitAlertmanagerFile(MouseEvent event)
    {
        if(content.isEmpty()==false)
        {
            alertmanager = AlertManager.getInstance();
            alertmanager.setConfig(content);
            configFilePath = file.getPath();
            configFilePathFx.setText(configFilePath);
        }
    }
    
    @FXML
    private void SubmitThanosFile(MouseEvent event)
    {
        if(content.isEmpty()==false)
        {
            thanos = ThanosRuleParser.getInstance();
            thanos.setRule(content);
            rulePath = file.getPath();
            rulePathFx = (Label)App.root.lookup("#rulePathFx"+App.numRules);
            rulePathFx.setText(rulePath);
            plusButtonFx.setDisable(false);
        }
    }
    
    @FXML
    private void addRule(MouseEvent event)
    {
        App.numRules+=1;
        Label path = new Label("PATH");
        path.setPrefWidth(605);
        path.getStyleClass().add("paths");
        path.setId("rulePathFx"+App.numRules);
        
        browse = new Button("BROWSE");
        browse.setPrefHeight(30);
        browse.setPrefWidth(100);
        browse.getStyleClass().add("controls");
        browse.setOnMouseClicked(new EventHandler<MouseEvent>() { 
            @Override 
            public void handle(MouseEvent event) { 
                try {
                    UploadThanosFile(event);
                } catch (IOException ex) {
                    Logger.getLogger("Dynamic Browse button's handler not handled properly").log(Level.SEVERE, null, ex);
                }
            } 
        });
        
        submit = new Button("SUBMIT");
        submit.setPrefHeight(30);
        submit.setPrefWidth(100);
        submit.getStyleClass().add("controls");
        submit.setOnMouseClicked(new EventHandler<MouseEvent>() { 
            @Override 
            public void handle(MouseEvent event) { 
                SubmitThanosFile(event);
            } 
        });
        
        controlBox = new HBox();
        controlBox.getStyleClass().add("controls-bar");
        controlBox.setPrefHeight(50);
        controlBox.setPrefWidth(900);
        controlBox.setPadding(new Insets(15,15,15,15));
        
        controlBox.getChildren().add(path);
        controlBox.getChildren().add(browse);
        controlBox.getChildren().add(submit);
        controlBox.setSpacing(10);
        
        ruleBoxFx.getChildren().add(controlBox);
        ruleBoxFx.setSpacing(10);
        ruleBoxFx.setPadding(new Insets(10,10,10,10));
        plusButtonFx.setDisable(true);
    }
   
}