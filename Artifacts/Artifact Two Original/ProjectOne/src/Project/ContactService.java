// Author: Stewart Withrow

package Project;

import java.util.List;
import java.util.ArrayList;

public class ContactService {
	
	// Holds the next ID for an added contact
	int currentID = 0;
	
	// New ArrayList to hold contact objects
	public static List<Contact> contactList = new ArrayList<Contact>();
	
	// Adds a new contact to the list
	public void addContact(String firstName, String lastName, String phone, String address) {
		
		// Converts currentID to string
		String stringID = Integer.toString(currentID);
		
		// Created new contact
		Contact contact = new Contact(stringID, firstName, lastName, phone, address);
		contactList.add(contact.getContactID(),contact);
		
		// Increments ID for next entry
		currentID++;
	}
	
	// Deletes a contact from the list
	public void deleteContact(String ID) {
		
		// Converts ID to int
		int intID = Integer.valueOf(ID);
		
		// Iterates through list to find desired ID
		for(int i = 0; i < ContactService.contactList.size(); i++) {
			if(ContactService.contactList.get(i).getContactID() == intID) {
				contactList.remove(i);
			}
		}
	}
	
	// Iterates through list with a contactID to edit the first name
	public void editFirstName(String contactID, String firstName) {
		for(Contact i : contactList) {
			if(i.getContactID() == Integer.valueOf(contactID)) {
				i.setFirstName(firstName);				
			}			
		}
	}
	
	// Iterates through list with a contactID to edit the last name
	public void editLastName(String contactID, String lastName) {
		for(Contact i : contactList) {
			if(i.getContactID() == Integer.valueOf(contactID)) {
				i.setLastName(lastName);
			}
		}
	}
	
	// Iterates through list with a contactID to edit the phone number
	public void editPhone(String contactID, String phone) {
		for(Contact i : contactList) {
			if(i.getContactID() == Integer.valueOf(contactID)) {
				i.setPhone(phone);
			}
		}
	}
	
	// Iterates through list with a contactID to edit the address
	public void editAddress(String contactID, String address) {
		for(Contact i : contactList) {
			if(i.getContactID() == Integer.valueOf(contactID)) {
				i.setAddress(address);
			}
		}
	}
}
