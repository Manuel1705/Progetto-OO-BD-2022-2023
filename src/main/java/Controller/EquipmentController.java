package Controller;

import GUI.EquipmentListController;
import Model.Equipment;
import Model.Laboratory;
import Model.Project;

import java.util.ArrayList;

public class EquipmentController {
    Controller controller;
    ArrayList<Equipment>equipmentArrayList= new ArrayList<>();
    public ArrayList<Equipment> getEquipmentArrayList(){ return equipmentArrayList; }
    public void addEquipmentList(Equipment equipment){equipmentArrayList.add(equipment);}
    public void addEquipmentList(String id_equipment, String name, String description,
                                 String price,String dealer,
                                 String lab,String project){
        boolean exists=false;
        for (Equipment equipment : equipmentArrayList) {
            if(equipment.getId().equals(id_equipment)){
                exists=true;
                break;
            }
        }
        if (!exists){
            Equipment equipment= new Equipment(id_equipment,name,
                    Float.parseFloat(price),dealer);
            if(!lab.equals("Null")){
                ArrayList<Laboratory>laboratoryArrayList = controller.getLaboratoryController().getLaboratoryArrayList();
                for (Laboratory laboratory:laboratoryArrayList) {
                    if(laboratory.getName().equals(lab)){
                        equipment.setLab(laboratory);
                    }
                }
            }else equipment.setLab(null);
            if(!project.equals("Null")){
                ArrayList<Project>projectArrayList = controller.getProjectController().getProjectArrayList();
                for (Project prj:projectArrayList) {
                    if(prj.getCup().equals(project)){
                        equipment.setProject(prj);
                    }
                }
            }else equipment.setProject(null);
            if(!description.isBlank()){
                equipment.setDescription(description);
            }else equipment.setDescription("No description");

            equipmentArrayList.add(equipment);
            EquipmentListController.list.add(equipment);
        }
    }
    public void modifyEquipment(int index,String name,
                                String description,String lab,String project){
        controller=Controller.getInstance();
        Equipment equipment = controller.getEquipmentController().getEquipmentArrayList().get(index);
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
    }

