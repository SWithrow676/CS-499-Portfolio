// Author: Stewart Withrow

package Project;

import java.util.Date;
import java.util.HashMap;

import Project.Appointment;

public class AppointmentService {
	
	// Holds the next ID for an added appointment
	int currentID = 0;
		
	// HashMap used since each entry has a unique key that cannot be duplicated
	public static HashMap<String, Appointment> appointments = new HashMap<String, Appointment>();
		
	// Adds an entry to the Appointment HashMap
	public void addAppointment(Date date, String description) {
			
		String stringID = Integer.toString(currentID);	
		Appointment tempAppoint = new Appointment (stringID, date, description);
		appointments.put(stringID, tempAppoint);

		++currentID;
	}
		
	// Deletes an entry from the Appointment HashMap
	public void deleteAppointment(String ID) {
			
		if (appointments.containsKey(ID)) {
			appointments.remove(ID);
		}
	}
}
