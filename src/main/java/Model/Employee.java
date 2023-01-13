package Model;

import javafx.collections.ObservableArray;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;


public class Employee {
    private String ssn, firstName, lastName, phoneNum, email="NULL", address="NULL", role, labName="Null";
    LocalDate employmentDate;
    float salary;
    private Laboratory lab;
ArrayList<CareerDevelopment> careerChanges = new ArrayList<CareerDevelopment>();
    public Employee(String ssn, String firstName, String lastName, String phoneNum, String role, float salary) {
        this.ssn = ssn;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNum = phoneNum;
        this.employmentDate = LocalDate.now();
        this.role = role;
        this.salary=salary;;
        this.email=email;
    }

    public Period getEmploymentTime() {
        return Period.between(employmentDate, LocalDate.now());
    }

    public void ChangeRole(String role, float salary) {
        if (role.equals("Junior") && getEmploymentTime().getYears() < 3 ||
                role.equals("Middle") && getEmploymentTime().getYears() >= 3 && getEmploymentTime().getYears() < 7 ||
                role.equals("Senior") && getEmploymentTime().getYears() >= 7 ||
                role.equals("Executive")){
            careerChanges.add(new CareerDevelopment(this.role, role, ssn, this.salary-salary));
            this.role = role;
            this.salary = salary;
        } else {
            System.out.println("Cambio ruolo non consentito");
        }
    }
    public String getRole(){
        return role;
    }
    public void assignLab(Laboratory lab){

        this.lab=lab;
        labName=lab.getName();
    }
    public Laboratory getLab(){
        return lab;
    }
    public String getLabName(){
        return labName;
    }
    public String getFirstName(){
        return firstName;
    }
    public String getLastName(){
        return lastName;
    }
    public String getSsn(){
        return  ssn;
    }
    public String getPhoneNum(){
        return phoneNum;
    }
    public String getEmail(){
        return email;
    }
    public String getAddress(){
        return address;
    }
    public  float getSalary(){
        return  salary;
    }
    public LocalDate getEmploymentDate(){
        return employmentDate;
    }

}

//new Employee("432","name", "surname","32423","Junior",123)
