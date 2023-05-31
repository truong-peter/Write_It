package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

public class LoginController {
	private DatabaseManager db = new DatabaseManager();
	@FXML
	private Button loginButton;
	@FXML
	private Label loginMessageLabel;
	@FXML
	private PasswordField passwordField;

	// When the login button is pressed
	public void loginButtonAction(ActionEvent e) throws IOException {
		// Receive user password input
		String inputPassword = passwordField.getText();
		// Verify password input
		if (this.validateLogin(inputPassword)) {
			// If password is correct
			// redirect user to main menu if it is not their first time logging on
			// else redirect them to change/make a new password
			SceneController sceneController = new SceneController();
			if (!isFirstTimeLogin()) {
				sceneController.switchToMainMenuScene(e);
			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("First Time Login");
				alert.setHeaderText(null);
				alert.setContentText("This is your first time logging in. Please change your password.");
				alert.showAndWait();
				sceneController.switchToChangePasswordScene(e);
			}
		} else {
			// Otherwise show error message
			this.loginButtonError();
		}
	}

	// Displays error message if:
	// Password field is blank
	// Wrong password
	public void loginButtonError() {
		if (passwordField.getText().isEmpty()) {
			loginMessageLabel.setText("Please enter a password!");
		} else {
			loginMessageLabel.setText("Incorrect Password!");
		}
	}

	// Check to see if the password is correct
	public boolean validateLogin(String inputPassword) {
		return db.getSingleStringVarFromID("password", "key", "id", 1).equals(inputPassword);
	}

	// Check if the user is logging in for the first time
	public boolean isFirstTimeLogin() {
		return db.getSingleStringVarFromID("password", "key", "id", 1).equals("p");
	}

}
