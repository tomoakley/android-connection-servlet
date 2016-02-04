package ubiserv.simple.tom;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import com.mysql.jdbc.jdbc2.optional.*;
import javax.sql.*;
import javax.naming.*;
import java.util.*;

public class DBConnection {

    String dbClass, dbUrl, dbUser, dbPassword;
    
    public DBConnection(String dbClass, String dbUrl, String dbUser, String dbPassword) {
        this.dbClass = dbClass;
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    public Connection createConnection() throws Exception {
       Connection con;
	try {
	    Class.forName(dbClass); 
	    con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } catch (Exception e) {
	    e.printStackTrace();
            throw e;
        }
	return con;
    }
    
}
