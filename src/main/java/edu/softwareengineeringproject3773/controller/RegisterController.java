package edu.softwareengineeringproject3773.controller;

import edu.softwareengineeringproject3773.view.*;
import edu.softwareengineeringprojectcs3773.ApplicationState;
import edu.softwareengineeringprojectcs3773.SceneNavigator;
import edu.softwareengineeringprojectcs3773.model.Account;
import edu.softwareengineeringprojectcs3773.service.AccountService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterController {
	@FXML TextField firstNameField, lastNameField, emailField, usernameField, phoneNumberField;
	@FXML PasswordField passwordField, confirmPasswordField;
	@FXML Button registerButton, backToLoginButton;
	@FXML Label registerMessageLabel;
	
	RegisterView registerView;
	AccountService accounts;
	
	public void initialize() {
		accounts = new AccountService();
		backToLoginButton.setOnAction(event -> SceneNavigator.showLogin());
		registerButton.setOnAction(event -> createAccount());
	}
	
	public void createAccount() {
		String firstName = firstNameField.getText(), lastName = lastNameField.getText();
		String email = emailField.getText(), username = usernameField.getText();
		String password = passwordField.getText(), confirmPassword = confirmPasswordField.getText();
		String phoneNumber = phoneNumberField.getText();
		
		if(firstName.isEmpty() || lastName.isEmpty()) {
			registerMessageLabel.setText("First and last name fields are required.");
			return;
		}else if(email.isEmpty() || phoneNumber.isEmpty()) {
			registerMessageLabel.setText("Email and phone number fields are required.");
			return;
		}else if(username.isEmpty()) {
			registerMessageLabel.setText("Username field is required.");
			return;
		}else if(password.isEmpty() || confirmPassword.isEmpty()) {
			registerMessageLabel.setText("Password field is required.");
			return;
		}
		
		if(accounts.findAccountByEmail(email) != null) {
			registerMessageLabel.setText("An account with this email already exists.");
			return;
		}else if(accounts.findAccountByUsername(username) != null) {
			registerMessageLabel.setText("An account with this username already exists.");
			return;
		}
		
		if(!(password.matches(confirmPassword))){
			registerMessageLabel.setText("The password and confirmation password do not match.");
			return;
		}
		
		accounts.registerAccount(username, email, password, phoneNumber);
		ApplicationState.setCurrentAccount(accounts.findAccountByEmail(email));
		SceneNavigator.showHome();
		
	}
	
	
}
