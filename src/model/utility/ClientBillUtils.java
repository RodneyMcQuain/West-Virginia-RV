package model.utility;

import java.math.BigDecimal;
import java.util.List;

import model.ClientBill;
import model.database.ClientBillDAO;
import model.database.ClientBillDAOImpl;

public class ClientBillUtils {
	public static BigDecimal calculateClientBillTotal() {
		ClientBillDAO clientBillDao = new ClientBillDAOImpl();
		List<ClientBill> clientBills = clientBillDao.getAllClientBills();

		BigDecimal clientBillTotal = BigDecimal.ZERO;
		for (ClientBill clientBill : clientBills) {
			clientBillTotal = clientBillTotal.add(clientBill.getAmount());
		}
		
		return clientBillTotal;
	}
	
	public static BigDecimal calculateClientBillMonthlyTotal() {
		ClientBillDAO clientBillDao = new ClientBillDAOImpl();
		List<ClientBill> monthlyClientBills = clientBillDao.getAllClientBills();
		
		BigDecimal clientBillMonthlyTotal = BigDecimal.ZERO;
		for (ClientBill clientBill : monthlyClientBills) {
			clientBillMonthlyTotal = clientBillMonthlyTotal.add(clientBill.getAmount());
		}
		
		return clientBillMonthlyTotal;
	}
}
