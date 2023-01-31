package Controller;


import GUI.ProjectListController;
import Model.Employee;
import Model.Project;
import java.time.LocalDate;
import java.util.ArrayList;

public class ProjectController {
    ArrayList<Project> projectArrayList= new ArrayList<>();
    Controller controller;
    public ArrayList<Project> getProjectArrayList(){
        return projectArrayList;
    }
    public void addProjectList(Project project){
        projectArrayList.add(project);
    }
    public void addProjectList(String cup, String name, Float budget,
                               LocalDate endDate, String SrespSSN,
                               String SrefSSN)
    {
        controller=Controller.getInstance();

        //ottego la lista degli impiegati dal controller
        ArrayList<Employee> employeeArrayList = controller.getEmployeeController().getEmployeeArrayList();

        Employee Sresp = null , Sref = null;

        //cerca Sresp nella lista comtroller Employee
        if(!SrespSSN.isBlank()) {
            int i=0;
            while (!employeeArrayList.get(i).getSsn().equals(Sresp)) {
                i++;
            }
            Sresp=employeeArrayList.get(i);
        }

        //cerca Sref nella lista comtroller Employee
        if(!SrefSSN.isBlank()) {
            int i=0;
            while (!employeeArrayList.get(i).getSsn().equals(Sref)){
                i++;
            }
            Sref=employeeArrayList.get(i);
        }

        Project project = new Project(cup,name,budget,endDate, Sresp, Sref );

        projectArrayList.add(project); //aggiunge alla lista controller
        ProjectListController.list.add(project); //aggiunge alla tabella
    }
}
