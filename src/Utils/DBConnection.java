package Utils;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * Stores Database information and methods.
 *
 *
 */
public class DBConnection {
    //JDBC URL parts
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//wgudb.ucertify.com/WJ08AVA";

    //JDBC URL
    private static final String jdbcURL = protocol + vendorName + ipAddress;

    //Driver and Connection interface reference
    public static Connection conn = null;
    private static final String mySqlJDBCDriver = "com.mysql.jdbc.Driver";

    //Username and password
    private static final String username = "U08AVA";
    private static String password = "53689232650";

    /**
     * Initiates connection with database.
     *
     *
     */
    public static Connection startConnection(){
        try {
            Class.forName(mySqlJDBCDriver);
            conn = (Connection)DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Connection success!");
        }
        catch (ClassNotFoundException | SQLException x) {
            System.out.println(x.getMessage());
        } return conn;
    }
    /**
     * Closes connection with database.
     */
    public static void closeConnection() {
        try {
            conn.close();
            System.out.println("Connection Closed!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}
