package edu.softwareengineeringproject3773.controller;

import java.io.IOException;
import java.net.URISyntaxException;

import edu.softwareengineeringproject3773.view.*;
import edu.softwareengineeringprojectcs3773.repository.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
	@FXML Label loginMessageLabel;
	@FXML Button loginButton, openRegisterButton;
	@FXML TextField loginUsernameField;
	@FXML PasswordField loginPasswordField;
	
	Stage stage;
	Scene registerScene;
	Scene homeScene;
	LoginView loginView;
	AccountRepository accounts;
	
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
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	public void setRegisterScene(Scene registerScene) {
		this.registerScene = registerScene;
	}
	
	public void setHomeScene(Scene homeScene) {
		this.homeScene = homeScene;
	}
	
	public void createAccountAction(ActionEvent event) {
		Button button = (Button) (event.getTarget());
		String data = (String) (button.getUserData());
		loginView.displayMessage(data);
		stage.setScene(registerScene);
	}
	
	public void loginAction(ActionEvent event) {
		//System.out.println("test");
		Button button = (Button) (event.getTarget());
		String data = (String) (button.getUserData());
		loginView.displayMessage(data);
		
		String username = loginUsernameField.getText();
		String password = loginPasswordField.getText();
		if(username.isEmpty() || password.isEmpty()) {
			loginView.displayMessage("Please enter a username and password.");
		}else if(!(accounts.emailExists(username)) || !(accounts.usernameExists(username))) {
			loginView.displayMessage("Invalid username or email.");
			stage.setScene(homeScene);
		}
	}
}
