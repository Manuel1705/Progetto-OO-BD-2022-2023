package Controller;
import GUI.EmployeeListController;
import GUI.LaboratoryListController;
import GUI.ProjectListController;
import Model.Employee;
import Model.Laboratory;
import Model.Project;
import java.util.ArrayList;
public class LaboratoryController {
    Controller controller;
    ArrayList<Laboratory> laboratoryArrayList = new ArrayList<>();

    public ArrayList<Laboratory> getLaboratoryArrayList() {
        return laboratoryArrayList;
    }

    public void addLaboratoryList(String name, String topic, String Sresp, String project) {
        boolean exists = false;
        for (Laboratory lab : laboratoryArrayList) {
            if (lab.getName().equals(name)) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            controller = Controller.getInstance();
            ArrayList<Employee> employeeArrayList = controller.getEmployeeController().getEmployeeArrayList();
            ArrayList<Project> projectArrayList = controller.getProjectController().getProjectArrayList();
            if (topic.isBlank()) {
                topic = "No description";
            }
            Laboratory laboratory = new Laboratory(name, topic);
            if (!Sresp.equals("Null")) {
                for (Employee employee : employeeArrayList) {
                    if (employee.getSSN().equals(Sresp)) {
                        laboratory.setSresp(employee);
                        break;
                    }
                }
            }
            if (!project.equals("Null")) {
                for (Project prj : projectArrayList) {
                    if (prj.getCup().equals(project)) {
                        laboratory.setProject(prj);
                        break;
                    }
                }
            }
            laboratoryArrayList.add(laboratory);
            LaboratoryListController.list.add(laboratory);
        } else System.out.println("il laboratorio esiste già");
    }

    public void modifyLaboratory(int index, String topic, String Sresp, String project) {
        Laboratory laboratory = controller.getLaboratoryController().getLaboratoryArrayList().get(index);
        if (!topic.isBlank()) {
            laboratory.setTopic(topic);
        } else laboratory.setTopic("No Description");

        if (!Sresp.equals("Null")) {
            ArrayList<Employee> employeeArrayList = controller.getEmployeeController().getEmployeeArrayList();
            for (Employee employee : employeeArrayList) {
                if (employee.getSSN().equals(Sresp)) {
                    laboratory.setSresp(employee);
                    break;
                }
            }
        }laboratory.setSresp(null);

        if(!project.equals("Null")){
            ArrayList<Project>projectArrayList=controller.getProjectController().getProjectArrayList();
            for (Project prj:projectArrayList) {
                if(prj.getCup().equals(project)){
                    laboratory.setProject(prj);
                    break;
                }
            }
        }laboratory.setProject(null);
    }

}