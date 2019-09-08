package com.smirnovsoft.centerclientapp.uijavafx.controllers;
import com.jfoenix.controls.JFXTimePicker;
import com.smirnovsoft.centerclientapp.data.groups.Groups;
import com.smirnovsoft.centerclientapp.data.groups.OneDayBuilder;
import com.smirnovsoft.centerclientapp.data.groups.TimeTable;
import com.smirnovsoft.centerclientapp.utils.dbutils.CommandDateTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TimeTableController {

    private Groups group;
    private TimeTable timeTable = null;
    private List<OneDayBuilder> daysTimes;
    private ObservableList<OneDayBuilder> observableDay;


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label lStart;

    @FXML
    private Label lEnd;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnAddOnTable;

    @FXML
    private Button btnDelete;

    @FXML
    private ListView<LocalDate> listDays;

    @FXML
    private Label lPickedDate;

    @FXML
    private Label lTimeTable;

    @FXML
    private Label lPeriod;

    @FXML
    private JFXTimePicker pickerStart;

    @FXML
    private JFXTimePicker pickerEnd;

    @FXML
    private TableView<OneDayBuilder> tableTime;

    @FXML
    private TableColumn<OneDayBuilder, String> dayof;

    @FXML
    private TableColumn<OneDayBuilder, LocalDate> dayInTable;

    @FXML
    private TableColumn<OneDayBuilder, LocalTime> timeStart;

    @FXML
    private TableColumn<OneDayBuilder, LocalTime> timeEnd;

    @FXML
    void initialize() {
        pickerStart.set24HourView(true);
        pickerEnd.set24HourView(true);
        pickerStart.setEditable(false);
        pickerEnd.setEditable(false);
        initialCellTimeTable();

        btnAddOnTable.setOnAction(event -> {
            listDays.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            LocalDate selectedDate = listDays.getSelectionModel().getSelectedItem();
            LocalTime sTime = pickerStart.getValue();
            LocalTime eTime = pickerEnd.getValue();
            if ((selectedDate != null) && (sTime!= null)
                            && (eTime != null) ) {

                addOneDay(selectedDate, sTime, eTime);
                setDateInTable();

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("You must choose date in Period table or input time!");
                alert.showAndWait();
            }
        });

        btnDelete.setOnAction(event -> {
            tableTime.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            OneDayBuilder day = tableTime.getSelectionModel().getSelectedItem();
            deleteDay(day);
            setDateInTable();
        });

        btnSave.setOnAction(event -> {
            ObservableList<OneDayBuilder> allDay = tableTime.getItems();
            if (allDay.size() != 0) {
                List<OneDayBuilder> listDaysTimes = new ArrayList<>(allDay);
                timeTable.setDaysTimes(listDaysTimes);
                CommandDateTable insertTimeTable = null;
                try {
                    insertTimeTable = new CommandDateTable();
                    insertTimeTable.insert(timeTable);
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    btnSave.setDisable(true);
                    btnDelete.setDisable(true);
                    btnAddOnTable.setDisable(true);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("OK");
                    alert.setHeaderText(null);
                    alert.setContentText("Time table for this group save, you can close this window");
                    alert.showAndWait();

                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("You must create time table!");
                alert.showAndWait();
            }
        });

    }

    protected void setGroup(Groups group) {
        timeTable = null;
        this.group = group;
        lStart.setText("Start : " + group.getStart().toString());
        lEnd.setText("End : " + group.getEnd().toString());
        long id = group.getID();
        LocalDate start = group.getStart();
        LocalDate end = group.getEnd();
        TimeTable oneGroupTime = new TimeTable(id, start, end);
        timeTable = oneGroupTime;
        List<LocalDate> allPeriod = oneGroupTime.allPeriod();
        ObservableList period = FXCollections.observableList(allPeriod);

        listDays.setItems(period);
        listDays.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listDays.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            lPickedDate.setText("Picked date : " + newValue + " " + newValue.getDayOfWeek());
        });
    }

    private void addOneDay(LocalDate date, LocalTime start, LocalTime end) {
        OneDayBuilder oneDay = null;
        oneDay = new OneDayBuilder(date, start, end );
        timeTable.addDay(oneDay);
    }

    private void deleteDay (OneDayBuilder day) {
        timeTable.getDaysTimes().remove(day);
    }

    private void initialCellTimeTable() {
        dayof.setCellValueFactory(new PropertyValueFactory<OneDayBuilder, String>("dayOfWeek"));
        timeStart.setCellValueFactory(new PropertyValueFactory<OneDayBuilder, LocalTime>("startTime"));
        timeEnd.setCellValueFactory(new PropertyValueFactory<OneDayBuilder, LocalTime>("endTime"));
        dayInTable.setCellValueFactory(new PropertyValueFactory<OneDayBuilder, LocalDate>("day"));
        tableTime.setEditable(false);
    }

    private void setDateInTable() {
        daysTimes = timeTable.getDaysTimes();
        observableDay = FXCollections.observableList(daysTimes);
        tableTime.setItems(observableDay);
    }

}
