package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SignupValidation
 */
@WebServlet("/SignupValidation")
public class SignupValidation extends HttpServlet {
	public static final long serialVersionUID = 1;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    Connection conn = null;
	    Statement st = null;
	    ResultSet rs = null;
	    String userInputName = request.getParameter("username");
	    String userInputPw = request.getParameter("password");
	    boolean isSignedUp = false; 
	    try {
	    	Class.forName("com.mysql.jdbc.Driver");
	        conn = DriverManager.getConnection("jdbc:mysql://localhost/roomreservationdatabase?user=root&password=oiwamasami580920&useSSL=false");
	        st = conn.createStatement();
	        String query = "select* from user";
	        rs = st.executeQuery(query);
	        ArrayList<String> usernames = new ArrayList<String>();
	        ArrayList<String> passwords = new ArrayList<String>();
	       // ArrayList<Integer> studentIDs = new ArrayList<Integer>();
	        while (rs.next()) {
	        	String username = rs.getString("username");
	        	String password = rs.getString("password");
	        	//int studentID = rs.getInt("studentID");
	        	if(username.equals(userInputName))
	        	{
	        		request.setAttribute("usernameTaken", "Username has already been taken");
	        		isSignedUp = true;
	        	}
	        	System.out.println ("username = " + username);
	        	System.out.println ("password = " + password);
	        	//System.out.println ("studentID = " + studentID);
	        	usernames.add(username);
	        	passwords.add(password);
	        	
	        }
	        if(isSignedUp == false)
	        {
	        	String InsertStatement = "INSERT INTO roomreservationdatabase"+"(username,password)"+
	        			"VALUES"+"("+userInputName+","+userInputPw+")";
	        }
	       // System.out .println("something");
	        //request.setAttribute("fnames", fnames);
	        //request.setAttribute("lnames", lnames);
	        ///request.setAttribute("studentIDs", studentIDs);
	    } catch (SQLException sqle) {
	    	System.out.println (sqle.getMessage());
	    } catch (ClassNotFoundException cnfe) {
	    	System.out.println (cnfe.getMessage());
	    } finally {
	    	try {
	    		if (rs != null) {
	    			rs.close();
	    		}
	    		if (st != null) {
	    			st.close();
	    		}
	    		if (conn != null) {
	    			conn.close();
	    		}
	    	} catch (SQLException sqle) {
	    		System.out.println(sqle.getMessage());
	    	}
	    }  
	    
	    
	    if(isSignedUp == false)
	    {	
	    	String next = "SuccessPage.jsp";
	    }
	    else {
	    	String nextJSP = "SignUp.jsp";	
	    	 
	    }
	    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
		dispatcher.forward(request,response);
	 
	    
	    
		
	}
}
