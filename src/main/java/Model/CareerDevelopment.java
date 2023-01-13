package Model;

import java.time.LocalDate;

public class CareerDevelopment {
    String oldRole, newRole;
    LocalDate roleChangeDate;
    float salaryChange;

    public CareerDevelopment(String oldRole, String newRole, String ssn, float salaryChange ){
            this.newRole=oldRole;
            this.newRole=newRole;
            roleChangeDate=LocalDate.now();
            this.salaryChange=salaryChange;
    }
}
