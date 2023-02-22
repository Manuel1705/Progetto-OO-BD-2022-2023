package Model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Project{
    private String cup, name;
    private float budget, remainingFunds;
    private LocalDate startDate, endDate;
    private ArrayList<Laboratory> labs = new ArrayList<>(); //lista dei laboratori che lavorano a un determinato progetto
    private ArrayList<Employee> temporary = new ArrayList<>(); //lista degli impiegati temporanei che lavorano a un progetto
    private Employee Sresp;
    private Employee Sref;
    private ArrayList<Purchase> purchases = new ArrayList<>(); //lista degli acquisti compiuti per un determinato progetto
    
    /**
     * Costruttore della classe Project. Riceve in input i dati necessari per la creazione dell'oggetto Project e inizializza i suoi attributi.
     * @param cup       Codice che identifica il progetto
     * @param name      Nome del progetto
     * @param budget    Soldi stanziati per il progetto
     * @param startDate Data di inizio del progetto
     * @param endDate   Data di termine del progetto
     * @param Sresp     È l'impiegato che viene scelto come responsabile scientifico del progetto
     * @param Sref      È l'impiegato che viene scelto come referente scientifico del progetto
     */
    public Project(String cup, String name, float budget, LocalDate startDate, LocalDate endDate, Employee Sresp,Employee Sref){
        this.cup=cup;
        this.name=name;
        this.budget=budget;
        remainingFunds=budget;
        this.startDate= startDate;
        this.endDate=endDate;
        this.Sresp=Sresp;
        this.Sref=Sref;
    }
    

    //public void PurchaseEquipment(String name, String description, int id, Laboratory lab, float price, String dealer){
      //      purchases.add(new Purchase(price,LocalDate.now(),dealer,new Equipment(,name, description, id, lab)));
    //}
    
    /**
     * Metodo che permette l'assunzione di un nuovo impiegato temporaneo al progetto.
     * @param ssn           SSN del nuovo impiegato temporaneo
     * @param firstName     Nome del nuovo impiegato temporaneo
     * @param lastName      Cognome del nuovo impiegato temporaneo
     * @param phoneNum      Recapito telefonico del nuovo impiegato temporaneo
     * @param salary        Salario del nuovo impiegato temporaneo
    */
    public void HireTemporaryEmployee(String ssn, String firstName, String lastName, String phoneNum, float salary){
            temporary.add(new Employee(ssn, firstName, lastName, phoneNum, "Temporary", salary));
    }
    
    /**
     * Metodo che permette di assegnare un responsabile scientifico a un progetto.
     * Il responsabile scientifico deve avere ruolo "Executive".
     * @param ssn           SSN dell'impiegato che sarà il responsabile scientifico del progetto
     * @param firstName     Nome dell'impiegato scelto come responsabile scientifico
     * @param lastName      Cognome dell'impiegato scelto come responsabile scientifico
     * @param phoneNum      Recapito telefonico del responsabile scientifico
     * @param salary        Salario del responsabile scientifico
    */
    public void addScientificResponsible(String ssn, String firstName, String lastName, String phoneNum, float salary){
            Sresp = new Employee(ssn, firstName, lastName, phoneNum, "Executive", salary);
    }
    
    /**
     * Metodo che permette di assegnare un referente scientifico a un progetto.
     * Il referente scientifico deve avere ruolo "Senior".
     * @param ssn           SSN dell'impiegato che sarà il referente scientifico del progetto
     * @param firstName     Nome dell'impiegato scelto come referente scientifico
     * @param lastName      Cognome dell'impiegato scelto come referente scientifico
     * @param phoneNum      Recapito telefonico del referente scientifico
     * @param salary        Salario del referente scientifico
    */
    public void addReferent(String ssn, String firstName, String lastName, String phoneNum, float salary){
            Sref = new Employee(ssn, firstName, lastName, phoneNum, "Senior", salary);
    }
    
    /**
     * Metodo che restituisce il numero di laboratori che lavorano a un determinato progetto
     * @return Il numero di laboratori che lavorano a un progetto
    */
    public int getLabsNum(){
        return labs.size();
    }
    
    /**
     * Metodo che aggiunge un nuovo laboratorio a un progetto se quest'ultimo non ha già tre laboratori che lavorano su di esso.
     * @param lab Nome del laboratorio
    */
    public void addLaboratory(Laboratory lab){
        if(labs.size()<3)
            labs.add(lab);
    }
    
    /**
     * Metodo che restituisce il nome del progetto.
     * @return Il nome del progetto
    */
    public String getName(){return name; }
    
    /**
     * Metodo che restituisce il referente scientifico del progetto
     * @return Il riferimento all'oggetto Employee memorizzato nell'attributo Sref
    */
    public Employee getSref() {return Sref;}
    
    /**
     * Metodo che restituisce il responsabile scientifico del progetto
     * @return Il riferimento all'oggetto Employee memorizzato nell'attributo Sresp
    */
    public Employee getSResp(){return Sresp; }
    
    /**
     * Metodo che restituisce l'SSN del referente scientifico del progetto se esso esiste, altrimenti restituisce un messaggio
     * @return L'SSN dell'attuale referente scientifico del progetto oppure "Empty Position"
    */
    public String getSrefSSN(){
        if(Sref == null)
            return "Empty Position";
        return Sref.getSSN();
    }
    
    /**
     * Metodo che restituisce l'SSN del responsabile scientifico del progetto se esso esiste, altrimenti restituisce un messaggio
     * @return L'SSN dell'attuale responsabile scientifico del progetto oppure "Empty Position"
    */
    public String getSrespSSN(){
        if(Sresp == null)
            return "Empty Position";
        return Sresp.getSSN();
    }
    
    /**
     * Metodo che restituisce il codice identificativo del progetto
     * @return La cup del progetto
    */
    public String getCup(){return cup;  }
    
    /**
     * Metodo che restituisce il budget del progetto
     * @return Il budget del progetto
    */
    public float getBudget(){return budget;  }
    
    /**
     * Metodo che restituisce i fondi che non sono ancora stati spesi per il progetto
     * @return  L'esatto numero di fondi del progetto ancora a disposizione
    */
    public float getRemainingFunds(){return remainingFunds;  }
    
    /**
     * Metodo che restituisce la data in cui è stato avviato il progetto
     * @return La data d'inizio del progetto
    */
    public LocalDate getStartDate(){return startDate;  }
    
    /**
     * Metodo che restituisce la data in cui il progetto è stato terminato
     * @return La data di fine progetto
    */
    public LocalDate getEndDate(){return endDate;  }
    
    /**
     * Metodo che aggiorna il responsabile scientifico del progetto
     * @param employee Riferimento all'oggetto impiegato (colui che vogliamo sia il nuovo responsabile scientifico)
    */
    public void setSresp(Employee employee){Sresp=employee;  }
    
    /**
     * Metodo che aggiorna il referente scientifico del progetto
     * @param employee Riferimento all'oggetto impiegato (colui che vogliamo sia il nuovo referente scientifico)
    */
    public void setSref(Employee employee){Sref=employee;  }
    
    /**
     * Metodo che aggiorna il nome del progetto
     * @param name Nuovo nome del progetto
    */
    public void setName(String name){this.name=name; }
    
    /**
     * Metodo che aggiorna il budget a disposizione di un progetto
     * @param budget Nuovo valore del budget
    */
    public void setBudget(Float budget) {this.budget = budget; }
    
    /**
     * Metodo che aggiorna i fondi restanti di un progetto
     * @param remainingFunds Nuovo valore dei fondi rimanenti
    */
    public void setRemainingFunds(float remainingFunds){this.remainingFunds=remainingFunds; }
    
    /**
     * Metodo che aggiorna la data di fine del progetto
     * @param endDate Nuova data di termine del progetto
    */
    public void setEndDate(LocalDate endDate){this.endDate=endDate;}

}
