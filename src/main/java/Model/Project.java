package Model;

import java.time.LocalDate;

public class Project{
    private String cup, name;
    private float budget;
    private float remainingFunds;
    private LocalDate startDate, endDate;
    private CompanyEmployee Sresp;
    private CompanyEmployee Sref;
    
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
    public Project(String cup, String name, float budget, LocalDate startDate, LocalDate endDate, CompanyEmployee Sresp, CompanyEmployee Sref){
        this.cup=cup;
        this.name=name;
        this.budget=budget;
        remainingFunds=budget;
        this.startDate= startDate;
        this.endDate=endDate;
        this.Sresp=Sresp;
        this.Sref=Sref;
    }

    /**
     * Costruttore della classe Project. Riceve in input i dati necessari per la creazione dell'oggetto Project e inizializza i suoi attributi.
     * @param cup       Codice che identifica il progetto
     * @param name      Nome del progetto
     * @param budget    Soldi stanziati per il progetto
     * @param remainingFunds Fondi rimanenti
     * @param startDate Data di inizio del progetto
     * @param endDate   Data di termine del progetto
     * @param Sresp     È l'impiegato che viene scelto come responsabile scientifico del progetto
     * @param Sref      È l'impiegato che viene scelto come referente scientifico del progetto
     */
    public Project(String cup, String name, float budget, float remainingFunds, LocalDate startDate, LocalDate endDate, CompanyEmployee Sresp, CompanyEmployee Sref){
        this.cup=cup;
        this.name=name;
        this.budget=budget;
        this.remainingFunds=remainingFunds;
        this.startDate= startDate;
        this.endDate=endDate;
        this.Sresp=Sresp;
        this.Sref=Sref;
    }






    /**
     * Metodo che restituisce true se il progetto e' terminato, false altrimenti.
     * @return
     */
    public Boolean isExpired(){
        if(endDate.isBefore(LocalDate.now())) return true;
        else return false;
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
    public CompanyEmployee getSref() {return Sref;}
    
    /**
     * Metodo che restituisce il responsabile scientifico del progetto
     * @return Il riferimento all'oggetto Employee memorizzato nell'attributo Sresp
    */
    public CompanyEmployee getSResp(){return Sresp; }
    
    /**
     * Metodo che restituisce l'SSN del referente scientifico del progetto se esso esiste, altrimenti restituisce un messaggio
     * @return L'SSN dell'attuale referente scientifico del progetto oppure null.
    */
    public String getSrefSSN(){
        if(Sref == null)
            return null;
        return Sref.getSSN();
    }
    
    /**
     * Metodo che restituisce l'SSN del responsabile scientifico del progetto se esso esiste, altrimenti restituisce un messaggio
     * @return L'SSN dell'attuale responsabile scientifico del progetto oppure null.
    */
    public String getSrespSSN(){
        if(Sresp == null)
            return null;
        return Sresp.getSSN();
    }
    
    /**
     * Metodo che restituisce il codice identificativo del progetto
     * @return La cup del progetto
    */
    public String getCup(){return cup;}
    
    /**
     * Metodo che restituisce il budget del progetto
     * @return Il budget del progetto
    */
    public float getBudget(){return budget;}
    
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
    public void setSresp(CompanyEmployee employee){Sresp=employee;  }
    
    /**
     * Metodo che aggiorna il referente scientifico del progetto
     * @param employee Riferimento all'oggetto impiegato (colui che vogliamo sia il nuovo referente scientifico)
    */
    public void setSref(CompanyEmployee employee){Sref=employee;  }
    
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
