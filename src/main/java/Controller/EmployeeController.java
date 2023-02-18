package Controller;
import Model.CareerDevelopment;
import Model.Employee;
import Model.Laboratory;
import Model.Project;

import java.time.LocalDate;
import java.util.ArrayList;
public class EmployeeController {
    Controller controller;
    ArrayList<Employee> employeeArrayList= new ArrayList<Employee>();

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
     * Metodo che aggiunge un oggetto Employee passato in input alla lista.
     * @param employee
     */
    public void addEmployeeList(Employee employee){employeeArrayList.add(employee);}
    public void addEmployeeList(String ssn, String firstName, String lastName,
                                    String phoneNum, String address, String role,
                                    String email, LocalDate employmentDate,
                                    String salary, String lab)
    {
        boolean exists=false;
        for (Employee employee:employeeArrayList) {
            if(employee.getSSN().equals(ssn)){
                exists=true;
                break;
            }
        }
        if(!exists) {
            Employee employee = new Employee(ssn, firstName, lastName, phoneNum, role, Float.parseFloat(salary), employmentDate);
            controller = Controller.getInstance();
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

            employeeArrayList.add(employee);
        }else System.out.println("L'impigato esiste già");

    }
    public void modifyEmployeeList(String ssn, String firstName, String lastName,
                                                String phoneNumber, String newRole, String newSalary,
                                                String lab, String address, String email)
    {   Controller controller = Controller.getInstance();
        CareerDevelopment careerDevelopment = null;
        Employee employee = findEmployee(ssn);
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setPhoneNum(phoneNumber);
        if(!employee.getRole().equals(newRole)){
            careerDevelopment=controller.getCareerDevelopmentController().addCareerDevelopment(employee,newRole,newSalary);
            employee.setRole(newRole);
        }
        employee.setSalary(Float.parseFloat(newSalary));

        if(!lab.equals("Null")) {
            ArrayList<Laboratory>laboratoryArrayList=controller.getLaboratoryController().getLaboratoryArrayList();
            for (Laboratory laboratory : laboratoryArrayList) {
                if(laboratory.getName().equals(lab)){
                    employee.setLab(laboratory);
                    break;
                }
            }
        }else employee.setLab(null);

        if(!address.isBlank()){
            employee.setAddress(address);
        }else employee.setAddress("Null");

        if(!email.isBlank()){
            employee.setEmail(email);
        }else employee.setEmail("Null");
    }
    public void fireEmployee(String ssn){
        //ottimizzare in base al ruolo
        controller=Controller.getInstance();
        Employee employee = findEmployee(ssn);
        ArrayList<Laboratory>laboratoryArrayList=controller.getLaboratoryController().getLaboratoryArrayList();
        for (Laboratory laboratory:laboratoryArrayList) {
            if(laboratory.getSrespSSN().equals(employee.getSSN())){
                laboratory.setSresp(null);
            }
        }
        ArrayList<Project>projectArrayList=controller.getProjectController().getProjectArrayList();
        for (Project project:projectArrayList) {
            if(project.getSrespSSN().equals(employee.getSSN())){
                project.setSresp(null);
            }
            if(project.getSrefSSN().equals(employee.getSSN())){
                project.setSref(null);
            }
        }
        employeeArrayList.remove(employee);
    }
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
