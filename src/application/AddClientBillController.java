package application;

import java.math.BigDecimal;
import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import model.CheckPayment;
import model.ClientBill;
import model.History;
import model.Payment;
import model.database.BillDAO;
import model.database.BillDAOImpl;
import model.database.CheckPaymentDAO;
import model.database.CheckPaymentDAOImpl;
import model.database.ClientBillDAO;
import model.database.ClientBillDAOImpl;
import model.utility.BillUtils;
import model.utility.FXMLReferences;
import model.utility.PaymentUtils;

public class AddClientBillController {
	private int clientID;
	@FXML public GridPane gpClientBill;
	@FXML public ComboBox<String> cbPaymentType;
	@FXML public TextField tfAmount;
	@FXML public DatePicker dpDatePaid;
	@FXML public TextField tfCheckNumber;
	@FXML public TextField tfDebitTransactionNumber;
	
	public AddClientBillController(int clientID) {
		this.clientID = clientID;
	}
	
	@FXML
	public void initialize() {
		removePaymentControls();
		setPaymentControlPlaceholders();
		GUIUtils.populatePaymentTypeComboBox(cbPaymentType);
		GUIUtils.setTodaysDate(dpDatePaid);
		
		addAddClientBillToHistory();
	}
	
	private void removePaymentControls() {
		gpClientBill.getChildren().remove(tfCheckNumber);
		gpClientBill.getChildren().remove(tfDebitTransactionNumber);
	}
	
	private void setPaymentControlPlaceholders() {
		tfCheckNumber.setPromptText("Check Number");
		tfDebitTransactionNumber.setPromptText("Debit Transaction Number");
	}
	
	private void addAddClientBillToHistory() {
		AddClientBillController addClientBillController = new AddClientBillController(clientID);

		FXMLStringAndController fxmlStringAndController = new FXMLStringAndController(FXMLReferences.ADD_CLIENT_BILL, addClientBillController);
		History.addToHistory(fxmlStringAndController);		
	}
	
	@FXML
	public void onClick_btAddClientBill() {
		if (GUIUtils.isEmptyControl(cbPaymentType) 
		|| GUIUtils.isEmptyControl(tfAmount) 
		|| GUIUtils.isEmptyControl(dpDatePaid)
		|| (cbPaymentType.getValue().equals(PaymentUtils.CHECK_PAYMENT_STRING) && GUIUtils.isEmptyControl(tfCheckNumber))
		|| (cbPaymentType.getValue().equals(PaymentUtils.DEBIT_PAYMENT_STRING) && GUIUtils.isEmptyControl(tfDebitTransactionNumber))) {
			GUIUtils.emptyTextAlert();
			return;
		}

		if (!isValidPayment() || !isValidNumber()) {
			return;
		}
		
		String paymentType = cbPaymentType.getValue();
		int paymentTypeID = PaymentUtils.getPaymentTypeIDByString(paymentType);
		BigDecimal amount = new BigDecimal(tfAmount.getText());
		LocalDate datePaid = dpDatePaid.getValue();
		
		Payment payment = getSpecifiedPayment(paymentType);
		
		BillDAO billDao = new BillDAOImpl();
		ClientBillDAO clientBillDao = new ClientBillDAOImpl();

		billDao.insertBill(BillUtils.CLIENT_BILL_ID);
		int billID = billDao.getMostRecentBillID();
		payment.setPaymentID(billID);
		
		ClientBill clientBill = new ClientBill(billID, clientID, paymentTypeID, payment, amount, datePaid);
		clientBillDao.insertClientBill(clientBill);
		
		insertSpecifiedPayment(paymentType, payment);
		
		GUIUtils.successfulAdditionAlert("Client Bill");
		reloadAddClientBill();
	}
	
	private boolean isValidPayment() {
		boolean isValidPayment = true;
		
		if (cbPaymentType.getValue().equals(PaymentUtils.CHECK_PAYMENT_STRING) && !GUIUtils.isNumber(tfCheckNumber)) {
			GUIUtils.errorAlert("Not Number", "Please enter a number into the check number textfield.");
			isValidPayment = false;
		} else if (cbPaymentType.getValue().equals(PaymentUtils.DEBIT_PAYMENT_STRING) && !GUIUtils.isNumber(tfDebitTransactionNumber)) {
			GUIUtils.errorAlert("Not Number", "Please enter a number into the debit transaction number textfield.");
			isValidPayment = false;
		}
		
		return isValidPayment;
	}
	
	private boolean isValidNumber() {
		boolean isValidPayment = true;
		
		if (!GUIUtils.isNumber(tfAmount)) {
			GUIUtils.errorAlert("Not Number", "Please enter a number into the amount textfield.");
			isValidPayment = false;
		}
		
		return isValidPayment;
	}
	
	private Payment getSpecifiedPayment(String paymentType) {
		Payment payment = null;
		
		if (paymentType.equals(PaymentUtils.CHECK_PAYMENT_STRING)) {
			String checkNumber = tfCheckNumber.getText();
			CheckPayment checkPayment = new CheckPayment(checkNumber);
			
			payment = (Payment) checkPayment;
		} else if (paymentType.equals(PaymentUtils.DEBIT_PAYMENT_STRING)) {
			//to impl
		}
		
		return payment;
	}
	
	private void insertSpecifiedPayment(String paymentType, Payment payment) {
		if (paymentType.equals(PaymentUtils.CHECK_PAYMENT_STRING)) {			
			CheckPaymentDAO checkPaymentDao = new CheckPaymentDAOImpl();
			checkPaymentDao.insertCheckPayment((CheckPayment) payment);
		} else if (paymentType.equals(PaymentUtils.DEBIT_PAYMENT_STRING)) {
			//to impl
		}
	}
	
	private void reloadAddClientBill() {
		AddClientBillController addClientBillController = new AddClientBillController(clientID);
		
		FXMLStringAndController fxmlStringAndController = new FXMLStringAndController(FXMLReferences.ADD_CLIENT_BILL, addClientBillController);
		fxmlStringAndController.goToFXML();
	}
	
	@FXML
	public void onChange_cbPaymentType() {
		String paymentType = cbPaymentType.getValue();
		
		if (paymentType.equals(PaymentUtils.CHECK_PAYMENT_STRING)) {
			removePaymentControls();
			gpClientBill.add(tfCheckNumber, 1, 1);
		} else if (paymentType.equals(PaymentUtils.DEBIT_PAYMENT_STRING)) {
			removePaymentControls();
			gpClientBill.add(tfDebitTransactionNumber, 1, 1);
		} else {
			removePaymentControls();
		}
	}
}
