package DAOPostgresImplementation;

import DAO.DAOLaboratory;
import Database.PostgresDBConnection;

import java.sql.*;
import java.util.ArrayList;

public class DAOLaboratoryPostgres implements DAOLaboratory{

    private Connection connection;

    /**
     * Costruttore della classe. Ottiene il collegamento al database.
     * @throws SQLException
     */
    public DAOLaboratoryPostgres() throws SQLException{
        connection = PostgresDBConnection.getInstance().getConnection();
    }

    /**
     * Metodo che inserisce una nuova tupla nella tabella Laboratory
     * @param name Nome del laboratorio. Chiave primaria.
     * @param topic Topic del laboratorio.
     * @param sresp SSN del responsabile scientifico del laboratorio.
     * @param project CUP del progetto su cui lavora il laboratorio.
     */
    public void addLaboratoryDB(String name, String topic, String sresp, String project){

        String query = "INSERT INTO azienda.laboratory " +
                "VALUES (?,?,?,?);";
        try{
            PreparedStatement prst = connection.prepareStatement(query);
            prst.setString(1, name);
            prst.setString(2, topic);
            prst.setString(3, sresp);
            prst.setString(4, project);
            prst.executeUpdate();
            prst.close();
        }
        catch (SQLException ex) {
            System.out.println("Insert failed: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Metodo che rimuove una tupla dalla tabella Laboratorio, individuandola usando la sua chiave primaria.
     * @param name Chiave primaria della tupla da eliminare.
     */
    public void removeLaboratoryDB(String name){
        try {
            PreparedStatement prst = connection.prepareStatement("DELETE FROM azienda.laboratory WHERE name LIKE ?;");
            prst.setString(1, name);
            prst.executeUpdate();
            prst.close();
        }
        catch (SQLException ex){
            System.out.println("Delete failed: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Metodo che aggiorna la tupla di Laboratory con chiave primaria uguale a oldName
     * @param oldName Chiave primaria originale.
     * @param newName Nuova chiave primaria della tupla.
     * @param topic Topic del laboratorio.
     * @param sresp SSN del responsabile scientifico del laboratorio.
     * @param project CUP del progetto su cui lavora il laboratorio.
     */
    public void updateLaboratoryDB(String oldName, String newName, String topic, String sresp, String project){
        String query = "UPDATE azienda.laboratory " +
                "SET name = ?, topic = ?, sresp = ?, project = ? " +
                "WHERE name LIKE ?";
        try{
            PreparedStatement prst = connection.prepareStatement(query);
            prst.setString(1, newName);
            prst.setString(2, topic);
            prst.setString(3, sresp);
            prst.setString(4, project);
            prst.setString(5, oldName);
            prst.executeUpdate();
            prst.close();
        }
        catch (SQLException ex) {
            System.out.println("Update failed: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Metodo che riceve in input degli ArrayList passati per riferimento e salva al loro interno i valori di tutte
     * le tuple Laboratory salvate nel database
     * @param nameList Lista dei nomi delle tuple caricate. Chiave primaria della tabella Laboratory.
     * @param topicList Lista dei topic delle tuple.
     * @param srespList Lista degli ssn dei responsabili scientifici dei laboratori.
     * @param projectList Lista dei CUP dei progetti a cui lavorano i laboratori.
     */
    public void loadLaboratoryDB(ArrayList<String> nameList, ArrayList<String> topicList, ArrayList<String> srespList,
                                 ArrayList<String> projectList){
        try{
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM azienda.laboratory;");

            while (rs.next()) {
                nameList.add(rs.getString("name"));
                topicList.add(rs.getString("topic"));
                srespList.add(rs.getString("sresp"));
                projectList.add(rs.getString("project"));
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
