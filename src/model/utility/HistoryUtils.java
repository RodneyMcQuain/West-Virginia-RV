package model.utility;

import application.FXMLStringAndController;
import application.Main;

public class HistoryUtils {
	public static void addToHistory(FXMLStringAndController fxmlStringAndController) {
		Main.history.add(fxmlStringAndController);
	}
}
