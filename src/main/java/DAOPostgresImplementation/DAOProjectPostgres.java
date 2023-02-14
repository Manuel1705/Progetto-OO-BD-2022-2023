package DAOPostgresImplementation;

import DAO.DAOProject;
import Database.PostgresDBConnection;

import java.sql.*;
import java.util.ArrayList;

public class DAOProjectPostgres implements DAOProject {

    private Connection connection;

    /**
     * Costruttore della classe. Ottiene il collegamento al database.
     * @throws SQLException
     */
    public DAOProjectPostgres() throws SQLException {
        connection = PostgresDBConnection.getInstance().getConnection();
    }


    /**
     * Metodo che inserisce una nuova tupla nella tabella Project
     * @param cup Codice Unico Progetto del progetto. Chiave primaria.
     * @param name Nome del progetto.
     * @param budget Budget del progetto.
     * @param remainingFunds Fondi rimanenti del progetto.
     * @param startDate Data in cui e' iniziato il progetto.
     * @param endDate Data per cui e' prevista la fine del progetto.
     * @param sResp Responsabile scientifico del progetto.
     * @param sRef Referente scientifico del progetto.
     */
    public void addProjectDB(String cup, String name, float budget, float remainingFunds, Date startDate, Date endDate,
                             String sResp, String sRef){
        String query = "INSERT INTO azienda.project" +
                "VALUES (?,?,?,?,?,?,?,?);";
        try{
            PreparedStatement prst = connection.prepareStatement(query);
            prst.setString(1, cup);
            prst.setString(2, name);
            prst.setFloat(3, budget);
            prst.setFloat(4, remainingFunds);
            prst.setDate(5, startDate);
            prst.setDate(6, endDate);
            prst.setString(7, sResp);
            prst.setString(8, sRef);
            prst.executeUpdate();
            prst.close();
        }
        catch (SQLException ex) {
            System.out.println("Insert failed: " + ex.getMessage());
            ex.printStackTrace();
        }

    }

    /**
     * Metodo che rimuove una tupla dalla tabella Project, individuandola usando la sua chiave primaria.
     * @param cup Chiave primaria.
     */
    public void removeProjectDB(String cup){
        try {
            PreparedStatement prst = connection.prepareStatement("DELETE FROM azienda.project WHERE cup LIKE ?;");
            prst.setString(1, cup);
            prst.executeUpdate();
            prst.close();
        }
        catch (SQLException ex){
            System.out.println("Delete failed: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Metodo che aggiorna la tupla Project con chiave primaria uguale a oldCup
     * @param oldCup Chiave primaria originale.
     * @param newCup Nuovo CUP del progetto.
     * @param name Nome del progetto.
     * @param budget Budget del progetto.
     * @param remainingFunds Fondi rimanenti del progetto.
     * @param startDate Data in cui e' iniziato il progetto.
     * @param endDate Data per cui e' prevista la fine del progetto.
     * @param sResp Responsabile scientifico del progetto.
     * @param sRef Referente scientifico del progetto.
     */
    public void updateProjectDB(String oldCup, String newCup, String name, float budget, float remainingFunds, Date startDate, Date endDate,
                                String sResp, String sRef){
        String query = "UPDATE azienda.project" +
                "SET cup = ?, name = ?, budget = ?, remaining_funds = ?, start_date = ?, end_date = ?, sresp = ?, sref = ?" +
                "WHERE cup LIKE ?";
        try{
            PreparedStatement prst = connection.prepareStatement(query);
            prst.setString(1, newCup);
            prst.setString(2, name);
            prst.setFloat(3, budget);
            prst.setFloat(4, remainingFunds);
            prst.setDate(5, startDate);
            prst.setDate(6, endDate);
            prst.setString(7, sResp);
            prst.setString(8, sRef);
            prst.setString(9, oldCup);
            prst.executeUpdate();
            prst.close();
        }
        catch (SQLException ex) {
            System.out.println("Update failed: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     *
     * @param cupList Lista dei CUP delle tuple.
     * @param nameList Lista dei nomi delle tuple.
     * @param budgetList Lista dei budget delle tuple.
     * @param remainingFundsList Lista dei fondi rimanenti delle tuple.
     * @param startDateList Lista delle date di inizio delle tuple.
     * @param endDateList Lista delle date di fine delle tuple.
     * @param sRespList Lista dei responsabili scientifici delle tuple.
     * @param sRefList Lista dei referenti scientifici delle tuple.
     */
    public void loadProjectDB(ArrayList<String> cupList, ArrayList<String> nameList, ArrayList<Float> budgetList, ArrayList<Float> remainingFundsList,
                              ArrayList<Date> startDateList, ArrayList<Date> endDateList, ArrayList<String> sRespList, ArrayList<String> sRefList){
        try{
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM azienda.project;");

            while (rs.next()) {
                cupList.add(rs.getString("cup"));
                nameList.add(rs.getString("name"));
                budgetList.add(rs.getFloat("budget"));
                remainingFundsList.add(rs.getFloat("remaining_funds"));
                startDateList.add(rs.getDate("start_date"));
                endDateList.add(rs.getDate("end_date"));
                sRespList.add(rs.getString("sresp"));
                sRefList.add(rs.getString("sref"));
            }
            rs.close();
            st.close();
        }
        catch (SQLException ex) {
            System.out.println("Load failed: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

}
