package Model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Project{
    private String cup, name;
    private float budget, remainingFunds;
    private LocalDate startDate, endDate;
    private ArrayList<Laboratory> labs = new ArrayList<Laboratory>();
    private ArrayList<Employee> temporary = new ArrayList<Employee>();
    private Employee ScientificResponsible;
    private Employee Referent;
    private ArrayList<Purchase> purchases = new ArrayList<Purchase>();
    public void PurchaseEquipment(String name, String description, int id, Laboratory lab, float price, String dealer){
            purchases.add(new Purchase(price,LocalDate.now(),dealer,new Equipment(name, description, id, lab)));
    }
    public void HireTemporaryEmployee(String ssn, String firstName, String lastName, String phoneNum, float salary){
            temporary.add(new Employee(ssn, firstName, lastName, phoneNum, "Temporary", salary));
    }
    public void addScientificResponsible(String ssn, String firstName, String lastName, String phoneNum, float salary){
            ScientificResponsible = new Employee(ssn, firstName, lastName, phoneNum, "Executive", salary);
    }
    public void addReferent(String ssn, String firstName, String lastName, String phoneNum, float salary){
            Referent = new Employee(ssn, firstName, lastName, phoneNum, "Senior", salary);
    }
    public int getLabsNum(){
        return labs.size();
    }
    public void addLaboratory(Laboratory lab){
        if(labs.size()<3)
            labs.add(lab);
    }

}
