package Model;

public class Equipment {
    String name, description;
    int id;
    Laboratory lab;
    public Equipment(String name, String description, int id, Laboratory lab){
        this.name=name;
        this.description=description;
        this.id=id;
        this.lab=lab;
    }
    public void AssignLaboratory(Laboratory lab){
        this.lab=lab;

    }
}
