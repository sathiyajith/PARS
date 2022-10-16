
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
    private AlertManager.Receiver receiver;
    private ArrayList<Alert> rules;
    private final static Node root = new Node("root");
    private static Node defaultReceiver = null;
    private static ArrayList<Alert> alertsList;
    private final static ArrayList<Node> receiverNodeList = new ArrayList<>();
    private String repeatInterval;
    private VBox receiverBox;
    private ImageView serviceImageView;
    private FileInputStream imageStream;
    private Label receiverFx;
    private Label rule;
    private Route route;
    private Node receiverNameNode;
    private List<JSONObject> matcherConditions;
    private String labelKey;
    private String labelValue;
    private Node labelValueNode;
    private Node labelKeyNode;
    private Alert alert;
    private Node alertNameNode;
    private JSONObject labels;
    private Set labelKeySet;
    private Boolean isMatched;
    private String matcherRegex;
    private Node receiverNode;
    private Label receiverLabel;
    private Label alertLabel;
    private String receiverName;
    
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
           receiverFx = new Label(receivers.get(i).getReceiverName(), serviceImageView);
           receiverFx.setPrefWidth(500);
           receiverFx.setPrefHeight(500);
           receiverFx.getStyleClass().add("items");
           receiversBoxFx.getChildren().add(receiverFx);
       }
       
       for (int i = 0; i < rules.size(); i++) 
       {
           rule = new Label(rules.get(i).getAlertName());
           rule.getStyleClass().add("items");
           rule.setPrefWidth(500);
           rule.setPrefHeight(500);
           rulesBoxFx.getChildren().add(rule);
       }      
    }
    
    private AlertManager.Receiver findReceiverByName(String receiverName)
    {
        for (AlertManager.Receiver currReceiver : receivers) 
        {
            if (currReceiver.getReceiverName().equals(receiverName)) 
            {
                return currReceiver;
            }
        }
        return null;
    }
    
    private void setReceiverNodes(List<AlertManager.Route> routes)
    {
        for(int i=0;i<routes.size();i++)
         {
            route = routes.get(i);
            receiverName = route.getReceiverName();
            if(route.getContinue())
            {
                receiver = findReceiverByName(receiverName);
                receiver.setContinue();
            }
            receiverNameNode = new Node(receiverName);
            receiverNodeList.add(receiverNameNode);
            matcherConditions = route.getMatcherConditions();
            for(int j=0;j<matcherConditions.size();j++)
            {
                labelKey = matcherConditions.get(j).keySet().toArray()[0].toString();
                labelValue = matcherConditions.get(j).getString(labelKey);
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
                        labelValueNode = new Node(labelValue);
                        labelValueNode.addChild(receiverNameNode);
                        root.getChild(labelKey).addChild(labelValueNode);
                    }
                }
                else
                {
                    labelKeyNode = new Node(labelKey);
                    labelValueNode = new Node(labelValue);
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
            alert = alertsList.get(i);
            alertNameNode = new Node(alert.getAlertName());
            System.out.println(alertNameNode.getName());
            labels = alert.getLabels();
            labelKeySet = labels.keySet();
            isMatched = false;
            for (Object labelKeyObject : labelKeySet )
            {
                labelKey = labelKeyObject.toString();
                labelValue = labels.getString(labelKey);
                //System.out.println(labelKey);
                //System.out.println(labelValue);
                
                if (root.getChild(labelKey)!=null)
                {
                    if (!root.getChild(labelKey).getChildren().isEmpty())
                    {
                        for (Node matcherRegexNode : root.getChild(labelKey).getChildren())
                        {
                            matcherRegex = matcherRegexNode.getName();
                            if(matcherRegex.equals(labelValue) || labelValue.matches(matcherRegex))
                            {
                                if (!matcherRegexNode.getChildren().isEmpty())
                                {
                                    for (Node receiverNode : matcherRegexNode.getChildren())
                                    {    
                                        System.out.println(receiverNode.getName());
                                        receiverNode.addChild(alertNameNode);
                                        isMatched = true;
                                        receiver = findReceiverByName(receiverNode.getName());
                                        if(receiver.getContinue()==false && isMatched)
                                        {
                                            System.out.println("Continue condition breaks at the receiver level");
                                            break;
                                        }
                                    }
                                }
                                else
                                {
                                    System.out.println("No Receiver, but label key and label value is present!"+alertNameNode.getName());
                                }
                            }
                            if(receiver.getContinue()==false && isMatched)
                            {
                                System.out.println("Continue condition breaks at the label value level");
                                break;
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
                if(receiver.getContinue()==false && isMatched)
                {
                    System.out.println("Continue condition breaks at the label key level");
                    break;
                }
            }
            if(!isMatched)
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
           receiverNode = receiverNodeList.get(i);
           receiverBox = new VBox();
           receiverBox.getStyleClass().add("columns");
           receiverBox.setMinWidth(300);
           receiverBox.setAlignment(Pos.TOP_CENTER);
           //receiverBox.setFillWidth(true);
           
           receiverLabel = new Label(receiverNode.getName());
           receiverLabel.getStyleClass().add("titles");
           receiverBox.getChildren().add(receiverLabel);
           
           for(Node x : receiverNode.getChildren())
           {
                alertLabel = new Label(x.getName());
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
        receiverLabel = new Label(defaultReceiver.getName());
        receiverLabel.getStyleClass().add("titles");
        receiverBox.getChildren().add(receiverLabel);
        for(Node x : defaultReceiver.getChildren())
        {
                alertLabel = new Label(x.getName());
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
