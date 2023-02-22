package Controller;
import GUI.ProjectListController;
import Model.*;

import java.time.LocalDate;
import java.util.ArrayList;
public class ProjectController {
    private Controller controller;
    private ArrayList<Project> projectArrayList= new ArrayList<>();
    public ArrayList<Project> getProjectArrayList(){
        return projectArrayList;
    }
    public void addProjectList(Project project){
        projectArrayList.add(project);
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
     * @param cup
     * @return
     */
    public Project findProject(String cup){
        for(Project project: projectArrayList){
            if (project.getCup().equals(cup)) return project;
        }
        return null;
    }

    /**
     * Metodo che restituisce il numero di laboratori che partecipano al progetto associato al cup passato in input.
     * @param cup
     * @return
     */
    public int labCount(String cup){
        Project project = findProject(cup);
        int count = 0;
        for(Laboratory lab: controller.getLaboratoryController().getLaboratoryArrayList()){
            if (lab.getProjectCup().equals(cup)) count++;
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
        if(findProject(cup) != null) errors.add("CUP already belongs to another project.");


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


        controller = Controller.getInstance();
        ArrayList<Employee> employeeArrayList = controller.getEmployeeController().getEmployeeArrayList();
        Employee Sresp = controller.getEmployeeController().findEmployee(SrespSSN);
        Employee Sref = controller.getEmployeeController().findEmployee(SrefSSN);


        Project project = new Project(cup, name, budget, LocalDate.now(), endDate, Sresp, Sref);
        projectArrayList.add(project);

    }
    public void modifyProjectList(String cup,String name,
                                  float budget,LocalDate endDate,
                                  String Sref,String Sresp) {
        Project project = findProject(cup);
        if (project.getBudget()<=budget) {
            ArrayList<Employee> employeeArrayList = controller.getEmployeeController().getEmployeeArrayList();
            if (!Sref.equals("Empty Position")) {
                for (Employee employee : employeeArrayList) {
                    if (employee.getSSN().equals(Sref)) {
                        project.setSref(employee);
                    }
                }
            } else project.setSref(null);
            if (!Sresp.equals("Empty Position")) {
                for (Employee employee : employeeArrayList) {
                    if (employee.getSSN().equals(Sresp)) {
                        project.setSresp(employee);
                    }
                }
            } else project.setSresp(null);
            project.setName(name);
            project.setRemainingFunds(project.getRemainingFunds()+budget-project.getBudget());
            project.setBudget(budget);

            project.setEndDate(endDate);
        }else System.out.println("Il budget può essere solo maggiorato");
    }
    public void dismissProject(String cup){
        controller=Controller.getInstance();
        Project project= findProject(cup);
        ArrayList<Equipment>equipmentArrayList=controller.getEquipmentController().equipmentArrayList;
        for(Equipment equipment: equipmentArrayList){
            if(equipment.getProjectCup().equals(project.getCup())){
                equipment.setProject(null);
            }
        }
        ArrayList<TemporaryEmployee>temporaryEmployeeArrayList=controller.getTemporaryEmployeeController().temporaryEmployeeArrayList;
        for (int i=0;i<temporaryEmployeeArrayList.size();i++){
            if(temporaryEmployeeArrayList.get(i).getProjectCup().equals(project.getCup())){
                temporaryEmployeeArrayList.remove(i);
            }
        }
        projectArrayList.remove(project);
    }
}
