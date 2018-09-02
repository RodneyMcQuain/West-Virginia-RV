package application;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import model.Client;
import model.History;
import model.utility.ClientUtils;
import model.utility.FXMLReferences;

public class UnpaidClientsController {
	@FXML public TableView<Client> tvUnpaidClients = new TableView<>();
	@FXML public TableColumn<Client, String> tcFirstName;
	@FXML public TableColumn<Client, String> tcLastName;
	@FXML public TableColumn<Client, String> tcEmail;
	@FXML public TableColumn<Client, String> tcPhoneNumber;
	@FXML public TableColumn<Client, Integer> tcLotNumber;
	@FXML public TableColumn<Client, String> tcBillPeriod;

	@FXML
	public void initialize() {
		setCellValueFactory();
		tvUnpaidClients.setItems(getClientData());
		tvUnpaidClients.setOnMouseClicked(e -> onDoubleClick_goToSpecifiedClient(e));
		
		addUnpaidClientsToHistory();
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
		List<Client> monthlyClients = ClientUtils.getUnpaidMonthlyClients();
		List<Client> weeklyClients = ClientUtils.getUnpaidWeeklyClients();
		weeklyClients.addAll(monthlyClients);
		List<Client> unpaidClients = weeklyClients;
		
		ObservableList<Client> olUnpaidClients = FXCollections.observableArrayList(unpaidClients);
		
		return olUnpaidClients;
	}
	
	private void onDoubleClick_goToSpecifiedClient(MouseEvent e) {
		final int DOUBLE_CLICK = 2;
		
		if (e.getButton().equals(MouseButton.PRIMARY) && e.getClickCount() == DOUBLE_CLICK) {
			Client client = tvUnpaidClients.getSelectionModel().getSelectedItem();
			
			goToSpecifiedClient(client.getClientID());
		}
	}
	
	private void goToSpecifiedClient(int clientID) {
		SpecifiedClientController specifiedClientController = new SpecifiedClientController(clientID);
		
		FXMLStringAndController fxmlStringAndController = new FXMLStringAndController(FXMLReferences.SPECIFIED_CLIENT, specifiedClientController);
		fxmlStringAndController.goToFXML();
	}
	
	private void addUnpaidClientsToHistory() {
		UnpaidClientsController unpaidClientsController = new UnpaidClientsController();
		
		FXMLStringAndController fxmlStringAndController = new FXMLStringAndController(FXMLReferences.UNPAID_CLIENTS, unpaidClientsController);
		History.addToHistory(fxmlStringAndController);		
	}
}
