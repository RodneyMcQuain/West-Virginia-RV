package application;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.Client;
import model.database.ClientDAO;
import model.database.ClientDAOImpl;
import model.utility.FXMLReferences;
import model.utility.HistoryUtils;

public class AddClientController {
	@FXML public TextField tfFirstName;
	@FXML public TextField tfLastName;
	@FXML public TextField tfEmail;
	@FXML public TextField tfPhoneNumber;
	@FXML public TextField tfLotNumber;
	@FXML public ComboBox<String> cbBillPeriod;
	
	@FXML
	public void initialize() {
		GUIUtils.populateBillPeriodComboBox(cbBillPeriod);
		
		addAddClientToHistory();
	}
	
	private void addAddClientToHistory() {
		AddClientController addClientController = new AddClientController();
		
		FXMLStringAndController fxmlStringAndController = new FXMLStringAndController(FXMLReferences.ADD_CLIENT, addClientController);
		HistoryUtils.addToHistory(fxmlStringAndController);		
	}
	
	@FXML
	public void onClick_btAddClient() {
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
		
		Client client = new Client(firstName, lastName, email, phoneNumber, lotNumber, billPeriod);
		
		ClientDAO clientDao = new ClientDAOImpl();
		clientDao.insertClient(client);
		
		GUIUtils.successfulAdditionAlert("Client");
		reloadAddClient();
	}

	private void reloadAddClient() {
		AddClientController addClientController = new AddClientController();
		
		FXMLStringAndController fxmlStringAndController = new FXMLStringAndController(FXMLReferences.ADD_CLIENT, addClientController);
		fxmlStringAndController.goToFXML();
	}
}
