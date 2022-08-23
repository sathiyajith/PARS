
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
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author kssat
 */
public class VisualizePageController implements Initializable {

    @FXML
    private Label alertmanager_routes;
    @FXML
    private Label alertmanager_receivers;
    @FXML
    private Label thanos_rules;
    @FXML
    private VBox receiversbox;
    @FXML
    private VBox routesbox;
    @FXML
    private VBox rulesbox;
    
    public AlertManager alertmanager;
    public ThanosRuleParser thanos;
    public List<AlertManager.Route> routes;
    public List<AlertManager.Receiver> receivers;
    public ArrayList<Alert> rules;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
       alertmanager = AlertManager.getInstance();
       thanos = ThanosRuleParser.getInstance();
       
       routes = alertmanager.getRoutes();
       receivers = alertmanager.getReceivers();
       rules = thanos.getAlerts();
       
       for (int i = 0; i < routes.size(); i++) 
       {
           routesbox.getChildren().add(new Label(routes.get(i).getReceiverName()));
       }
       
       for (int i = 0; i < receivers.size(); i++) 
       {
           receiversbox.getChildren().add(new Label(receivers.get(i).getReceiverName()));
       }
       
       for (int i = 0; i < rules.size(); i++) 
       {
           rulesbox.getChildren().add(new Label(rules.get(i).getAlertName()));
       }
       
    }
    
}
