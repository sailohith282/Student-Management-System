package edu.jsp.view;

import java.util.Scanner;
import edu.jsp.dao.StudentDAO;
import edu.jsp.dto.*;

public class StudentView {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		/*
		 * By assigning args[0] to userid and args[1] to password, you are making these
		 * values available for use within the StudentView class.
		 */
		String userid = args[0];//
		String password = args[1];
		Login user = new Login();
		user.setUserid(userid);
		user.setUserpassword(password);

		int choice;
		while(true) {
			System.out.println("Student homepage");
			System.out.println("1. Save student");
			System.out.println("2. Delete student");
			System.out.println("3. Update student");
			System.out.println("4. Fetch student");
			System.out.println("5. Logout");
			System.out.println("Select the choice:");
			choice = scanner.nextInt();
			StudentDAO dao = new StudentDAO();
			switch (choice) {
			case 1: {
				System.out.println("Enter student Name:");
				String name = scanner.next();
				System.out.println("Enter student Email:");
				String email = scanner.next();
				System.out.println("Enter student Phno:");
				long phno = scanner.nextLong();
				System.out.println("Enter student branch:");
				String branch = scanner.next();

				Student student = new Student();

				student.setId(user.getUserid());
				student.setPassword(user.getUserpassword());
				student.setName(name);
				student.setEmail(email);
				student.setPhno(phno);
				student.setBranch(branch);

				boolean res = dao.saveStudent(student);
				System.out.println(res ? "Student details saved" : "Student details unable to save");
				break;
			}
			case 2: {
				String id = user.getUserid();
				Student student = new Student();
				student.setId(id);
				boolean res = dao.deleteStudent(user, student);
				System.out.println(res ? "Student details deleted" : "Student details unable to delete");
				break;
			}
			case 3: {
				System.out.println("Enter student Id:");
				String stuid = scanner.next();
				System.out.println("Enter new student Password:");
				String stuPassword = scanner.next();
				System.out.println("Enter new student Name:");
				String name = scanner.next();
				System.out.println("Enter new student branch:");
				String branch = scanner.next();

				Student student = new Student();

				student.setId(stuid);
				student.setPassword(stuPassword);
				student.setName(name);
				student.setBranch(branch);

				boolean res = dao.updateStudent(user, student);
				System.out.println(res ? "Student details updated" : "Student details unable to update");
				break;
			}
			case 4: {
				String id = user.getUserid();
				Student student = dao.fetchUser(userid, id);
				if (student != null) {
					System.out.println("Student Id: " + student.getId());
					System.out.println("Student Password: " + student.getPassword());
					System.out.println("Student Name: " + student.getName());
					System.out.println("Student Email: " + student.getEmail());
					System.out.println("Student Phno: " + student.getPhno());
					System.out.println("Student Password: " + student.getBranch());
				} else {
					System.out.println("Student not found");
				}
				break;
			}
			case 5:
				System.out.println("Logout successful..");
				break;
			default:
				System.out.println("Invalid choice, please try again.");
				break;
			}
			scanner.close();
		}
		
	}
}
