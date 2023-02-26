package Model;

import java.time.LocalDate;




public class CompanyEmployee extends Employee{
    private String role;

    /**
     * Costruttore della classe CompanyEmployee. Riceve in input i dati necessari per la creazione dell'oggetto Employee e
     * inizializza i suoi attributi. employmentDate viene inizializzato alla data attuale.
     *
     * @param ssn         Il Social Security Number dell'impiegato
     * @param firstName   Il nome dell'impiegato
     * @param lastName    Il cognome dell'impiegato
     * @param phoneNum    Il numero di telefono dell'impiegato
     * @param role        Il ruolo iniziale dell'impiegato. I valori previsti sono "Junior", "Middle", "Senior", "Executive" e "Temporary"
     * @param salary      Il salario iniziale dell'impiegato
     */
    public CompanyEmployee(String ssn, String firstName, String lastName, String phoneNum, String role, float salary) {
        super(ssn, firstName, lastName, phoneNum, salary);
        this.role = role;

    }

    /**
     *  Costruttore della classe CompanyEmployee. Riceve in input i dati necessari per la creazione dell'oggetto Employee e
     *  inizializza i suoi attributi. Con employmentDate esplicito
     * @param ssn
     * @param firstName
     * @param lastName
     * @param phoneNum
     * @param role
     * @param salary
     * @param employmentDate
     */
    public CompanyEmployee(String ssn, String firstName, String lastName, String phoneNum, String role, float salary, LocalDate employmentDate) {
        super(ssn, firstName, lastName, phoneNum, salary, employmentDate);
        this.role = role;

    }


    
    /**
     * Metodo che controlla la coerenza del ruolo di un impiegato con i suoi anni di servizio e se il ruolo non e' piu' valido,
     * restituisce il ruolo che l'impiegato dovrebbe assumere.
     * Un impiegato che lavora per l'azienda da più di tre anni, ma da meno di sette, assume ruolo "Middle".
     * Un impiegato che lavora per l'azienda da più di sette anni assume ruolo "Senior".
     */
    public String CheckRole(){
        if (getEmploymentTime().getYears()>=3 && getEmploymentTime().getYears()<7 &&
                !role.equals("Executive") &&!role.equals("Middle")){
            return "Middle";
        }
        if (getEmploymentTime().getYears()>=7 && !role.equals("Executive") &&!role.equals("Senior")){
            return "Senior";
        }
        return null;
    }

    /**
     * Metodo che restituisce il ruolo dell'impiegato
     * @return Ruolo attuale dell'impiegato
     */
    public String getRole(){
        return role;
    }
    

    /**
     * Metodo che aggiorna il ruolo dell'impiegato
     * @param role Nuovo ruolo dell'impiegato
     */
    public void setRole(String role){
        this.role = role;
    }
    

}

