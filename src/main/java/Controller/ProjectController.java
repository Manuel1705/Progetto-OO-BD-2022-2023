package Controller;
import GUI.ProjectListController;
import Model.Employee;
import Model.Project;
import java.time.LocalDate;
import java.util.ArrayList;
public class ProjectController {
    Controller controller;
    ArrayList<Project> projectArrayList= new ArrayList<>();
    public ArrayList<Project> getProjectArrayList(){
        return projectArrayList;
    }
    public void addProjectList(Project project){
        projectArrayList.add(project);
    }
    public void addProjectList(String cup, String name, String budget,
                               LocalDate endDate, String SrespSSN,
                               String SrefSSN)
    {
        controller=Controller.getInstance();
        ArrayList<Employee> employeeArrayList = controller.getEmployeeController().getEmployeeArrayList();
        Employee Sresp= null, Sref= null;

            if(!SrespSSN.equals("Empty Position")) {
                int i = 0;
                while (!employeeArrayList.get(i).getSSN().equals(SrespSSN)) {
                    i++;
                }
                Sresp = employeeArrayList.get(i);
            } else Sresp=null;

            if(!SrefSSN.equals("Empty Position")) {
                int i = 0;
                while (!employeeArrayList.get(i).getSSN().equals(SrefSSN)) {
                    i++;
                }
                Sref = employeeArrayList.get(i);
            }else Sref=null;

        Project project = new Project(cup,name,Float.parseFloat(budget),endDate, Sresp, Sref );
        projectArrayList.add(project); //aggiunge alla lista controller
        ProjectListController.list.add(project); //aggiunge alla tabella
    }
    public void modifyProjectList(){

    }
}
