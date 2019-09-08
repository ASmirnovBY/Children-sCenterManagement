package com.smirnovsoft.centerserver.data.groups;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class TimeTable {
    private long groupID;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private OneDayBuilder dayBuilder;
    private List<OneDayBuilder> daysTimes;

    public TimeTable(long groupID, LocalDate dateStart, LocalDate dateEnd) {
        this.groupID = groupID;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        daysTimes = new ArrayList<>();
    }

    public long getGroupID() {
        return groupID;
    }

    public void setGroupID(long groupID) {
        this.groupID = groupID;
    }

    public int allGroupDays(){
        Period between = Period.between(dateStart, dateEnd);
        int days = between.getDays() + 1;
        return days;
    }

    public List<LocalDate> allPeriod(){
      List<LocalDate> allDate = new ArrayList<>();
        LocalDate startDate = dateStart;
        allDate.add(startDate);

        for(int i = 0; i < allGroupDays() - 1; i++) {
            startDate = startDate.plusDays(1);
            allDate.add(startDate);
        }

        return allDate;
    }

    public void addDay(OneDayBuilder oneDayBuilder) {
        daysTimes.add(oneDayBuilder);
    }

    public List<OneDayBuilder> getDaysTimes() {
        return daysTimes;
    }

    public void setDaysTimes(List<OneDayBuilder> daysTimes) {
        this.daysTimes = daysTimes;
    }

    public LocalDate getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDate dateStart) {
        this.dateStart = dateStart;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
    }
}