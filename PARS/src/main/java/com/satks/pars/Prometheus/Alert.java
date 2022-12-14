
package com.satks.pars.Prometheus;

import org.json.JSONObject;

public class Alert {
    String alertName;
    JSONObject labels;
    int thresholdPeriod;
    
    public Alert(String alertName, JSONObject labels, int thresholdPeriod)
    {
        this.alertName = alertName;
        this.labels = labels;
        this.thresholdPeriod = thresholdPeriod;
    }
    
    public String getAlertName()
    {
        return this.alertName;
    }
    
    public JSONObject getLabels()
    {
        return this.labels;
    }
}
