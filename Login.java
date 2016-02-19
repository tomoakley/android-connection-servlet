package ubiserv.simple.tom;

import javax.sql.*;
import java.sql.*;

public class Login {
  
  public boolean checkEmail(String email) throws Exception {
    boolean userExists = false;
    DBConnection dbConnection = null;
    Connection con = null;
    try {
        dbConnection = new DBConnection(Constants.DB_CLASS, Constants.DB_URL, Constants.DB_USER, Constants.DB_PASSWORD);
        con = dbConnection.createConnection();
        Statement statement = con.createStatement();
        ResultSet results = statement.executeQuery("SELECT email FROM users WHERE email ='" + email + "'");
        while (results.next()) {
          String resultsEmail = results.getString(1);
          if (resultsEmail.equals(email)) {
            userExists = true;
          }
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

  public int checkMatch(String email, String password) throws Exception {
    int userID = 0;
    DBConnection dbConnection = null;
    Connection con = null;
    try {
        dbConnection = new DBConnection(Constants.DB_CLASS, Constants.DB_URL, Constants.DB_USER, Constants.DB_PASSWORD);
        con = dbConnection.createConnection();
        Statement statement = con.createStatement();
        ResultSet results = statement.executeQuery("SELECT id FROM users WHERE email ='" + email + "'");
        while (results.next()) {
            userID = results.getInt(1);
        }
    } catch (Exception e) {
        e.printStackTrace(); 
        con.close();
    } finally {
        if (con != null) {
            con.close();
        }
    }
    return userID;
  }

}
