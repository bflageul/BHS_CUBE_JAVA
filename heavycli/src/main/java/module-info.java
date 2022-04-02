module com.cesi.bhs.heavycli {
  requires javafx.controls;
  requires javafx.fxml;

  opens com.cesi.bhs.heavycli to javafx.fxml;
  exports com.cesi.bhs.heavycli;
}
