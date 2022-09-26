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

        public Receiver(String name, String service) {
            this.name = name;
            this.service = service;
        }

        public String getReceiverName() {
            return this.name;
        }

        public String getService() {
            return this.service;
        }
    }

    public class Route {

        public List<JSONObject> matcherConditions;
        public String receiverName;

        public Route(String receiverName, List<JSONObject> matcherConditions) {
            this.receiverName = receiverName;
            this.matcherConditions = matcherConditions;
        }

        public String getReceiverName() {
            return this.receiverName;
        }

        public List<JSONObject> getMatcherConditions() {
            return this.matcherConditions;
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
        Yaml yaml = new Yaml();
        try {
            obj = yaml.load(this.config);
        } catch (Exception e) {
            System.out.println("Not Valid");
            this.validity = false;
        } finally {
            if (obj != null) {
                this.validity = true;
                json = new JSONObject(obj);
                //System.out.println(json);    
                setReceivers(json);
                setRoutes(json); 
            }
        }
    }
    

    public void setReceivers(JSONObject json) {
        JSONArray receiverArray = json.getJSONArray("receivers");
        for (int i = 0; i < receiverArray.length(); i++) {
            JSONObject receiverItem = receiverArray.getJSONObject(i);
            String receiverName = receiverItem.getString("name");
            String receiverService = receiverItem.keySet().toArray()[1].toString();
            receivers.add(new Receiver(receiverName, receiverService));
        }
    }

    public void setRoutes(JSONObject json) {
        defaultReceiver = json.getJSONObject("route").getString("receiver");
        repeatInterval = json.getJSONObject("route").getString("repeat_interval");
        JSONArray routesArray = json.getJSONObject("route").getJSONArray("routes");
        for (int i = 0; i < routesArray.length(); i++) {
            JSONObject routeItem = routesArray.getJSONObject(i);
            String receiverName = routeItem.getString("receiver");
            JSONArray matchers = routeItem.getJSONArray("matchers");
            List<JSONObject> matcherConditions = new ArrayList<>();
            for (int j = 0; j < matchers.length(); j++) {
                String[] matcher = matchers.getString(j).split("=");
                JSONObject matcherObject = new JSONObject().put(matcher[0], matcher[1].replaceAll("~", "").replaceAll("\"", ""));
                matcherConditions.add(matcherObject);
            }
            //System.out.println(matcherConditions);
            //System.out.println(receiverService);
            routes.add(new Route(receiverName, matcherConditions));
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
