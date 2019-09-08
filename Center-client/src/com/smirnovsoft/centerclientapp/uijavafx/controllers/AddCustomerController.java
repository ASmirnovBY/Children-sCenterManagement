package com.smirnovsoft.centerclientapp.uijavafx.controllers;

import com.smirnovsoft.centerclientapp.data.Customer;
import com.smirnovsoft.centerclientapp.utils.dbutils.CommandCustomer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;


public class AddCustomerController{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField editChildLastName;

    @FXML
    private DatePicker pickDateOfBirth;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnAdd;

    @FXML
    private TextField editMobilePhone;

    @FXML
    private TextField editParentName;

    @FXML
    private TextField editChildFirstName;

    @FXML
    private TableView<Customer> tableCustomer;

    @FXML
    private TableColumn<Customer, String> parentName;

    @FXML
    private TableColumn<Customer, String> mobilePhone;

    @FXML
    private TableColumn<Customer, String> childLastName;

    @FXML
    private TableColumn<Customer, LocalDate> dateOfBirth;

    @FXML
    private TableColumn<Customer, String> childFirstName;

    @FXML
    private TableColumn<Customer, Long> id;

    @FXML
    private TableColumn<Customer, Integer> age;

    private long nextID;


    @FXML
    void initialize() {
        initializeTable();
        fillTableCustomers();

        btnAdd.setOnAction(event -> {
          insertCustomer();
        });

        btnDelete.setOnAction(event -> {
            try{
                deleteCustomerFromTable();
            } catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("You must select customer from table!");
                alert.showAndWait();
            } finally {
                fillTableCustomers();
                cleaningFields();
            }
            });
    }

    private void initializeTable() {
        //иницилизация для TableColumn
        id.setCellValueFactory(new PropertyValueFactory<Customer, Long>("ID"));
        childFirstName.setCellValueFactory(new PropertyValueFactory<Customer, String>("childFirstName"));
        childLastName.setCellValueFactory(new PropertyValueFactory<Customer, String>("childLastName"));
        dateOfBirth.setCellValueFactory(new PropertyValueFactory<Customer, LocalDate>("childBirth"));
        parentName.setCellValueFactory(new PropertyValueFactory<Customer, String >("parentName"));
        mobilePhone.setCellValueFactory(new PropertyValueFactory<Customer, String>("parentMobilePhone"));
        age.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("age"));
    }

    private long fillTableCustomers(){
        CommandCustomer comand = null;
        try {
            comand = new CommandCustomer();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ObservableList<Customer> customersObservableList = FXCollections.observableList(comand.getAll());
        tableCustomer.setItems(customersObservableList);

        return comand.nextID();

    }

    private void insertCustomer(){
        CommandCustomer commandCustomer;
        Customer customer = null;
        customer = createCustomer();

        if(customer != null) {
            try {
                commandCustomer = new CommandCustomer();
                commandCustomer.insert(customer);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                fillTableCustomers();
                cleaningFields();
            }
        }
    }

    private Customer createCustomer(){
        Customer customer = null;
        String cFirstName = null;
        String cLastName = null;
        LocalDate birth = null;
        String pName = null;
        String pMobilePhone = null;
        nextID = 0;

        try{
            cFirstName = editChildFirstName.getText();
            cLastName = editChildLastName.getText();
            birth = pickDateOfBirth.getValue();
            pName = editParentName.getText();
            pMobilePhone = editMobilePhone.getText();
            nextID = fillTableCustomers();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Check the entered data");
            alert.showAndWait();
        }

        if ((!cFirstName.isEmpty()) && (!cLastName.isEmpty()) && (!pName.isEmpty())
                                    && (birth != null) && (!pMobilePhone.isEmpty())) {

            customer = new Customer(nextID, cFirstName, cLastName, pName, pMobilePhone, birth);
            return customer;

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

    private void cleaningFields() {
       editChildFirstName.clear();
       editChildLastName.clear();
       editMobilePhone.clear();
       editParentName.clear();
       pickDateOfBirth.getEditor().clear();
    }

    private boolean deleteCustomerFromTable(){
        long id = 0;
        id = getIdFromTable();
        CommandCustomer cmdDelete = null;
        try {
            cmdDelete = new CommandCustomer();
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
        TableView.TableViewSelectionModel<Customer> selectionModel = null;
        long customerId = 0L;
        selectionModel = tableCustomer.getSelectionModel();
        customerId = selectionModel.getSelectedItem().getID();
        return customerId;
    }

}
