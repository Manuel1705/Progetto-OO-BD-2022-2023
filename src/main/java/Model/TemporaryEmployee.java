package Model;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;


public class TemporaryEmployee {
    private String ssn, firstName, lastName, phoneNum, email, address;
    LocalDate employmentDate;
    float salary;
    private Laboratory lab;
    private Project project;

    /**
     * Costruttore della classe TemporaryEmployee. Riceve in input i dati necessari per la creazione dell'oggetto TemporaryEmployee e inizializza i suoi attributi.
     * @param ssn           Il Social Security Number del temporary employee
     * @param firstName     Il nome dell'impiegato
     * @param lastName      Il cognome dell'impiegato
     * @param phoneNum      Il recapito telefonico dell'impiegato
     * @param salary        Il salario iniziale dell'impiegato
     * @param project       Il progetto per cui lavora l'impiegato temporaneo
     */
    public TemporaryEmployee(String ssn, String firstName, String lastName, String phoneNum, float salary,Project project) {
        this.ssn = ssn;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNum = phoneNum;
        this.employmentDate = LocalDate.now(); //setta la data di assunzione dell'impiegato con la data attuale
        this.salary = salary;
        email = null;
        address= null;
        lab= null;
        this.project=project;

    }
    //costruttore con employmentDate esplicito
    public TemporaryEmployee(String ssn, String firstName, String lastName, String phoneNum, float salary, LocalDate employmentDate,Project project) {
        this.ssn = ssn;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNum = phoneNum;
        this.employmentDate = employmentDate;
        this.salary = salary;
        email= null;
        address= null;
        lab=null;
        this.project=project;
    }

    /**
     * Metodo che restituisce il periodo di tempo lavorativo di un impiegato
     * @return Il numero di anni, mesi e giorni lavorativi dell'impiegato
     */
    public Period getTemporaryEmploymentTime() {
        return Period.between(employmentDate, LocalDate.now());
    }

    /**
     * Metodo che assegna il laboratorio per cui lavora l'impiegato
     * @param lab Riferimento all'oggetto laboratorio per cui vogliamo che l'impiegato temporaneo lavori
     */
    public void assignLab(Laboratory lab){
        this.lab = lab;
    }

    /**
     * Metodo che restituisce il laboratorio per cui lavora l'impiegato temporaneo
     * @return Il riferimento all'oggetto Laboratory memorizzato nell'attributo lab
     */
    public Laboratory getLab(){
        return lab;
    }

    /**
     * Metodo che restituisce il nome del laboratorio per cui lavora l'impiegato temporaneo, se esso esiste. Altrimenti restituisce null.
     * @return Il nome del laboratorio oppure Null.
     */
    public String getLabName(){
        return lab.getName();
    }

    /**
     * Metodo che restituisce il codice identificativo del progetto per cui lavora l'impiegato temporaneo
     * @return La cup del progetto
     */
    public String getProjectCup(){
     return project.getCup();
    }

    /**
     * Metodo che restituisce il nome dell'impiegato temporaneo
     * @return Il nome dell'impiegato temporaneo
     */
    public String getFirstName(){
        return firstName;
    }

    /**
     * Metodo che restituisce il cognome dell'impiegato temporaneo
     * @return Il cognome del temporary employee
     */
    public String getLastName(){
        return lastName;
    }

    /**
     * Metodo che restituisce il Social Security Number dell'impiegato temporaneo
     * @return L'SSN dell'impiegato temporaneo
     */
    public String getSSN(){
        return  ssn;
    }

    /**
     * Metodo che restituisce il recapito telefonico dell'impiegato temporaneo
     * @return Il numero di telefono dell'impiegato temporaneo
     */
    public String getPhoneNum(){
        return phoneNum;
    }

    /**
     * Metodo che restituisce l'email dell'impiegato temporaneo
     * @return L'email dell'impiegato temporaneo
     */
    public String getEmail(){
        return email;
    }

    /**
     * Metodo che restituisce l'indirizzo di residenza dell'impiegato temporaneo
     * @return L'indirizzo di residenza dell'impiegato temporaneo
     */
    public String getAddress(){
        return address;
    }

    /**
     * Metodo che restituisce il salario dell'impiegato temporaneo
     * @return Il salario attuale dell'impiegato temporaneo
     */
    public float getSalary(){
        return salary;
    }

    /**
     * Metodo che restituisce la data di assunzione dell'impiegato temporaneo
     * @return Data di assunzione dell'impiegato temporaneo
     */
    public LocalDate getEmploymentDate(){
        return employmentDate;
    }

    /**
     * Metodo che aggiorna il laboratorio per cui lavora l'impiegato
     * @param lab Riferimento all'oggetto laboratorio per cui vogliamo che l'impiegato temporaneo lavori
     */
    public void setLab(Laboratory lab){
        this.lab=lab;
    }

    /**
     * Metodo che aggiorna il progetto per cui vogliamo che l'impiegato temporaneo lavori
     * @param project Il progetto per cui vogliamo che l'impiegato temporaneo lavori
     */
    public void setProject(Project project){
        this.project=project;
    }

    /**
     * Metodo che aggiorna l'indirizzo di residenza dell'impiegato temporaneo
     * @param address Il nuovo indirizzo di residenza dell'impiegato temporaneo
     */
    public void setAddress(String address){
        this.address = address;
    }

    /**
     * Metodo che aggiorna l'email di residenza dell'impiegato temporaneo
     * @param email La nuova email dell'impiegato temporaneo
     */
    public void setEmail(String email){
        this.email = email;
    }

    /**
     * Metodo che aggiorna il nome dell'impiegato temporaneo
     * @param firstName Il nuovo nome del'impiegato temporaneo
     */
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    /**
     * Metodo che aggiorna il cognome dell'impiegato temporaneo
     * @param lastName Il nuovo cognome del'impiegato temporaneo
     */
    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    /**
     * Metodo che aggiorna il recapito telefonico dell'impiegato temporaneo
     * @param phoneNum Il nuovo recapito telefonico dell'impiegato temporaneo
     */
    public void setPhoneNum(String phoneNum){
        this.phoneNum = phoneNum;
    }

    /**
     * Metodo che aggiorna il salario dell'impiegato temporaneo
     * @param salary Il nuovo salario dell'impiegato temporaneo
     */
    public void setSalary(float salary){
        this.salary = salary;
    }
}

