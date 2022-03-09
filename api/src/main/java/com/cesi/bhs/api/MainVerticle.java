package com.cesi.bhs.api;

import com.cesi.bhs.api.data.SimpleHttpResult;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.Json;
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

    // -- Set the routes in the router --
    // Main page
    router.get("/").handler(routingContext -> {
      routingContext.response()
        .putHeader("content-type", "application/json; charset=utf-8")
        .end("{\n" +
          "\"body\": \"Welcome on the BHS API, for more information about this please contact BHß Software or NegoSud\",\n" +
          "\"copyright\": \"BHẞ Software 2022\"\n" +
          "}");
    });

    // Authentication Handler routes
    router.post("/login").handler(AuthenticationHandler::login);
    router.post("/login/register").handler(AuthenticationHandler::register);


    /**
     * Current User handler routes : token must be checked before all actions on current user
     */
    router.get("/user").handler(AuthenticationHandler::checkToken);
    router.get("/user").handler(UserHandler::getCurrentUser);
    router.post("/user").handler(UserHandler::createUser);

    /**
     * User by id handler routes: token must be checked before all actions on specific user
     */
    router.get("/user/:id").handler(AuthenticationHandler::checkToken);
    router.get("/user/:id").handler(UserHandler::getUserById);
    router.put("/user/:id").handler(AuthenticationHandler::checkToken);
    router.put("/user/:id").handler(UserHandler::updateUserById);
    router.delete("/user/:id").handler(AuthenticationHandler::checkToken);
    router.delete("/user/:id").handler(UserHandler::removeUserById);

    /**
     * All Users handler routes : token must be checked before rendering all registered users
     * */
    router.get("/users").handler(AuthenticationHandler::checkToken);
    router.get("/users").handler(UserHandler::getAllUsers);

    // Demo validation token utilisateur.
    // On vérifie le token
    router.get("/login/validate-token").handler(AuthenticationHandler::checkToken);
    // Puis si le token est bon cette fonction là est appelée
    router.get("/login/validate-token").handler(routingContext -> {
      routingContext.response()
        .setStatusCode(200)
        .putHeader("content-type", "application/json; charset=utf-8")
        .end(Json.encodePrettily(new SimpleHttpResult(200, "Yop salut !👋👋👋👋")));
      return;
    });

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
