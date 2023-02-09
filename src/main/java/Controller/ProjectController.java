package Controller;
import GUI.ProjectListController;
import Model.*;

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
        }else System.out.println("Il progetto esiste già");
    }
    public void modifyProjectList(int index,
                                  String cup,String name,
                                  String budget,LocalDate endDate,
                                  String Sref,String Sresp) {
        Project project = controller.getProjectController().getProjectArrayList().get(index);
        if (project.getBudget()<=Float.parseFloat(budget)) {
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
            project.setRemainingFunds(project.getRemainingFunds()+(Float.parseFloat(budget)-project.getBudget()));
            project.setBudget(Float.parseFloat(budget));

            project.setEndDate(endDate);
        }else System.out.println("Il budget può essere solo maggiorato");
    }
    public void dismissProject(int index){
        controller=Controller.getInstance();
        Project project=projectArrayList.get(index);
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
        projectArrayList.remove(index);
    }
}
