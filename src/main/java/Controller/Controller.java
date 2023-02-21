package Controller;

import DAO.DAOEmployee;
import DAOPostgresImplementation.DAOEmployeePostgres;
import Database.PostgresDBConnection;
import Model.Employee;
import javafx.geometry.Pos;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Controller
{
    private boolean DBSet = false;
    private EmployeeController employeeController;
    private ProjectController projectController;
    private LaboratoryController laboratoryController;
    private EquipmentController equipmentController;
    private TemporaryEmployeeController temporaryEmployeeController;
    private CareerDevelopmentController careerDevelopmentController;

    private Controller(){ //costruttore
        employeeController = new EmployeeController(this);
        projectController = new ProjectController();
        laboratoryController = new LaboratoryController();
        equipmentController = new EquipmentController();
        temporaryEmployeeController= new TemporaryEmployeeController();
        careerDevelopmentController= new CareerDevelopmentController();
    }
    private static Controller instance = null;
    public static Controller getInstance(){//singleton controller
        if(instance==null)
            instance=new Controller();

        return instance;
    }
    public EmployeeController getEmployeeController(){return employeeController; }
    public ProjectController getProjectController(){return projectController;}
    public LaboratoryController getLaboratoryController(){return laboratoryController;}
    public EquipmentController getEquipmentController(){return equipmentController;}
    public TemporaryEmployeeController getTemporaryEmployeeController(){return temporaryEmployeeController;}
    public CareerDevelopmentController getCareerDevelopmentController(){ return careerDevelopmentController;}
    public Boolean isDBConnected(){return DBSet;}

    /**
     * Metodo che collega il programma al database che corrisponde ai parametri di ingresso e carica i dati in memoria
     * @param username Nome utente del server DBMS
     * @param password Password del server DBMS
     * @param database Nome del database
     * @param dbms DBMS utilizzato
     */
    public void setDB(String username, String password, String database, String dbms){
        try{
            if(dbms.equals("PostgreSQL")){
                PostgresDBConnection dbConnection = PostgresDBConnection.getInstance(username, password, database);

                DBSet = true;
                loadDB();
            }
        }
        catch (SQLException ex){
            System.out.println("Connection failed: " + ex.getMessage());
            ex.printStackTrace();
            DBSet = false;
        }
    }

    //WIP
    private void loadDB(){
        try {
            DAOEmployee daoEmployee = new DAOEmployeePostgres();
            employeeController.getEmployeeArrayList().clear();

            ArrayList<String> ssnList = new ArrayList<String>();
            ArrayList<String> firstNameList = new ArrayList<String>();
            ArrayList<String> lastNameList = new ArrayList<String>();
            ArrayList<String> phoneNumList = new ArrayList<String>();
            ArrayList<String> roleList = new ArrayList<String>();
            ArrayList<Float> salaryList = new ArrayList<Float>();
            ArrayList<Date> employmentDateList = new ArrayList<Date>();
            ArrayList<String> emailList = new ArrayList<String>();
            ArrayList<String> addressList = new ArrayList<String>();
            ArrayList<String> laboratoryList = new ArrayList<String>();

            daoEmployee.loadEmployeeDB(ssnList, firstNameList, lastNameList, phoneNumList,
                    roleList, salaryList, employmentDateList, emailList,
                    addressList, laboratoryList);

            for(int i = 0; i < ssnList.size(); i++){
                System.out.println(ssnList.get(i));
                Employee employee = new Employee(ssnList.get(i), firstNameList.get(i),
                        lastNameList.get(i), phoneNumList.get(i), employeeController.convertRole(roleList.get(i)), salaryList.get(i), employmentDateList.get(i).toLocalDate());
                employee.setEmail(emailList.get(i));
                employee.setAddress(addressList.get(i));
                employeeController.addEmployeeList(employee);
            }
        }
        catch (SQLException ex){
            System.out.println("Connection failed: " + ex.getMessage());
            ex.printStackTrace();
            DBSet = false;
        }

    }
}
