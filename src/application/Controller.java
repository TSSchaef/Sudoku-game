package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Controller implements Initializable{

	@FXML
	private Stage stage;
	private Scene scene;
	private Parent root;
	@FXML
	private ComboBox<String> selector;
	@FXML
	private Label myLabel;
	
	private String[] difficulty = {"Beginner", "Intermediate", "Advanced", "Expert"};

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		selector.getItems().addAll(difficulty);
		selector.setOnAction(this::getDifficulty);
	}
	
	public void getDifficulty(ActionEvent e) 
	{
		String difficulty = selector.getValue();
		String description;
		
		switch(difficulty){
			case "Beginner":
				description = "Unlimited hints";
				break;
			case "Intermediate":
				description = "5 hints";
				break;
			case "Advanced":
				description = "3 hints";
				break;
			default:
				description = "No hints, the hardest challenge";
				break;
		}
		
		myLabel.setText(difficulty + ": " + description);
	}
	
	public void function(ActionEvent event) throws Exception
	{
			String difficulty = selector.getValue();
			
			if(difficulty != null){
				FXMLLoader loader = new FXMLLoader(getClass().getResource("GameScene.fxml"));
				root = loader.load();
			
				SudokuGame gameState = new SudokuGame(difficulty);
				
				SudokuController gameController = loader.getController();
				gameController.setupPuzzle(gameState, 0);
				stage = (Stage)((Node)event.getSource()).getScene().getWindow();
				scene = new Scene(root);
				String css = this.getClass().getResource("application.css").toExternalForm();
				scene.getStylesheets().add(css);
				stage.setScene(scene);
				stage.show();
			
			}else{
				myLabel.setText("To begin, first select a difficulty");
			}
	}
}
 