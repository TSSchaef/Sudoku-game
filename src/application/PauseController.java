package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class PauseController{
	
	@FXML	
	private Stage stage;
	private Scene scene;
	private Parent root;
	@FXML
	private Label TimerBox;
	@FXML
	private CheckBox hideTimer;
	
	private int Time;
	private SudokuGame gameState;
	
	public void holdState(SudokuGame gameState, int time)
	{
		Time = time;
		this.gameState = gameState;
		TimerBox.setText(Utility.formatTime(Time));
		hideTimer.setSelected(Time < 0);
	}
	
	public void hideTimer(ActionEvent event) {
		Time *= (-1);
		TimerBox.setText(Utility.formatTime(Time));
	}
	
	public void resume(ActionEvent event) throws Exception
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("GameScene.fxml"));
		root = loader.load();
	
		SudokuController gameController = loader.getController();
		gameController.setupPuzzle(gameState, Time);
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		String css = this.getClass().getResource("application.css").toExternalForm();
		scene.getStylesheets().add(css);
		stage.setScene(scene);
		stage.show();
	}
	
	public void restart(ActionEvent event) throws Exception
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("GameScene.fxml"));
		root = loader.load();
	
		SudokuController gameController = loader.getController();
		
		gameState.restartGame();
		if(Time >= 0){
			gameController.setupPuzzle(gameState, 0);
		}else {
			gameController.setupPuzzle(gameState, -1);
		}
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		String css = this.getClass().getResource("application.css").toExternalForm();
		scene.getStylesheets().add(css);
		stage.setScene(scene);
		stage.show();
	}
	
	public void newPuzzle(ActionEvent event) throws Exception
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("GameScene.fxml"));
		root = loader.load();
	
		SudokuController gameController = loader.getController();
		gameState = new SudokuGame(gameState.getDifficulty());
		if(Time >= 0){
			gameController.setupPuzzle(gameState, 0);
		}else{
			gameController.setupPuzzle(gameState, -1);
		}
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		String css = this.getClass().getResource("application.css").toExternalForm();
		scene.getStylesheets().add(css);
		stage.setScene(scene);
		stage.show();
	}

}