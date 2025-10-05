package Project;

import java.util.regex.Pattern;

/**
 * Class with basic task information such as name and description.
 * Provides validation for all fields upon creation.
 * 
 * @author Stewart Withrow
 */
public class Task {
	
	private static final int ID_MAX_LENGTH = 10;
	private static final int NAME_MAX_LENGTH = 20;
	private static final int DESC_MAX_LENGTH = 50;
	private static final Pattern INJECTION_PATTERN = Pattern.compile("[<>\"'%;()&+]");
	
	private String taskID;
	private String name;
	private String description;
		
	/**
     * Constructs a Task with validated fields.
     * 
     * @param taskID Unique task identifier (<= 10 chars, not null and not empty)
     * @param name Task name (<= 20 chars, not null and not empty)
     * @param description Task description (<= 50 chars, not null and not empty)
     * @throws IllegalArgumentException if any parameter is invalid
     */
	public Task (String taskID, String name, String description) {
			
		validateTaskID(taskID);
		validateName(name);
		validateDescription(description);
			
		setTaskID(taskID);
		setName(name);
		setDescription(description);
			
	}
		
	 /**
     * Gets the task's unique identifier as an integer.
     * @return Task ID as integer
     */
	public int getTaskID() {
		return Integer.valueOf(taskID);
	}
	
	 /**
	 * Gets the task's name.
	 * @return name Task name
	 */
	public String getName() {
		return name;
	}

	 /**
	 * Gets the task's description.
	 * @return Task description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
     * Sets the task's ID.
     * ID setter is private as it cannot be changed
     * @param taskID Task ID
     */
	private void setTaskID(String taskID) {
		validateTaskID(taskID);
		this.taskID = taskID;
	}

	 /**
     * Sets the task's name.
     * @param name Task name
     */
	public void setName(String name) {
		validateName(name);
		this.name = name;
	}

	 /**
	 * Sets the task's description.
	 * @param description Task description
	 */
	public void setDescription(String description) {
		validateDescription(description);
		this.description = description;
	}
	
	
	/**
	 * Validates the task ID.
	 * @param taskID Task ID
	 */
	private void validateTaskID(String taskID) {
	    if (taskID == null || taskID.length() > ID_MAX_LENGTH || taskID.isEmpty()) {
	        throw new IllegalArgumentException("Task ID must be non-null, non-empty and <= " + ID_MAX_LENGTH + " characters.");
	    }
	    validateNoInjection(taskID, "Task ID");
	}

	/**
	 * Validates the task name.
	 * @param name Task name
	 */
	private void validateName(String name) {
	    if (name == null || name.length() > NAME_MAX_LENGTH || name.isEmpty()) {
	        throw new IllegalArgumentException("Name must be non-null, non-empty and < " + NAME_MAX_LENGTH + " characters.");
	    }
	    validateNoInjection(name, "Name");
	}

	/**
	 * Validates the task description.
	 * @param description Task description
	 */
	private void validateDescription(String description) {
	    if (description == null || description.length() > DESC_MAX_LENGTH || description.isEmpty()) {
	        throw new IllegalArgumentException("Description must be non-null, non-empty and < " + DESC_MAX_LENGTH + " characters.");
	    }
	    validateNoInjection(description, "Description");
	}
	
	/**
	 * Validates input against injection attacks.
	 * @param input Input string to validate
	 * @param fieldName Name of the field being validated (for error messages)
	 */
	public static void validateNoInjection(String input, String fieldName) {
	    if (input != null && INJECTION_PATTERN.matcher(input).find()) {
	        throw new IllegalArgumentException(fieldName + " contains illegal characters.");
	    }
	}
}