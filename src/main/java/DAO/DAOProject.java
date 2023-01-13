package DAO;

import Model.Project;

public interface DAOProject {
    public void addProjectDB(Project project);
    public void removeProjectDB(Project project);
    public void updateProjectDB(Project project);
}
