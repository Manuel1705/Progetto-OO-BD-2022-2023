package Model;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;


public class Employee {
    private String ssn, firstName, lastName, phoneNum, role, email, address;
    private LocalDate employmentDate;
    private float salary;
    private Laboratory lab; //laboratorio dove lavora l'impiegato

    /**
     * Costruttore della classe Employee. Riceve in input i dati necessari per la creazione dell'oggetto Employee e inizializza i suoi attributi.
     *
     * @param ssn         Il Social Security Number dell'impiegato
     * @param firstName   Il nome dell'impiegato
     * @param lastName    Il cognome dell'impiegato
     * @param phoneNum    Il numero di telefono dell'impiegato
     * @param role        Il ruolo iniziale dell'impiegato. I valori previsti sono "Junior", "Middle", "Senior", "Executive" e "Temporary"
     * @param salary      Il salario iniziale dell'impiegato
     */
    //costruttore con employmentDate automatico
    public Employee(String ssn, String firstName, String lastName, String phoneNum, String role, float salary) {
        this.ssn = ssn;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNum = phoneNum;
        this.employmentDate = LocalDate.now(); //setta la data di assunzione dell'impiegato con la data attuale
        this.role = role;
        this.salary = salary;
        email = null;
        address = null;
        lab = null;
    }
    //costruttore con employmentDate esplicito
    public Employee(String ssn, String firstName, String lastName, String phoneNum, String role, float salary, LocalDate employmentDate) {
        this.ssn = ssn;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNum = phoneNum;
        this.employmentDate = employmentDate;
        this.role = role;
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
     * Metodo che permette di aggiornare il ruolo dell'impiegato e modificarne il salario.
     * Se il nuovo ruolo e' legato al numero di anni di servizio dell'impiegato, la modifica viene fatta solo se valida.
     * Inoltre il metodo registra il cambio di ruolo creando un oggetto CareerDevelopment {@link CareerDevelopment}
     * e lo inserisce nell'ArrayList careerChanges dell'impiegato.
     *
     * @param role    Il nuovo ruolo dell'impiegato
     * @param salary  Il nuovo salario dell'impiegato
     */
    public void ChangeRole(String role, float salary) {
        if (role.equals("Junior") && getEmploymentTime().getYears() < 3 ||
            role.equals("Middle") && getEmploymentTime().getYears() >= 3 && getEmploymentTime().getYears() < 7 ||
            role.equals("Senior") && getEmploymentTime().getYears() >= 7 ||
            role.equals("Executive"))
        {
            this.role = role;
            this.salary = salary;
        }
        else {
            System.out.println("Cambio ruolo non consentito");
        }
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
     * Metodo che aggiorna il laboratorio in cui lavora l'impiegato
     * @param lab Riferimento all'oggetto laboratorio che corrisponde al nuovo laboratorio dell'impiegato
     */
    public void assignLab(Laboratory lab){
        this.lab = lab;
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
    public String getLabName(){
        if(lab != null) return lab.getName();
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
     * Metodo che aggiorna il ruolo dell'impiegato
     * @param role Nuovo ruolo dell'impiegato
     */
    public void setRole(String role){
        this.role = role;
    }
    
    /**
     * Metodo che aggiorna il salario dell'impiegato
     * @param salary Nuovo salario dell'impiegato
     */
    public void setSalary(float salary){
        this.salary = salary;
    }
}

//new Employee("432","name", "surname","32423","Junior",123)
