package dataBaseOperation;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

class DatabaseAuthorization {
	public static String USER_NAME = "";
	public static String PASSWORD = "";
}

@WebServlet("/DatabaseQuery")
public class DatabaseQuery extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//dynamically load the class
			Class.forName("com.mysql.jdbc.Driver");
			//uses the driver just allocated

			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/StudentGrades?user=" 
			+ DatabaseAuthorization.USER_NAME + 
			"&password=" + DatabaseAuthorization.PASSWORD + "&useSSL=false");
			
			
			//Statement st = conn.createStatement();
			String firstName = request.getParameter("fname");
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Student WHERE fname=?");
			ps.setString(1, firstName);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) { //iterate row by row
				int studentID = rs.getInt("studentID");
				String fname = rs.getString("fname");
				String lname = rs.getString("lname");
			}
			
			rs.close();
			ps.close();
			conn.close();  
		}catch(SQLException sqle) {
			System.out.println("sql exception:" + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe exception:" + cnfe.getMessage());
		}
	}
}
