package DAO;


import java.sql.Date;
import java.util.ArrayList;

/**
 * Interfaccia che permette d'implementare i DAO della tabella Equipment
 */
public interface DAOEquipment {
    /**
     * Metodo che inserisce una nuova tupla nella tabella Equipment
     * @param id             ID dell'equipment. Chiave primaria.
     * @param name           Nome dell'attrezzatura.
     * @param description    Descrizione dell'attrezzatura.
     * @param price          Prezzo d'acquisto dell'attrezzatura.
     * @param purchaseDate   Data d'acquisto dell'attrezzatura.
     * @param dealer         Fornitore dell'attrezzatura.
     * @param laboratoryName Nome del laboratorio nel quale è attualmente utilizzata l'attrezzatura.
     * @param projectCup     CUP del progetto che ha acquistato l'attrezzatura.
     */
    public void addEquipmentDB(int id, String name, String description, float price, Date purchaseDate, String dealer, String laboratoryName,
                               String projectCup);

    /**
     * Metodo che aggiorna la tupla Equipment con l'id passato in input.
     * @param id             ID dell'equipment. Chiave primaria.
     * @param name           Nome dell'attrezzatura.
     * @param description    Descrizione dell'attrezzatura.
     * @param price          Prezzo d'acquisto dell'attrezzatura.
     * @param purchaseDate   Data d'acquisto dell'attrezzatura.
     * @param dealer         Fornitore dell'attrezzatura.
     * @param laboratoryName Nome del laboratorio nel quale è attualmente utilizzata l'attrezzatura.
     * @param projectCup     CUP del progetto che ha acquistato l'attrezzatura.
     */
    public void updateEquipmentDB(int old_id,int new_id, String name, String description, float price, Date purchaseDate, String dealer, String laboratoryName,
                                  String projectCup);

    /**
     * Metodo che rimuove la tupla Equipment con l'id passato in input.
     * @param id Id della tupla.
     */
    public void removeEquipmentDB(int id);

    /**
     * Metodo che inserisce nelle liste passate per riferimento i dati di ogni singola tupla della tabella Equipment
     * @param idList             Lista degli id.
     * @param nameList           Lista dei nomi.
     * @param descriptionList    Lista delle descrizioni.
     * @param priceList          Lista dei prezzi.
     * @param purchaseDateList   Lista delle date d'acquisto.
     * @param dealerList         Lista dei fornitori.
     * @param laboratoryNameList Lista dei laboratori.
     * @param projectCupList     Lista dei cup dei progetti.
     */
    public void loadEquipmentDB(ArrayList<Integer> idList, ArrayList<String> nameList, ArrayList<String> descriptionList, ArrayList<Float> priceList,
                              ArrayList<Date> purchaseDateList, ArrayList<String> dealerList, ArrayList<String> laboratoryNameList,
                              ArrayList<String> projectCupList);
}
