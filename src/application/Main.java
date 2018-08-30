package application;
	
import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import model.utility.MySQLUtils;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
	public static Stage theStage;
	public static ArrayList<FXMLStringAndController> history;
	
	@Override
	public void start(Stage primaryStage) {				
		theStage = primaryStage;
		
		try {
			Scene primaryScene = getMainMenuScene();
			
			primaryStage.setScene(primaryScene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private Scene getMainMenuScene() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        
		MainMenuController mainMenuController = new MainMenuController();
		loader.setController(mainMenuController);

		Parent root = null;
		try {
			root = (Parent) loader.load();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
		return new Scene(root);
	}
	
	public static void main(String[] args) {
		history = new ArrayList<FXMLStringAndController>();
		MySQLUtils.initializeStaticRecords();
	
		launch(args);
	}
}
