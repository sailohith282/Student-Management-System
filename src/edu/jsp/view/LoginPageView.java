package edu.jsp.view;

import java.util.Scanner;
import edu.jsp.dao.LoginDAO;
import edu.jsp.dto.Login;

public class LoginPageView {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            LoginDAO loginDAO = new LoginDAO();
            System.out.println("Welcome to student management system");
            System.out.println("1. STUDENT LOGIN");
            System.out.println("2. ADMIN LOGIN");
            System.out.println("3. NEW USER REGISTER");
            System.out.println("Select the choice:");
            
            while (!scanner.hasNextInt()) {
                System.out.println("Please enter a valid choice:");
                scanner.next(); // Clear invalid input
            }
            choice = scanner.nextInt();

            switch (choice) {
                case 1: {
                    System.out.println("Enter user ID:");
                    String userid = scanner.next();
                    System.out.println("Enter User password:");
                    String password = scanner.next();
                    Login user = loginDAO.fetchUserCredentials(userid);
                    if (user != null && userid.equals(user.getUserid()) && password.equals(user.getUserpassword())) {
                        System.out.println("Login successful!");
                        System.out.println("Redirecting to Student Management System...");
                        StudentView.main(new String[]{userid, password}); // Call StudentView
                    } else {
                        System.out.println("Invalid credentials. Register as a new USER.");
                        System.out.println("Do you want to continue to register? (y/n):");
                        char decision = scanner.next().charAt(0);
                        if (decision == 'y' || decision == 'Y') {
                            registerNewUser(scanner, loginDAO);
                        } else {
                            System.out.println("You chose to exit.");
                        }
                    }
                    break;
                }
                case 2: {
                    System.out.println("Enter Admin ID:");
                    String adminid = scanner.next();
                    System.out.println("Enter Admin password:");
                    String password = scanner.next();
                    Login admin = loginDAO.fetchAdminCredentials(adminid);

                    if (admin != null) {
                        if (adminid.equals(admin.getAdminid()) && password.equals(admin.getAdminpassword())) {
                            System.out.println("Login successful!");
                            System.out.println("Redirecting to Student Management System...");
                            try {
                                AdminView.main(null); // Call AdminView
                            } catch (Exception e) {
                                System.out.println("An error occurred while redirecting: " + e.getMessage());
                            }
                        } else {
                            System.out.println("Invalid credentials. Contact administration.");
                        }
                    } else {
                        System.out.println("Admin credentials not found.");
                    }
                    break;
                }
                case 3: {
                    registerNewUser(scanner, loginDAO);
                    break;
                }
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        } while (choice != 3);
          
    }

    private static void registerNewUser(Scanner scanner, LoginDAO loginDAO) {
        System.out.println("Enter preferred user ID:");
        String userid = scanner.next();
        System.out.println("Enter preferred password:");
        String userpassword = scanner.next();
        if (loginDAO.userExists(userid)) {
            System.out.println("User already exists. Please choose another User ID.");
        } else {
            Login newUser = new Login();
            newUser.setUserid(userid);
            newUser.setUserpassword(userpassword);
            boolean registered = loginDAO.registerUser(newUser);
            if (registered) {
                System.out.println("User registered successfully with User ID: " + newUser.getUserid());
            } else {
                System.out.println("Registration failed. Please try again.");
            }
        }
    }
}

