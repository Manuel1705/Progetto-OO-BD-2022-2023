package Controller;
import GUI.EmployeeListController;
import GUI.LaboratoryListController;
import GUI.ProjectListController;
import Model.*;

import java.util.ArrayList;
public class LaboratoryController {
    private Controller controller;
    private ArrayList<Laboratory> laboratoryArrayList = new ArrayList<>();

    /**
     * Costruttore che riceve in input il controller che lo ha chiamato e inizializza l'attributo controller.
     * @param controller
     */
    public LaboratoryController(Controller controller){
        this.controller = controller;
    }

    public ArrayList<Laboratory> getLaboratoryArrayList() {
        return laboratoryArrayList;
    }

    /**
     * Metodo che controlla le potenziali violazioni dei vincoli del Model dopo l'inserimento dei dati in input e restituisce
     * un elenco di violazioni individuate.
     * @param name
     * @param topic
     * @param Sresp
     * @param project
     * @return
     */
    public ArrayList<String> checkLaboratoryInsert(String name, String topic, String Sresp, String project){
        ArrayList<String> errors = new ArrayList<String>();

        //Controllo dominio nome
        if(name == null || name.isBlank()) errors.add("Name must not be blank.");
        else if(name.length() > 30) errors.add("Name is too long. (Max. 30 characters)");
        //Controllo dominio topic
        if(topic != null && topic.length() > 50) errors.add("Topic is too long. (Max. 50 characters)");
        //Controllo unicita' nome

        //Controllo inserimento sresp
        if(Sresp == null || Sresp.isBlank()) errors.add("Must insert scientific responsible.");
        //Controllo chiavi esterne
        else if(controller.getEmployeeController().findEmployee(Sresp) == null)
            errors.add("Inserted scientific responsible does not exist.");
        else if(!controller.getEmployeeController().findEmployee(Sresp).getRole().equals("Senior"))
            errors.add("Scientific responsible must be a senior employee");
        if((project != null) && (!project.isBlank())){
            if((controller.getProjectController().findProject(project)) == null) errors.add("Selected project does not exist.");
            //Controllo numero massimo di laboratori che possono lavorare sullo stesso progetto.
            else if(controller.getProjectController().labCount(project) >= 3) errors.add("Project already has 3 laboratories working on it.");
        }
        return errors;
    }

    /**
     * Metodo che crea un nuovo oggetto Laboratory usando i dati passati in input e lo inserisce nella lista.
     * @param name
     * @param topic
     * @param Sresp
     * @param project
     */
    public void addLaboratoryList(String name, String topic, String Sresp, String project) {

        controller = Controller.getInstance();
        ArrayList<Employee> employeeArrayList = controller.getEmployeeController().getEmployeeArrayList();
        ArrayList<Project> projectArrayList = controller.getProjectController().getProjectArrayList();
        if (topic == null || topic.isBlank()) {
            topic = "No description";
        }
        Laboratory laboratory = new Laboratory(name, topic);
        if (Sresp != null && !Sresp.isBlank()) {
            laboratory.setSresp(controller.getEmployeeController().findEmployee(Sresp));
        }
        if (project != null && !project.isBlank()) {
            laboratory.setProject(controller.getProjectController().findProject(project));
        }
        laboratoryArrayList.add(laboratory);
    }

    /**
     * Metodo che controlla le potenziali violazioni dei vincoli del Model dopo la modifica dei dati in input e restituisce
     * un elenco di violazioni individuate.
     * @param name
     * @param topic
     * @param Sresp
     * @param project
     * @return
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
        //Controllo unicita' nome
        if(findLaboratory(name) != null) errors.add("Name is already in use by another laboratory.");
        //Controllo inserimento sresp
        if(Sresp == null || Sresp.isBlank()) errors.add("Must insert scientific responsible.");
        //Controllo chiavi esterne
        else if(controller.getEmployeeController().findEmployee(Sresp) == null)
            errors.add("Inserted scientific responsible does not exist.");
        else if(!controller.getEmployeeController().findEmployee(Sresp).getRole().equals("Senior"))
            errors.add("Scientific responsible must be a senior employee");
        if((project != null) && (!project.isBlank())){
            if((controller.getProjectController().findProject(project)) == null) errors.add("Selected project does not exist.");
            //Controllo numero massimo di laboratori che possono lavorare sullo stesso progetto.
            else if(laboratory.getProjectCup() != project
                    && controller.getProjectController().labCount(project) >= 3) errors.add("Project already has 3 laboratories working on it.");
        }
        return errors;
    }

    /**
     * Metodo che modifica i dati del laboratorio corrispondente al nome in input.
     * @param name
     * @param topic
     * @param Sresp
     * @param project
     */
    public void modifyLaboratory(String name, String topic, String Sresp, String project) {
        Laboratory laboratory = findLaboratory(name);
        if (topic != null && !topic.isBlank()) {
            laboratory.setTopic(topic);
        } else laboratory.setTopic("No Description");

        if (Sresp != null && !Sresp.isBlank()) {
            laboratory.setSresp(controller.getEmployeeController().findEmployee(Sresp));
        }
        else laboratory.setSresp(null);


        if(project != null && !project.isBlank()){
            laboratory.setProject(controller.getProjectController().findProject(project));
        }
        else laboratory.setProject(null);
    }

    /**
     * Metodo che restituisce l'oggetto Laboratory il cui nome corrisponde al parametro passato in input. Se non esiste
     * allora viene restituito null.
     * @param name
     * @return
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
     * @param name
     * @return
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
     * @param name
     */
    public void dismissLaboratory(String name) {
        controller = Controller.getInstance();
        Laboratory laboratory = findLaboratory(name);
        ArrayList<Employee> employeeArrayList = controller.getEmployeeController().getEmployeeArrayList();
        for (Employee employee : employeeArrayList) {
            if (employee.getLabName().equals(laboratory.getName())) {
                employee.setLab(null);
            }
        }
        ArrayList<TemporaryEmployee> temporaryEmployeeArrayList = controller.getTemporaryEmployeeController().getTemporaryEmployeeArrayList();
        for (TemporaryEmployee temporaryEmployee : temporaryEmployeeArrayList) {
            if (temporaryEmployee.getLabName().equals(laboratory.getName())) {
                temporaryEmployee.setLab(null);
            }
        }
        ArrayList<Equipment> equipmentArrayList = controller.getEquipmentController().getEquipmentArrayList();
        for (Equipment equipment : equipmentArrayList) {
            if (equipment.getLabName().equals(laboratory.getName())) {
                equipment.setLab(null);
            }
        }
        laboratoryArrayList.remove(laboratory);
    }
}
