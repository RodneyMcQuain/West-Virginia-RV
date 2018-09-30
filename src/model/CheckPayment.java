package model;

import model.utility.PaymentUtils;

public class CheckPayment extends Payment {
	private String checkNumber;
	
	public CheckPayment(int checkPaymentID, String checkNumber) {
		super(checkPaymentID, PaymentUtils.CHECK_PAYMENT_ID);
		this.checkNumber = checkNumber;
	}
	
	public CheckPayment(String checkNumber) {
		this.checkNumber = checkNumber;
	}

	public String getCheckNumber() {
		return checkNumber;
	}

	public void setCheckNumber(String checkNumber) {
		this.checkNumber = checkNumber;
	}
}
