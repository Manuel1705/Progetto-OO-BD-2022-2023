package Controller;

import GUI.EquipmentListController;
import Model.Employee;
import Model.Equipment;
import Model.Laboratory;
import Model.Project;
import java.util.ArrayList;

public class EquipmentController {
    private Controller controller;
    private ArrayList<Equipment>equipmentArrayList= new ArrayList<>();
    public ArrayList<Equipment> getEquipmentArrayList(){ return equipmentArrayList; }
    public void addEquipmentList(Equipment equipment){equipmentArrayList.add(equipment);}
    public void addEquipmentList(int id_equipment, String name, String description,
                                 float price, String dealer,
                                 String lab,String project){
        boolean exists=false;
        for (Equipment equipment : equipmentArrayList) {
            if(equipment.getId() == id_equipment){
                exists=true;
                break;
            }
        }
        if (!exists) {
            controller = Controller.getInstance();
            Equipment equipment = null;
            boolean verifyPrice = false;
            if (!project.equals("Null")) {
                ArrayList<Project> projectArrayList = controller.getProjectController().getProjectArrayList();
                for (Project prj : projectArrayList) {
                    if (prj.getCup().equals(project)) {
                        if (prj.getRemainingFunds() / 2 - price >= 0) {
                            equipment = new Equipment(id_equipment, name,
                                    price, dealer);
                            equipment.setProject(prj);
                            verifyPrice = true;
                        }
                    }
                }
            } else equipment.setProject(null);
            if (verifyPrice) {
                if (!lab.equals("Null")) {
                    ArrayList<Laboratory> laboratoryArrayList = controller.getLaboratoryController().getLaboratoryArrayList();
                    for (Laboratory laboratory : laboratoryArrayList) {
                        if (laboratory.getName().equals(lab)) {
                            equipment.setLab(laboratory);
                        }
                    }
                } else equipment.setLab(null);

                if (!description.isBlank()) {
                    equipment.setDescription(description);
                } else equipment.setDescription("No description");

                equipmentArrayList.add(equipment);
            }
        }
    }
    private Equipment findEquipment(int id){
        for(Equipment equipment: equipmentArrayList) {
            if (equipment.getId() == id) return equipment;
        }
        return null;
    }
    public void modifyEquipment(int id,String name,
                                String description,String lab,String project){
        controller=Controller.getInstance();
        Equipment equipment = findEquipment(id);
        equipment.setName(name);
        equipment.setDescription(description);
        if(!lab.equals("Null")){
            ArrayList<Laboratory>laboratoryArrayList = controller.getLaboratoryController().getLaboratoryArrayList();
            for (Laboratory laboratory : laboratoryArrayList) {
                if(laboratory.getName().equals(lab)){
                    equipment.setLab(laboratory);
                    break;
                }
            }
        }else equipment.setLab(null);

        if(!project.equals("Null")){
            ArrayList<Project>projectArrayList=controller.getProjectController().getProjectArrayList();
            for (Project prj: projectArrayList) {
                if(prj.getCup().equals(project)){
                    equipment.setProject(prj);
                    break;
                }
            }
        }else equipment.setProject(null);
    }
    public void deleteEquipment(int id){
        equipmentArrayList.remove(findEquipment(id));
    }

    /**
     * Metodo che restituisce il costo totale di tutto l'equipaggiamento acquistato da un determinato progetto.
     * @param project Progetto che ha acquistato l'equipaggiamento.
     * @return Costo totale.
     */
    public float getTotalProjectPrice(Project project){
        float totalPrice = 0;
        for(Equipment equipment: equipmentArrayList){
            if(equipment.getProjectCup().equals(project.getCup()))
                totalPrice += equipment.getPrice();
        }
        return totalPrice;
    }
}

