package Controller;
import GUI.EmployeeListController;
import GUI.LaboratoryListController;
import GUI.ProjectListController;
import Model.*;

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
        } else System.out.println("il laboratorio esiste già");
    }

    public void modifyLaboratory(String name, String topic, String Sresp, String project) {
        Laboratory laboratory = findLaboratory(name);
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
        }

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

    public Laboratory findLaboratory(String name){
        for(Laboratory laboratory: laboratoryArrayList){
            if (laboratory.getName().equals(name)) return laboratory;
        }
        return null;
    }

    public void dismissLaboratory(String name) {
        controller = Controller.getInstance();
        Laboratory laboratory = findLaboratory(name);
        ArrayList<Employee> employeeArrayList = controller.getEmployeeController().getEmployeeArrayList();
        for (Employee employee : employeeArrayList) {
            if (employee.getLabName().equals(laboratory.getName())) {
                employee.setLab(null);
            }
        }
        ArrayList<TemporaryEmployee> temporaryEmployeeArrayList = controller.getTemporaryEmployeeController().temporaryEmployeeArrayList;
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
