package edu.jsp.view;

import java.util.Scanner;
import edu.jsp.dao.*;
import edu.jsp.dto.*;
import java.util.List;

public class AdminView {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int choice;

		AdminDAO adminDAO = new AdminDAO();
		StudentDAO studentDAO = new StudentDAO();

		while (true) {
			System.out.println("Admin homepage");
			System.out.println("0. Create Table");
			System.out.println("1. Save student");
			System.out.println("2. Delete student");
			System.out.println("3. Update student phno");
			System.out.println("4. Update student email");
			System.out.println("5. Fetch student");
			System.out.println("6. Fetch all students");
			System.out.println("7. Logout");
			System.out.println("Select the choice:");
			choice = scanner.nextInt();

			switch (choice) {
			case 0:
				adminDAO.createTable();
				break;
			case 1: {
				System.out.println("Enter student Id:");
				String id = scanner.next();
				System.out.println("Enter student Password:");
				String password = scanner.next();
				System.out.println("Enter student Name:");
				String name = scanner.next();
				System.out.println("Enter student Email:");
				String email = scanner.next();
				System.out.println("Enter student Phno:");
				long phno = scanner.nextLong();
				Student student = new Student();
				student.setId(id);
				student.setPassword(password);
				student.setName(name);
				student.setEmail(email);
				student.setPhno(phno);
				Login user = new Login();
				user.setUserid(id);
				user.setUserpassword(password);
				boolean res = studentDAO.saveStudent(student);
				boolean res1 = adminDAO.saveStudent(user);
				System.out.println(res && res1 ? "Student details saved" : "Student details unable to save");
				break;
			}
			case 2: {
				System.out.println("Enter user id to delete");
				String id = scanner.next();
				Student student = new Student();
				student.setId(id);
				Login user = new Login();
				user.setUserid(id);
				boolean res = studentDAO.deleteStudent(user, student);
				System.out.println(res ? "Student details deleted" : "Student details unable to delete");
				break;
			}
			case 3: {
				System.out.println("Enter student Id:");
				String id = scanner.next();
				System.out.println("Enter student Phno:");
				long phno = scanner.nextLong();
				Student student = new Student();
				student.setId(id);
				student.setPhno(phno);
				boolean res = adminDAO.updateStudentPhno(student);
				System.out.println(res ? "Student Phno updated" : "Unable to update student Phno");
				break;
			}
			case 4: {
				System.out.println("Enter student Id:");
				String id = scanner.next();
				System.out.println("Enter new student Email:");
				String email = scanner.next();
				Student student = new Student();
				student.setId(id);
				student.setEmail(email);
				boolean res = adminDAO.updateStudentEmail(student);
				System.out.println(res ? "Student Email updated" : "Unable to update student Email");
				break;
			}
			case 5: {
				System.out.println("Enter student Id:");
				String id = scanner.next();
				Student student = adminDAO.fetchStudent(id); // Updated method call
				if (student != null) {
					System.out.println("Student Id : " + student.getId());
					System.out.println("Student Name : " + student.getName());
					System.out.println("Student Email : " + student.getEmail());
					System.out.println("Student Phno : " + student.getPhno());
					System.out.println("Student Branch : " + student.getBranch());
				} else {
					System.out.println("Student not found");
				}
				break;
			}

			case 6: {
				List<Student> students = adminDAO.fetchAllStudents();
				if (students != null && !students.isEmpty()) {
					for (Student s : students) {
						System.out.println("Student Id : " + s.getId());
						System.out.println("Student Name : " + s.getName());
						System.out.println("Student Email : " + s.getEmail());
						System.out.println("Student Phno : " + s.getPhno());
						System.out.println("Student Branch : " + s.getBranch());
						System.out.println("------------");
					}
				} else {
					System.out.println("No students found");
				}
				break;
			}
			case 7: {
				System.out.println("Logout successful..");
				break;
			}
			default: {
				System.out.println("Invalid choice, please try again.");
				break;
			}
			}
			scanner.close();
		}

	}
}
