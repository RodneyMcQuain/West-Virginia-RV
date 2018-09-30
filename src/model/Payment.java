package model;

public class Payment {
	private int paymentID;
	
	public Payment(int paymentID) {
		this.paymentID = paymentID;
	}
	
	public Payment() {
		
	}
	
	public int getPaymentID() {
		return paymentID;
	}
	
	public void setPaymentID(int paymentID) {
		this.paymentID = paymentID;
	}
}
