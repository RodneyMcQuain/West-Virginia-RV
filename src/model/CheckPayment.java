package model;

public class CheckPayment extends Payment {
	private String checkNumber;
	
	public CheckPayment(int checkPaymentID, String checkNumber) {
		super(checkPaymentID);
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
