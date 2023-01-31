package Model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Project{
    public Project(String cup, String name, float budget, LocalDate endDate, Employee Sresp,Employee Sref){
        this.cup=cup;
        this.name=name;
        this.budget=budget;
        remainingFunds=budget;
        startDate=LocalDate.now();
        this.endDate=endDate;
        this.Sresp=Sresp;
        this.Sref=Sref;
    }
    public Project(String cup, String name, float budget, LocalDate endDate){
        this.cup=cup;
        this.name=name;
        this.budget=budget;
        remainingFunds=budget;
        startDate=LocalDate.now();
        this.endDate=endDate;
        Sref=null;
        Sresp=null;
    }
    private String cup, name;
    private float budget, remainingFunds;
    private LocalDate startDate, endDate;
    private ArrayList<Laboratory> labs = new ArrayList<>();
    private ArrayList<Employee> temporary = new ArrayList<>();
    private Employee Sresp;
    private Employee Sref;
    private ArrayList<Purchase> purchases = new ArrayList<>();
    //public void PurchaseEquipment(String name, String description, int id, Laboratory lab, float price, String dealer){
      //      purchases.add(new Purchase(price,LocalDate.now(),dealer,new Equipment(,name, description, id, lab)));
    //}
    public void HireTemporaryEmployee(String ssn, String firstName, String lastName, String phoneNum, float salary){
            temporary.add(new Employee(ssn, firstName, lastName, phoneNum, "Temporary", salary));
    }
    public void addScientificResponsible(String ssn, String firstName, String lastName, String phoneNum, float salary){
            Sresp = new Employee(ssn, firstName, lastName, phoneNum, "Executive", salary);
    }
    public void addReferent(String ssn, String firstName, String lastName, String phoneNum, float salary){
            Sref = new Employee(ssn, firstName, lastName, phoneNum, "Senior", salary);
    }
    public int getLabsNum(){
        return labs.size();
    }
    public void addLaboratory(Laboratory lab){
        if(labs.size()<3)
            labs.add(lab);
    }
    public String getName(){
        return name;
    }

    public Employee getSref() {return Sref;}
    public Employee getSResp(){
        return Sresp;
    }

    public String getSrefSSN(){
        if(Sref == null)
            return "Empty Position";
        return Sref.getSSN();
    }
    public String getSrespSSN(){
        if(Sresp == null)
            return "Empty Position";
        return Sresp.getSSN();
    }
    public String getCup(){
        return cup;
    }
    public float getBudget(){
        return budget;
    }
    public float getRemainingFunds(){
        return remainingFunds;
    }
    public LocalDate getStartDate(){
        return startDate;
    }
    public LocalDate getEndDate(){
        return endDate;
    }
    public void setSresp(Employee employee){
        Sresp=employee;
    }
    public void setSref(Employee employee){
        Sref=employee;
    }

}
