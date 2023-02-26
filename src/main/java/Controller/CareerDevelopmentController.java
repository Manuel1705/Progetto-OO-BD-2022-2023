package Controller;
import Model.CareerDevelopment;
import Model.Employee;
import java.util.ArrayList;
public class CareerDevelopmentController {
    private ArrayList<CareerDevelopment>careerDevelopmentArrayList = new ArrayList<>();

    /**
     * Metodo che crea un oggetto Career Development usando i dati passati in input e lo aggiunge alla lista.
     * @param employee L'impiegato che ha cambiato ruolo.
     * @param newRole Il nuovo ruolo dell'impiegato.
     * @param newSalary Il nuovo salario dell'impiegato.
     */
    public void addCareerDevelopment(Employee employee, String newRole, float newSalary){
        CareerDevelopment careerDevelopment = new CareerDevelopment(employee, employee.getRole(), newRole,
                newSalary - employee.getSalary());
                careerDevelopmentArrayList.add(careerDevelopment);
    }

    /**
     * Metodo che restituisce careerDevelopmentArrayList
     * @return La lista che contiene gli oggetti Career Development
     */
    public ArrayList<CareerDevelopment> getCareerDevelopmentArrayList(){return careerDevelopmentArrayList;}

}
