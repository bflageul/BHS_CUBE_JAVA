package com.negosud.heavycli;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController {
  @FXML
  private Label label;

  @FXML
  private void handleButtonAction(ActionEvent event) {
    System.out.println("You clicked me!");
    label.setText("Hello World!");
  }


  public void initialize(URL url, ResourceBundle resources) {
    // Initialization code can go here.
    // The parameters url and resources can be omitted if they are not needed
  }

}
