package com.smirnovsoft.centerclientapp.data.groups;

import com.smirnovsoft.centerclientapp.utils.calculations.GainCalculator;
import com.smirnovsoft.centerclientapp.utils.dbutils.CommandDateTable;
import com.smirnovsoft.centerclientapp.utils.dbutils.CommandGroup;

import com.smirnovsoft.centerclientapp.utils.report.GroupReport;
import org.apache.poi.xwpf.usermodel.*;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class TestClass {

    public static void main(String[] args) throws IOException {
        CommandGroup gra = new CommandGroup();
        List<Groups> all = gra.getAll();


        try {
            GroupReport gr = new GroupReport();
            FileOutputStream outputStream = new FileOutputStream(new File("test.docx"));

            XWPFDocument document = gr.createDocument(all.get(0));
            document.write(outputStream);
            outputStream.close();

        } catch (Exception ex) {
            System.out.println("Ошибка");
        }
    }
}
