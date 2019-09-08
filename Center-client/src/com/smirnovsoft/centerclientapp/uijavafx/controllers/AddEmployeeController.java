package com.smirnovsoft.centerclientapp.uijavafx.controllers;

import com.smirnovsoft.centerclientapp.data.Employee;
import com.smirnovsoft.centerclientapp.data.Position;
import com.smirnovsoft.centerclientapp.utils.dbutils.CommandEmployee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddEmployeeController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField editLastName;

    @FXML
    private DatePicker pickDateOfBirth;

    @FXML
    private TextField editFirstName;

    @FXML
    private ComboBox<Position> pickPosition;

    @FXML
    private TextField editSalary;

    @FXML
    private TextField editMobile;

    @FXML
    private DatePicker pickStartWork;

    @FXML
    private Button btnAdd;

    @FXML
    private TableView tableEmloyee;

    @FXML
    private TableColumn<Employee, Long> id;

    @FXML
    private TableColumn<Employee, String> lastName;

    @FXML
    private TableColumn<Employee, String> firstName;

    @FXML
    private TableColumn<Employee, String> position;

    @FXML
    private TableColumn<Employee, Double> salary;

    @FXML
    private TableColumn<Employee, String> phone;

    @FXML
    private TableColumn<Employee, LocalDate> birth;

    @FXML
    private TableColumn<Employee, LocalDate> startWork;

    @FXML
    private Button btnDelete;

    private long nextID;

    @FXML
    void initialize() {
        initializeTable();
        fillTableEmployees();
        //иницилизация выбора проффесии
        ObservableList<Position> positionList = FXCollections.observableArrayList(Position.values());
        pickPosition.setItems(positionList);

        btnAdd.setOnAction(event -> {
            insertEmployeeInDb();
        });

        btnDelete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                try{
                    deleteEmployeeFromTable();
                } catch (Exception e){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("You must select employee from table!");
                    alert.showAndWait();
                } finally {
                   fillTableEmployees();
                }
            }
        });
    }

   private void initializeTable() {
        //иницилизация для TableColumn
        id.setCellValueFactory(new PropertyValueFactory<Employee, Long>("id"));
        firstName.setCellValueFactory(new PropertyValueFactory<Employee, String>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<Employee, String>("lastName"));
        position.setCellValueFactory(new PropertyValueFactory<Employee, String>("position"));
        salary.setCellValueFactory(new PropertyValueFactory<Employee, Double>("salary"));
        phone.setCellValueFactory(new PropertyValueFactory<Employee, String>("phone"));
        birth.setCellValueFactory(new PropertyValueFactory<Employee, LocalDate>("birth"));
        startWork.setCellValueFactory(new PropertyValueFactory<Employee, LocalDate>("startWork"));

    }

   private long fillTableEmployees(){
        CommandEmployee commandEmployeeGetAll = null;
        try {
            commandEmployeeGetAll = new CommandEmployee();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ObservableList<Employee> employeeObservableList = FXCollections.observableList(commandEmployeeGetAll.getAll());
        tableEmloyee.setItems(employeeObservableList);

        return commandEmployeeGetAll.nextID();

    }

   private void cleaningFields() {
        editFirstName.clear();
        editLastName.clear();
        editMobile.clear();
        editSalary.clear();
        pickStartWork.getEditor().clear();
        pickDateOfBirth.getEditor().clear();
        pickPosition.getEditor().clear();
   }

   private boolean deleteEmployeeFromTable(){
        long id = 0;
        id = getIdFromTable();
        CommandEmployee cmdDelete = null;
        try {
            cmdDelete = new CommandEmployee();
            cmdDelete.delete(id);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (id == 0) {
            return false;
        }
        return true;
    }

   private long getIdFromTable(){
        TableView.TableViewSelectionModel<Employee> selectionModel = null;
        long employeeId = 0L;
        CommandEmployee cmd = null;
        selectionModel = tableEmloyee.getSelectionModel();
        employeeId = selectionModel.getSelectedItem().getID();
        return employeeId;
    }

   private Employee createEmployeeForDb() {
       Employee employee = null;
       String firstName = null;
       String lastName = null;
       String position = null;
       String strSalary = null;
       double salary = 0;
       String phone = null;
       LocalDate birth = null;
       LocalDate startWork = null;

       try {

           firstName = editFirstName.getText();
           lastName = editLastName.getText();
           position = pickPosition.getValue().toString();
           strSalary = editSalary.getText();
           salary = Double.parseDouble(strSalary);
           phone = editMobile.getText();
           birth = pickDateOfBirth.getValue();
           startWork = pickStartWork.getValue();
           nextID = fillTableEmployees();

       } catch (Exception e) {
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Error");
           alert.setHeaderText(null);
           alert.setContentText("Check the format entered data");
           alert.showAndWait();
       }

       if ((!firstName.isEmpty()) && (!lastName.isEmpty()) && (position != null)
               && (salary != 0) && (!phone.isEmpty())
               && (birth != null) && (startWork) != null) {

           employee = new Employee(nextID, firstName, lastName, position, salary,
                   phone, birth, startWork);

           return employee;

       } else {

           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Error");
           alert.setHeaderText(null);
           alert.setContentText("You must input all fields");
           alert.showAndWait();
           return null;
       }
   }

   private void insertEmployeeInDb() {
       CommandEmployee insertEmployee;
       Employee employee = createEmployeeForDb();

       if(employee != null) {
           try {
               insertEmployee = new CommandEmployee();
               insertEmployee.insert(employee);

           } catch (IOException e) {
               e.printStackTrace();
           } finally {
               fillTableEmployees();
               cleaningFields();
           }
       }

   }

}
