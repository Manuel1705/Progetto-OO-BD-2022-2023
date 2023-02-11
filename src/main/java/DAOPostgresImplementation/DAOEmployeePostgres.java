package DAOPostgresImplementation;

import DAO.DAOEmployee;
import Database.PostgresDBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

public class DAOEmployeePostgres implements DAOEmployee {


    private Connection connection;

    /**
     * Costruttore della classe che ottiene la connessione al database dall'oggetto PostgresDBConnection
     */
    public DAOEmployeePostgres() throws SQLException{
        connection = PostgresDBConnection.getInstance().getConnection();
    }


    /**
     * Metodo che inserisce nel databse una tupla Employee con gli attributi passati alla funzione.
     * @param ssn
     * @param firstName
     * @param lastName
     * @param phoneNum
     * @param role
     * @param salary
     * @param employmentDate
     * @param email
     * @param address
     * @param laboratory
     */
    public void addEmployeeDB (String ssn, String firstName, String lastName, String phoneNum,
                String role,float salary, Date employmentDate, String email,
                String address, String laboratory)
    {
        String query = "INSERT INTO azienda.employee " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?,  ?, ?);";

        try {
            PreparedStatement prst = connection.prepareStatement(query);
            prst.setString(1, ssn);
            prst.setString(2, firstName);
            prst.setString(3, lastName);
            prst.setString(4, phoneNum);
            prst.setString(5, email);
            prst.setString(6, address);
            prst.setDate(7, employmentDate);
            prst.setFloat(8, salary);
            prst.setString(9, role);
            prst.setString(10, laboratory);
            prst.executeUpdate();
            prst.close();
        } catch (SQLException ex) {
            System.out.println("Insert failed: " + ex.getMessage());
            ex.printStackTrace();
        }
    }


    /**
     * Metodo che cerca un impiegato con l'ssn passato e elimina la tupla associata.
     * @param ssn
     */
    public void removeEmployeeDB(String ssn){
        try {
            PreparedStatement prst = connection.prepareStatement("DELETE FROM azienda.employee WHERE ssn LIKE ?;");
            prst.setString(1, ssn);
            prst.executeUpdate();
            prst.close();
        }
        catch (SQLException ex){
            System.out.println("Delete failed: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Metodo che cerca la tupla con ssn = oldSsn, e aggiorna i valori della tupla con quelli passati alla funzione.
     * @param oldSsn
     * @param newSsn
     * @param firstName
     * @param lastName
     * @param phoneNum
     * @param role
     * @param salary
     * @param employmentDate
     * @param email
     * @param address
     * @param laboratory
     */
    public void updateEmployeeDB(String oldSsn, String newSsn, String firstName, String lastName, String phoneNum,
                                 String role, float salary, Date employmentDate, String email,
                                 String address, String laboratory)
    {

        String query = "UPDATE azienda.employee " +
                "SET ssn = ?, first_name = ?, last_name = ?, phone_num = ?, email = ?, address = ?," +
                "employment_date = ?, salary = ?, role = ?, laboratory_name = ?" +
                "WHERE ssn LIKE ?;";

        try {
            PreparedStatement prst = connection.prepareStatement(query);
            prst.setString(1, newSsn);
            prst.setString(2, firstName);
            prst.setString(3, lastName);
            prst.setString(4, phoneNum);
            prst.setString(5, email);
            prst.setString(6, address);
            prst.setDate(7, employmentDate);
            prst.setFloat(8, salary);
            prst.setString(9, role);
            prst.setString(10, laboratory);
            prst.setString(11, oldSsn);
            prst.executeUpdate();
            prst.close();
        }
        catch(SQLException ex){
            System.out.println("Update failed: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Metodo che riceve in input degli ArrayList passati per riferimento e salva al loro interno i valori di tutte
     * le tuple Employee salvate nel database
     * @param ssnList
     * @param firstNameList
     * @param lastNameList
     * @param phoneNumList
     * @param roleList
     * @param employmentDateList
     * @param emailList
     * @param addressList
     * @param laboratoryList
     */
    public void loadEmployeeDB(ArrayList<String> ssnList, ArrayList<String> firstNameList, ArrayList<String> lastNameList, ArrayList<String> phoneNumList,
                               ArrayList<String> roleList, ArrayList<Float> salaryList, ArrayList<Date> employmentDateList, ArrayList<String> emailList,
                               ArrayList<String> addressList, ArrayList<String> laboratoryList)
    {
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM azienda.employee;");

            while (rs.next()) {
                ssnList.add(rs.getString("ssn"));
                firstNameList.add(rs.getString("first_name"));
                lastNameList.add(rs.getString("last_name"));
                phoneNumList.add(rs.getString("phone_num"));
                roleList.add(rs.getString("role"));
                salaryList.add(rs.getFloat("salary"));
                employmentDateList.add(rs.getDate("employment_date"));
                emailList.add(rs.getString("email"));
                addressList.add(rs.getString("address"));
                laboratoryList.add(rs.getString("laboratory_name"));
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            System.out.println("Load failed: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
