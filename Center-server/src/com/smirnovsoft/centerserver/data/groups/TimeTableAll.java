package com.smirnovsoft.centerserver.data.groups;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimeTableAll {
   private static final String pathDb = "DB/timetable.json";
   private static List<TimeTable> timeTables = null;
   private static Map<Long, TimeTable> mapTimesTable = null;

   public static List<TimeTable> getTimeTableList() throws IOException {
        timeTables = new ArrayList<>();

        FileReader reader = null;
        JSONTokener tokener;
        JSONArray arrayTimes;
        JSONObject object;

        try {
            reader = new FileReader(pathDb);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        tokener = new JSONTokener(reader);
        arrayTimes = (JSONArray) tokener.nextValue();

        for(int i = 0; i < arrayTimes.length(); i++) {
            object = (JSONObject) arrayTimes.get(i);
            long ID = object.getLong("groupID");
            LocalDate start = LocalDate.parse(object.getString("dateStart"));
            LocalDate end = LocalDate.parse(object.getString("dateEnd"));
            TimeTable oneGroupTable = new TimeTable(ID,start, end);

            JSONArray daysTimes = object.getJSONArray("daysTimes");
            List<OneDayBuilder>oneDayBuilders = new ArrayList<>();
            for (int j = 0; j < daysTimes.length(); j++) {
                JSONObject objDaysTimes = daysTimes.getJSONObject(j);
                LocalDate day = LocalDate.parse(objDaysTimes.getString("day"));
                LocalTime startTime = LocalTime.parse(objDaysTimes.getString("startTime"));
                LocalTime endTime = LocalTime.parse(objDaysTimes.getString("endTime"));
                oneDayBuilders.add(new OneDayBuilder(day, startTime, endTime ));
            }
            oneGroupTable.setDaysTimes(oneDayBuilders);
            timeTables.add(oneGroupTable);
        }

        reader.close ();
        mapTimesTable = new HashMap<>();

        for(int i = 0; i < timeTables.size(); i++) {
            mapTimesTable.put(timeTables.get(i).getGroupID(),timeTables.get(i));
        }


        return timeTables;
    }

   public static Map<Long, TimeTable> getMapTimesTable() {
        return mapTimesTable;
    }
}
