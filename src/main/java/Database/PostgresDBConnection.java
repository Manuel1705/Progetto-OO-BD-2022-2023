package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe che effettua la connessione del programma ad un database Postgres
 */
public class PostgresDBConnection {

    private static PostgresDBConnection instance = null;

    private Connection connection = null;
    private String user;
    private String password;
    private String url;
    private String driver = "org.postgresql.Driver";


    /**
     * Costruttore privato della classe. Inizializza il driver di Postgres se viene trovato dal metodo Class.forName
     * e lo collega al DriverManager, che stabilisce la connessione.
     */
    private PostgresDBConnection(){
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
     * Costruttore privato della classe che inizializza le credenziali di accesso al database.
     * Inizializza il driver di Postgres se viene trovato dal metodo Class.forName
     * e lo collega al DriverManager, che stabilisce la connessione.
     */
    private PostgresDBConnection(String username, String newPassword, String database){
        setCredentials(username, newPassword, database);
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
     * Metodo statico che connette la classe al database con le nuove credenziali passate in input.
     *
     * @return instance Istanza della classe PostgresDBConnection
     * @throws SQLException
     */
    public static PostgresDBConnection getInstance(String username, String newPassword, String database) throws SQLException {

        instance = new PostgresDBConnection(username, newPassword, database);
        return instance;
    }

    /**
     * Metodo che restituisce l'oggetto connection
     * @return Connnection
     */
    public Connection getConnection() {
        return connection;
    }

    private void setCredentials(String username, String newPassword, String database){

        user = username;
        password = newPassword;
        url= "jdbc:postgresql://localhost:5432/" + database;

    }
}
