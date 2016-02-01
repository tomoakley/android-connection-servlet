package ubiserv.simple.tom;

import java.sql.*;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import com.mysql.jdbc.jdbc2.optional.*;
import javax.sql.*;
import javax.naming.*;
import java.util.*;

public class DBConnection {
    
    public static Connection createConnection() throws Exception {
	Connection con;
	try {
	    Class.forName(Constants.DB_CLASS); 
	    con = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USER, Constants.DB_PASSWORD);
        } catch (Exception e) {
	    e.printStackTrace();
            throw e;
        }
	return con;
    }
    
    public static boolean checkLogin(String email) throws Exception {
        boolean userExists = false;
        Connection dbConnection = null;
        try {
            dbConnection = createConnection();
            Statement statement = dbConnection.createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM users WHERE email ='" + email + "'");
            while (results.next()) {
                userExists = true;
            }
        } catch (Exception e) {
	    e.printStackTrace(); 
	    dbConnection.close();
        } finally {
            if (dbConnection != null) {
                dbConnection.close();
            }
        }
        return userExists;
    }
}
