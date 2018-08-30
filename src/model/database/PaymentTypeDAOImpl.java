package model.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLIntegrityConstraintViolationException;

import model.utility.MySQLUtils;

public class PaymentTypeDAOImpl implements PaymentTypeDAO {

	@Override
	public void insertDefaultPaymentTypes() {
		PreparedStatement ps = null;
		Connection conn = MySQLUtils.getConnection();
		
		try {
			String sql = "INSERT INTO PaymentType (paymentTypeID, type) " + 
						 "VALUES (?, ?), (?, ?);"; 
			
			ps = conn.prepareStatement(sql);
			ps.setInt(1, 1);
			ps.setString(2, "Check");
			
			ps.setInt(3, 2);
			ps.setString(4, "Debit");
			
			ps.executeUpdate();
		} catch (SQLIntegrityConstraintViolationException icve) {
			System.out.println("Default Payment Types already inserted.");
		} catch (Exception ex) {
 			ex.printStackTrace();
 		} finally {
 			MySQLUtils.closeQuietly(ps);
 			MySQLUtils.closeQuietly(conn);
 		}						
	}
}
