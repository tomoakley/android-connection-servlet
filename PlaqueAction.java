package ubiserv.simple.tom;

import java.sql.*;
import javax.sql.*;
import java.util.Date;
import java.util.Calendar;

public class PlaqueAction {

  int plaque;
  int user;
  
  public PlaqueAction(int plaque, int user) {
    this.plaque = plaque;
    this.user = user;
  }

  public boolean checkFavourite() throws Exception {
    DBConnection dbConnection = null;
    Connection con = null;
    boolean result = false;
    try {
        dbConnection = new DBConnection(Constants.DB_CLASS, Constants.DB_URL, Constants.DB_USER, Constants.DB_PASSWORD);
        con = dbConnection.createConnection();
        Statement statement = con.createStatement();
        ResultSet results = statement.executeQuery("SELECT id FROM favouritePlaques WHERE userId='" + user + "' AND plaqueId='" + plaque + "'");
        if (results.next()) {
          result = true;
          con.close();
          return result;
        }
      } catch (Exception e) {
        e.printStackTrace(); 
        con.close();
    } finally {
        if (con != null) {
            con.close();
        }
    }
    return result;
  }

  public boolean favourite(String action) throws Exception {
    DBConnection dbConnection = null;
    Connection con = null;
    boolean result = false;
    try {
        dbConnection = new DBConnection(Constants.DB_CLASS, Constants.DB_URL, Constants.DB_USER, Constants.DB_PASSWORD);
        con = dbConnection.createConnection();
        Statement statement = con.createStatement();
        int rowsAffected = 0;
        if (action.equals("favourite")) {
          java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
          rowsAffected = statement.executeUpdate("INSERT INTO favouritePlaques (userId, plaqueId, datetime) VALUES ('" + user + "', '" + plaque + "', '" + currentTimestamp + "')");
        } else if (action.equals("unfavourite")) {
          rowsAffected = statement.executeUpdate("DELETE FROM favouritePlaques WHERE userId='" + user + "' AND plaqueId='" + plaque + "'");
        }
        if (rowsAffected == 1) {
          result = true;
          return result;
        }
    } catch (Exception e) {
        e.printStackTrace(); 
        con.close();
    } finally {
        if (con != null) {
            con.close();
        }
    }
    return result;
  }

}
