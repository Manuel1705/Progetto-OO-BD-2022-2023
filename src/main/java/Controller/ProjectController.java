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
        boolean exists=false;
        for (Project project:projectArrayList) {
            if(project.getName().equals(name) || project.getCup().equals(cup)) {
                exists=true;
                break;
            }
        }
        if(!exists) {
            controller = Controller.getInstance();
            ArrayList<Employee> employeeArrayList = controller.getEmployeeController().getEmployeeArrayList();
            Employee Sresp = null, Sref = null;

            if (!SrespSSN.equals("Empty Position")) {
                for (Employee employee : employeeArrayList) {
                    if(employee.getSSN().equals(SrespSSN)){
                        Sresp=employee;
                    }
                }
            } else Sresp = null;

            if (!SrefSSN.equals("Empty Position")) {
                for (Employee employee : employeeArrayList) {
                    if(employee.getSSN().equals(SrefSSN)){
                        Sref=employee;
                    }
                }
            } else Sref = null;

            Project project = new Project(cup, name, Float.parseFloat(budget), endDate, Sresp, Sref);
            projectArrayList.add(project);
            ProjectListController.list.add(project);
        }
        System.out.println("Il progetto esiste già");
    }
    public void modifyProjectList(){

    }
}
