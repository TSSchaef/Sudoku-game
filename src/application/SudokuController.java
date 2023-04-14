package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SudokuController implements Initializable{	
	
	@FXML
	private Label TimerBox, messages;
	
	@FXML
	TextField
	box00, box01, box02, box03, box04, box05, box06, box07, box08,
	box10, box11, box12, box13, box14, box15, box16, box17, box18,
	box20, box21, box22, box23, box24, box25, box26, box27, box28,
	box30, box31, box32, box33, box34, box35, box36, box37, box38,
	box40, box41, box42, box43, box44, box45, box46, box47, box48,
	box50, box51, box52, box53, box54, box55, box56, box57, box58,
	box60, box61, box62, box63, box64, box65, box66, box67, box68,
	box70, box71, box72, box73, box74, box75, box76, box77, box78,
	box80, box81, box82, box83, box84, box85, box86, box87, box88;
	
	@FXML
	private ArrayList<ArrayList<TextField>> boxes= new ArrayList<ArrayList<TextField>>();
	
	private Stage stage;
	private Scene scene;
	private Parent root;

	private Timer timer = new Timer();
	private int secondsElapsed;
	
	private SudokuGame gameState;
	
	public void setupPuzzle(SudokuGame gameState, int time)
	{
		secondsElapsed = time;
		this.gameState = gameState;
		outputPuzzle(gameState.getCurrentArr());
		outputLockedPuzzle(gameState.getGivenArr());
		startTimer();
	}
	
	public void hint(ActionEvent event)
	{
		if(gameState.useHint()) {
			int[] cellCoor = gameState.lastUpdated();
			outputPuzzle(gameState.getCurrentArr());
			outputLockedPuzzle(gameState.getGivenArr());
			boxes.get(cellCoor[0]).get(cellCoor[1]).setStyle("-fx-background-color: #002869");
		}
		
		int numHints = gameState.getNumHints();
		String Difficulty = gameState.getDifficulty();
		
		if(numHints < 0) {
			if(!Difficulty.equals("Expert")) {
				messages.setText("Out of hints! Add 2 minutes!");
				secondsElapsed += 120;
			}
			else{
				switch(numHints) {
				case -1:
					messages.setText("It's the hardest mode, there's no hints!!");
					break;
				case -2:
					messages.setText("Really? Add 10 minutes for your insolence!");
					secondsElapsed += 600;
					break;
				case -3:
					messages.setText("Stop trying!!");
					secondsElapsed += 600;
					break;
				case -4:
					messages.setText("THERE ARE NO HINTS!!");
					messages.setStyle("-fx-font-size: 35");
					secondsElapsed += 1200;
					break;
				case -5:
					messages.setText("Am I a joke to you??");
					messages.setStyle("-fx-font-size: 35");
					secondsElapsed += 600;
					break;
				case -6:
					messages.setText("Please... stop");
					messages.setStyle("-fx-font-size: 15");
					secondsElapsed += 600;
					break;
				default:
					messages.setText("Fine...take your hints");
					messages.setStyle("-fx-font-size: 25");
					gameState.setNumHints(90);
					break;
				}		
			}
		}else if(numHints < 5) {
			messages.setText(numHints + " hints left.");
		}else {
			messages.setText("Infinte hints remaining");
		}
	}
	
	public void undo(ActionEvent e) {
		gameState.undo();
		outputPuzzle(gameState.getCurrentArr());
		outputLockedPuzzle(gameState.getGivenArr());
	}
	
	public void pause(ActionEvent event) throws Exception
	{
		pauseTimer();
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("PauseScene.fxml"));
		root = loader.load();
		
		PauseController pause = loader.getController();
		pause.holdState(gameState, secondsElapsed);
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		String css = this.getClass().getResource("application.css").toExternalForm();
		scene.getStylesheets().add(css);
		stage.setScene(scene);
		stage.show();

	}
	
	private void startTimer()
	{
			timer.scheduleAtFixedRate(
				new TimerTask(){	
					@Override				
					public void run() {
						Platform.runLater(() -> {
						if(secondsElapsed >= 0) {	
							secondsElapsed++;
						}
						else {
							secondsElapsed--;
						}
						TimerBox.setText(Utility.formatTime(secondsElapsed));
					});
				}
			}, 0, 1000);
	}
	
	private void pauseTimer()
	{
		timer.cancel();
	}
	
	private void outputLockedPuzzle(char[][] puzzleArr)
	{
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				if(puzzleArr[i][j] != '\0') {
					boxes.get(i).get(j).setText("" + puzzleArr[i][j]);
					boxes.get(i).get(j).setEditable(false);
					boxes.get(i).get(j).setStyle("-fx-background-color: #424242");
				}
			}
		}
	}

	private void outputPuzzle(char[][] puzzleArr)
	{
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				if(puzzleArr[i][j] != '\0') {
					boxes.get(i).get(j).setText("" + puzzleArr[i][j]);
					boxes.get(i).get(j).setEditable(true);
				}
				else {
					boxes.get(i).get(j).setText("");
				}
			}
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		messages.setText("");
		boxes.add(new ArrayList<TextField>());
		boxes.add(new ArrayList<TextField>());
		boxes.add(new ArrayList<TextField>());
		boxes.add(new ArrayList<TextField>());
		boxes.add(new ArrayList<TextField>());
		boxes.add(new ArrayList<TextField>());
		boxes.add(new ArrayList<TextField>());
		boxes.add(new ArrayList<TextField>());
		boxes.add(new ArrayList<TextField>());
		
		boxes.get(0).add(box00);
		boxes.get(0).add(box01);
		boxes.get(0).add(box02);
		boxes.get(0).add(box03);
		boxes.get(0).add(box04);
		boxes.get(0).add(box05);
		boxes.get(0).add(box06);
		boxes.get(0).add(box07);
		boxes.get(0).add(box08);
		boxes.get(1).add(box10);
		boxes.get(1).add(box11);
		boxes.get(1).add(box12);
		boxes.get(1).add(box13);
		boxes.get(1).add(box14);
		boxes.get(1).add(box15);
		boxes.get(1).add(box16);
		boxes.get(1).add(box17);
		boxes.get(1).add(box18);
		boxes.get(2).add(box20);
		boxes.get(2).add(box21);
		boxes.get(2).add(box22);
		boxes.get(2).add(box23);
		boxes.get(2).add(box24);
		boxes.get(2).add(box25);
		boxes.get(2).add(box26);
		boxes.get(2).add(box27);
		boxes.get(2).add(box28);
		boxes.get(3).add(box30);
		boxes.get(3).add(box31);
		boxes.get(3).add(box32);
		boxes.get(3).add(box33);
		boxes.get(3).add(box34);
		boxes.get(3).add(box35);
		boxes.get(3).add(box36);
		boxes.get(3).add(box37);
		boxes.get(3).add(box38);
		boxes.get(4).add(box40);
		boxes.get(4).add(box41);
		boxes.get(4).add(box42);
		boxes.get(4).add(box43);
		boxes.get(4).add(box44);
		boxes.get(4).add(box45);
		boxes.get(4).add(box46);
		boxes.get(4).add(box47);
		boxes.get(4).add(box48);
		boxes.get(5).add(box50);
		boxes.get(5).add(box51);
		boxes.get(5).add(box52);
		boxes.get(5).add(box53);
		boxes.get(5).add(box54);
		boxes.get(5).add(box55);
		boxes.get(5).add(box56);
		boxes.get(5).add(box57);
		boxes.get(5).add(box58);
		boxes.get(6).add(box60);
		boxes.get(6).add(box61);
		boxes.get(6).add(box62);
		boxes.get(6).add(box63);
		boxes.get(6).add(box64);
		boxes.get(6).add(box65);
		boxes.get(6).add(box66);
		boxes.get(6).add(box67);
		boxes.get(6).add(box68);
		boxes.get(7).add(box70);
		boxes.get(7).add(box71);
		boxes.get(7).add(box72);
		boxes.get(7).add(box73);
		boxes.get(7).add(box74);
		boxes.get(7).add(box75);
		boxes.get(7).add(box76);
		boxes.get(7).add(box77);
		boxes.get(7).add(box78);
		boxes.get(8).add(box80);
		boxes.get(8).add(box81);
		boxes.get(8).add(box82);
		boxes.get(8).add(box83);
		boxes.get(8).add(box84);
		boxes.get(8).add(box85);
		boxes.get(8).add(box86);
		boxes.get(8).add(box87);
		boxes.get(8).add(box88);
		
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
				int k = i;
				int m = j;
				boxes.get(i).get(j).focusedProperty().addListener(new ChangeListener<Boolean>() {
					public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
						if(newPropertyValue) {
							//received focus
						}
						else {
							String text = boxes.get(k).get(m).getText();
							if(text.length() != 1) {
								boxes.get(k).get(m).setText("");
							}
							else {
								char entered = text.charAt(0);
								if(entered >= 49 && entered <= 57) {
									gameState.updateCurrentArr(k, m, entered);
									if(gameState.currentArrFull()){
										puzzleFinished();
									}
								}
								else {
									boxes.get(k).get(m).setText("");
								}
							}
						}
						
					}
					
				});
			}
		}
	}
	
	private void puzzleFinished()
	{
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
				if(!Utility.validatePuzzle(gameState.getCurrentArr())) {
					messages.setText("Some numbers are incorrect...");
				}
				else {
					messages.setText("Well done! Congratulations!!");
					pauseTimer();
				}
			}
		}
	}
}