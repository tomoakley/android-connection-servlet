package ubiserv.simple.tom;

import java.io.IOException; 
import java.io.PrintWriter; 
import javax.servlet.ServletException; 
import javax.servlet.http.HttpServlet; 
import javax.servlet.http.HttpServletRequest; 
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import javax.sql.*;
import javax.json.*;
import org.json.*;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import com.mysql.jdbc.jdbc2.optional.*;
import javax.naming.*;
import java.util.*;

public class Login extends HttpServlet {

    public JSONObject doLogin(String email) {
	JSONObject response = new JSONObject();
        boolean result = false;
	// response.put("tag", "login");
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
        DBConnection dbConnection = null;
        Connection con = null;
        try {
            dbConnection = new DBConnection(Constants.DB_CLASS, Constants.DB_URL, Constants.DB_USER, Constants.DB_PASSWORD);
            con = dbConnection.createConnection();
            Statement statement = con.createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM users WHERE email ='" + email + "'");
            while (results.next()) {
                userExists = true;
            }
        } catch (Exception e) {
	    e.printStackTrace(); 
	    con.close();
        } finally {
            if (con != null) {
                con.close();
            }
        }
        return userExists;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	response.setContentType("application/json");
    	PrintWriter out = response.getWriter();
        JSONObject error = new JSONObject(); 
        String jResponse;
        try {
          String action = request.getParameter("action").toString();
          String email = request.getParameter("email").toString();
          switch (action) {
            case "login":
              jResponse = doLogin(email).toString();
              break;
            default:
              error = Utility.addToObject(error, "error", "action not specified"); 
              jResponse = error.toString();
              break;
          }
        } catch (Exception e) {
          e.printStackTrace();
          try {
            error = Utility.addToObject(error, "error", "action not specified");
            jResponse = error.toString();
          } catch (JSONException json) {
            json.printStackTrace();
          }
        }
        out.write(jResponse);
	out.close();
    }
    
}
