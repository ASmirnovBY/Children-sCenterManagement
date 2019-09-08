package com.smirnovsoft.centerclientapp.data.groups;

import com.smirnovsoft.centerclientapp.data.Customer;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Groups {
    private long ID;
    private String groupTitle;
    private double groupCost;
    private LocalDate start;
    private LocalDate end;
    private String teacherName;
    private double teacherSalary;
    private List<Customer> childrenInfo;
    private String status;
    private TimeTable timeTable;

    public Groups(long ID, String groupTitle, double groupCost, LocalDate start, LocalDate end,
                  String teacherName, double teacherSalary, List<Customer> childrenInfo) {
        this.ID = ID;
        this.groupTitle = groupTitle;
        this.groupCost = groupCost;
        this.start = start;
        this.end = end;
        this.teacherName = teacherName;
        this.teacherSalary = teacherSalary;
        this.childrenInfo = childrenInfo;
        this.status = getStatus();
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getGroupTitle() {
        return groupTitle;
    }

    public void setGroupTitle(String groupTitle) {
        this.groupTitle = groupTitle;
    }

    public double getGroupCost() {
        return groupCost;
    }

    public void setGroupCost(double groupCost) {
        this.groupCost = groupCost;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public double getTeacherSalary() {
        return teacherSalary;
    }

    public void setTeacherSalary(double teacherSalary) {
        this.teacherSalary = teacherSalary;
    }

    public List<Customer> getChildrenInfo() {
        return childrenInfo;
    }

    public void setChildrenInfo(List<Customer> childrenInfo) {
        this.childrenInfo = childrenInfo;
    }

    public String getStatus() {
        LocalDate now = LocalDate.now();

        if (end.isBefore(now)) {
            return "finished";
        }

        if (start.isAfter(now)) {
            return "begin soon";
        }

            return "in processing";

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Groups groups = (Groups) o;
        return ID == groups.ID &&
                Double.compare(groups.groupCost, groupCost) == 0 &&
                Double.compare(groups.teacherSalary, teacherSalary) == 0 &&
                Objects.equals(groupTitle, groups.groupTitle) &&
                Objects.equals(start, groups.start) &&
                Objects.equals(end, groups.end) &&
                Objects.equals(teacherName, groups.teacherName) &&
                Objects.equals(childrenInfo, groups.childrenInfo) &&
                Objects.equals(status, groups.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, groupTitle, groupCost, start, end, teacherName, teacherSalary, childrenInfo, status);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Groups{");
        sb.append("ID=").append(ID);
        sb.append(", groupTitle='").append(groupTitle).append('\'');
        sb.append(", groupCost=").append(groupCost);
        sb.append(", start=").append(start);
        sb.append(", end=").append(end);
        sb.append(", teacherName='").append(teacherName).append('\'');
        sb.append(", teacherSalary=").append(teacherSalary);
        sb.append(", childrenInfo=").append(childrenInfo);
        sb.append(", status='").append(status).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public void setTimeTable(TimeTable timeTable) {
        this.timeTable = timeTable;
    }

    public TimeTable getTimeTable() {
        if (timeTable != null) {
            return timeTable;
        }
        return null;
    }
}
