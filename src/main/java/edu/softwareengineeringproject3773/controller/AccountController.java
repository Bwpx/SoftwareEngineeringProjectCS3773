package edu.softwareengineeringproject3773.controller;

import edu.softwareengineeringprojectcs3773.ApplicationState;
import edu.softwareengineeringprojectcs3773.SceneNavigator;
import edu.softwareengineeringprojectcs3773.model.Account;
import edu.softwareengineeringprojectcs3773.model.Address;
import edu.softwareengineeringprojectcs3773.repository.AddressRepository;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AccountController {
	@FXML private Button backHomeButton;
	@FXML private Button updateAccountButton;
	@FXML private Button addAddressButton;
	@FXML private TextField accountUsernameField;
	@FXML private TextField accountEmailField;
	@FXML private TextField accountPhoneField;
	@FXML private Label accountMessageLabel;
	@FXML private TableView<Address> addressTableView;
	@FXML private TableColumn<Address, String> addressActionsColumn;
	@FXML private TableColumn<Address, String> addressTypeColumn;
	@FXML private TableColumn<Address, String> addressValueColumn;

	private Account currentAccount;
	private AddressRepository addresses;


	public void initialize() {
		addresses = new AddressRepository();
		loadAccount();
		backHomeButton.setOnAction(event -> SceneNavigator.showHome());
		updateAccountButton.setOnAction(event -> updateAccount());
		addAddressButton.setOnAction(event -> SceneNavigator.showAddressDialogue());
		
		addressTypeColumn.setCellValueFactory(data ->
				new SimpleStringProperty(String.valueOf(data.getValue().getType())));
		addressValueColumn.setCellValueFactory(data ->
		new SimpleStringProperty(String.valueOf(data.getValue().toString())));
		addressActionsColumn.setCellValueFactory(data ->
		new SimpleStringProperty("Remove"));
		
		addressTableView.setItems(FXCollections.observableArrayList(addresses.findByAccountId(currentAccount.getAccountId())));
	}

	private void loadAccount() {
		currentAccount = ApplicationState.getCurrentAccount();
		if (currentAccount == null) {
			showMessage("Log in to manage your account.", true);
			updateAccountButton.setDisable(true);
			addAddressButton.setDisable(true);
			return;
		}

		accountUsernameField.setText(safe(currentAccount.getUsername()));
		accountEmailField.setText(safe(currentAccount.getEmail()));
		accountPhoneField.setText(safe(currentAccount.getPhoneNumber()));
	}


	private void updateAccount() {
		if (currentAccount == null) {
			showMessage("Log in to update your account.", true);
			return;
		}

		String username = accountUsernameField.getText().trim();
		String email = accountEmailField.getText().trim();
		String phone = accountPhoneField.getText().trim();

		if (username.isBlank() || email.isBlank() || phone.isBlank()) {
			showMessage("Username, email, and phone number are required.", true);
			return;
		}
		if (!email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
			showMessage("Enter a valid email address.", true);
			return;
		}
		if (!phone.matches("[0-9()\\-\\s+]{10,20}")) {
			showMessage("Enter a valid phone number.", true);
			return;
		}

		// Keep ApplicationState synchronized. Persistent account updates should be
		// delegated to AccountService once the update method is available.
		currentAccount.setUsername(username);
		currentAccount.setEmail(email);
		currentAccount.setPhoneNumber(phone);
		showMessage("Account information updated for this session.", false);
	}

	private void openAddressDialog() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(
					"/edu/softwareengineeringprojectcs3773/address-dialog.fxml"));
			Parent root = loader.load();
			Stage dialog = new Stage();
			dialog.initOwner(SceneNavigator.getPrimaryStage());
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.setTitle("Add Address");
			dialog.setResizable(false);
			dialog.setScene(new Scene(root));
			dialog.showAndWait();
		} catch (IOException | RuntimeException exception) {
			showMessage("The address form could not be opened.", true);
			exception.printStackTrace();
		}
	}

	private void showMessage(String message, boolean error) {
		accountMessageLabel.setText(message);
		accountMessageLabel.getStyleClass().removeAll("error-label", "success-label");
		accountMessageLabel.getStyleClass().add(error ? "error-label" : "success-label");
	}

	private String safe(String value) {
		return value == null ? "" : value;
	}


}
