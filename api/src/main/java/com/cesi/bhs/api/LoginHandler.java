package com.cesi.bhs.api;

import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;

public class LoginHandler {
  public static void getAll(RoutingContext routingContext) {
    routingContext.response()
      .putHeader("content-type", "application/json; charset=utf-8")
      .end(Json.encodePrettily("{test: test}"));
  }
}
