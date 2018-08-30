package model.database;

import java.util.List;

import model.ClientBill;

public interface ClientBillDAO {
	public List<ClientBill> getAllClientBills();
	public List<ClientBill> getAllClientBillsByClientID(int clientID);
	public List<ClientBill> getMonthlyClientBills();
	public ClientBill getClientBillByID(int clientBillID);
	public ClientBill getMostRecentClientBillByClientID(int clientID);
	public void insertClientBill(ClientBill clientBill);
	public void updateClientBill(ClientBill clientBill);
	public void deleteClientBillByID(int clientBillID);
	public int getMostRecentClientBillID();
}
