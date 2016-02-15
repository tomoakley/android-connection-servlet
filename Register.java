package ubiserv.simple.tom;

import java.sql.*;
import javax.sql.*;

public class Register {

  String email;
  String fName;
  String lName;

  Register(String email, String fName, String lName) {
    this.email = email;
    this.fName = fName;
    this.lName = lName;
  }

  public int createUser() throws Exception {
    DBConnection dbConnection = null;
    Connection con = null;
    int id = 0;
    try {
        dbConnection = new DBConnection(Constants.DB_CLASS, Constants.DB_URL, Constants.DB_USER, Constants.DB_PASSWORD);
        con = dbConnection.createConnection();
        Statement statement = con.createStatement();
        statement.executeUpdate("INSERT INTO users (email, firstName, lastName) VALUES ('" + email + "', '" + fName + "', '" + lName + "')");
        ResultSet results = statement.executeQuery("SELECT id FROM users WHERE email='" + email + "'");
        while (results.next()) {
          id = results.getInt(1); 
        }
    } catch (Exception e) {
        e.printStackTrace(); 
        con.close();
    } finally {
        if (con != null) {
            con.close();
        }
    }
    return id;
  }
}
