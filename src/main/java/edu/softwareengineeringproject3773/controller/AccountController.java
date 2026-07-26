package edu.softwareengineeringproject3773.controller;

import edu.softwareengineeringprojectcs3773.SceneNavigator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AccountController {
	@FXML Button backHomeButton, updateAccountButton, addAddressButton;
	@FXML TextField accountFirstNameField, accountLastNameField, accountEmailField;
	
	public void initialize() {
		backHomeButton.setOnAction(event -> SceneNavigator.showHome());
		updateAccountButton.setOnAction(event -> updateAccount());
		addAddressButton.setOnAction(event -> SceneNavigator.showAddressDialogue());
	}
	
	public void updateAccount() {
		String firstName = accountFirstNameField.getText();
		String lastName = accountLastNameField.getText();
		String email = accountEmailField.getText();
		
		
	}
}
