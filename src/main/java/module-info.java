module edu.softwareengineeringprojectcs3773 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires java.sql;
    requires org.xerial.sqlitejdbc;

    opens edu.softwareengineeringprojectcs3773 to javafx.fxml;
    opens edu.softwareengineeringproject3773.controller to javafx.fxml;

    opens edu.softwareengineeringprojectcs3773.service
            to org.junit.platform.commons;

    opens edu.softwareengineeringprojectcs3773.repository
            to org.junit.platform.commons;

    opens edu.softwareengineeringprojectcs3773.database
            to org.junit.platform.commons, javafx.fxml;

    exports edu.softwareengineeringprojectcs3773;
    exports edu.softwareengineeringprojectcs3773.database;
}