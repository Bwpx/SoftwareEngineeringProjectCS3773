package edu.softwareengineeringproject3773.controller;

import edu.softwareengineeringproject3773.view.RegisterView;
import edu.softwareengineeringprojectcs3773.ApplicationState;
import edu.softwareengineeringprojectcs3773.SceneNavigator;
import edu.softwareengineeringprojectcs3773.service.AccountService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterController {
    @FXML
    TextField firstNameField, lastNameField, emailField,
            usernameField, phoneNumberField;

    @FXML
    PasswordField passwordField, confirmPasswordField;

    @FXML
    Button registerButton, backToLoginButton;

    @FXML
    Label registerMessageLabel;

    RegisterView registerView;
    AccountService accounts;

    public void initialize() {
        accounts = new AccountService();

        backToLoginButton.setOnAction(
                event -> SceneNavigator.showLogin()
        );

        registerButton.setOnAction(
                event -> createAccount()
        );
    }

    public void createAccount() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String phoneNumber = phoneNumberField.getText();

        if (firstName.isBlank() || lastName.isBlank()) {
            registerMessageLabel.setText(
                    "First and last name fields are required."
            );
            return;
        }

        if (email.isBlank() || phoneNumber.isBlank()) {
            registerMessageLabel.setText(
                    "Email and phone number fields are required."
            );
            return;
        }

        if (username.isBlank()) {
            registerMessageLabel.setText(
                    "Username field is required."
            );
            return;
        }

        if (password.isEmpty() || confirmPassword.isEmpty()) {
            registerMessageLabel.setText(
                    "Password field is required."
            );
            return;
        }

        if (accounts.findAccountByEmail(email) != null) {
            registerMessageLabel.setText(
                    "An account with this email already exists."
            );
            return;
        }

        if (accounts.findAccountByUsername(username) != null) {
            registerMessageLabel.setText(
                    "An account with this username already exists."
            );
            return;
        }

        if (!password.equals(confirmPassword)) {
            registerMessageLabel.setText(
                    "The password and confirmation password do not match."
            );
            return;
        }

        boolean registered = accounts.registerAccount(
                firstName,
                lastName,
                username,
                email,
                password,
                phoneNumber
        );

        if (!registered) {
            registerMessageLabel.setText(
                    "The account could not be created."
            );
            return;
        }

        ApplicationState.setCurrentAccount(
                accounts.findAccountByEmail(email)
        );

        SceneNavigator.showHome();
    }
}
