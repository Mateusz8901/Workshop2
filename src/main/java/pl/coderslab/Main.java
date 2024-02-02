import pl.coderslab.entity.User;
import pl.coderslab.dao.UserDao;

import java.util.List;
import java.util.Scanner;

public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    String username;
    UserDao userDao = new UserDao();

    boolean runProgram = true;

    while (runProgram) {
        printMenu();
        String userChoice = scanner.nextLine();


        switch (userChoice) {
            case "1":
                System.out.println("Enter username:");
                username = scanner.nextLine();
                System.out.println("Enter email:");
                String email = scanner.nextLine();
                System.out.println("Enter password:");
                String password = scanner.nextLine();

                User user = new User();
                user.setUsername(username);
                user.setEmail(email);
                user.setPassword(password);

                userDao.create(user);
                System.out.println("User created successfully.");

                break;

            case "2":
                System.out.println("Enter the username you want to display:");
                username = scanner.nextLine();

                User[] users = userDao.findAll();
                User foundUser = null;

                for (User currentUser : users) {
                    if (currentUser.getUsername().equals(username)) {
                        foundUser = currentUser;
                        break;
                    }
                }

                if (foundUser != null) {
                    System.out.println("User details:");
                    System.out.println("ID: " + foundUser.getId());
                    System.out.println("Username: " + foundUser.getUsername());
                    System.out.println("Email: " + foundUser.getEmail());
                    System.out.println("Password: " + foundUser.getPassword());
                } else {
                    System.out.println("User not found!");
                }
                break;
            case "3":
                System.out.println("Enter username of the user you want to update:");
                String existingUsername = scanner.nextLine();

                User userToUpdate = null;
                User[] usersToSearch = userDao.findAll();

                for (User currentUser : usersToSearch) {
                    if (currentUser.getUsername().equals(existingUsername)) {
                        userToUpdate = currentUser;
                        break;
                    }
                }

                if (userToUpdate != null) {
                    System.out.println("Enter new email:");
                    String newEmail = scanner.nextLine();
                    System.out.println("Enter new password:");
                    String newPassword = scanner.nextLine();

                    userToUpdate.setEmail(newEmail);
                    userToUpdate.setPassword(newPassword);

                    userDao.update(userToUpdate);
                    System.out.println("User updated successfully.");
                } else {
                    System.out.println("User not found.");
                }

                break;
            case "4": {
                int tryCount = 0;
                while (true) {
                    try {
                        if (tryCount == 3) {
                            System.out.println("Maximum number of attempts reached. Returning to main menu.");
                            break;
                        }
                        System.out.println("Enter the ID of the user you want to delete:");
                        int idToDelete = Integer.parseInt(scanner.nextLine());

                        User userToDelete = userDao.read(idToDelete);

                        if (userToDelete != null) {
                            userDao.delete(idToDelete);
                            System.out.println("User deleted successfully.");
                            break;
                        } else {
                            System.out.println("User not found! Please, try again.");
                            tryCount++;
                        }
                    } catch (Exception e) {
                        System.out.println("There was an error: " + e.getMessage() + " Please, try again.");
                        tryCount++;
                    }
                }
                break;
            }
            case "5": {
                User[] allUsers = userDao.findAll();
                for (User currentUser : allUsers) {
                    System.out.println("ID: " + currentUser.getId());
                    System.out.println("Username: " + currentUser.getUsername());
                    System.out.println("Email: " + currentUser.getEmail());
                    System.out.println("Password: " + currentUser.getPassword());
                    System.out.println("------------------");
                }
                break;
            }
            case "q":
                runProgram = false;
                System.out.println("Quitting the program.");
                break;
        }
    }
}

private static void printMenu() {
    System.out.println("Choose an option:");
    System.out.println("1 - Add a user");
    System.out.println("2 - Read user data");
    System.out.println("3 - Update user data");
    System.out.println("4 - Delete a user");
    System.out.println("5 - Display all users");
    System.out.println("q - Quit");
}