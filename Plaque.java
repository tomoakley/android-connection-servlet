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

  public JSONObject favourite(int plaqueId, int userId) {
      JSONObject response = new JSONObject();
      int id = 0;
      // response.put("tag", "login");
      try {
        PlaqueAction plaqueAction = new PlaqueAction(plaqueId, userId);
        id = plaqueAction.favourite();
        response.put("id", id);
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
    try {
      String action = request.getParameter("action").toString();
      int plaqueId = Integer.parseInt(request.getParameter("plaqueid"));
      int userId = Integer.parseInt(request.getParameter("userid"));
      switch (action) {
        case "favourite":
          jResponse = favourite(plaqueId, userId).toString();
          break;
        /* case "visited":
          jResponse = visited(plaqueId, userId).toString();
          break; */
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
