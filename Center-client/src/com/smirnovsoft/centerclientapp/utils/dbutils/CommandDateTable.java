package com.smirnovsoft.centerclientapp.utils.dbutils;

import com.smirnovsoft.centerclientapp.data.groups.OneDayBuilder;
import com.smirnovsoft.centerclientapp.data.groups.TimeTable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class CommandDateTable implements RequestCommand<TimeTable> {
    private TimeTable timeTable;
    private JsonRequestBuilder jsonBuilder = new JsonRequestBuilder();
    private List<TimeTable> timeTables;

    public CommandDateTable() throws IOException {
    }

    @Override
    public TimeTable insert(TimeTable object) throws IOException {

        if (object != null) {
            JSONObject jsonObject = new JSONObject(object);
            jsonObject.put("db", "timetable");
            jsonObject.put("command", "insert");
            jsonObject.write(jsonBuilder.getOutput());
            jsonBuilder.flushOutput();
            jsonBuilder.closeWriter();
        }
        return object;
    }

    @Override
    public List<TimeTable> getAll() {
        timeTables = new ArrayList<>();


        jsonBuilder.getWriter().object().key("db").value("timetable")
                .key("command").value("getall")
                .endObject();
        jsonBuilder.flushOutput();

        try {
            JSONArray arrayTimesTable = (JSONArray) jsonBuilder.getReader().nextValue();

            for(int i = 0; i < arrayTimesTable.length(); i++) {
                JSONObject object = (JSONObject) arrayTimesTable.get(i);
                long ID = object.getLong("groupID");
                LocalDate start = LocalDate.parse(object.getString("dateStart"));
                LocalDate end = LocalDate.parse(object.getString("dateEnd"));
                TimeTable oneGroupTable = new TimeTable(ID,start, end);

                JSONArray daysTimes = object.getJSONArray("daysTimes");
                List<OneDayBuilder> dayBuilder  = new ArrayList<>();
                for (int j = 0; j < daysTimes.length(); j++) {
                    JSONObject objDaysTimes = (JSONObject) daysTimes.get(j);
                    LocalDate day = LocalDate.parse(objDaysTimes.getString("day"));
                    LocalTime startTime = LocalTime.parse(objDaysTimes.getString("startTime"));
                    LocalTime endTime = LocalTime.parse(objDaysTimes.getString("endTime"));
                    dayBuilder.add(new OneDayBuilder(day, startTime, endTime ));
                }
                oneGroupTable.setDaysTimes(dayBuilder);
                dayBuilder = null;
                timeTables.add(oneGroupTable);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return timeTables;
    }

    @Override
    public boolean delete(long ID) {
        if (ID != 0) {
            jsonBuilder.getWriter().object().key("db").value("timetable")
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
        return 0;
    }
}
