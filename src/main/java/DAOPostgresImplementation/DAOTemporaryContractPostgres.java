package DAOPostgresImplementation;

import DAO.DAOTemporaryContract;
import Database.PostgresDBConnection;
import com.sun.scenario.effect.impl.sw.java.JSWLinearConvolvePeer;

import java.sql.*;
import java.util.ArrayList;

public class DAOTemporaryContractPostgres implements DAOTemporaryContract {

    private Connection connection;

    /**
     * Costruttore della classe. Ottiene il collegamento al database.
     * @throws SQLException
     */
    public DAOTemporaryContractPostgres() throws SQLException {
        connection = PostgresDBConnection.getInstance().getConnection();
    }

    /**
     * Inserisce una nuova tupla Temporary Contract nel database.
     * @param ssn SSN dell'impiegato assunto temporaneamente
     * @param cup CUP del progetto che ha assunto l'impiegato.
     */
    public void addTemporaryContractDB(String ssn, String cup){
        String query = "INSERT INTO azienda.temporary_contract " +
                "VALUES (?,?);";

        try{
            PreparedStatement prst = connection.prepareStatement(query);
            prst.setString(1, ssn);
            prst.setString(2, cup);
            prst.executeUpdate();
            prst.close();
        }
        catch (SQLException ex) {
            System.out.println("Insert failed: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Metodo che inserisce nelle liste passate per riferimento i dati di tutte le tuple della tabella Temporary Contract.
     * @param ssnList Lista di SSN.
     * @param cupList Lista dei CUP.
     */
    public void loadTemporaryContractDB(ArrayList<String> ssnList, ArrayList<String> cupList){
        try{
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM azienda.temporary_contract;");

            while (rs.next()) {
                ssnList.add(rs.getString("ssn"));
                cupList.add(rs.getString("cup"));
            }
            rs.close();
            st.close();
        }
        catch (SQLException ex) {
            System.out.println("Load failed: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Metodo che cerca una tupla con l'ssn passato e la elimina.
     * @param ssn
     */
    public void removeTemporaryContractDB(String ssn){
        try {
            PreparedStatement prst = connection.prepareStatement("DELETE FROM azienda.temporary_contract WHERE ssn LIKE ?;");
            prst.setString(1, ssn);
            prst.executeUpdate();
            prst.close();
        }
        catch (SQLException ex){
            System.out.println("Delete failed: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
