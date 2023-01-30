package Controller;

import GUI.EmployeeListController;
import GUI.LaboratoryListController;
import Model.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jdk.jshell.execution.LoaderDelegate;

import java.time.LocalDate;
import java.util.ArrayList;

public class EmployeeController {
    ArrayList<Employee> employeeArrayList= new ArrayList<Employee>();
    public ArrayList<Employee> getEmployeeArrayList(){
        return employeeArrayList;
    }
    public void addEmployeeList(Employee employee){
        employeeArrayList.add(employee);
    }
    public void addEmployeeList(String ssn, String firstName, String lastName,
                                String phoneNum, String address, String role,
                                String email, LocalDate employmentDate,
                                float salary, String lab){
        Employee employee= new Employee(ssn,firstName,lastName,phoneNum,role,salary,employmentDate);
        if(!lab.equals("Null")){
            //implemantare dopo
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

        employeeArrayList.add(employee);
        EmployeeListController.list.add(employee);
    }

}
