package application;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import model.Client;
import model.ClientBill;
import model.History;
import model.Payment;
import model.database.ClientBillDAO;
import model.database.ClientBillDAOImpl;
import model.database.ClientDAO;
import model.database.ClientDAOImpl;
import model.utility.FXMLReferences;

public class SpecifiedClientController {
	private int clientID;
	@FXML public Label lblSpecifiedClient;
	@FXML public TextField tfFirstName;
	@FXML public TextField tfLastName;
	@FXML public TextField tfEmail;
	@FXML public TextField tfPhoneNumber;
	@FXML public TextField tfLotNumber;
	@FXML public ComboBox<String> cbBillPeriod;
	@FXML public TableView<ClientBill> tvClientBills;
	@FXML public TableColumn<ClientBill, BigDecimal> tcAmount;
	@FXML public TableColumn<ClientBill, LocalDate> tcDatePaid;
	@FXML public TableColumn<ClientBill, Payment> tcPayment;
	
	public SpecifiedClientController(int clientID) {
		this.clientID = clientID;
	}
	
	@FXML
	public void initialize() {
		GUIUtils.populateBillPeriodComboBox(cbBillPeriod);
		
		printClientFields();
	
		setCellFactory();
		tvClientBills.setItems(getClientBillData());
		tvClientBills.setOnMouseClicked(e -> onDoubleClick_goToSpecifiedClientBill(e));	
		
		addSpecifiedClientToHistory();
	}
	
	private void printClientFields() {
		ClientDAO clientDao = new ClientDAOImpl();
		Client client = clientDao.getClientByID(clientID);
		
		String firstName = client.getFirstName();
		String lastName = client.getLastName();
		String email = client.getEmail();
		String phoneNumber = client.getPhoneNumber();
		int lotNumber = client.getLotNumber();
		String billPeriod = client.getBillPeriod();
		
		lblSpecifiedClient.setText(client.toString());
		tfFirstName.setText(firstName);
		tfLastName.setText(lastName);
		tfEmail.setText(email);
		tfPhoneNumber.setText(phoneNumber);
		tfLotNumber.setText(String.valueOf(lotNumber));
		cbBillPeriod.setValue(billPeriod);
	}
	
	private void setCellFactory() {
		tcAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
		tcPayment.setCellValueFactory(new PropertyValueFactory<>("payment"));
		tcDatePaid.setCellValueFactory(new PropertyValueFactory<>("datePaid"));
	}
	
	private ObservableList<ClientBill> getClientBillData() {
		ClientBillDAO clientBillDao = new ClientBillDAOImpl();
		List<ClientBill> specifiedClientBills = clientBillDao.getAllClientBillsByClientID(clientID);
		
		ObservableList<ClientBill> olSpecifiedClientBills = FXCollections.observableArrayList(specifiedClientBills);
		
		return olSpecifiedClientBills;
	}
	
	private void onDoubleClick_goToSpecifiedClientBill(MouseEvent e) {
		final int DOUBLE_CLICK = 2;
		
		if (e.getButton().equals(MouseButton.PRIMARY) && e.getClickCount() == DOUBLE_CLICK) {
			ClientBill clientBill = tvClientBills.getSelectionModel().getSelectedItem();
			
			goToSpecifiedClientBill(clientBill);
		}
	}
	
	private void goToSpecifiedClientBill(ClientBill clientBill) {
		SpecifiedClientBillController specifiedClientBillController = new SpecifiedClientBillController(clientBill);

		FXMLStringAndController fxmlStringAndController = new FXMLStringAndController(FXMLReferences.SPECIFIED_CLIENT_BILL, specifiedClientBillController);
		fxmlStringAndController.goToFXML();	
	}
	
	private void addSpecifiedClientToHistory() {
		SpecifiedClientController specifiedClientController = new SpecifiedClientController(clientID);
		
		FXMLStringAndController fxmlStringAndController = new FXMLStringAndController(FXMLReferences.SPECIFIED_CLIENT, specifiedClientController);
		History.addToHistory(fxmlStringAndController);		
	}	
	
	@FXML
	public void onClick_btApplyChanges() {
		if (GUIUtils.isEmptyControl(tfFirstName) 
		|| GUIUtils.isEmptyControl(tfLastName) 
		|| GUIUtils.isEmptyControl(tfEmail) 
		|| GUIUtils.isEmptyControl(tfPhoneNumber) 
		|| GUIUtils.isEmptyControl(tfLotNumber) 
		|| GUIUtils.isInvalidLotNumber(tfLotNumber)
		|| GUIUtils.isEmptyControl(cbBillPeriod)) {
			GUIUtils.emptyTextAlert();
			return;
		}
		
		String firstName = tfFirstName.getText();
		String lastName = tfLastName.getText();
		String email = tfEmail.getText();
		String phoneNumber = tfPhoneNumber.getText();
		int lotNumber = Integer.valueOf(tfLotNumber.getText());
		String billPeriod = cbBillPeriod.getValue();
		
		Client client = new Client(clientID, firstName, lastName, email, phoneNumber, lotNumber, billPeriod);
		ClientDAO clientDao = new ClientDAOImpl();
		clientDao.updateClient(client);

		GUIUtils.successfulUpdateAlert("Client");
		reloadSpecifiedClient();
	}
	
	private void reloadSpecifiedClient() {
		SpecifiedClientController specifiedClientController = new SpecifiedClientController(clientID);
		
		FXMLStringAndController fxmlStringAndController = new FXMLStringAndController(FXMLReferences.SPECIFIED_CLIENT, specifiedClientController);
		fxmlStringAndController.goToFXML();
	}
	
	@FXML
	public void onClick_btRemove() {
		Optional<ButtonType> optionSelected = GUIUtils.promptCascadeRemovalAlert("Client", "Client Bill");
		if (optionSelected.get() == ButtonType.OK) {
			ClientDAO clientDao = new ClientDAOImpl();
			clientDao.deleteClientByID(clientID);
				
			goToMainMenu();
		} else {
			return;
		}
	}
	
	private void goToMainMenu() {
		MainMenuController mainMenuController = new MainMenuController();
		
		FXMLStringAndController fxmlStringAndController = new FXMLStringAndController(FXMLReferences.MAIN_MENU, mainMenuController);
		fxmlStringAndController.goToFXML();
	}
	
	
	@FXML
	public void onClick_btAddClientBill() {
		AddClientBillController addClientBillController = new AddClientBillController(clientID);
		
		FXMLStringAndController fxmlStringAndController = new FXMLStringAndController(FXMLReferences.ADD_CLIENT_BILL, addClientBillController);
		fxmlStringAndController.goToFXML();
	}
}
