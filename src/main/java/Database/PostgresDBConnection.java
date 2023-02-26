package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Classe che effettua la connessione del programma a un database Postgres
 */
public class PostgresDBConnection {

    private static PostgresDBConnection instance = null;

    private Connection connection = null;
    private String user;
    private String password;
    private String url;
    private String driver = "org.postgresql.Driver";

    private ArrayList<String> errors = new ArrayList<String>();


    /**
     * Costruttore privato della classe. Inizializza il driver di Postgres se viene trovato dal metodo Class.forName
     * e lo collega al DriverManager, che stabilisce la connessione.
     */
    private PostgresDBConnection(){
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);

        } catch (ClassNotFoundException ex) {
            errors.add("Database Driver not found: " + ex.getMessage());

        }
         catch (SQLException ex) {
             errors.add("Database connection failed: " + ex.getMessage());

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
            errors.add("Database Driver not found: " + ex.getMessage());

        }
        catch (SQLException ex) {
            errors.add("Database connection failed: " + ex.getMessage());

        }
    }

    /**
     * Metodo statico che implementa il pattern Singleton. Permette al metodo chiamante di
     * ottenere l'istanza della classe PostgresDBConnection se essa è già stata creata,
     * altrimenti la crea usando il metodo costruttore privato.
     *
     * @return instance Istanza della classe PostgresDBConnection
     * @throws SQLException
     */
    public static PostgresDBConnection getInstance() throws SQLException{

        if (instance == null) {
            instance = new PostgresDBConnection();
        }
        else if (instance.connection.isClosed()) {
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
    public static PostgresDBConnection getInstance(String username, String newPassword, String database){

        instance = new PostgresDBConnection(username, newPassword, database);
        return instance;
    }

    /**
     * Metodo che restituisce l'oggetto connection
     * @return Connection
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Metodo che setta le credenziali del database.
     * @param username
     * @param newPassword
     * @param database
     */

    private void setCredentials(String username, String newPassword, String database){

        user = username;
        password = newPassword;
        url= "jdbc:postgresql://localhost:5432/" + database;

    }

    /**
     * Metodo che restituisce errors.
     * @return errors.
     */
    public ArrayList<String> getErrors(){return errors;}
}

