package application;


import java.time.LocalDate;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import model.utility.ClientUtils;
import model.utility.PaymentUtils;
import model.utility.UtilityBillUtils;

public class GUIUtils {
	public static void populateBillPeriodComboBox(ComboBox<String> cbBillPeriod) {
		cbBillPeriod.getItems().clear();
		cbBillPeriod.getItems().addAll(ClientUtils.MONTHLY_BILL_PERIOD, ClientUtils.WEEKLY_BILL_PERIOD);
	}
	
	public static void populateUtilityBillTypeComboBox(ComboBox<String> cbUtilityBillType) {
		cbUtilityBillType.getItems().clear();
		cbUtilityBillType.getItems().addAll(UtilityBillUtils.ELECTRIC_STRING, UtilityBillUtils.GAS_STRING, 
											UtilityBillUtils.WATER_STRING, UtilityBillUtils.TAXES_STRING, 
											UtilityBillUtils.INSURANCE_STRING);
	}
	
	public static void populatePaymentTypeComboBox(ComboBox<String> cbPaymentType) {
		cbPaymentType.getItems().clear();
		cbPaymentType.getItems().addAll(PaymentUtils.CHECK_PAYMENT_STRING, PaymentUtils.DEBIT_PAYMENT_STRING);
	}
	
	public static void setDefualtPaymentType(ComboBox<String> cbPaymentType) {
		cbPaymentType.setValue(PaymentUtils.CHECK_PAYMENT_STRING);
	}
	
	public static void setTodaysDate(DatePicker dpDate) {
		LocalDate today = LocalDate.now();
		
		dpDate.setValue(today);
	}
	
	public static boolean isEmptyControl(TextField textField) {
		boolean isEmpty = false;
		String textFieldText = textField.getText();
			
		if (textFieldText.trim().equals("")) {
			isEmpty = true;
		}
		
		return isEmpty;
	}
	
	public static boolean isEmptyControl(ComboBox<String> comboBox) {
		boolean isEmpty = false;
		String comboBoxValue = comboBox.getValue();		
		
		if (comboBoxValue == null) {
			isEmpty = true;
		}
		
		return isEmpty;
	}
	
	public static boolean isEmptyControl(DatePicker datePicker) {
		boolean isEmpty = false;
		LocalDate datePickerValue = datePicker.getValue();		
		
		if (datePickerValue == null) {
			isEmpty = true;
		}
		
		return isEmpty;
	}
	
	public static boolean isPhoneNumber(TextField tfPhoneNumber) {
		boolean isPhoneNumber = false;
		String phoneNumber = tfPhoneNumber.getText();
		final String phonePattern = "[(][0-9]{3}[)] [0-9]{3}-[0-9]{4}";
		
		if (phoneNumber.matches(phonePattern)) {
			isPhoneNumber = true;
		}
	
		return isPhoneNumber;
	}
	
	public static void emptyTextAlert() {
    	Alert alEmptyTextField = new Alert(AlertType.ERROR);
    	alEmptyTextField.setTitle("Required Empty Field");
    	alEmptyTextField.setHeaderText(null);
    	alEmptyTextField.setContentText("Please enter information into all of the empty fields.");
    	alEmptyTextField.showAndWait();	
	}
	
	public static boolean isInvalidLotNumber(TextField tfLotNumber) {
		boolean isInvalid = false;
		String lotNumberString = tfLotNumber.getText();
		int lotNumber = 0;
		
		try {
			lotNumber = Integer.parseInt(lotNumberString);
		} catch (NumberFormatException nfe) {
	    	Alert alNotNumber = new Alert(AlertType.ERROR);
	    	alNotNumber.setTitle("Lot Number");
	    	alNotNumber.setHeaderText(null);
	    	alNotNumber.setContentText("Please enter an integer for the lot.");
	    	alNotNumber.showAndWait();
	    	isInvalid = true;
		}
		if (lotNumber < 0) {
	    	Alert alLessThanZero = new Alert(AlertType.ERROR);
	    	alLessThanZero.setTitle("Lot Number");
	    	alLessThanZero.setHeaderText(null);
	    	alLessThanZero.setContentText("Please enter a non-negative number for the lot.");
	    	alLessThanZero.showAndWait();
	    	isInvalid = true;
	    }
	
		return isInvalid;
	}
	
	public static void successfulAdditionAlert(String addedModel) {
    	Alert alAddition = new Alert(AlertType.INFORMATION);
    	alAddition.setTitle(addedModel + " Addition");
    	alAddition.setHeaderText(null);
    	alAddition.setContentText(addedModel + " added successfully.");
    	alAddition.showAndWait();	
	}
	
	public static void successfulUpdateAlert(String addedModel) {
    	Alert alUpdate = new Alert(AlertType.INFORMATION);
    	alUpdate.setTitle(addedModel + " Update");
    	alUpdate.setHeaderText(null);
    	alUpdate.setContentText(addedModel + " updated successfully.");
    	alUpdate.showAndWait();	
	}
	
	public static Optional<ButtonType> promptCascadeRemovalAlert(String removeModel, String cascadeDeleteModel) {
    	Alert alRemove = new Alert(AlertType.CONFIRMATION);
    	alRemove.setTitle(removeModel + " Removal");
    	alRemove.setHeaderText(null);
    	alRemove.setContentText("Are you sure you want to remove this " + removeModel + "? All associated " + cascadeDeleteModel + " will also be removed.");
		Optional<ButtonType> optionSelected = alRemove.showAndWait();
		
		return optionSelected;
	}
	
	public static Optional<ButtonType> promptRemovalAlert(String removeModel) {
    	Alert alRemove = new Alert(AlertType.CONFIRMATION);
    	alRemove.setTitle(removeModel + " Removal");
    	alRemove.setHeaderText(null);
    	alRemove.setContentText("Are you sure you want to remove this " + removeModel + "?");
		Optional<ButtonType> optionSelected = alRemove.showAndWait();
		
		return optionSelected;
	}
}
