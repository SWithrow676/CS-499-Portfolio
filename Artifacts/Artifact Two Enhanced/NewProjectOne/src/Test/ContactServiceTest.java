package Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import Project.ContactService;
import Project.Contact;

/**
 * Unit tests for the ContactService class.
 * Tests adding, deleting, and editing contacts.
 * 
 * @author Stewart Withrow
 */
class ContactServiceTest {

	/*
	 * Clears the contacts map after each test to ensure test isolation.
	 * @throws Exception if an error occurs during teardown
	 */
    @AfterEach
    void tearDown() throws Exception {
        ContactService.contacts.clear();
    }

    /*
     * Tests adding a contact to the ContactService.
     */
    @Test
    void testAddContact() {
    	String id = "0";
        String firstName = "John";
        String lastName = "Smith";
        String phone = "1234567891";
        String address = "123 Main Street";
        
        ContactService contactService = new ContactService();
        assertTrue(ContactService.contacts.isEmpty(), "Map should be empty before adding");
        contactService.addContact(firstName, lastName, phone, address);
        
        assertFalse(ContactService.contacts.isEmpty(), "Map should not be empty after adding");
        
        Contact contact = ContactService.contacts.get(id);
        
        assertNotNull(contact);
        assertEquals(id, contact.getContactID());
        assertEquals(firstName, contact.getFirstName());
        assertEquals(lastName, contact.getLastName());
        assertEquals(phone, contact.getPhone());
        assertEquals(address, contact.getAddress());
    }

    /*
	 * Tests deleting a contact from the ContactService.
	 */
    @Test
    void testDeleteContact() {
        String firstName = "John";
        String lastName = "Smith";
        String phone = "1234567891";
        String address = "123 Main Street";
        
        ContactService contactService = new ContactService();
        int expectedSizeBefore = 0;
        int expectedSizeAfterAdd = 3;
        int expectedSizeAfterDelete = 2;
        String deleteID = "1";
        
        assertEquals(expectedSizeBefore, ContactService.contacts.size());
        contactService.addContact(firstName, lastName, phone, address);
        contactService.addContact(firstName, lastName, phone, address);
        contactService.addContact(firstName, lastName, phone, address);
        assertEquals(expectedSizeAfterAdd, ContactService.contacts.size());
        contactService.deleteContact(deleteID);
        assertEquals(expectedSizeAfterDelete, ContactService.contacts.size());
        assertNull(ContactService.contacts.get(deleteID));
    }

    /*
     * Tests editing the first name of a contact in the ContactService.
     */
    @Test
    void testEditFirst() {
    	String id = "0";
        String firstName = "John";
        String lastName = "Smith";
        String phone = "1234567891";
        String address = "123 Main Street";
        String newFirstName = "Mark";

        ContactService contactService = new ContactService();
        contactService.addContact(firstName, lastName, phone, address);
        
        assertEquals(firstName, ContactService.contacts.get(id).getFirstName());
        contactService.editFirstName(id, newFirstName);
        assertEquals(newFirstName, ContactService.contacts.get(id).getFirstName());
    }

    /*
	 * Tests editing the last name of a contact in the ContactService.
	 */
    @Test
    void testEditLast() {
    	String id = "0";
        String firstName = "John";
        String lastName = "Smith";
        String phone = "1234567891";
        String address = "123 Main Street";
        String newLastName = "Hall";

        ContactService contactService = new ContactService();
        contactService.addContact(firstName, lastName, phone, address);
        
        assertEquals(lastName, ContactService.contacts.get(id).getLastName());
        contactService.editLastName(id, newLastName);
        assertEquals(newLastName, ContactService.contacts.get(id).getLastName());
    }

    /*
     * Tests editing the phone number of a contact in the ContactService.
     */
    @Test
    void testEditPhone() {
    	String id = "0";
        String firstName = "John";
        String lastName = "Smith";
        String phone = "1234567891";
        String address = "123 Main Street";
        String newPhone = "9876543210";

        ContactService contactService = new ContactService();
        contactService.addContact(firstName, lastName, phone, address);
        
        assertEquals(phone, ContactService.contacts.get(id).getPhone());
        contactService.editPhone(id, newPhone);
        assertEquals(newPhone, ContactService.contacts.get(id).getPhone());
    }
    
    /*
	 * Tests editing the address of a contact in the ContactService.
	 */
    @Test
    void testEditAddress() {
    	String id = "0";
        String firstName = "John";
        String lastName = "Smith";
        String phone = "1234567891";
        String address = "123 Main Street";
        String newAddress = "123 Holly Street";

        ContactService contactService = new ContactService();
        contactService.addContact(firstName, lastName, phone, address);
        
        assertEquals(address, ContactService.contacts.get(id).getAddress());
        contactService.editAddress(id, newAddress);
        assertEquals(newAddress, ContactService.contacts.get(id).getAddress());
    }

    /*
     * Tests editing a contact first name with null parameters, expecting exceptions.
     */
    @Test
    void testEditFirstNameNull() {
    	String id = "0";
        String firstName = "John";
        String lastName = "Smith";
        String phone = "1234567891";
        String address = "123 Main Street";
        String newFirstName = null;

        ContactService contactService = new ContactService();
        contactService.addContact(firstName, lastName, phone, address);
        
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            contactService.editFirstName(id, newFirstName);
        });
    }

    /*
     * Tests editing a contact last name with null parameters, expecting exceptions.
     */
    @Test
    void testEditLastNameNull() {
    	String id = "0";
        String firstName = "John";
        String lastName = "Smith";
        String phone = "1234567891";
        String address = "123 Main Street";
        String newLastName = null;

        ContactService contactService = new ContactService();
        contactService.addContact(firstName, lastName, phone, address);
        
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            contactService.editLastName(id, newLastName);
        });
    }

    /*
     * Tests editing a contact phone number with null parameters, expecting exceptions.
     */
    @Test
    void testEditPhoneNull() {
    	String id = "0";
        String firstName = "John";
        String lastName = "Smith";
        String phone = "1234567891";
        String address = "123 Main Street";
        String newPhone = null;

        ContactService contactService = new ContactService();
        contactService.addContact(firstName, lastName, phone, address);
        
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            contactService.editPhone(id, newPhone);
        });
    }
    /*
	 * Tests editing a contact address with null parameters, expecting exceptions.
	 */
    @Test
    void testEditAddressNull() {
    	String id = "0";
        String firstName = "John";
        String lastName = "Smith";
        String phone = "1234567891";
        String address = "123 Main Street";
        String newAddress = null;

        ContactService contactService = new ContactService();
        contactService.addContact(firstName, lastName, phone, address);
        
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            contactService.editAddress(id, newAddress);
        });
    }

    /*
     * Tests editing a contact first name with too-long parameters, expecting exceptions.
     */
    @Test
    void testEditFirstNameLong() {
    	String id = "0";
        String firstName = "John";
        String lastName = "Smith";
        String phone = "1234567891";
        String address = "123 Main Street";
        String longName = new String(new char[20]).replace('\0', 'a');

        ContactService contactService = new ContactService();
        contactService.addContact(firstName, lastName, phone, address);
        
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            contactService.editFirstName(id, longName);
        });
    }

    /*
	 * Tests editing a contact last name with too-long parameters, expecting exceptions.
	 */
    @Test
    void testEditLastNameLong() {
    	String id = "0";
        String firstName = "John";
        String lastName = "Smith";
        String phone = "1234567891";
        String address = "123 Main Street";
        String longName = new String(new char[20]).replace('\0', 'b');

        ContactService contactService = new ContactService();
        contactService.addContact(firstName, lastName, phone, address);
        
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            contactService.editLastName(id, longName);
        });
    }

    /*
	 * Tests editing a contact phone number with too-short parameters, expecting exceptions.
	 */
    @Test
    void testEditPhoneShort() {
    	String id = "0";
        String firstName = "John";
        String lastName = "Smith";
        String phone = "1234567891";
        String address = "123 Main Street";
        String shortPhone = "123";

        ContactService contactService = new ContactService();
        contactService.addContact(firstName, lastName, phone, address);
        
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            contactService.editPhone(id, shortPhone);
        });
    }

    /*
     * Tests editing a contact phone number with too-long parameters, expecting exceptions.
     */
    @Test
    void testEditPhoneLong() {
    	String id = "0";
        String firstName = "John";
        String lastName = "Smith";
        String phone = "1234567891";
        String address = "123 Main Street";
        String longPhone = new String(new char[20]).replace('\0', '1');

        ContactService contactService = new ContactService();
        contactService.addContact(firstName, lastName, phone, address);
        
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            contactService.editPhone(id, longPhone);
        });
    }

    /*
     * Tests editing a contact address with too-long parameters, expecting exceptions.
     */
    @Test
    void testEditAddressLong() {
    	String id = "0";
        String firstName = "John";
        String lastName = "Smith";
        String phone = "1234567891";
        String address = "123 Main Street";
        String longAddress = new String(new char[40]).replace('\0', 'a');

        ContactService contactService = new ContactService();
        contactService.addContact(firstName, lastName, phone, address);
        
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            contactService.editAddress(id, longAddress);
        });
    }

    /*
	 * Tests editing a contact first name with empty parameters, expecting exceptions.
	 */
    @Test
    void testEditFirstNameEmpty() {
    	String id = "0";
    	String firstName = "John";
        String lastName = "Smith";
        String phone = "1234567891";
        String address = "123 Main Street";
        String emptyFirstName = "";
        
        ContactService contactService = new ContactService();
        contactService.addContact(firstName, lastName, phone, address);
        
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            contactService.editFirstName(id, emptyFirstName);
        });
    }

    /*
     * Tests editing a contact last name with empty parameters, expecting exceptions.
     */
    @Test
    void testEditLastNameEmpty() {
    	String id = "0";
    	String firstName = "John";
        String lastName = "Smith";
        String phone = "1234567891";
        String address = "123 Main Street";
        String emptyLastName = "";
        
        ContactService contactService = new ContactService();
        contactService.addContact(firstName, lastName, phone, address);
        
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            contactService.editLastName(id, emptyLastName);
        });
    }

    /*
	 * Tests editing a contact address with empty parameters, expecting exceptions.
	 */
    @Test
    void testEditAddressEmpty() {
    	String id = "0";
    	String firstName = "John";
        String lastName = "Smith";
        String phone = "1234567891";
        String address = "123 Main Street";
        String emptyAddress = "";
        
        ContactService contactService = new ContactService();
        contactService.addContact(firstName, lastName, phone, address);
        
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            contactService.editAddress(id, emptyAddress);
        });
    }

    /*
	 * Tests editing a contact that does not exist, expecting no changes.
	 */
    @Test
    void testEditNonExistentContact() {
        String id = "999";
        String firstName = "Mark";
        String lastName = "Hall";
        String phone = "1987654321";
        String address = "123 Holy Street";

        ContactService contactService = new ContactService();
        
        contactService.editFirstName(id, firstName);
        contactService.editLastName(id, lastName);
        contactService.editPhone(id, phone);
        contactService.editAddress(id, address);
        
        assertNull(ContactService.contacts.get(id));
    }
}