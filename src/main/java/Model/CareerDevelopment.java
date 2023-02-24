package Model;

import java.time.LocalDate;

public class CareerDevelopment {
    private String oldRole, newRole;
    private LocalDate date;
    private float salaryChange;
    private Employee employee;
    private float oldSalary, newSalary;
    
    /**
     * Costruttore della classe CareerDevelopment. Riceve in input i dati necessari per la creazione dell'oggetto CareerDevelopment e inizializza i suoi attributi.
     * @param employee          Riferimento all'oggetto impiegato interessato allo scatto di carriera
     * @param oldRole           Vecchio ruolo ricoperto dall'impiegato
     * @param newRole           Nuovo ruolo ricoperto dall'impiegato
     * @param salaryChange      Differenza di salario da aggiungere al vecchio salario dell'impiegato
    */
    public CareerDevelopment(Employee employee, String oldRole, String newRole, float salaryChange ){
        this.employee=employee;
            this.oldRole=oldRole;
            this.newRole=newRole;
            date=LocalDate.now(); //quando avviene uno scatto di carriera la data viene inizializzata con la data attuale
            this.salaryChange=salaryChange;
            this.oldSalary=employee.getSalary();
            this.newSalary=employee.getSalary()+salaryChange;
    }
    
    /**
     * Metodo che restituisce l'SSN dell'impiegato interessato dallo scatto di carriera
     * @return L'SSN dell'impiegato
    */
    public String getSSN(){
        return employee.getSSN();
    }
    
    /**
     * Metodo che restituisce il Nome dell'impiegato interessato dallo scatto di carriera
     * @return Il nome dell'impiegato
    */
    public String getFirstName(){
        return employee.getFirstName();
    }
    
    /**
     * Metodo che restituisce il cognome dell'impiegato interessato dallo scatto di carriera
     * @return Il cognome dell'impiegato
    */
    public String getLastName(){
        return employee.getLastName();
    }
    
    /**
     * Metodo che restituisce il vecchio ruolo dell'impiegato interessato dallo scatto di carriera
     * @return Il vecchio ruolo dell'impiegato
    */
    public String getOldRole(){
        return oldRole;
    }
    
    /**
     * Metodo che restituisce il nuovo ruolo dell'impiegato interessato dallo scatto di carriera
     * @return Il ruolo attuale dell'impiegato
    */
    public String getNewRole(){
        return newRole;
    }
    
    /**
     * Metodo che restituisce il vecchio salario dell'impiegato interessato dallo scatto di carriera
     * @return Il valore del precedente salario retribuito all'impiegato
    */
    public float getOldSalary(){ return oldSalary; }
    
    /**
     * Metodo che restituisce il nuovo salario dell'impiegato interessato dallo scatto di carriera
     * @return Il salario attuale dell'impiegato
    */
    public float getNewSalary(){
        return newSalary;
    }
    
    /**
     * Metodo che restituisce la data in cui è avvenuto lo scatto di carriera dell'impiegato
     * @return La data dello scatto di carriera
    */
    public LocalDate getDate(){
        return date;
    }
}
