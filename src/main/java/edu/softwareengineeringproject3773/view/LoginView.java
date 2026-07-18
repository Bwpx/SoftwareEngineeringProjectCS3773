package edu.softwareengineeringproject3773.view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginView {
	Label label;
	Button login, create;
	TextField username;
	PasswordField password;
	
	public void setLoginButton(Button login) {
		this.login = login;
	}
	
	public void setCreateAccountButton(Button create) {
		this.create = create;
	}
	
	public void setMessageLabel(Label label) {
		this.label = label;
	}
	
	public void setUsernameField(TextField username) {
		this.username = username;
	}
	
	public void setPasswordField(PasswordField password) {
		this.password = password;
	}
	
	public void displayMessage(String text) {
		label.setText(text);
	}
}
