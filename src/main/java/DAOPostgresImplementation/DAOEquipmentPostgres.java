package DAOPostgresImplementation;

import DAO.DAOEquipment;
import Database.PostgresDBConnection;

import java.sql.*;
import java.util.ArrayList;

public class DAOEquipmentPostgres implements DAOEquipment {

    private Connection connection;

    /**
     * Costruttore della classe. Ottiene il collegamento al database.
     * @throws SQLException Gestione delle eccezioni SQL
     */
    public DAOEquipmentPostgres() throws SQLException{
        connection = PostgresDBConnection.getInstance().getConnection();
    }

    /**
     * Metodo che inserisce una nuova tupla nella tabella Equipment
     * @param id                ID dell'equipment. Chiave primaria.
     * @param name              Nome dell'attrezzatura.
     * @param description       Descrizione dell'attrezzatura.
     * @param price             Prezzo d'acquisto dell'attrezzatura.
     * @param purchaseDate      Data d'acquisto dell'attrezzatura.
     * @param dealer            Fornitore dell'attrezzatura.
     * @param laboratoryName    Nome del laboratorio nel quale è attualmente utilizzata l'attrezzatura.
     * @param projectCup        CUP del progetto che ha acquistato l'attrezzatura.
     */
    public void addEquipmentDB(int id, String name, String description, float price, Date purchaseDate, String dealer, String laboratoryName,
                               String projectCup){
        String query = "INSERT INTO azienda.equipment " +
                "VALUES (?,?,?,?,?,?,?,?);";

        try{
            PreparedStatement prst = connection.prepareStatement(query);
            prst.setInt(1, id);
            prst.setString(2, name);
            prst.setString(3, description);
            prst.setFloat(4, price);
            prst.setDate(5, purchaseDate);
            prst.setString(6, dealer);
            prst.setString(7, laboratoryName);
            prst.setString(8, projectCup);
            prst.executeUpdate();
            prst.close();
        }
        catch (SQLException ex) {
            System.out.println("Insert failed: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Metodo che aggiorna la tupla Equipment con l'id passato in input.
     * @param old_id                Vecchio ID dell'equipment. Chiave primaria.
     * @param new_id Nuovo          ID dell'equipment. Chiave primaria.
     * @param name                  Nome dell'attrezzatura.
     * @param description           Descrizione dell'attrezzatura.
     * @param price                 Prezzo d'acquisto dell'attrezzatura.
     * @param purchaseDate          Data d'acquisto dell'attrezzatura.
     * @param dealer                Fornitore dell'attrezzatura.
     * @param laboratoryName        Nome del laboratorio nel quale è attualmente utilizzata l'attrezzatura.
     * @param projectCup            CUP del progetto che ha acquistato l'attrezzatura.
     */
    public void updateEquipmentDB(int old_id, int new_id, String name, String description, float price, Date purchaseDate, String dealer, String laboratoryName,
                                  String projectCup){
        String query = "UPDATE azienda.equipment " +
                "SET name = ?, description = ?, price = ?, purchase_date = ?, dealer = ?, laboratory_name = ?, project_cup = ?, id_equipment = ? " +
                "WHERE id_equipment = ?;";
        try{
            PreparedStatement prst = connection.prepareStatement(query);
            prst.setString(1, name);
            prst.setString(2, description);
            prst.setFloat(3, price);
            prst.setDate(4, purchaseDate);
            prst.setString(5, dealer);
            prst.setString(6, laboratoryName);
            prst.setString(7, projectCup);
            prst.setInt(8, new_id);
            prst.setInt(9, old_id);
            prst.executeUpdate();
            prst.close();
        }
        catch (SQLException ex) {
            System.out.println("Update failed: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Metodo che rimuove la tupla Equipment con l'id passato in input.
     * @param id Id della tupla.
     */
    public void removeEquipmentDB(int id){
        try {
            PreparedStatement prst = connection.prepareStatement("DELETE FROM azienda.equipment WHERE id_equipment = ?;");
            prst.setInt(1, id);
            prst.executeUpdate();
            prst.close();
        }
        catch (SQLException ex){
            System.out.println("Delete failed: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Metodo che inserisce nelle liste passate per riferimento i dati di ogni singola tupla della tabella Equipment
     * @param idList                Lista degli id.
     * @param nameList              Lista dei nomi.
     * @param descriptionList       Lista delle descrizioni.
     * @param priceList             Lista dei prezzi.
     * @param purchaseDateList      Lista delle date d'acquisto.
     * @param dealerList            Lista dei fornitori.
     * @param laboratoryNameList    Lista dei laboratori.
     * @param projectCupList        Lista dei cup dei progetti.
     */
    public void loadEquipmentDB(ArrayList<Integer> idList, ArrayList<String> nameList, ArrayList<String> descriptionList, ArrayList<Float> priceList,
                              ArrayList<Date> purchaseDateList, ArrayList<String> dealerList, ArrayList<String> laboratoryNameList,
                              ArrayList<String> projectCupList){
        try{
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM azienda.equipment;");

            while (rs.next()) {
               idList.add(rs.getInt("id_equipment"));
               nameList.add(rs.getString("name"));
               descriptionList.add(rs.getString("description"));
               priceList.add(rs.getFloat("price"));
               purchaseDateList.add(rs.getDate("purchase_date"));
               dealerList.add(rs.getString("dealer"));
               laboratoryNameList.add(rs.getString("laboratory_name"));
               projectCupList.add(rs.getString("project_cup"));
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
