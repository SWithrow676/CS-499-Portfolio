// Author: Stewart Withrow

package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import Project.AppointmentService;

class AppointmentServiceTest {
	
	// Clears hash after each test
	@AfterEach
	void tearDown() throws Exception {
		AppointmentService.appointments.clear();
	}
	
	// Tests the addAppointment class
	@Test
	void testAddAppt() {
		String apptID = "0";
		String description = "This is a good description";
		Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.MONTH, 12);
		calendar.set(Calendar.DATE, 31);
		calendar.set(Calendar.YEAR, 2025);

		Date date = calendar.getTime();

		AppointmentService tempAppt = new AppointmentService();

		assertEquals(0, AppointmentService.appointments.size());

		tempAppt.addAppointment(date, description);

		assertTrue(AppointmentService.appointments.containsKey(apptID));
		assertEquals(date, AppointmentService.appointments.get(apptID).getDate());
		assertEquals(description, AppointmentService.appointments.get(apptID).getDescription());

	}
	
	// Tests the addAppointment class with empty description
	@Test
	void testAddApptEmptyDesc() {
		String description = "";
		Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.MONTH, 12);
		calendar.set(Calendar.DATE, 31);
		calendar.set(Calendar.YEAR, 2025);

		Date date = calendar.getTime();

		Assertions.assertThrows(IllegalArgumentException.class, () -> {
      		AppointmentService tempAppt = new AppointmentService();
      		tempAppt.addAppointment(date, description);
        });

	}
	
	// Tests the addAppointment class with null description
	@Test
	void testAddApptNullDesc() {
		String description = null;
		Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.MONTH, 12);
		calendar.set(Calendar.DATE, 31);
		calendar.set(Calendar.YEAR, 2025);

		Date date = calendar.getTime();

		Assertions.assertThrows(IllegalArgumentException.class, () -> {
      		AppointmentService tempAppt = new AppointmentService();
      		tempAppt.addAppointment(date, description);
        });

	}
	
	// Tests the deleteAppointment class
	@Test
	void testDeleteAppt() {
		String description = "This is a good description";
		Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.MONTH, 12);
		calendar.set(Calendar.DATE, 31);
		calendar.set(Calendar.YEAR, 2025);

		Date date = calendar.getTime();

		AppointmentService tempAppt = new AppointmentService();

		assertEquals(0, AppointmentService.appointments.size());

		tempAppt.addAppointment(date, description);
		tempAppt.addAppointment(date, description);
		tempAppt.addAppointment(date, description);

		assertEquals(3, AppointmentService.appointments.size());

		tempAppt.deleteAppointment("1");

		assertEquals(2, AppointmentService.appointments.size());
		assertFalse(AppointmentService.appointments.containsKey("1"));

	}
	
	// Tests the addAppointment class with script injection in the description
	@Test
	void testAddApptInjectionDesc() {
		String description = "<script>alert('xss')</script>";
		Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.MONTH, 12);
		calendar.set(Calendar.DATE, 31);
		calendar.set(Calendar.YEAR, 2025);

		Date date = calendar.getTime();

		Assertions.assertThrows(IllegalArgumentException.class, () -> {
      		AppointmentService tempAppt = new AppointmentService();
      		tempAppt.addAppointment(date, description);
        });

	}
	
	// Additional service method tests for coverage
    @Test
    void testEditDateValid() {
        String apptID = "0";
        String description = "desc";
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, 12);
		calendar.set(Calendar.DATE, 31);
		calendar.set(Calendar.YEAR, 2025);
        Date date = calendar.getTime();
        AppointmentService service = new AppointmentService();
        service.addAppointment(date, description);
        Calendar newCal = Calendar.getInstance();
        newCal.set(Calendar.YEAR, 2030);
        newCal.set(Calendar.MONTH, 0);
        newCal.set(Calendar.DATE, 1);
        Date newDate = newCal.getTime();
        service.editDate(apptID, newDate);
        assertEquals(newDate, AppointmentService.appointments.get(apptID).getDate());
    }

    @Test
    void testEditDateInvalid() {
        String apptID = "0";
        String description = "desc";
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, 12);
		calendar.set(Calendar.DATE, 31);
		calendar.set(Calendar.YEAR, 2025);
        Date date = calendar.getTime();
        AppointmentService service = new AppointmentService();
        service.addAppointment(date, description);
        Calendar pastCal = Calendar.getInstance();
        pastCal.set(Calendar.YEAR, 2010);
        pastCal.set(Calendar.MONTH, 0);
        pastCal.set(Calendar.DATE, 1);
        Date pastDate = pastCal.getTime();
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            service.editDate(apptID, pastDate);
        });
    }
    @Test
    void testEditDescriptionValid() {
        String apptID = "0";
        String description = "desc";
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, 12);
		calendar.set(Calendar.DATE, 31);
		calendar.set(Calendar.YEAR, 2025);
        Date date = calendar.getTime();
        AppointmentService service = new AppointmentService();
        service.addAppointment(date, description);
        String newDesc = "new description";
        service.editDescription(apptID, newDesc);
        assertEquals(newDesc, AppointmentService.appointments.get(apptID).getDescription());
    }
    @Test
    void testEditDescriptionInvalid() {
        String description = "desc";
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, 12);
		calendar.set(Calendar.DATE, 31);
		calendar.set(Calendar.YEAR, 2025);
        Date date = calendar.getTime();
        AppointmentService service = new AppointmentService();
        service.addAppointment(date, description);
        String badDesc = "DROP TABLE";
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            service.editDescription("0", badDesc);
        });
    }
    @Test
    void testEditNonExistentID() {
        String apptID = "0";
        String description = "desc";
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, 12);
		calendar.set(Calendar.DATE, 31);
		calendar.set(Calendar.YEAR, 2025);
        Date date = calendar.getTime();
        AppointmentService service = new AppointmentService();
        service.addAppointment(date, description);
        service.editDate("999", date); // Should do nothing, no error
        service.editDescription("999", "desc"); // Should do nothing, no error
        assertEquals(1, AppointmentService.appointments.size());
        assertEquals(description, AppointmentService.appointments.get(apptID).getDescription());
    }
    
    @Test
    void testEditDateNull() {
        String description = "Valid description";
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, 12);
        calendar.set(Calendar.DATE, 31);
        calendar.set(Calendar.YEAR, 2025);
        Date date = calendar.getTime();
        AppointmentService tempAppt = new AppointmentService();
        tempAppt.addAppointment(date, description);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            tempAppt.editDate("0", null);
        });
    }
    
    @Test
    void testEditDatePast() {
        String description = "Valid description";
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, 12);
        calendar.set(Calendar.DATE, 31);
        calendar.set(Calendar.YEAR, 2025);
        Date date = calendar.getTime();
        AppointmentService tempAppt = new AppointmentService();
        tempAppt.addAppointment(date, description);
        Calendar pastCalendar = Calendar.getInstance();
        pastCalendar.set(Calendar.MONTH, 1);
        pastCalendar.set(Calendar.DATE, 1);
        pastCalendar.set(Calendar.YEAR, 2010);
        Date pastDate = pastCalendar.getTime();
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            tempAppt.editDate("0", pastDate);
        });
    }
    @Test
    void testEditDescriptionNull() {
        String description = "Valid description";
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, 12);
        calendar.set(Calendar.DATE, 31);
        calendar.set(Calendar.YEAR, 2025);
        Date date = calendar.getTime();
        AppointmentService tempAppt = new AppointmentService();
        tempAppt.addAppointment(date, description);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            tempAppt.editDescription("0", null);
        });
    }
    @Test
    void testEditDescriptionTooLong() {
        String description = "Valid description";
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, 12);
        calendar.set(Calendar.DATE, 31);
        calendar.set(Calendar.YEAR, 2025);
        Date date = calendar.getTime();
        AppointmentService tempAppt = new AppointmentService();
        tempAppt.addAppointment(date, description);
        String longDesc = new String(new char[51]).replace('\0', 'a');
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            tempAppt.editDescription("0", longDesc);
        });
    }
    @Test
    void testEditDescriptionBlacklist() {
        String description = "Valid description";
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, 12);
        calendar.set(Calendar.DATE, 31);
        calendar.set(Calendar.YEAR, 2025);
        Date date = calendar.getTime();
        AppointmentService tempAppt = new AppointmentService();
        tempAppt.addAppointment(date, description);
        String blacklistDesc = "DROP TABLE users";
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            tempAppt.editDescription("0", blacklistDesc);
        });
    }
    @Test
    void testEditDescriptionInjection() {
        String description = "Valid description";
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, 12);
        calendar.set(Calendar.DATE, 31);
        calendar.set(Calendar.YEAR, 2025);
        Date date = calendar.getTime();
        AppointmentService tempAppt = new AppointmentService();
        tempAppt.addAppointment(date, description);
        String injectionDesc = "Hello <script>";
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            tempAppt.editDescription("0", injectionDesc);
        });
    }
    @Test
    void testEditNonExistentAppointment() {
        AppointmentService tempAppt = new AppointmentService();
        // Should not throw, but should not change anything
        tempAppt.editDate("999", new Date());
        tempAppt.editDescription("999", "desc");
        assertNull(AppointmentService.appointments.get("999"));
    }
    // Injection pattern tests for editDescription
    @Test
    void testEditDescriptionInjectionPatterns() {
        String description = "Valid description";
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, 12);
        calendar.set(Calendar.DATE, 31);
        calendar.set(Calendar.YEAR, 2025);
        Date date = calendar.getTime();
        AppointmentService tempAppt = new AppointmentService();
        tempAppt.addAppointment(date, description);
        String[] patterns = {"<", ">", "'", "\"", "%", ";", "(", ")", "&", "+"};
        for (String pattern : patterns) {
            String desc = "Valid" + pattern + "Description";
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                tempAppt.editDescription("0", desc);
            });
        }
    }
    // Blacklist pattern tests for editDescription
    @Test
    void testEditDescriptionBlacklistPatterns() {
        String description = "Valid description";
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, 12);
        calendar.set(Calendar.DATE, 31);
        calendar.set(Calendar.YEAR, 2025);
        Date date = calendar.getTime();
        AppointmentService tempAppt = new AppointmentService();
        tempAppt.addAppointment(date, description);
        String[] blacklist = {"DROP TABLE", "DELETE FROM", "UPDATE", "INSERT", "<script>", "alert(", "SELECT *"};
        for (String pattern : blacklist) {
            String desc = "Valid " + pattern + " Description";
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                tempAppt.editDescription("0", desc);
            });
        }
    }

}