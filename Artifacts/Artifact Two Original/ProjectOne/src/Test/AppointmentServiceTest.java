// Author: Stewart Withrow

package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.AfterEach;
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
		String apptID = "0";
		String description = "";
		Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.MONTH, 12);
		calendar.set(Calendar.DATE, 31);
		calendar.set(Calendar.YEAR, 2025);

		Date date = calendar.getTime();

      	IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      		AppointmentService tempAppt = new AppointmentService();
      		tempAppt.addAppointment(date, description);
        });

	}
	
	// Tests the addAppointment class with null description
	@Test
	void testAddApptNullDesc() {
		String apptID = "0";
		String description = null;
		Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.MONTH, 12);
		calendar.set(Calendar.DATE, 31);
		calendar.set(Calendar.YEAR, 2025);

		Date date = calendar.getTime();

      	IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      		AppointmentService tempAppt = new AppointmentService();
      		tempAppt.addAppointment(date, description);
        });

	}
	
	// Tests the deleteAppointment class
	@Test
	void testDeleteAppt() {

		String id = "0";
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

}
