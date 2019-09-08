package com.smirnovsoft.centerserver.utils.dbutils;

import com.smirnovsoft.centerserver.data.GroupProperties;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;

import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;

public abstract class ProccessorSpecific {

    public abstract void operation(String operation);

    public static ProccessorSpecific getSpecific(String dbName, JSONTokener tokener, JSONObject object){
        switch (dbName) {
            case "employee" :
                return new ProcessorEmployee(tokener, object);
            case "account" :
                return new ProcessorAccount(tokener, object);
            case "customers" :
                return new ProccessorCustomer(tokener, object);
            case "groupsproperties" :
                return new ProccesorGroupProperties(tokener, object);
            case "groups" :
                return new ProccesorGroup(tokener, object);
            case "timetable" :
                return new ProccessorTimeTable(tokener, object);
        }
        return null;
    }

}
