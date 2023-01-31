package Controller;

import GUI.EmployeeListController;
import GUI.LaboratoryListController;
import Model.Employee;
import java.time.LocalDate;
import java.util.ArrayList;

public class EmployeeController {
    Controller controller;
    ArrayList<Employee> employeeArrayList= new ArrayList<>(); //lista impiegati model
    public ArrayList<Employee> getEmployeeArrayList(){
        return employeeArrayList;
    }
    //public void addEmployeeList(Employee employee){employeeArrayList.add(employee);}
    public void addEmployeeList(String ssn, String firstName, String lastName,
                                String phoneNum, String address, String role,
                                String email, LocalDate employmentDate,
                                float salary, String lab)
    {
        Employee employee= new Employee(ssn,firstName,lastName,phoneNum,role,salary,employmentDate);

        controller=Controller.getInstance();

        if(!lab.equals("Null")){
            //implementare dopo
            }
            //implementa dopo

        if(!address.isBlank())
            employee.setAddress(address);
        else
            employee.setAddress("Null");

        if(!email.isBlank())
            employee.setEmail(email);
        else
            employee.setEmail("Null");

        employeeArrayList.add(employee); //aggiunge alla lista model
        EmployeeListController.list.add(employee); //aggiunge alla tablella
    }

    public void modifyEmployeeList(int index , String firstName, String lastName,
                                   String phoneNumber, String role, float salary,
                                   String lab,String address,String email)
    {
        Employee employee = EmployeeListController.list.get(index);
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setPhoneNum(phoneNumber);
        employee.setRole(role);
        employee.setSalary(salary);

        if(!lab.equals("Null")) {
            int i = 0;
            while (!LaboratoryListController.list.get(i).getName().equals(lab)) {
                i++;
            }
            employee.setLab(LaboratoryListController.list.get(i));
        }else employee.setLab(null);


        if(!address.isEmpty()){
            employee.setAddress(address);
        }

        if(!email.isEmpty()){
            employee.setEmail(email);
        }
    }
}
