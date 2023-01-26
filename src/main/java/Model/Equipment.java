package Model;

import java.time.LocalDate;

public class Equipment {
    String name, description, dealer, labName, projectName;
    int id;
    Laboratory lab;
    Project project;
    float price;
    LocalDate purchaseDate;
    public Equipment(int id, String name, String description, float price, Project project,Laboratory lab, String dealer){
        this.name=name;
        this.description=description;
        this.id=id;
        this.lab=lab;
        labName= lab.getName();
        this.dealer=dealer;
        this.price=price;
        purchaseDate=LocalDate.now();
        this.project=project;
        projectName=project.getName();
    }
    public void AssignLaboratory(Laboratory lab){
        this.lab=lab;

    }
    public String getName(){
        return name;
    }
    public String getProjectName(){
        return projectName;
    }
    public String getLabName(){
        return labName;
    }
    public String getDescription() {
        return description;
    }
    public String getDealer(){
        return dealer;
    }
    public int getId(){
        return id;
    }
    public float getPrice(){
        return  price;
    }
    public LocalDate getPurchaseDate(){
        return purchaseDate;
    }
}
