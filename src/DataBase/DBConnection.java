package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {

    private final String USERNAME = "root";
    private final String PASSWORD = "";
    private final String DATABASE = "govlashlaundry";
    private final String HOST = "localhost:3306";
    private final String CONNECTION = String.format("jdbc:mysql://%s/%s", HOST, DATABASE);

    private Connection con;
    private Statement st;

    private static DBConnection instance;

    //Private constructor (Singleton)
    private DBConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
            st = con.createStatement();
            System.out.println("Connected!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Connection Failed!");
        }
    }

    //Get instance (Singleton)
    public static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }
    
    public Connection getConnection() {
        return con;
    }

    //Select
    public ResultSet executeQuery(String query) {
        try {
            return st.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    //INSERT, UPDATE, DELETE
    public void executeUpdate(String query) {
        try {
            st.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //PreparedStatement (lebih aman)
    public PreparedStatement prepareStatement(String query) {
        try {
            return con.prepareStatement(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    

    public Statement getStatement() throws SQLException {
		
		return con.createStatement();
	}
}
