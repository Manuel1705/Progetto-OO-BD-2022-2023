package Model;

import java.time.LocalDate;

public class Equipment {
    String id,name, description, dealer;
    Laboratory lab;
    Project project;
    float price;
    LocalDate purchaseDate;
    public Equipment(String id, String name, float price, Project project,Laboratory lab, String dealer){
        this.name=name;
        this.id=id;
        this.lab=lab;
        this.dealer=dealer;
        this.price=price;
        purchaseDate=LocalDate.now();
        this.project=project;
    }
    public Equipment(String id, String name, float price,String dealer){
        this.name=name;
        this.id=id;
        lab=null;
        this.dealer=dealer;
        this.price=price;
        purchaseDate=LocalDate.now();
        project=null;
    }
    public void AssignLaboratory(Laboratory lab){
        this.lab=lab;

    }
    public String getName(){
        return name;
    }
    public String getProjectCup(){
        if(project!=null) {
            return project.getCup();
        }else return "Null";
    }
    public String getLabName(){
        if(lab!=null) {
            return lab.getName();
        }else return "Null";
    }
    public String getDescription() {
        return description;
    }
    public String getDealer(){
        return dealer;
    }
    public String getId(){
        return id;
    }
    public float getPrice(){
        return  price;
    }
    public LocalDate getPurchaseDate(){
        return purchaseDate;
    }
    public void setLab(Laboratory lab){
        this.lab=lab;
    }
    public void setProject(Project project){
        this.project=project;
    }
    public void setDescription(String description){
        if (!description.isBlank()) {
            this.description = description;
        }else this.description="No description";
    }
    public void setName(String name){
        this.name=name;
    }
}
