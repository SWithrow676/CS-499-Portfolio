// Author: Stewart Withrow

package Test;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import Project.Task;

class TaskTest {

		// Tests and validates the constructor
		@Test
		void testContact() {
			String taskID = "1";
	      	String name = "John Smith";
	        String description = "Lorem ipsum dolor sit amet.";
			
	        Task testTask = new Task(taskID, name, description);
	        
	        assertEquals(1, testTask.getTaskID());
	        assertEquals(name, testTask.getName());
	        assertEquals(description, testTask.getDescription());
		}
		
		// Tests the constructor with invalid info 
		@Test
		void testTaskInvalid() {
			String taskID = "1";
	      	String name = "John Smith";
	        String description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod.";
	        
	        Assertions.assertThrows(IllegalArgumentException.class, () -> {
	        	new Task(taskID, name, description);
	        });
		}
		
		// Tests the constructor with invalid ID 
		@Test
		void testTaskIDInvalid() {
			String taskID = "123456789101112";
			String name = "John Smith";
			String description = "Lorem ipsum dolor sit amet.";
			
			Assertions.assertThrows(IllegalArgumentException.class, () -> {
				new Task(taskID, name, description);
			});
		}
		
		// Tests the constructor with null ID 
		@Test
		void testTaskIDNull() {
			String taskID = null;
			String name = "John Smith";
			String description = "Lorem ipsum dolor sit amet.";
					
			Assertions.assertThrows(IllegalArgumentException.class, () -> {
				new Task(taskID, name, description);
			});
		}

		// Tests the constructor with valid name
		@Test
		void testSetName() {
			String taskID = "1";
			String name = "John Smith";
			String description = "Lorem ipsum dolor sit amet.";
			String newName = "New Name";
							
			Task testTask = new Task(taskID, name, description);
			
			testTask.setName(newName);        
	        assertEquals("New Name", testTask.getName());
		}
		
		// Tests the constructor with invalid name
		@Test
		void testSetNameInvalid() {
			String taskID = "1";
			String name = "Testing A. Too-Long-Name";
			String description = "Lorem ipsum dolor sit amet.";
			
			Assertions.assertThrows(IllegalArgumentException.class, () -> {
				new Task(taskID, name, description);
			});
		}
				
		// Tests the constructor with null name
		@Test
		void testSetNameNull() {
			String taskID = "1";
			String name = null;
			String description = "Lorem ipsum dolor sit amet.";
			
			Assertions.assertThrows(IllegalArgumentException.class, () -> {
				new Task(taskID, name, description); 
			});
		}
		
		// Tests the constructor with empty name
		@Test
		void testSetNameEmpty() {
			String taskID = "1";
			String name = "";
			String description = "Lorem ipsum dolor sit amet.";
							
			Assertions.assertThrows(IllegalArgumentException.class, () -> {
				new Task(taskID, name, description);
			});
		}
		
		// Tests the constructor with valid description
		@Test
		void testSetDescription() {
			String taskID = "1";
			String name = "John Smith";
			String description = "Lorem ipsum dolor sit amet.";
			String newDesc = "This is an example description.";
									
			Task testTask = new Task(taskID, name, description);
					
			testTask.setDescription(newDesc);        
			assertEquals("This is an example description.", testTask.getDescription());
		}
				
		// Tests the constructor with invalid description
		@Test
		void testSetDescriptionInvalid() {
			String taskID = "1";
			String name = "John Smith";
			String description = "This descprition is way way way too long for this to be a valid input.";
					
			Assertions.assertThrows(IllegalArgumentException.class, () -> {
				new Task(taskID, name, description); 
			});
		}
						
		// Tests the constructor with null description
		@Test
		void testSetDescriptionNull() {
			String taskID = "1";
			String name = "John Smith";
			String description = null;
					
			Assertions.assertThrows(IllegalArgumentException.class, () -> {
				new Task(taskID, name, description);
			});
		}
		
		// Tests the constructor with empty name
		@Test
		void testSetDescriptionEmpty() {
			String taskID = "1";
			String name = "John Smith";
			String description = "";
									
			Assertions.assertThrows(IllegalArgumentException.class, () -> {
				new Task(taskID, name, description);
			});
		}

}
