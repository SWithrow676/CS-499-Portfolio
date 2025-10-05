package Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import Project.Contact;

/**
 * Unit tests for the Contact class.
 * Tests constructor, getters, setters, and validation logic.
 * 
 * @author Stewart Withrow
 */
class ContactTest {

	/**
     * Tests valid creation of a Contact object.
     */
    @Test
    void testContact() {
        String contactID = "1";
        String firstName = "John";
        String lastName = "Smith";
        String phone = "1234567891";
        String address = "123 Main Street";

        Contact contact = new Contact(contactID, firstName, lastName, phone, address);

        assertEquals("1", contact.getContactID());
        assertEquals(firstName, contact.getFirstName());
        assertEquals(lastName, contact.getLastName());
        assertEquals(phone, contact.getPhone());
        assertEquals(address, contact.getAddress());
    }
    
    /**
     * Tests Contact constructor with invalid phone number.
     */
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

    /**
     * Tests getter for contactID.
     */
    @Test
    void testSetContactID() {
        String contactID = "3";
        String firstName = "John";
        String lastName = "Smith";
        String phone = "1234567891";
        String address = "123 Main Street";

        Contact contact = new Contact(contactID, firstName, lastName, phone, address);
        
        assertEquals("3", contact.getContactID());
    }

    /**
     * Tests the constructor with a null contactID.
     */
    @Test
    void testContactIDNull() {
        String contactID = null;
        String firstName = "John";
        String lastName = "Smith";
        String phone = "1234567891";
        String address = "123 Main Street";
        
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Contact(contactID, firstName, lastName, phone, address);
        });
    }

    /**
     * Tests the constructor with a too-long contactID.
     */
    @Test
    void testContactIDLong() {
        String contactID = "1234567891011";
        String firstName = "John";
        String lastName = "Smith";
        String phone = "1234567891";
        String address = "123 Main Street";

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Contact(contactID, firstName, lastName, phone, address);
        });
    }

    /**
     * Tests setter for first name with valid value.
     */
    @Test
    void testSetFirstName() {
        String contactID = "3";
        String firstName = "John";
        String lastName = "Smith";
        String phone = "1234567891";
        String address = "123 Main Street";

        Contact contact = new Contact(contactID, firstName, lastName, phone, address);
        
        contact.setFirstName("John");
        assertEquals("John", contact.getFirstName());
    }

    /**
     * Tests setter with a null first name.
     */
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

    /**
     * Tests setter with a too-long first name.
     */
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
    
    /*
     * Tests setter for first name with empty string.
     */
    @Test
    void testSetFirstNameEmptySetter() {
        String contactID = "1";
        String firstName = "John";
        String lastName = "Smith";
        String phone = "1234567891";
        String address = "123 Main Street";
        String newFirstName = "";
        
        Contact contact = new Contact(contactID, firstName, lastName, phone, address);
        
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            contact.setFirstName(newFirstName);
        });
    }

    /**
     * Tests setter for last name with valid value.
     */
    @Test
    void testSetLastName() {
        String contactID = "3";
        String firstName = "John";
        String lastName = "Smith";
        String phone = "1234567891";
        String address = "123 Main Street";

        Contact contact = new Contact(contactID, firstName, lastName, phone, address);
        
        contact.setLastName("Smith");
        assertEquals("Smith", contact.getLastName());
    }

    /**
     * Tests setter with a null last name.
     */
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

    /**
     * Tests setter with a too-long last name.
     */
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
    
    /**
  	 * Tests setter for last name with empty string.
  	 */
     @Test
     void testSetLastNameEmptySetter() {
         String contactID = "1";
         String firstName = "John";
         String lastName = "Smith";
         String phone = "1234567891";
         String address = "123 Main Street";
         String newLastName = "";
         
         Contact contact = new Contact(contactID, firstName, lastName, phone, address);
         
         Assertions.assertThrows(IllegalArgumentException.class, () -> {
             contact.setLastName(newLastName);
         });
     }

    /**
     * Tests setter for phone number with valid value.
     */
    @Test
    void testSetPhone() {
        String contactID = "3";
        String firstName = "John";
        String lastName = "Smith";
        String phone = "1234567891";
        String address = "123 Main Street";

        Contact contact = new Contact(contactID, firstName, lastName, phone, address);
        
        contact.setPhone(phone);
        assertEquals(phone, contact.getPhone());
    }

    /**
     * Tests setter with a null phone number.
     */
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

    /**
     * Tests setter with a too-long phone number.
     */
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
    
    /**
	 * Tests setter with a too-short phone number.
	 */
    @Test
    void testSetPhoneShortSetter() {
        String contactID = "1";
        String firstName = "John";
        String lastName = "Smith";
        String phone = "1234567891";
        String address = "123 Main Street";
        
        Contact contact = new Contact(contactID, firstName, lastName, phone, address);
        
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            contact.setPhone("123");
        });
    }

    /**
     * Tests setter for address with valid value.
     */
    @Test
    void testSetAddress() {
        String contactID = "3";
        String firstName = "John";
        String lastName = "Smith";
        String phone = "1234567891";
        String address = "123 Main Street";

        Contact contact = new Contact(contactID, firstName, lastName, phone, address);
        contact.setAddress(address);
        assertEquals(address, contact.getAddress());
    }

    /**
     * Tests setter with a null address.
     */
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

    /**
     * Tests setter with a too-long address.
     */
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

    /**
     * Tests setter for address with empty string.
     */
    @Test
    void testSetAddressEmptySetter() {
        String contactID = "1";
        String firstName = "John";
        String lastName = "Smith";
        String phone = "1234567891";
        String address = "123 Main Street";
        String newAddress = "";
        
        Contact contact = new Contact(contactID, firstName, lastName, phone, address);
        
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            contact.setAddress(newAddress);
        });
    }

    /**
	 * Tests the validateNoInjection method with valid input.
	 */
    @Test
    void testValidateNoInjectionValid() {
    	String input = "NormalName";
        String field = "First Name";
        
        assertDoesNotThrow(() -> Project.Contact.validateNoInjection(input, field));
    }

    /**
     * Tests the validateNoInjection method with invalid input.
     */
    @Test
    void testValidateNoInjectionInvalid() {
    	String input = "Bad<Name>";
        String field = "First Name";
    	
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            Project.Contact.validateNoInjection(input, field));
        assertTrue(exception.getMessage().contains("illegal characters"));
    }

}