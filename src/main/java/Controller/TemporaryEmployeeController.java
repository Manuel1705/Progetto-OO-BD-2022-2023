package Controller;
import GUI.EmployeeListController;
import GUI.LaboratoryListController;
import GUI.TemporaryEmployeeListController;
import Model.Employee;
import Model.Laboratory;
import Model.Project;
import Model.TemporaryEmployee;

import java.time.LocalDate;
import java.util.ArrayList;
public class TemporaryEmployeeController {
    Controller controller;
    ArrayList<TemporaryEmployee> temporaryEmployeeArrayList= new ArrayList<TemporaryEmployee>();
    public ArrayList<TemporaryEmployee> getTemporaryEmployeeArrayList(){ return temporaryEmployeeArrayList; }
    public void addTemporaryEmployeeList(TemporaryEmployee employee){temporaryEmployeeArrayList.add(employee);}
    public void addTemporaryEmployeeList(String ssn, String firstName, String lastName,
                                String phoneNum, String address,
                                String email, LocalDate employmentDate,
                                String salary, String lab,String project)
    {
        controller = Controller.getInstance();
        boolean exists=false;
        for (TemporaryEmployee employee:temporaryEmployeeArrayList) {
            if(employee.getSSN().equals(ssn)){
                exists=true;
                break;
            }
        }
        Project prj=null;
        if (!project.equals("Null")) {
            ArrayList<Project> projectArrayList = controller.getProjectController().getProjectArrayList();
            for (Project prj_temp : projectArrayList) {
                if(prj_temp.getCup().equals(project)){
                    prj=prj_temp;
                }
            }
        }
        if(!exists && prj!=null) {
            TemporaryEmployee employee = new TemporaryEmployee(ssn, firstName, lastName, phoneNum, Float.parseFloat(salary), employmentDate, prj);
            if (!lab.equals("Null")) {
                ArrayList<Laboratory> labs = controller.getLaboratoryController().getLaboratoryArrayList();
                for (Laboratory laboratory : labs) {
                    if(laboratory.getName().equals(lab)){
                        employee.setLab(laboratory);
                    }
                }
            } else employee.setLab(null);


            if (!address.isBlank())
                employee.setAddress(address);
            else
                employee.setAddress("Null");

            if (!email.isBlank())
                employee.setEmail(email);
            else
                employee.setEmail("Null");

            temporaryEmployeeArrayList.add(employee);
            TemporaryEmployeeListController.list.add(employee);
        }else System.out.println("L'impigato esiste già");
    }
    public void modifyTemporaryEmployeeList(int index , String firstName, String lastName,
                                   String phoneNumber, String salary,
                                   String lab,String address,String email,String project)
    {

        TemporaryEmployee employee = controller.getTemporaryEmployeeController().getTemporaryEmployeeArrayList().get(index);
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setPhoneNum(phoneNumber);
        employee.setSalary(Float.parseFloat(salary));

        if (!lab.equals("Null")) {
            ArrayList<Laboratory> labs = controller.getLaboratoryController().getLaboratoryArrayList();
            for (Laboratory laboratory : labs) {
                if(laboratory.getName().equals(lab)){
                    employee.setLab(laboratory);
                }
            }
        } else employee.setLab(null);
        if (!project.equals("Null")) {
            ArrayList<Project> projectArrayList = controller.getProjectController().getProjectArrayList();
            for (Project prj : projectArrayList) {
                if(prj.getName().equals(lab)){
                    employee.setProject(prj);
                }
            }
        } else employee.setProject(null);

        if(!address.isEmpty()){
            employee.setAddress(address);
        }

        if(!email.isEmpty()){
            employee.setEmail(email);
        }
    }
}
