package Test;

import java.util.Date;
import java.util.Calendar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import Project.Appointment;

/*
 * Unit tests for the Appointment class.
 * Tests cover constructor validation and setter methods.
 * 
 * @author Stewart Withrow
 */
class AppointmentTest {

	/*
	 * Tests and validates the constructor
	 */
	@Test
	void testAppointment() {
        String apptID = "1";
        String description = "Lorem ipsum dolor sit amet.";
        Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.MONTH, 12);
		calendar.set(Calendar.DATE, 31);
		calendar.set(Calendar.YEAR, 2025);

		Date date = calendar.getTime();
        
        Appointment appointment = new Appointment(apptID, date, description);
        
        Assertions.assertEquals(1, appointment.getApptID());
        Assertions.assertEquals(date, appointment.getDate());
        Assertions.assertEquals(description, appointment.getDescription());
	}
	
	/*
	 * Tests the Appointment constructor with a too-long ID
	 */
	@Test
	void testApptIDLong() {
        String apptID = "123456789101112";
        Date date = new Date();
        String description = "Lorem ipsum dolor sit amet.";
        
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Appointment(apptID, date, description);
		});
	}
	
	/*
	 * Tests the Appointment constructor with a null ID
	 */
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
	
	/*
	 * Tests the setDate method with a valid date
	 */
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
	        
	    Appointment appointment = new Appointment(apptID, date, description);
	        
	    appointment.setDate(newDate);        
	    Assertions.assertEquals(newDate, appointment.getDate());
	}
	
	/*
	 * Tests the Appointment constructor with a date from the past
	 */
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
	
	/*
	 * Tests the Appointment constructor with a null date
	 */
	@Test
	void testDateNull() {
		String apptID = "1";
		Date date = null;
		String description = "Lorem ipsum dolor sit amet.";
			
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Appointment(apptID, date, description);
		});
			        
	}
	
	/*
	 * Tests the setDescription method with a valid description
	 */
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
		        
		Appointment appointment = new Appointment(apptID, date, description);
		        
		appointment.setDescription(newDescription);        
		Assertions.assertEquals(newDescription, appointment.getDescription());
	}
	
	/*
	 * Tests the Appointment constructor with a too-long description
	 */
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
	
	/*
	 * Tests the Appointment constructor with a null description
	 */
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
	
	/*
	 * Tests the Appointment constructor with an empty description
	 */
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
	
	/*
	 * Tests the Appointment constructor with a script injection in the description
	 */
	@Test
	void testScriptInjectionInDescription() {
	    String apptID = "1";
	    String description = "<script>alert('xss')</script>";
	    Calendar calendar = Calendar.getInstance();
	    
	    calendar.set(Calendar.MONTH, 12);
	    calendar.set(Calendar.DATE, 31);
	    calendar.set(Calendar.YEAR, 2025);
	    
	    Date date = calendar.getTime();

	    Assertions.assertThrows(IllegalArgumentException.class, () -> {
	        new Appointment(apptID, date, description);
	    });
	}

    /*
     * Tests the setDate method with a null date
     */
    @Test
    void testSetDateNull() {
        String apptID = "1";
        String description = "Valid description";
        Calendar calendar = Calendar.getInstance();
        
        calendar.set(Calendar.MONTH, 12);
        calendar.set(Calendar.DATE, 31);
        calendar.set(Calendar.YEAR, 2025);
        
        Date date = calendar.getTime();
        Appointment appointment = new Appointment(apptID, date, description);
        
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
        	appointment.setDate(null);
        });
    }

    /*
	 * Tests the setDate method with a past date
	 */
    @Test
    void testSetDatePast() {
        String apptID = "1";
        String description = "Valid description";
        Calendar calendar = Calendar.getInstance();
        
        calendar.set(Calendar.MONTH, 12);
        calendar.set(Calendar.DATE, 31);
        calendar.set(Calendar.YEAR, 2025);
        
        Date date = calendar.getTime();
        Appointment appointment = new Appointment(apptID, date, description);
        Calendar pastCalendar = Calendar.getInstance();
        
        pastCalendar.set(Calendar.MONTH, 1);
        pastCalendar.set(Calendar.DATE, 1);
        pastCalendar.set(Calendar.YEAR, 2010);
        
        Date pastDate = pastCalendar.getTime();
        
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            appointment.setDate(pastDate);
        });
    }

    /*
	 * Tests the setDate method with a past date
	 */
    @Test
    void testSetDescriptionTooLong() {
        String apptID = "1";
        String description = "Valid description";
        Calendar calendar = Calendar.getInstance();
        
        calendar.set(Calendar.MONTH, 12);
        calendar.set(Calendar.DATE, 31);
        calendar.set(Calendar.YEAR, 2025);
        
        Date date = calendar.getTime();
        
        Appointment appointment = new Appointment(apptID, date, description);
        String longDesc = new String(new char[51]).replace('\0', 'a');
        
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            appointment.setDescription(longDesc);
        });
    }

    /*
     * Tests the setDescription method with a blacklisted term
     */
    @Test
    void testSetDescriptionBlacklist() {
        String apptID = "1";
        String description = "Valid description";
        Calendar calendar = Calendar.getInstance();
        
        calendar.set(Calendar.MONTH, 12);
        calendar.set(Calendar.DATE, 31);
        calendar.set(Calendar.YEAR, 2025);
        
        Date date = calendar.getTime();
        
        Appointment appointment = new Appointment(apptID, date, description);
        String blacklistDesc = "DROP TABLE users";
        
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            appointment.setDescription(blacklistDesc);
        });
    }

    /*
	 * Tests the setDescription method with an injection pattern
	 */
    @Test
    void testSetDescriptionInjection() {
        String apptID = "1";
        String description = "Valid description";
        Calendar calendar = Calendar.getInstance();
        
        calendar.set(Calendar.MONTH, 12);
        calendar.set(Calendar.DATE, 31);
        calendar.set(Calendar.YEAR, 2025);
        
        Date date = calendar.getTime();
        Appointment appointment = new Appointment(apptID, date, description);
        String injectionDesc = "Hello <script>";
        
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            appointment.setDescription(injectionDesc);
        });
    }

    /*
     * Injection pattern tests for constructor
     */
    @Test
    void testDescriptionInjectionPatternsConstructor() {
        String apptID = "1";
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, 12);
        calendar.set(Calendar.DATE, 31);
        calendar.set(Calendar.YEAR, 2025);
        Date date = calendar.getTime();
        String[] patterns = {"<", ">", "'", "\"", "%", ";", "(", ")", "&", "+"};
        for (String pattern : patterns) {
            String desc = "Valid" + pattern + "Description";
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                new Appointment(apptID, date, desc);
            });
        }
    }
    
    /*
     * Injection pattern tests for setter
     */
    @Test
    void testDescriptionInjectionPatternsSetter() {
        String apptID = "1";
        String description = "Valid description";
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, 12);
        calendar.set(Calendar.DATE, 31);
        calendar.set(Calendar.YEAR, 2025);
        Date date = calendar.getTime();
        Appointment appointment = new Appointment(apptID, date, description);
        String[] patterns = {"<", ">", "'", "\"", "%", ";", "(", ")", "&", "+"};
        for (String pattern : patterns) {
            String desc = "Valid" + pattern + "Description";
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                appointment.setDescription(desc);
            });
        }
    }

	/*
	 * Blacklist pattern tests for constructor
	 */
    @Test
    void testDescriptionBlacklistPatternsConstructor() {
        String apptID = "1";
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, 12);
        calendar.set(Calendar.DATE, 31);
        calendar.set(Calendar.YEAR, 2025);
        Date date = calendar.getTime();
        String[] blacklist = {"DROP TABLE", "DELETE FROM", "UPDATE", "INSERT", "<script>", "alert(", "SELECT *"};
        for (String pattern : blacklist) {
            String desc = "Valid " + pattern + " Description";
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                new Appointment(apptID, date, desc);
            });
        }
    }
    
    /*
	 * Blacklist pattern tests for setter
	 */
    @Test
    void testDescriptionBlacklistPatternsSetter() {
        String apptID = "1";
        String description = "Valid description";
        Calendar calendar = Calendar.getInstance();
        
        calendar.set(Calendar.MONTH, 12);
        calendar.set(Calendar.DATE, 31);
        calendar.set(Calendar.YEAR, 2025);
        
        Date date = calendar.getTime();
        
        Appointment appointment = new Appointment(apptID, date, description);
        String[] blacklist = {"DROP TABLE", "DELETE FROM", "UPDATE", "INSERT", "<script>", "alert(", "SELECT *"};
        
        for (String pattern : blacklist) {
            String desc = "Valid " + pattern + " Description";
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                appointment.setDescription(desc);
            });
        }
    }
}