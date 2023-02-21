package Controller;
import Model.CareerDevelopment;
import Model.Employee;
import java.util.ArrayList;
public class CareerDevelopmentController {
    ArrayList<CareerDevelopment>careerDevelopmentArrayList = new ArrayList<>();
    public CareerDevelopment addCareerDevelopment(Employee employee,String newRole, float newSalary){
        CareerDevelopment careerDevelopment = new CareerDevelopment(employee, employee.getRole(),newRole,
                newSalary - employee.getSalary());
                careerDevelopmentArrayList.add(careerDevelopment);
                        return careerDevelopment;
    }

    public ArrayList<CareerDevelopment> getCareerDevelopmentArrayList(){return careerDevelopmentArrayList;}

}
