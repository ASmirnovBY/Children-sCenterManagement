package com.smirnovsoft.centerclientapp.utils.dbutils;

import com.smirnovsoft.centerclientapp.data.groups.GroupProperties;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CommandGroupProperties implements RequestCommand<GroupProperties> {
    private static final String nameDB = "groupsproperties";
    private GroupProperties properties;
    private JsonRequestBuilder jsonBuilder = new JsonRequestBuilder();
    private List<GroupProperties> propertiesList = null;

    public CommandGroupProperties() throws IOException {
    }

    @Override
    public GroupProperties insert(GroupProperties properties) throws IOException {
        if (properties != null) {
            jsonBuilder.getWriter().object().key("db").value("groupsproperties")
                    .key("command").value("insert")
                    .key("ID").value(properties.getID())
                    .key("name").value(properties.getName())
                    .key("costInHour").value(properties.getCostInHour())
                    .key("hourInDay").value(properties.getHourInDay())
                    .endObject();
            jsonBuilder.flushOutput();
            jsonBuilder.closeWriter();
            return properties;
        }
        return null;
    }

    @Override
    public List<GroupProperties> getAll() {
        propertiesList = new ArrayList<>();
        //Пишу запрос
        jsonBuilder.getWriter().object().key("db").value(nameDB)
                .key("command").value("getall")
                .endObject();
        jsonBuilder.flushOutput();
        //Получаю ответ
        try {
            JSONArray arrayoptions = (JSONArray) jsonBuilder.getReader().nextValue();

            for(int i = 0; i < arrayoptions.length(); i++) {
                JSONObject object = (JSONObject) arrayoptions.get(i);
                long ID = object.getLong("ID");
                String name = object.getString("name");
                double costInHour = object.getDouble("costInHour");
                int hourInDay = object.getInt("hourInDay");
                properties = new GroupProperties(ID, name, costInHour, hourInDay);
                propertiesList.add(properties);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        jsonBuilder.closeWriter();
        return propertiesList;
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
        for (int i = 0; i < propertiesList.size(); i++) {
            if(maxID < propertiesList.get(i).getID()) {
                maxID = propertiesList.get(i).getID();
            }
        }
        return maxID + 1;
    }
}
