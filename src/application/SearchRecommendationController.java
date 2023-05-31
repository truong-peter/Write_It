package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class SearchRecommendationController implements Initializable {
	private DatabaseManager db;
	private List<String> lastName = new ArrayList<String>();
	@FXML
	private Button exitButton;
	@FXML
	private TextField searchField;
	@FXML
	private Button searchButton;
	@FXML
	private Button editButton;
	@FXML
	private Button deleteButton;
	@FXML
	private ListView<String> recommendationListView;

	String selectedItem;

	// Initializes the results that show up in the ListView
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		db = new DatabaseManager();
		lastName = db.getAllSingleStringVars("recommendations", "lastName");
		recommendationListView.getItems().addAll(lastName);
		
		// Listener for selecting in the ListView
		recommendationListView.getSelectionModel().selectedItemProperty()
				.addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
					// Your action here
					selectedItem = recommendationListView.getSelectionModel().getSelectedItem();
				});
	}

	// When search button is pressed
	// Update the search results
	public void searchButtonAction(ActionEvent e) throws IOException {
		recommendationListView.getItems().clear();
		recommendationListView.getItems().addAll(searchList(searchField.getText(), lastName));
	}

	// Helper method
	// Filters and returns the search results in a list
	private List<String> searchList(String searchField, List<String> listOfStrings) {

		List<String> searchWordsArray = Arrays.asList(searchField.trim().split(" "));

		return listOfStrings.stream().filter(input -> {
			return searchWordsArray.stream().allMatch(word -> input.toLowerCase().contains(word.toLowerCase()));
		}).collect(Collectors.toList());
	}

	// When edit button is pressed
	// Open edit recommendation menu
	// Edit recommendation in database
	public void editButtonAction(ActionEvent e) throws IOException {
		if (selectedItem == null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Selection Error");
			alert.setHeaderText(null);
			alert.setContentText("Select a recommendation before editing!");
			alert.showAndWait();
		} else
			editRecommendation(e);
	}

	public void editRecommendation(ActionEvent e) throws IOException {
		db.setNameToEdit(selectedItem); // pass name to edit to DatabaseManager
		db.closeConnection();
		SceneController sceneController = new SceneController();
		sceneController.switchToEditRecommendationScene(e);
	}

	// When delete button is pressed
	// Delete recommendation from database
	public void deleteButtonAction(ActionEvent e) throws IOException {
		if (selectedItem == null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Selection Error");
			alert.setHeaderText(null);
			alert.setContentText("Select a recommendation before deleting!");
			alert.showAndWait();
		} else
			deleteRecommendation();
	}

	// Helper method that displays delete confirmation
	// If OK, then delete recommendation from database
	public void deleteRecommendation() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirm");
		alert.setHeaderText(null);
		alert.setContentText("Are you sure you want to delete " + selectedItem + "'s recommendation?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			db.deleteRecommendation(selectedItem);
			lastName.remove(selectedItem);
			recommendationListView.getItems().clear();
			recommendationListView.getItems().addAll(searchList(searchField.getText(), lastName));
		}
	}

	// When exit button is pressed
	// Return to main menu
	public void exitButtonAction(ActionEvent e) throws IOException {
		db.closeConnection();
		SceneController sceneController = new SceneController();
		sceneController.switchToMainMenuScene(e);
	}

	// When home button is pressed
	public void homeButtonAction(ActionEvent e) throws IOException {
		SceneController sceneController = new SceneController();
		sceneController.switchToMainMenuScene(e);
	}

	// Switch to account info scene
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