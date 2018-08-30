package model.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Client;
import model.utility.MySQLUtils;

public class ClientDAOImpl implements ClientDAO {
	List<Client> clients;
	
	@Override
	public List<Client> getAllClients() {
		clients = new ArrayList<Client>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = MySQLUtils.getConnection();
		
		try {
			String sql = "SELECT * " + 
						 "FROM Client;";  
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				int clientID = rs.getInt("clientID");
				String firstName = rs.getString("firstName");
				String lastName = rs.getString("lastName");
				String email = rs.getString("email");
				String phoneNumber = rs.getString("phoneNumber");
				int lotNumber = rs.getInt("lotNumber");
				String billPeriod = rs.getString("billPeriod");
				
				Client client = new Client(clientID, firstName, lastName, email, phoneNumber, lotNumber, billPeriod);
				clients.add(client);
			}
			
			return clients;
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
	public List<Client> getAllWeeklyClients() {
		clients = new ArrayList<Client>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = MySQLUtils.getConnection();
		
		try {
			String sql = "SELECT * " + 
						 "FROM Client " + 
						 "WHERE billPeriod = 'Weekly';";  
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				int clientID = rs.getInt("clientID");
				String firstName = rs.getString("firstName");
				String lastName = rs.getString("lastName");
				String email = rs.getString("email");
				String phoneNumber = rs.getString("phoneNumber");
				int lotNumber = rs.getInt("lotNumber");
				String billPeriod = rs.getString("billPeriod");
				
				Client client = new Client(clientID, firstName, lastName, email, phoneNumber, lotNumber, billPeriod);
				clients.add(client);
			}
			
			return clients;
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
	public List<Client> getAllMonthlyClients() {
		clients = new ArrayList<Client>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = MySQLUtils.getConnection();
		
		try {
			String sql = "SELECT * " + 
						 "FROM Client " + 
						 "WHERE billPeriod = 'Monthly';";  
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				int clientID = rs.getInt("clientID");
				String firstName = rs.getString("firstName");
				String lastName = rs.getString("lastName");
				String email = rs.getString("email");
				String phoneNumber = rs.getString("phoneNumber");
				int lotNumber = rs.getInt("lotNumber");
				String billPeriod = rs.getString("billPeriod");
				
				Client client = new Client(clientID, firstName, lastName, email, phoneNumber, lotNumber, billPeriod);
				clients.add(client);
			}
			
			return clients;
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
	public Client getClientByID(int clientID) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = MySQLUtils.getConnection();
		
		try {
			String sql = "SELECT * " + 
						 "FROM Client " +
						 "WHERE clientID = ?;";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, clientID);
			rs = ps.executeQuery();

			Client client = null;
			if (rs.next()) {
				String firstName = rs.getString("firstName");
				String lastName = rs.getString("lastName");
				String email = rs.getString("email");
				String phoneNumber = rs.getString("phoneNumber");
				int lotNumber = rs.getInt("lotNumber");
				String billPeriod = rs.getString("billPeriod");
				
				client = new Client(clientID, firstName, lastName, email, phoneNumber, lotNumber, billPeriod);
			}
			
			return client;
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
	public void insertClient(Client client) {
		PreparedStatement ps = null;
		Connection conn = MySQLUtils.getConnection();
		
		try {
			String sql = "INSERT INTO Client (firstName, lastName, email, phoneNumber, lotNumber, billPeriod) " +
						 "VALUES (?, ?, ?, ?, ?, ?);";
			ps = conn.prepareStatement(sql);
			ps.setString(1, client.getFirstName());
			ps.setString(2, client.getLastName() );
			ps.setString(3, client.getEmail());
			ps.setString(4, client.getPhoneNumber());
			ps.setInt(5, client.getLotNumber());
			ps.setString(6, client.getBillPeriod() );
			ps.executeUpdate();			
		} catch (Exception ex) {
 			ex.printStackTrace();
 		} finally {
 			MySQLUtils.closeQuietly(ps);
 			MySQLUtils.closeQuietly(conn);
 		}	
	}

	@Override
	public void updateClient(Client client) {
		PreparedStatement ps = null;
		Connection conn = MySQLUtils.getConnection();
		
		try {
			String sql = "UPDATE Client " +
						 "SET firstName = ?, lastName = ?, email = ?, phoneNumber = ?, lotNumber = ?, billPeriod = ? " + 
						 "WHERE clientID = ?;";
			ps = conn.prepareStatement(sql);
			ps.setString(1, client.getFirstName());
			ps.setString(2, client.getLastName() );
			ps.setString(3, client.getEmail());
			ps.setString(4, client.getPhoneNumber());
			ps.setInt(5, client.getLotNumber());
			ps.setString(6, client.getBillPeriod());
			ps.setInt(7, client.getClientID());
			ps.executeUpdate();
		} catch (Exception ex) {
 			ex.printStackTrace();
 		} finally {
 			MySQLUtils.closeQuietly(ps);
 			MySQLUtils.closeQuietly(conn);
 		}	
	}

	@Override
	public void deleteClientByID(int clientID) {
		PreparedStatement ps = null;
		Connection conn = MySQLUtils.getConnection();
		
		try {
			String sql = "DELETE FROM Client " + 
						 "WHERE clientID = ?;";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, clientID);
			ps.executeUpdate();
		} catch (Exception ex) {
 			ex.printStackTrace();
 		} finally {
 			MySQLUtils.closeQuietly(ps);
 			MySQLUtils.closeQuietly(conn);
 		}	
	}
}
