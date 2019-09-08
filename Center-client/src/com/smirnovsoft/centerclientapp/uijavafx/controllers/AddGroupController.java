package com.smirnovsoft.centerclientapp.uijavafx.controllers;

import com.smirnovsoft.centerclientapp.data.*;
import com.smirnovsoft.centerclientapp.data.groups.GroupProperties;
import com.smirnovsoft.centerclientapp.data.groups.Groups;
import com.smirnovsoft.centerclientapp.uijavafx.base.WindowProperties;
import com.smirnovsoft.centerclientapp.uijavafx.base.WindowsManager;
import com.smirnovsoft.centerclientapp.utils.dbutils.CommandCustomer;
import com.smirnovsoft.centerclientapp.utils.dbutils.CommandEmployee;
import com.smirnovsoft.centerclientapp.utils.dbutils.CommandGroup;
import com.smirnovsoft.centerclientapp.utils.dbutils.CommandGroupProperties;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;


public class AddGroupController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label lStart;

    @FXML
    private TableColumn<Customer, String> nameChild;

    @FXML
    private TableView<Customer> tableChild;

    @FXML
    private TableColumn<Customer, Long> IDChild;

    @FXML
    private TableColumn<Employee, Double> salary;

    @FXML
    private DatePicker pickEnd;

    @FXML
    private DatePicker pickStart;

    @FXML
    private Label lChildren;

    @FXML
    private TableView<Employee> tableTeacher;

    @FXML
    private ComboBox<GroupProperties> pickGroup;

    @FXML
    private TableColumn<Employee, String> name;

    @FXML
    private Label labelNameGroup;

    @FXML
    private TableColumn<Employee, Long> ID;

    @FXML
    private TableColumn<Employee, String> position;

    @FXML
    private TableColumn<Customer, Integer> ageChild;

    @FXML
    private TableView<Customer> tableChildren;

    @FXML
    private TableColumn<Customer, String> childsNameColum;

    @FXML
    private TableColumn<Customer, Integer> childsAgeColum;

    @FXML
    private Label lEnd;

    @FXML
    private Label lTeacher;

    @FXML
    private Button btnFormate;

    @FXML
    private Button clearTable;

    @FXML
    private Button save;

    @FXML
    private Button btnOpt;

    @FXML
    private Button btnR;

    private Groups newGroupInDB;
    private List<Employee> allTeachers;
    private List<Customer> allChild;
    private List<Customer> childInGroup;
    private List<GroupProperties> groupPicker;

    @FXML
    public void initialize() {
        initializeTableTeacher();
        initializeTableChilde();
        initializeTableChildren();
        fillTableEmployees();
        fillTableChild();
        initilizePickGroup();
        disableBtn();
        initMultiPickChildrenTable();
        pickStart.setEditable(false);
        pickEnd.setEditable(false);

        btnFormate.setOnAction(event -> {
            Groups group = createNewGroup();
            newGroupInDB = null;

            try {
                labelNameGroup.setText("Group title : " + group.getGroupTitle());
                lTeacher.setText("Teacher : " + group.getTeacherName());
                lEnd.setText("End : " + group.getEnd());
                lStart.setText("Start : " + group.getStart());
                fillChildrenInGroup(group);
                this.newGroupInDB = group;
                loadTimeTableWindow(group);
                enableBtn();
                group = null;
                clear();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Incorrect input, you must inputed all fields! ");
                alert.showAndWait();
            }
        });

        btnOpt.setOnAction(event -> {
            WindowsManager.getManager().showWindow(WindowProperties.GroupPropWindow);
        });

        btnR.setOnAction(event -> {
            initilizePickGroup();
        });

        save.setOnAction(event -> {
            CommandGroup cmdGroup = null;
            try {
                cmdGroup = new CommandGroup();
                cmdGroup.insert(newGroupInDB);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                save.setDisable(true);
                clearTable.setDisable(false);
                initialStartLabel();
                cmdGroup = null;
                newGroupInDB = null;
                disableBtn();
                clear();
            }
            pickStart.setEditable(false);
        });

        clearTable.setOnAction(event -> {
            newGroupInDB = null;
            initialStartLabel();
            disableBtn();
        });
    }

    private void initializeTableTeacher() {
        //иницилизация для TableColumn
        ID.setCellValueFactory(new PropertyValueFactory<Employee, Long>("id"));
        name.setCellValueFactory(new PropertyValueFactory<Employee, String>("lastName"));
        position.setCellValueFactory(new PropertyValueFactory<Employee, String>("position"));
        salary.setCellValueFactory(new PropertyValueFactory<Employee, Double>("salary"));
    }

    private void initializeTableChilde() {
        //иницилизация для TableColumn
        IDChild.setCellValueFactory(new PropertyValueFactory<Customer, Long>("ID"));
        nameChild.setCellValueFactory(new PropertyValueFactory<Customer, String>("childLastName"));
        ageChild.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("age"));
    }

    private void initializeTableChildren() {
        //иницилизация для TableColumn
        childsNameColum.setCellValueFactory(new PropertyValueFactory<Customer, String>("childLastName"));
        childsAgeColum.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("age"));
    }

    private void fillTableEmployees() {
        CommandEmployee commandEmployeeGetAll = null;
        try {
            commandEmployeeGetAll = new CommandEmployee();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ObservableList<Employee> employeeObservableList = FXCollections.observableList(commandEmployeeGetAll.getAll());
        tableTeacher.setItems(employeeObservableList);
        this.allTeachers = new ArrayList<Employee>(employeeObservableList);
    }

    private void fillTableChild() {
        CommandCustomer cmdGetAllChild = null;
        try {
            cmdGetAllChild = new CommandCustomer();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ObservableList<Customer> customerObservableList = FXCollections.observableList(cmdGetAllChild.getAll());
        tableChild.setItems(customerObservableList);
        this.allChild = new ArrayList<Customer>(customerObservableList);
    }

    private void clear() {
        pickGroup.getEditor().clear();
        pickStart.getEditor().clear();
        pickEnd.getEditor().clear();
    }

    private Employee getTeacher() {
        TableView.TableViewSelectionModel<Employee> selectionModel = null;
        selectionModel = tableTeacher.getSelectionModel();
        int index = selectionModel.getSelectedIndex();

        if (index != -1) {
            return selectionModel.getSelectedItem();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("You must choose teacher on the table!");
            alert.showAndWait();
            return null;
        }
    }

    private List<Customer> getSelectedChild() {
        childInGroup = new ArrayList<>();
        childInGroup = initMultiPickChildrenTable();
        return childInGroup;
    }

    private void fillChildrenInGroup(Groups group) {
        if (group != null) {
            ObservableList<Customer> observableList = FXCollections.observableList(group.getChildrenInfo());
            tableChildren.setItems(observableList);
        }
    }

    private void initilizePickGroup() {
        groupPicker = new ArrayList<>();
        CommandGroupProperties all = null;
        try {
            all = new CommandGroupProperties();
            groupPicker = all.getAll();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            ObservableList<GroupProperties> listGroup = FXCollections.observableArrayList(groupPicker);
            pickGroup.setItems(listGroup);
            pickGroup.setConverter(new StringConverter<GroupProperties>() {
                @Override
                public String toString(GroupProperties object) {
                    return object.getName();
                }

                @Override
                public GroupProperties fromString(String string) {
                    return null;
                }
            });
        }
    }

    private void disableBtn() {
        save.setDisable(true);
        clearTable.setDisable(true);
    }

    private void enableBtn() {
        save.setDisable(false);
        clearTable.setDisable(false);
    }

    private List<Customer> initMultiPickChildrenTable() {
        MultipleSelectionModel<Customer> multipleChild = null;
        multipleChild = tableChild.getSelectionModel();
        multipleChild.setSelectionMode(SelectionMode.MULTIPLE);
        return multipleChild.getSelectedItems();
    }

    private Groups createNewGroup() {
        Groups group = null;
        CommandGroup cmdGroup = null;
        GroupProperties propertyGroup = null;
        Employee teacher = null;
        List<Customer> children = null;

        try {
            cmdGroup = new CommandGroup();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            teacher = getTeacher();
            propertyGroup = pickGroup.getValue();
            long ID = cmdGroup.nextID();
            String groupTitle = propertyGroup.getName();
            double groupCost = propertyGroup.getCostInHour();
            LocalDate start = pickStart.getValue();
            LocalDate end = pickEnd.getValue();
            String teacherName = teacher.getFirstName() + " " + teacher.getLastName();
            double teacherSalary = teacher.getSalary();
            children = getSelectedChild();

            if ((teacher != null) && (propertyGroup != null)
                    && (children.size() > 0)) {
                group = new Groups(ID, groupTitle, groupCost, start, end, teacherName,
                        teacherSalary, children);

            }

        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Incorrect input, try again !");
            alert.showAndWait();
        }

        return group;
    }

    private void initialStartLabel() {
        labelNameGroup.setText("Group title :");
        lTeacher.setText("Teacher :");
        lEnd.setText("End :");
        lStart.setText("Start :");
        tableChildren.setItems(null);
    }

    private void loadTimeTableWindow(Groups group) {
        URL fxmlLocation = getClass().getResource("/com/smirnovsoft/centerclientapp/uijavafx/view/timetable.fxml");
        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        AnchorPane page = null;

        try {
            page = (AnchorPane) loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage stage = new Stage();
        stage.setTitle("Time table");
        stage.setScene(new Scene(page, 700, 400));
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        TimeTableController controller = loader.getController();
        controller.setGroup(group);
        stage.show();
    }

}

