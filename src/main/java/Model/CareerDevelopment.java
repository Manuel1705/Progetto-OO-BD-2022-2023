package Model;

import java.time.LocalDate;

public class CareerDevelopment {
    private String oldRole, newRole;
    private LocalDate date;
    private float salaryChange;
    private CompanyEmployee employee;
    private float oldSalary, newSalary;
    
    /**
     * Costruttore della classe CareerDevelopment. Riceve in input i dati necessari per la creazione dell'oggetto CareerDevelopment e inizializza i suoi attributi.
     * @param employee          Riferimento all'oggetto impiegato interessato allo scatto di carriera
     * @param oldRole           Vecchio ruolo ricoperto dall'impiegato
     * @param newRole           Nuovo ruolo ricoperto dall'impiegato
     * @param salaryChange      Differenza di salario da aggiungere al vecchio salario dell'impiegato
    */
    public CareerDevelopment(CompanyEmployee employee, String oldRole, String newRole, float salaryChange ){
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

    public LocalDate getDate(){
        return date;
    }
}
