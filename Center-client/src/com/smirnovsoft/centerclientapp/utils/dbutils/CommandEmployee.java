package com.smirnovsoft.centerclientapp.utils.dbutils;

import com.smirnovsoft.centerclientapp.data.Employee;
import com.smirnovsoft.centerclientapp.data.Position;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CommandEmployee implements RequestCommand<Employee> {

    private Employee employee;
    private JsonRequestBuilder jsonBuilder = new JsonRequestBuilder();
    private List<Employee> employeeList = null;

    public CommandEmployee() throws IOException {
    }

    @Override
    public Employee insert(Employee employee) throws IOException {

        if (employee != null) {
            jsonBuilder.getWriter().object().key("db").value("employee")
                    .key("command").value("insert")
                    .key("id").value(employee.getId())
                    .key("firstName").value(employee.getFirstName())
                    .key("lastName").value(employee.getLastName())
                    .key("position").value(employee.getPosition())
                    .key("salary").value(employee.getSalary())
                    .key("phone").value(employee.getPhone())
                    .key("birth").value(employee.getBirth())
                    .key("startWork").value(employee.getStartWork())
                    .endObject();

            jsonBuilder.flushOutput();
            jsonBuilder.closeWriter();
            return employee;
        }

         return null;
    }

    @Override
    public List<Employee> getAll() {
        employeeList = new ArrayList<>();
        //Пишу запрос
        jsonBuilder.getWriter().object().key("db").value("employee")
                                        .key("command").value("getall")
                                        .endObject();
        jsonBuilder.flushOutput();
        // получаю ответ

        try {
            JSONArray arrayEmployee = (JSONArray) jsonBuilder.getReader().nextValue();

            for(int i = 0; i < arrayEmployee.length(); i++) {

                JSONObject employeeJsonObj = (JSONObject) arrayEmployee.get(i);
                long id = employeeJsonObj.getLong("id");
                String firstName = employeeJsonObj.getString("firstName");
                String lastName = employeeJsonObj.getString("lastName");
                String position = employeeJsonObj.getString("position");
                double salary = employeeJsonObj.getDouble("salary");
                String phone = employeeJsonObj.getString("phone");
                String strBirth = employeeJsonObj.getString("birth");
                String strStartWork = employeeJsonObj.getString("startWork");
                LocalDate birth = LocalDate.parse(strBirth);
                LocalDate startWork = LocalDate.parse(strBirth);
                employee = new Employee(id,firstName, lastName, position, salary,phone, birth, startWork);
                employeeList.add(employee);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        jsonBuilder.closeWriter();
        return employeeList;
    }

    @Override
    public boolean delete(long ID) {
        if (ID != 0) {
            jsonBuilder.getWriter().object().key("db").value("employee")
                    .key("command").value("delete")
                    .key("ID").value(ID)
                    .endObject();
            jsonBuilder.flushOutput();
            jsonBuilder.closeWriter();
            return true;
        }
        return false;
    }

    @Override
    public long nextID () {
        long maxID = 0;
        for (int i = 0; i < employeeList.size(); i++) {
            if(maxID < employeeList.get(i).getId()) {
                maxID = employeeList.get(i).getID();
            }
        }
        return maxID + 1;

    }
}
