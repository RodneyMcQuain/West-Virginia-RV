package application;

import javafx.fxml.FXML;
import model.utility.FXMLReferences;

public class HeaderController {
	@FXML
	public void onClick_btBack() {
		int lastIndexNum = Main.history.size() - 1;
		
		lastIndexNum = validateList(lastIndexNum);
				
		FXMLStringAndController fxmlStringAndController = Main.history.get(lastIndexNum);
		fxmlStringAndController.goToFXML();
	}
	
	/**
	 * If last element and 2nd to last element are equal to each other get rid of both
	 * to avoid endless looping. If not remove just the last element like normal.
	 * 
	 * @param lastIndexNum - Current last index number.
	 * @return lastIndexNum - Updated last index number, reflecting new list size.
	*/
	private int validateList(int lastIndexNum) {
		if (lastIndexNum >= 1) {
			if (!(Main.history.get(lastIndexNum).equals(Main.history.get(lastIndexNum - 1)))) {
				lastIndexNum = removeLastElementAndDecrement(lastIndexNum);
			} else {
				lastIndexNum = removeLastElementAndDecrement(lastIndexNum);
				lastIndexNum = removeLastElementAndDecrement(lastIndexNum);
			}
		}
		
		return lastIndexNum;
	}
	
	private int removeLastElementAndDecrement(int lastIndexNum) {
		Main.history.remove(lastIndexNum);
		lastIndexNum--;
		
		return lastIndexNum;
	}
	
	@FXML
	public void onClick_btMainMenu() {
		MainMenuController mainMenuController = new MainMenuController();
		
		FXMLStringAndController fxmlStringAndController = new FXMLStringAndController(FXMLReferences.MAIN_MENU, mainMenuController);
		fxmlStringAndController.goToFXML();
	}
}
