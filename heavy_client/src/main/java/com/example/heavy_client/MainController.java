package com.example.heavy_client;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;


import java.util.ResourceBundle;

public class MainController {

  @FXML
  private Label label1 ;
  @FXML
  private Label label2 ;

  // called by the FXML loader after the labels declared above are injected:
  public void initialize() {

    // do initialization and configuration work...

    // trivial example, could also be done directly in the fxml:
    label1.setText("Foo");
    label2.setText("Bar");
  }
}
