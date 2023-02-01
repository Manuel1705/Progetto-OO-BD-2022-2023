package Controller;
import GUI.EmployeeListController;
import GUI.LaboratoryListController;
import Model.Employee;
import Model.Laboratory;

import java.time.LocalDate;
import java.util.ArrayList;
public class EmployeeController {
    Controller controller;
    ArrayList<Employee> employeeArrayList= new ArrayList<Employee>();
    public ArrayList<Employee> getEmployeeArrayList(){ return employeeArrayList; }
    public void addEmployeeList(Employee employee){employeeArrayList.add(employee);}
    public void addEmployeeList(String ssn, String firstName, String lastName,
                                String phoneNum, String address, String role,
                                String email, LocalDate employmentDate,
                                String salary, String lab)
    {
        boolean exists=false;
        for (Employee employee:employeeArrayList) {
            if(employee.getSSN().equals(ssn)){
                exists=true;
                break;
            }
        }
        if(!exists) {
            Employee employee = new Employee(ssn, firstName, lastName, phoneNum, role, Float.parseFloat(salary), employmentDate);
            controller = Controller.getInstance();
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

            employeeArrayList.add(employee);
            EmployeeListController.list.add(employee);
        }else System.out.println("L'impigato esiste già");
    }
    public void modifyEmployeeList(int index , String firstName, String lastName,
                                   String phoneNumber, String role, String salary,
                                   String lab,String address,String email)
    {

        Employee employee = controller.getEmployeeController().getEmployeeArrayList().get(index);
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setPhoneNum(phoneNumber);
        employee.setRole(role);
        employee.setSalary(Float.parseFloat(salary));

        if(!lab.equals("Null")) {
            ArrayList<Laboratory>laboratoryArrayList=controller.getLaboratoryController().getLaboratoryArrayList();
            for (Laboratory laboratory : laboratoryArrayList) {
                if(laboratory.getName().equals(lab)){
                    employee.setLab(laboratory);
                    break;
                }
            }
        }else employee.setLab(null);

        if(!address.isEmpty()){
            employee.setAddress(address);
        }

        if(!email.isEmpty()){
            employee.setEmail(email);
        }
    }
}
