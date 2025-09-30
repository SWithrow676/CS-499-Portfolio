// Author: Stewart Withrow

package Project;

public class Task {
	
	// Private variables initialized
		private String taskID;
		private String name;
		private String description;
		
		// Constructor
		public Task (String taskID, String name, String description) {
			
			// Validates taskID (No longer than 10 char and not null)
			if (taskID == null || taskID.length() > 10 || taskID.equals("")) {
				throw new IllegalArgumentException("Invalid ID");
			}
			// Validates name (No longer than 20 char and not null)
			if (name == null || name.length() > 20 || name.equals("")) {
				throw new IllegalArgumentException("Invalid name");
			}
			// Validates description (No longer than 50 char and not null)
			if (description == null || description.length() > 50 || description.equals("")) {
				throw new IllegalArgumentException("Invalid description");
			}
			
			setTaskID(taskID);
			setName(name);
			setDescription(description);
			
			
		}
		
		/*
		 * Getters and setters for each variable of the Task object 
		 * ID setter is private as it cannot be changed
		 */
		
		public int getTaskID() {
			return Integer.valueOf(taskID);
		}

		public String getName() {
			return name;
		}

		public String getDescription() {
			return description;
		}
		
		private void setTaskID(String taskID) {
			this.taskID = taskID;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setDescription(String description) {
			this.description = description;
		}
}
