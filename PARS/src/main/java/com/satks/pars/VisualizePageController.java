
package com.satks.pars;

import com.satks.pars.Prometheus.Alert;
import com.satks.pars.Prometheus.AlertManager;
import com.satks.pars.Prometheus.AlertManager.Route;
import com.satks.pars.Prometheus.ThanosRuleParser;
import com.satks.pars.Prometheus.Node;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private VBox receiversBoxFx;
    @FXML
    private VBox rulesBoxFx;
    @FXML
    private HBox visualizeTableFx;
    
    
    private static AlertManager alertmanager;
    private ThanosRuleParser thanos;
    private List<AlertManager.Route> routes;
    private List<AlertManager.Receiver> receivers;
    private ArrayList<Alert> rules;
    private final static Node root = new Node("root");
    private static Node defaultReceiver = null;
    private static ArrayList<Alert> alertsList;
    private static ArrayList<AlertManager.Receiver> receiversList;
    private final static ArrayList<Node> receiverNodeList = new ArrayList<>();
    private String repeatInterval;
    private VBox receiverBox;
    private ImageView serviceImageView;
    private FileInputStream imageStream;

    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
       
       alertmanager = AlertManager.getInstance();
       defaultReceiver = new Node("Default Receiver "+alertmanager.getDefaultReceiver());
       repeatInterval = alertmanager.getRepeatInterval();
       thanos = ThanosRuleParser.getInstance();
       routes = alertmanager.getRoutes();
       receivers = alertmanager.getReceivers();
       rules = thanos.getAlerts();
       
       for (int i = 0; i < receivers.size(); i++) 
       {
           if (receivers.get(i).getService().equals("email_configs"))
           {
               try { 
                   imageStream = new FileInputStream("D:\\code\\GITHUB_Projects\\PARS\\res\\mail.png");
               } catch (FileNotFoundException ex) {
                   Logger.getLogger("Cannot open mail image");
               }
           }
           else if(receivers.get(i).getService().equals("pagerduty_configs"))
           {
               try {
                   imageStream = new FileInputStream("D:\\code\\GITHUB_Projects\\PARS\\res\\pagerduty.png");
               } catch (FileNotFoundException ex) {
                   Logger.getLogger("Cannot open pd image");
               }
           }
           else if(receivers.get(i).getService().equals("slack_configs"))
           {
               try {
                   imageStream = new FileInputStream("D:\\code\\GITHUB_Projects\\PARS\\res\\slack.png");
               } catch (FileNotFoundException ex) {
                   Logger.getLogger("Cannot open slack image");
               }
           }
           else if(receivers.get(i).getService().equals("webhook_configs"))
           {
               try {
                   imageStream = new FileInputStream("D:\\code\\GITHUB_Projects\\PARS\\res\\webhook.png");
               } catch (FileNotFoundException ex) {
                   Logger.getLogger("Cannot open webhook image");
               }
           }
           serviceImageView = new ImageView(new Image(imageStream));
           serviceImageView.setFitHeight(40);
           serviceImageView.setFitWidth(40);
           Label receiver = new Label(receivers.get(i).getReceiverName(), serviceImageView);
           receiver.setPrefWidth(500);
           receiver.setPrefHeight(500);
           receiver.getStyleClass().add("items");
           receiversBoxFx.getChildren().add(receiver);
       }
       
       for (int i = 0; i < rules.size(); i++) 
       {
           Label rule = new Label(rules.get(i).getAlertName());
           rule.getStyleClass().add("items");
           rule.setPrefWidth(500);
           rule.setPrefHeight(500);
           rulesBoxFx.getChildren().add(rule);
       }      
    }
    
    private void setReceiverNodes(List<AlertManager.Route> routes)
    {
        for(int i=0;i<routes.size();i++)
         {
            Route route = routes.get(i);
            Node receiverNameNode = new Node(route.getReceiverName());
            receiverNodeList.add(receiverNameNode);
            List<JSONObject> matcherConditions = route.getMatcherConditions();
            for(int j=0;j<matcherConditions.size();j++)
            {
                String labelKey = matcherConditions.get(j).keySet().toArray()[0].toString();
                String labelValue = matcherConditions.get(j).getString(labelKey);
                //System.out.println(labelKey);
                //System.out.println(labelValue);
                if (root.getChild(labelKey)!=null)
                {
                    if (root.getChild(labelKey).getChild(labelValue)!=null)
                    {
                        root.getChild(labelKey).getChild(labelValue).addChild(receiverNameNode);
                    }
                    else
                    {
                        Node labelValueNode = new Node(labelValue);
                        labelValueNode.addChild(receiverNameNode);
                        root.getChild(labelKey).addChild(labelValueNode);
                    }
                }
                else
                {
                    Node labelKeyNode = new Node(labelKey);
                    Node labelValueNode = new Node(labelValue);
                    labelValueNode.addChild(receiverNameNode);
                    labelKeyNode.addChild(labelValueNode);
                    root.addChild(labelKeyNode);
                }
            }
         }
    }
    
    private void setRulesNodes(ArrayList<Alert> alertsList)
    {
         for(int i=0;i<alertsList.size();i++)
         {
            Alert alert = alertsList.get(i);
            Node alertNameNode = new Node(alert.getAlertName());
            JSONObject labels = alert.getLabels();
            Set labelKeySet = labels.keySet();
            Boolean matched = false;
            for (Object labelKeyObject : labelKeySet )
            {
                String labelKey = labelKeyObject.toString();
                String labelValue = labels.getString(labelKey);
                //System.out.println(labelKey);
                //System.out.println(labelValue);
                
                if (root.getChild(labelKey)!=null)
                {
                    if (!root.getChild(labelKey).getChildren().isEmpty())
                    {
                        for (Node matcherRegexNode : root.getChild(labelKey).getChildren())
                        {
                            String matcherRegex = matcherRegexNode.getName();
                            System.out.println(matcherRegex);
                            if(matcherRegex.equals(labelValue) || labelValue.matches(matcherRegex))
                            {
                                System.out.println("matches with: "+matcherRegex);
                                if (!matcherRegexNode.getChildren().isEmpty())
                                {
                                    for (Node receiverNode : matcherRegexNode.getChildren())
                                    {    
                                        receiverNode.addChild(alertNameNode);
                                        matched = true;
                                    }
                                }
                                else
                                {
                                    System.out.println("No Receiver, but label key and label value is present!"+alertNameNode.getName());
                                }
                            }
                        } 
                    }
                    else
                    {
                        System.out.println("No label value, but label key is present!"+alertNameNode.getName());
                    }
                }
                else
                {
                    System.out.println("No label key!"+alertNameNode.getName());
                }
            }
            if(!matched)
            {
                defaultReceiver.addChild(alertNameNode);
            }
         }
    }
    
    private void map()
    {
        routes = alertmanager.getRoutes();
        alertsList = thanos.getAlerts();
         
        setReceiverNodes(routes);
        setRulesNodes(alertsList);
    }
    

        private void fillColumnReceivers()
    {
        for (int i = 0; i < receiverNodeList.size(); i++) 
        {
           Node receiver = receiverNodeList.get(i);
           receiverBox = new VBox();
           receiverBox.getStyleClass().add("columns");
           receiverBox.setMinWidth(300);
           receiverBox.setAlignment(Pos.TOP_CENTER);
           //receiverBox.setFillWidth(true);
           
           Label receiverLabel = new Label(receiver.getName());
           receiverLabel.getStyleClass().add("titles");
           receiverBox.getChildren().add(receiverLabel);
           
           for(Node x : receiver.getChildren())
           {
                Label alertLabel = new Label(x.getName());
                alertLabel.getStyleClass().add("items");
                alertLabel.setMinWidth(300);
                receiverBox.getChildren().add(alertLabel);
           }
           visualizeTableFx.getChildren().add(receiverBox);
         } 
    }
    
    private void fillColumnDefaultReceiver()
    {
        receiverBox = new VBox();
        receiverBox.getStyleClass().add("columns");
        receiverBox.setMinWidth(300);
        receiverBox.setAlignment(Pos.TOP_CENTER);
        Label receiverLabel = new Label(defaultReceiver.getName());
        receiverLabel.getStyleClass().add("titles");
        receiverBox.getChildren().add(receiverLabel);
        for(Node x : defaultReceiver.getChildren())
        {
                Label alertLabel = new Label(x.getName());
                alertLabel.getStyleClass().add("items");
                alertLabel.setMinWidth(300);
                receiverBox.getChildren().add(alertLabel);
        }
        visualizeTableFx.getChildren().add(receiverBox);
    }
    
    @FXML
    private void visualize(MouseEvent event)
    {
        map();
        fillColumnReceivers();
        fillColumnDefaultReceiver();  
    }
    
    
    
}
