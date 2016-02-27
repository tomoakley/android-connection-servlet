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

public class Plaque extends HttpServlet {

  public JSONObject favourite(String action, int plaqueId, int userId) {
      JSONObject response = new JSONObject();
      boolean result = false;
      try {
        PlaqueAction plaqueAction = new PlaqueAction(plaqueId, userId);
        switch (action) {
          case "check":
            result = plaqueAction.checkfavourite();
          case "favourite": case "unfavourite":
            result = plaqueAction.favourite(action);
            break;
          default:
            break;
        }
        response.put("status", result);
      } catch (Exception e) {
        e.printStackTrace();
      }
      return response;
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("application/json");
    PrintWriter out = response.getWriter();
    JSONObject error = new JSONObject(); 
    String jResponse = null;
    String action = "";
    try {
      action = request.getParameter("action").toString();
      int plaqueId = Integer.parseInt(request.getParameter("plaqueid"));
      int userId = Integer.parseInt(request.getParameter("userid"));
      jResponse = favourite(action, plaqueId, userId).toString();
    } catch (Exception e) {
      e.printStackTrace();
      try {
        if (!Utility.isNotNull(action)) {
          error = Utility.addToObject(error, "error", "action not specified");
        } else {
          error = Utility.addToObject(error, "error", "unknown error");
        }
        jResponse = error.toString();
      } catch (JSONException json) {
        json.printStackTrace();
      }
    }
    out.write(jResponse);
    out.close();
  }
    
}
