package model;

import java.util.Stack;

import application.FXMLStringAndController;

public class History {
	private static Stack<FXMLStringAndController> history = new Stack<FXMLStringAndController>();
	
	private History() {
		
	}
	
	public static Stack<FXMLStringAndController> getHistory() {
		return history;
	}
	
	public static void addToHistory(FXMLStringAndController fxmlStringAndController) {				
		if (isInitialAdd())
			history.push(fxmlStringAndController);
		else
			addIfNonDuplicate(fxmlStringAndController);
	}
	
	private static boolean isInitialAdd() {
		boolean isInitialAdd = false;
		
		if (history.isEmpty()) {
			isInitialAdd = true;
		}
		
		return isInitialAdd;
	}
	
	private static void addIfNonDuplicate(FXMLStringAndController fxmlStringAndController) {
		if (!fxmlStringAndController.equals(history.peek()))
			history.push(fxmlStringAndController);
	}
	
	public static void removeMostRecent() {
		history.pop();
	}
}
