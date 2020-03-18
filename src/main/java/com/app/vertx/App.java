package com.app.vertx;

import com.timgroup.statsd.NonBlockingStatsDClient;
import com.timgroup.statsd.StatsDClient;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

/**
 * Hello world!
 *
 */
public class App 
{
    private static final StatsDClient statsd = new NonBlockingStatsDClient("nehomes.be","172.26.116.42", 30385);
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        Vertx vertx = Vertx.vertx();

        HttpServer httpServer = vertx.createHttpServer();

        Router router = Router.router(vertx);

        router
                .get("/api/test/:name")
                .handler(routingConext -> {
                    System.out.println("In my handler test");
                    String name = routingConext.request().getParam("name");
                    JsonObject json =  new JsonObject().put("message", name);
                    HttpServerResponse response  = routingConext.response();
                    statsd.increment("java.test.success");

                    //response.setChunked(true);
                    //response.write("Hey you there!");
                    response.putHeader("content-type","application/json");
                    response.end(json.encode());
                });

        httpServer.requestHandler(router::accept).listen(6002);
    }
}
