package Controller;
import GUI.EmployeeListController;
import GUI.LaboratoryListController;
import GUI.TemporaryEmployeeListController;
import Model.Employee;
import Model.Laboratory;
import Model.Project;
import Model.TemporaryEmployee;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
public class TemporaryEmployeeController {
    Controller controller;
    ArrayList<TemporaryEmployee> temporaryEmployeeArrayList= new ArrayList<TemporaryEmployee>();
    public ArrayList<TemporaryEmployee> getTemporaryEmployeeArrayList(){ return temporaryEmployeeArrayList; }

    /**
     * Metodo che cerca l'impiegato temporaneo con l'ssn fornito e se esso esiste viene restituito. Altrimenti il metodo
     * restituisce null.
     * @param ssn
     * @return
     */
    private TemporaryEmployee findTemporaryEmployee(String ssn){
        for(TemporaryEmployee temporaryEmployee: temporaryEmployeeArrayList){
            if(temporaryEmployee.getSSN().equals(ssn)) return temporaryEmployee;
        }
        return null;
    }
    public void addTemporaryEmployeeList(TemporaryEmployee employee){temporaryEmployeeArrayList.add(employee);}
    public TemporaryEmployee addTemporaryEmployeeList(String ssn, String firstName, String lastName,
                                String phoneNum, String address,
                                String email, LocalDate employmentDate,
                                String salary, String lab,String project)
    {
        controller = Controller.getInstance();
        boolean exists=false;
        for (TemporaryEmployee employee:temporaryEmployeeArrayList) {
            if(employee.getSSN().equals(ssn)){
                exists=true;
                break;
            }
        }
        Project prj=null;
        if (!project.equals("Null")) {
            ArrayList<Project> projectArrayList = controller.getProjectController().getProjectArrayList();
            for (Project prj_temp : projectArrayList) {
                if(prj_temp.getCup().equals(project)){
                    prj=prj_temp;
                }
            }
        }
        Boolean verifySalary=prj.getRemainingFunds()/2 //50%
                - Float.parseFloat(salary) //meno salario
                * (Period.between(LocalDate.now(),prj.getEndDate()).getMonths()+(Period.between(LocalDate.now(),prj.getEndDate()).getYears()*12))>=0;//per i mesi da pagare
        if(!exists && verifySalary)
            {
            TemporaryEmployee employee = new TemporaryEmployee(ssn, firstName, lastName, phoneNum, Float.parseFloat(salary), employmentDate, prj);
            if (!lab.equals("Null")) {
                ArrayList<Laboratory> labs = controller.getLaboratoryController().getLaboratoryArrayList();
                for (Laboratory laboratory : labs) {
                    if(laboratory.getName().equals(lab)){
                        employee.setLab(laboratory);
                    }
                }
            } else employee.setLab(null);


            if (!address.isBlank())
                employee.setAddress(address);
            else
                employee.setAddress("Null");

            if (!email.isBlank())
                employee.setEmail(email);
            else
                employee.setEmail("Null");

            temporaryEmployeeArrayList.add(employee);
            return employee;
        }else System.out.println("Errore");
        return null;
    }
    public void modifyTemporaryEmployeeList(String ssn, String firstName, String lastName,
                                   String phoneNumber, String salary,
                                   String lab,String address,String email,String project)
    {

        TemporaryEmployee employee = findTemporaryEmployee(ssn);
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setPhoneNum(phoneNumber);


        if (!lab.equals("Null")) {
            ArrayList<Laboratory> labs = controller.getLaboratoryController().getLaboratoryArrayList();
            for (Laboratory laboratory : labs) {
                if(laboratory.getName().equals(lab)){
                    employee.setLab(laboratory);
                }
            }
        } else employee.setLab(null);
        if (!project.equals("Null")) {
            ArrayList<Project> projectArrayList = controller.getProjectController().getProjectArrayList();
            for (Project prj : projectArrayList) {
                if(prj.getName().equals(project)){
                    employee.setProject(prj);
                    Boolean verifySalary=prj.getRemainingFunds()/2 //50%
                            - Float.parseFloat(salary) //meno salario
                            * (Period.between(LocalDate.now(),prj.getEndDate()).getMonths()+(Period.between(LocalDate.now(),prj.getEndDate()).getYears()*12))>=0;//per i mesi da pagare
                    if(verifySalary)
                        employee.setSalary(Float.parseFloat(salary));
                }
            }
        } else employee.setProject(null);

        if(!address.isEmpty()){
            employee.setAddress(address);
        }

        if(!email.isEmpty()){
            employee.setEmail(email);
        }
    }
    public void fireTemporaryEmployee(String ssn){
        controller=Controller.getInstance();
        TemporaryEmployee temporaryEmployee = findTemporaryEmployee(ssn);
        Project project=null;
        ArrayList<Project>projectArrayList=controller.getProjectController().getProjectArrayList();
        for (Project prj: projectArrayList){
            if(prj.getCup().equals(temporaryEmployee.getProjectCup())){
                System.out.println(prj.getCup());
                System.out.println(temporaryEmployee.getProjectCup());
                project=prj;
                break;
          }
        }
        int remainingMonths;
        if(project!=null){
            remainingMonths = Period.between(LocalDate.now(),project.getEndDate()).getMonths()+(Period.between(LocalDate.now(),project.getEndDate()).getYears()*12);
            System.out.println(remainingMonths);
            float  recoveredBudget = temporaryEmployee.getSalary()*remainingMonths;
            System.out.println(recoveredBudget);
            System.out.println(project.getRemainingFunds());
            project.setRemainingFunds(project.getRemainingFunds()+recoveredBudget);
            System.out.println(project.getRemainingFunds());
        }
        temporaryEmployeeArrayList.remove(temporaryEmployee);
    }
}
