package model.database;

import model.CheckPayment;

public interface CheckPaymentDAO {
	public CheckPayment getPaymentByID(int checkPaymentID);
	public void insertCheckPayment(CheckPayment checkPayment);
	public void updateCheckPayment(CheckPayment checkPayment);
}
