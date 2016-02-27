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
import org.json.JSONException;
import java.util.HashMap;

public class User extends HttpServlet {

  public JSONObject checkEmail(String email) {
      JSONObject response = new JSONObject();
      boolean result = false;
      // response.put("tag", "login");
      if (Utility.isNotNull(email)) {
          try {
              Login login = new Login();
              result = login.checkEmail(email);
              response.put("status", result);
          } catch (Exception e) {
              e.printStackTrace();
          }
      }
      return response;
  }

  public JSONObject doLogin(String email, String password) {
    JSONObject response = new JSONObject();
    int userID = 0;
    if (Utility.isNotNull(email)) {
      try {
        Login login = new Login();
        userID = login.checkMatch(email, password);
        response.put("userid", userID);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return response;
  }

  public JSONObject doRegister(String fName, String lName, String email) {
    JSONObject response = new JSONObject();
    int userId = 0;
    // response.put("tag", "login");
    if (Utility.isNotNull(email) && Utility.isNotNull(fName) && Utility.isNotNull(lName)) {
        try {
            Register register = new Register(fName, lName, email);
            userId = register.createUser();
            response.put("userid", userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    return response;
  }

  public JSONObject getDetails(int userId, String[] parameters) throws Exception {
    JSONObject response;
    try {
      Login user = new Login();
      HashMap<String, String> details = user.getDetails(userId, parameters);
      response = new JSONObject(details);
    } catch (Exception e) {
      e.printStackTrace();
      response = new JSONObject();
      response.put("error", "an error occurred");
    }
    return response;
  }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	response.setContentType("application/json");
    	PrintWriter out = response.getWriter();
        JSONObject error = new JSONObject(); 
        String jResponse = null;
        try {
          String action = request.getParameter("action").toString();
          String email = request.getParameter("email").toString();
          switch (action) {
            case "checkemail":
              jResponse = checkEmail(email).toString();
              break;
            case "login":
              jResponse = doLogin(email, "notset").toString();
              break;
            case "register":
              String firstName = request.getParameter("firstName").toString();
              String lastName = request.getParameter("lastName").toString();
              jResponse = doRegister(email, firstName, lastName).toString();
              break;
            case "getdetails":
              String[] params = request.getParameterValues("params");
              int userId = Integer.parseInt(request.getParameter("userid"));
              jResponse = getDetails(userId, params).toString();
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
