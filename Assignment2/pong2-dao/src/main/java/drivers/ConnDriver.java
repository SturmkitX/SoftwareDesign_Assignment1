package drivers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnDriver {
    private static Connection connection = null;

    private ConnDriver() {
        // created in order to overwrite the public constructor
    }

    public static Connection getInstance() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");		// may need .newInstance()
//				connection = DriverManager.getConnection("jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11229932",
//				"sql11229932", "p7AqBeesmh");

                connection = DriverManager.getConnection("jdbc:mysql://localhost/pong2?" +
                        "user=ponguser&password=ponguser");
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                System.out.println("SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
            }
        }

        return connection;
    }
}