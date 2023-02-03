package Model;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;


public class TemporaryEmployee {
    private String ssn, firstName, lastName, phoneNum, email, address;
    LocalDate employmentDate;
    float salary;
    private Laboratory lab;
    private Project project;
    ArrayList<CareerDevelopment> careerChanges = new ArrayList<CareerDevelopment>();

    public TemporaryEmployee(String ssn, String firstName, String lastName, String phoneNum, float salary,Project project) {
        this.ssn = ssn;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNum = phoneNum;
        this.employmentDate = LocalDate.now();
        this.salary = salary;
        email="Null";
        address="Null";
        lab=null;

    }
    //costruttore con employmentDate esplicito
    public TemporaryEmployee(String ssn, String firstName, String lastName, String phoneNum, float salary, LocalDate employmentDate,Project project) {
        this.ssn = ssn;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNum = phoneNum;
        this.employmentDate = employmentDate;
        this.salary = salary;
        email="Null";
        address="Null";
        lab=null;
        this.project=project;
    }
    public Period getTemporaryEmploymentTime() {
        return Period.between(employmentDate, LocalDate.now());
    }


    public void assignLab(Laboratory lab){
        this.lab = lab;
    }

    public Laboratory getLab(){
        return lab;
    }

    public String getLabName(){
        if(lab != null) return lab.getName();
        else return "Null";
    }
    public String getProjectCup(){
     return project.getCup();
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public String getSSN(){
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

    public float getSalary(){
        return salary;
    }

    public LocalDate getEmploymentDate(){
        return employmentDate;
    }

    public void setLab(Laboratory lab){
        this.lab=lab;
    }
    public void setProject(Project project){
        this.project=project;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public void setPhoneNum(String phoneNum){
        this.phoneNum = phoneNum;
    }

    public void setSalary(float salary){
        this.salary = salary;
    }
}

