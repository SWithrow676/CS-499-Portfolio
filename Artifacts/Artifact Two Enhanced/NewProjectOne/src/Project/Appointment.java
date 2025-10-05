package Project;

import java.util.Date;
import java.util.regex.Pattern;
import java.util.HashSet;
import java.util.Arrays;

/**
 * Class with basic appointment information such as date and description.
 * Provides validation for all fields upon creation.
 * 
 * @author Stewart Withrow
 */
public class Appointment {

	private static final int ID_MAX_LENGTH = 10;
	private static final int DESC_MAX_LENGTH = 50;
	
	// Pattern to detect common injection characters
	private static final Pattern INJECTION_PATTERN = Pattern.compile("[<>\"'%;()&+]");
	// Blacklist of forbidden substrings to prevent SQL/Script injection
	private static final HashSet<String> BLACKLIST = new HashSet<>(Arrays.asList(
	    "DROP TABLE", "DELETE FROM", "UPDATE", "INSERT", "<script>", "alert(", "SELECT *"
	));
	
	private String apptID;
	private Date date;
	private String description;
			
	/**
     * Constructs an Appointment with validated fields.
     * 
     * @param apptID Unique appointment identifier (<= 10 chars, not null and not empty)
     * @param date Scheduled date (not in the past and not null)
     * @param description Appointment description (<= 50 chars, not null and not empty)
     * @throws IllegalArgumentException if any parameter is invalid
     */
	public Appointment (String apptID, Date date, String description) {
	    validateID(apptID);
	    validateDate(date);
	    validateDescription(description);
	    
	    setApptID(apptID);
	    setDate(date);
	    setDescription(description);
	}
			
	/**
	 * Gets the appointment's unique identifier as an integer.
	 * @return apptID Appointment ID as integer
	 */	
	public int getApptID() {
		return Integer.valueOf(apptID);
	}

	 /**
	  * Gets the appointment's date.
	  * @return date Appointment date
	  */
	public Date getDate() {
		return date;
	}

	 /**
	  * Gets the appointment's description.
	  * @return description Appointment description
	  */
	public String getDescription() {
		return description;
	}
			
	/**
     * Sets the appointment's ID.
     * ID setter is private as it cannot be changed
     * @param apptID Appointment ID
     */
	private void setApptID(String apptID) {
		validateID(apptID);
		this.apptID = apptID;
	}

	/**
	 * Sets the appointment's date.
	 * @param date Appointment date
	 */
	public void setDate(Date date) {
	    validateDate(date);
	    this.date = date;
	}

	/**
	 * Sets the appointment's description.
	 * @param description Appointment description
	 */
	public void setDescription(String description) {
	    validateDescription(description);
	    this.description = description;
	}
	
	/**
	 * Validates the appointment ID.
	 * @param apptID Appointment ID
	 */
	private void validateID(String apptID) {
	    if (apptID == null || apptID.length() > ID_MAX_LENGTH || apptID.isEmpty()) {
	        throw new IllegalArgumentException("Appointment ID must be non-null, non-empty and < " + ID_MAX_LENGTH + " characters.");
	    }
	    
	    validateNoInjection(apptID, "Appointment ID");
	}
	
	/**
	 * Validates the appointment date.
	 * @param date Appointment date
	 */
	private void validateDate(Date date) {
	    if (date == null || date.before(new Date())) {
	        throw new IllegalArgumentException("Date cannot be null or in the past.");
	    }
	}
	
	/**
	 * Validates the appointment description.
	 * @param description Appointment description
	 */
	private void validateDescription(String description) {
	    if (description == null || description.length() > DESC_MAX_LENGTH || description.isEmpty()) {
	        throw new IllegalArgumentException("Description must be non-null, non-empty and < " + DESC_MAX_LENGTH + " characters.");
	    }

	    validateNoInjection(description, "Description");
	    validateBlacklist(description, "Description");
	}

	/**
	 * Validates input against injection attacks.
	 * @param input Input string to validate
	 * @param fieldName Name of the field being validated (for error messages)
	 */
	public static void validateNoInjection(String input, String fieldName) {
	    if (input != null && INJECTION_PATTERN.matcher(input).find()) {
	        throw new IllegalArgumentException(fieldName + " contains illegal characters.");
	    }
	}

	/**
	 * Validates input against a blacklist of forbidden substrings.
	 * @param input Input string to validate
	 * @param fieldName Name of the field being validated (for error messages)
	 */
	public static void validateBlacklist(String input, String fieldName) {
	    for (String forbidden : BLACKLIST) {
	        if (input != null && input.contains(forbidden)) {
	            throw new IllegalArgumentException(fieldName + " contains forbidden content: " + forbidden);
	        }
	    }
	}
}