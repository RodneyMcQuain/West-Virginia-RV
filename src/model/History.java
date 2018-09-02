package model;

import java.util.ArrayList;

import application.FXMLStringAndController;

public class History {
	private static ArrayList<FXMLStringAndController> history = new ArrayList<FXMLStringAndController>();
	
	private History() {
		
	}
	
	public static ArrayList<FXMLStringAndController> getHistory() {
		return history;
	}
	
	public static void addToHistory(FXMLStringAndController fxmlStringAndController) {
		int historySize = history.size();
				
		if (isInitialAdd(fxmlStringAndController, historySize))
			return;
		
		add(fxmlStringAndController, historySize);
	}
	
	private static boolean isInitialAdd(FXMLStringAndController fxmlStringAndController, int historySize) {
		boolean isInitialAdd = false;
		
		if (historySize == 0) {
			history.add(fxmlStringAndController);
			isInitialAdd = true;
		}
		
		return isInitialAdd;
	}
	
	private static void add(FXMLStringAndController fxmlStringAndController, int historySize) {
		if (historySize > 0)
			if (!fxmlStringAndController.equals(history.get(historySize - 1)))
				history.add(fxmlStringAndController);
	}
	
	public static void removeMostRecent() {
		int historySize = history.size();

		history.remove(historySize - 1);
	}
}
