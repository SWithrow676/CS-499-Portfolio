// Author: Stewart Withrow

package Test;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.Calendar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import Project.Appointment;

class AppointmentTest {

	// Tests and validates the constructor
	@Test
	void testAppointment() {
        String apptID = "1";
        String description = "Lorem ipsum dolor sit amet.";
        Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.MONTH, 12);
		calendar.set(Calendar.DATE, 31);
		calendar.set(Calendar.YEAR, 2025);

		Date date = calendar.getTime();
        
        Appointment testAppt = new Appointment(apptID, date, description);
        
        assertEquals(1, testAppt.getApptID());
        assertEquals(date, testAppt.getDate());
        assertEquals(description, testAppt.getDescription());
	}
	
	// Tests the constructor with invalid info (long ID and 
	@Test
	void testAppointmentInvalid() {
        String apptID = "123456789101112";
        Date date = new Date();
        String description = "Lorem ipsum dolor sit amet.";
        
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Appointment(apptID, date, description);
		});
	}
	
	// Tests the constructor with null ID
	@Test
	void testApptIDNull() {
		String apptID = null;
		String description = "Lorem ipsum dolor sit amet.";
		Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.MONTH, 12);
		calendar.set(Calendar.DATE, 31);
		calendar.set(Calendar.YEAR, 2025);

		Date date = calendar.getTime();
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Appointment(apptID, date, description);
		});
	        
	}
	
	// Tests the constructor with a valid date
	@Test
	void testSetDate() {
		String apptID = "1";
	    String description = "Lorem ipsum dolor sit amet.";
	    Calendar calendar = Calendar.getInstance();
	    Calendar newCalendar = Calendar.getInstance();
	        
	    calendar.set(Calendar.MONTH, 12);
	    calendar.set(Calendar.DATE, 31);
		calendar.set(Calendar.YEAR, 2025);

		Date date = calendar.getTime();

	    newCalendar.set(Calendar.MONTH, 07);
	    newCalendar.set(Calendar.DATE, 12);
	    newCalendar.set(Calendar.YEAR, 2030);

		Date newDate = newCalendar.getTime();
	        
	    Appointment testAppt = new Appointment(apptID, date, description);
	        
	    testAppt.setDate(newDate);        
	    assertEquals(newDate, testAppt.getDate());
	}
	
	// Tests the constructor with date in the past
	@Test
	void testDateInvalid() {
		String apptID = "1";
		String description = "Lorem ipsum dolor sit amet.";
		Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.MONTH, 12);
		calendar.set(Calendar.DATE, 31);
		calendar.set(Calendar.YEAR, 2015);

		Date date = calendar.getTime();
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Appointment(apptID, date, description);
		});
		        
	}
	
	// Tests the constructor with a null date
	@Test
	void testDateNull() {
		String apptID = "1";
		Date date = null;
		String description = "Lorem ipsum dolor sit amet.";
			
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Appointment(apptID, date, description);
		});
			        
	}
	
	// Tests the constructor with a valid description
	@Test
	void testSetDescription() {
		String apptID = "1";
		String description = "Lorem ipsum dolor sit amet.";
		Calendar calendar = Calendar.getInstance();
		String newDescription = "This is an example description.";
		        
		calendar.set(Calendar.MONTH, 12);
		calendar.set(Calendar.DATE, 31);
		calendar.set(Calendar.YEAR, 2025);

		Date date = calendar.getTime();
		        
		Appointment testAppt = new Appointment(apptID, date, description);
		        
		testAppt.setDescription(newDescription);        
		assertEquals(newDescription, testAppt.getDescription());
	}
	
	// Tests the constructor with an invalid description
	@Test
	void testDescriptionInvalid() {
		String apptID = "1";
	    String description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.";
	    Calendar calendar = Calendar.getInstance();
	    
	    calendar.set(Calendar.MONTH, 12);
	    calendar.set(Calendar.DATE, 31);
		calendar.set(Calendar.YEAR, 2025);

		Date date = calendar.getTime();
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Appointment(apptID, date, description);
		});
	}
	
	// Tests the constructor with a null description
	@Test
	void testDescriptionNull() {
		String apptID = "1";
		String description = null;
		Calendar calendar = Calendar.getInstance();
		    
		calendar.set(Calendar.MONTH, 12);
		calendar.set(Calendar.DATE, 31);
		calendar.set(Calendar.YEAR, 2025);

		Date date = calendar.getTime();
			
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Appointment(apptID, date, description);
		});
	}
	
	// Tests the constructor with a null description
	@Test
	void testDescriptionEmpty() {
		String apptID = "1";
		String description = "";
		Calendar calendar = Calendar.getInstance();
			    
		calendar.set(Calendar.MONTH, 12);
		calendar.set(Calendar.DATE, 31);
		calendar.set(Calendar.YEAR, 2025);

		Date date = calendar.getTime();
				
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Appointment(apptID, date, description);
		});
	}

}
