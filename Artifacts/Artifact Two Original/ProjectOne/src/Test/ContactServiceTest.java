// Author: Stewart Withrow

package Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import Project.ContactService;

class ContactServiceTest {
	
	// Clears array after each test
	@AfterEach
	void tearDown() throws Exception {
		ContactService.contactList.clear();
	}
	
	// Tests adding contacts to the list
	@Test
	void testAddContact() {

		String firstName = "John";
		String lastName = "Smith";
		String phone = "1234567891";
		String address = "123 Main Street";
		
		// Initializes contact
		ContactService test = new ContactService();
		
		// Validates list is empty
		assertTrue(ContactService.contactList.isEmpty());
		
		// Adds contact info to list
		test.addContact(firstName, lastName, phone, address);
		
		// Validates list is NOT empty and list has the same info
		assertFalse(ContactService.contactList.isEmpty());
		assertEquals(0, ContactService.contactList.get(0).getContactID());
		assertEquals(firstName, ContactService.contactList.get(0).getFirstName());
		assertEquals(lastName, ContactService.contactList.get(0).getLastName());
		assertEquals(phone, ContactService.contactList.get(0).getPhone());
		assertEquals(address, ContactService.contactList.get(0).getAddress());
	}
	
	// Tests a contact to be deleted and validated
	@Test void testDeleteContact() {
		  
		String firstName = "John";
		String lastName = "Smith";
		String phone = "1234567891";
		String address = "123 Main Street";
		boolean testForID = false;
		
		// Initializes contact
		ContactService test = new ContactService();
		
		// Validates list is empty
		assertTrue(ContactService.contactList.isEmpty());
		
		// Creates new contacts from IDs 0-2
		test.addContact(firstName, lastName, phone, address);
		test.addContact(firstName, lastName, phone, address);
		test.addContact(firstName, lastName, phone, address);
		  
		assertEquals(3,ContactService.contactList.size());
		
		// Deletes contact with ID 1
		test.deleteContact("1");
		 
		// Validates list has 2 entries
		assertEquals(2,ContactService.contactList.size());
		
		// Loops through the array for deleted ID
		for(int i = 0; i < ContactService.contactList.size(); i++) {
			if(ContactService.contactList.get(i).getContactID() == 1) {
				testForID = true;
			}
		}		
		assertFalse(testForID);				 
	}
	
	// Creates contact and validates first name, then edits and re-validates first name
	@Test
	void testEditFirst() {
		
		String firstName = "John";
		String lastName = "Smith";
		String phone = "1234567891";
		String address = "123 Main Street";
		String newFirstName = "Mark";
		
		// Creates contact
		ContactService test = new ContactService();
		test.addContact(firstName, lastName, phone, address);
		
		//Validates first name
		assertEquals(firstName, ContactService.contactList.get(0).getFirstName());
		
		//Edits and validates new first name
		test.editFirstName("0", newFirstName);
		assertEquals(newFirstName, ContactService.contactList.get(0).getFirstName());
	}
	
	// Creates contact and validates last name, then edits and re-validates last name
	@Test
	void testEditLast() {
		
		String firstName = "John";
		String lastName = "Smith"; 
		String phone = "1234567891"; 
		String address = "123 Main Street";
		String newLastName = "Hall";
		
		// Creates contact
		ContactService test = new ContactService();
		test.addContact(firstName, lastName, phone, address);
		
		//Validates last name
		assertEquals(lastName, ContactService.contactList.get(0).getLastName());
		
		//Edits and validates new last name
		test.editLastName("0", newLastName);
		assertEquals(newLastName, ContactService.contactList.get(0).getLastName());
	}
	
	// Creates contact and validates phone number, then edits and re-validates phone number
	@Test
	void testEditPhone() {
		
		String firstName = "John";
		String lastName = "Smith";
		String phone = "1234567891";
		String address = "123 Main Street";
		String newPhone = "1987654321";
		
		// Creates contact
		ContactService test = new ContactService();
		test.addContact(firstName, lastName, phone, address);
		
		//Validates phone number
		assertEquals(phone, ContactService.contactList.get(0).getPhone());
		
		//Edits and validates new phone number
		test.editPhone("0", newPhone);
		assertEquals(newPhone, ContactService.contactList.get(0).getPhone());
	}
	
	// Creates contact and validates address, then edits and re-validates address
	@Test
	void testEditAddress() {
		
		String firstName = "John";
		String lastName = "Smith";
		String phone = "1234567891";
		String address = "123 Main Street";
		String newAddress = "123 Holy Street";
		
		// Creates contact
		ContactService test = new ContactService();
		test.addContact(firstName, lastName, phone, address);
		
		//Validates address
		assertEquals(address, ContactService.contactList.get(0).getAddress());
		
		//Edits and validates new address
		test.editAddress("0", newAddress);
		assertEquals(newAddress, ContactService.contactList.get(0).getAddress());
	}

}
