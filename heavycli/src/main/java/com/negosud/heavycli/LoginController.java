package com.negosud.heavycli;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static javafx.fxml.FXMLLoader.load;

public class LoginController implements Initializable {

  @FXML
  private Label lbl1,lbl2;

  @FXML
  private Button btn1,btn2;

  @FXML
  private void handleButtonAction (ActionEvent event) throws Exception {
    Stage stage;
    Parent root;

    if(event.getSource()==btn1){
      stage = (Stage) btn1.getScene().getWindow();
      root = load(getClass().getResource("index.fxml"));
    }
    else{
      stage = (Stage) btn2.getScene().getWindow();
      root = load(getClass().getResource("login.fxml"));
    }
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // TODO
  }


}

