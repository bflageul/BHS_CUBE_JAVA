package com.cesi.bhs.api;

import com.cesi.bhs.api.data.Product;
import com.cesi.bhs.api.data.ProductImpl;
import com.cesi.bhs.api.product.RegistrationProduct;
import io.vertx.core.json.DecodeException;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

import static com.cesi.bhs.api.dao.Product.registerProduct;


public class ProductHandler {

  public static void createOneProduct(@NotNull RoutingContext routingContext) {

    try {
      final RegistrationProduct registrationProduct = Json.decodeValue(routingContext.getBodyAsString(), RegistrationProduct.class);

      Product product = new ProductImpl(registrationProduct);
      product = registerProduct(product);
      routingContext.response()
        .setStatusCode(201)
        .end(Json.encodePrettily(product));
    } catch (DecodeException e){
      routingContext.response()
        .setStatusCode(400)
        .putHeader("content-type" , "application/json; charset=utf-8")
        .end();

    } catch (SQLException e) {
      e.printStackTrace();
      routingContext.response()
        .setStatusCode(500)
        .putHeader("content-type" , "application/json; charset=utf-8")
        .end();

    }
  }

  public static void getAllProducts (@NotNull RoutingContext routingContext) {}

  public static void deleteOneProduct (@NotNull RoutingContext routingContext){}
}






