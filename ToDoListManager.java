package JAVA;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * A program to manage a to-do list.
 * <p>
 * Users can add tasks, remove tasks, view the list, save to a file, and load
 * from a file.
 * </p>
 * 
 * @author YourName
 * @version 1.0
 */
public class ToDoListManager {

	/** List of tasks (uses ArrayList). */
	private List<String> tasks;

	/** Set of unique tasks to prevent duplicates (uses HashSet). */
	private Set<String> uniqueTasks;

	/**
	 * Constructor to initialize the task list and set.
	 */
	public ToDoListManager() {
		tasks = new ArrayList<>();
		uniqueTasks = new HashSet<>();
	}

	/**
	 * Adds a new task to the to-do list if it is unique.
	 * 
	 * @param task the task to be added
	 */
	public void addTask(String task) {
		if (uniqueTasks.contains(task)) {
			System.out.println("Task already exists. Please add a unique task.");
		} else {
			tasks.add(task);
			uniqueTasks.add(task);
			System.out.println("Task added: " + task);
		}
	}

	/**
	 * Removes a task from the to-do list by its index.
	 * 
	 * @param index the index of the task to be removed (0-based)
	 */
	public void removeTask(int index) {
		if (index >= 0 && index < tasks.size()) {
			String removedTask = tasks.remove(index);
			uniqueTasks.remove(removedTask);
			System.out.println("Task removed: " + removedTask);
		} else {
			System.out.println("Invalid index.");
		}
	}

	/**
	 * Displays all tasks in the to-do list.
	 */
	public void viewTasks() {
		System.out.println("\nTo-Do List:");
		if (tasks.isEmpty()) {
			System.out.println("No tasks available.");
		} else {
			for (int i = 0; i < tasks.size(); i++) {
				System.out.println((i + 1) + ". " + tasks.get(i));
			}
		}
	}

	/**
	 * Saves the to-do list to a file.
	 * 
	 * @param filename the name of the file to save the tasks to
	 */
	public void saveTasks(String filename) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
			for (String task : tasks) {
				writer.write(task);
				writer.newLine();
			}
			System.out.println("Tasks saved to " + filename);
		} catch (IOException e) {
			System.out.println("Error saving tasks: " + e.getMessage());
		}
	}

	/**
	 * Loads the to-do list from a file.
	 * 
	 * @param filename the name of the file to load the tasks from
	 */
	public void loadTasks(String filename) {
		File file = new File(filename);
		if (!file.exists()) {
			System.out.println("File not found: " + filename);
			return;
		}

		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
			String line;
			tasks.clear();
			uniqueTasks.clear();
			while ((line = reader.readLine()) != null) {
				tasks.add(line);
				uniqueTasks.add(line);
			}
			System.out.println("Tasks loaded from " + filename);
		} catch (IOException e) {
			System.out.println("Error loading tasks: " + e.getMessage());
		}
	}

	/**
	 * Main method to provide a menu and interact with the user.
	 * 
	 * @param args command-line arguments (not used)
	 */
	public static void main(String[] args) {
		ToDoListManager manager = new ToDoListManager();
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.println("\nMenu:");
			System.out.println("1. Add Task");
			System.out.println("2. View Tasks");
			System.out.println("3. Remove Task");
			System.out.println("4. Save Tasks");
			System.out.println("5. Load Tasks");
			System.out.println("6. Exit");
			System.out.print("Choose an option: ");

			int choice;
			try {
				choice = scanner.nextInt();
				scanner.nextLine(); // Handle newline
			} catch (Exception e) {
				System.out.println("Invalid input. Please enter a number.");
				scanner.nextLine(); // Clear invalid input
				continue;
			}

			switch (choice) {
			case 1:
				System.out.print("Enter task: ");
				String task = scanner.nextLine();
				manager.addTask(task);
				break;
			case 2:
				manager.viewTasks();
				break;
			case 3:
				System.out.print("Enter task number to remove: ");
				try {
					int index = scanner.nextInt() - 1;
					scanner.nextLine(); // Handle newline
					manager.removeTask(index);
				} catch (Exception e) {
					System.out.println("Invalid task number.");
					scanner.nextLine(); // Clear invalid input
				}
				break;
			case 4:
				System.out.print("Enter filename to save tasks: ");
				String saveFile = scanner.nextLine();
				manager.saveTasks(saveFile);
				break;
			case 5:
				System.out.print("Enter filename to load tasks: ");
				String loadFile = scanner.nextLine();
				manager.loadTasks(loadFile);
				break;
			case 6:
				System.out.println("Exiting program.");
				scanner.close();
				return;
			default:
				System.out.println("Invalid option. Please try again.");
			}
		}
	}
}