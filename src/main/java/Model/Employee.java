package Model;

import GUI.LaboratoryListController;
import javafx.collections.ObservableArray;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;


public class Employee {
    private String ssn, firstName, lastName, phoneNum, email="Null", address="Null", role, labName="Null";
    LocalDate employmentDate;
    float salary;
    private Laboratory lab;
ArrayList<CareerDevelopment> careerChanges = new ArrayList<CareerDevelopment>();

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
    public Employee(String ssn, String firstName, String lastName, String phoneNum, String role, float salary) {
        this.ssn = ssn;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNum = phoneNum;
        this.employmentDate = LocalDate.now();
        this.role = role;
        this.salary = salary;
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
     * Inoltre il metodo registra il cambio di ruolo creando un oggetto CareerDeelopment {@link CareerDevelopment}
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

            careerChanges.add(new CareerDevelopment(this.role, role, ssn, this.salary-salary));
            this.role = role;
            this.salary = salary;
        }
        else {
            System.out.println("Cambio ruolo non consentito");
        }
    }

    /**
     * Metodo che restituisce il ruolo dell'impiegato
     * @return Ruolo attuale dell'impiegato
     */
    public String getRole(){
        return role;
    }
    public void assignLab(Laboratory lab){

        this.lab = lab;
        labName = lab.getName();
    }
    public Laboratory getLab(){
        return lab;
    }
    public String getLabName(){
        return labName;
    }
    public String getFirstName(){
        return firstName;
    }
    public String getLastName(){
        return lastName;
    }
    public String getSsn(){
        return  ssn;
    }
    public String getPhoneNum(){
        return phoneNum;
    }
    public String getEmail(){
        return email;
    }
    public String getAddress(){
        return address;
    }
    public  float getSalary(){
        return  salary;
    }
    public LocalDate getEmploymentDate(){
        return employmentDate;
    }
    public  void setLabName(String laboratory) {
            labName = laboratory;
    }
    public void setLab(Laboratory lab){
        this.lab=lab;
    }
    public void setAddress(String address){
        this.address=address;
    }
    public void setEmail(String email){
        this.email=email;
    }
    public void setFirstName(String firstName){
        this.firstName=firstName;
    }
    public  void setLastName(String lastName){
        this.lastName=lastName;
    }
    public  void setPhoneNum(String phoneNum){
        this.phoneNum=phoneNum;
    }
    public  void setRole(String role){
        this.role=role;
    }
    public void setSalary(float salary){
        this.salary=salary;
    }
    }

//new Employee("432","name", "surname","32423","Junior",123)
