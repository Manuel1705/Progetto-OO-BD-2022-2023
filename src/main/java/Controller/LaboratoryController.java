package Controller;

import GUI.EmployeeListController;
import GUI.LaboratoryListController;
import GUI.ProjectListController;
import Model.Laboratory;

import java.util.ArrayList;

public class LaboratoryController {
    Controller controller;
    ArrayList<Laboratory> laboratoryArrayList = new ArrayList<>();//lista laboratory controller
    public ArrayList<Laboratory> getLaboratoryArrayList(){ return laboratoryArrayList;}
    public void addLaboratoryList(String name,String topic,String Sresp,String project){

        Laboratory laboratory= new Laboratory(name,topic);
        if(!Sresp.isBlank()){
            int i=0;
            while(!EmployeeListController.list.get(i).getSsn().equals(Sresp)){
                i++;
            }
            laboratory.setSresp(EmployeeListController.list.get(i));
        }
        if(!project.isBlank()){
            int i=0;
            while(!ProjectListController.list.get(i).getCup().equals(project)){
                i++;
            }
            laboratory.setProject(ProjectListController.list.get(i));
        }
        laboratoryArrayList.add(laboratory);
        LaboratoryListController.list.add(laboratory);
    }
}
