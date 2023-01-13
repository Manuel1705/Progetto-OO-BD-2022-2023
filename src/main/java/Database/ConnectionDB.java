package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {

    // ATTRIBUTI
    private static ConnectionDB instance;
    public Connection connection = null;
    private String nome = "postgres";
    private String password = "password";
    private String url = "jdbc:postgresql://localhost:5432/Progetto2022-2023";
    private String driver = "org.postgresql.Driver";

    // COSTRUTTORE
    private void ConnectionDB() throws SQLException {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, nome, password);

        } catch (ClassNotFoundException ex) {
            System.out.println("Database Connection Creation Failed : " + ex.getMessage());
            ex.printStackTrace();
        }

    }


    public static ConnectionDB getInstance() throws SQLException {
        if (instance == null) {
            instance = new ConnectionDB();
        } else if (instance.connection.isClosed()) {
            instance = new ConnectionDB();
        }
        return instance;
    }
}