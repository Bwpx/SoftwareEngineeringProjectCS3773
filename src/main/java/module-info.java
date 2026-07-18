module edu.softwareengineeringprojectcs3773 {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.base;


    opens edu.softwareengineeringprojectcs3773 to javafx.fxml;
    opens edu.softwareengineeringproject3773.controller to javafx.fxml;
    exports edu.softwareengineeringprojectcs3773;
}