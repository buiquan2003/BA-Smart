package Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionManager {
	    private static final String URL = "jdbc:mysql://localhost:3306/smartlunch";
	    private static final String USERNAME = "root";
	    private static final String PASSWORD = "quanbui2003";

	    static {
	        try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	            throw new IllegalStateException("Cannot find the driver in the classpath!", e);
	        }
	    }

	    public static Connection getConnection() throws SQLException {
	        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
	    }
}
