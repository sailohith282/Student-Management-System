package edu.jsp.dao;

import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;
import edu.jsp.dto.*;
//import edu.jsp.dao.*;

public class StudentDAO {
	public static void prop(Properties p) throws Exception {
		FileInputStream fis = new FileInputStream("sms.properties");
		p.load(fis); // Load properties from the file
	}

	public static Connection connect() throws Exception {
		Class.forName("org.postgresql.Driver");
		Properties p = new Properties();
		prop(p);
		Connection con = DriverManager.getConnection(p.getProperty("url"), p);
		System.out.println("Connection established successfully");
		return con;
	}

	public boolean saveStudent(Student student) {
		try {
			Properties p = new Properties();
			prop(p);
			Connection con = connect();
			PreparedStatement pstmt = con.prepareStatement(p.getProperty("userssavequery"));
			pstmt.setString(1, student.getId());
			pstmt.setString(2, student.getPassword());
			pstmt.setString(3, student.getName());
			pstmt.setString(4, student.getEmail());
			pstmt.setLong(5, student.getPhno());
			pstmt.setString(6, student.getBranch());
			int res = pstmt.executeUpdate();
			pstmt.close();
			con.close();
			return res > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteStudent(Login user, Student student) {
		try {
			Properties p = new Properties();
			prop(p);
			Connection con = connect();
			PreparedStatement pstmt1 = con.prepareStatement(p.getProperty("userdeletequery1"));
			PreparedStatement pstmt2 = con.prepareStatement(p.getProperty("userdeletequery2"));
			pstmt1.setString(1, student.getId());
			pstmt2.setString(2, user.getUserid());
			int res1 = pstmt1.executeUpdate();
			int res2 = pstmt2.executeUpdate();
			pstmt1.close();
			pstmt2.close();
			con.close();
			return res1 > 0 && res2 > 0;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateStudent(Login user, Student student) {
		try {
			Properties p = new Properties();
			prop(p);
			Connection con = connect();
			LoginDAO loginDAO = new LoginDAO();

			// Check if the new userid already exists in user table
	        if (loginDAO.userExists(student.getId())) {
	            System.out.println("User ID already exists. Update aborted.");
	            return false;
	        }else {
			// Update student table
			PreparedStatement pstmt = con.prepareStatement(p.getProperty("userupdatequery1"));
			pstmt.setString(1, student.getName());
			pstmt.setString(2, student.getPassword());
			pstmt.setString(3, student.getBranch());
			pstmt.setString(4, student.getId());
			pstmt.setString(5, user.getUserid());
			int res = pstmt.executeUpdate();

			// Update users table
			PreparedStatement pstmt1 = con.prepareStatement(p.getProperty("userupdatequery2"));
			pstmt1.setString(1, student.getId());
			pstmt1.setString(2, student.getPassword());
			pstmt1.setString(3, user.getUserid());
			int res1 = pstmt1.executeUpdate();

			pstmt.close();
			con.close();
			return res > 0 && res1 > 0;
	        }
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public Student fetchUser(String userid, String studentId) {
		try {
			Properties p = new Properties();
			prop(p);
			Connection con = connect();
			PreparedStatement pstmt = con.prepareStatement(p.getProperty("userfetchquery"));
			/*Here the method works if both userid and id is same (Since the user should able to see only his detail not other)*/
			pstmt.setString(1, studentId);
			pstmt.setString(2, userid);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				Student student = new Student();
				//Login user = new Login();
				student.setId(rs.getString("id"));
				student.setName(rs.getString("name"));
				student.setPassword(rs.getString("password"));
				student.setEmail(rs.getString("email"));
				student.setPhno(rs.getLong("phno"));
				rs.close();
				pstmt.close();
				con.close();
				return student;
			} else {
				rs.close();
				pstmt.close();
				con.close();
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
