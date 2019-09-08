package com.smirnovsoft.centerserver.utils.dbutils;

import com.smirnovsoft.centerserver.data.Customer;
import com.smirnovsoft.centerserver.data.CustomersTable;
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

public class ProccessorCustomer extends ProccessorSpecific implements QueryCommand<Customer> {

    private static final String pathDb = "DB/customers.json";
    private JSONTokener tokener;
    private JSONObject object;
    private List<Customer> customers;
    private OutputStreamWriter outputStreamWriter;
    private JSONWriter writer;
    private JSONArray jsonArray;
    private Map<Long, Customer> mapCustomer;

    public ProccessorCustomer(JSONTokener tokener, JSONObject object) {
        this.tokener = tokener;
        this.object = object;
    }

    @Override
    public void insert() throws IOException {
        customers = new ArrayList<>();
        customers = CustomersTable.getCustomerList();
        FileWriter writer = new FileWriter(pathDb);
        long id = object.getLong("ID");
        String childFirstName = object.getString("childFirstName");
        String childLastName = object.getString("childLastName");
        String strChildBirth = object.getString("childBirth");
        LocalDate childBirth = LocalDate.parse(strChildBirth);
        String parentName = object.getString("parentName");
        String parentMobilePhone = object.getString("parentMobilePhone");
        object = null;
        customers.add(new Customer(id, childFirstName, childLastName, parentName, parentMobilePhone, childBirth));

        JSONArray arrayCustomer = new JSONArray(customers);
        arrayCustomer.write(writer);
        writer.flush();
        writer.close();
        ServerConnection.closeSocket();

    }

    @Override
    public List<Customer> getAll() {
        try {
            jsonArray = new JSONArray(CustomersTable.getCustomerList());
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

        return customers;
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
        try {
            mapCustomer = CustomersTable.getMapCustomers();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        mapCustomer.remove(id);
        customers = new ArrayList<>(mapCustomer.values());
        jsonArray = new JSONArray(customers);
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
