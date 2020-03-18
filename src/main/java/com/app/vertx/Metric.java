package com.app.vertx;
import com.timgroup.statsd.NonBlockingStatsDClient;
import com.timgroup.statsd.StatsDClient;
import io.vertx.core.json.JsonObject;

public class Metric {
    private static StatsDClient metric;

    private String prefix = "nehomes"+"."+"be";
    private String serviceName = "javapoc";
    private String host = "172.26.116.42";
    private int port = 30385;

    Metric(){
        metric = new NonBlockingStatsDClient(prefix,host,port);
    }


    private String getMetricName(String state, String name, JsonObject options){
        String metricName = serviceName+"."+name+"."+state;

        if(options.containsKey("missionId")){
            metricName = metricName+"."+options.getString("missionId");
        }

        return metricName;
    }

    public void success(String name, JsonObject options){
        metric.increment(getMetricName("success",name, options));

    }

    public void error(String name, JsonObject options){
        metric.increment(getMetricName("error",name, options));
    }

    public void fail(String name, JsonObject options){
        metric.increment(getMetricName("failed",name, options));
    }



}
