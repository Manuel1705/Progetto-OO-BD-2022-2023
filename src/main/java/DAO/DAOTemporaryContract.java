package DAO;


import java.util.ArrayList;

/**
 * Interfaccia che permette di implementare i DAO della tabella Temporary Contract
 */
public interface DAOTemporaryContract {
    /**
     * Inserisce una nuova tupla Temporary Contract nel database.
     * @param ssn SSN dell'impiegato assunto temporaneamente
     * @param cup CUP del progetto che ha assunto l'impiegato.
     */
    public void addTemporaryContractDB(String ssn, String cup);

    /**
     * Metodo che inserisce nelle liste passate per riferimento i dati di tutte le tuple della tabella Temporary Contract.
     * @param ssnList Lista di SSN.
     * @param cupList Lista dei CUP.
     */
    public void loadTemporaryContractDB(ArrayList<String> ssnList, ArrayList<String> cupList);


}
