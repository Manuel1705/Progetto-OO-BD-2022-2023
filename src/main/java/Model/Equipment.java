package Model;

import java.time.LocalDate;

public class Equipment {
    private int id;
    private String name, description, dealer;
    private Laboratory lab;
    private Project project;
    private float price;
    private LocalDate purchaseDate;
    
    /**
     * Costruttore della classe Equipment. Riceve in input i dati necessari per la creazione dell'oggetto Equipment e inizializza i suoi attributi.
     * @param id        Identificatore dell'equipaggiamento
     * @param name      Nome dell'equipaggiamento
     * @param price     Prezzo dell'equipaggiamento
     * @param project   Il progetto per cui è richiesto quell'equipaggiamento
     * @param lab       Il laboratorio che ha acquistato tale equipaggiamento
     * @param dealer    Colui che ha venduto l'equipaggiamento
    */
    public Equipment(int id, String name, float price, Project project,Laboratory lab, String dealer){
        this.name=name;
        this.id=id;
        this.lab=lab;
        this.dealer=dealer;
        this.price=price;
        purchaseDate = LocalDate.now(); //la purchaseDate quando viene creato un nuovo oggetto Equipment, è quella attuale
        this.project = project;
    }
    
    /**
     * Costruttore di Equipment con inizializzazione di project e lab a null.
     * @param id        Identificatore dell'equipaggiamento
     * @param name      Nome dell'equipaggiamento
     * @param price     Prezzo dell'equipaggiamento
     * @param dealer    Colui che ha venduto l'equipaggiamento
    */
    public Equipment(int id, String name, float price, String dealer){
        this.name=name;
        this.id=id;
        lab=null;
        this.dealer=dealer;
        this.price=price;
        purchaseDate=LocalDate.now(); //la data in cui viene acquistato un nuovo equipment è quella attuale
        project=null;
    }
    

    
    /**
     * Metodo che restituisce il nome dell'equipaggiamento
     * @return Il nome dell'equipaggiamento
    */
    public String getName(){return name;}
    
    /**
     * Metodo che restituisce il codice identificativo, se esiste, del progetto per il quale l'equipaggiamento è stato richiesto. Altrimenti restituisce null.
     * @return La cup del progetto oppure Null
    */
    public String getProjectCup(){
        if(project != null)
            return project.getCup();
        else return null;
    }
    
    /**
     * Metodo che restituisce il nome del laboratorio, se esiste, che ha richiesto l'equipaggiamento. Altrimenti restituisce null.
     * @return Il nome del laboratorio oppure Null
    */
    public String getLabName(){
        if(lab != null)
            return lab.getName();
        else return null;

    }
    
    /**
     * Metodo che restituisce la descrizione dell'equipaggiamento
     * @return La descrizione dell'equipaggiamento
    */
    public String getDescription() {return description;}
    
    /**
     * Metodo che restituisce il venditore dell'equipaggiamento
     * @return Il dealer dell'equipaggiamento
    */
    public String getDealer(){ return dealer;}
    
    /**
     * Metodo che restituisce l'identificativo dell'equipaggiamento
     * @return L'id dell'equipaggiamento
    */
    public int getId(){return id;  }
    
    /**
     * Metodo che restituisce il prezzo dell'equipaggiamento
     * @return Il prezzo dell'equipaggiamento
    */
    public float getPrice(){return  price;  }
    
    /**
     * Metodo che restituisce la data in cui è stato acquistato l'equipaggiamento
     * @return Data di acquisto dell'equipaggiamento
    */
    public LocalDate getPurchaseDate(){return purchaseDate;  }
    
    /**
     * Metodo che aggiorna il laboratorio che ha richiesto tale equipaggiamento
     * @param lab Riferimento all'oggetto laboratorio
    */
    public void setLab(Laboratory lab){this.lab=lab;  }
    
    /**
     * Metodo che aggiorna il progetto per cui è stato richiesto l'equipaggiamento
     * @param project Riferimento all'oggetto progetto
    */
    public void setProject(Project project){this.project=project;  }
    
    /**
     * Metodo che aggiorna la descrizione di un equipaggiamento se essa è stata inserita, altrimenti la aggiorna con "No description".
     * @param description La descrizione dell'equipaggiamento
    */
    public void setDescription(String description){
        if (!description.isBlank()) {
            this.description = description;
        }else this.description = "No description";
    }
    
    /**
     * Metodo che aggiorna il nome dell'equipaggiamento
     * @param name Nuovo nome dell'equipaggiamento
    */
    public void setName(String name){
        this.name=name;
    }
}
