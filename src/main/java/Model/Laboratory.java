package Model;

public class Laboratory {
    public Laboratory(String name, String topic, Employee scientificResponsible, Project project){
        this.name=name;
        this.topic=topic;
        this.scientificResponsible=scientificResponsible;
        Sresp=scientificResponsible.getSsn();
        this.project=project;
        projectName=project.getName();
    }
    private String name="NULL",
            topic,
            projectName="Null",
            Sresp="NULL";
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
        public String getTopic(){
        return topic;
        }
        public String getProjectName(){
        return projectName;
        }
        public String getSresp(){
        return Sresp;
        }
}
