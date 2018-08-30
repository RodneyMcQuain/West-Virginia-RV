package application;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FXMLStringAndController {
	private String fxmlString;
	private Object controller;
	
	public FXMLStringAndController(String fxmlString, Object controller) {
		this.fxmlString = fxmlString;
		this.controller = controller;
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean isEqual = false;
		
		if (obj == null || !(obj instanceof FXMLStringAndController)) {
			return isEqual;
		}
		
		FXMLStringAndController fxmlStringAndControllerObj = (FXMLStringAndController) obj;
		
		if (this.fxmlString.equals(fxmlStringAndControllerObj.fxmlString)) {
			isEqual = true;
		} 
		
		return isEqual;
	}
	
	@Override
	public int hashCode() {
		return fxmlString.hashCode();
	}
	
	public void goToFXML() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlString));
        
		loader.setController(controller);

		Parent root = null;
		try {
			root = (Parent) loader.load();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
		Stage currentStage = Main.theStage;
		currentStage.setScene(new Scene(root));
        currentStage.show();
	}
	
	public String getFxmlString() {
		return fxmlString;
	}

	public Object getController() {
		return controller;
	}
}
