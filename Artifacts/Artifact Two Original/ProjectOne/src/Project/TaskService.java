// Author: Stewart Withrow

package Project;

import java.util.HashMap;

public class TaskService {
	
	// Holds the next ID for an added task
	int currentID = 0;
	
	// HashMap used since each entry has a unique key that cannot be duplicated
	public static HashMap<String, Task> tasks = new HashMap<String, Task>();
	
	// Adds an entry to the Task HashMap
	public void addTask(String name, String description) {
		
		String stringID = Integer.toString(currentID);	
		Task tempTask = new Task (stringID, name, description);
		tasks.put(stringID, tempTask);

		++currentID;
	}
	
	// Deletes an entry to the Task HashMap
	public void deleteTask(String ID) {
		
		if (tasks.containsKey(ID)) {
			tasks.remove(ID);
		}
	}
	
	// Changes an entry in the HashMap
	public void updateTask(String ID, String newName, String newDescription) {
		
		if (tasks.containsKey(ID)) {
			tasks.get(ID).setName(newName);
			tasks.get(ID).setDescription(newDescription);
		}
	}
}
