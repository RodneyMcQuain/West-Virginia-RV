package model.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.CheckPayment;
import model.utility.MySQLUtils;

public class CheckPaymentDAOImpl implements CheckPaymentDAO {

	@Override
	public CheckPayment getPaymentByID(int checkPaymentID) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = MySQLUtils.getConnection();
		
		try {
			String sql = "SELECT * " +
					 	 "FROM CheckPayment " +
					 	 "WHERE checkPaymentID = " + checkPaymentID + ";";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			CheckPayment checkPayment = null;
			if (rs.next()) {
				String checkNumber = rs.getString("CheckNumber");
			
				checkPayment = new CheckPayment(checkPaymentID, checkNumber);
			}
			
			return checkPayment;
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
	public void insertCheckPayment(CheckPayment checkPayment) {
		PreparedStatement ps = null;
		Connection conn = MySQLUtils.getConnection();

		try {
			String sql = "INSERT INTO CheckPayment (checkPaymentID, checkNumber) " + 
						 "VALUES (?, ?);"; 
			
			ps = conn.prepareStatement(sql);
			ps.setInt(1, checkPayment.getCheckPaymentID());
			ps.setString(2, checkPayment.getCheckNumber());
			ps.executeUpdate();
		} catch (Exception ex) {
 			ex.printStackTrace();
 		} finally {
 			MySQLUtils.closeQuietly(ps);
 			MySQLUtils.closeQuietly(conn);
 		}		
	}

	@Override
	public void updateCheckPayment(CheckPayment checkPayment) {
		PreparedStatement ps = null;
		Connection conn = MySQLUtils.getConnection();
		
		try {
			String sql = "UPDATE UtilityBill " + 
						 "SET checkNumber = ? " +
						 "WHERE checkPaymentID = ?;"; 
			ps = conn.prepareStatement(sql);
			ps.setString(1, checkPayment.getCheckNumber());
			ps.setInt(2, checkPayment.getCheckPaymentID());
			ps.executeUpdate();
		} catch (Exception ex) {
 			ex.printStackTrace();
 		} finally {
 			MySQLUtils.closeQuietly(ps);
 			MySQLUtils.closeQuietly(conn);
 		}				
	}

}
