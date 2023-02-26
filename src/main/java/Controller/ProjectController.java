package Controller;

import DAOPostgresImplementation.DAOProjectPostgres;
import Model.*;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
public class ProjectController {
    private Controller controller;
    private ArrayList<Project> projectArrayList= new ArrayList<>();

    /**
     * Metodo che restituisce projectArrayList.
     * @return L'ArrayList.
     */
    public ArrayList<Project> getProjectArrayList(){
        return projectArrayList;
    }



    /**
     * Costruttore che riceve in input il controller che lo ha chiamato e inizializza l'attributo controller.
     * @param controller
     */
    public ProjectController(Controller controller){
        this.controller = controller;
    }

    /**
     * Metodo che restituisce il progetto con il cup passato in input se esso esiste, altrimenti restituisce null.
     * @param cup CUP del progetto da cercare.
     * @return L'ogetto Project trovato oppure null.
     */
    public Project findProjectCup(String cup){
        for(Project project: projectArrayList){
            if (project.getCup().equals(cup)) return project;
        }
        return null;
    }

    /**
     * Metodo che restituisce il progetto con il nome passato in input se esso esiste, altrimenti restituisce null.
     * @param name Nome del progetto da cercare.
     * @return L'ogetto Project trovato oppure null.
     */
    public Project findProjectName(String name){
        for(Project project: projectArrayList){
            if (project.getName().equals(name)) return project;
        }
        return null;
    }



    /**
     * Metodo che restituisce il numero di laboratori che partecipano al progetto associato al cup passato in input.
     * @param cup
     * @return
     */
    public int labCount(String cup){
        int count = 0;
        for(Laboratory lab: controller.getLaboratoryController().getLaboratoryArrayList()){
            if (lab.getProjectCup() != null && lab.getProjectCup().equals(cup)) count++;
        }
        return count;
    }

    /**
     * Metodo che controlla le potenziali violazioni dei vincoli del Model dopo l'inserimento dei dati in input e restituisce
     * un elenco di violazioni individuate.
     * @param cup
     * @param name
     * @param budget
     * @param endDate
     * @param SrespSSN
     * @param SrefSSN
     * @return
     */
    public ArrayList<String> checkProjectInsert(String cup, String name, float budget,
                                                LocalDate endDate, String SrespSSN,
                                                String SrefSSN){
        ArrayList<String> errors = new ArrayList<String>();

        //Controllo dominio cup
        if(cup == null || cup.isBlank()) errors.add("CUP must not be blank.");
        else if(cup.length() != 15) errors.add("CUP must be 15 characters long.");
        //Controllo dominio nome
        if(name == null || name.isBlank()) errors.add("Name must not be blank.");
        else if(name.length() > 30) errors.add("Name is too long. (Max. 30 characters)");
        //Controllo dominio budget
        if(budget < 0) errors.add("Budget must be positive.");
        //Controllo dominio end date
        if(endDate == null) errors.add("Project end date must be inserted.");
        else if(endDate.isBefore(LocalDate.now())) errors.add("Project end date must be a future date.");
        //Controllo chiavi esterne
        if(SrespSSN == null || SrespSSN.isBlank()) errors.add("Must insert scientific responsible.");
        else if(controller.getEmployeeController().findEmployee(SrespSSN) == null)
            errors.add("Inserted scientific responsible does not exist.");
        else if(!controller.getEmployeeController().findEmployee(SrespSSN).getRole().equals("Executive"))
            errors.add("Scientific responsible must be an executive employee.");

        if(SrefSSN == null || SrefSSN.isBlank()) errors.add("Must insert scientific reference.");
        else if(controller.getEmployeeController().findEmployee(SrefSSN) == null)
            errors.add("Inserted scientific reference does not exist.");
        else if(!controller.getEmployeeController().findEmployee(SrefSSN).getRole().equals("Senior"))
            errors.add("Scientific reference must be a senior employee.");

        //Controllo unicita' cup
        if(findProjectCup(cup) != null) errors.add("CUP already belongs to another project.");
        //Controllo unicita' nome
        if(findProjectName(name) != null) errors.add("Name already in use by another project.");


        return errors;
    }

    /**
     * Metodo che crea un oggetto Project usando i dati passati in input e lo aggiunge alla lista.
     * @param cup
     * @param name
     * @param budget
     * @param endDate
     * @param SrespSSN
     * @param SrefSSN
     */
    public void addProjectList(String cup, String name, float budget,
                               LocalDate endDate, String SrespSSN,
                               String SrefSSN)
    {

        ArrayList<CompanyEmployee> employeeArrayList = controller.getEmployeeController().getEmployeeArrayList();
        CompanyEmployee Sresp = controller.getEmployeeController().findEmployee(SrespSSN);
        CompanyEmployee Sref = controller.getEmployeeController().findEmployee(SrefSSN);


        Project project = new Project(cup, name, budget, LocalDate.now(), endDate, Sresp, Sref);
        projectArrayList.add(project);

        //Il progetto viene inserito nel database.
        try{
            if(controller.isDBConnected() && controller.getDBMS().equals("PostgreSQL")){
                DAOProjectPostgres daoProjectPostgres = new DAOProjectPostgres();
                daoProjectPostgres.addProjectDB(cup, name, budget, project.getRemainingFunds(), Date.valueOf(project.getStartDate()),
                        Date.valueOf(endDate), SrespSSN, SrefSSN);
            }
        }
        catch(SQLException ex) {
            controller.setDBConnectionState(false);
        }

    }

    /**
     * Metodo che controlla le potenziali violazioni dei vincoli del Model dopo la modifica dei dati in input e restituisce
     * un elenco di violazioni individuate.
     * @param cup
     * @param name
     * @param budget
     * @param endDate
     * @param SrespSSN
     * @param SrefSSN
     * @return
     */
    public ArrayList<String> checkProjectModify(String cup, String name, float budget,
                                                LocalDate endDate, String SrespSSN,
                                                String SrefSSN){
        ArrayList<String> errors = new ArrayList<String>();
        Project project = findProjectCup(cup);

        //Controllo dominio nome
        if(name == null || name.isBlank()) errors.add("Name must not be blank.");
        else if(name.length() > 30) errors.add("Name is too long. (Max. 30 characters)");
        //Controllo dominio budget
        if(budget < 0) errors.add("Budget must be positive.");
        //Controllo dominio end date
        if(endDate == null) errors.add("Project end date must be inserted.");
        else if(endDate.isBefore(LocalDate.now())) errors.add("Project end date must be a future date.");
        else if (endDate.isBefore(project.getStartDate())) errors.add("Project end date must not be before start date.");
        //Controllo unicita' nome
        if(findProjectName(name) != null) errors.add("Name already in use by another project.");
        //Controllo chiavi esterne
        if(SrespSSN == null || SrespSSN.isBlank()) errors.add("Must insert scientific responsible.");
        else if(controller.getEmployeeController().findEmployee(SrespSSN) == null)
            errors.add("Inserted scientific responsible does not exist.");
        else if(!controller.getEmployeeController().findEmployee(SrespSSN).getRole().equals("Executive"))
            errors.add("Scientific responsible must be an executive employee.");

        if(SrefSSN == null || SrefSSN.isBlank()) errors.add("Must insert scientific reference.");
        else if(controller.getEmployeeController().findEmployee(SrefSSN) == null)
            errors.add("Inserted scientific reference does not exist.");
        else if(!controller.getEmployeeController().findEmployee(SrefSSN).getRole().equals("Senior"))
            errors.add("Scientific reference must be a senior employee.");

        //Controlli sul budget del progetto
        if(endDate.isAfter(project.getEndDate()) || budget < project.getBudget()){
            if(controller.getTemporaryEmployeeController().getTotalProjectSalaries(project, project.getStartDate(), endDate) > budget/2)
                errors.add("Total employee salary must be under 50% of the project's budget.");
            if(controller.getEquipmentController().getTotalProjectPrice(project) > budget/2)
                errors.add("Total equipment price must be under 50% of the project's budget.");
        }



        return errors;
    }

    /**
     * Metodo che modifica il progetto associato al CUP passato in input usando i dati associati ai parametri.
     * @param cup
     * @param name
     * @param budget
     * @param endDate
     * @param Sref
     * @param Sresp
     */
    public void modifyProjectList(String cup,String name,
                                  float budget,LocalDate endDate,
                                  String Sref,String Sresp) {
        Project project = findProjectCup(cup);

        ArrayList<CompanyEmployee> employeeArrayList = controller.getEmployeeController().getEmployeeArrayList();
        if (Sref != null && !Sref.isBlank()) {
            project.setSref(controller.getEmployeeController().findEmployee(Sref));
        } else project.setSref(null);
        if (Sresp != null && !Sresp.isBlank()) {
            project.setSresp(controller.getEmployeeController().findEmployee(Sresp));
        } else project.setSresp(null);
        project.setName(name);
        project.setRemainingFunds(budget
                - (controller.getTemporaryEmployeeController().getTotalProjectSalaries(project, project.getStartDate(), endDate)
                - controller.getEquipmentController().getTotalProjectPrice(project)));
        project.setBudget(budget);
        project.setEndDate(endDate);

        //Il progetto viene modificato nel database.
        try{
            if(controller.isDBConnected() && controller.getDBMS().equals("PostgreSQL")){
                DAOProjectPostgres daoProjectPostgres = new DAOProjectPostgres();
                daoProjectPostgres.updateProjectDB(cup, cup, name, budget, project.getRemainingFunds(), Date.valueOf(project.getStartDate()),
                        Date.valueOf(endDate), Sresp, Sref);
            }
        }
        catch(SQLException ex) {
            controller.setDBConnectionState(false);
        }
    }

    /**
     * Metodo che controlla le potenziali violazioni dei vincoli del Model dopo l'eliminazione del progetto associato
     * al CUP passato in input e restituisce un elenco di violazioni individuate.
     * @param cup
     * @return
     */
    public ArrayList<String> checkProjectDelete(String cup){
        ArrayList<String> errors = new ArrayList<String>();

        Project project = findProjectCup(cup);
        if (project == null) {
            errors.add("Project does not exist.");
            return errors;
        }
        return errors;
    }

    /**
     * Metodo che rimuove il progetto associato al cup passato in input.
     * @param cup
     */
    public void dismissProject(String cup){
        Project project= findProjectCup(cup);
        //Il metodo setta a null l'attributo project dell'equipaggiamento acquistato dal progetto
        ArrayList<Equipment>equipmentArrayList=controller.getEquipmentController().getEquipmentArrayList();
        for(Equipment equipment: equipmentArrayList){
            if(equipment.getProjectCup() != null && equipment.getProjectCup().equals(project.getCup())){
                equipment.setProject(null);
            }
        }
        //Il metodo licenza gli impiegati assunti usando i fondi del progetto.
        ArrayList<TemporaryEmployee> temporaryEmployeeArrayList = controller.getTemporaryEmployeeController().getTemporaryEmployeeArrayList();
        ArrayList<TemporaryEmployee> projectEmployeeList = new ArrayList<TemporaryEmployee>();
        for(TemporaryEmployee temp: temporaryEmployeeArrayList){
            if(temp.getProjectCup().equals(project.getCup())){
                projectEmployeeList.add(temp);
            }
        }
        for(TemporaryEmployee temp: projectEmployeeList){
            controller.getTemporaryEmployeeController().fireTemporaryEmployee(temp.getSSN(), null);

        }

        //Il metodo setta a null l'attributo project dei laboratori che contribuivano al progetto.
        ArrayList<Laboratory> laboratoryArrayList = controller.getLaboratoryController().getLaboratoryArrayList();
        for(Laboratory lab: laboratoryArrayList){
            if(lab.getProjectCup() != null && lab.getProjectCup().equals(project.getCup())){
               lab.setProject(null);
            }
        }
        projectArrayList.remove(project);

        //Il progetto viene rimosso dal database.
        try{
            if(controller.isDBConnected() && controller.getDBMS().equals("PostgreSQL")){
                DAOProjectPostgres daoProjectPostgres = new DAOProjectPostgres();
                daoProjectPostgres.removeProjectDB(cup);
            }
        }
        catch(SQLException ex) {
            controller.setDBConnectionState(false);
        }
    }
}
