package DAO;


import java.sql.Date;
import java.util.ArrayList;

/**
 * Interfaccia per implementare i DAO Employee
 */
public interface DAOEmployee {

    /**
     * Metodo che inserisce nel database una tupla Employee con gli attributi passati alla funzione.
     * @param ssn                Il Social Security Number dell'impiegato
     * @param firstName          Il nome dell'impiegato
     * @param lastName           Il cognome dell'impiegato
     * @param phoneNum           Il recapito telefonico dell'impiegato
     * @param role               Il ruolo dell'impiegato
     * @param salary             Il salario iniziale dell'impiegato
     * @param employmentDate     La data di assunzione dell'impiegato
     * @param email              L'email dell'impiegato
     * @param address            L'indirizzo dell'impiegato
     * @param laboratory         Il laboratorio presso cui lavora l'impiegato
     */
    public void addEmployeeDB(String ssn, String firstName, String lastName, String phoneNum,
                              String role, float salary, Date employmentDate, String email,
                              String address, String laboratory);

    /**
     * Metodo che cerca un impiegato con l'ssn passato e elimina la tupla associata.
     * @param ssn  Social Security Number di un impiegato
     */
    public void removeEmployeeDB(String ssn);
    
    /**
     * Metodo che cerca la tupla con ssn = oldSsn, e aggiorna i valori della tupla con quelli passati alla funzione.
     * @param oldSsn            Precedente SSN di un impiegato
     * @param newSsn            SSN attuale dell'impiegato
     * @param firstName         Nome dell'impiegato
     * @param lastName          Cognome dell'impiegato
     * @param phoneNum          Recapito telefonico dell'impiegato
     * @param role              Ruolo dell'impiegato
     * @param salary            Salario dell'impiegato
     * @param employmentDate    Data di assunzione dell'impiegato
     * @param email             Email dell'impiegato
     * @param address           Domicilio dell'impiegato
     * @param laboratory        Laboratorio presso cui lavora l'impiegato
     */
    public void updateEmployeeDB(String oldSsn, String newSsn, String firstName, String lastName, String phoneNum,
                                 String role, float salary, Date employmentDate, String email,
                                 String address, String laboratory);

    /**
     * Metodo che riceve in input degli ArrayList passati per riferimento e salva al loro interno i valori di tutte
     * le tuple Employee salvate nel database
     * @param ssn               ArrayList di stringhe contenenti gli SSN degli impiegati
     * @param firstName         ArrayList di stringhe contenenti i nomi degli impiegati
     * @param lastName          ArrayList di stringhe contenenti i cognomi degli impiegati
     * @param phoneNum          ArrayList di stringhe contenenti i numeri di telefono degli impiegati
     * @param role              ArrayList di stringhe contenenti i ruoli di ogni impiegato
     * @param salary            ArrayList di Float contenenti gli stipendi dei vari impiegati
     * @param employmentDate    ArrayList di tipo Date contenenti le date di assunzione degli impiegati
     * @param email             ArrayList di stringhe contenenti le email degli impiegati
     * @param address           ArrayList di stringhe contenenti gli indirizzi domiciliari degli impiegati
     * @param laboratory        ArrayList di stringhe contenenti i laboratori presso cui lavorano
     */
    public void loadEmployeeDB(ArrayList<String> ssn, ArrayList<String> firstName, ArrayList<String> lastName, ArrayList<String> phoneNum,
                               ArrayList<String> role, ArrayList<Float> salary, ArrayList<Date> employmentDate, ArrayList<String> email,
                               ArrayList<String> address, ArrayList<String> laboratory);

}
