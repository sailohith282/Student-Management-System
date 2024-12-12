package edu.jsp.dao;

import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import edu.jsp.dto.Login;
import edu.jsp.dto.Student;

public class AdminDAO {
	public static void prop(Properties p) throws Exception {
		FileInputStream fis = new FileInputStream("sms.properties");
		p.load(fis);
	}

	public static Connection connect() throws Exception {
		Class.forName("org.postgresql.Driver");
		Properties p = new Properties();
		prop(p);
		Connection con = DriverManager.getConnection(p.getProperty("url"), p);
		System.out.println("Connection established successfully");
		return con;
	}

	public void createTable() {
		Properties p = new Properties();
		try {
			Class.forName("org.postgresql.Driver");
			System.out.println("Class Loaded Successfully");
			prop(p);
			Connection con = connect();
			Statement stmt = con.createStatement();
			String[] tables = p.getProperty("tables").split(",");
			for (String table : tables) {
				String createTableDB = p.getProperty(table + ".create");
				stmt.executeUpdate(createTableDB);
				System.out.println("Table " + table + " Created Successfully");
			}
			stmt.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean saveStudent(Login user) {
		try {
			Properties p = new Properties();
			prop(p);
			Connection con = connect();
			PreparedStatement pstmt = con.prepareStatement(p.getProperty("adminsavequery"));
			pstmt.setString(1, user.getUserid());
			pstmt.setString(2, user.getUserpassword());
			int res1 = pstmt.executeUpdate();
			pstmt.close();
			con.close();
			return res1 > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	//delete method is fetch from studentDAO
	
	public boolean updateStudentPhno(Student student) {
		try {
			Properties p = new Properties();
			prop(p);
			Connection con = connect();
			PreparedStatement pstmt = con.prepareStatement(p.getProperty("adminupdatephno"));
			pstmt.setLong(1, student.getPhno());
			pstmt.setString(2, student.getId());
			int res = pstmt.executeUpdate();
			pstmt.close();
			con.close();
			return res > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateStudentEmail(Student student) {
		try {
			Properties p = new Properties();
			prop(p);
			Connection con = connect();
			PreparedStatement pstmt = con.prepareStatement(p.getProperty("adminupdatemail"));
			pstmt.setString(1, student.getEmail());
			pstmt.setString(2, student.getId());
			int res = pstmt.executeUpdate();
			pstmt.close();
			con.close();
			return res > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public Student fetchStudent(String id) {
	    try {
	        Properties p = new Properties();
	        prop(p);
	        Connection con = connect();
	        PreparedStatement pstmt = con.prepareStatement(p.getProperty("adminFetchStudentQuery"));
	        pstmt.setString(1, id);
	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next()) {
	            Student student = new Student();
	            student.setId(rs.getString("id"));
	            student.setName(rs.getString("name"));
	            student.setEmail(rs.getString("email"));
	            student.setPhno(rs.getLong("phno"));
	            student.setBranch(rs.getString("branch"));
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

	
	public List<Student> fetchAllStudents() {
		List<Student> students = new ArrayList<>();
		try {
			Properties p = new Properties();
			prop(p);
			Connection con = connect();
			PreparedStatement pstmt = con.prepareStatement(p.getProperty("adminFetchAllUsersQuery"));
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Student student = new Student();
				student.setId(rs.getString("id"));
				student.setName(rs.getString("name"));
				student.setEmail(rs.getString("email"));
				student.setPhno(rs.getLong("phno"));
				student.setBranch(rs.getString("branch"));
				students.add(student);
			}
			rs.close();
			pstmt.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return students;
	}

	

	
}
