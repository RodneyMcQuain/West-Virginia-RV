package model;

import model.utility.PaymentUtils;

public class DebitPayment extends Payment {
	private String debitTransactionNumber;
	
	public DebitPayment(int debitPaymentID, String debitTransactionNumber) {
		super(debitPaymentID, PaymentUtils.DEBIT_PAYMENT_ID);
		this.debitTransactionNumber = debitTransactionNumber;
	}
	
	public DebitPayment(String debitTransactionNumber) {
		this.debitTransactionNumber = debitTransactionNumber;
	}

	public String getDebitTransactionNumber() {
		return debitTransactionNumber;
	}

}
