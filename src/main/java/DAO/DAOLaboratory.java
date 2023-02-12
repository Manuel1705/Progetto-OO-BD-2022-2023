package DAO;

import Model.Laboratory;

import java.util.ArrayList;

/**
 * Interfaccia che permette di implementare i DAO Laboratory
 */
public interface DAOLaboratory {
    /**
     * Metodo che inserisce una nuova tupla nella tabella Laboratory
     * @param name Nome del laboratorio. Chiave primaria.
     * @param topic Topic del laboratorio.
     * @param sresp SSN del responsabile scientifico del laboratorio.
     * @param project CUP del progetto su cui lavora il laboratorio.
     */
    public void addLaboratoryDB(String name, String topic, String sresp, String project);

    /**
     * Metodo che rimuove una tupla dalla tabella Laboratorio, individuandola usando la sua chiave primaria.
     * @param name Chiave primaria della tupla da eliminare.
     */
    public void removeLaboratoryDB(String name);

    /**
     * Metodo che aggiorna la tupla di Laboratory con chiave primaria uguale a oldName
     * @param oldName Chiave primaria originale.
     * @param newName Nuova chiave primaria della tupla.
     * @param topic Topic del laboratorio.
     * @param sresp SSN del responsabile scientifico del laboratorio.
     * @param project CUP del progetto su cui lavora il laboratorio.
     */
    public void updateLaboratoryDB(String oldName, String newName, String topic, String sresp, String project);

    /**
     * Metodo che riceve in input degli ArrayList passati per riferimento e salva al loro interno i valori di tutte
     * le tuple Laboratory salvate nel database
     * @param nameList Lista dei nomi delle tuple caricate. Chiave primaria della tabella Laboratory.
     * @param topicList Lista dei topic delle tuple.
     * @param srespList Lista degli ssn dei responsabili scientifici dei laboratori.
     * @param projectList Lista dei CUP dei progetti a cui lavorano i laboratori.
     */
    public void loadLaboratoryDB(ArrayList<String> nameList, ArrayList<String> topicList, ArrayList<String> srespList,
                                  ArrayList<String> projectList);
}
