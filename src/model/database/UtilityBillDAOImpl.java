package model.database;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Payment;
import model.UtilityBill;
import model.utility.MySQLUtils;
import model.utility.PaymentUtils;

public class UtilityBillDAOImpl implements UtilityBillDAO {
	List<UtilityBill> utilityBills;
	
	@Override
	public List<UtilityBill> getAllUtilityBills() {
		utilityBills = new ArrayList<UtilityBill>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = MySQLUtils.getConnection();
		
		try {
			String sql = "SELECT * " + 
						 "FROM UtilityBill;";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				int utilityBillID = rs.getInt("utilityBillID");
				int utilityBillTypeID = rs.getInt("utilityBillTypeID");
				int paymentTypeID = rs.getInt("paymentTypeID");
				BigDecimal amount = rs.getBigDecimal("amount");
				LocalDate datePaid = rs.getDate("datePaid").toLocalDate();
				
				Payment payment = PaymentUtils.getPaymentByID(paymentTypeID, utilityBillID);
				
				UtilityBill utilityBill = new UtilityBill(utilityBillID, utilityBillTypeID, paymentTypeID, payment, amount, datePaid);
				utilityBills.add(utilityBill);
			}
			
			return utilityBills;
		} catch (Exception ex) {
 			ex.printStackTrace();
 			
 			return null;
 		} finally {
 			MySQLUtils.closeQuietly(rs);
 			MySQLUtils.closeQuietly(ps);
 			MySQLUtils.closeQuietly(conn);
 		}
	}

	@Override
	public List<UtilityBill> getMonthlyUtilityBills() {
		utilityBills = new ArrayList<UtilityBill>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = MySQLUtils.getConnection();
		
		LocalDate today = LocalDate.now();
		LocalDate lastMonth = today.minusMonths(1);
		
		try {
			String sql = "SELECT * " + 
						 "FROM UtilityBill " +
						 "WHERE datePaid " +
						 "BETWEEN ? AND ?;";
			ps = conn.prepareStatement(sql);
			ps.setDate(1, Date.valueOf(lastMonth));
			ps.setDate(2, Date.valueOf(today));
			rs = ps.executeQuery();
			
			while(rs.next()) {
				int utilityBillID = rs.getInt("utilityBillID");
				int utilityBillTypeID = rs.getInt("utilityBillTypeID");
				int paymentTypeID = rs.getInt("paymentTypeID");
				BigDecimal amount = rs.getBigDecimal("amount");
				LocalDate datePaid = rs.getDate("datePaid").toLocalDate();
				
				Payment payment = PaymentUtils.getPaymentByID(paymentTypeID, utilityBillID);
				
				UtilityBill utilityBill = new UtilityBill(utilityBillID, utilityBillTypeID, paymentTypeID, payment, amount, datePaid);
				utilityBills.add(utilityBill);
			}
			
			return utilityBills;
		} catch (Exception ex) {
 			ex.printStackTrace();
 			
 			return null;
 		} finally {
 			MySQLUtils.closeQuietly(rs);
 			MySQLUtils.closeQuietly(ps);
 			MySQLUtils.closeQuietly(conn);
 		}
	}
	
	@Override
	public UtilityBill getUtilityBillByID(int utilityBillID) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = MySQLUtils.getConnection();
		
		try {
			String sql = "SELECT * " + 
						 "FROM UtilityBill " +
						 "WHERE utilityBillID = ?;";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, utilityBillID);
			rs = ps.executeQuery();

			UtilityBill utilityBill = null;
			if (rs.next()) {
				int utilityBillTypeID = rs.getInt("utilityBillTypeID");
				int paymentTypeID = rs.getInt("paymentTypeID");
				BigDecimal amount = rs.getBigDecimal("amount");
				LocalDate datePaid = rs.getDate("datePaid").toLocalDate();
				
				Payment payment = PaymentUtils.getPaymentByID(paymentTypeID, utilityBillID);

				utilityBill = new UtilityBill(utilityBillID, utilityBillTypeID, paymentTypeID, payment, amount, datePaid);
			}
			
			return utilityBill;
		} catch (Exception ex) {
 			ex.printStackTrace();
 			
 			return null;
 		} finally {
 			MySQLUtils.closeQuietly(rs);
 			MySQLUtils.closeQuietly(ps);
 			MySQLUtils.closeQuietly(conn);
 		}	
	}

	@Override
	public void insertUtilityBill(UtilityBill utilityBill) {
		PreparedStatement ps = null;
		Connection conn = MySQLUtils.getConnection();
		
		try {
			String sql = "INSERT INTO UtilityBill (utilityBillID, utilityBillTypeID, paymentTypeID, amount, datePaid) " + 
						 "VALUES (?, ?, ?, ?, ?);"; 
			
			ps = conn.prepareStatement(sql);
			ps.setInt(1, utilityBill.getUtilityBillID());
			ps.setInt(2, utilityBill.getUtilityBillTypeID());
			ps.setInt(3, utilityBill.getPaymentTypeID());
			ps.setBigDecimal(4, utilityBill.getAmount());
			ps.setDate(5, Date.valueOf(utilityBill.getDatePaid()));
			ps.executeUpdate();
		} catch (Exception ex) {
 			ex.printStackTrace();
 		} finally {
 			MySQLUtils.closeQuietly(ps);
 			MySQLUtils.closeQuietly(conn);
 		}				
	}

	@Override
	public void updateUtilityBill(UtilityBill utilityBill) {
		PreparedStatement ps = null;
		Connection conn = MySQLUtils.getConnection();
		
		try {
			String sql = "UPDATE UtilityBill " + 
						 "SET utilityBillTypeID = ?, paymentTypeID = ?, amount = ?, datePaid = ? " +
						 "WHERE clientBillID = ?;"; 
			ps = conn.prepareStatement(sql);
			ps.setInt(1, utilityBill.getUtilityBillTypeID());
			ps.setInt(2, utilityBill.getPaymentTypeID());
			ps.setBigDecimal(3, utilityBill.getAmount());
			ps.setDate(4, Date.valueOf(utilityBill.getDatePaid()));
			ps.executeUpdate();
		} catch (Exception ex) {
 			ex.printStackTrace();
 		} finally {
 			MySQLUtils.closeQuietly(ps);
 			MySQLUtils.closeQuietly(conn);
 		}		
	}

	@Override
	public void deleteUtilityBillByID(int utilityBillID) {
		PreparedStatement ps = null;
		Connection conn = MySQLUtils.getConnection();
		
		try {
			String sql = "DELETE FROM UtilityBill " + 
						 "WHERE utilityBillID = ?;";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, utilityBillID);
			ps.executeUpdate();
		} catch (Exception ex) {
 			ex.printStackTrace();
 		} finally {
 			MySQLUtils.closeQuietly(ps);
 			MySQLUtils.closeQuietly(conn);
 		}		
	}

	@Override
	public int getMostRecentUtilityBillID() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = MySQLUtils.getConnection();
		
		try {
			String sql = "SELECT MAX(utilityBillID) AS mostRecentUtilityBillID " + 
						 "FROM UtilityBill;";  
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			int clientBillID = 0;
			if (rs.next()) {
				clientBillID = rs.getInt("mostRecentUtilityBillID");
			}
			
			return clientBillID;
		} catch (Exception ex) {
 			ex.printStackTrace();
 			
 			return 0;
 		} finally {
 			MySQLUtils.closeQuietly(rs);
 			MySQLUtils.closeQuietly(ps);
 			MySQLUtils.closeQuietly(conn);
 		}	
	}
}
