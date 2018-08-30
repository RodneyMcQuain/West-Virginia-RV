package model.database;

import java.util.List;

import model.UtilityBill;

public interface UtilityBillDAO {
	public List<UtilityBill> getAllUtilityBills();
	public List<UtilityBill> getMonthlyUtilityBills();
	public UtilityBill getUtilityBillByID(int utilityBillID);
	public void insertUtilityBill(UtilityBill utilityBill);
	public void updateUtilityBill(UtilityBill utilityBill);
	public void deleteUtilityBillByID(int utilityBillID);
	public int getMostRecentUtilityBillID();
}
