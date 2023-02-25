package Controller;
import Model.CareerDevelopment;
import Model.Employee;
import java.util.ArrayList;
public class CareerDevelopmentController {
    private ArrayList<CareerDevelopment>careerDevelopmentArrayList = new ArrayList<>();
    public void addCareerDevelopment(Employee employee,String newRole, float newSalary){
        CareerDevelopment careerDevelopment = new CareerDevelopment(employee, employee.getRole(),newRole,
                newSalary - employee.getSalary());
                careerDevelopmentArrayList.add(careerDevelopment);
    }

    public ArrayList<CareerDevelopment> getCareerDevelopmentArrayList(){return careerDevelopmentArrayList;}

}
