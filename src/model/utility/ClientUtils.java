package model.utility;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Client;
import model.ClientBill;
import model.database.ClientBillDAO;
import model.database.ClientBillDAOImpl;
import model.database.ClientDAO;
import model.database.ClientDAOImpl;

public class ClientUtils {
	public static final String WEEKLY_BILL_PERIOD = "Weekly";
	public static final String MONTHLY_BILL_PERIOD = "Monthly";
	
	public static int getNumberOfClients() {
		ClientDAO clientDao = new ClientDAOImpl();
		List<Client> clients = clientDao.getAllClients();
		
		return clients.size();
	}
	
	public static int getNumberOfUnpaidClients(List<Client> weeklyClients, List<Client> monthlyClients) {		
		List<Client> unpaidWeeklyClients = getUnpaidWeeklyClients(weeklyClients);
		int numberOfUnpaidWeeklyClients = unpaidWeeklyClients.size();
		
		List<Client> unpaidMonthlyClients = getUnpaidMonthlyClients(monthlyClients);
		int numberOfUnpaidMonthlyClients = unpaidMonthlyClients.size();
		
		return numberOfUnpaidWeeklyClients + numberOfUnpaidMonthlyClients;
	}
	
	public static List<Client> getUnpaidWeeklyClients(List<Client> weeklyClients) {
		ClientBillDAO clientBillDao = new ClientBillDAOImpl();
		List<Client> unpaidWeeklyClients = new ArrayList<Client>();
		LocalDate today = LocalDate.now();
		LocalDate lastMonth = today.minusMonths(1);
		
		for (Client client : weeklyClients) {
			int clientID = client.getClientID();
			
			ClientBill clientBill = clientBillDao.getMostRecentClientBillByClientID(clientID);
			if (clientBill != null) {
				LocalDate datePaid = clientBill.getDatePaid();
			
				if (datePaid.isBefore(lastMonth)) {
					unpaidWeeklyClients.add(client);
				}
			} else { //client has no bills
				unpaidWeeklyClients.add(client);
			}
		}
		
		return unpaidWeeklyClients;
	}
	
	public static List<Client> getUnpaidMonthlyClients(List<Client> monthlyClients) {
		ClientBillDAO clientBillDao = new ClientBillDAOImpl();
		List<Client> unpaidMonthlyClients = new ArrayList<Client>();

		LocalDate today = LocalDate.now();
		LocalDate lastMonth = today.minusMonths(1);
		
		for (Client client : monthlyClients) {
			int clientID = client.getClientID();
			
			ClientBill clientBill = clientBillDao.getMostRecentClientBillByClientID(clientID);
			if (clientBill != null) {
				LocalDate datePaid = clientBill.getDatePaid();
			
				if (datePaid.isBefore(lastMonth)) {
					unpaidMonthlyClients.add(client);
				}
			} else { //client has no bills
				unpaidMonthlyClients.add(client);
			}
		}
		
		return unpaidMonthlyClients;
	}
}
