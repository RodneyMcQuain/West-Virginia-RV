package model.database;

public interface BillDAO {
	public void insertBill(int billTypeID);
	public void deleteBillByID(int billID);
	public int getMostRecentBillID();
}
