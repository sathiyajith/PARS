package com.satks.pars.Prometheus;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import org.yaml.snakeyaml.Yaml;

public class ThanosRuleParser {

    public String rulefile;
    public ArrayList<Alert> alertsList = new ArrayList<>();
    public final static ThanosRuleParser instance = new ThanosRuleParser();
    private ArrayList obj;
    private static  List<Boolean> validity = new ArrayList<>();
    private String text;
    private Yaml yaml;
    private JSONObject alertObject;
    private String alertName;
    private int thresholdPeriod;
    private JSONObject labels;

    public void setRule(String rulefile) {
        ParseConfig(removeUnwantedLines(rulefile));
    }

    public ArrayList<Alert> getAlerts() {
        return this.alertsList;
    }
    
    public static List<Boolean> getValidity()
    {
        return validity;
    }
    

    public String removeUnwantedLines(String rules) {
        //String text = rules.replaceAll("(?m)expr:.*|(?m)#.*", "").replaceAll("(?m)annotations:.*", "").replaceAll("(?m)summary:.*", "").replaceAll("(?m)description:.*", "").replaceAll("(?m)VALUE.*", "").replaceAll("(?m)LABELS.*", "").replaceAll("(?m)^[ \\t]*\\r?\\n","");
        text = rules.replaceAll("(?m)expr:.*|(?m)#.*|(?m)annotations:.*|(?m)summary:.*|(?m)description:.*|(?m)VALUE.*|(?m)LABELS.*", "").replaceAll("(?m)^[ \\t]*\\r?\\n", "");
        //System.out.println(text);
        return text;
    }

    public void ParseConfig(String rulefile) {
        yaml = new Yaml();
        try {
            obj = yaml.load(rulefile);
        } catch (Exception e) {
            System.out.println("Not Valid");
            validity.add(Boolean.FALSE);
        } finally {
            if (obj != null) {
                validity.add(Boolean.TRUE);
                //System.out.println(obj);
                for (int i = 0; i < obj.size(); i++) {
                    alertObject = new JSONObject(obj.get(i).toString().replaceAll("=", ":"));
                    alertName = alertObject.getString("alert");
                    thresholdPeriod = Integer.parseInt(alertObject.getString("for").replace("m", ""));
                    labels = alertObject.getJSONObject("labels");
                    //System.out.println(alertName);
                    //System.out.println(thresholdPeriod);
                    //System.out.println(labels);
                    //System.out.println("--------------------");
                    alertsList.add(new Alert(alertName, labels, thresholdPeriod));
                }

                System.out.println(alertsList);
            }
        }

    }

    public static ThanosRuleParser getInstance() {
        return instance;
    }

}
