package com.smirnovsoft.centerclientapp.uijavafx.controllers;

import com.jfoenix.controls.JFXListView;
import com.smirnovsoft.centerclientapp.data.Customer;
import com.smirnovsoft.centerclientapp.data.groups.Groups;
import com.smirnovsoft.centerclientapp.data.groups.OneDayBuilder;
import com.smirnovsoft.centerclientapp.data.groups.TimeTable;
import com.smirnovsoft.centerclientapp.uijavafx.base.WindowProperties;
import com.smirnovsoft.centerclientapp.uijavafx.base.WindowsManager;
import com.smirnovsoft.centerclientapp.utils.calculations.GainCalculator;
import com.smirnovsoft.centerclientapp.utils.dbutils.CommandDateTable;
import com.smirnovsoft.centerclientapp.utils.dbutils.CommandGroup;
import com.smirnovsoft.centerclientapp.utils.report.GroupReport;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.ResourceBundle;


public class GroupsManager {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Groups> tableGroups;

    @FXML
    private TableColumn<Groups, Long> groupsID;

    @FXML
    private TableColumn<Groups, String> groupTitle;

    @FXML
    private TableColumn<Groups, String> groupTeacher;

    @FXML
    private TableColumn<Groups, LocalDate> groupStart;

    @FXML
    private TableColumn<Groups, LocalDate> groupEnd;

    @FXML
    private TableColumn<Groups, Double> groupCost;

    @FXML
    private TableColumn<Groups, String> groupStatus ;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private JFXListView<OneDayBuilder> timeTable;

    @FXML
    private ListView<Customer> childrenList;

    @FXML
    private Label lDisbursement;

    @FXML
    private Label lGain;

    @FXML
    private Label lTotal;

    @FXML
    private Label lD;

    @FXML
    private Label lG;

    @FXML
    private Label lT;


    private List<TimeTable > allTimeTable;

    private Groups selectedGroup;

    private TimeTable concretGroupTimeTable;

    private GainCalculator oneGroupCalculate;

    private FileChooser saveChooser;

    private GroupReport report;

    @FXML
    void initialize() {
        saveWindow();
        listViewInit();
        initialGroupTable();


        btnDelete.setOnAction(event -> {
            deleteSelectedGroups();
            fillGroupTable();
        });

        btnSave.setOnAction(event -> {
            saveReportFile();
        });

    }

    private void initialGroupTable() {
        groupsID.setCellValueFactory(new PropertyValueFactory<Groups, Long>("ID"));
        groupTeacher.setCellValueFactory(new PropertyValueFactory<Groups, String>("teacherName"));
        groupTitle.setCellValueFactory(new PropertyValueFactory<Groups, String>("groupTitle"));
        groupStart.setCellValueFactory(new PropertyValueFactory<Groups, LocalDate>("start"));
        groupEnd.setCellValueFactory(new PropertyValueFactory<Groups, LocalDate>("end"));
        groupCost.setCellValueFactory(new PropertyValueFactory<Groups, Double>("groupCost"));
        groupStatus.setCellValueFactory(new PropertyValueFactory<Groups, String>("status"));
        fillGroupTable();
    }

    private void fillGroupTable() {
        CommandGroup groupCmd = null;
        try {
            groupCmd = new CommandGroup();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ObservableList<Groups> listGroups = FXCollections.observableList(groupCmd.getAll());
        tableGroups.setItems(listGroups);
    }

    private long getIdFromGroupsTable() {
        Groups group = null;
        TableView.TableViewSelectionModel<Groups> selectedGroups = null;
        long groupsId = 0;
        group = tableGroups.getSelectionModel().getSelectedItem();
        this.selectedGroup = group;

            if (group != null) {
                groupsId = group.getID();
            }

        return groupsId;
    }

    private void listViewInit() {

        tableGroups.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            fillChildrenList();
        } );

        tableGroups.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            initialTimeTable();
        } );

        tableGroups.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            gainCalculate();
        } );


    }

    private void deleteSelectedGroups() {

        if (selectedGroup != null) {
            CommandGroup deleteCmd = null;
            CommandDateTable deleteTable = null;
            try {
                deleteCmd = new CommandGroup();
                deleteCmd.delete(selectedGroup.getID());
                deleteTable = new CommandDateTable();
                deleteTable.delete(selectedGroup.getID());
                childrenList.setItems(null);
                timeTable.setItems(null);
                oneGroupCalculate = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("You must select group from table!");
            alert.showAndWait();
        }
    }

    private void fillChildrenList() {
        Groups children = null;
        children  = tableGroups.getSelectionModel().getSelectedItem();

        if (children != null) {

            ObservableList<Customer> cList = FXCollections.observableList(children.getChildrenInfo());
            childrenList.setItems(cList);
            childrenList.setCellFactory(param -> new ListCell<Customer>() {
                protected void updateItem(Customer item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null || item.getFullName() == null) {
                        setText(null);
                    } else {
                        setText(item.getFullName() + " " + item.getAge() + "years");
                    }
            }
            });
    }
}

    private void initialTimeTable () {
        CommandDateTable getAllTimeTable = null;
        try {
            getAllTimeTable = new CommandDateTable();
        } catch (IOException e) {
            e.printStackTrace();
        }
        allTimeTable = getAllTimeTable.getAll();
        List<OneDayBuilder> oneGroup = null;
        long ID = getIdFromGroupsTable();

        for (int i = 0; i < allTimeTable.size(); i++) {
            if (ID == allTimeTable.get(i).getGroupID()) {
                 oneGroup = allTimeTable.get(i).getDaysTimes();
                 concretGroupTimeTable = allTimeTable.get(i);
                 selectedGroup.setTimeTable(concretGroupTimeTable);
                 break;
            }
        }

        if (oneGroup != null) {

            ObservableList<OneDayBuilder> oneGroupTimeTable = FXCollections.observableList(oneGroup);

            timeTable.setItems(oneGroupTimeTable);
            timeTable.setCellFactory(param -> new ListCell<OneDayBuilder>() {
                @Override
                protected void updateItem(OneDayBuilder item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null || item.getDay() == null || item.getStartTime() == null
                                    || item.getEndTime() == null) {
                        setText(null);
                    } else {
                        setText(item.getDayOfWeek() + "\n " +
                                item.getDay().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG))
                                + "\n Start time  : " + item.getStartTime() +
                                "\n End time : " + item.getEndTime());
                    }
                }
            });

        }
    }

    private void gainCalculate() {
        lD.setText("");
        lG.setText("");
        lT.setText("");
        if(selectedGroup != null &&
           concretGroupTimeTable != null) {
           this.oneGroupCalculate = new GainCalculator(selectedGroup, concretGroupTimeTable);
        }

        if (oneGroupCalculate != null) {
            String disbursement = String.valueOf(oneGroupCalculate.getDisbursement());
            String total = String.valueOf(oneGroupCalculate.getTotal());
            String gain = String.valueOf(oneGroupCalculate.getGain());
            lD.setText(disbursement);
            lG.setText(gain);
            lT.setText(total);

        } else {
            lD.setText("");
            lG.setText("");
            lT.setText("");
        }

    }

    private void saveWindow() {
        report = new GroupReport();
        saveChooser = new FileChooser();
        saveChooser.setTitle("Save report");
        FileChooser.ExtensionFilter extensionFilter =
                new FileChooser.ExtensionFilter("MSDOCX(*.docx)", "*.docx");
        saveChooser.getExtensionFilters().add(extensionFilter);

    }

    private void saveReportFile() {
        if (selectedGroup != null) {
            File file = saveChooser.showSaveDialog(WindowsManager.getManager().getStage(WindowProperties.GroupsManager));
            report.createDocument(selectedGroup);
            report.save(file);
        }
    }
}