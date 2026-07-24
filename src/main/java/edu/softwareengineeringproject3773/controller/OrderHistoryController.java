package edu.softwareengineeringproject3773.controller;

import edu.softwareengineeringprojectcs3773.SceneNavigator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class OrderHistoryController {
	@FXML Button homeButton;
	
	public void initialize() {
		homeButton.setOnAction(event -> SceneNavigator.showHome());
	}
}
