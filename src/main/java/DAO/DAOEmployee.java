package DAO;

import Model.Employee;

public interface DAOEmployee {
    public void addEmployeeDB(Employee employee);
    public void removeEmployeeDB(Employee employee);
    public void updateEmployeeDB(Employee employee);

}
