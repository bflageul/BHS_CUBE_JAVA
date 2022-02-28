package com.cesi.bhs.api;

import static com.cesi.bhs.api.dao.Product.registerProduct;

import com.cesi.bhs.api.data.Product;
import com.cesi.bhs.api.data.ProductImpl;
import com.cesi.bhs.api.product.RegistrationProduct;
import io.vertx.core.json.DecodeException;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;

import java.sql.SQLException;


public class ProductHandler {

  public static void createOneProduct(final RoutingContext routingContext) {

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




  /**public static void getOneProduct(final RoutingContext routingContext) {

    final Integer id = routingContext.request().getParam(id);
    if (id != null) {
      final Product product = ProductCrudService.findById(id);
    }
    if (id == null){
      routingContext.response().end("Produit id ="+id,"introuvable");
    }
  }

  public static void updateOneProduct(RoutingContext routingContext){
    final Integer id = routingContext.request().getParam(id);

    final JsonObject body = routingContext.getBodyAsJson();

    final Integer id = body.getInteger("id");
    final String name = body.getString("name");
    final Integer stock = body.getInteger("stock");
    final String description = body.getString("description");
    final String type = body.getString("type");
    final String origin = body.getString("origin");
    final String medal = body.getString("medal");
    final String productorname = body.getString("productorname");

    final Product product = new Product(id, name, stock, description, type, origin, medal, productorname)
    final Product updatedProduct = ProductCrudService.update(product);
    routingContext.response()
      .setStatusCode(200)
      .putHeader("content-type", "application/json")
      .end(Json.encode(updatedProduct));
  }

  public static  void getAllProducts(RoutingContext routingContext){
    final List<Product> products =ProductCrudService.getAll();


  }
  public static void deleteOneProduct(RoutingContext routingContext){
    final Integer id = routingContext.request().getParam(id);
        ProductCrudService.remove(id);
    routingContext.response()
      .setStatusCode(200)
      .putHeader("content-type", "application/json")
      .end();
  }**/
}






