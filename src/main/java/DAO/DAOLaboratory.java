package DAO;

import Model.Laboratory;

public interface DAOLaboratory {
    public void addLaboratoryDB(Laboratory lab);
    public void removeLaboratoryDB(Laboratory lab);
    public void updateLaboratoryDB(Laboratory lab);
}
