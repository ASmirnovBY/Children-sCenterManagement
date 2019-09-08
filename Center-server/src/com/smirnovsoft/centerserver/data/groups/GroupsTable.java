package com.smirnovsoft.centerserver.data.groups;

import com.smirnovsoft.centerserver.data.Customer;
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

public class GroupsTable {
    private static final String pathDb = "DB/groups.json";
    private static List<Groups> groupsList = null;
    private static Map<Long, Groups> mapGroups = null;

    public static List<Groups> getAllGroups() throws IOException {
        groupsList = new ArrayList<>();
        FileReader reader = null;
        JSONTokener tokener = null;
        JSONArray arrayGroups;
        JSONObject object;

        try {
            reader = new FileReader(pathDb);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }

        tokener = new JSONTokener(reader);
        arrayGroups = (JSONArray) tokener.nextValue();

        for(int i = 0; i < arrayGroups.length(); i++) {
            object = (JSONObject) arrayGroups.get(i);
            long ID = object.getLong("ID");
            String teacherName = object.getString("teacherName");
            double teacherSalary = object.getDouble("teacherSalary");
            double groupCost = object.getDouble("groupCost");
            String titleGroup = object.getString("groupTitle");
            String strStart = object.getString("start");
            LocalDate start = LocalDate.parse(strStart);
            String strEnd = object.getString("end");
            LocalDate end = LocalDate.parse(strEnd);

            JSONArray childrenInfo = object.getJSONArray("childrenInfo");
            List<Customer> children = new ArrayList<>();

            for (int j = 0; j < childrenInfo.length(); j++) {

                JSONObject objChildren = childrenInfo.getJSONObject(j);
                long id = objChildren.getLong("ID");
                String childFirstName = objChildren.getString("childFirstName");
                String childLastName = objChildren.getString("childLastName");
                String strBirth = objChildren.getString("childBirth");
                String parentName = objChildren.getString("parentName");
                String parentMobilePhone = objChildren.getString("parentMobilePhone");
                LocalDate birth = LocalDate.parse(strBirth);
                children.add(new Customer(id, childFirstName, childLastName, parentName, parentMobilePhone, birth));
            }

            groupsList.add(new Groups(ID, titleGroup, groupCost, start, end, teacherName,
                    teacherSalary, children));

        }

        reader.close();
        mapGroups = new HashMap<>();

        for(int i = 0; i < groupsList.size(); i++) {
            mapGroups.put(groupsList.get(i).getID(),groupsList.get(i));
        }

        return groupsList;
    }

    public static Map<Long, Groups> getMapGroups() {
        return mapGroups;
    }
}
