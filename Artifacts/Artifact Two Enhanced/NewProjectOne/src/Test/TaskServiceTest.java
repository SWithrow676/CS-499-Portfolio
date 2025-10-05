package Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import Project.TaskService;

/**
 * Unit tests for the TaskService class.
 * Tests adding, deleting, and editing tasks with valid and invalid inputs.
 * 
 * @author Stewart Withrow
 */
class TaskServiceTest {

	/*
	 * Clears the contacts map after each test to ensure test isolation.
	 * @throws Exception if an error occurs during teardown
	 */
    @AfterEach
    void tearDown() throws Exception {
        TaskService.tasks.clear();
    }

    /*
	 * Tests adding a unique task to the TaskService.
	 */
    @Test
    void testAddUniqueTask() {
        String taskID = "0";
        String name = "John Smith";
        String description = "Lorem ipsum dolor sit amet.";
        TaskService taskService = new TaskService();
        
        int expectedSizeBefore = 0;
        int expectedSizeAfter = 1;
        
        assertEquals(expectedSizeBefore, TaskService.tasks.size());
        taskService.addTask(name, description);
        
        assertEquals(expectedSizeAfter, TaskService.tasks.size());
        assertTrue(TaskService.tasks.containsKey(taskID));
        assertEquals(name, TaskService.tasks.get(taskID).getName());
        assertEquals(description, TaskService.tasks.get(taskID).getDescription());
    }

    /*
     * Tests deleting a task from the TaskService.
     */
    @Test
    void testDeleteTask() {
        String name = "Greg Tippton";
        String description = "This is a good description";
        TaskService taskService = new TaskService();
        
        int expectedSizeBefore = 0;
        int expectedSizeAfterAdd = 3;
        int expectedSizeAfterDelete = 2;
        String deleteID = "1";
        
        assertEquals(expectedSizeBefore, TaskService.tasks.size());
        taskService.addTask(name, description);
        taskService.addTask(name, description);
        taskService.addTask(name, description);
        assertEquals(expectedSizeAfterAdd, TaskService.tasks.size());
        
        taskService.deleteTask(deleteID);
        assertEquals(expectedSizeAfterDelete, TaskService.tasks.size());
        assertFalse(TaskService.tasks.containsKey(deleteID));
    }

    /*
	 * Tests editing a task's name and description.
	 */
    @Test
    void testEditTasks() {
        String taskID = "0";
        String name = "John Smith";
        String description = "Lorem ipsum dolor sit amet.";
        String newDescription = "New description";
        
        TaskService taskService = new TaskService();
        taskService.addTask(name, description);
        taskService.editTask(taskID, name, newDescription);
        
        assertEquals(newDescription, TaskService.tasks.get(taskID).getDescription());
        assertEquals(name, TaskService.tasks.get(taskID).getName());
    }

    /*
     * Tests editing a task with an ID that does not exist.
     */
    @Test
    void testBadEditTasks() {
        String taskID = "0";
        String name = "John Smith";
        String description = "Lorem ipsum dolor sit amet.";
        String newDescription = "New description";
        String badID = "1";
        
        TaskService tempTask = new TaskService();
        tempTask.addTask(name, description);
        tempTask.editTask(badID, name, newDescription);
        
        assertNotEquals(newDescription, TaskService.tasks.get(taskID).getDescription());
        assertEquals(name, TaskService.tasks.get(taskID).getName());
    }

    /*
     * Tests editing a task with null name parameters.
     */
    @Test
    void testEditTaskNullName() {
        String taskID = "0";
        String name = "John Smith";
        String description = "Lorem ipsum dolor sit amet.";
        String newName = null;
        String newDescription = "New description";
        
        TaskService taskService = new TaskService();
        taskService.addTask(name, description);
        
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            taskService.editTask(taskID, newName, newDescription);
        });
    }

    /*
	 * Tests editing a task with null description parameters.
	 */
    @Test
    void testEditTaskNullDescription() {
        String taskID = "0";
        String name = "John Smith";
        String description = "Lorem ipsum dolor sit amet.";
        String newName = "New Name";
        String newDescription = null;
        
        TaskService taskService = new TaskService();
        taskService.addTask(name, description);
        
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            taskService.editTask(taskID, newName, newDescription);
        });
    }

    /*
     * Tests editing a task with empty name parameters.
     */
    @Test
    void testEditTaskEmptyName() {
        String taskID = "0";
        String name = "John Smith";
        String description = "Lorem ipsum dolor sit amet.";
        String newName = "";
        String newDescription = "New description";
        
        TaskService taskService = new TaskService();
        taskService.addTask(name, description);
        
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            taskService.editTask(taskID, newName, newDescription);
        });
    }

    /*
	 * Tests editing a task with empty description parameters.
	 */
    @Test
    void testEditTaskEmptyDescription() {
        String taskID = "0";
        String name = "John Smith";
        String description = "Lorem ipsum dolor sit amet.";
        String newName = "New Name";
        String newDescription = "";
        
        TaskService taskService = new TaskService();
        taskService.addTask(name, description);
        
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            taskService.editTask(taskID, newName, newDescription);
        });
    }
    
	/*
	 * Tests editing a task with too-long name parameters.
	 */
    @Test
    void testEditTaskLongName() {
        String taskID = "0";
        String name = "Valid Name";
        String description = "Valid description";
        String longName = "This is a very long name that surpases the maximum length";
        
        TaskService taskService = new TaskService();
        taskService.addTask(name, description);
        
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            taskService.editTask(taskID, longName, description);
        });
    }

    /*
     * Tests editing a task with too-long description parameters.
     */
    @Test
    void testEditTaskLongDescription() {
        String taskID = "0";
        String name = "Valid Name";
        String description = "Valid description";
        String longDesc = "This description is way too long and surpasses the maximum allowed length for a task description.";
        
        TaskService taskService = new TaskService();
        taskService.addTask(name, description);
        
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            taskService.editTask(taskID, name, longDesc);
        });
    }

}