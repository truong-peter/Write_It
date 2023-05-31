package application;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class EditRecommendationController implements Initializable {
	private DatabaseManager db = new DatabaseManager();

	@FXML
	private Button exitButton;
	@FXML
	private Button editRecommendationButton;
	@FXML
	private TextField firstNameField;
	@FXML
	private TextField lastNameField;
	@FXML
	private ChoiceBox<String> genderChoiceBox;
	@FXML
	private TextField schoolNameField;
	@FXML
	private DatePicker datePicker;
	@FXML
	private ChoiceBox<String> programChoiceBox;
	@FXML
	private ChoiceBox<String> semesterChoiceBox;
	@FXML
	private TextField yearField;
	@FXML
	private ListView<String> courseListView;
	@FXML
	private HBox gradeHBox;
	@FXML
	private ListView<String> personalListView;
	@FXML
	private ListView<String> academicListView;
	@FXML
	private Map<String, TextArea> gradeTextAreas;

	public void initialize(URL location, ResourceBundle resources) {

		// Set choices for genderChoice
		genderChoiceBox.getItems().addAll("Male", "Female", "Non-Binary", "Other");

		// Set choices for programChoice
		programChoiceBox.getItems().addAll(db.getAllSingleStringVars("programs", "programName"));

		// Set choices for First Semester semesterChoice
		semesterChoiceBox.getItems().addAll(db.getAllSingleStringVars("semesters", "semesterName"));

		// Set other Courses by Professor #MULTI-SELECT BY LEFT CLICK + CONTROL
		courseListView.getItems().addAll(db.getAllSingleStringVars("courses", "courseName"));
		courseListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		gradeTextAreas = new HashMap<>();
		// Insert recommendation data from db
		int hash = db.getHashFromLast(db.getNameToEdit()); // get hash id of saved name to edit
		// insert existing recommendation data into fields
		firstNameField.setText(db.getSingleStringVarFromID("recommendations", "firstName", "hash", hash));
		lastNameField.setText(db.getNameToEdit());
		genderChoiceBox.setValue(db.getSingleStringVarFromID("recommendations", "gender", "hash", hash));
		schoolNameField.setText(db.getSingleStringVarFromID("recommendations", "schoolName", "hash", hash));
		datePicker.setValue(db.getDate(hash));
		programChoiceBox.setValue(db.getSingleStringVarFromID("recommendations", "program", "hash", hash));
		semesterChoiceBox.setValue(db.getSingleStringVarFromID("recommendations", "semester", "hash", hash));
		yearField.setText(db.getSingleStringVarFromID("recommendations", "year", "hash", hash));

		// Update choices if user selects / deselects choices
		courseListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			// Clear existing text areas from gradeVBox
			gradeHBox.getChildren().clear();

			// Add new text areas for the currently selected courses
			List<String> courses = courseListView.getSelectionModel().getSelectedItems();
			for (String course : courses) {
				TextArea gradeTextArea = new TextArea();
				gradeTextArea.setPromptText("Enter grade for " + course);
				gradeHBox.getChildren().add(gradeTextArea);
				gradeTextAreas.put(course, gradeTextArea);
			}
		});

		// Set List of Student's Personal Characteristics #MULTI-SELECT BY LEFT CLICK +
		// CONTROL
		personalListView.getItems().addAll(db.getAllSingleStringVars("characteristics", "description", "type", 0));
		personalListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		// Set List of Student's Academic Characteristics #MULTI-SELECT BY LEFT CLICK +
		// CONTROL
		academicListView.getItems().addAll(db.getAllSingleStringVars("characteristics", "description", "type", 1));
		academicListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

	}

	public void editRecommendation(ActionEvent e) throws IOException {

		// Get values from input fields
		String firstName = firstNameField.getText();
		String lastName = lastNameField.getText();
		String gender = genderChoiceBox.getValue();
		String schoolName = schoolNameField.getText();
		LocalDate selectedDate = datePicker.getValue();
		String program = programChoiceBox.getValue();
		String semester = semesterChoiceBox.getValue();
		String year = yearField.getText();

		// Loop through the gradeTextAreas map and get the grade for each course
		List<String> courses = courseListView.getSelectionModel().getSelectedItems();
		Map<String, String> grades = new HashMap<>();
		for (String course : courses) {
			TextArea gradeTextArea = gradeTextAreas.get(course);
			if (gradeTextArea != null) {
				String grade = gradeTextArea.getText();
				if (grade.isEmpty()) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Input Error");
					alert.setHeaderText(null);
					alert.setContentText("Please fill out all the fields!");
					alert.showAndWait();
					return;
				}
				grades.put(course, grade);
			}
		}

		List<String> personalCharacteristics = personalListView.getSelectionModel().getSelectedItems();
		List<String> academicCharacteristics = academicListView.getSelectionModel().getSelectedItems();

		// Check if any required fields are empty
		if (firstName.trim().isEmpty() || lastName.trim().isEmpty() || gender == null || gender.trim().isEmpty()
				|| schoolName.trim().isEmpty() || selectedDate == null || program == null || program.trim().isEmpty()
				|| semester == null || semester.trim().isEmpty() || year.trim().isEmpty() || courses.isEmpty()
				|| personalCharacteristics.isEmpty() || academicCharacteristics.isEmpty()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Input Error");
			alert.setHeaderText(null);
			alert.setContentText("Please fill out all the fields!");
			alert.showAndWait();
			return; // exit the function to prevent further execution
		}

		db.deleteRecommendation(db.getNameToEdit());
		db.addRecommendation(firstName, lastName, gender, schoolName, selectedDate, program, semester, year, courses,
				grades, personalCharacteristics, academicCharacteristics);

		SceneController sceneController = new SceneController();
		sceneController.switchToDraftRecommendationScene(e);
	}

	// Clear all selected Courses
	@FXML
	private void handleClearSelection() {
		courseListView.getSelectionModel().clearSelection();
		gradeHBox.getChildren().clear();
		gradeTextAreas.clear();
	}

	// When exit button is pressed
	// Return to main menu
	public void exitButtonAction(ActionEvent e) throws IOException {
		SceneController sceneController = new SceneController();
		sceneController.switchToSearchRecommendationScene(e);
	}

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
