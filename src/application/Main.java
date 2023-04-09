package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception{
			Parent root = FXMLLoader.load(getClass().getResource("OpeningScene.fxml"));
			Scene scene = new Scene(root,600,700,Color.BLACK);
			Stage stage = new Stage();
			
			String css = this.getClass().getResource("application.css").toExternalForm();
			scene.getStylesheets().add(css);
			
			stage.setScene(scene);
			stage.setTitle("Sudoku");
			stage.centerOnScreen();
			stage.setResizable(false);
			
			Image myIcon = new Image("icon.png");
			stage.getIcons().add(myIcon);
			
			stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
