package com.satks.pars.Prometheus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.json.*;
import org.yaml.snakeyaml.Yaml;

public class AlertManager {

    public class Receiver {

        public String name;
        public String service;
        public Boolean continueVar;

        public Receiver(String name, String service) {
            this.name = name;
            this.service = service;
            this.continueVar = false;
        }

        public String getReceiverName() {
            return this.name;
        }

        public String getService() {
            return this.service;
        }
        
        public void setContinue()
        {
            this.continueVar = true;
        }
        
        public Boolean getContinue() {
            return this.continueVar;
        }
    }

    public class Route {

        public List<JSONObject> matcherConditions;
        public String receiverName;
        public Boolean continueVar;

        public Route(String receiverName, List<JSONObject> matcherConditions, Boolean continueVar) {
            this.receiverName = receiverName;
            this.matcherConditions = matcherConditions;
            this.continueVar = continueVar;
            
        }

        public String getReceiverName() {
            return this.receiverName;
        }

        public List<JSONObject> getMatcherConditions() {
            return this.matcherConditions;
        }
        
        public Boolean getContinue() {
            return this.continueVar;
        }
    }

    private final List<Receiver> receivers = new ArrayList<>();
    private final List<Route> routes = new ArrayList<>();
    public String config;
    public String repeatInterval;
    public String groupInterval;
    public String defaultReceiver;
    public final static AlertManager instance = new AlertManager();
    public static JSONObject json;
    private Map<String, Object> obj=null;
    private Boolean validity = false;
    private Yaml yaml;
    private JSONArray receiverArray;
    private JSONObject receiverItem;
    private String receiverName;
    private String receiverService;
    private JSONArray routesArray;
    private JSONObject routeItem;
    private JSONArray matchers;
    private List<JSONObject> matcherConditions;
    private String[] matcher ;
    private JSONObject matcherObject;
    private Boolean continueVar;
    

    public void setConfig(String config) {
        this.config = config;
        ParseConfig();
    }

    public String getConfig() {
        return this.config;
    }

    public String getDefaultReceiver() {
        return this.defaultReceiver;
    }

    public String getRepeatInterval() {
        return this.repeatInterval;
    }
    
    public Boolean getValidity()
    {
        return validity;
    }
    

    public void ParseConfig() {
        yaml = new Yaml();
        try {
            obj = yaml.load(this.config);
        } catch (Exception e) {
            System.out.println("Not Valid");
            this.validity = false;
        } finally {
            if (obj != null) {
                this.validity = true;
                json = new JSONObject(obj);
                System.out.println(json);    
                setReceivers(json);
                setRoutes(json); 
            }
        }
    }
    

    public void setReceivers(JSONObject json) {
        receiverArray = json.getJSONArray("receivers");
        for (int i = 0; i < receiverArray.length(); i++) {
            receiverItem = receiverArray.getJSONObject(i);
            receiverName = receiverItem.getString("name");
            receiverService = receiverItem.keySet().toArray()[1].toString();
            
            receivers.add(new Receiver(receiverName, receiverService));
        }
    }

    public void setRoutes(JSONObject json) {
        defaultReceiver = json.getJSONObject("route").getString("receiver");
        repeatInterval = json.getJSONObject("route").getString("repeat_interval");
        routesArray = json.getJSONObject("route").getJSONArray("routes");
        for (int i = 0; i < routesArray.length(); i++) {
            routeItem = routesArray.getJSONObject(i);
            receiverName = routeItem.getString("receiver");
            matchers = routeItem.getJSONArray("matchers");
            matcherConditions = new ArrayList<>();
            for (int j = 0; j < matchers.length(); j++) {
                matcher = matchers.getString(j).split("=");
                matcherObject = new JSONObject().put(matcher[0], matcher[1].replaceAll("~", "").replaceAll("\"", ""));
                matcherConditions.add(matcherObject);
            }
            if(routeItem.has("continue"))
            {
                if (routeItem.getBoolean("continue"))
                {
                    continueVar = true;
                }
                else
                {
                    continueVar = false;
                }
            }
            else
            {
                continueVar = false;
            }
            //System.out.println(matcherConditions);
            //System.out.println(receiverService);
            routes.add(new Route(receiverName, matcherConditions,continueVar));
        }
    }

    public static AlertManager getInstance() {
        return instance;
    }

    public List<Receiver> getReceivers() {
        return this.receivers;
    }

    public List<Route> getRoutes() {
        return this.routes;
    }

}
