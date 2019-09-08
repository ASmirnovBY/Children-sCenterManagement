package com.smirnovsoft.centerclientapp.uijavafx.controllers;

import com.smirnovsoft.centerclientapp.data.groups.GroupProperties;
import com.smirnovsoft.centerclientapp.utils.dbutils.CommandGroupProperties;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddGroupPropertiesController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField editCost;

    @FXML
    private TextField editTitle;

    @FXML
    private TableView<GroupProperties> groupProper;

    @FXML
    private TextField editLesson;

    @FXML
    private TableColumn<GroupProperties, Long> id;

    @FXML
    private TableColumn<GroupProperties, String> title;

    @FXML
    private TableColumn<GroupProperties, Double> cost;

    @FXML
    private TableColumn<GroupProperties, Integer> lesson;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    void initialize() {
        initializeTable();
        fillTableGroup();

        btnAdd.setOnAction(event -> {
            insertNewGroup();
        });
        
        btnDelete.setOnAction(event -> {
            try{
               deleteGroup();
            } catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("You must select group from table!");
                alert.showAndWait();
            } finally {
                fillTableGroup();
                cleaningFields();
            }
        });

    }

    private void initializeTable() {
        id.setCellValueFactory(new PropertyValueFactory<GroupProperties, Long>("ID"));
        title.setCellValueFactory(new PropertyValueFactory<GroupProperties, String>("name"));
        cost.setCellValueFactory(new PropertyValueFactory<GroupProperties, Double>("costInHour"));
        lesson.setCellValueFactory(new PropertyValueFactory<GroupProperties, Integer>("hourInDay"));
    }

    private long fillTableGroup() {
        CommandGroupProperties commandGetAll = null;
        try {
            commandGetAll = new CommandGroupProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ObservableList<GroupProperties> groupPropertiesObservableList
                = FXCollections.observableList(commandGetAll.getAll());
        groupProper.setItems(groupPropertiesObservableList);

        return commandGetAll.nextID();
    }

    private GroupProperties newGroup() {
        GroupProperties newGroup = null;
        String name = null;
        long ID  = fillTableGroup();
        String strCost = null;
        double cost = 0;
        int lesson = 0;

        try{
            name = editTitle.getText();
            strCost = editCost.getText();
            cost = Double.parseDouble(strCost);
            lesson = Integer.parseInt(editLesson.getText());

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Check the entered data");
            alert.showAndWait();
        }

        if ((!name.isEmpty() && (cost != 0) && (lesson != 0) )) {

            newGroup = new GroupProperties(ID, name, cost, lesson);
            return newGroup;
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("You must input all fields");
            alert.showAndWait();
            return null;
        }

    }

    private void insertNewGroup() {
        CommandGroupProperties insertGroup;
        GroupProperties newGroupPropertie = null;
        newGroupPropertie = newGroup();

        if(newGroupPropertie != null) {
            try {
                insertGroup = new CommandGroupProperties();
                insertGroup.insert(newGroupPropertie);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                fillTableGroup();
                cleaningFields();
            }
        }

    }

    private void cleaningFields() {
        editTitle.clear();
        editCost.clear();
        editLesson.clear();
    }

    private boolean deleteGroup () {
        CommandGroupProperties delete = null;
        long id = getIdFromTable();

        if (id != 0) {
            try {
            delete = new CommandGroupProperties();
            delete.delete(id);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            return true;
        }} else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("You must select group from table!");
            alert.showAndWait();
            return false;
        }
    }

    private long getIdFromTable () {
        long id = 0;
        TableView.TableViewSelectionModel<GroupProperties> idselected = null;
        idselected = groupProper.getSelectionModel();
        id = idselected.getSelectedItem().getID();
        return id;

    }
}
