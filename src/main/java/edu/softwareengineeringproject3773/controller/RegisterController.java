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
	@FXML TextField firstNameField, lastNameField, emailField, usernameField;
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
		
		if(firstName.isEmpty() || lastName.isEmpty()) {
			registerMessageLabel.setText("Please fill in your full name.");
		}else if(email.isEmpty() || username.isEmpty()) {
			registerMessageLabel.setText("Please fill in your email and username.");
		}else if(password.isEmpty() || confirmPassword.isEmpty()) {
			registerMessageLabel.setText("Please fill in your password.");
		}
		
		Account account = accounts.findAccountByEmail(email);
		if(account != null) {
			registerMessageLabel.setText("An account with this email already exists.");
		}else if(accounts.findAccountByUsername(username) != null) {
			registerMessageLabel.setText("An account with this username already exists.");
		}else if(!(password.matches(confirmPassword))){
			registerMessageLabel.setText("The password and confirmation password do not match.");
		}else {
			accounts.registerAccount(username, email, password, "temp");
			ApplicationState.setCurrentAccount(accounts.findAccountByEmail(email));
			SceneNavigator.showHome();
		}
	}
	
	
}
