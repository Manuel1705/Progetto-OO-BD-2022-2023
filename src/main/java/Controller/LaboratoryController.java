package Controller;

import DAOPostgresImplementation.DAOLaboratoryPostgres;
import Model.*;


import java.sql.SQLException;
import java.util.ArrayList;
public class LaboratoryController {
    private Controller controller;
    private ArrayList<Laboratory> laboratoryArrayList = new ArrayList<>();

    /**
     * Costruttore che riceve in input il controller che lo ha chiamato e inizializza l'attributo controller.
     * @param controller Valore iniziale di controller
     */
    public LaboratoryController(Controller controller){
        this.controller = controller;
    }

    /**
     * Metodo che restituisce laboratoryArrayList.
     * @return L'ArrayList.
     */
    public ArrayList<Laboratory> getLaboratoryArrayList() {
        return laboratoryArrayList;
    }

    /**
     * Metodo che controlla le potenziali violazioni dei vincoli del Model dopo l'inserimento dei dati in input e restituisce
     * un elenco di violazioni individuate.
     * @param name      Nome del laboratorio
     * @param topic     Argomento di studio
     * @param Sresp     Responsabile Scientifico del laboratorio
     * @param project   La cup del progetto di cui si occupa
     * @return delle stringhe che spiegano l'eventuale errore rilevato
     */
    public ArrayList<String> checkLaboratoryInsert(String name, String topic, String Sresp, String project){
        ArrayList<String> errors = new ArrayList<String>();

        //Controllo dominio nome
        if(name == null || name.isBlank()) errors.add("Name must not be blank.");
        else if(name.length() > 30) errors.add("Name is too long. (Max. 30 characters)");
        //Controllo dominio topic
        if(topic != null && topic.length() > 50) errors.add("Topic is too long. (Max. 50 characters)");
        //Controllo unicità nome

        //Controllo inserimento sresp
        if(Sresp == null || Sresp.isBlank()) errors.add("Must insert scientific responsible.");
        //Controllo chiavi esterne
        else if(controller.getEmployeeController().findEmployee(Sresp) == null)
            errors.add("Inserted scientific responsible does not exist.");
        else if(!controller.getEmployeeController().findEmployee(Sresp).getRole().equals("Senior"))
            errors.add("Scientific responsible must be a senior employee");
        if((project != null) && (!project.isBlank())){
            if((controller.getProjectController().findProjectCup(project)) == null) errors.add("Selected project does not exist.");
            //Controllo numero massimo di laboratori che possono lavorare sullo stesso progetto.
            else if(controller.getProjectController().labCount(project) >= 3) errors.add("Project already has 3 laboratories working on it.");
        }
        return errors;
    }

    /**
     * Metodo che crea un nuovo oggetto Laboratory usando i dati passati in input e lo inserisce nella lista.
     * @param name      Nome del laboratorio
     * @param topic     Argomento di studio
     * @param Sresp     Responsabile Scientifico del laboratorio
     * @param project   La cup del progetto di cui si occupa
     */
    public void addLaboratoryList(String name, String topic, String Sresp, String project) {


        //argomento
        if (topic == null || topic.isBlank()) {
            topic = "No description";
        }
        CompanyEmployee sRespEmp = null;
        Project newProject = null;
        
        //responsabile scientifico
        if (Sresp != null && !Sresp.isBlank()) {
            sRespEmp = controller.getEmployeeController().findEmployee(Sresp);
        }
        
        //progetto
        if (project != null && !project.isBlank()) {
            newProject = controller.getProjectController().findProjectCup(project);
        }
        Laboratory laboratory = new Laboratory(name, topic, sRespEmp, newProject);
        laboratoryArrayList.add(laboratory);

        //Il laboratorio viene inserito nel database.
        try{
            if(controller.isDBConnected() && controller.getDBMS().equals("PostgreSQL")){
                DAOLaboratoryPostgres daoLaboratoryPostgres = new DAOLaboratoryPostgres();
                daoLaboratoryPostgres.addLaboratoryDB(name, topic, Sresp, project);

            }
        }
        catch(SQLException ex) {
            controller.setDBConnectionState(false);
        }
    }

    /**
     * Metodo che controlla le potenziali violazioni dei vincoli del Model dopo la modifica dei dati in input e restituisce
     * un elenco di violazioni individuate.
     * @param name      Nome del laboratorio
     * @param topic     Argomento di studio
     * @param Sresp     Responsabile Scientifico del laboratorio
     * @param project   La cup del progetto di cui si occupa
     * @return  Stringhe contenenti eventuali errori
     */
    public ArrayList<String> checkLaboratoryModify(String name, String topic, String Sresp, String project){
        ArrayList<String> errors = new ArrayList<String>();
        Laboratory laboratory = findLaboratory(name);
        if(laboratory == null){
            errors.add("Laboratory does not exist.");
            return errors;
        }
        //Controllo dominio topic
        if(topic != null && topic.length() > 50) errors.add("Topic is too long. (Max. 50 characters)");
        //Controllo inserimento sresp
        if(Sresp == null || Sresp.isBlank()) errors.add("Must insert scientific responsible.");
        //Controllo chiavi esterne
        else if(controller.getEmployeeController().findEmployee(Sresp) == null)
            errors.add("Inserted scientific responsible does not exist.");
        else if(!controller.getEmployeeController().findEmployee(Sresp).getRole().equals("Senior"))
            errors.add("Scientific responsible must be a senior employee");
        if((project != null) && (!project.isBlank())){
            if((controller.getProjectController().findProjectCup(project)) == null) errors.add("Selected project does not exist.");
            //Controllo numero massimo di laboratori che possono lavorare sullo stesso progetto.
            else if(laboratory.getProjectCup() != project
                    && controller.getProjectController().labCount(project) >= 3) errors.add("Project already has 3 laboratories working on it.");
        }
        return errors;
    }

    /**
     * Metodo che modifica i dati del laboratorio corrispondente al nome in input.
     * @param name      Nome del laboratorio
     * @param topic     Argomento di studio
     * @param Sresp     Responsabile Scientifico del laboratorio
     * @param project   La cup del progetto di cui si occupa
     */
    public void modifyLaboratory(String name, String topic, String Sresp, String project) {
        Laboratory laboratory = findLaboratory(name);
        //argomento di ricerca
        if (topic != null && !topic.isBlank()) {
            laboratory.setTopic(topic);
        } else laboratory.setTopic("No Description");
        
        //responsabile scinetifico
        if (Sresp != null && !Sresp.isBlank()) {
            laboratory.setSresp(controller.getEmployeeController().findEmployee(Sresp));
        }
        else laboratory.setSresp(null);

        //progetto
        if(project != null && !project.isBlank()){
            laboratory.setProject(controller.getProjectController().findProjectCup(project));
        }
        else laboratory.setProject(null);

        //Il laboratorio modificato nel database.
        try{
            if(controller.isDBConnected() && controller.getDBMS().equals("PostgreSQL")){
                DAOLaboratoryPostgres daoLaboratoryPostgres = new DAOLaboratoryPostgres();
                daoLaboratoryPostgres.updateLaboratoryDB(name, name, topic, Sresp, project);

            }
        }
        catch(SQLException ex) {
            controller.setDBConnectionState(false);
        }
    }

    /**
     * Metodo che restituisce l'oggetto Laboratory il cui nome corrisponde al parametro passato in input. Se non esiste
     * allora viene restituito null.
     * @param name Il nome del laboratorio da cercare.
     * @return L'oggetto Laboratory trovato oppure null.
     */
    public Laboratory findLaboratory(String name){
        for(Laboratory laboratory: laboratoryArrayList){
            if (laboratory.getName().equals(name)) return laboratory;
        }
        return null;
    }
    /**
     * Metodo che controlla le potenziali violazioni dei vincoli del Model dopo l'eliminazione del laboratorio associato
     * al nome passato in input e restituisce un elenco di violazioni individuate.
     * @param name  Nome del laboratorio da eliminare
     * @return  Stringhe di eventuali errori riscontrati
     */
    public ArrayList<String> checkLaboratoryDelete(String name) {
        ArrayList<String> errors = new ArrayList<String>();

        Laboratory laboratory = findLaboratory(name);
        if (laboratory == null) {
            errors.add("Laboratory does not exist.");
            return errors;
        }
        return errors;
    }

    /**
     * Metodo che rimuove il laboratorio corrispondente al nome passato in input.
     * @param name  Nome del laboratorio da rimuovere
     */
    public void dismissLaboratory(String name) {
        controller = Controller.getInstance();
        Laboratory laboratory = findLaboratory(name);
        //setta a null il nome del laboratorio che compare nelle tuple degli impiegati
        ArrayList<CompanyEmployee> employeeArrayList = controller.getEmployeeController().getEmployeeArrayList();
        for (CompanyEmployee employee : employeeArrayList) {
            if (employee.getLabName() != null && employee.getLabName().equals(laboratory.getName())) {
                employee.setLab(null);
            }
        }
        //setta a null il nome del laboratorio che compare nelle tuple degli impiegati temporanei
        ArrayList<TemporaryEmployee> temporaryEmployeeArrayList = controller.getTemporaryEmployeeController().getTemporaryEmployeeArrayList();
        for (TemporaryEmployee temporaryEmployee : temporaryEmployeeArrayList) {
            if (temporaryEmployee.getLabName() != null && temporaryEmployee.getLabName().equals(laboratory.getName())) {
                temporaryEmployee.setLab(null);
            }
        }
        //setta a null il nome del laboratorio se esso compare nelle tuple di un qualche equipaggiamento
        ArrayList<Equipment> equipmentArrayList = controller.getEquipmentController().getEquipmentArrayList();
        for (Equipment equipment : equipmentArrayList) {
            if (equipment.getLabName() != null && equipment.getLabName().equals(laboratory.getName())) {
                equipment.setLab(null);
            }
        }
        laboratoryArrayList.remove(laboratory);

        //Il laboratorio viene eliminato nel database.
        try{
            if(controller.isDBConnected() && controller.getDBMS().equals("PostgreSQL")){
                DAOLaboratoryPostgres daoLaboratoryPostgres = new DAOLaboratoryPostgres();
                daoLaboratoryPostgres.removeLaboratoryDB(name);

            }
        }
        catch(SQLException ex) {
            controller.setDBConnectionState(false);
        }
    }
}
