package Controller;
import DAOPostgresImplementation.DAOEmployeePostgres;
import DAOPostgresImplementation.DAOProjectPostgres;
import DAOPostgresImplementation.DAOTemporaryContractPostgres;
import Model.Project;
import Model.TemporaryEmployee;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
public class TemporaryEmployeeController {
    private Controller controller;
    private ArrayList<TemporaryEmployee> temporaryEmployeeArrayList= new ArrayList<TemporaryEmployee>();

    /**
     * Metodo che restituisce temporaryEmployeeArrayList
     * @return L'ArrayList.
     */
    public ArrayList<TemporaryEmployee> getTemporaryEmployeeArrayList(){ return temporaryEmployeeArrayList; }

    /**
     * Costruttore della classe che inizializza l'attributo controller.
     * @param controller Valore iniziale del controller
     */
    public TemporaryEmployeeController(Controller controller){
        this.controller = controller;
    }

    /**
     * Metodo che cerca l'impiegato temporaneo con l'ssn fornito e se esso esiste viene restituito. Altrimenti il metodo
     * restituisce null.
     * @param ssn   SSN dell'impiegato temporaneo che ci interessa cercare
     * @return Il riferimento all'oggetto TemporaryEmployee oppure null
     */
    public TemporaryEmployee findTemporaryEmployee(String ssn){
        for(TemporaryEmployee temporaryEmployee: temporaryEmployeeArrayList){
            if(temporaryEmployee.getSSN().equals(ssn)) return temporaryEmployee;
        }
        return null;
    }

    /**
     * Metodo che restituisce il totale dei salari da pagare ai dipendenti temporanei assunti per il progetto passato in input
     * tra le due date fornite in input.
     * @param project   Progetto che ha assunto gli impiegati
     * @param startDate Data iniziale
     * @param endDate   Data finale
     * @return  Salario totale
     */
    public float getTotalProjectSalaries(Project project, LocalDate startDate, LocalDate endDate){
        float totalSalary = 0;
        for(TemporaryEmployee temp: temporaryEmployeeArrayList){
            if (temp.getProjectCup().equals(project.getCup()))
                totalSalary += temp.getSalary()*(temp.getEmploymentDate().until(endDate).toTotalMonths());
        }
        return totalSalary;
    }

    /**
     * Metodo che controlla le potenziali violazioni dei vincoli del Model dopo l'inserimento dei dati in input e restituisce
     * un elenco di violazioni individuate.
     * @param ssn               Il Social Security Number dell'impiegato temporaneo
     * @param firstName         Il nome dell'impiegato temporaneo
     * @param lastName          Il cognome dell'impiegato temporaneo
     * @param phoneNum          Il recapito telefonico dell'impiegato temporaneo
     * @param address           L'indirizzo dell'impiegato temporaneo
     * @param email             L'email dell'impiegato temporaneo
     * @param employmentDate    La data di assunzione dell'impiegato temporaneo
     * @param salary            Il salario dell'impiegato temporaneo
     * @param lab               Il laboratorio presso cui l'impiegato temporaneo lavora
     * @param project           Il progetto di cui l'impiegato temporaneo si occupa
     * @return Stringhe che comunicano gli eventuali errori riscontrati
     */
    public ArrayList<String> checkTemporaryEmployeeInsert(String ssn, String firstName, String lastName,
                                                          String phoneNum, String address,
                                                          String email, LocalDate employmentDate,
                                                          float salary, String lab, String project){
        ArrayList<String> errors = new ArrayList<String>();

        //Controllo dominio ssn
        if(ssn == null || ssn.isBlank()) errors.add("SSN must not be blank.");
        else if(ssn.length() != 9) errors.add("SSN must be 9 digits long.");
        else{
            for (char c : ssn.toCharArray()) {
                if (!Character.isDigit(c)) {
                    errors.add("SSN must be 9 digits long.");
                    break;
                }
            }
        }
        //Controllo dominio nome e cognome
        if(firstName == null || firstName.isBlank()) errors.add("First name must not be blank.");
        else if(firstName.length() > 30) errors.add("First name is too long. (Max. 20 characters)");
        if(lastName == null || lastName.isBlank()) errors.add("Last name must not be blank.");
        else if(lastName.length() > 30) errors.add("Last name is too long. (Max. 20 characters)");
        //Controllo dominio numero di telefono
        if(phoneNum == null || phoneNum.isBlank()) errors.add("Phone number must not be blank.");
        else if(phoneNum.length() != 10) errors.add("Phone number must be 10 digits long.");
        else{
            for (char c : phoneNum.toCharArray()) {
                if (!Character.isDigit(c)) {
                    errors.add("Phone number must be 10 digits long.");
                    break;
                }
            }
        }
        //Controllo dominio email e indirizzo
        if(email != null && email.length() > 50) errors.add("E-mail is too long. (Max. 50 characters)");
        if(address != null && address.length() > 50) errors.add("Address is too long. (Max. 50 characters)");
        //Controllo dominio data di assunzione
        if(employmentDate.isAfter(LocalDate.now())) errors.add("Employment date must be a past or current date.");
        //Controllo dominio salario
        if(salary < 0) errors.add("Salary must be positive.");
        //Controllo chiavi esterne
        if (lab != null && !lab.isBlank() && controller.getLaboratoryController().findLaboratory(lab) == null)
            errors.add("Laboratory does not exist.");

        if (project == null || project.isBlank()) errors.add("Project must not be blank.");
        else {
            Project newProject = controller.getProjectController().findProjectCup(project);
            if (newProject == null) errors.add("Project does not exist.");
            else if (newProject.getEndDate().isBefore(LocalDate.now())) errors.add("Project has already ended.");
            //Controllo fondi progetto
            else if ((getTotalProjectSalaries(newProject, newProject.getStartDate(), newProject.getEndDate())
                    + salary * (employmentDate.until(newProject.getEndDate())).toTotalMonths()) > newProject.getBudget() / 2)
                errors.add("Project budget too low to hire employee.");
        }

        //Controllo unicità ssn
        if(findTemporaryEmployee(ssn) != null
                || controller.getEmployeeController().findEmployee(ssn) != null) errors.add("SSN already belongs to an employee.");


        return errors;
        
    }

    /**
     * Metodo che crea un oggetto Temporary Employee usando i dati passati in input e lo aggiunge alla lista.
     * @param ssn               Il Social Security Number dell'impiegato temporaneo
     * @param firstName         Il nome dell'impiegato temporaneo
     * @param lastName          Il cognome dell'impiegato temporaneo
     * @param phoneNum          Il recapito telefonico dell'impiegato temporaneo
     * @param address           L'indirizzo dell'impiegato temporaneo
     * @param email             L'email dell'impiegato temporaneo
     * @param employmentDate    La data di assunzione dell'impiegato temporaneo
     * @param salary            Il salario dell'impiegato temporaneo
     * @param lab               Il laboratorio presso il quale l'impiegato temporaneo lavora
     * @param project           Il progetto per cui l'impiegato temporaneo lavora
     */
    public void addTemporaryEmployeeList(String ssn, String firstName, String lastName,
                                String phoneNum, String address,
                                String email, LocalDate employmentDate,
                                float salary, String lab, String project)
    {


        Project newProject = controller.getProjectController().findProjectCup(project);


        //Aggiornamento fondi rimanenti progetto
        newProject.setRemainingFunds(newProject.getRemainingFunds() - salary * (employmentDate.until(newProject.getEndDate())).toTotalMonths());

        //laboratorio
        TemporaryEmployee employee = new TemporaryEmployee(ssn, firstName, lastName, phoneNum, salary, employmentDate, newProject);
        if (lab != null && !lab.isBlank()) {
            employee.setLab(controller.getLaboratoryController().findLaboratory(lab));
        }
        else
            employee.setLab(null);

        //indirizzo
        if (address != null && !address.isBlank())
            employee.setAddress(address);
        else
            employee.setAddress(null);
        //email
        if (email != null && !email.isBlank())
            employee.setEmail(email);
        else
            employee.setEmail(null);

        temporaryEmployeeArrayList.add(employee);

        //L'impiegato viene inserito nel database.
        try{
            if(controller.isDBConnected() && controller.getDBMS().equals("PostgreSQL")){
                DAOEmployeePostgres daoEmployee = new DAOEmployeePostgres();
                daoEmployee.addEmployeeDB(ssn, firstName, lastName, phoneNum, "temporary", salary, Date.valueOf(employmentDate), email,
                        address, lab);

                DAOTemporaryContractPostgres daoTemporaryContractPostgres = new DAOTemporaryContractPostgres();
                daoTemporaryContractPostgres.addTemporaryContractDB(ssn, project);

                DAOProjectPostgres daoProjectPostgres = new DAOProjectPostgres();
                daoProjectPostgres.updateProjectDBRemainingFunds(project, newProject.getRemainingFunds());

            }
        }
        catch(SQLException ex) {
            controller.setDBConnectionState(false);
        }
    }

    /**
     * Metodo che controlla le potenziali violazioni dei vincoli del Model dopo la modifica dei dati in input e restituisce
     * un elenco di violazioni individuate.
     * @param ssn           Il Social Security Number dell'impiegato temporaneo
     * @param firstName     Il nome dell'impiegato temporaneo
     * @param lastName      Il cognome dell'impiegato temporaneo
     * @param phoneNum      Il recapito telefonico dell'impiegato temporaneo
     * @param salary        Il salario dell'impiegato temporaneo
     * @param lab           Il laboratorio presso cui l'impiegato temporaneo lavora
     * @param address       L'indirizzo dell'impiegato temporaneo
     * @param email         L'email dell'impiegato temporaneo
     * @param project       Il progetto per cui l'impiegato temporaneo lavora
     * @return  Stringhe che comunicano gli eventuali errori riscontrati
     */
    public ArrayList<String> checkTemporaryEmployeeModify(String ssn, String firstName, String lastName,
                                                          String phoneNum, float salary,
                                                          String lab,String address,String email,String project){
        ArrayList<String> errors = new ArrayList<String>();

        TemporaryEmployee employee = findTemporaryEmployee(ssn);
        if(employee == null){
            errors.add("Employee does not exist.");
            return errors;
        }
        //Controllo dominio nome e cognome
        if(firstName == null || firstName.isBlank()) errors.add("First name must not be blank.");
        else if(firstName.length() > 30) errors.add("First name is too long. (Max. 20 characters)");
        if(lastName == null || lastName.isBlank()) errors.add("Last name must not be blank.");
        else if(lastName.length() > 30) errors.add("Last name is too long. (Max. 20 characters)");
        //Controllo dominio numero di telefono
        if(phoneNum == null || phoneNum.isBlank()) errors.add("Phone number must not be blank.");
        else if(phoneNum.length() != 10) errors.add("Phone number must be 10 digits long.");
        else{
            for (char c : phoneNum.toCharArray()) {
                if (!Character.isDigit(c)) {
                    errors.add("Phone number must be 10 digits long.");
                    break;
                }
            }
        }
        //Controllo dominio email e indirizzo
        if(email != null && email.length() > 50) errors.add("E-mail is too long. (Max. 50 characters)");
        if(address != null && address.length() > 50) errors.add("Address is too long. (Max. 50 characters)");

        //Controllo dominio salario
        if(salary < 0) errors.add("Salary must be positive.");
        //Controllo chiavi esterne
        if (lab != null && !lab.isBlank() && controller.getLaboratoryController().findLaboratory(lab) == null)
            errors.add("Laboratory does not exist.");

        //Controllo fondi progetto
        Project newProject = controller.getProjectController().findProjectCup(project);
        if ((getTotalProjectSalaries(newProject, newProject.getStartDate(), newProject.getEndDate())
                + salary * (LocalDate.now().until(newProject.getEndDate())).toTotalMonths()
                - employee.getSalary() * (LocalDate.now().until(newProject.getEndDate())).toTotalMonths())
                > newProject.getBudget() / 2)
            errors.add("Project budget too low for new salary.");


        return errors;
    }

    /**
     * Metodo che modifica l'impiegato con l'ssn passato in input usando i parametri ricevuti.
     * @param ssn           Il Social Security Number dell'impiegato temporaneo
     * @param firstName     Il nome dell'impiegato temporaneo
     * @param lastName      Il cognome dell'impiegato temporaneo
     * @param phoneNumber   Il recapito telefonico dell'impiegato temporaneo
     * @param salary        Il salario dell'impiegato temporaneo
     * @param lab           Il laboratorio presso cui l'impiegato temporaneo lavora
     * @param address       L'indirizzo dell'impiegato temporaneo
     * @param email         L'email dell'impiegato temporaneo
     * @param project       Il progetto per cui l'impiegato temporaneo lavora
     */
    public void modifyTemporaryEmployeeList(String ssn, String firstName, String lastName,
                                   String phoneNumber, float salary,
                                   String lab,String address,String email, String project)
    {

        TemporaryEmployee employee = findTemporaryEmployee(ssn);
        //nome
        employee.setFirstName(firstName);
        //cognome
        employee.setLastName(lastName);
        //numero di telefono
        employee.setPhoneNum(phoneNumber);

        //laboratorio
        if(lab != null && !lab.isBlank()) {
            employee.setLab(controller.getLaboratoryController().findLaboratory(lab));
        }else employee.setLab(null);
        
        //progetto
        Project employeeProject = controller.getProjectController().findProjectCup(project);
        employee.setProject(employeeProject);
        
        //data di assunzione
        LocalDate employmentDate = employee.getEmploymentDate();
        
        //Aggiornamento fondi rimanenti progetto
        employeeProject.setRemainingFunds(employeeProject.getRemainingFunds()
                - salary * (LocalDate.now().until(employeeProject.getEndDate())).toTotalMonths()
                + employee.getSalary() * (LocalDate.now().until(employeeProject.getEndDate())).toTotalMonths());
        //salario
        employee.setSalary(salary);

        //indirizzo
        if(address != null && !address.isBlank()){
            employee.setAddress(address);
        }else employee.setAddress(null);
        
        //email
        if(email != null && !email.isBlank()){
            employee.setEmail(email);
        }else employee.setEmail(null);

        //Viene modificato l'impiegato nel database
        try{
            if(controller.isDBConnected() && controller.getDBMS().equals("PostgreSQL")){
                DAOEmployeePostgres daoEmployee = new DAOEmployeePostgres();
                daoEmployee.updateEmployeeDB(ssn, ssn, firstName, lastName, phoneNumber, "temporary", salary, Date.valueOf(employmentDate), email,
                        address, lab);


                DAOProjectPostgres daoProjectPostgres = new DAOProjectPostgres();
                daoProjectPostgres.updateProjectDBRemainingFunds(project, employeeProject.getRemainingFunds());
            }
        }
        catch(SQLException ex) {
            controller.setDBConnectionState(false);
        }
    }

    /**
     * Metodo che controlla le potenziali violazioni dei vincoli del Model dopo l'eliminazione dell'impiegato associato
     * all'ssn passato in input e restituisce un elenco di violazioni individuate.
     * @param ssn   SSN dell'impiegato temporaneo da licenziare
     * @return  Stringa di eventuali errori
     */
    public ArrayList<String> checkTemporaryEmployeeDelete(String ssn){
        ArrayList<String> errors = new ArrayList<String>();

        TemporaryEmployee employee = findTemporaryEmployee(ssn);
        if(employee == null){
            errors.add("Employee does not exist.");
            return errors;
        }

        return errors;
    }

    /**
     * Metodo che licenzia l'impiegato temporaneo che possiede l'SSN passato in input.
     * @param ssn   SSN dell'impiegato temporaneo da licenziare
     * @param projectCup  Cup del progetto per cui lavora l'impiegato temporaneo
     */
    public void fireTemporaryEmployee(String ssn, String projectCup){
        TemporaryEmployee temporaryEmployee = findTemporaryEmployee(ssn);
        Project project = controller.getProjectController().findProjectCup(projectCup);

        if(project != null) {
            //se l'impiegato è stato licenziato prima della scadenza del progetto, viene recuperato il salario che gli spettava nei restanti mesi sotto forma di budget
            int remainingMonths = (int)(LocalDate.now().until(project.getEndDate())).toTotalMonths();

            float recoveredBudget = temporaryEmployee.getSalary() * remainingMonths;

            project.setRemainingFunds(project.getRemainingFunds() + recoveredBudget);
        }

        temporaryEmployeeArrayList.remove(temporaryEmployee);

        //L'impiegato viene rimosso dal database
        try{
            if(controller.isDBConnected() && controller.getDBMS().equals("PostgreSQL")){
                DAOEmployeePostgres daoEmployee = new DAOEmployeePostgres();
                daoEmployee.removeEmployeeDB(ssn);

                DAOTemporaryContractPostgres daoTemporaryContractPostgres = new DAOTemporaryContractPostgres();
                daoTemporaryContractPostgres.removeTemporaryContractDB(ssn);

                if(project != null) {
                    DAOProjectPostgres daoProjectPostgres = new DAOProjectPostgres();
                    daoProjectPostgres.updateProjectDBRemainingFunds(project.getCup(), project.getRemainingFunds());
                }
            }
        }
        catch(SQLException ex) {
            controller.setDBConnectionState(false);
        }
    }
}
