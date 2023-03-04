package DAOPostgresImplementation;

import DAO.DAOCareerDevelopment;
import Database.PostgresDBConnection;

import java.sql.*;
import java.util.ArrayList;

public class DAOCareerDevelopmentPostgres implements DAOCareerDevelopment {

    private Connection connection;

    /**
     * Costruttore della classe. Ottiene il collegamento al database.
     * @throws SQLException Gestione delle eccezioni SQL
     */
    public DAOCareerDevelopmentPostgres() throws SQLException{
        connection = PostgresDBConnection.getInstance().getConnection();
    }

    /**
     * Metodo che carica tutte le tuple della tabella Career Development
     * @param oldRoleList           Lista dei vecchi ruoli.
     * @param newRoleList           Lista dei nuovi ruoli.
     * @param roleChangeDateList    Lista delle date degli scatti di carriera.
     * @param salaryChangeList      Lista dei cambi di salario.
     * @param ssnList               Lista degli ssn.
     */
    public void loadCareerDevelopment(ArrayList<String> oldRoleList, ArrayList<String> newRoleList, ArrayList<Date> roleChangeDateList,
                                      ArrayList<Float> salaryChangeList, ArrayList<String> ssnList){
        try{
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM azienda.career_development;");

            while (rs.next()) {
                oldRoleList.add(rs.getString("old_role"));
                newRoleList.add(rs.getString("new_role"));
                roleChangeDateList.add(rs.getDate("role_change_date"));
                salaryChangeList.add(rs.getFloat("salary_change"));
                ssnList.add(rs.getString("ssn"));
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
