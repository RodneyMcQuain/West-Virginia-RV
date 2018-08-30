package model.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.database.BillTypeDAO;
import model.database.BillTypeDAOImpl;
import model.database.PaymentTypeDAO;
import model.database.PaymentTypeDAOImpl;
import model.database.UtilityBillTypeDAO;
import model.database.UtilityBillTypeDAOImpl;

public class MySQLUtils {
	public static Connection getConnection() {
	    Connection conn = null;

	    try {
	        Class.forName(MySQLCredentials.DRIVER).newInstance();
	        conn = DriverManager.getConnection(MySQLCredentials.URL + MySQLCredentials.DATABASE_NAME, 
	        								   MySQLCredentials.USER_NAME, MySQLCredentials.PASSWORD);
	    } catch (Exception e) {
	        e.printStackTrace();
	        unableToConnectToDatabaseAlert();
	        System.exit(0);
	    }
	    
	    return conn;
	}

	private static void unableToConnectToDatabaseAlert() {
    	Alert alConnectionError = new Alert(AlertType.ERROR);
    	alConnectionError.setTitle("Can't Connect to Database.");
    	alConnectionError.setHeaderText(null);
    	alConnectionError.setContentText("Error connecting to the database.");
    	alConnectionError.showAndWait();	
	}
	
	public static void initializeStaticRecords() {
		UtilityBillTypeDAO utilityBillTypeDao = new UtilityBillTypeDAOImpl();
		utilityBillTypeDao.insertDefaultUtilityBillsTypes();
		
		PaymentTypeDAO paymentTypeDao = new PaymentTypeDAOImpl();
		paymentTypeDao.insertDefaultPaymentTypes();
		
		BillTypeDAO billTypeDao = new BillTypeDAOImpl();
		billTypeDao.insertDefaultBillTypes();
	}
	
	public static void closeQuietly(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void closeQuietly(Statement stmt) {
		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void closeQuietly(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
