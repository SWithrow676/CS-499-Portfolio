// Author: Stewart Withrow

package Project;

import java.util.Date;

public class Appointment {
	
	// Private variables initialized
	private String apptID;
	private Date date;
	private String description;
			
	// Constructor
	public Appointment (String apptID, Date date, String description) {
				
		// Validates apptID (No longer than 10 char, not null, and required)
		if (apptID == null || apptID.length() > 10 || apptID.equals("")) {
			throw new IllegalArgumentException("Invalid ID");
		}
		// Validates date (Cannot be in the past, not null, and required)
		if (date == null || date.before(new Date())) {
			throw new IllegalArgumentException("Invalid date");
		}
		// Validates description (No longer than 50 char, not null, and required)
		if (description == null || description.length() > 50 || description.equals("")) {
			throw new IllegalArgumentException("Invalid description");
		}
				
		setApptID(apptID);
		setDate(date);
		setDescription(description);
				
				
	}
			
	/*
	 * Getters and setters for each variable of the Appointment object 
	 * ID setter is private as it cannot be changed
	 */
			
	public int getApptID() {
		return Integer.valueOf(apptID);
	}

	public Date getDate() {
		return date;
	}

	public String getDescription() {
		return description;
	}
			
	private void setApptID(String apptID) {
		this.apptID = apptID;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
