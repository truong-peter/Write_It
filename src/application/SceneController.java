package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SceneController {

	private Stage stage;
	private Scene scene;
	private Parent root;

	// Scene Controller is for switching scenes in the application

	public void switchToLoginScene(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("LoginScene.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setTitle("Login Screen");
		stage.setScene(scene);
		stage.show();
	}

	public void switchToMainMenuScene(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("MainMenuScene.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setTitle("Main Menu");
		stage.setScene(scene);
		stage.show();
	}

	public void switchToChangePasswordScene(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("ChangePasswordScene.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setTitle("Change Password");
		stage.setScene(scene);
		stage.show();
	}

	public void switchToCreateRecommendationScene(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("CreateRecommendationScene.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setTitle("Create Recommendation");
		stage.setScene(scene);
		stage.show();
	}

	public void switchToCreateRecommendationSceneFromText(MouseEvent e) throws IOException {
		root = FXMLLoader.load(getClass().getResource("CreateRecommendationScene.fxml"));
		stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setTitle("Create Recommendation");
		stage.setScene(scene);
		stage.show();
	}

	public void switchToDraftRecommendationScene(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("DraftRecommendationScene.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setTitle("Draft Recommendation");
		stage.setScene(scene);
		stage.show();
	}

	public void switchToSearchRecommendationScene(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("SearchRecommendationScene.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setTitle("Search Recommendation");
		stage.setScene(scene);
		stage.show();
	}

	public void switchToSearchRecommendationSceneFromText(MouseEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("SearchRecommendationScene.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setTitle("Search Recommendation");
		stage.setScene(scene);
		stage.show();
	}

	public void switchToEditRecommendationScene(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("EditRecommendationScene.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setTitle("Edit Recommendation");
		stage.setScene(scene);
		stage.show();
	}

	public void switchToAccountInfoScene(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("AccountInfoScene.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setTitle("Account Info");
		stage.setScene(scene);
		stage.show();
	}

	public void switchToAboutUsScene(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("AboutUsScene.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setTitle("About");
		stage.setScene(scene);
		stage.show();
	}
}