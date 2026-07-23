module edu.softwareengineeringprojectcs3773 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires java.sql;
    requires org.xerial.sqlitejdbc;

    opens edu.softwareengineeringprojectcs3773 to javafx.fxml;
    opens edu.softwareengineeringproject3773.controller to javafx.fxml;

    exports edu.softwareengineeringprojectcs3773;
    exports edu.softwareengineeringprojectcs3773.database;
}