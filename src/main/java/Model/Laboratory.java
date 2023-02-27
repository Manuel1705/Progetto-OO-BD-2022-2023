package Model;

public class Laboratory {
    private String name, topic;
    private CompanyEmployee Sresp;
    private Project project;
    
    /**
     * Costruttore della classe Laboratory. Riceve in input i dati necessari per la creazione dell'oggetto Laboratory e inizializza i suoi attributi.
     * @param name      Nome del laboratorio
     * @param topic     Argomento di studio del laboratorio
     * @param Sresp     È l'impiegato che viene scelto come responsabile scientifico del laboratorio
     * @param project   È il progetto di cui si occuperà il laboratorio
     */
    public Laboratory(String name, String topic, CompanyEmployee Sresp, Project project){
        this.name=name;
        this.topic=topic;
        this.Sresp=Sresp;
        this.project=project;
    }
    
    /**
     * Costruttore della classe Laboratory con inizializzazione del responsabile di laboratorio e del progetto a null.
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
     * Metodo che restituisce il nome del laboratorio
     * @return Il nome del laboratorio
     */
     public String getName(){
            return name;
     }
    
     /**
     * Metodo che restituisce l'argomento di cui tratta il laboratorio
     * @return L'argomento del laboratorio
     */
     public String getTopic(){
        return topic;
     }
    

     /**
     * Metodo che restituisce la Cup del progetto di cui di occupa il laboratorio se esso esiste, altrimenti restituisce null
     * @return La cup del progetto oppure Null
     */
     public String getProjectCup(){
        if(project !=null){
            return project.getCup();
        }
        else return null;
     }
     
     /**
     * Metodo che restituisce l'SSN del responsabile scientifico del laboratorio se esso esiste, altrimenti restituisce null
     * @return L'SSN del responsabile scientifico oppure Null
     */
     public String getSrespSSN(){
        if (Sresp!=null){
            return Sresp.getSSN();
        }else return null;
     }
    
    /**
     * Metodo che aggiorna il responsabile scientifico del laboratorio
     * @param employee Riferimento all'oggetto impiegato che vogliamo sia il nuovo responsabile scientifico
     */
    public void setSresp(CompanyEmployee employee){
        Sresp=employee;
    }
    
    /**
     * Metodo che aggiorna il progetto di cui si occupa il laboratorio
     * @param project Riferimento all'oggetto progetto 
     */
    public void setProject(Project project){
        this.project=project;
    }
    
    /**
     * Metodo che aggiorna l'argomento di cui si occupa il laboratorio
     * @param topic Nuovo argomento del laboratorio
     */
    public void setTopic(String topic){this.topic=topic;}
}
