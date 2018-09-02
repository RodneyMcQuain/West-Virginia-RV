package application;

import java.math.BigDecimal;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import model.Client;
import model.History;
import model.database.ClientDAO;
import model.database.ClientDAOImpl;
import model.utility.BillUtils;
import model.utility.ClientUtils;
import model.utility.FXMLReferences;

public class MainMenuController {
	@FXML public TableView<Client> tvClients = new TableView<>();
	@FXML public TableColumn<Client, String> tcFirstName;
	@FXML public TableColumn<Client, String> tcLastName;
	@FXML public TableColumn<Client, String> tcEmail;
	@FXML public TableColumn<Client, String> tcPhoneNumber;
	@FXML public TableColumn<Client, Integer> tcLotNumber;
	@FXML public TableColumn<Client, String> tcBillPeriod;
	@FXML public Label lblNumberOfUnpaidClients;
	@FXML public Label lblTotalRevenue;
	@FXML public Label lblTotalMonthlyRevenue;
	
	@FXML
	public void initialize() {
		setCellValueFactory();
		tvClients.setItems(getClientData());
		tvClients.setOnMouseClicked(e -> onDoubleClick_goToSpecifiedClient(e));
		
		setNumberOfUnpaidClients();
		setTotalRevenue();
		setTotalMonthlyRevenue();
		
		addMainMenuToHistory();
	}
	
	private void setCellValueFactory() {
		tcFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		tcLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		tcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		tcPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
		tcLotNumber.setCellValueFactory(new PropertyValueFactory<>("lotNumber"));
		tcBillPeriod.setCellValueFactory(new PropertyValueFactory<>("billPeriod"));		
	}
	
	private ObservableList<Client> getClientData() {
		ClientDAO clientDao = new ClientDAOImpl();
		List<Client> clients = clientDao.getAllClients();
		
		ObservableList<Client> olClients = FXCollections.observableArrayList(clients);
		
		return olClients;
	}

	private void onDoubleClick_goToSpecifiedClient(MouseEvent e) {
		final int DOUBLE_CLICK = 2;
		
		if (e.getButton().equals(MouseButton.PRIMARY) && e.getClickCount() == DOUBLE_CLICK) {
			Client client = tvClients.getSelectionModel().getSelectedItem();
			
			goToSpecifiedClient(client.getClientID());
		}
	}
	
	private void goToSpecifiedClient(int clientID) {        
		SpecifiedClientController specifiedClientController = new SpecifiedClientController(clientID);

		FXMLStringAndController fxmlStringAndController = new FXMLStringAndController(FXMLReferences.SPECIFIED_CLIENT, specifiedClientController);
		fxmlStringAndController.goToFXML();
	}
	
	private void setNumberOfUnpaidClients() {
		int numberOfUnpaidClients = ClientUtils.gettNumberOfUnpaidClients();
		
		if (numberOfUnpaidClients == 0) 
			lblNumberOfUnpaidClients.setTextFill(Color.GREEN);
		else
			lblNumberOfUnpaidClients.setTextFill(Color.RED);

		lblNumberOfUnpaidClients.setText(String.valueOf(numberOfUnpaidClients));
	}
	
	private void setTotalRevenue() {
		BigDecimal totalRevenue = BillUtils.calculateTotalRevenue();
		
		if (totalRevenue.compareTo(BigDecimal.ZERO) < 0)
			lblTotalRevenue.setTextFill(Color.RED);
		else 
			lblTotalRevenue.setTextFill(Color.GREEN);
		
		lblTotalRevenue.setText(totalRevenue.toString());
	}
	
	private void setTotalMonthlyRevenue() {
		BigDecimal totalMonthlyRevenue = BillUtils.calculateTotalMonthlyRevenue();
		
		if (totalMonthlyRevenue.compareTo(BigDecimal.ZERO) < 0)
			lblTotalMonthlyRevenue.setTextFill(Color.RED);
		else 
			lblTotalMonthlyRevenue.setTextFill(Color.GREEN);
		
		lblTotalMonthlyRevenue.setText(totalMonthlyRevenue.toString());
	}
	
	private void addMainMenuToHistory() {
		MainMenuController mainMenuController = new MainMenuController();
		
		FXMLStringAndController fxmlStringAndController = new FXMLStringAndController(FXMLReferences.MAIN_MENU, mainMenuController);
		History.addToHistory(fxmlStringAndController);		
	}
	
	@FXML
	public void onClick_btAddClient() {
		AddClientController addClientController = new AddClientController();
		
		FXMLStringAndController fxmlStringAndController = new FXMLStringAndController(FXMLReferences.ADD_CLIENT, addClientController);
		fxmlStringAndController.goToFXML();
	}
	
	@FXML
	public void onClick_btUtilityBills() {
		UtilityBillsController utilityBillsController = new UtilityBillsController();
		
		FXMLStringAndController fxmlStringAndController = new FXMLStringAndController(FXMLReferences.UTILITY_BILLS, utilityBillsController);
		fxmlStringAndController.goToFXML();
	}
	
	@FXML 
	public void onClick_btUnpaidClients() {        
		UnpaidClientsController unpaidClientsController = new UnpaidClientsController();

		FXMLStringAndController fxmlStringAndController = new FXMLStringAndController(FXMLReferences.UNPAID_CLIENTS, unpaidClientsController);
		fxmlStringAndController.goToFXML();
	}
}
