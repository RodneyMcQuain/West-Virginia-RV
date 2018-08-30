package model;

public class Client {
	private int clientID;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private int lotNumber;
	private String billPeriod;
	
	public Client(int clientID, String firstName, String lastName, String email, String phoneNumber, int lotNumber, String billPeriod) {
		this.clientID = clientID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.lotNumber = lotNumber;
		this.billPeriod = billPeriod;
	}
	
	public Client(String firstName, String lastName, String email, String phoneNumber, int lotNumber, String billPeriod) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.lotNumber = lotNumber;
		this.billPeriod = billPeriod;
	}
	
	@Override
	public String toString() {
		return firstName + " " + lastName;
	}

	public int getClientID() {
		return clientID;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public int getLotNumber() {
		return lotNumber;
	}

	public String getBillPeriod() {
		return billPeriod;
	}
}
