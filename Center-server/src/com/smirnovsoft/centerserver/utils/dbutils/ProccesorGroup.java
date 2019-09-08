package com.smirnovsoft.centerserver.utils.dbutils;

import com.smirnovsoft.centerserver.data.Customer;
import com.smirnovsoft.centerserver.data.groups.Groups;
import com.smirnovsoft.centerserver.data.groups.GroupsTable;
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

public class ProccesorGroup extends ProccessorSpecific implements QueryCommand<Groups> {
    private static final String pathDb = "DB/groups.json";
    private JSONTokener tokener;
    private JSONObject object;
    private List<Groups> groups;
    private OutputStreamWriter outputStreamWriter;
    private JSONWriter writer;
    private JSONArray jsonArray;
    private Map<Long, Groups> mapGroup;

    public ProccesorGroup(JSONTokener tokener, JSONObject object) {
        this.tokener = tokener;
        this.object = object;
    }

    @Override
    public void insert() throws IOException {
        groups = new ArrayList<>();
        groups = GroupsTable.getAllGroups();
        FileWriter writer = new FileWriter(pathDb);

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

        groups.add(new Groups(ID, titleGroup, groupCost, start, end, teacherName,
                teacherSalary, children));

        JSONArray arrayGroups = new JSONArray(groups);
        arrayGroups.write(writer);
        writer.flush();
        writer.close();
        ServerConnection.closeSocket();

    }

    @Override
    public List<Groups> getAll() {
        try {
            jsonArray = new JSONArray(GroupsTable.getAllGroups());
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

        return groups;
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

        long id = (long) object.getLong("ID");
        mapGroup = GroupsTable.getMapGroups();

        mapGroup.remove(id);
        groups = new ArrayList<>(mapGroup.values());
        jsonArray = new JSONArray(groups);
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

