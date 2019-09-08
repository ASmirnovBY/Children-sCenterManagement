package com.smirnovsoft.centerserver.data;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class EmployeesTable {
    private static List<Employee> employeeList = null;
    private static final String pathDb = "DB/employee.json";
    private static Map<Long, Employee> mapEmployee = null;


   public static List<Employee> getEmployeeList() throws IOException {
        employeeList = new ArrayList<>();
        FileReader reader = null;
        JSONTokener tokener;
        JSONArray arrayEmployee;
        JSONObject object;

        try {
            reader = new FileReader(pathDb);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        tokener = new JSONTokener(reader);
        arrayEmployee = (JSONArray) tokener.nextValue();

        for(int i = 0; i < arrayEmployee.length(); i++) {
            object = ((JSONObject) arrayEmployee.get(i));
            long id = object.getLong("id");
            String firstName = object.getString("firstName");
            String lastName = object.getString("lastName");
            String position = object.getString("position").toString();
            double salary = object.getDouble("salary");
            String phone = object.getString("phone");
            String birth = object.getString("birth").toString();
            String startWork = object.getString("startWork").toString();
            LocalDate birthL = LocalDate.parse(birth);
            LocalDate startL = LocalDate.parse(startWork);
            employeeList.add(new Employee(id, firstName, lastName, position, salary, phone, birthL, startL));
        }

        reader.close ();
        mapEmployee = new HashMap<>();

       for(int i = 0; i < employeeList.size(); i++) {
           mapEmployee.put(employeeList.get(i).getId(),employeeList.get(i));
       }


       return employeeList;
    }

    public static Map<Long, Employee> getMapEmployee(){
       return mapEmployee;
    }
}
