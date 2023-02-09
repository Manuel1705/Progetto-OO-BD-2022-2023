package Model;

import Controller.Controller;

import java.time.LocalDate;
import java.util.ArrayList;

public class CareerDevelopment {
    String oldRole, newRole;
    LocalDate date;
    float salaryChange;
    Employee employee;

    public CareerDevelopment(Employee employee,String oldRole, String newRole,  float salaryChange ){
            this.oldRole=oldRole;
            this.newRole=newRole;
            date=LocalDate.now();
            this.salaryChange=salaryChange;
    }
    public String getSSN(){
        return employee.getSSN();
    }
    public String getOldRole(){
        return oldRole;
    }
    public String getNewRole(){
        return newRole;
    }
    public float getOldSalary(){
        return employee.getSalary()-salaryChange;
    }
    public float getNewSalary(){
        return employee.getSalary();
    }
    public LocalDate getDate(){
        return date;
    }
}
