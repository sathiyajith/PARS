
package com.satks.pasv.Prometheus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.json.*;
import org.yaml.snakeyaml.Yaml;

public class AlertManager {
    
    public class Receiver
    {
        public String name;
        public String service;
        
        public Receiver(String name, String service)
        {
            this.name = name;
            this.service = service;
        }
        
        public String getReceiverName()
        {
            return this.name;
        }
        
    }        
    
    public class Route
    {
        public List<String> matcherConditions;
        public String receiverName;
        
        public Route(String receiverName, List<String> matcherConditions)
        {
            this.receiverName = receiverName;
            this.matcherConditions = matcherConditions;
        }
        
        public String getReceiverName()
        {
            return this.receiverName;
        }
        
    }
    
    private final List<Receiver> receivers = new ArrayList<>();
    private final List<Route> routes = new ArrayList<>();
    public String config;
    public String repeat_interval;
    public String group_interval;
    public String default_receiver;
    public final static AlertManager instance = new AlertManager();
        
   
    public void setConfig(String config)
    {
        this.config = config;
        ParseConfig();
        System.out.println(this.receivers);
        System.out.println(routes);
    }
    
    public String getConfig()
    {
        return this.config;
    }
    
    public void ParseConfig()
    {
        Yaml yaml = new Yaml();
        Map<String, Object> obj = yaml.load(this.config);
        JSONObject json = new JSONObject(obj);
        //System.out.println(json);    
        setReceivers(json);
        setRoutes(json);
    }
    
    public void setReceivers(JSONObject json)
    {
        JSONArray receiverArray = json.getJSONArray("receivers");
        for(int i=0;i<receiverArray.length();i++)
        {
            JSONObject receiverItem = receiverArray.getJSONObject(i);
            String receiverName = receiverItem.getString("name");
            String receiverService = receiverItem.keySet().toArray()[1].toString();
            receivers.add(new Receiver(receiverName,receiverService));
        }  
    }
    
    public void setRoutes(JSONObject json)
    {
        JSONArray routesArray = json.getJSONObject("route").getJSONArray("routes");
        for(int i=0;i<routesArray.length();i++)
        {
            JSONObject routeItem = routesArray.getJSONObject(i);
            String receiverName = routeItem.getString("receiver");
            JSONArray matchers = routeItem.getJSONArray("matchers");
            List<String> matcherConditions = new ArrayList<> ();
            for(int j=0;j<matchers.length();j++)
            {
                matcherConditions.add(matchers.getString(j));
            }
            //System.out.println(matcherConditions);
            //System.out.println(receiverService);
            routes.add(new Route(receiverName,matcherConditions));
        }  
    }
    
    public static AlertManager getInstance() {
        return instance;
    }
    
    public List<Receiver> getReceivers()
    {
        return receivers;
    }
    
    public List<Route> getRoutes()
    {
        return routes;
    }
    
}
