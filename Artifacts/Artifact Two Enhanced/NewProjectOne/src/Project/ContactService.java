package Project;

import java.util.HashMap;

/**
 * Service class to manage Contact objects.
 * Provides methods to add, delete, and edit contacts.
 * 
 * @author Stewart Withrow
 */
public class ContactService {
	
	// Holds the next ID for an added contact
	private int currentID = 0;
	
	// HashMap to hold contact objects, keyed by their unique ID
	public static HashMap<String, Contact> contacts = new HashMap<String, Contact>();
	
	/**
     * Adds a new Contact to the map.
     * 
     * @param firstName First name
     * @param lastName Last name
     * @param phone Phone number
     * @param address Address
     * @throws IllegalArgumentException if parameters are invalid
     */
	public void addContact(String firstName, String lastName, String phone, String address) {
		String stringID = Integer.toString(currentID);
		Contact newContact = new Contact(stringID, firstName, lastName, phone, address);
		contacts.put(stringID, newContact);
		currentID++;
	}
	
	 /**
     * Deletes a contact from the map by ID.
     * 
     * @param ID Contact ID to delete
     */
	public void deleteContact(String ID) {
        contacts.remove(ID);
    }
	
	/**
     * Edits the first name of a contact by ID.
     * 
     * @param contactID Contact ID
     * @param firstName New first name
     */
	public void editFirstName(String contactID, String firstName) {
		Contact contact = contacts.get(contactID);
		if (contact != null) {
			contact.setFirstName(firstName);
		}
	}
	
	/**
     * Edits the last name of a contact by ID.
     * 
     * @param contactID Contact ID
     * @param lastName New last name
     */
	public void editLastName(String contactID, String lastName) {
		Contact contact = contacts.get(contactID);
		if (contact != null) {
			contact.setLastName(lastName);
		}
	}
	
	/**
     * Edits the phone number of a contact by ID.
     * 
     * @param contactID Contact ID
     * @param phone New phone number
     */
	public void editPhone(String contactID, String phone) {
		Contact contact = contacts.get(contactID);
		if (contact != null) {
			contact.setPhone(phone);
		}
	}
	
	/**
	 * Edits the address of a contact by ID.
	 * 
	 * @param contactID Contact ID
	 * @param address New address
	 */
	public void editAddress(String contactID, String address) {
		Contact contact = contacts.get(contactID);
		if (contact != null) {
			contact.setAddress(address);
		}
	}
}