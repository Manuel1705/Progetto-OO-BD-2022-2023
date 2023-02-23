package Controller;
import DAO.DAOEmployee;
import DAOPostgresImplementation.DAOEmployeePostgres;
import Model.CareerDevelopment;
import Model.Employee;
import Model.Laboratory;
import Model.Project;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
public class EmployeeController {
    private Controller controller;
    private ArrayList<Employee> employeeArrayList= new ArrayList<Employee>();

    /**
     * Costruttore della classe che inizializza l'attributo controller.
     * @param controller
     */
    public EmployeeController(Controller controller){
        this.controller = controller;
    }

    /**
     * Metodo che restituisce la lista degli impiegati e la aggiorna in base alla data attuale.
     * @return
     */
    public ArrayList<Employee> getEmployeeArrayList(){
        for (Employee employee:employeeArrayList){
            employee.CheckRole();
        }
        return employeeArrayList;

    }

    /**
     * Metodo che restituisce l'impiegato con l'ssn fornito in input, se non esiste restituisce null.
     * @param ssn
     * @return
     */
    public Employee findEmployee(String ssn){
        for(Employee employee: employeeArrayList){
            if (employee.getSSN().equals(ssn)) return employee;
        }
        return null;
    }

    /**
     * Metodo che controlla le potenziali violazioni dei vincoli del Model dopo l'inserimento dei dati in input e restituisce
     * un elenco di violazioni individuate.
     * @param ssn
     * @param firstName
     * @param lastName
     * @param phoneNum
     * @param address
     * @param role
     * @param email
     * @param employmentDate
     * @param salary
     * @param lab
     * @return
     */
    public ArrayList<String> checkEmployeeInsert(String ssn, String firstName, String lastName,
                                    String phoneNum, String address, String role,
                                    String email, LocalDate employmentDate,
                                    float salary, String lab){
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
        //Contollo dominio numero di telefono
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
        //Controllo dominio ruolo
        if(role == null || (!role.equals("Junior") && !role.equals("Middle") && !role.equals("Senior") && !role.equals("Executive")))
            errors.add("Role invalid.");
        //Controllo esistenza chiave esterna
        if (lab != null && !lab.isBlank() && controller.getLaboratoryController().findLaboratory(lab) == null)
            errors.add("Laboratory does not exist.");
        //Controllo unicita' ssn
        if(findEmployee(ssn) != null
                || controller.getTemporaryEmployeeController().findTemporaryEmployee(ssn) != null) errors.add("SSN already belongs to an employee.");


        return errors;
    }
    /**
     * Metodo che controlla le potenziali violazioni dei vincoli del Model dopo la modifica dei dati in input e restituisce
     * un elenco di violazioni individuate.
     * @param ssn
     * @param firstName
     * @param lastName
     * @param phoneNum
     * @param address
     * @param role
     * @param email
     * @param salary
     * @param lab
     * @return
     */
    public ArrayList<String> checkEmployeeModify(String ssn, String firstName, String lastName,
                                                 String phoneNum, String address, String role,
                                                 String email, float salary, String lab){
        ArrayList<String> errors = new ArrayList<String>();

        Employee employee = findEmployee(ssn);
        if(employee == null){
            errors.add("Employee does not exist.");
            return errors;
        }

        //Controllo dominio nome e cognome
        if(firstName == null || firstName.isBlank()) errors.add("First name must not be blank.");
        else if(firstName.length() > 30) errors.add("First name is too long. (Max. 20 characters)");
        if(lastName == null || lastName.isBlank()) errors.add("Last name must not be blank.");
        else if(lastName.length() > 30) errors.add("Last name is too long. (Max. 20 characters)");

        //Contollo dominio numero di telefono
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
        //Controllo dominio ruolo

        if(role == null || (!role.equals("Junior") && !role.equals("Middle") && !role.equals("Senior") && !role.equals("Executive")))
            errors.add("Role invalid.");

        //Controllo esistenza chiave esterna
        if (lab != null && !lab.isBlank() && controller.getLaboratoryController().findLaboratory(lab) == null)
            errors.add("Laboratory does not exist.");

        //Controllo unicita' ssn
        if(!(findEmployee(ssn) == null) && findEmployee(ssn) != employee) errors.add("SSN already belongs to an employee.");

        //Controllo posizioni legate al ruolo
        if(employee.getRole().equals("Senior") && !role.equals("Senior")){
            ArrayList<Laboratory> labList = controller.getLaboratoryController().getLaboratoryArrayList();
            for(Laboratory laboratory: labList){
                if (laboratory.getSrespSSN().equals(employee.getSSN())){
                    errors.add("Employee is scientific responsible for a laboratory. Role cannot be changed.");
                }
            }
        }
        if(employee.getRole().equals("Senior") && !role.equals("Senior")){
            ArrayList<Project> projectList = controller.getProjectController().getProjectArrayList();
            for(Project project: projectList){
                if (project.getSrefSSN().equals(employee.getSSN())){
                    errors.add("Employee is scientific reference for a project. Role cannot be changed.");
                }
            }
        }
        if(employee.getRole().equals("Executive") && !role.equals("Executive")){
            ArrayList<Project> projectList = controller.getProjectController().getProjectArrayList();
            for(Project project: projectList){
                if (project.getSrespSSN().equals(employee.getSSN())){
                    errors.add("Employee is scientific responsible for a project. Role cannot be changed.");
                }
            }
        }


        return errors;

    }

    /**
     * Metodo che controlla le potenziali violazioni dei vincoli del Model dopo l'eliminazione dell'impiegato associato
     * all'ssn passato in input e restituisce un elenco di violazioni individuate.
     * @param ssn
     * @return
     */
    public ArrayList<String> checkEmployeeDelete(String ssn){
        ArrayList<String> errors = new ArrayList<String>();

        Employee employee = findEmployee(ssn);
        if(employee == null){
            errors.add("Employee does not exist.");
            return errors;
        }



        //Controllo posizioni legate al ruolo
        if(employee.getRole().equals("Senior")){
            ArrayList<Laboratory> labList = controller.getLaboratoryController().getLaboratoryArrayList();
            for(Laboratory laboratory: labList){
                if (laboratory.getSrespSSN().equals(employee.getSSN())){
                    errors.add("Employee is scientific responsible for a laboratory. Cannot be deleted.");
                }
            }
        }
        if(employee.getRole().equals("Senior")){
            ArrayList<Project> projectList = controller.getProjectController().getProjectArrayList();
            for(Project project: projectList){
                if (project.getSrefSSN().equals(employee.getSSN())){
                    errors.add("Employee is scientific reference for a project. Cannot be deleted..");
                }
            }
        }
        if(employee.getRole().equals("Executive")){
            ArrayList<Project> projectList = controller.getProjectController().getProjectArrayList();
            for(Project project: projectList){
                if (project.getSrespSSN().equals(employee.getSSN())){
                    errors.add("Employee is scientific responsible for a project. Cannot be deleted.");
                }
            }
        }


        return errors;

    }

    /**
     * Metodo che aggiunge un oggetto Employee passato in input alla lista.
     * @param employee
     */
    public void addEmployeeList(Employee employee){employeeArrayList.add(employee);}

    /**
     * Metodo che usa i dati passati in input per creare un oggetto Employee e aggiungerlo alla lista.
     * @param ssn
     * @param firstName
     * @param lastName
     * @param phoneNum
     * @param address
     * @param role
     * @param email
     * @param employmentDate
     * @param salary
     * @param lab
     */
    public void addEmployeeList(String ssn, String firstName, String lastName,
                                    String phoneNum, String address, String role,
                                    String email, LocalDate employmentDate,
                                    float salary, String lab)
    {


        Employee employee = new Employee(ssn, firstName, lastName, phoneNum, role, salary, employmentDate);
        if (lab != null && !lab.isBlank()) {
            ArrayList<Laboratory> labs = controller.getLaboratoryController().getLaboratoryArrayList();
            for (Laboratory laboratory : labs) {
                if(laboratory.getName().equals(lab)){
                    employee.setLab(laboratory);
                }
            }
        }
        else
            employee.setLab(null);

        if (address != null && !address.isBlank())
            employee.setAddress(address);
        else
            employee.setAddress(null);

        if (email != null && !email.isBlank())
            employee.setEmail(email);
        else
            employee.setEmail(null);

        employeeArrayList.add(employee);
        try{
            if(controller.isDBConnected()){
                DAOEmployeePostgres daoEmployee = new DAOEmployeePostgres();
                daoEmployee.addEmployeeDB(ssn, firstName, lastName, phoneNum, role, salary, Date.valueOf(employmentDate), email,
                        address, lab);
            }
        }catch(Exception e){
            System.out.println("Database error");
        }
    }

    /**
     * Metodo che modifica i dati dell'impiegato che corrisponde all'SSN passato in input.
     * @param ssn
     * @param firstName
     * @param lastName
     * @param phoneNumber
     * @param newRole
     * @param newSalary
     * @param lab
     * @param address
     * @param email
     */
    public void modifyEmployeeList(String ssn, String firstName, String lastName,
                                                String phoneNumber, String newRole, float newSalary,
                                                String lab, String address, String email)
    {

        Employee employee = findEmployee(ssn);
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setPhoneNum(phoneNumber);
        if(!employee.getRole().equals(newRole)){
            controller.getCareerDevelopmentController().addCareerDevelopment(employee,newRole,newSalary);
            employee.setRole(newRole);
        }
        employee.setSalary(newSalary);

        if(lab != null && !lab.isBlank()) {
            employee.setLab(controller.getLaboratoryController().findLaboratory(lab));
        }else employee.setLab(null);

        if(address != null && !address.isBlank()){
            employee.setAddress(address);
        }else employee.setAddress(null);

        if(email != null && !email.isBlank()){
            employee.setEmail(email);
        }else employee.setEmail(null);
    }

    /**
     * Metodo che licenzia l'impiegato con che possiede l'ssn passato in input.
     * @param ssn
     */
    public void fireEmployee(String ssn){

        Employee employee = findEmployee(ssn);

        employeeArrayList.remove(employee);
        try{
            if(controller.isDBConnected()){
                DAOEmployeePostgres daoEmployee = new DAOEmployeePostgres();
                daoEmployee.removeEmployeeDB(ssn);
            }
        }catch(Exception e){
            System.out.println("error");
        }
    }

    /**
     * Metodo che converte una stringa che rappresenta il ruolo di un impiegato dal formatto adottato dall'applicativo
     * e il formato utilizzato dal database.
     * @param role Ruolo da convertire
     * @return
     */
    public String convertRole(String role){
        switch(role){
            case "executive":
                return "Executive";
            case "senior":
                return "Senior";
            case "middle":
                return "Middle";
            case "junior":
                return "Junior";
        }
        return null;
    }
}
