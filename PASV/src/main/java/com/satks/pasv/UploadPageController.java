package com.satks.pasv;

import com.satks.pasv.Prometheus.AlertManager;
import com.satks.pasv.Prometheus.ThanosRuleParser;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
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
    
    @FXML
    private Label configFilePathFx;
    @FXML
    private GridPane uploadpageFx;
    @FXML
    private Label rulePathFx;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(configFilePath!=null)
        {
            configFilePathFx.setText(configFilePath);
            rulePathFx.setText(rulePath);
        }
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
        
        if(fileType.equals("alertmanager"))
        {
            alertmanager = AlertManager.getInstance();
            alertmanager.setConfig(content);
            configFilePath = file.getPath();
            configFilePathFx.setText(configFilePath);
        }
        else
        {
            thanos = ThanosRuleParser.getInstance();
            thanos.setRule(content);
            rulePath = file.getPath();
            rulePathFx.setText(rulePath);
        }
    }

    
}
