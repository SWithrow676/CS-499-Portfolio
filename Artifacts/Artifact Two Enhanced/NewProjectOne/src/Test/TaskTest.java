package Test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import Project.Task;

/**
 * Unit tests for the Task class.
 * Tests constructor, getters, and setters with valid and invalid inputs.
 * 
 * @author Stewart Withrow
 */
class TaskTest {

    /*
     * Tests valid creation of a Task object.
     */
    @Test
    void testTask() {
        String taskID = "1";
        String name = "John Smith";
        String description = "Lorem ipsum dolor sit amet.";
        
        Task task = new Task(taskID, name, description);
        
        Assertions.assertEquals(1, task.getTaskID());
        Assertions.assertEquals(name, task.getName());
        Assertions.assertEquals(description, task.getDescription());
    }

    /*
     * Tests the Task constructor with too-long description.
     */
    @Test
    void testTaskDescriptionLong() {
        String taskID = "1";
        String name = "John Smith";
        String description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod.";
        
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Task(taskID, name, description);
        });
    }

    /*
     * Tests the Task constructor with too-long ID.
     */
    @Test
    void testTaskIDLong() {
        String taskID = "123456789101112";
        String name = "John Smith";
        String description = "Lorem ipsum dolor sit amet.";
        
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Task(taskID, name, description);
        });
    }

    /*
     * Tests the Task constructor with null ID.
     */
    @Test
    void testTaskIDNull() {
        String taskID = null;
        String name = "John Smith";
        String description = "Lorem ipsum dolor sit amet.";
        
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Task(taskID, name, description);
        });
    }

    /*
     * Tests the Task constructor with too-long name.
     */
    @Test
    void testTaskNameLong() {
        String taskID = "1";
        String name = "Testing A. Too-Long-Name";
        String description = "Lorem ipsum dolor sit amet.";
        
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Task(taskID, name, description);
        });
    }
    
    /*
     * Tests the Task constructor with null name
     */
    @Test
    void testTaskNameNull() {
        String taskID = "1";
        String name = null;
        String description = "Lorem ipsum dolor sit amet.";
        
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Task(taskID, name, description); 
        });
    }
    
    /*
     * Tests the Task constructor with empty name
     */
    @Test
    void testTaskNameEmpty() {
        String taskID = "1";
        String name = "";
        String description = "Lorem ipsum dolor sit amet.";
        
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Task(taskID, name, description);
        });
    }
    
    /*
     * Tests setting a valid description
     */
    @Test
    void testSetDescription() {
        String taskID = "1";
        String name = "John Smith";
        String description = "Lorem ipsum dolor sit amet.";
        String newDesc = "This is an example description.";
        
        Task task = new Task(taskID, name, description);
        task.setDescription(newDesc);    
        
        Assertions.assertEquals("This is an example description.", task.getDescription());
    }
    
    /*
     * Tests the Task constructor with too-long description
     */
    @Test
    void testSetDescriptionLong() {
        String taskID = "1";
        String name = "John Smith";
        String description = "This descprition is way way way too long for this to be a valid input.";
        
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Task(taskID, name, description); 
        });
    }
    
    /*
     * Tests the Taskconstructor with null description
     */
    @Test
    void testSetDescriptionNull() {
        String taskID = "1";
        String name = "John Smith";
        String description = null;
        
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Task(taskID, name, description);
        });
    }
    
    /*
     * Tests the Task constructor with empty description
     */
    @Test
    void testSetDescriptionEmpty() {
        String taskID = "1";
        String name = "John Smith";
        String description = "";
        
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Task(taskID, name, description);
        });
    }
    
    /*
     * Tests Name setter with null input
     */
    @Test
    void testSetNameNullSetter() {
        String taskID = "1";
        String name = "Valid Name";
        String description = "Valid description";
        
        Task task = new Task(taskID, name, description);
        String newName = null;
        
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            task.setName(newName);
        });
    }
    
    /*
     * Tests Name setter with too-long input
     */
    @Test
    void testSetNameLongSetter() {
        String taskID = "1";
        String name = "Valid Name";
        String description = "Valid description";
        String longName = new String(new char[30]).replace('\0', 'a');
        
        Task task = new Task(taskID, name, description);
        
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            task.setName(longName);
        });
    }
    
    /*
	 * Tests Name setter with empty input
	 */
    @Test
    void testSetNameEmptySetter() {
        String taskID = "1";
        String name = "Valid Name";
        String description = "Valid description";
        String newName = "";
        
        Task task = new Task(taskID, name, description);
        
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            task.setName(newName);
        });
    }
    
    /*
     * Tests Description setter with null input
     */
    @Test
    void testSetDescriptionNullSetter() {
        String taskID = "1";
        String name = "Valid Name";
        String description = "Valid description";
        String newDescription = null;
        
        Task task = new Task(taskID, name, description);
        
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            task.setDescription(newDescription);
        });
    }
    
    /*
	 * Tests Description setter with too-long input
	 */
    @Test
    void testSetDescriptionLongSetter() {
        String taskID = "1";
        String name = "Valid Name";
        String description = "Valid description";
        String longDesc = new String(new char[60]).replace('\0', 'a');
        
        Task task = new Task(taskID, name, description);
        
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            task.setDescription(longDesc);
        });
    }
    
    /*
     * Tests Description setter with empty input
     */
    @Test
    void testSetDescriptionEmptySetter() {
        String taskID = "1";
        String name = "Valid Name";
        String description = "Valid description";
        String newDescription = "";
        
        Task task = new Task(taskID, name, description);
        
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            task.setDescription(newDescription);
        });
    }
    
    /*
	 * Tests the validateNoInjection method with valid input
	 */
    @Test
    void testValidateNoInjectionValid() {
        String input = "NormalName";
        String field = "Name";
        
        Assertions.assertDoesNotThrow(() -> Project.Task.validateNoInjection(input, field));
    }

    /*
     * Tests the validateNoInjection method with invalid input
     */
    @Test
    void testValidateNoInjectionInvalid() {
        String input = "Bad<Name>";
        String field = "Name";
        
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () ->
            Project.Task.validateNoInjection(input, field));
        Assertions.assertTrue(exception.getMessage().contains("illegal characters"));
    }

}