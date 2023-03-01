package DAO;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Interfaccia che permette d'implementare i DAO della tabella Project
 */
public interface DAOProject {
    /**
     * Metodo che inserisce una nuova tupla nella tabella Project
     * @param cup               Codice Unico Progetto del progetto. Chiave primaria.
     * @param name              Nome del progetto.
     * @param budget            Budget del progetto.
     * @param remainingFunds    Fondi rimanenti del progetto.
     * @param startDate         Data in cui è iniziato il progetto.
     * @param endDate           Data per cui è prevista la fine del progetto.
     * @param sResp             Responsabile scientifico del progetto.
     * @param sRef              Referente scientifico del progetto.
     */
    public void addProjectDB(String cup, String name, float budget, float remainingFunds, Date startDate, Date endDate,
                             String sResp, String sRef);

    /**
     * Metodo che rimuove una tupla dalla tabella Project, individuandola usando la sua chiave primaria.
     * @param cup Chiave primaria.
     */
    public void removeProjectDB(String cup);

    /**
     * Metodo che aggiorna la tupla Project con chiave primaria uguale a oldCup
     * @param oldCup            Chiave primaria originale.
     * @param newCup            Nuovo CUP del progetto.
     * @param name              Nome del progetto.
     * @param budget            Budget del progetto.
     * @param remainingFunds    Fondi rimanenti del progetto.
     * @param startDate         Data in cui è iniziato il progetto.
     * @param endDate           Data per cui è prevista la fine del progetto.
     * @param sResp             Responsabile scientifico del progetto.
     * @param sRef              Referente scientifico del progetto.
     */
    public void updateProjectDB(String oldCup, String newCup, String name, float budget, float remainingFunds, Date startDate, Date endDate,
                                String sResp, String sRef);

    /**
     * Aggiorna i fondi rimanenti del progetto.
     * @param cup               La cup del progetto da aggiornare
     * @param remainingFunds    I fondi rimanenti
     */
    public void updateProjectDBRemainingFunds(String cup, float remainingFunds);

    /**
     * Metodo che carica tutte le tuple project dal databasse.
     * @param cupList               Lista dei CUP delle tuple.
     * @param nameList              Lista dei nomi delle tuple.
     * @param budgetList            Lista dei budget delle tuple.
     * @param remainingFundsList    Lista dei fondi rimanenti delle tuple.
     * @param startDateList         Lista delle date di inizio delle tuple.
     * @param endDateList           Lista delle date di fine delle tuple.
     * @param sRespList             Lista dei responsabili scientifici delle tuple.
     * @param sRefList              Lista dei referenti scientifici delle tuple.
     */
    public void loadProjectDB(ArrayList<String> cupList, ArrayList<String> nameList, ArrayList<Float> budgetList, ArrayList<Float> remainingFundsList,
                              ArrayList<Date> startDateList, ArrayList<Date> endDateList, ArrayList<String> sRespList, ArrayList<String> sRefList);
}
