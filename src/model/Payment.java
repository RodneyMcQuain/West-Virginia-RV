package model;

public class Payment {
	private int paymentID;
	private int paymentTypeID;
	
	public Payment(int paymentID) {
		this.paymentID = paymentID;
	}
	
	public Payment(int paymentID, int paymentTypeID) {
		this.paymentID = paymentID;
		this.paymentTypeID = paymentTypeID;
	}
	
	public Payment() {
		
	}
	
	public int getPaymentID() {
		return paymentID;
	}
	
	public void setPaymentID(int paymentID) {
		this.paymentID = paymentID;
	}

	public int getPaymentTypeID() {
		return paymentTypeID;
	}

	public void setPaymentTypeID(int paymentTypeID) {
		this.paymentTypeID = paymentTypeID;
	}
}
