package application;

import java.util.Stack;

import javafx.fxml.FXML;
import model.History;
import model.utility.FXMLReferences;

public class HeaderController {
	@FXML
	public void onClick_btBack() {
		History.removeMostRecent(); //This is called so there is not an endless loop of going back to the same scene
		Stack<FXMLStringAndController> history = History.getHistory();
						
		FXMLStringAndController fxmlStringAndController = history.pop();
		fxmlStringAndController.goToFXML();
	}
	
	@FXML
	public void onClick_btMainMenu() {
		MainMenuController mainMenuController = new MainMenuController();
		
		FXMLStringAndController fxmlStringAndController = new FXMLStringAndController(FXMLReferences.MAIN_MENU, mainMenuController);
		fxmlStringAndController.goToFXML();
	}
}
