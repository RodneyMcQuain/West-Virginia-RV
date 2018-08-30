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
import javafx.scene.layout.GridPane;
import model.CheckPayment;
import model.ClientBill;
import model.DebitPayment;
import model.Payment;
import model.database.CheckPaymentDAO;
import model.database.CheckPaymentDAOImpl;
import model.database.ClientBillDAO;
import model.database.ClientBillDAOImpl;
import model.utility.FXMLReferences;
import model.utility.HistoryUtils;
import model.utility.PaymentUtils;

public class SpecifiedClientBillController {
	private ClientBill clientBill;
	@FXML public Label lblSpecifiedClientBill;
	@FXML public GridPane gpSpecifiedClientBill;
	@FXML public ComboBox<String> cbPaymentType;
	@FXML public TextField tfAmount;
	@FXML public DatePicker dpDatePaid;
	@FXML public TextField tfCheckNumber;
	@FXML public TextField tfDebitTransactionNumber;
	
	public SpecifiedClientBillController(ClientBill clientBill) {
		this.clientBill = clientBill;
	}
	
	@FXML
	public void initialize() {		
		removePaymentControls();
		setPaymentControlPlaceholders();
		GUIUtils.populatePaymentTypeComboBox(cbPaymentType);
		
		printClientBillFields();
		
		addSpecifiedClientBillToHistory();
	}
	
	private void printClientBillFields() {
		int paymentTypeID = clientBill.getPaymentTypeID();
		String paymentType = PaymentUtils.getPaymentTypeByID(paymentTypeID);
		Payment payment = clientBill.getPayment();
		if (paymentTypeID == PaymentUtils.CHECK_PAYMENT_ID) {
			gpSpecifiedClientBill.add(tfCheckNumber, 1, 1);
			CheckPayment checkPayment = (CheckPayment) payment;
			tfCheckNumber.setText(checkPayment.getCheckNumber());
		} else if (paymentTypeID == PaymentUtils.DEBIT_PAYMENT_ID) {
			gpSpecifiedClientBill.add(tfDebitTransactionNumber, 1, 1);
			DebitPayment debitPayment = (DebitPayment) payment;
			tfDebitTransactionNumber.setText(debitPayment.getDebitTransactionNumber());
		}
		BigDecimal amount = clientBill.getAmount();
		LocalDate datePaid = clientBill.getDatePaid();
		
		lblSpecifiedClientBill.setText(clientBill.toString());
		cbPaymentType.setValue(paymentType);
		tfAmount.setText(amount.toString());
		dpDatePaid.setValue(datePaid);
	}
	
	private void removePaymentControls() {
		gpSpecifiedClientBill.getChildren().remove(tfCheckNumber);
		gpSpecifiedClientBill.getChildren().remove(tfDebitTransactionNumber);
	}
	
	private void setPaymentControlPlaceholders() {
		tfCheckNumber.setPromptText("Check Number");
		tfDebitTransactionNumber.setPromptText("Debit Transaction Number");
	}
	
	private void addSpecifiedClientBillToHistory() {
		SpecifiedClientBillController specifiedClientBillController = new SpecifiedClientBillController(clientBill);
		
		FXMLStringAndController fxmlStringAndController = new FXMLStringAndController(FXMLReferences.SPECIFIED_CLIENT_BILL, specifiedClientBillController);
		HistoryUtils.addToHistory(fxmlStringAndController);		
	}
	
	@FXML
	public void onChange_cbPaymentType() {
		String paymentType = cbPaymentType.getValue();
		
		if (paymentType.equals(PaymentUtils.CHECK_PAYMENT_STRING)) {
			removePaymentControls();
			gpSpecifiedClientBill.add(tfCheckNumber, 1, 1);
		} else if (paymentType.equals(PaymentUtils.DEBIT_PAYMENT_STRING)) {
			removePaymentControls();
			gpSpecifiedClientBill.add(tfDebitTransactionNumber, 1, 1);
		} else {
			removePaymentControls();
		}
	}
	
	@FXML
	private void onClick_btApplyChanges() {
		if (GUIUtils.isEmptyControl(cbPaymentType) 
		|| GUIUtils.isEmptyControl(tfAmount) 
		|| GUIUtils.isEmptyControl(dpDatePaid)
		|| (cbPaymentType.getValue().equals("Check") && GUIUtils.isEmptyControl(tfCheckNumber))
		|| (cbPaymentType.getValue().equals("Debit") && GUIUtils.isEmptyControl(tfDebitTransactionNumber))) {
			GUIUtils.emptyTextAlert();
			return;
		}
		
		String paymentType = cbPaymentType.getValue();
		int paymentTypeID = PaymentUtils.getPaymentTypeIDByString(paymentType);
		BigDecimal amount = new BigDecimal(tfAmount.getText());
		LocalDate datePaid = dpDatePaid.getValue();
		int clientID = clientBill.getClientID();
		
		Payment payment = null;
		if (paymentType.equals(PaymentUtils.CHECK_PAYMENT_STRING)) {
			String checkNumber = tfCheckNumber.getText();
			CheckPayment checkPayment = new CheckPayment(checkNumber);
			
			CheckPaymentDAO checkPaymentDao = new CheckPaymentDAOImpl();
			checkPaymentDao.updateCheckPayment(checkPayment);
			
			payment = (Payment) checkPayment;
		} else if (paymentType.equals(PaymentUtils.DEBIT_PAYMENT_STRING)) {
			//to impl
		}		
		
		ClientBill clientBill = new ClientBill(clientID, paymentTypeID, payment, amount, datePaid);
		
		ClientBillDAO clientBillDao = new ClientBillDAOImpl();
		clientBillDao.updateClientBill(clientBill);
		
		GUIUtils.successfulUpdateAlert("Client Bill");
		reloadSpecifiedClientBill();
	}
	
	private void reloadSpecifiedClientBill() {        
		SpecifiedClientBillController specifiedClientBillController = new SpecifiedClientBillController(clientBill);
		
		FXMLStringAndController fxmlStringAndController = new FXMLStringAndController(FXMLReferences.SPECIFIED_CLIENT_BILL, specifiedClientBillController);
		HistoryUtils.addToHistory(fxmlStringAndController);		
	}
	
	@FXML
	private void onClick_btRemove() {
		Optional<ButtonType> optionSelected = GUIUtils.promptRemovalAlert("Client Bill");
		if (optionSelected.get() == ButtonType.OK) {
			ClientBillDAO clientBillDao = new ClientBillDAOImpl();
			clientBillDao.deleteClientBillByID(clientBill.getClientBillID());
				
			goToSpecifiedClient();
		} else {
			return;
		}
	}
	
	private void goToSpecifiedClient() {        
		SpecifiedClientController specifiedClientController = new SpecifiedClientController(clientBill.getClientID());
		
		FXMLStringAndController fxmlStringAndController = new FXMLStringAndController(FXMLReferences.SPECIFIED_CLIENT, specifiedClientController);
		fxmlStringAndController.goToFXML();
	}
}
