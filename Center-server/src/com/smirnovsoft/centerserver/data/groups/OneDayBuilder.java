package com.smirnovsoft.centerserver.data.groups;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class OneDayBuilder {
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate day;
    private String dayOfWeek;
    private double hours;

    public OneDayBuilder(LocalDate day, LocalTime startTime, LocalTime endTime) {
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.dayOfWeek = day.getDayOfWeek().toString();
    }

     public LocalTime getStartTime() {
         return startTime;
     }

     public void setStartTime(LocalTime startTime) {
         this.startTime = startTime;
     }

     public LocalTime getEndTime() {
         return endTime;
     }

     public void setEndTime(LocalTime endTime) {
         this.endTime = endTime;
     }

     public LocalDate getDay() {
         return day;
     }

     public void setDay(LocalDate day) {
         this.day = day;
     }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public double allHours() {
        if((startTime != null) && (endTime != null)) {
            hours = 0;
        }
        return hours;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OneDayBuilder that = (OneDayBuilder) o;
        return Objects.equals(startTime, that.startTime) &&
                Objects.equals(endTime, that.endTime) &&
                Objects.equals(day, that.day);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, endTime, day);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("OneDayBuilder{");
        sb.append("startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", day=").append(day);
        sb.append('}');
        return sb.toString();
    }
}
