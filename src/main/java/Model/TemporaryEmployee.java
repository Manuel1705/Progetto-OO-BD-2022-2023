package Model;

import java.time.LocalDate;
import java.time.Period;



public class TemporaryEmployee extends Employee{
    private Project project;

    /**
     * Costruttore della classe TemporaryEmployee. Riceve in input i dati necessari per la creazione dell'oggetto
     * TemporaryEmployee e inizializza i suoi attributi.
     * @param ssn           Il Social Security Number del temporary employee
     * @param firstName     Il nome dell'impiegato
     * @param lastName      Il cognome dell'impiegato
     * @param phoneNum      Il recapito telefonico dell'impiegato
     * @param salary        Il salario iniziale dell'impiegato
     * @param project       Il progetto per cui lavora l'impiegato temporaneo
     */

    /**
     * Costruttore della classe TemporaryEmployee. Riceve in input i dati necessari per la creazione dell'oggetto
     * TemporaryEmployee e inizializza i suoi attributi. Con employment date esplicito.
     * @param ssn           Il Social Security Number del temporary employee
     * @param firstName     Il nome dell'impiegato
     * @param lastName      Il cognome dell'impiegato
     * @param phoneNum      Il recapito telefonico dell'impiegato
     * @param salary        Il salario iniziale dell'impiegato
     * @param employmentDate La data di assunzione dell'impiegato.
     * @param project       Il progetto per cui lavora l'impiegato temporaneo
     */
    public TemporaryEmployee(String ssn, String firstName, String lastName, String phoneNum, float salary, LocalDate employmentDate,Project project) {
        super(ssn, firstName, lastName, phoneNum, salary, employmentDate);
        this.project=project;
    }


    /**
     * Metodo che restituisce il codice identificativo del progetto per cui lavora l'impiegato temporaneo
     * @return La cup del progetto
     */
    public String getProjectCup(){
     return project.getCup();
    }

    /**
     * Metodo che setta il valore di project con quello del parametro passato in input.
     * @param project
     */
    public void setProject(Project project){
        this.project=project;
    }


}

