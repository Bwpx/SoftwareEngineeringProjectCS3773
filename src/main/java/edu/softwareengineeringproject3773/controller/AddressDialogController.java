package edu.softwareengineeringproject3773.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddressDialogController {
    @FXML private ComboBox<String> addressTypeComboBox;
    @FXML private TextField addressLine1Field;
    @FXML private TextField addressLine2Field;
    @FXML private TextField cityField;
    @FXML private TextField stateField;
    @FXML private TextField zipField;
    @FXML private CheckBox defaultAddressCheckBox;
    @FXML private Button cancelButton;
    @FXML private Button saveButton;

    @FXML
    private void initialize() {
        addressTypeComboBox.getItems().setAll("Shipping", "Billing");
        addressTypeComboBox.getSelectionModel().selectFirst();
        cancelButton.setOnAction(event -> close());
        saveButton.setOnAction(event -> handleSave());

        stateField.textProperty().addListener((obs, oldValue, newValue) -> {
            String value = newValue.replaceAll("[^a-zA-Z]", "").toUpperCase();
            if (value.length() > 2) value = value.substring(0, 2);
            if (!value.equals(newValue)) stateField.setText(value);
        });
        zipField.textProperty().addListener((obs, oldValue, newValue) -> {
            String value = newValue.replaceAll("[^0-9]", "");
            if (value.length() > 5) value = value.substring(0, 5);
            if (!value.equals(newValue)) zipField.setText(value);
        });
    }

    private void handleSave() {
        if (addressLine1Field.getText().isBlank() || cityField.getText().isBlank()
                || stateField.getText().length() != 2 || !zipField.getText().matches("\\d{5}")) {
            addressLine1Field.requestFocus();
            return;
        }
        // Address persistence will be connected through the team's AddressService.
        close();
    }

    private void close() {
        ((Stage) cancelButton.getScene().getWindow()).close();
    }
}
