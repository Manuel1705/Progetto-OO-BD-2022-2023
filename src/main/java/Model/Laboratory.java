package Model;

public class Laboratory {
    private String name="NULL", topic;
    private int numEmployees;
    private Employee scientificResponsible;
    private Project project;
    public void addScientificResponsible(Employee employee){
            if(employee.getRole().equals("Senior")){
                scientificResponsible = employee;
            }
    }
    public void addProject(Project project){
        if(project.getLabsNum()<3) {
            this.project = project;
            project.addLaboratory(this);
        }else {
            System.out.println("Questo progetto ha già tre laboratori");
        }
        }
        public String getName(){
        return name;
        }
}
