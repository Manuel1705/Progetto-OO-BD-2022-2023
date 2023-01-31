package Controller;

public class Controller
{
    private EmployeeController employeeController;
    private ProjectController projectController;
    private LaboratoryController laboratoryController;

    private Controller(){ //costruttore
        employeeController = new EmployeeController();
        projectController = new ProjectController();
        laboratoryController= new LaboratoryController();
    }
    private static Controller instance=null;
    public static Controller getInstance(){//singleton controller
        if(instance==null)
            instance=new Controller();

        return instance;
    }
    public EmployeeController getEmployeeController(){return employeeController; }
    public ProjectController getProjectController(){
        return projectController;
    }
    public LaboratoryController getLaboratoryController(){ return laboratoryController;}

}
