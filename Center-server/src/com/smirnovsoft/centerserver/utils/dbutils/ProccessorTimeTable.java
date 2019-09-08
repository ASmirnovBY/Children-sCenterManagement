package com.smirnovsoft.centerserver.utils.dbutils;

import com.smirnovsoft.centerserver.data.groups.OneDayBuilder;
import com.smirnovsoft.centerserver.data.groups.TimeTable;
import com.smirnovsoft.centerserver.data.groups.TimeTableAll;
import com.smirnovsoft.centerserver.server.ServerConnection;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProccessorTimeTable extends ProccessorSpecific implements QueryCommand<TimeTable> {
    private static final String pathDb = "DB/timetable.json";
    private JSONTokener tokener;
    private JSONObject object;
    private List<TimeTable> timeTables;
    private OutputStreamWriter outputStreamWriter;
    private JSONWriter writer;
    private JSONArray jsonArray;
    private Map<Long, TimeTable> mapTimeTable;


    public ProccessorTimeTable(JSONTokener tokener, JSONObject object) {
        this.tokener = tokener;
        this.object = object;
    }

    @Override
    public void insert() throws IOException {
        timeTables = new ArrayList<>();
        List<OneDayBuilder> oneDayBuilders = new ArrayList<>();
        timeTables = TimeTableAll.getTimeTableList();
        FileWriter writer = new FileWriter(pathDb);

        long ID = object.getLong("groupID");
        LocalDate start = LocalDate.parse(object.getString("dateStart"));
        LocalDate end = LocalDate.parse(object.getString("dateEnd"));
        TimeTable oneGroupTable = new TimeTable(ID,start, end);

        JSONArray daysTimes = object.getJSONArray("daysTimes");
        for (int j = 0; j < daysTimes.length(); j++) {
            JSONObject objDaysTimes = (JSONObject) daysTimes.get(j);
            LocalDate day = LocalDate.parse(objDaysTimes.getString("day"));
            LocalTime startTime = LocalTime.parse(objDaysTimes.getString("startTime"));
            LocalTime endTime = LocalTime.parse(objDaysTimes.getString("endTime"));
            oneGroupTable.addDay(new OneDayBuilder(day, startTime, endTime));
        }

        timeTables.add(oneGroupTable);

        JSONArray requetJSON = new JSONArray(timeTables);
        requetJSON.write(writer);
        writer.flush();
        writer.close();
        ServerConnection.closeSocket();

    }

    @Override
    public List<TimeTable> getAll() {
        try {
            jsonArray = new JSONArray(TimeTableAll.getTimeTableList());
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

        return timeTables;
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
        mapTimeTable = TimeTableAll.getMapTimesTable();

        mapTimeTable.remove(id);
        timeTables= new ArrayList<>(mapTimeTable.values());
        jsonArray = new JSONArray(timeTables);
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

