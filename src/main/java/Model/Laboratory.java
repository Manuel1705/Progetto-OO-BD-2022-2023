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
        if(project !=null){
            return project.getName();
        }
        else return "Null";
    }
    public String getProjectCup(){
        if(project !=null){
            return project.getCup();
        }
        else return "Null";
    }
        public String getSrespSSN(){
        if (Sresp!=null){
            return Sresp.getSSN();
        }else return "Null";
    }
    public void setSresp(Employee employee){
        Sresp=employee;
    }
    public void setProject(Project project){
        this.project=project;
    }
    public void setTopic(String topic){this.topic=topic;}
}
