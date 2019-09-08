package com.smirnovsoft.centerserver.data;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupsOptionsTable {
    private static List<GroupProperties> optionsList = null;
    private static final String pathDb = "DB/groupsoptions.json";
    private static Map<Long, GroupProperties> mapOptionList = null;

    public static List<GroupProperties> getOptionsList() throws IOException {
        optionsList = new ArrayList<>();
        FileReader reader = null;
        JSONTokener tokener;
        JSONArray arrayOptions;
        JSONObject object;

        try {
            reader = new FileReader(pathDb);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        tokener = new JSONTokener(reader);
        arrayOptions = (JSONArray) tokener.nextValue();

        for(int i = 0; i < arrayOptions.length(); i++) {
            object = ((JSONObject) arrayOptions.get(i));
            long ID = object.getLong("ID");
            String name = object.getString("name");
            double costInHour = object.getDouble("costInHour");
            int hourInDay = object.getInt("hourInDay");
            optionsList.add(new GroupProperties(ID, name, costInHour, hourInDay));
        }

        reader.close();
        mapOptionList = new HashMap<>();

        for(int i = 0; i < arrayOptions.length(); i++){
            mapOptionList.put(optionsList.get(i).getID(), optionsList.get(i));
        }

        return optionsList;
    }

    public static Map<Long, GroupProperties> getMapOptionList() {
        return mapOptionList;
    }
}
