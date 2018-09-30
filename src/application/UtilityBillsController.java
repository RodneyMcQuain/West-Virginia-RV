package application;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import model.CheckPayment;
import model.History;
import model.Payment;
import model.UtilityBill;
import model.database.BillDAO;
import model.database.BillDAOImpl;
import model.database.CheckPaymentDAO;
import model.database.CheckPaymentDAOImpl;
import model.database.UtilityBillDAO;
import model.database.UtilityBillDAOImpl;
import model.utility.BillUtils;
import model.utility.FXMLReferences;
import model.utility.PaymentUtils;
import model.utility.UtilityBillUtils;

public class UtilityBillsController {
	@FXML public TableView<UtilityBill> tvUtilityBills;
	@FXML public TableColumn<UtilityBill, String> tcType;
	@FXML public TableColumn<UtilityBill, String> tcAmount;
	@FXML public TableColumn<Payment, String> tcCheckNumber;
	@FXML public TableColumn<UtilityBill, String> tcDatePaid;
	@FXML public ComboBox<String> cbType;
	@FXML public TextField tfCheckNumber;
	@FXML public TextField tfAmount;
	@FXML public DatePicker dpDatePaid;
	@FXML public Label lblMonthlyTotal;
	@FXML public Label lblTotal;
	
	@FXML
	public void initialize() {
		GUIUtils.populateUtilityBillTypeComboBox(cbType);
		GUIUtils.setTodaysDate(dpDatePaid);
		
		setCellValueFactory();
		tvUtilityBills.setItems(getClientData());
		tvUtilityBills.setOnMouseClicked(e -> onDoubleClick_goToSpecifiedClient(e));
		
		setMonthlyTotal();
		setTotal();
		
		addUtilityBillsToHistory();
	}
	
	private void setCellValueFactory() {
		tcType.setCellValueFactory(new PropertyValueFactory<>("utilityBillTypeID"));
		tcAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
		tcCheckNumber.setCellValueFactory(new PropertyValueFactory<>("payment"));
		tcDatePaid.setCellValueFactory(new PropertyValueFactory<>("datePaid"));	
	}
	
	private ObservableList<UtilityBill> getClientData() {
		UtilityBillDAO utilityBillDao = new UtilityBillDAOImpl();
		List<UtilityBill> utilityBills = utilityBillDao.getAllUtilityBills();
		
		ObservableList<UtilityBill> olUtilityBills = FXCollections.observableArrayList(utilityBills);
		
		return olUtilityBills;
	}
	
	private void onDoubleClick_goToSpecifiedClient(MouseEvent e) {
		final int DOUBLE_CLICK = 2;
		
		if (e.getButton().equals(MouseButton.PRIMARY) && e.getClickCount() == DOUBLE_CLICK) {
			UtilityBill utilityBill = tvUtilityBills.getSelectionModel().getSelectedItem();
			
			goToSpecifiedUtilityBill(utilityBill);
		}
	}
	
	private void goToSpecifiedUtilityBill(UtilityBill utilityBill) {        
		SpecifiedUtilityBillController specifiedUtilityBillController = new SpecifiedUtilityBillController(utilityBill);

		FXMLStringAndController fxmlStringAndController = new FXMLStringAndController(FXMLReferences.SPECIFIED_UTILITY_BILL, specifiedUtilityBillController);
		fxmlStringAndController.goToFXML();
	}
	
	private void setMonthlyTotal() {
		BigDecimal monthlyTotal = UtilityBillUtils.calculateUtilityBillMonthlyTotal();
		
		lblMonthlyTotal.setText(String.valueOf(monthlyTotal));
	}
	
	private void setTotal() {
		BigDecimal total = UtilityBillUtils.calculateUtilityBillTotal();
		
		lblTotal.setText(String.valueOf(total));
	}
	
	private void addUtilityBillsToHistory() {
		UtilityBillsController utilityBillsController = new UtilityBillsController();
		
		FXMLStringAndController fxmlStringAndController = new FXMLStringAndController(FXMLReferences.UTILITY_BILLS, utilityBillsController);
		History.addToHistory(fxmlStringAndController);		
	}
	
	@FXML
	public void onClick_btAddUtilityBill() {
		if (GUIUtils.isEmptyControl(cbType) 
		|| GUIUtils.isEmptyControl(tfAmount) 
		|| GUIUtils.isEmptyControl(dpDatePaid)) {
			GUIUtils.emptyTextAlert();
			return;
		}
		
		if (!GUIUtils.isNumber(tfAmount)) {
			GUIUtils.errorAlert("Not Number", "Please enter a number into the amount textfield.");
			return;
		}
		
		
		String utilityBillType = cbType.getValue();
		int utilityBillTypeID = UtilityBillUtils.getUtilityBillTypeIDByString(utilityBillType);
		BigDecimal amount = new BigDecimal(tfAmount.getText());
		LocalDate datePaid = dpDatePaid.getValue();
		String checkNumber = tfCheckNumber.getText();
		
		Payment payment = new CheckPayment(checkNumber);
		
		BillDAO billDao = new BillDAOImpl();
		billDao.insertBill(BillUtils.UTILITY_BILL_ID);
		
		int billID = billDao.getMostRecentBillID();
		
		//for the forseeable future only checks will be used to pay for utilities
		UtilityBill utilityBill = new UtilityBill(billID, utilityBillTypeID, PaymentUtils.CHECK_PAYMENT_ID, payment, amount, datePaid); 
		
		UtilityBillDAO utilityBillDao = new UtilityBillDAOImpl();
		utilityBillDao.insertUtilityBill(utilityBill);
		
		CheckPayment checkPayment = (CheckPayment) payment;
		checkPayment.setPaymentID(billID);
		CheckPaymentDAO checkPaymentDao = new CheckPaymentDAOImpl();
		checkPaymentDao.insertCheckPayment((CheckPayment) checkPayment);
		
		GUIUtils.successfulAdditionAlert("Utility Bill");
		reloadUtilityBills();
	}
	
	private void reloadUtilityBills() {
		UtilityBillsController utilityBillsController = new UtilityBillsController();
		
		FXMLStringAndController fxmlStringAndController = new FXMLStringAndController(FXMLReferences.UTILITY_BILLS, utilityBillsController);
		fxmlStringAndController.goToFXML();
	}
}
