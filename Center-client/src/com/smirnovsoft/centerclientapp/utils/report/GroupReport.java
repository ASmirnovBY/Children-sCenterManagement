package com.smirnovsoft.centerclientapp.utils.report;

import com.smirnovsoft.centerclientapp.data.Customer;
import com.smirnovsoft.centerclientapp.data.groups.Groups;
import com.smirnovsoft.centerclientapp.data.groups.OneDayBuilder;
import com.smirnovsoft.centerclientapp.data.groups.TimeTable;
import org.apache.poi.xwpf.usermodel.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

public class GroupReport implements Reporteble<Groups> {
    private String FONT = "Times new roman";
    private int MAIN_SIZE = 20;
    private int CUSTOM_SIZE = 14;
    private int TABLE_SIZE = 14;

    private Groups group;
    private XWPFDocument doc;
    private XWPFRun runTitle;
    private XWPFRun runInformation;
    private XWPFTable childrenTable;
    private XWPFTable timeTable;

    public GroupReport() {
    }

    @Override
    public XWPFDocument createDocument(Groups group) {
        this.group = group;
        if (group != null) {
            doc = new XWPFDocument();
            createParagraph();
        }
        return doc;
    }

    @Override
    public void save(File file) {
        if(file != null) {
            FileOutputStream reportFile = null;
            try {
                reportFile = new FileOutputStream(file);
                if(doc != null) {
                    doc.write(reportFile);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    reportFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private  void createParagraph() {
        XWPFParagraph title = doc.createParagraph();
        title.setAlignment(ParagraphAlignment.CENTER);
        runTitle = title.createRun();
        runTitle.setText("Group title : " + group.getGroupTitle());
        runTitle.setFontFamily(FONT);
        runTitle.setFontSize(MAIN_SIZE);
        runTitle.addBreak();

        XWPFParagraph groupInfo = doc.createParagraph();
        groupInfo.setAlignment(ParagraphAlignment.LEFT);
        runInformation = groupInfo.createRun();
        runInformation.setFontFamily(FONT);
        runInformation.setFontSize(CUSTOM_SIZE);
        runInformation.setText("Teacher : " + group.getTeacherName());
        runInformation.addTab();
        runInformation.setText("Start : " + group.getStart().
                                            format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));
        runInformation.addTab();
        runInformation.setText("End : " + group.getEnd().
                                            format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));
        runInformation.addBreak();

        XWPFParagraph childrenList = doc.createParagraph();
        childrenList.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun runChildrenList = childrenList.createRun();
        runChildrenList = childrenList.createRun();
        runChildrenList.setFontFamily(FONT);
        runChildrenList.setFontSize(CUSTOM_SIZE);
        runChildrenList.setText("Children list");

        List<Customer> childrenInfo = group.getChildrenInfo();
        int row = group.getChildrenInfo().size() + 1;
        int cols = 4;
        childrenTable = doc.createTable(row, cols);
        childrenTable.setWidth("auto");
        childrenTable.setCellMargins(100,100,100,100);
        childrenTable.getRow(0).getCell(0).setText("Child name");
        childrenTable.setTableAlignment(TableRowAlign.CENTER);
        childrenTable.getRow(0).setHeight(50);
        childrenTable.getRow(0).getCell(1).setText("Age");
        childrenTable.getRow(0).getCell(2).setText("Parent name");
        childrenTable.getRow(0).getCell(3).setText("Parent number");

        for (int i = 0; i < row - 1 ; i++) {
            XWPFTableRow child = childrenTable.getRow(i + 1);
            child.getCell(0).setWidth("auto");
            child.getCell(0).setText(childrenInfo.get(i).getFullName());
            child.getCell(1).setText(String.valueOf(childrenInfo.get(i).getAge()));
            child.getCell(2).setText(childrenInfo.get(i).getParentName());
            child.getCell(3).setText(childrenInfo.get(i).getParentMobilePhone());
        }

        XWPFParagraph time = doc.createParagraph();
        time.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun runTime = childrenList.createRun();
        runTime = time.createRun();
        runTime.setFontFamily(FONT);
        runTime.setFontSize(CUSTOM_SIZE);
        runTime.setText("Time table");

        List<OneDayBuilder> daysTimes = group.getTimeTable().getDaysTimes();
        int rowsTime = daysTimes.size() + 1;
        int colsTime = 4;
        timeTable = doc.createTable(rowsTime, colsTime);
        timeTable.setWidth("auto");
        timeTable.setCellMargins(100,100,100,100);
        timeTable.getRow(0).getCell(0).setText("Day of week");
        timeTable.setTableAlignment(TableRowAlign.CENTER);
        timeTable.getRow(0).getCell(1).setText("Date");
        timeTable.getRow(0).getCell(2).setText("Start time");
        timeTable.getRow(0).getCell(3).setText("End time");

        for (int i = 0; i < rowsTime - 1; i++ ) {
            XWPFTableRow times = timeTable.getRow(i + 1);
            times.getCell(0).setText(daysTimes.get(i).getDayOfWeek());
            times.getCell(1).setText(daysTimes.get(i).getDay()
                                    .format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
            times.getCell(2).setText(daysTimes.get(i).getStartTime()
                                    .format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)));
            times.getCell(3).setText(daysTimes.get(i).getEndTime()
                                    .format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)));

        }
    }

}
