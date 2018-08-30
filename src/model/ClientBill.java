package model;

import java.math.BigDecimal;
import java.time.LocalDate;

import model.database.ClientDAO;
import model.database.ClientDAOImpl;

public class ClientBill {
	private int clientBillID;
	private int clientID;
	private int paymentTypeID;
	private Payment payment;
	private BigDecimal amount;
	private LocalDate datePaid;
	
	public ClientBill(int clientBillID, int clientID, int paymentTypeID, Payment payment, BigDecimal amount, LocalDate datePaid) {
		this.clientBillID = clientBillID;
		this.clientID = clientID;
		this.paymentTypeID = paymentTypeID;
		this.payment = payment;
		this.amount = amount;
		this.datePaid = datePaid;
	}
	
	public ClientBill(int clientID, int paymentTypeID, Payment payment, BigDecimal amount, LocalDate datePaid) {
		this.clientID = clientID;
		this.paymentTypeID = paymentTypeID;
		this.payment = payment;
		this.amount = amount;
		this.datePaid = datePaid;
	}
	
	public String toString() {
		ClientDAO clientDao = new ClientDAOImpl();
		Client client = clientDao.getClientByID(clientID);
		
		return client.toString() + " paid at " + datePaid;
	}
	
	public int getClientBillID() {
		return clientBillID;
	}

	public int getClientID() {
		return clientID;
	}
	
	public int getPaymentTypeID() {
		return paymentTypeID;
	}
	
	public Payment getPayment() {
		return payment;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public LocalDate getDatePaid() {
		return datePaid;
	}
}
