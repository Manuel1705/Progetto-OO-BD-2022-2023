package DAO;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Interfaccia che permette di implementare i DAO della tabella Career Development
 */
public interface DAOCareerDevelopment {
    /**
     * Metodo che inserisce una nuova tupla nella tabella CareerDevelopment
     * @param oldRole Vecchio ruolo dell'impiegato
     * @param newRole Nuovo ruolo dell'impiegato
     * @param roleChangeDate Data scatto di carriera
     * @param salaryChange Differenza tra salario nuovo e salario vecchio
     * @param ssn SSN dell'impiegato
     */
    public void addCareerDevelopmentDB(String oldRole, String newRole, Date roleChangeDate, float salaryChange, String ssn);

    /**
     * Metodo che carica tutte le tuple della tabella Career Development
     * @param oldRoleList Lista dei vecchi ruoli.
     * @param newRoleList Lista dei nuovi ruoli.
     * @param roleChangeDateList Lista delle date degli scatti di carriera.
     * @param salaryChangeList Lista dei cambi di salario.
     * @param ssnList Lista degli ssn.
     */
    public void loadCareerDevelopment(ArrayList<String> oldRoleList, ArrayList<String> newRoleList, ArrayList<Date> roleChangeDateList,
                                      ArrayList<Float> salaryChangeList, ArrayList<String> ssnList);

}
