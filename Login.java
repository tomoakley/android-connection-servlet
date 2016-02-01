package ubiserv.simple.tom;

import java.io.IOException; 
import java.io.PrintWriter; 
import javax.servlet.ServletException; 
import javax.servlet.http.HttpServlet; 
import javax.servlet.http.HttpServletRequest; 
import javax.servlet.http.HttpServletResponse;
import javax.json.*;
import org.json.JSONObject;

public class Login extends HttpServlet {

    public JSONObject doLogin(String email) {
	JSONObject response = new JSONObject();
        boolean result = false;
	response.put("tag", "login");
        if (email != "") {
            try {
                result = DBConnection.checkLogin(email);
		response.put("status", result);
            } catch (Exception e) {
		e.printStackTrace();
            }
        }
        return response;
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
