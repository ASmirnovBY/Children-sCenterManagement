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

public class CustomersTable {
    private static List<Customer> customersList = null;
    private static final String pathDb = "DB/customers.json";
    private static Map<Long, Customer> mapCustomers = null;


    public static List<Customer> getCustomerList() throws IOException {
        customersList = new ArrayList<>();
        FileReader reader = null;
        JSONTokener tokener;
        JSONArray arrayCustomer;
        JSONObject object;

        try {
            reader = new FileReader(pathDb);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        tokener = new JSONTokener(reader);
        arrayCustomer = (JSONArray) tokener.nextValue();

        for(int i = 0; i < arrayCustomer.length(); i++) {
            object = ((JSONObject) arrayCustomer.get(i));
            long id = object.getLong("ID");
            String childFirstName = object.getString("childFirstName");
            String childLastName = object.getString("childLastName");
            String strBirth = object.getString("childBirth");
            String parentName = object.getString("parentName");
            String parentMobilePhone = object.getString("parentMobilePhone");
            LocalDate birth = LocalDate.parse(strBirth);
            customersList.add(new Customer(id, childFirstName, childLastName, parentName, parentMobilePhone, birth));
        }

        reader.close ();
        mapCustomers = new HashMap<>();

        for(int i = 0; i < customersList.size(); i++) {
            mapCustomers.put(customersList.get(i).getID(),customersList.get(i));
        }

        return customersList;
    }

    public static Map<Long, Customer> getMapCustomers() throws IOException {
        return  mapCustomers;
    }

}
