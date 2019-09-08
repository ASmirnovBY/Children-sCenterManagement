package com.smirnovsoft.centerserver.utils.dbutils;

import com.smirnovsoft.centerserver.data.GroupProperties;
import com.smirnovsoft.centerserver.data.GroupsOptionsTable;
import com.smirnovsoft.centerserver.server.ServerConnection;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProccesorGroupProperties extends ProccessorSpecific implements QueryCommand<GroupProperties>{
    private static final String pathDb = "DB/groupsoptions.json";
    private JSONTokener tokener;
    private JSONObject object;
    private List<GroupProperties> groupOptions;
    private OutputStreamWriter outputStreamWriter;
    private JSONWriter writer;
    private JSONArray jsonArray;
    private Map<Long, GroupProperties> mapProper;

    public ProccesorGroupProperties(JSONTokener tokener, JSONObject object) {
        this.tokener = tokener;
        this.object = object;
    }

    @Override
    public void operation(String operation) {
        switch (operation){
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

    @Override
    public void insert() throws IOException {
       groupOptions = new ArrayList<>();
       groupOptions = GroupsOptionsTable.getOptionsList();
       FileWriter writer = new FileWriter(pathDb);
       long ID = object.getLong("ID");
       String name = object.getString("name");
       double costInHour = object.getDouble("costInHour");
       int hourInDay = object.getInt("hourInDay");
       groupOptions.add(new GroupProperties(ID, name, costInHour, hourInDay));

        JSONArray arrayOptions = new JSONArray(groupOptions);
        arrayOptions.write(writer);
        writer.flush();
        writer.close();
        ServerConnection.closeSocket();

    }

    @Override
    public List<GroupProperties> getAll() {
        try {
            jsonArray = new JSONArray(GroupsOptionsTable.getOptionsList());
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

        return groupOptions;
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
        mapProper = GroupsOptionsTable.getMapOptionList();

        mapProper.remove(id);
        groupOptions = new ArrayList<>(mapProper.values());
        jsonArray = new JSONArray(groupOptions);
        jsonArray.write(filewriter);
        try {
            filewriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error");
        }
        return true;
    }
}
