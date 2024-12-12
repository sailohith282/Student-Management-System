package edu.jsp.dao;

import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;
import edu.jsp.dto.Login;

public class LoginDAO {

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

	public Login fetchUserCredentials(String userid) {
		/* this method is used to check login credentials of users from D.B */
		try {
			Properties p = new Properties();
			prop(p);
			Connection con = connect();
			PreparedStatement pstmt = con.prepareStatement(p.getProperty("fetchUserCredentialsQuery"));
			pstmt.setString(1, userid);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				Login user = new Login();
				user.setUserid(rs.getString("userid"));
				user.setUserpassword(rs.getString("password"));
				rs.close();
				pstmt.close();
				con.close();
				return user;
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

	public boolean userExists(String userid) {
		//this method is used to check whether a userid is already present in the database in order to create a new account
		try {
			Properties p = new Properties();
			prop(p);
			Connection con = connect();
			PreparedStatement pstmt = con.prepareStatement(p.getProperty("userExistsquery"));
			pstmt.setString(1, userid);
			ResultSet rs = pstmt.executeQuery();
			rs.close();
			pstmt.close();
			con.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean registerUser(Login user) {
		try {
			Properties p = new Properties();
			prop(p);
			Connection con = connect();
			PreparedStatement pstmt = con.prepareStatement(p.getProperty("registerUserquery"));
			pstmt.setString(1, user.getUserid());
			pstmt.setString(2, user.getUserpassword());
			int result = pstmt.executeUpdate();
			pstmt.close();
			con.close();
			return result > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// Method to fetch admin credentials
	public Login fetchAdminCredentials(String adminid) {
		// Check login credentials of admin from DB
		Properties p = new Properties();
		try {
			prop(p); // Load properties
			Connection con = connect(); // Establish a connection
			String sqlQuery = p.getProperty("fetchAdminCredentialsQuery");
			// System.out.println("Executing query: " + sqlQuery); // Debug print for SQL
			// query
			PreparedStatement pstmt = con.prepareStatement(sqlQuery);
			pstmt.setString(1, adminid);
			// System.out.println("Admin ID passed: " + adminid); // Debug print for admin
			// ID
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				Login login = new Login(); // Login object for storing DB details
				login.setAdminid(rs.getString(1)); // Retrieve admin ID
				login.setAdminpassword(rs.getString(2)); // Retrieve admin password
				rs.close();
				pstmt.close();
				con.close();
				return login;
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
