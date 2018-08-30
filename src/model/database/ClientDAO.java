package model.database;

import java.util.List;

import model.Client;

public interface ClientDAO {
	public List<Client> getAllClients();
	public List<Client> getAllWeeklyClients();
	public List<Client> getAllMonthlyClients();
	public Client getClientByID(int clientID);
	public void insertClient(Client client);
	public void updateClient(Client client);
	public void deleteClientByID(int clientID);
}
