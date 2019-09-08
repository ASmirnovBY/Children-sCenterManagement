package com.smirnovsoft.centerclientapp.utils.dbutils;

import com.smirnovsoft.centerclientapp.data.Customer;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CommandCustomer implements RequestCommand<Customer> {
    private static final String nameDB = "customers";
    private Customer customer;
    private List<Customer> customerList = null;
    private JsonRequestBuilder jsonBuilder = new JsonRequestBuilder();

    public CommandCustomer() throws IOException {
    }

    @Override
    public Customer insert(Customer customer) throws IOException {

        if (customer != null) {
            jsonBuilder.getWriter().object().key("db").value(nameDB)
                    .key("command").value("insert")
                    .key("ID").value(customer.getID())
                    .key("childFirstName").value(customer.getChildFirstName())
                    .key("childLastName").value(customer.getChildLastName())
                    .key("childBirth").value(customer.getChildBirth())
                    .key("parentName").value(customer.getParentName())
                    .key("parentMobilePhone").value(customer.getParentMobilePhone())
                    .endObject();

            jsonBuilder.flushOutput();
            jsonBuilder.closeWriter();
            return customer;
        }
            return null;
    }

    @Override
    public List<Customer> getAll() {
        customerList = new ArrayList<>();
        //Пишу запрос
        jsonBuilder.getWriter().object().key("db").value(nameDB)
                .key("command").value("getall")
                .endObject();
        jsonBuilder.flushOutput();
        // получаю ответ и записываю
        try {
            JSONArray arrayCustomer = (JSONArray) jsonBuilder.getReader().nextValue();

            for(int i = 0; i < arrayCustomer.length(); i++) {
                JSONObject customerJsonObj = (JSONObject) arrayCustomer.get(i);
                long id = customerJsonObj.getLong("ID");
                String childFirstName = customerJsonObj.getString("childFirstName");
                String childLastName = customerJsonObj.getString("childLastName");
                String strBirth = customerJsonObj.getString("childBirth");
                String parentName = customerJsonObj.getString("parentName");
                String parentMobilePhone = customerJsonObj.getString("parentMobilePhone");
                LocalDate birth = LocalDate.parse(strBirth);
                customer = new Customer(id, childFirstName, childLastName, parentName, parentMobilePhone, birth);
                customerList.add(customer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        jsonBuilder.closeWriter();
        return customerList;
    }

    @Override
    public boolean delete(long ID) {
        if (ID != 0) {
            jsonBuilder.getWriter().object().key("db").value(nameDB)
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
    public long nextID() {
        long maxID = 0;
        for (int i = 0; i < customerList.size(); i++) {
            if(maxID < customerList.get(i).getID()) {
                maxID = customerList.get(i).getID();
            }
        }
        return maxID + 1;
    }
}
