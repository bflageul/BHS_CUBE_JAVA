package com.cesi.bhs.api;

import com.cesi.bhs.api.db.Product;
import com.cesi.bhs.api.db.ProductImpl;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.sql.Connection;
import java.sql.DriverManager;

public class ProductHandler {

  public static void createOneProduct(final RoutingContext routingContext) {
    final JsonObject body = routingContext.getBodyAsJson();

    final Integer id = body.getInteger("id");
    final String name = body.getString("name");
    final Integer stock = body.getInteger("stock");
    final String description = body.getString("description");
    final String type = body.getString("type");
    final String origin = body.getString("origin");
    final String medal = body.getString("medal");
    final String productorname = body.getString("productorname");

    final Product product = new Product(id, name, stock, description, type, origin, medal, productorname);
    final Product createdProduct = ProductCrudService.add(product);
    routingContext.response()
      .setStatusCode(201)
      .putHeader("contentType", "appllication/json")
      .end(Json.encode(createdProduct));

  }

  public static void getOneProduct(final RoutingContext routingContext) {

    final Integer id = routingContext.request().getParam(id);
    if (id != null) {
      final Product product = ProductCrudService.findById(id);
    }
    if (id == null){
      routingContext.response().end("Produit id ="+id,"introuvable");
    }
  }

  public static void updateOneProduct(RoutingContext routingContext){

  }
}






