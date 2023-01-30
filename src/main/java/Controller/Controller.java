package Controller;

public class Controller
{
    private EmployeeController employeeController;
    private Controller(){
        employeeController= new EmployeeController();
        System.out.println(employeeController);
    }
    private static Controller instance=null;
    public static Controller getInstance(){
        if(instance==null)
            instance=new Controller();

        return instance;

    }
    public EmployeeController getEmployeeController(){
        System.out.println(employeeController);
        return employeeController;
    }

}
