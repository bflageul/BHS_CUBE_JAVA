package com.cesi.bhs.api;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;

public class MainVerticle extends AbstractVerticle {
  @Override
  public void start() throws Exception {
    // Create a Router
    Router router = Router.router(vertx);

    // Allow POST requests on all urls
    router.route().handler(BodyHandler.create());

    // Set the routes in the router
    router.route("/product").handler(ProductHandler::createOneProduct);
    router.route("/product/").handler(ProductHandler::getOneProduct);
    router.route("/product/").handler(ProductHandler::updateOneProduct);
    // Serve static resources from the /assets directory
    router.route("/assets/*").handler(StaticHandler.create("assets"));

    // Create the HTTP server and show it port to the console
    vertx.createHttpServer()
      .requestHandler(router)
      .listen(config().getInteger("http.port", 8888))
      .onSuccess(server ->
        System.out.println(
          "BHS API listening on http://localhost:" + server.actualPort() + "/"
        )
      );
  }
}
