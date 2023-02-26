package Model;

import java.time.LocalDate;
import java.time.Period;

public class Employee {

    private String ssn, firstName, lastName, phoneNum, email, address;
    private LocalDate employmentDate;
    private float salary;
    private Laboratory lab;

    /**
     * Costruttore della classe Employee. Riceve in input i dati necessari per la creazione dell'oggetto Employee e
     * inizializza i suoi attributi. employmentDate viene inizializzato alla data attuale.
     *
     * @param ssn         Il Social Security Number dell'impiegato
     * @param firstName   Il nome dell'impiegato
     * @param lastName    Il cognome dell'impiegato
     * @param phoneNum    Il numero di telefono dell'impiegato
     * @param salary      Il salario iniziale dell'impiegato
     */
    public Employee(String ssn, String firstName, String lastName, String phoneNum, float salary) {
        this.ssn = ssn;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNum = phoneNum;
        this.employmentDate = LocalDate.now(); //setta la data di assunzione dell'impiegato con la data attuale
        this.salary = salary;
        email = null;
        address = null;
        lab = null;
    }

    /**
     *  Costruttore della classe Employee. Riceve in input i dati necessari per la creazione dell'oggetto Employee e
     *  inizializza i suoi attributi. Con employmentDate esplicito
     * @param ssn
     * @param firstName
     * @param lastName
     * @param phoneNum
     * @param salary
     * @param employmentDate
     */
    public Employee(String ssn, String firstName, String lastName, String phoneNum, float salary, LocalDate employmentDate) {
        this.ssn = ssn;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNum = phoneNum;
        this.employmentDate = employmentDate;
        this.salary = salary;
        email= null;
        address = null;
        lab=null;
    }

    /**
     * Metodo che restituisce l'intervallo di tempo tra l'assunzione dell'impiegato e la data attuale.
     *
     * @return oggetto Period che contiene l'intervallo di tempo calcolato
     */
    public Period getEmploymentTime() {
        return Period.between(employmentDate, LocalDate.now());
    }

    /**
     * Metodo che restituisce il laboratorio in cui lavora l'impiegato
     * @return Riferimento all'oggetto laboratorio memorizzato nell'attributo lab
     */
    public Laboratory getLab(){
        return lab;
    }

    /**
     * Metodo che restituisce il nome del laboratorio dell'impiegato se esso esiste, altrimenti restituisce null
     * @return Il nome del laboratorio oppure null
     */
    public String getLabName() {
        if (lab != null) return lab.getName();
        else return null;
    }

    /**
     * Metodo che restituisce il nome dell'impiegato
     * @return Nome dell'impiegato
     */
    public String getFirstName(){
        return firstName;
    }

    /**
     * Metodo che restituisce il cognome dell'impiegato
     * @return Cognome dell'impiegato
     */
    public String getLastName(){
        return lastName;
    }

    /**
     * Metodo che restituisce il Social Security Number dell'impiegato
     * @return Ssn dell'impiegato
     */
    public String getSSN(){
        return  ssn;
    }

    /**
     * Metodo che restituisce il numero di telefono dell'impiegato
     * @return Numero di telefono dell'impiegato
     */
    public String getPhoneNum(){
        return phoneNum;
    }

    /**
     * Metodo che restituisce l'indirizzo email dell'impiegato
     * @return Indirizzo email dell'impiegato
     */
    public String getEmail(){
        return email;
    }

    /**
     * Metodo che restituisce l'indirizzo stradale del domicilio dell'impiegato
     * @return Indirizzo dell'impiegato
     */
    public String getAddress(){
        return address;
    }

    /**
     * Metodo che restituisce il salario dell'impiegato
     * @return Salario dell'impiegato
     */
    public float getSalary(){
        return salary;
    }

    /**
     * Metodo che restituisce la data di assunzione dell'impiegato
     * @return Data di assunzione dell'impiegato
     */
    public LocalDate getEmploymentDate(){
        return employmentDate;
    }

    /**
     * Metodo che aggiorna il nome del laboratorio per cui lavora l'impiegato
     * @param lab Nuovo nome di laboratorio
     */
    public void setLab(Laboratory lab){
        this.lab=lab;
    }

    /**
     * Metodo che aggiorna l'indirizzo dell'impiegato
     * @param address Nuovo indirizzo dell'impiegato
     */
    public void setAddress(String address){
        this.address = address;
    }

    /**
     * Metodo che aggiorna l'indirizzo email dell'impiegato
     * @param email Nuovo indirizzo email dell'impiegato
     */
    public void setEmail(String email){
        this.email = email;
    }

    /**
     * Metodo che aggiorna il nome dell'impiegato
     * @param firstName Nuovo nome dell'impiegato
     */
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    /**
     * Metodo che aggiorna il cognome dell'impiegato
     * @param lastName Nuovo cognome dell'impiegato
     */
    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    /**
     * Metodo che aggiorna il numero di telefono dell'impiegato
     * @param phoneNum Nuovo numero di telefono dell'impiegato
     */
    public void setPhoneNum(String phoneNum){
        this.phoneNum = phoneNum;
    }

    /**
     * Metodo che aggiorna il salario dell'impiegato
     * @param salary Nuovo salario dell'impiegato
     */
    public void setSalary(float salary){
        this.salary = salary;
    }
}
