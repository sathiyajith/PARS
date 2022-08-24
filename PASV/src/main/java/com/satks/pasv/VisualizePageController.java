
package com.satks.pasv;

import com.satks.pasv.Prometheus.Alert;
import com.satks.pasv.Prometheus.AlertManager;
import com.satks.pasv.Prometheus.ThanosRuleParser;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author kssat
 */
public class VisualizePageController implements Initializable {

    @FXML
    private VBox receiversbox;
    @FXML
    private VBox routesbox;
    @FXML
    private VBox rulesbox;
    @FXML
    private HBox visualize_table;
    @FXML
    private Button map_button;
    
    
    public AlertManager alertmanager;
    public ThanosRuleParser thanos;
    public List<AlertManager.Route> routes;
    public List<AlertManager.Receiver> receivers;
    public ArrayList<Alert> rules;
    private TableView table = new TableView();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
       alertmanager = AlertManager.getInstance();
       thanos = ThanosRuleParser.getInstance();
       
       routes = alertmanager.getRoutes();
       receivers = alertmanager.getReceivers();
       rules = thanos.getAlerts();
       
       
       for (int i = 0; i < receivers.size(); i++) 
       {
           Label receiver = new Label(receivers.get(i).getReceiverName());
           receiver.getStyleClass().add("items");
           receiversbox.getChildren().add(receiver);
       }
       
       for (int i = 0; i < rules.size(); i++) 
       {
           Label rule = new Label(rules.get(i).getAlertName());
           rule.getStyleClass().add("items");
           rulesbox.getChildren().add(rule);
       }      
    }
    
    @FXML
    private void visualize(MouseEvent event)
    {
        for (int i = 0; i < receivers.size(); i++) 
       {
           TableColumn colName = new TableColumn(receivers.get(i).getReceiverName());
           table.getColumns().add(colName);
       }
        
        for (int i = 0; i < rules.size(); i++) 
       {
           Alert alert = rules.get(i);
           String alertName = alert.getAlertName();
           JSONObject labels = alert.getLabels();
       }
        
        
        visualize_table.getChildren().add(table);
        
        
    }
    
    
}
