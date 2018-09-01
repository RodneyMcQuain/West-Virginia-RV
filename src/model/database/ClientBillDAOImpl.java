package model.database;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.ClientBill;
import model.Payment;
import model.utility.MySQLUtils;
import model.utility.PaymentUtils;

public class ClientBillDAOImpl implements ClientBillDAO {
	List<ClientBill> clientBills;
	
	@Override
	public List<ClientBill> getAllClientBillsByClientID(int clientID) {
		clientBills = new ArrayList<ClientBill>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = MySQLUtils.getConnection();
		
		try {
			String sql = "SELECT * " + 
						 "FROM ClientBill " + 
						 "WHERE clientID = ?;";  
			ps = conn.prepareStatement(sql);
			ps.setInt(1, clientID);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				int clientBillID = rs.getInt("clientBillID");
				int paymentTypeID = rs.getInt("paymentTypeID");
				BigDecimal amount = rs.getBigDecimal("amount");
				LocalDate datePaid = rs.getDate("datePaid").toLocalDate();
				
				Payment payment = PaymentUtils.getPaymentByID(paymentTypeID, clientBillID);
				
				ClientBill clientBill = new ClientBill(clientBillID, clientID, paymentTypeID, payment, amount, datePaid);
				clientBills.add(clientBill);
			}
			
			return clientBills;
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
	public List<ClientBill> getAllClientBills() {
		clientBills = new ArrayList<ClientBill>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = MySQLUtils.getConnection();
		
		try {
			String sql = "SELECT * " + 
						 "FROM ClientBill;";  
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				int clientBillID = rs.getInt("clientBillID");
				int clientID = rs.getInt("clientID");
				int paymentTypeID = rs.getInt("paymentTypeID");
				BigDecimal amount = rs.getBigDecimal("amount");
				LocalDate datePaid = rs.getDate("datePaid").toLocalDate();
				
				Payment payment = PaymentUtils.getPaymentByID(paymentTypeID, clientBillID);
				
				ClientBill clientBill = new ClientBill(clientBillID, clientID, paymentTypeID, payment, amount, datePaid);
				clientBills.add(clientBill);
			}
			
			return clientBills;
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
	public List<ClientBill> getMonthlyClientBills() {
		clientBills = new ArrayList<ClientBill>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = MySQLUtils.getConnection();
		
		LocalDate today = LocalDate.now();
		LocalDate lastMonth = today.minusMonths(1);
		
		try {
			String sql = "SELECT * " + 
						 "FROM ClientBill " +
						 "WHERE datePaid " +
						 "BETWEEN ? AND ?;";
			ps = conn.prepareStatement(sql);
			ps.setDate(1, Date.valueOf(lastMonth));
			ps.setDate(2, Date.valueOf(today));
			rs = ps.executeQuery();
			
			while(rs.next()) {
				int clientID = rs.getInt("clientID");
				int clientBillID = rs.getInt("clientBillID");
				int paymentTypeID = rs.getInt("paymentTypeID");
				BigDecimal amount = rs.getBigDecimal("amount");
				LocalDate datePaid = rs.getDate("datePaid").toLocalDate();
								
				Payment payment = PaymentUtils.getPaymentByID(paymentTypeID, clientBillID);
		
				ClientBill clientBill = new ClientBill(clientBillID, clientID, paymentTypeID, payment, amount, datePaid);
				clientBills.add(clientBill);
			}
			
			return clientBills;
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
	public ClientBill getClientBillByID(int clientBillID) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = MySQLUtils.getConnection();
		
		try {
			String sql = "SELECT * " + 
						 "FROM ClientBill " + 
						 "WHERE clientBillID = ?;";  
			ps = conn.prepareStatement(sql);
			ps.setInt(1, clientBillID);
			rs = ps.executeQuery();
			
			ClientBill clientBill = null;
			if (rs.next()) {
				int clientID = rs.getInt("clientID");
				int paymentTypeID = rs.getInt("paymentTypeID");
				BigDecimal amount = rs.getBigDecimal("amount");
				LocalDate datePaid = rs.getDate("datePaid").toLocalDate();
								
				Payment payment = PaymentUtils.getPaymentByID(paymentTypeID, clientBillID);
		
				clientBill = new ClientBill(clientBillID, clientID, paymentTypeID, payment, amount, datePaid);
			}
			
			return clientBill;
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
	public ClientBill getMostRecentClientBillByClientID(int clientID) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = MySQLUtils.getConnection();
		
		try {
			String sql = "SELECT * " + 
						 "FROM ClientBill " + 
						 "WHERE clientID = ? AND " + 
						 "(SELECT MAX(datePaid) FROM ClientBill);";  
			ps = conn.prepareStatement(sql);
			ps.setInt(1, clientID);
			rs = ps.executeQuery();
			
			ClientBill clientBill = null;
			if (rs.next()) {
				int clientBillID = rs.getInt("clientBillID");
				int paymentTypeID = rs.getInt("paymentTypeID");
				BigDecimal amount = rs.getBigDecimal("amount");
				LocalDate datePaid = rs.getDate("datePaid").toLocalDate();
								
				Payment payment = PaymentUtils.getPaymentByID(paymentTypeID, clientBillID);
		
				clientBill = new ClientBill(clientBillID, clientID, paymentTypeID, payment, amount, datePaid);
			}
			
			return clientBill;
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
	public void insertClientBill(ClientBill clientBill) {
		PreparedStatement ps = null;
		Connection conn = MySQLUtils.getConnection();
		
		try {
			String sql = "INSERT INTO ClientBill (clientBillID, clientID, paymentTypeID, amount, datePaid) " + 
						 "VALUES (?, ?, ?, ?, ?);"; 
			
			ps = conn.prepareStatement(sql);
			ps.setInt(1, clientBill.getClientBillID());
			ps.setInt(2, clientBill.getClientID());
			ps.setInt(3, clientBill.getPaymentTypeID());
			ps.setBigDecimal(4, clientBill.getAmount());
			ps.setDate(5, Date.valueOf(clientBill.getDatePaid()));
			ps.executeUpdate();
		} catch (Exception ex) {
 			ex.printStackTrace();
 		} finally {
 			MySQLUtils.closeQuietly(ps);
 			MySQLUtils.closeQuietly(conn);
 		}		
	}

	@Override
	public void updateClientBill(ClientBill clientBill) {
		PreparedStatement ps = null;
		Connection conn = MySQLUtils.getConnection();
		
		try {
			String sql = "UPDATE ClientBill " + 
						 "SET clientID = ?, paymentTypeID = ?, amount = ?, datePaid = ? " +
						 "WHERE clientBillID = ?;"; 
			ps = conn.prepareStatement(sql);
			ps.setInt(1, clientBill.getClientID());
			ps.setInt(2, clientBill.getPaymentTypeID());
			ps.setBigDecimal(3, clientBill.getAmount());
			ps.setDate(4, Date.valueOf(clientBill.getDatePaid()));
			ps.setInt(5, clientBill.getClientBillID());
			ps.executeUpdate();
		} catch (Exception ex) {
 			ex.printStackTrace();
 		} finally {
 			MySQLUtils.closeQuietly(ps);
 			MySQLUtils.closeQuietly(conn);
 		}
	}

	@Override
	public void deleteClientBillByID(int clientBillID) {
		PreparedStatement ps = null;
		Connection conn = MySQLUtils.getConnection();
		
		try {
			String sql = "DELETE FROM ClientBill " + 
						 "WHERE clientBillID = ?;";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, clientBillID);
			ps.executeUpdate();
		} catch (Exception ex) {
 			ex.printStackTrace();
 		} finally {
 			MySQLUtils.closeQuietly(ps);
 			MySQLUtils.closeQuietly(conn);
 		}		
	}

	@Override
	public int getMostRecentClientBillID() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = MySQLUtils.getConnection();
		
		try {
			String sql = "SELECT MAX(clientBillID) AS mostRecentClientBillID " + 
						 "FROM ClientBill;";  
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			int clientBillID = 0;
			if (rs.next()) {
				clientBillID = rs.getInt("mostRecentClientBillID");
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
