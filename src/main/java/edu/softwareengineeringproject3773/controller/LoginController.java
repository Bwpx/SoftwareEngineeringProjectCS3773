package edu.softwareengineeringproject3773.controller;

import java.io.IOException;
import java.net.URISyntaxException;

import edu.softwareengineeringproject3773.view.LoginView;
import edu.softwareengineeringprojectcs3773.ApplicationState;
import edu.softwareengineeringprojectcs3773.SceneNavigator;
import edu.softwareengineeringprojectcs3773.model.Account;
import edu.softwareengineeringprojectcs3773.repository.AccountRepository;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
	@FXML Label loginMessageLabel;
	@FXML Button loginButton, openRegisterButton;
	@FXML TextField loginUsernameField;
	@FXML PasswordField loginPasswordField;


	private LoginView loginView;
	private AccountRepository accounts;

	
	public void initialize() throws URISyntaxException, IOException {
		accounts = new AccountRepository();
		
		loginView = new LoginView();
		loginView.setCreateAccountButton(openRegisterButton);
		loginView.setLoginButton(loginButton);
		loginView.setMessageLabel(loginMessageLabel);
		loginView.setUsernameField(loginUsernameField);
		loginView.setPasswordField(loginPasswordField);
		
		loginButton.setUserData("login");
		openRegisterButton.setUserData("create");
		//System.out.println(loginButton.getUserData());
	}


	
	public void createAccountAction(ActionEvent event) {
		SceneNavigator.showRegister();
	}
	
	public void loginAction(ActionEvent event) {
		String usernameOrEmail = loginUsernameField.getText();
		String password = loginPasswordField.getText();

		if (usernameOrEmail.isEmpty() || password.isEmpty()) {
			loginView.displayMessage("Please enter a username and password.");
			return;
		}

		Account account = accounts.findByUsername(usernameOrEmail);
		if (account == null) {
			account = accounts.findByEmail(usernameOrEmail);
		}

		if (account == null || !account.getPassword().equals(password)) {
			loginView.displayMessage("Invalid username/email or password.");
			return;
		}

		ApplicationState.setCurrentAccount(account);
		SceneNavigator.showHome();
	}
}

