package Project;

import java.util.Date;
import java.util.HashMap;

/**
 * Service class to manage Appointment objects.
 * Provides methods to add, delete, and edit appointments.
 * 
 * @author Stewart Withrow
 */
public class AppointmentService {
	
	// Holds the next ID for an added appointment
	private int currentID = 0;
		
	// HashMap to hold appointment objects, keyed by their unique ID
	public static HashMap<String, Appointment> appointments = new HashMap<String, Appointment>();
		
	 /**
     * Adds a new Appointment to the map.
     * 
     * @param date Scheduled date of the appointment
     * @param description Appointment description
     * @throws IllegalArgumentException if parameters are invalid
     */
	public void addAppointment(Date date, String description) {
		String stringID = Integer.toString(currentID);	
		Appointment appointment = new Appointment (stringID, date, description);
		appointments.put(stringID, appointment);

		++currentID;
	}
		
	 /**
	 * Deletes an appointment from the map by ID.
	 * 
	 * @param ID Appointment ID to delete
	 */
	public void deleteAppointment(String ID) {
        appointments.remove(ID);
    }
	
	 /**
	  * Edits an appointment date from the map by ID.
	  * 
	  * @param apptID Appointment ID
	  * @param date New appointment date
	  */
	public void editDate(String apptID, Date date) {
		Appointment appointment = appointments.get(apptID);
		
		if (appointment != null) {
			appointment.setDate(date);
		}
	}
	
	 /**
	  * Edits an appointment description from the map by ID.
	  * 
	  * @param apptID Appointment ID
	  * @param description New appointment description
	  */
	public void editDescription(String apptID, String description) {
		Appointment appointment = appointments.get(apptID);
		
		if (appointment != null) {
			appointment.setDescription(description);
		}
	}
}