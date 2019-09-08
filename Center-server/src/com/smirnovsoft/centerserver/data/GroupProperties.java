package com.smirnovsoft.centerserver.data;

import java.util.Objects;

public class GroupProperties {
    private long ID;
    private String name;
    private double costInHour;
    private int hourInDay;

    public GroupProperties(long ID, String name, double costInHour, int hourInDay) {
        this.ID = ID;
        this.name = name;
        this.costInHour = costInHour;
        this.hourInDay = hourInDay;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCostInHour() {
        return costInHour;
    }

    public void setCostInHour(double costInHour) {
        this.costInHour = costInHour;
    }

    public int getHourInDay() {
        return hourInDay;
    }

    public void setHourInDay(int hourInDay) {
        this.hourInDay = hourInDay;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupProperties that = (GroupProperties) o;
        return Double.compare(that.costInHour, costInHour) == 0 &&
                hourInDay == that.hourInDay &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, costInHour, hourInDay);
    }

    @Override
    public String toString() {
        return "GroupProperties{" +
                "name='" + name + '\'' +
                ", costInHour=" + costInHour +
                ", hourInDay=" + hourInDay +
                '}';
    }
}
