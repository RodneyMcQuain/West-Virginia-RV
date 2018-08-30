package model.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLIntegrityConstraintViolationException;

import model.utility.MySQLUtils;

public class BillTypeDAOImpl implements BillTypeDAO {
	@Override
	public void insertDefaultBillTypes() {
		PreparedStatement ps = null;
		Connection conn = MySQLUtils.getConnection();
		
		try {
			String sql = "INSERT INTO BillType (BillTypeID, type) " + 
						 "VALUES (?, ?), (?, ?);"; 
			
			ps = conn.prepareStatement(sql);
			ps.setInt(1, 1);
			ps.setString(2, "Client");
			
			ps.setInt(3, 2);
			ps.setString(4, "Utility");
			
			ps.executeUpdate();
		} catch (SQLIntegrityConstraintViolationException icve) {
			System.out.println("Default Bill Types already inserted.");
		} catch (Exception ex) {
 			ex.printStackTrace();
 		} finally {
 			MySQLUtils.closeQuietly(ps);
 			MySQLUtils.closeQuietly(conn);
 		}								
	}
}
