package Model;

import java.time.LocalDate;

public class CareerDevelopment {
    String oldRole, newRole;
    LocalDate date;
    float salaryChange;
    Employee employee;
    float oldSalary,newSalary;

    public CareerDevelopment(Employee employee,String oldRole, String newRole,  float salaryChange ){
        this.employee=employee;
            this.oldRole=oldRole;
            this.newRole=newRole;
            date=LocalDate.now();
            this.salaryChange=salaryChange;
            this.oldSalary=employee.getSalary();
            this.newSalary=employee.getSalary()+salaryChange;
    }
    public String getSSN(){
        return employee.getSSN();
    }
    public String getFirstName(){
        return employee.getFirstName();
    }
    public String getLastName(){
        return employee.getLastName();
    }
    public String getOldRole(){
        return oldRole;
    }
    public String getNewRole(){
        return newRole;
    }
    public float getOldSalary(){ return oldSalary; }
    public float getNewSalary(){
        return newSalary;
    }
    public LocalDate getDate(){
        return date;
    }
}
