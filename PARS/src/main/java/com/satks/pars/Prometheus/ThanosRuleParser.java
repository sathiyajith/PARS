
package com.satks.pars.Prometheus;

import java.util.ArrayList;
import org.json.JSONObject;
import org.yaml.snakeyaml.Yaml;

public class ThanosRuleParser {
       
    public String rulefile;
    public ArrayList<Alert> alertsList = new ArrayList<>();
    public final static ThanosRuleParser instance = new ThanosRuleParser();
    
  
    public void setRule(String rulefile)
    {
        ParseConfig(removeUnwantedLines(rulefile));
    }
    
    public ArrayList<Alert> getAlerts()
    {
        return this.alertsList;
    }
    
    
    public String removeUnwantedLines(String rules)
    {
        //String text = rules.replaceAll("(?m)expr:.*|(?m)#.*", "").replaceAll("(?m)annotations:.*", "").replaceAll("(?m)summary:.*", "").replaceAll("(?m)description:.*", "").replaceAll("(?m)VALUE.*", "").replaceAll("(?m)LABELS.*", "").replaceAll("(?m)^[ \\t]*\\r?\\n","");
        String text = rules.replaceAll("(?m)expr:.*|(?m)#.*|(?m)annotations:.*|(?m)summary:.*|(?m)description:.*|(?m)VALUE.*|(?m)LABELS.*", "").replaceAll("(?m)^[ \\t]*\\r?\\n","");
        //System.out.println(text);
        return text;
    }
    
    
    
    public void ParseConfig(String rulefile)
    {
        Yaml yaml = new Yaml();
        ArrayList obj = yaml.load(rulefile);
        //System.out.println(obj);
        
        for(int i=0;i<obj.size();i++)
        {
            JSONObject alertObject = new JSONObject(obj.get(i).toString().replaceAll("=",":"));
            String alertName = alertObject.getString("alert");
            int thresholdPeriod = Integer.parseInt(alertObject.getString("for").replace("m",""));
            JSONObject labels = alertObject.getJSONObject("labels");
            //System.out.println(alertName);
            //System.out.println(thresholdPeriod);
            //System.out.println(labels);
            //System.out.println("--------------------");
            alertsList.add(new Alert(alertName, labels, thresholdPeriod));
        }
        
        System.out.println(alertsList);
        
    }
    
    public static ThanosRuleParser getInstance() {
        return instance;
    }
    
}
