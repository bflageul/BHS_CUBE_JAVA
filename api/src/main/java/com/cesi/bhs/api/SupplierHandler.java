package com.cesi.bhs.api;

import com.cesi.bhs.api.data.Supplier;
import com.cesi.bhs.api.data.SupplierImpl;
import com.cesi.bhs.api.supplier.RegistrationSupplier;
import io.vertx.core.json.DecodeException;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

import static com.cesi.bhs.api.dao.Supplier.registerSupplier;



public class SupplierHandler {

    public static void createOneSupplier(@NotNull RoutingContext routingContext) {

      try {
        final RegistrationSupplier registrationSupplier = Json.decodeValue(routingContext.getBodyAsString(), RegistrationSupplier.class);

        Supplier supplier = new SupplierImpl(registrationSupplier);
        supplier = registerSupplier(supplier);
        routingContext.response()
          .setStatusCode(201)
          .end(Json.encodePrettily(supplier));
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








}
