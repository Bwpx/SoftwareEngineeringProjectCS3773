module edu.softwareengineeringprojectcs3773 {
    requires javafx.controls;
    requires javafx.fxml;


    opens edu.softwareengineeringprojectcs3773 to javafx.fxml;
    exports edu.softwareengineeringprojectcs3773;
}