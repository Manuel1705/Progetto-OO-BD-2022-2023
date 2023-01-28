package Model;

import GUI.LaboratoryListController;
import javafx.collections.ObservableArray;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;


public class Employee {
    private String ssn, firstName, lastName, phoneNum, email="Null", address="Null", role, labName="Null";
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
        this.salary=salary;
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
    public  void setLabName(String laboratory) {

        if (laboratory != null) {
            labName = laboratory;
            int i = 0;
            while (LaboratoryListController.list.get(i).getName() != labName) {
                i++;
            }
            lab = LaboratoryListController.list.get(i);
        }else
        {
            labName="Null";
            lab=null;
        }
    }
    public void setAddress(String address){
        this.address=address;
    }
    public void setEmail(String email){
        this.email=email;
    }
    public void setFirstName(String firstName){
        this.firstName=firstName;
    }
    public  void setLastName(String lastName){
        this.lastName=lastName;
    }
    public  void setPhoneNum(String phoneNum){
        this.phoneNum=phoneNum;
    }
    public  void setRole(String role){
        this.role=role;
    }
    public void setSalary(float salary){
        this.salary=salary;
    }
    }

//new Employee("432","name", "surname","32423","Junior",123)
