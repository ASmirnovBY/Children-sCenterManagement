package com.smirnovsoft.centerclientapp.uijavafx.controllers;

import com.smirnovsoft.centerclientapp.uijavafx.base.WindowProperties;
import com.smirnovsoft.centerclientapp.uijavafx.base.WindowsManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addEmployee;

    @FXML
    private Button addCustomer;

    @FXML
    private Button addGroup;

    @FXML
    private Button groupsManager;


    @FXML
    void initialize() {

        addEmployee.setOnAction(event -> {
           WindowsManager.getManager().showUpdateWindow(WindowProperties.AddEmployeeWindow);
        });

        addCustomer.setOnAction(event -> {
            WindowsManager.getManager().showUpdateWindow(WindowProperties.AddCustomerWindow);
        });

        addGroup.setOnAction(event -> {
             WindowsManager.getManager().showUpdateWindow(WindowProperties.AddGroupWindow);
        });

        groupsManager.setOnAction(event -> {
            WindowsManager.getManager().showUpdateWindow(WindowProperties.GroupsManager);
        });
    }

}
