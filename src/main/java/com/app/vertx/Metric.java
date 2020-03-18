package com.app.vertx;
import com.timgroup.statsd.NonBlockingStatsDClient;
import com.timgroup.statsd.StatsDClient;

public class Metric {
    private static StatsDClient metric;

    private String prefix = "nehomes"+"."+"be";
    private String serviceName = "javapoc";
    private String host = "172.26.116.42";
    private int port = 30385;

    Metric(){
        metric = new NonBlockingStatsDClient(prefix,host,port);
    }


    private String getMetricName(String state, String name){
        String metricName = serviceName+"."+name+"."+state;
        return metricName;
    }

    public void success(String name){
        metric.increment(getMetricName("success",name));

    }

    public void error(String name){
        metric.increment(getMetricName("error",name));
    }

    public void fail(String name){
        metric.increment(getMetricName("failed",name));
    }



}
