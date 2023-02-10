package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe che effettua la connessione del programma ad un database Postgres
 */
public class PostgresDBConnection {

    private static PostgresDBConnection instance;

    private Connection connection = null;
    private String user = "postgres";
    private String password = "password";
    private String url = "jdbc:postgresql://localhost:5432/Progetto2022-2023";
    private String driver = "org.postgresql.Driver";


    /**
     * Costruttore privato della classe. Inizializza il driver di Postgres se viene trovato dal metodo Class.forName
     * e lo collega al DriverManager, che stabilisce la connessione.
     * @throws SQLException
     */
    private void PostgresDBConnection() throws SQLException {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);

        } catch (ClassNotFoundException ex) {
            System.out.println("Database Driver not found: " + ex.getMessage());
            ex.printStackTrace();
        }
         catch (SQLException ex) {
             System.out.println("Database connection failed: " + ex.getMessage());
             ex.printStackTrace();
         }
    }

    /**
     * Metodo statico che implementa il pattern Singleton. Permette al metodo chiamante di
     * ottenere l'istanza della classe PostgresDBConnection se essa e' gia' stata creata,
     * altrimenti la crea usando il metodo costruttore privato.
     *
     * @return instance Istanza della classe PostgresDBConnection
     * @throws SQLException
     */
    public static PostgresDBConnection getInstance() throws SQLException {
        if (instance == null) {
            instance = new PostgresDBConnection();
        } else if (instance.connection.isClosed()) {
            instance = new PostgresDBConnection();
        }
        return instance;
    }

    /**
     * Metodo che restituisce l'oggetto connection
     * Metodo che restituisce l'oggetto connection
     * @return
     */
    public Connection getConnection() {
        return connection;
    }
}

