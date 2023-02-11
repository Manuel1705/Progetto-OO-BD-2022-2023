package Model;

public class Laboratory {
    private String name, topic;
    private int numEmployees;
    private Employee Sresp;
    private Project project;
    
    /**
     * Costruttore della classe Laboratory. Riceve in input i dati necessari per la creazione dell'oggetto Laboratory e inizializza i suoi attributi.
     * @param name      Nome del laboratorio
     * @param topic     Argomento di studio del laboratorio
     * @param Sresp     È l'impiegato che viene scelto come responsabile scientifico del laboratorio
     * @param project   È il progetto di cui si occuperà il laboratorio
     */
    public Laboratory(String name, String topic, Employee Sresp, Project project){
        this.name=name;
        this.topic=topic;
        this.Sresp=Sresp;
        this.project=project;
    }
    
    /**
     * Costruttore della classe Laboratory con inizializzazione del responsabile di laboratorio e del progetto a null
     * @param name      Nome del laboratorio
     * @param topic     Argomento di studio del laboratorio
     */
    public Laboratory(String name, String topic){
        this.name=name;
        this.topic=topic;
        Sresp=null;
        project=null;
    }
    
    /**
     * Metodo che inizializza Sresp ma solo se l'impiegato ha ruolo "Senior".
     * @param employee Impiegato scelto come responsabile scientifico
     */
    public void addScientificResponsible(Employee employee){
            if(employee.getRole().equals("Senior")){
                Sresp = employee;
            }
    }
    
    /**
     * Metodo che associa un progetto a un laboratorio ma solo se tale progetto non ha già tre laboratori che si occupano di esso.
     * @param project Cup del progetto che vogliamo aggiungere
     */
    public void addProject(Project project){
        if(project.getLabsNum()<3) {
            this.project = project;
            project.addLaboratory(this);
        }else {
            System.out.println("Questo progetto ha già tre laboratori");
        }
     }
    
     /**
     * Metodo che restituisce il nome del laboratorio
     * @return Il nome del laboratorio
     */
     public String getName(){
            return name;
     }
    
     /**
     * Metodo che restituisce l'argomento di cui tratta il laboratorio
     * @return Argomento del laboratorio
     */
     public String getTopic(){
        return topic;
     }
    
    /**
     * Metodo che restituisce il nome del progetto di cui si occupa il laboratorio se esso esiste, altrimenti restituisce null
     * @return Nome del progetto del laboratorio oppure Null
     */
     public String getProjectName(){
        if(project !=null){
            return project.getName();
        }
        else return "Null";
     }
     
     /**
     * Metodo che restituisce la Cup del progetto di cui di occupa il laboratorio se esso esiste, altrimenti restituisce null
     * @return Cup del progetto oppure Null
     */
     public String getProjectCup(){
        if(project !=null){
            return project.getCup();
        }
        else return "Null";
     }
     
     /**
     * Metodo che restituisce l'SSN del responsabile scientifico del laboratorio se esso esiste, altrimenti restituisce null
     * @return SSN del responsabile scientifico oppure Null
     */
     public String getSrespSSN(){
        if (Sresp!=null){
            return Sresp.getSSN();
        }else return "Null";
     }
    
    /**
     * Metodo che aggiorna il responsabile scientifico del laboratorio
     * @param employee SSN dell'impiegato che vogliamo sia il nuovo responsabile scientifico
     */
    public void setSresp(Employee employee){
        Sresp=employee;
    }
    
    /**
     * Metodo che aggiorna il progetto di cui si occupa il laboratorio
     * @param project Cup del nuovo progetto
     */
    public void setProject(Project project){
        this.project=project;
    }
    
    /**
     * Metodo che aggiorna l'Argomento di cui si occupa il laboratorio
     * @param topic Nuovo argomento del laboratorio
     */
    public void setTopic(String topic){this.topic=topic;}
}
