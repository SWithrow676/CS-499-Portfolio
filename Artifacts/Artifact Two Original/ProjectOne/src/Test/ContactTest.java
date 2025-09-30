// Author: Stewart Withrow

package Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import Project.Contact;

class ContactTest {

	// Tests and validates the constructor
	@Test
	void testContact() {
		String contactID = "1";
      	String firstName = "John";
        String lastName = "Smith";
        String phone = "1234567891";
        String address = "123 Main Street";
		
        Contact testContact = new Contact(contactID, firstName, lastName, phone, address);
        
        assertEquals(1, testContact.getContactID());
		assertEquals(firstName, testContact.getFirstName());
		assertEquals(lastName, testContact.getLastName());
		assertEquals(phone, testContact.getPhone());
		assertEquals(address, testContact.getAddress());
	}
	
	// Tests the constructor with invalid info
	@Test
	void testContactInvalid() {
		String contactID = "2";
      	String firstName = "John";
        String lastName = "Smith";
        String phone = "1";
        String address = "123 Main Street";
        
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
        	new Contact(contactID, firstName, lastName, phone, address);
        });
	}
	
	// Tests and validates a valid contactID
	@Test
	void testSetContactID() {
		String contactID = "3";
      	String firstName = "John";
        String lastName = "Smith";
        String phone = "1234567891";
        String address = "123 Main Street";
		
        Contact testContact = new Contact(contactID, firstName, lastName, phone, address);
        assertEquals(3, testContact.getContactID());
	}
	
	// Tests an invalid contactID 
	@Test
	void testSetContactIDInvalid() {
		String contactID = "3";
      	String firstName = "John";
        String lastName = "Smith";
        String phone = "1234567891";
        String address = "123 Main Street";
		
        Contact testContact = new Contact(contactID, firstName, lastName, phone, address);
        assertNotEquals("3", testContact.getContactID());
	}
	
	// Tests a too long contactID
	@Test
	void testSetContactIDLong() {
		String contactID = "1234567891011";
      	String firstName = "John";
        String lastName = "Smith";
        String phone = "1234567891";
        String address = "123 Main Street";
		
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
        	new Contact(contactID, firstName, lastName, phone, address);
        });
	}
	
	// Tests and validates a valid first name
	@Test
	void testSetFirstName() {
		String contactID = "3";
      	String firstName = "John";
        String lastName = "Smith";
        String phone = "1234567891";
        String address = "123 Main Street";
		
        Contact testContact = new Contact(contactID, firstName, lastName, phone, address);
        testContact.setFirstName("John");
        assertEquals("John", testContact.getFirstName());
	}
	
	// Tests a null first name
	@Test
	void testSetFirstNameNull() {
		String contactID = "3";
      	String firstName = null;
        String lastName = "Smith";
        String phone = "1234567891";
        String address = "123 Main Street";
		
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
        	new Contact(contactID, firstName, lastName, phone, address);
        });
	}
	
	// Tests a too long first name
	@Test
	void testSetFirstNameLong() {
		String contactID = "3";
      	String firstName = "AVeryLongFirstName";
        String lastName = "Smith";
        String phone = "1234567891";
        String address = "123 Main Street";
		
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
        	new Contact(contactID, firstName, lastName, phone, address);
        });
	}
	
	// Tests and validates a valid last name 
	@Test
	void testSetLastName() {
		String contactID = "3";
      	String firstName = "John";
        String lastName = "Smith";
        String phone = "1234567891";
        String address = "123 Main Street";
		
        Contact testContact = new Contact(contactID, firstName, lastName, phone, address);
        testContact.setLastName("Smith");
        assertEquals("Smith", testContact.getLastName());
	}
	
	// Tests a null last name
	@Test
	void testSetLastNameNull() {
		String contactID = "3";
      	String firstName = "John";
        String lastName = null;
        String phone = "1234567891";
        String address = "123 Main Street";
		
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
        	new Contact(contactID, firstName, lastName, phone, address);
        });
	}
	
	// Tests a too long last name
	@Test
	void testSetLastNameLong() {
		String contactID = "3";
      	String firstName = "John";
        String lastName = "AVeryLongLastName";
        String phone = "1234567891";
        String address = "123 Main Street";
		
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
        	new Contact(contactID, firstName, lastName, phone, address);
        });
	}
	
	// Tests and validates a valid phone number 
	@Test
	void testSetPhone() {
		String contactID = "3";
      	String firstName = "John";
        String lastName = "Smith";
        String phone = "1234567891";
        String address = "123 Main Street";
		
        Contact testContact = new Contact(contactID, firstName, lastName, phone, address);
        testContact.setPhone(phone);
        assertEquals(phone, testContact.getPhone());
	}
	
	// Tests a null phone number
	@Test
	void testSetPhoneNull() {
		String contactID = "3";
      	String firstName = "John";
        String lastName = "Smith";
        String phone = null;
        String address = "123 Main Street";
		
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
        	new Contact(contactID, firstName, lastName, phone, address);
        });
	}
	
	// Tests a too long phone number
	@Test
	void testSetPhoneLong() {
		String contactID = "3";
      	String firstName = "John";
        String lastName = "Smith";
        String phone1 = "1";
        String phone2 = "12345678910111213141516171819";
        String address = "123 Main Street";
		
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
        	new Contact(contactID, firstName, lastName, phone1, address);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
        	new Contact(contactID, firstName, lastName, phone2, address);
        });
	}
	
	// Tests and validates a valid address
	@Test
	void testSetAddress() {
		String contactID = "3";
      	String firstName = "John";
        String lastName = "Smith";
        String phone = "1234567891";
        String address = "123 Main Street";
		
        Contact testContact = new Contact(contactID, firstName, lastName, phone, address);
        testContact.setAddress(address);
        assertEquals(address, testContact.getAddress());
	}
	
	// Tests a null address
	@Test
	void testSetAddressNull() {
		String contactID = "3";
      	String firstName = "John";
        String lastName = "Smith";
        String phone = "1234567891";
        String address = null;
		
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
        	new Contact(contactID, firstName, lastName, phone, address);
        });
	}
	
	// Tests a too long address
	@Test
	void testSetAddressLong() {
		String contactID = "3";
      	String firstName = "John";
        String lastName = "Smith";
        String phone = "1234567891";
        String address = "1234567890 Extremely Long Street Road Avenue";
		
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
        	new Contact(contactID, firstName, lastName, phone, address);
        });
	}

}
