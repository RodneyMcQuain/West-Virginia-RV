package model;

public class CheckPayment extends Payment {
	private int checkPaymentID;
	private String checkNumber;
	
	public CheckPayment(int checkPaymentID, String checkNumber) {
		this.checkPaymentID = checkPaymentID;
		this.checkNumber = checkNumber;
	}
	
	public CheckPayment(String checkNumber) {
		this.checkNumber = checkNumber;
	}

	public int getCheckPaymentID() {
		return checkPaymentID;
	}

	public String getCheckNumber() {
		return checkNumber;
	}

	public void setCheckPaymentID(int checkPaymentID) {
		this.checkPaymentID = checkPaymentID;
	}

	public void setCheckNumber(String checkNumber) {
		this.checkNumber = checkNumber;
	}
}
