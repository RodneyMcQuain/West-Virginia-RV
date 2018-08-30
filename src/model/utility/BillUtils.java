package model.utility;

import java.math.BigDecimal;

public class BillUtils {
	public static final int CLIENT_BILL_ID = 1;
	public static final int UTILITY_BILL_ID = 2;
	
	public static BigDecimal calculateTotalRevenue() {
		BigDecimal clientBillTotal = ClientBillUtils.calculateClientBillTotal();
		BigDecimal utilityBillTotal = UtilityBillUtils.calculateUtilityBillTotal();
		
		BigDecimal totalRevenue = clientBillTotal.subtract(utilityBillTotal);
		
		return totalRevenue;
	}
	
	public static BigDecimal calculateTotalMonthlyRevenue() {
		BigDecimal clientBillMonthlyTotal = ClientBillUtils.calculateClientBillTotal();
		BigDecimal utilityBillMonthlyTotal = UtilityBillUtils.calculateUtilityBillTotal();
		
		BigDecimal totalMonthlyRevenue = clientBillMonthlyTotal.subtract(utilityBillMonthlyTotal);
		
		return totalMonthlyRevenue;
	}
}
