package application;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.CheckPayment;
import model.Payment;
import model.UtilityBill;
import model.database.UtilityBillDAO;
import model.database.UtilityBillDAOImpl;
import model.utility.FXMLReferences;
import model.utility.HistoryUtils;
import model.utility.PaymentUtils;
import model.utility.UtilityBillUtils;

public class SpecifiedUtilityBillController {
	private UtilityBill utilityBill;
	@FXML public Label lblSpecifiedUtilityBill;
	@FXML public ComboBox<String> cbType;
	@FXML public TextField tfCheckNumber;
	@FXML public TextField tfAmount;
	@FXML public DatePicker dpDatePaid;
	
	public SpecifiedUtilityBillController(UtilityBill utilityBill) {
		this.utilityBill = utilityBill;
	}
	
	@FXML
	public void initialize() {
		printFields();
		
		addSpecifiedUtilityBillToHistory();
	}
	
	private void printFields() {
		int utilityBillTypeID = utilityBill.getUtilityBillTypeID();
		String utilityBillType = UtilityBillUtils.getUtilityBillTypeStringByID(utilityBillTypeID);
		CheckPayment checkPayment = (CheckPayment) utilityBill.getPayment();
		String checkNumber = checkPayment.getCheckNumber();
		BigDecimal amount = utilityBill.getAmount();
		LocalDate datePaid = utilityBill.getDatePaid();
		
		lblSpecifiedUtilityBill.setText(utilityBill.toString());
		cbType.setValue(utilityBillType);
		tfCheckNumber.setText(checkNumber);
		tfAmount.setText(String.valueOf(amount));
		dpDatePaid.setValue(datePaid);
	}
	
	private void addSpecifiedUtilityBillToHistory() {
		SpecifiedUtilityBillController specifiedUtilityBillController = new SpecifiedUtilityBillController(utilityBill);
		
		FXMLStringAndController fxmlStringAndController = new FXMLStringAndController(FXMLReferences.SPECIFIED_UTILITY_BILL, specifiedUtilityBillController);
		HistoryUtils.addToHistory(fxmlStringAndController);		
	}
	
	@FXML
	public void onClick_btApplyChanges() {
		if (GUIUtils.isEmptyControl(tfCheckNumber) 
		|| GUIUtils.isEmptyControl(tfAmount) 
		|| GUIUtils.isEmptyControl(dpDatePaid)) {
			GUIUtils.emptyTextAlert();
			return;
		}

		String type = cbType.getValue();
		int utilityBillTypeID = UtilityBillUtils.getUtilityBillTypeIDByString(type);
		String checkNumber = tfCheckNumber.getText();
		BigDecimal amount = new BigDecimal(tfAmount.getText());
		LocalDate datePaid = dpDatePaid.getValue();
		
		Payment payment = new CheckPayment(checkNumber);
		
		//for the forseeable future only checks will be used to pay for utilities
		UtilityBill utilityBill = new UtilityBill(this.utilityBill.getUtilityBillID(), utilityBillTypeID, PaymentUtils.CHECK_PAYMENT_ID, payment, amount, datePaid);
		
		UtilityBillDAO utilityBillDao = new UtilityBillDAOImpl();
		utilityBillDao.updateUtilityBill(utilityBill);
	}
	
	@FXML
	public void onClick_btRemove() {
		Optional<ButtonType> optionSelected = GUIUtils.promptRemovalAlert("Utility Bill");
		if (optionSelected.get() == ButtonType.OK) {
			UtilityBillDAO utilityBillDao = new UtilityBillDAOImpl();
			utilityBillDao.deleteUtilityBillByID(utilityBill.getUtilityBillID());
				
			goToUtilityBills();
		} else {
			return;
		}
	}
	
	private void goToUtilityBills() {        
		UtilityBillsController utilityBillsController = new UtilityBillsController();		
		
		FXMLStringAndController fxmlStringAndController = new FXMLStringAndController(FXMLReferences.UTILITY_BILLS, utilityBillsController);
		fxmlStringAndController.goToFXML();
	}
}
