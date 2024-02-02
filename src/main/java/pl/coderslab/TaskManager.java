package pl.coderslab;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class TaskManager {
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();
        manager.run();
    }

    private List<Task> tasks = new ArrayList<>();

    public void loadTasksFromFile(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length >= 3) {
                    String title = parts[0];
                    LocalDate dueDate = LocalDate.parse(parts[1], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    boolean isDone = Boolean.parseBoolean(parts[2]);
                    tasks.add(new Task(title, dueDate, isDone));
                }
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            throw e;
        }
    }

    public void saveTaskToFile(Task task, String filename) {
        try (FileWriter writer = new FileWriter(filename, true)) {
            String taskDetails = task.getTitle() + ", "
                    + task.getDueDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                    + ", " + task.isDone();
            writer.write(taskDetails);
            writer.write(System.lineSeparator());
        } catch (IOException ex) {
            System.out.println("An error occurred while trying to save the task to file");
            ex.printStackTrace();
        }
    }

    public void saveTasksToFile(String filename) {
        try (FileWriter writer = new FileWriter(filename, false)) {
            for (Task task : tasks) {
                writer.write(task.toString());
                writer.write(System.lineSeparator());
            }
        } catch (IOException ex) {
            System.out.println("An error occurred while trying to save tasks to file");
            ex.printStackTrace();
        }
    }

    public void printMenu() {
        System.out.println(pl.coderslab.ConsoleColors.RED + "Please select an option:" + pl.coderslab.ConsoleColors.RESET);
        System.out.println(pl.coderslab.ConsoleColors.GREEN + "(1) Add task" + pl.coderslab.ConsoleColors.RESET);
        System.out.println(pl.coderslab.ConsoleColors.YELLOW + "(2) Remove task" + pl.coderslab.ConsoleColors.RESET);
        System.out.println(pl.coderslab.ConsoleColors.BLUE + "(3) List tasks" + pl.coderslab.ConsoleColors.RESET);
        System.out.println(pl.coderslab.ConsoleColors.PURPLE + "(4) Exit" + pl.coderslab.ConsoleColors.RESET);
    }

    public int getMenuOption() {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        while (choice == -1) {
            try {
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number for the menu option.");
                scanner.next();
            }
        }
        return choice;
    }

    boolean keepRunning = true;
    Scanner scanner = new Scanner(System.in);

    public void run() {

        while (keepRunning) {
            printMenu();
            int choice = getMenuOption();
            switch (choice) {

                case 1:
                    System.out.print("Enter task title: ");
                    String title = scanner.nextLine().trim();

                    LocalDate dueDate = null;
                    while (dueDate == null) {
                        try {
                            System.out.print("Enter task due date (dd-mm-yyyy): ");
                            String dueDateStr = scanner.nextLine().trim();
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                            dueDate = LocalDate.parse(dueDateStr, formatter);
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid date entered. Please enter the date in the format dd-MM-yyyy.");
                        }
                    }

                    Boolean isDone = null;
                    while (isDone == null) {
                        try {
                            System.out.print("Is your task important? (true for yes, false for no): ");
                            String booleanStr = scanner.nextLine().trim();
                            if (booleanStr.equalsIgnoreCase("true")) {
                                isDone = true;
                            } else if (booleanStr.equalsIgnoreCase("false")) {
                                isDone = false;
                            } else {
                                System.out.println("Invalid input. Please enter true or false.");
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input. Please enter true or false.");
                        }
                    }

                    Task task = new Task(title, dueDate, isDone);
                    saveTaskToFile(task, "tasks.csv");
                    System.out.println("Task added successfully.");
                    break;

                case 2:
                    System.out.println("Enter the index of the task to remove:");
                    int index = scanner.nextInt();
                    scanner.nextLine();

                    if (index >= 0 && index < tasks.size()) {
                        tasks.remove(index);
                        System.out.println("Task removed successfully.");

                        saveTasksToFile("tasks.csv");
                    } else {
                        System.out.println("Invalid task index.");
                    }

                    break;

                case 3:

                    try {
                        loadTasksFromFile("tasks.csv");
                    } catch (IOException e) {
                        System.out.println("An error occurred while loading tasks from file.");
                        break;
                    }

                    for (int i = 0; i < tasks.size(); i++) {
                        Task t = tasks.get(i);
                        System.out.println(i + ": " + t.getTitle() + " " + t.getDueDate() + " " + t.isDone());
                    }

                    break;

                case 4:
                    saveTasksToFile("tasks.csv");
                    System.out.println("Tasks saved successfully. Exiting...");
                    keepRunning = false;
                    break;

                default:
                    System.out.println("Invalid choice, please choose a number between 1 and 4.");
            }
        }
    }
}

