package model.utility;

import java.math.BigDecimal;
import java.util.List;

import model.UtilityBill;
import model.database.UtilityBillDAO;
import model.database.UtilityBillDAOImpl;

public class UtilityBillUtils {
	public static final String ELECTRIC_STRING = "Electric";
	public static final String GAS_STRING = "Gas";
	public static final String WATER_STRING = "Water";
	public static final String TAXES_STRING = "Taxes";
	public static final String INSURANCE_STRING = "Insurance";

	public static final int ELECTRIC_ID = 1;
	public static final int GAS_ID = 2;
	public static final int WATER_ID = 3;
	public static final int TAXES_ID = 4;
	public static final int INSURANCE_ID = 5;

	public static int getUtilityBillTypeIDByString(String utilityBillType) {
		int utilityBillTypeID = 0;
		
		if (utilityBillType.equals(ELECTRIC_STRING)) {
			utilityBillTypeID = ELECTRIC_ID;
		} else if (utilityBillType.equals(GAS_STRING)) {
			utilityBillTypeID = GAS_ID;
		} else if (utilityBillType.equals(WATER_STRING)) {
			utilityBillTypeID = WATER_ID;
		} else if (utilityBillType.equals(TAXES_STRING)) {
			utilityBillTypeID = TAXES_ID;
		} else if (utilityBillType.equals(INSURANCE_STRING)) {
			utilityBillTypeID = INSURANCE_ID;
		}
		
		return utilityBillTypeID;
	}
	
	public static String getUtilityBillTypeStringByID(int utilityBillTypeID) {
		String utilityBillType = "";
		
		if (utilityBillTypeID == ELECTRIC_ID) {
			utilityBillType = ELECTRIC_STRING;
		} else if (utilityBillTypeID == GAS_ID) {
			utilityBillType = GAS_STRING;
		} else if (utilityBillTypeID == WATER_ID) {
			utilityBillType = WATER_STRING;
		} else if (utilityBillTypeID == TAXES_ID) {
			utilityBillType = TAXES_STRING;
		} else if (utilityBillTypeID == INSURANCE_ID) {
			utilityBillType = INSURANCE_STRING;
		}
		
		return utilityBillType;
	}
	
	public static BigDecimal calculateUtilityBillTotal() {
		UtilityBillDAO utilityBillDao = new UtilityBillDAOImpl();
		List<UtilityBill> utilityBills = utilityBillDao.getAllUtilityBills();
		
		BigDecimal utilityBillTotal = BigDecimal.ZERO;
		for (UtilityBill utilityBill : utilityBills) {
			utilityBillTotal = utilityBillTotal.add(utilityBill.getAmount());
		}
		
		return utilityBillTotal;
	}
	
	public static BigDecimal calculateUtilityBillMonthlyTotal() {
		UtilityBillDAO utilityBillDao = new UtilityBillDAOImpl();
		List<UtilityBill> monthlyUtilityBills = utilityBillDao.getMonthlyUtilityBills();
		
		BigDecimal utilityBillMonthlyTotal = BigDecimal.ZERO;
		for (UtilityBill utilityBill : monthlyUtilityBills) {
			utilityBillMonthlyTotal = utilityBillMonthlyTotal.add(utilityBill.getAmount());
		}
		
		return utilityBillMonthlyTotal;
	}
}
