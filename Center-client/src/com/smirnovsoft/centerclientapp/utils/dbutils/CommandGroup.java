package com.smirnovsoft.centerclientapp.utils.dbutils;


import com.smirnovsoft.centerclientapp.data.Customer;
import com.smirnovsoft.centerclientapp.data.groups.Groups;
import com.smirnovsoft.centerclientapp.utils.ConnectionServer;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CommandGroup implements RequestCommand<Groups> {
    private Groups group;
    private JsonRequestBuilder jsonBuilder = new JsonRequestBuilder();
    private List<Groups> groupList = null;

    public CommandGroup() throws IOException {
    }

    @Override
    public Groups insert(Groups group) throws IOException {

        if (group != null) {
            JSONObject groupObj = new JSONObject(group);
            groupObj.put("db", "groups");
            groupObj.put("command", "insert");
            groupObj.write(jsonBuilder.getOutput());
            jsonBuilder.flushOutput();
            jsonBuilder.closeWriter();
        }
        return group;
    }

    @Override
    public List<Groups> getAll() {
        groupList = new ArrayList<>();
        //Пишу запрос
        jsonBuilder.getWriter().object().key("db").value("groups")
                .key("command").value("getall")
                .endObject();
        jsonBuilder.flushOutput();
        // получаю ответ
        try {
            JSONArray arrayGroups = (JSONArray) jsonBuilder.getReader().nextValue();

            for(int i = 0; i < arrayGroups.length(); i++) {
                JSONObject object = (JSONObject) arrayGroups.get(i);
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

                groupList.add(new Groups(ID, titleGroup, groupCost, start, end, teacherName,
                        teacherSalary, children));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
            return groupList;
    }

    @Override
    public boolean delete(long ID) {
        if (ID != 0) {
            jsonBuilder.getWriter().object().key("db").value("groups")
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
        groupList = getAll();
        long maxID = 0;
        for (int i = 0; i < groupList.size(); i++) {
            if(maxID < groupList.get(i).getID()) {
                maxID = groupList.get(i).getID();
            }
        }
        return maxID + 1;
    }
}
