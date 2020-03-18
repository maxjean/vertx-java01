package com.app.vertx;

import com.timgroup.statsd.NonBlockingStatsDClient;
import com.timgroup.statsd.StatsDClient;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import com.app.vertx.Metric;

/**
 * Hello world!
 *
 */
public class App 
{
    private static final Metric metric = new Metric();
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        Vertx vertx = Vertx.vertx();

        HttpServer httpServer = vertx.createHttpServer();

        Router router = Router.router(vertx);

        router
                .get("/api/test/:name")
                .handler(routingConext -> {
                    String name = routingConext.request().getParam("name");
                    JsonObject data = new JsonObject();
                    JsonObject json =  new JsonObject().put("message", name);
                    HttpServerResponse response  = routingConext.response();

                    metric.success("testapi",data);
                    metric.error("testapi",data);
                    metric.fail("testapi",data.put("missionId","1234"));

                    response.putHeader("content-type","application/json");
                    response.end(json.encode());
                });

        httpServer.requestHandler(router::accept).listen(6002);
    }
}
