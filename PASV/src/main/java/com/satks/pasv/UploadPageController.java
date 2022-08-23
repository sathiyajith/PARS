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
    public String content = null;
    public String configFilePath;
    public AlertManager alertmanager;
    public ThanosRuleParser thanos;
    
    @FXML
    private Label filePath;
    @FXML
    private GridPane uploadpage;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(configFilePath!=null)
        {
        filePath.setText(configFilePath);
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
        stage = (Stage)uploadpage.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("YAML", "*.yaml","*.yml")
            );
        File file = fileChooser.showOpenDialog(stage);
        if (file!=null)
        {
            configFilePath = file.getPath();
            filePath.setText(configFilePath);
            readFile(file,fileType);
        }
    }
      
    private void readFile(File file,String fileType) throws IOException
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
        
        if(fileType.equals("alertmanager"))
        {
            alertmanager = AlertManager.getInstance();
            alertmanager.setConfig(content);
        }
        else
        {
            thanos = ThanosRuleParser.getInstance();
            thanos.setRule(content);
        }
    }

    
}
