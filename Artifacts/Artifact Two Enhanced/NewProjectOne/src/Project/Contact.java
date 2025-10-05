package Project;

import java.util.regex.Pattern;

/**
 * Class with basic contact information such as name, phone, and address.
 * Provides validation for all fields upon creation.
 * 
 * @author Stewart Withrow
 */
public class Contact {
	
	private static final int VAR_MAX_LENGTH = 10;
	private static final int ADDR_MAX_LENGTH = 30;
	private static final Pattern INJECTION_PATTERN = Pattern.compile("[<>\"'%;()&+]");
	
	private String contactID;
	private String firstName;
	private String lastName;
	private String phone;
	private String address;	
	
	/**
     * Constructs a Contact with validated fields.
     * 
     * @param contactID Unique contact identifier (<= 10 chars, not null or empty)
     * @param firstName First name (<= 10 chars, not null and not empty)
     * @param lastName Last name (<= 10 chars, not null and not empty)
     * @param phone Phone number (exactly 10 chars and not null)
     * @param address Address (<= 30 chars, not null and not empty) 
     * @throws IllegalArgumentException if any parameter is invalid
     */
	public Contact (String contactID, String firstName, String lastName, 
			String phone, String address) {
		
		validateContactID(contactID);
		validateFirstName(firstName);
		validateLastName(lastName);
		validatePhone(phone);
		validateAddress(address);
		
		setContactID(contactID);
		setFirstName(firstName);
		setLastName(lastName);
		setPhone(phone);
		setAddress(address);
			
	}

	/**
     * Gets the contact's unique identifier
     * @return Contact ID
     */
	public String getContactID() {
		return contactID;
	}

	/**
	 * Gets the contact's first name.
	 * @return First name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Gets the contact's last name.
	 * @return Last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Gets the contact's phone number.
	 * @return Phone number
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Gets the contact's address.
	 * @return Address
	 */
	public String getAddress() {
		return address;
	}
	
	/**
     * Sets the contact's ID.
     * ID setter is private as it cannot be changed
     * @param contactID Contact ID
     */
	private void setContactID(String contactID) {
		validateContactID(contactID);
		this.contactID = contactID;
	}

	/**
     * Sets the contact's first name.
     * @param firstName First name
     */
	public void setFirstName(String firstName) {
		validateFirstName(firstName);
		this.firstName = firstName;
	}

	/**
	 * Sets the contact's last name.
	 * @param lastName Last name
	 */
	public void setLastName(String lastName) {
		validateLastName(lastName);
		this.lastName = lastName;
	}

	/**
	 * Sets the contact's phone number.
	 * @param phone Phone number
	 */
	public void setPhone(String phone) {
		validatePhone(phone);
		this.phone = phone;
	}
	
	/**
	 * Sets the contact's address.
	 * @param address Address
	 */
	public void setAddress(String address) {
		validateAddress(address);
		this.address = address;
	}
	
	/**
	 * Validates the contact ID.
	 * @param contactID Contact ID
	 */
	private void validateContactID(String contactID) {
	    if (contactID == null || contactID.length() > VAR_MAX_LENGTH || contactID.isEmpty()) {
	        throw new IllegalArgumentException("Contact ID must be non-null, non-empty and < " + VAR_MAX_LENGTH + " characters.");
	    }
	    validateNoInjection(contactID, "Contact ID");
	}

	/**
	 * Validates the first name.
	 * @param firstName First name
	 */
	private void validateFirstName(String firstName) {
	    if (firstName == null || firstName.length() > VAR_MAX_LENGTH || firstName.isEmpty()) {
	        throw new IllegalArgumentException("First name must be non-null, non-empty and < " + VAR_MAX_LENGTH + " characters.");
	    }
	    validateNoInjection(firstName, "First Name");
	}

	/**
	 * Validates the last name.
	 * @param lastName Last name
	 */
	private void validateLastName(String lastName) {
	    if (lastName == null || lastName.length() > VAR_MAX_LENGTH || lastName.isEmpty()) {
	        throw new IllegalArgumentException("Last name must be non-null, non-empty and < " + VAR_MAX_LENGTH + " characters.");
	    }
	    validateNoInjection(lastName, "Last Name");
	}

	/**
	 * Validates the phone number.
	 * @param phone Phone number
	 */
	private void validatePhone(String phone) {
	    if (phone == null || phone.length() != VAR_MAX_LENGTH) {
	        throw new IllegalArgumentException("Phone number must be non-empty and exactly " + VAR_MAX_LENGTH + " characters.");
	    }
	    validateNoInjection(phone, "Phone Number");
	}

	/**
	 * Validates the address.
	 * @param address Address
	 */
	private void validateAddress(String address) {
	    if (address == null || address.length() > ADDR_MAX_LENGTH || address.isEmpty()) {
	        throw new IllegalArgumentException("Address must be non-null, non-empty and < " + ADDR_MAX_LENGTH + " characters.");
	    }
	    validateNoInjection(address, "Address");
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
}