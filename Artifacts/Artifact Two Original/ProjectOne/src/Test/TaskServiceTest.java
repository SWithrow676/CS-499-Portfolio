// Author: Stewart Withrow

package Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import Project.TaskService;

class TaskServiceTest {
	
	// Clears hash after each test
	@AfterEach
	void tearDown() throws Exception {
		TaskService.tasks.clear();
	}

	// Tests the addTask class
	@Test
	void testAddUniqueTask() {
		String taskID = "0";
      	String name = "John Smith";
        String description = "Lorem ipsum dolor sit amet.";
        
        TaskService testTask = new TaskService();
        
        assertEquals(0, TaskService.tasks.size());
        
        testTask.addTask(name, description);
       
		assertTrue(TaskService.tasks.containsKey(taskID));
		assertEquals(name, TaskService.tasks.get(taskID).getName());
		assertEquals(description, TaskService.tasks.get(taskID).getDescription());              
        
	}
	
	// Tests the deleteTask class
	@Test 
	void testDeleteTask() {
		  
      	String name = "Greg Tippton";
        String description = "This is a good description";
		  
        TaskService testTask = new TaskService();
        
        assertEquals(0, TaskService.tasks.size());

        testTask.addTask(name, description); 
        testTask.addTask(name, description); 
        testTask.addTask(name, description); 
		  
		assertEquals(3,TaskService.tasks.size());
		  
		testTask.deleteTask("1");
		  
		assertEquals(2,TaskService.tasks.size());		
		assertFalse(TaskService.tasks.containsKey("1"));
			 
	}
	
	// Tests the updateTask class with valid entries
	@Test
	void testUpdateTasks() {
		String taskID = "0";
      	String name = "John Smith";
        String description = "Lorem ipsum dolor sit amet.";
		  
        TaskService testTask = new TaskService();
        
        testTask.addTask(name, description);

        testTask.updateTask("0", name, "New description");
        assertEquals("New description", TaskService.tasks.get(taskID).getDescription());
        assertEquals(name, TaskService.tasks.get(taskID).getName());
        
	}
	
	// Tests the updateTask class with invalid entries
	@Test
	void testBadUpdateTasks() {
		String taskID = "0";
      	String name = "John Smith";
        String description = "Lorem ipsum dolor sit amet.";
		  
        TaskService tempTask = new TaskService();
        
        tempTask.addTask(name, description);

        tempTask.updateTask("1", name, "New description");
        assertNotEquals("New description", TaskService.tasks.get(taskID).getDescription());
        assertEquals(name, TaskService.tasks.get(taskID).getName());
        
	}

}
