package DAO;


import java.sql.Date;
import java.util.ArrayList;

/**
 * Interfaccia per implementare i DAO Employee
 */
public interface DAOEmployee {

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
    public void addEmployeeDB(String ssn, String firstName, String lastName, String phoneNum,
                              String role, float salary, Date employmentDate, String email,
                              String address, String laboratory);

    /**
     * Metodo che cerca un impiegato con l'ssn passato e elimina la tupla associata.
     * @param ssn
     */
    public void removeEmployeeDB(String ssn);

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
                                 String address, String laboratory);

    /**
     * Metodo che riceve in input degli ArrayList passati per riferimento e salva al loro interno i valori di tutte
     * le tuple Employee salvate nel database
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
    public void loadEmployeeDB(ArrayList<String> ssn, ArrayList<String> firstName, ArrayList<String> lastName, ArrayList<String> phoneNum,
                               ArrayList<String> role, ArrayList<Float> salary, ArrayList<Date> employmentDate, ArrayList<String> email,
                               ArrayList<String> address, ArrayList<String> laboratory);

}
