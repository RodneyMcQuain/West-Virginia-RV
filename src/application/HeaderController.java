package application;

import java.util.ArrayList;

import javafx.fxml.FXML;
import model.History;
import model.utility.FXMLReferences;

public class HeaderController {
	@FXML
	public void onClick_btBack() {
		History.removeMostRecent();
		ArrayList<FXMLStringAndController> history = History.getHistory();
		int lastIndexNum = history.size() - 1;
						
		FXMLStringAndController fxmlStringAndController = history.get(lastIndexNum);
		fxmlStringAndController.goToFXML();
	}
	
	@FXML
	public void onClick_btMainMenu() {
		MainMenuController mainMenuController = new MainMenuController();
		
		FXMLStringAndController fxmlStringAndController = new FXMLStringAndController(FXMLReferences.MAIN_MENU, mainMenuController);
		fxmlStringAndController.goToFXML();
	}
}
