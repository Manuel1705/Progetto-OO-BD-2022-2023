package Controller;

import DAO.DAOEmployee;
import DAOPostgresImplementation.*;
import Database.PostgresDBConnection;
import Model.*;


import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public class Controller
{
    private boolean DBSet = false;
    private String dbms;
    private EmployeeController employeeController;
    private ProjectController projectController;
    private LaboratoryController laboratoryController;
    private EquipmentController equipmentController;
    private TemporaryEmployeeController temporaryEmployeeController;
    private CareerDevelopmentController careerDevelopmentController;

    /**
     * Costruttore privato della classe che crea le istanze degli altri controller e implementa il pattern Singleton.
     */
    private Controller(){
        employeeController = new EmployeeController(this);
        projectController = new ProjectController(this);
        laboratoryController = new LaboratoryController(this);
        equipmentController = new EquipmentController(this);
        temporaryEmployeeController= new TemporaryEmployeeController(this);
        careerDevelopmentController= new CareerDevelopmentController();
    }
    private static Controller instance = null;

    /**
     * Metodo statico che restituisce l'istanza del controller se essa esiste, altrimenti ne crea una nuova. Implementa il
     * pattern Singleton.
     * @return
     */
    public static Controller getInstance(){//singleton controller
        if(instance==null)
            instance=new Controller();

        return instance;
    }

    /**
     * Metodo che restituisce employeeController.
     * @return Il controller.
     */
    public EmployeeController getEmployeeController(){return employeeController; }
    /**
     * Metodo che restituisce projectController.
     * @return Il controller.
     */
    public ProjectController getProjectController(){return projectController;}
    /**
     * Metodo che restituisce laboratoryController.
     * @return Il controller.
     */
    public LaboratoryController getLaboratoryController(){return laboratoryController;}
    /**
     * Metodo che restituisce equipmentController.
     * @return Il controller.
     */
    public EquipmentController getEquipmentController(){return equipmentController;}
    /**
     * Metodo che restituisce temporaryEmployeeController.
     * @return Il controller.
     */
    public TemporaryEmployeeController getTemporaryEmployeeController(){return temporaryEmployeeController;}
    /**
     * Metodo che restituisce careerDevelopmentController.
     * @return Il controller.
     */
    public CareerDevelopmentController getCareerDevelopmentController(){ return careerDevelopmentController;}
    /**
     * Metodo che restituisce il valore di DBSet.
     */
    public Boolean isDBConnected(){return DBSet;}

    /**
     * Metodo che setta il valore di DBSet.
     * @param value
     */
    public void setDBConnectionState(Boolean value){DBSet = value;}

    /**
     * Metodo che restituisce la Stringa contenente il nome del DBMS utilizzato dal database.
     * @return
     */
    public String getDBMS(){return dbms;}


    /**
     * Metodo che aggiorna i dati del controller in base alla data attuale.
     */
    public void updateData(){
        //Aggiornamento impiegati
        for(CompanyEmployee employee: employeeController.getEmployeeArrayList()) {
            String newRole = employee.CheckRole();
            if(newRole != null){
                employeeController.modifyEmployeeList(employee.getSSN(), employee.getFirstName(), employee.getLastName(),
                        employee.getPhoneNum(), newRole, employee.getSalary() + 500, employee.getLabName(),
                        employee.getAddress(), employee.getEmail());
            }

        }
        //Aggiornamento progetti
        ArrayList<Project> endedProjects = new ArrayList<Project>();
        for(Project project: projectController.getProjectArrayList()){
            if(project.isExpired()) endedProjects.add(project);
        }
        for(Project project: endedProjects)
            projectController.dismissProject(project.getCup());
    }

    /**
     * Metodo che collega il programma al database che corrisponde ai parametri di ingresso e carica i dati in memoria
     * @param username Nome utente del server DBMS
     * @param password Password del server DBMS
     * @param database Nome del database
     * @param dbms DBMS utilizzato
     */
    public ArrayList<String> setDB(String username, String password, String database, String dbms){
        this.dbms = "";
        ArrayList<String> errors = new ArrayList<String>();
        if(dbms.equals("PostgreSQL")){
            PostgresDBConnection dbConnection = PostgresDBConnection.getInstance(username, password, database);
            errors = dbConnection.getErrors();
            if(errors.isEmpty()) {
                this.dbms = "PostgreSQL";
                DBSet = true;
                try{
                    loadDB();
                }
                catch(SQLException ex){
                    DBSet = false;
                    clearData();
                    errors.add("Database load error.");
                }

            }
            else{
                DBSet = false;
            }
        }
        return errors;

    }

    /**
     * Metodo che elimina tutti i dati contenuti salvati dal programma.
     */
    private void clearData(){
        employeeController.getEmployeeArrayList().clear();
        projectController.getProjectArrayList().clear();
        laboratoryController.getLaboratoryArrayList().clear();
        equipmentController.getEquipmentArrayList().clear();
        temporaryEmployeeController.getTemporaryEmployeeArrayList().clear();
        careerDevelopmentController.getCareerDevelopmentArrayList().clear();
    }

    /**
     * Metodo che carica i dati dal database.
     * @throws SQLException
     */
    private void loadDB() throws SQLException{
        clearData();
        if (dbms.equals("PostgreSQL")) {

            DAOEmployee daoEmployee = new DAOEmployeePostgres();
            DAOTemporaryContractPostgres daoTemporaryContract = new DAOTemporaryContractPostgres();
            DAOProjectPostgres daoProject = new DAOProjectPostgres();
            DAOLaboratoryPostgres daoLaboratory = new DAOLaboratoryPostgres();
            DAOEquipmentPostgres daoEquipment = new DAOEquipmentPostgres();
            DAOCareerDevelopmentPostgres daoCareerDevelopment = new DAOCareerDevelopmentPostgres();


            //Caricamento tabella Employee.
            ArrayList<String> ssnListEmp = new ArrayList<String>();
            ArrayList<String> firstNameListEmp = new ArrayList<String>();
            ArrayList<String> lastNameListEmp = new ArrayList<String>();
            ArrayList<String> phoneNumListEmp = new ArrayList<String>();
            ArrayList<String> roleListEmp = new ArrayList<String>();
            ArrayList<Float> salaryListEmp = new ArrayList<Float>();
            ArrayList<Date> employmentDateListEmp = new ArrayList<Date>();
            ArrayList<String> emailListEmp = new ArrayList<String>();
            ArrayList<String> addressListEmp = new ArrayList<String>();
            ArrayList<String> laboratoryListEmp = new ArrayList<String>();

            daoEmployee.loadEmployeeDB(ssnListEmp, firstNameListEmp, lastNameListEmp, phoneNumListEmp,
                    roleListEmp, salaryListEmp, employmentDateListEmp, emailListEmp,
                    addressListEmp, laboratoryListEmp);

            //Caricamento tabella Laboratory
            ArrayList<String> nameListLab = new ArrayList<String>();
            ArrayList<String> topicListLab = new ArrayList<String>();
            ArrayList<String> sRespListLab = new ArrayList<String>();
            ArrayList<String> projectListLab = new ArrayList<String>();

            daoLaboratory.loadLaboratoryDB(nameListLab, topicListLab, sRespListLab, projectListLab);

            //Caricamento tabella Project
            ArrayList<String> cupListProject = new ArrayList<String>();
            ArrayList<String> nameListProject = new ArrayList<String>();
            ArrayList<Float> budgetListProject = new ArrayList<Float>();
            ArrayList<Float> remainingFundsListProject = new ArrayList<Float>();
            ArrayList<Date> startDateListProject = new ArrayList<Date>();
            ArrayList<Date> endDateListProject = new ArrayList<Date>();
            ArrayList<String> sRespListProject = new ArrayList<String>();
            ArrayList<String> sRefListProject = new ArrayList<String>();

            daoProject.loadProjectDB(cupListProject, nameListProject, budgetListProject, remainingFundsListProject,
                    startDateListProject, endDateListProject, sRespListProject, sRefListProject);

            //Caricamento tabella Equipment
            ArrayList<Integer> idListEquipment = new ArrayList<Integer>();
            ArrayList<String> nameListEquipment = new ArrayList<String>();
            ArrayList<String> descriptionListEquipment = new ArrayList<String>();
            ArrayList<Float> priceListEquipment = new ArrayList<Float>();
            ArrayList<Date> purchaseDateListEquipment = new ArrayList<Date>();
            ArrayList<String> dealerListEquipment = new ArrayList<String>();
            ArrayList<String> laboratoryNameListEquipment = new ArrayList<String>();
            ArrayList<String> projectCupListEquipment = new ArrayList<String>();

            daoEquipment.loadEquipmentDB(idListEquipment, nameListEquipment, descriptionListEquipment, priceListEquipment,
                    purchaseDateListEquipment, dealerListEquipment, laboratoryNameListEquipment, projectCupListEquipment);

            //Caricamento tabella Career Development
            ArrayList<String> oldRoleListCD = new ArrayList<String>();
            ArrayList<String> newRoleListCD = new ArrayList<String>();
            ArrayList<Date> roleChangeDateListCD = new ArrayList<Date>();
            ArrayList<Float> salaryChangeListCD = new ArrayList<Float>();
            ArrayList<String> ssnListCD = new ArrayList<String>();

            daoCareerDevelopment.loadCareerDevelopment(oldRoleListCD, newRoleListCD, roleChangeDateListCD, salaryChangeListCD,
                    ssnListCD);

            //Caricamento tabella TemporaryContract
            ArrayList<String> ssnListTC = new ArrayList<String>();
            ArrayList<String> cupListTC = new ArrayList<String>();

            daoTemporaryContract.loadTemporaryContractDB(ssnListTC, cupListTC);

            //Creazione oggetti employee e inizializzazione attributi (tranne lab)
            for (int i = 0; i < ssnListEmp.size(); i++) {
                if (!roleListEmp.get(i).equals("temporary")) {
                    CompanyEmployee employee = new CompanyEmployee(ssnListEmp.get(i), firstNameListEmp.get(i),
                            lastNameListEmp.get(i), phoneNumListEmp.get(i),
                            employeeController.convertRole(roleListEmp.get(i)), salaryListEmp.get(i),
                            employmentDateListEmp.get(i).toLocalDate());

                    employee.setEmail(emailListEmp.get(i));
                    employee.setAddress(addressListEmp.get(i));
                    employeeController.getEmployeeArrayList().add(employee);
                }
            }
            //Creazione oggetti project e inizializzazione attributi
            for (int i = 0; i < cupListProject.size(); i++) {
                CompanyEmployee sResp = employeeController.findEmployee(sRespListProject.get(i));
                CompanyEmployee sRef = employeeController.findEmployee(sRefListProject.get(i));
                Project project = new Project(cupListProject.get(i), nameListProject.get(i), budgetListProject.get(i),
                        remainingFundsListProject.get(i), startDateListProject.get(i).toLocalDate(),
                        endDateListProject.get(i).toLocalDate(), sResp, sRef);
                projectController.getProjectArrayList().add(project);
            }

            //Creazione oggetti laboratory e inizializzazione attributi
            for (int i = 0; i < nameListLab.size(); i++) {
                CompanyEmployee sResp = employeeController.findEmployee(sRespListLab.get(i));
                Project project = projectController.findProjectCup(projectListLab.get(i));
                Laboratory laboratory = new Laboratory(nameListLab.get(i), topicListLab.get(i), sResp, project);
                laboratoryController.getLaboratoryArrayList().add(laboratory);
            }

            //Creazione oggetti equipment e inizializzazione attributi
            for (int i = 0; i < idListEquipment.size(); i++) {
                Laboratory lab = laboratoryController.findLaboratory(laboratoryNameListEquipment.get(i));
                Project project = projectController.findProjectCup(projectCupListEquipment.get(i));
                Equipment equipment = new Equipment(idListEquipment.get(i), nameListEquipment.get(i), priceListEquipment.get(i),
                        project, lab, dealerListEquipment.get(i));
                equipment.setDescription(descriptionListEquipment.get(i));
                equipmentController.getEquipmentArrayList().add(equipment);
            }

            //Creazione oggetti temporary employee e inizializzazione attributi
            for (int i = 0; i < ssnListEmp.size(); i++) {
                if (roleListEmp.get(i).equals("temporary")) {
                    Laboratory lab = laboratoryController.findLaboratory(laboratoryListEmp.get(i));
                    int j = ssnListTC.indexOf(ssnListEmp.get(i));
                    Project project = projectController.findProjectCup(cupListTC.get(j));
                    TemporaryEmployee employee = new TemporaryEmployee(ssnListEmp.get(i), firstNameListEmp.get(i),
                            lastNameListEmp.get(i), phoneNumListEmp.get(i), salaryListEmp.get(i),
                            employmentDateListEmp.get(i).toLocalDate(), project);

                    employee.setLab(lab);
                    employee.setAddress(addressListEmp.get(i));
                    employee.setEmail(emailListEmp.get(i));

                    temporaryEmployeeController.getTemporaryEmployeeArrayList().add(employee);
                }
            }

            //Creazione oggetti career development e inizializzazione attributi
            for(int i = 0; i < ssnListCD.size(); i++){
                CompanyEmployee employee = employeeController.findEmployee(ssnListCD.get(i));
                CareerDevelopment careerDevelopment = new CareerDevelopment(employee, employeeController.convertRole(oldRoleListCD.get(i)),
                        employeeController.convertRole(newRoleListCD.get(i)), salaryChangeListCD.get(i));

                careerDevelopmentController.getCareerDevelopmentArrayList().add(careerDevelopment);
            }

            //Inizializzazione attributo lab di employee
            for (int i = 0; i < ssnListEmp.size(); i++) {
                if (!roleListEmp.get(i).equals("temporary")) {
                    Laboratory lab = laboratoryController.findLaboratory(laboratoryListEmp.get(i));
                    employeeController.findEmployee(ssnListEmp.get(i)).setLab(lab);
                }
            }

        }

    }
}
