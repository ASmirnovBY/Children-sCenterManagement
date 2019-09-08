package com.smirnovsoft.centerserver.utils.dbutils;

import com.smirnovsoft.centerserver.data.Employee;
import com.smirnovsoft.centerserver.data.EmployeesTable;
import com.smirnovsoft.centerserver.data.Position;
import com.smirnovsoft.centerserver.server.ServerConnection;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProcessorEmployee extends ProccessorSpecific implements QueryCommand<Employee> {

    private final String pathDb = "DB/employee.json";
    private JSONTokener tokener;
    private JSONObject object;
    private List<Employee> employees;
    private OutputStreamWriter outputStreamWriter;
    private JSONWriter writer;
    private JSONArray jsonArray;
    private Map<Long, Employee> mapEmployee;

    public ProcessorEmployee(JSONTokener tokener, JSONObject object) {
        this.tokener = tokener;
        this.object = object;
    }

    @Override
    public void insert() throws IOException {
        employees = new ArrayList<>();
        employees = EmployeesTable.getEmployeeList();
        FileWriter writer = new FileWriter(pathDb);
        int id = object.getInt("id");
        String firstName = object.getString("firstName");
        String lastName = object.getString("lastName");
        String position = object.getString("position");
        double salary = object.getDouble("salary");
        String phone = object.getString("phone");
        String strBirth = object.getString("birth");
        String strStartWork = object.getString("startWork");
        LocalDate birth = LocalDate.parse(strBirth);
        LocalDate startWork = LocalDate.parse(strStartWork);
        object = null;

       employees.add(new Employee(id, firstName, lastName, position, salary, phone,
               birth,startWork ));

        JSONArray arrayEmployee = new JSONArray(employees);
        arrayEmployee.write(writer);
        writer.flush();
        writer.close();
        ServerConnection.closeSocket();
    }

    @Override
    public List<Employee> getAll() {

        try {
            jsonArray = new JSONArray(EmployeesTable.getEmployeeList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            outputStreamWriter = new OutputStreamWriter(ServerConnection.getOutputSocket());
        } catch (IOException e) {
            e.printStackTrace();
        }

        jsonArray.write(outputStreamWriter);

        try {
            outputStreamWriter.flush();
            outputStreamWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return employees;
    }

    @Override
    public boolean delete() {
        FileWriter filewriter = null;
        try {
            filewriter = new FileWriter(pathDb);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("File not found");
        }

        long id = object.getLong("ID");
        mapEmployee = EmployeesTable.getMapEmployee();

        mapEmployee.remove(id);
        employees = new ArrayList<>(mapEmployee.values());
        jsonArray = new JSONArray(employees);
        jsonArray.write(filewriter);
        try {
            filewriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error");
        }

        return true;
    }

    @Override
    public void operation(String operation) {
        switch (operation) {
            case "insert":
                try {
                    insert();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "getall":
                getAll();
                break;
            case "delete":
                delete();
                break;
        }
    }
}
