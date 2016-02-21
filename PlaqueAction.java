package ubiserv.simple.tom;

import java.sql.*;
import javax.sql.*;

public class PlaqueAction {

  int plaque;
  int user;
  
  public PlaqueAction(int plaque, int user) {
    this.plaque = plaque;
    this.user = user;
  }

  public int favourite() {
    DBConnection dbConnection = null;
    Connection con = null;
    int id = 0;
    try {
        dbConnection = new DBConnection(Constants.DB_CLASS, Constants.DB_URL, Constants.DB_USER, Constants.DB_PASSWORD);
        con = dbConnection.createConnection();
        Statement statement = con.createStatement();
        statement.executeUpdate("INSERT INTO favouritePlaques (userId, plaqueId) VALUES ('". user ."', '". plaque ."')");
        ResultSet result = statement.executeQuery("SELECT id WHERE userId='". user ."' AND plaqueId='". plaque ."'");
        while (result.next()) {
          id = result.getInt(1);
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
