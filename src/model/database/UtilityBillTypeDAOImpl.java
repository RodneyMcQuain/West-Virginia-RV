package model.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLIntegrityConstraintViolationException;

import model.utility.MySQLUtils;

public class UtilityBillTypeDAOImpl implements UtilityBillTypeDAO {

	@Override
	public void insertDefaultUtilityBillsTypes() {
		PreparedStatement ps = null;
		Connection conn = MySQLUtils.getConnection();
		
		try {
			String sql = "INSERT INTO UtilityBillType (utilityBillTypeID, type) " + 
						 "VALUES (?, ?), (?, ?), (?,?), (?,?), (?,?);"; 
			
			ps = conn.prepareStatement(sql);
			ps.setInt(1, 1);
			ps.setString(2, "Electric");
			
			ps.setInt(3, 2);
			ps.setString(4, "Gas");
			
			ps.setInt(5, 3);
			ps.setString(6, "Water");
			
			ps.setInt(7, 4);
			ps.setString(8, "Taxes");
			
			ps.setInt(9, 5);
			ps.setString(10, "Insurance");
			
			ps.executeUpdate();
		} catch (SQLIntegrityConstraintViolationException icve) {
			System.out.println("Default Utility Bill Types already inserted.");
		} catch (Exception ex) {
 			ex.printStackTrace();
 		} finally {
 			MySQLUtils.closeQuietly(ps);
 			MySQLUtils.closeQuietly(conn);
 		}				
	}
}
