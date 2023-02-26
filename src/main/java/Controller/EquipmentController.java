package Controller;


import DAOPostgresImplementation.DAOEquipmentPostgres;
import DAOPostgresImplementation.DAOProjectPostgres;
import Model.Equipment;
import Model.Laboratory;
import Model.Project;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public class EquipmentController {
    private Controller controller;
    private ArrayList<Equipment> equipmentArrayList= new ArrayList<>();

    /**
     * Metodo che restituisce equipmentArrayList.
     * @return L'ArrayList.
     */
    public ArrayList<Equipment> getEquipmentArrayList(){ return equipmentArrayList; }

    /**
     * Costruttore della classe che inizializza l'attributo controller.
     * @param controller Il valore iniziale di controller.
     */
    public EquipmentController(Controller controller){
        this.controller = controller;
    }


    /**
     * Metodo che controlla le potenziali violazioni dei vincoli del Model dopo l'inserimento dei dati in input e restituisce
     * un elenco di violazioni individuate.
     * @param id_equipment
     * @param name
     * @param description
     * @param price
     * @param dealer
     * @param lab
     * @param project
     * @return
     */
    public ArrayList<String> checkEquipmentInsert(int id_equipment, String name, String description,
                                                  float price, String dealer,
                                                  String lab, String project){
        ArrayList<String> errors = new ArrayList<String>();

        //Controllo dominio nome
        if(name == null || name.isBlank()) errors.add("Name must not be blank.");
        else if(name.length() > 30) errors.add("Name is too long. (MAX. 30 characters.)");

        //Controllo dominio fornitore
        if(dealer == null || dealer.isBlank()) errors.add("Dealer must not be blank.");
        else if(dealer.length() > 30) errors.add("Dealer name is too long. (MAX. 30 characters.)");

        //Controllo dominio prezzo
        if(price < 0) errors.add("Price must not be negative.");

        //Controllo dominio descrizione
        if(description != null && !description.isBlank() && description.length() > 200) errors.add("Description is too long. (MAX. 200 characters.)");

        //Unicita' id
        if(findEquipment(id_equipment) != null) errors.add("ID is already taken.");

        //Controllo budget progetto
        if(project == null || project.isBlank()) errors.add("Project must not be blank.");
        else {
            Project newProject = controller.getProjectController().findProjectCup(project);
            if (getTotalProjectPrice(newProject) + price > newProject.getBudget() / 2)
                errors.add("Project budget is too low to purchase equipment.");
        }
        return errors;
    }

    /**
     * Metodo che crea un oggetto Equipment usando i dati passati in input e lo aggiunge alla lista.
     * @param id_equipment
     * @param name
     * @param description
     * @param price
     * @param dealer
     * @param lab
     * @param project
     */
    public void addEquipmentList(int id_equipment, String name, String description,
                                 float price, String dealer,
                                 String lab,String project){


        Equipment equipment = new Equipment(id_equipment, name, price, dealer);
        Project newProject = controller.getProjectController().findProjectCup(project);
        equipment.setProject(newProject);

        newProject.setRemainingFunds(newProject.getRemainingFunds() - price);

        if (lab != null && !lab.isBlank()) {
            ArrayList<Laboratory> laboratoryArrayList = controller.getLaboratoryController().getLaboratoryArrayList();
            for (Laboratory laboratory : laboratoryArrayList) {
                if (laboratory.getName().equals(lab)) {
                    equipment.setLab(laboratory);
                }
            }
        } else equipment.setLab(null);

        if (description != null && !description.isBlank()) {
            equipment.setDescription(description);
        } else equipment.setDescription("No description");

        equipmentArrayList.add(equipment);

        //L'equipaggiamento viene inserito nel database e vengono aggiornati i fondi rimanenti del progetto.
        try{
            if(controller.isDBConnected() && controller.getDBMS().equals("PostgreSQL")){
                DAOEquipmentPostgres daoEquipmentPostgres = new DAOEquipmentPostgres();
                daoEquipmentPostgres.addEquipmentDB(id_equipment, name, description, price,
                        Date.valueOf(equipment.getPurchaseDate()), dealer, lab, project);

                DAOProjectPostgres daoProjectPostgres = new DAOProjectPostgres();
                daoProjectPostgres.updateProjectDBRemainingFunds(project, newProject.getRemainingFunds());

            }
        }
        catch(SQLException ex) {
            controller.setDBConnectionState(false);
        }


    }

    /**
     * Metodo che controlla le potenziali violazioni dei vincoli del Model dopo la modifica dei dati in input e restituisce
     * un elenco di violazioni individuate.
     * @param id_equipment
     * @param name
     * @param description
     * @param lab
     * @return
     */
    public ArrayList<String> checkEquipmentModify(int id_equipment, String name, String description, String lab){
        ArrayList<String> errors = new ArrayList<String>();

        //Controllo dominio nome
        if(name == null || name.isBlank()) errors.add("Name must not be blank.");
        else if(name.length() > 30) errors.add("Name is too long. (MAX. 30 characters.)");

        //Controllo dominio descrizione
        if(description != null && !description.isBlank() && description.length() > 200) errors.add("Description is too long. (MAX. 200 characters.)");


        return errors;
    }

    /**
     * Metodo che restituisce l'oggetto Equipment che corrisponde al parametro id se esso e' salvato nella lista, altrimenti
     * il metodo restituisce null.
     * @param id L'id dell'equipaggiamento da cercare.
     * @return L'oggetto Equipment oppure null.
     */
    private Equipment findEquipment(int id){
        for(Equipment equipment: equipmentArrayList) {
            if (equipment.getId() == id) return equipment;
        }
        return null;
    }

    /**
     * Metodo che modifica l'oggetto Equipment che corrisponde all'id fornito usando i dati passati in input.
     * @param id
     * @param name
     * @param description
     * @param lab
     */
    public void modifyEquipment(int id,String name,
                                String description,String lab){

        Equipment equipment = findEquipment(id);
        equipment.setName(name);
        equipment.setDescription(description);
        if(lab != null && !lab.isBlank()){
            ArrayList<Laboratory>laboratoryArrayList = controller.getLaboratoryController().getLaboratoryArrayList();
            for (Laboratory laboratory : laboratoryArrayList) {
                if(laboratory.getName().equals(lab)){
                    equipment.setLab(laboratory);
                    break;
                }
            }
        }else equipment.setLab(null);

        //L'equipaggiamento viene modificato nel database.
        try{
            if(controller.isDBConnected() && controller.getDBMS().equals("PostgreSQL")){
                DAOEquipmentPostgres daoEquipmentPostgres = new DAOEquipmentPostgres();
                daoEquipmentPostgres.addEquipmentDB(id, name, description, equipment.getPrice(),
                        Date.valueOf(equipment.getPurchaseDate()), equipment.getDealer(), lab, equipment.getProjectCup());

            }
        }
        catch(SQLException ex) {
            controller.setDBConnectionState(false);
        }

    }

    /**
     * Metodo che rimuove l'oggetto Equipment che corrisponde all'id fornito dalla lista e aggiorna i fondi del progetto che l'ha
     * acquistato.
     * @param id
     */
    public void deleteEquipment(int id){
        Equipment equipment = findEquipment(id);
        if(equipment.getProjectCup() != null && !equipment.getProjectCup().isBlank()) {
            Project newProject = controller.getProjectController().findProjectCup(equipment.getProjectCup());
            newProject.setRemainingFunds(newProject.getRemainingFunds() + equipment.getPrice());
        }
        equipmentArrayList.remove(equipment);

        //L'equipaggiamento viene eliminato dal database e aggiorna i fondi rimanenti del progetto nel database.
        try{
            if(controller.isDBConnected() && controller.getDBMS().equals("PostgreSQL")){
                DAOEquipmentPostgres daoEquipmentPostgres = new DAOEquipmentPostgres();
                daoEquipmentPostgres.removeEquipmentDB(id);

                if(equipment.getProjectCup() != null && !equipment.getProjectCup().isBlank()) {

                    DAOProjectPostgres daoProjectPostgres = new DAOProjectPostgres();
                    daoProjectPostgres.updateProjectDBRemainingFunds(equipment.getProjectCup(),
                            controller.getProjectController().findProjectCup(equipment.getProjectCup()).getRemainingFunds());
                }

            }
        }
        catch(SQLException ex) {
            controller.setDBConnectionState(false);
        }
    }

    /**
     * Metodo che restituisce il costo totale di tutto l'equipaggiamento acquistato da un determinato progetto.
     * @param project Progetto che ha acquistato l'equipaggiamento.
     * @return Costo totale.
     */
    public float getTotalProjectPrice(Project project){
        float totalPrice = 0;
        for(Equipment equipment: equipmentArrayList){
            if(equipment.getProjectCup()!= null && equipment.getProjectCup().equals(project.getCup()))
                totalPrice += equipment.getPrice();
        }
        return totalPrice;
    }
}

