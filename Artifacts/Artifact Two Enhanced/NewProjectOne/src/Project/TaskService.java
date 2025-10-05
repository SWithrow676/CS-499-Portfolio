package Project;

import java.util.HashMap;

/**
 * Service class to manage Task objects.
 * Provides methods to add, delete, and edit tasks.
 * 
 * @author Stewart Withrow
 */
public class TaskService {
	
	// Holds the next ID for an added task
	private int currentID = 0;
	
	// HashMap to hold task objects, keyed by their unique ID
	public static HashMap<String, Task> tasks = new HashMap<String, Task>();
	
	/**
     * Adds a new Task to the map.
     * 
     * @param name Task name
     * @param description Task description
     * @throws IllegalArgumentException if parameters are invalid
     */
	public void addTask(String name, String description) {
		String stringID = Integer.toString(currentID);	
		Task newTask = new Task (stringID, name, description);
		tasks.put(stringID, newTask);

		++currentID;
	}
	
	/**
     * Deletes a Task from the map by ID.
     * 
     * @param ID Task ID to delete
     */
	public void deleteTask(String ID) {
		tasks.remove(ID);
	}
	
	/**
     * Updates an existing Task's name and description.
     * 
     * @param ID Task ID
     * @param newName New name for the task
     * @param newDescription New description for the task
     */
	public void editTask(String ID, String newName, String newDescription) {
		Task task = tasks.get(ID);
		if (task != null) {
			task.setName(newName);
			task.setDescription(newDescription);
		}
	}
}