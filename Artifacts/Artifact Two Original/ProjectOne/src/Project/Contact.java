// Author: Stewart Withrow

package Project;

public class Contact {
	
	// Private variables initialized
	private String contactID;
	private String firstName;
	private String lastName;
	private String phone;
	private String address;	
	
	// Constructor
	public Contact (String contactID, String firstName, String lastName, 
			String phone, String address) {
		
		// Validates contactID (No longer than 10 char and not null)
		if (contactID == null || contactID.length() > 10) {
			throw new IllegalArgumentException("Invalid contact ID");
		}
		// Validates first name (No longer than 10 char and not null)
		if (firstName == null || firstName.length() > 10) {
			throw new IllegalArgumentException("Invalid first name");
		}
		// Validates last name (No longer than 10 char and not null)
		if (lastName == null || lastName.length() > 10) {
			throw new IllegalArgumentException("Invalid last name");
		}
		// Validates phone number (Only 10 char long and not null)
		if (phone == null || phone.length() != 10) {
			throw new IllegalArgumentException("Invalid phone number");
		}
		// Validates address (No longer than 30 char and not null)
		if (address == null || address.length() > 30) {
			throw new IllegalArgumentException("Invalid address");
		}
		
		setContactID(contactID);
		setFirstName(firstName);
		setLastName(lastName);
		setPhone(phone);
		setAddress(address);
			
	}

	/*
	 * Getters and setters for each variable of the Contact object 
	 * ID setter is private as it cannot be changed
	 */
	
	public int getContactID() {
		return Integer.valueOf(contactID);
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPhone() {
		return phone;
	}

	public String getAddress() {
		return address;
	}
	
	private void setContactID(String contactID) {
		this.contactID = contactID;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
