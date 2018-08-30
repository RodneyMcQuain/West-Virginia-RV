package model;

import java.math.BigDecimal;
import java.time.LocalDate;

import model.utility.GeneralUtils;
import model.utility.UtilityBillUtils;

public class UtilityBill {
	private int utilityBillID;
	private int utilityBillTypeID;
	private int paymentTypeID;
	private Payment payment;
	private BigDecimal amount;
	private LocalDate datePaid;
	
	public UtilityBill(int utilityBillID, int utilityBillTypeID, int paymentTypeID, Payment payment, BigDecimal amount, LocalDate datePaid) {
		this.utilityBillID = utilityBillID;
		this.utilityBillTypeID = utilityBillTypeID;
		this.paymentTypeID = paymentTypeID;
		this.payment = payment;
		this.amount = amount;
		this.datePaid = datePaid;
	}
	
	public UtilityBill(int utilityBillTypeID, int paymentTypeID, Payment payment, BigDecimal amount, LocalDate datePaid) {
		this.utilityBillTypeID = utilityBillTypeID;
		this.paymentTypeID = paymentTypeID;
		this.payment = payment;
		this.amount = amount;
		this.datePaid = datePaid;
	}
	
	public String toString() {
		String utilityBillType = UtilityBillUtils.getUtilityBillTypeStringByID(utilityBillTypeID);
		String datePaidString = GeneralUtils.formatDate(datePaid.toString());
		
		return utilityBillType + " - " + datePaidString;
	}
	
	public int getUtilityBillID() {
		return utilityBillID;
	}

	public int getUtilityBillTypeID() {
		return utilityBillTypeID;
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
