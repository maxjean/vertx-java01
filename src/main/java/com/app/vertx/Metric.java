import com.timgroup.statsd.NonBlockingStatsDClient;
import com.timgroup.statsd.StatsDClient;
import java.util.HashMap;



public class Metric {
    private static StatsDClient metric;

    private String prefix = "nehomes"+"."+"eai";
    private String serviceName = "missions";
    private String host = "172.26.116.42";
    private int port = 30385;

    public Metric(){
        metric = new NonBlockingStatsDClient(prefix,host,port);
    }


    private String getMetricName(String state, String name, HashMap<String,String> options){
        String metricName = serviceName+"."+name+"."+state;

        if(options.get("missionId") != null){
            metricName = metricName+"."+options.get("missionId");
        }

        return metricName;
    }

    public void success(String name, HashMap<String,String> options){
        metric.increment(getMetricName("success",name, options));
    }

    public void error(String name, HashMap<String,String> options){
        metric.increment(getMetricName("error",name, options));
    }

    public void fail(String name, HashMap<String,String> options){
        metric.increment(getMetricName("failed",name, options));
    }

}
