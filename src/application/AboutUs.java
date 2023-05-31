package application;

import java.io.IOException;

import javafx.event.ActionEvent;

public class AboutUs {

	// When home button is pressed
	// Return to main menu screen
	public void homeButtonAction(ActionEvent e) throws IOException {
		SceneController sceneController = new SceneController();
		sceneController.switchToMainMenuScene(e);
	}

	// When account button is pressed
	// Refresh current page
	public void accountButtonAction(ActionEvent e) throws IOException {
		SceneController sceneController = new SceneController();
		sceneController.switchToAccountInfoScene(e);
	}

	// When logout button is pressed
	// Return to log-in screen
	public void logoutButtonAction(ActionEvent e) throws IOException {
		SceneController sceneController = new SceneController();
		sceneController.switchToLoginScene(e);
	}

	// When about us button is pressed
	// Redirect to About Us page
	public void aboutUsButtonAction(ActionEvent e) throws IOException {
		SceneController sceneController = new SceneController();
		sceneController.switchToAboutUsScene(e);
	}
}
