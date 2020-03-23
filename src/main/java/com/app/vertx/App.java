package com.app.vertx;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import java.util.IntSummaryStatistics;
import java.util.stream.IntStream;
import java.util.HashMap;


/**
 * Hello world!
 *
 */
public class App 
{
    private static final Metric metric = new Metric();
    public static void main( String[] args )
    {
        try {
            System.out.println("Hello World!");
            Vertx vertx = Vertx.vertx();

            HttpServer httpServer = vertx.createHttpServer();

            Router router = Router.router(vertx);

            router
                    .get("/api/test/:name")
                    .handler(routingConext -> {
                        String name = routingConext.request().getParam("name");
                        JsonObject data = new JsonObject();
                        int[] numbers = {4, 1, 13, 90, 16, 2, 0};
                        IntSummaryStatistics statistics = IntStream.of(numbers).summaryStatistics();
                        JsonObject json = new JsonObject().put("message", statistics.getCount());
                        HttpServerResponse response = routingConext.response();

                        HashMap<String, String> options = new HashMap<String, String>();
                        options.put("missionId","12345");

                        metric.success("sendMission",options);
                        metric.error("testapi", options2);

                        metric.fail("sendMission",options);

                        response.putHeader("content-type", "application/json");
                        response.end(json.encode());

                    });

            httpServer.requestHandler(router::accept).listen(6002);
        }catch(Exception e){
            System.out.println("An error has occured "+ e);
        }
    }
}
