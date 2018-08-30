package model.utility;

import model.Payment;
import model.database.CheckPaymentDAO;
import model.database.CheckPaymentDAOImpl;

public class PaymentUtils {
	public final static int CHECK_PAYMENT_ID = 1;
	public final static int DEBIT_PAYMENT_ID = 2;
	
	public final static String CHECK_PAYMENT_STRING = "Check";
	public final static String DEBIT_PAYMENT_STRING = "Debit";
	
	public static int getPaymentTypeIDByString(String paymentType) {
		int paymentTypeID = 0;
		
		if (paymentType.equals(CHECK_PAYMENT_STRING)) {
			paymentTypeID = CHECK_PAYMENT_ID;
		} else if (paymentType.equals(DEBIT_PAYMENT_STRING)) {
			paymentTypeID = DEBIT_PAYMENT_ID;
		}
		
		return paymentTypeID;
	}
	
	public static String getPaymentTypeByID(int paymentTypeID) {
		String paymentType = "";
		
		if (paymentTypeID == CHECK_PAYMENT_ID) {
			paymentType = CHECK_PAYMENT_STRING;
		} else if (paymentTypeID == DEBIT_PAYMENT_ID) {
			paymentType = DEBIT_PAYMENT_STRING;
		}
		
		return paymentType;
	}
	
	public static Payment getPaymentByID(int paymentTypeID, int billID) {
		Payment payment = null;

		if (paymentTypeID == CHECK_PAYMENT_ID) {
			CheckPaymentDAO checkPaymentDao = new CheckPaymentDAOImpl();
			payment = checkPaymentDao.getPaymentByID(billID);
		} else if (paymentTypeID == DEBIT_PAYMENT_ID) {
			//debit dao
		}
		
		return payment;
	}
}
