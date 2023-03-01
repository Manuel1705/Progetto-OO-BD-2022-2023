package DAO;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Interfaccia che permette d'mplementare i DAO della tabella Career Development
 */
public interface DAOCareerDevelopment {
    /**
     * Metodo che carica tutte le tuple della tabella Career Development
     * @param oldRoleList           Lista dei vecchi ruoli.
     * @param newRoleList           Lista dei nuovi ruoli.
     * @param roleChangeDateList    Lista delle date degli scatti di carriera.
     * @param salaryChangeList      Lista dei cambi di salario.
     * @param ssnList               Lista degli ssn.
     */
    public void loadCareerDevelopment(ArrayList<String> oldRoleList, ArrayList<String> newRoleList, ArrayList<Date> roleChangeDateList,
                                      ArrayList<Float> salaryChangeList, ArrayList<String> ssnList);

}
