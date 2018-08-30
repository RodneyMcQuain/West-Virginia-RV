package model.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.utility.MySQLUtils;

public class BillDAOImpl implements BillDAO {
	@Override
	public void insertBill(int billTypeID) {
		PreparedStatement ps = null;
		Connection conn = MySQLUtils.getConnection();
		
		try {
			String sql = "INSERT INTO Bill (billTypeID) " + 
						 "VALUES(?)";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, billTypeID);
			ps.executeUpdate();
		} catch (Exception ex) {
 			ex.printStackTrace();
 		} finally {
 			MySQLUtils.closeQuietly(ps);
 			MySQLUtils.closeQuietly(conn);
 		}				
	}

	@Override
	public void deleteBillByID(int billID) {
		PreparedStatement ps = null;
		Connection conn = MySQLUtils.getConnection();
		
		try {
			String sql = "DELETE FROM Bill " + 
						 "WHERE billID = ?;";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, billID);
			ps.executeUpdate();
		} catch (Exception ex) {
 			ex.printStackTrace();
 		} finally {
 			MySQLUtils.closeQuietly(ps);
 			MySQLUtils.closeQuietly(conn);
 		}				
	}
	
	@Override
	public int getMostRecentBillID() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = MySQLUtils.getConnection();
		
		try {
			String sql = "SELECT MAX(billID) AS mostRecentBillID " + 
						 "FROM Bill;";  
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			int clientBillID = 0;
			if (rs.next()) {
				clientBillID = rs.getInt("mostRecentBillID");
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