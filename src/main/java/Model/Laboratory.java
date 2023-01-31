package Model;

public class Laboratory {
    public Laboratory(String name, String topic, Employee Sresp, Project project){
        this.name=name;
        this.topic=topic;
        this.Sresp=Sresp;
        this.project=project;
    }
    public Laboratory(String name, String topic){
        this.name=name;
        this.topic=topic;
        Sresp=null;
        project=null;
    }
    private String name, topic;
    private int numEmployees;
    private Employee Sresp;
    private Project project;
    public void addScientificResponsible(Employee employee){
            if(employee.getRole().equals("Senior")){
                Sresp = employee;
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
        return project.getName();
        }
        public String getSrespSSN(){ return Sresp.getSSN();}
    public void setSresp(Employee employee){
        Sresp=employee;
    }
    public void setProject(Project project){
        this.project=project;
    }
}
