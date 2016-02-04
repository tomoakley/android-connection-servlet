package ubiserv.simple.tom;

import java.io.IOException; 
import java.io.PrintWriter; 
import javax.servlet.ServletException; 
import javax.servlet.http.HttpServlet; 
import javax.servlet.http.HttpServletRequest; 
import javax.servlet.http.HttpServletResponse;
import javax.sql.*;
import javax.json.*;
import org.json.JSONObject;

public class Login extends HttpServlet {

    public JSONObject doLogin(String email) {
	JSONObject response = new JSONObject();
        boolean result = false;
	response.put("tag", "login");
        if (email != "") {
            try {
                result = checkLogin(email);
		response.put("status", result);
            } catch (Exception e) {
		e.printStackTrace();
            }
        }
        return response;
    }

    public static boolean checkLogin(String email) throws Exception {
        boolean userExists = false;
        Connection dbConnection = null;
        try {
            dbConnection = new DBConnection(Constants.DB_URL, Constants.DB_USER, Constants.DB_PASSWORD);
            Statement statement = dbConnection.createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM users WHERE email ='" + email + "'");
            while (results.next()) {
                userExists = true;
            }
        } catch (Exception e) {
	    e.printStackTrace(); 
	    dbConnection.close();
        } finally {
            if (dbConnection != null) {
                dbConnection.close();
            }
        }
        return userExists;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	response.setContentType("application/json");
    	PrintWriter out = response.getWriter();
	String email = request.getParameter("email").toString();
	String loginResponse = doLogin(email).toString();
	out.write(loginResponse);
	out.close();
    }
    
}
