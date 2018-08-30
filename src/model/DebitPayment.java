package model;

public class DebitPayment extends Payment {
	private int debitPaymentID;
	private String debitTransactionNumber;
	
	public DebitPayment(int debitPaymentID, String debitTransactionNumber) {
		this.debitPaymentID = debitPaymentID;
		this.debitTransactionNumber = debitTransactionNumber;
	}
	
	public DebitPayment(String debitTransactionNumber) {
		this.debitTransactionNumber = debitTransactionNumber;
	}

	public int getDebitPaymentID() {
		return debitPaymentID;
	}

	public String getDebitTransactionNumber() {
		return debitTransactionNumber;
	}
}
