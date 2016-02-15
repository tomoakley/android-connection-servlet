package ubiserv.simple.tom;

import javax.sql.*;
import java.sql.*;

public class Login {
  
  String email; 

  Login(String email) {
    this.email = email;
  }

  public static boolean checkLogin() throws Exception {
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

}
