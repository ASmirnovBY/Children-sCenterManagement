package com.smirnovsoft.centerclientapp.utils.calculations;

import com.smirnovsoft.centerclientapp.data.groups.Groups;
import com.smirnovsoft.centerclientapp.data.groups.TimeTable;

import java.time.Duration;
import java.time.LocalTime;
import java.time.Period;

public class GainCalculator {
    private Groups group;
    private TimeTable oneGroupTimeTable;
    private double total;
    private double gain;
    private double disbursement;
    private int allMinutes;
    private double teacherSalary;


    public GainCalculator(Groups group, TimeTable groupTimeTable) {
        this.group = group;
        this.oneGroupTimeTable = groupTimeTable;
        this.allMinutes = getAllMinutes();
        this.teacherSalary = group.getTeacherSalary();
        this.disbursement = setDisbursement();
        this.gain = setGain();

    }

    public int getAllMinutes() {
        allMinutes = 0;
        int allDays = oneGroupTimeTable.getDaysTimes().size();
        int minutes = 0;
        for (int i = 0; i < allDays; i++) {
            LocalTime startTime = oneGroupTimeTable.getDaysTimes().get(i).getStartTime();
            LocalTime endTime = oneGroupTimeTable.getDaysTimes().get(i).getEndTime();
            long l = Duration.between(startTime, endTime).toMinutes();
            minutes += (int) l;
            allMinutes += minutes;

        }
        return minutes;
    }

    private double setDisbursement() {
        disbursement = (teacherSalary / 60) * allMinutes;
        return disbursement;
    }

    private double setGain() {
        gain = (group.getGroupCost() / 60) * allMinutes * group.getChildrenInfo().size();
        return gain;
    }

    public double getGain() {
        return gain;
    }

    public double getTotal () {
        this.total = gain - disbursement;
        return this.total;
    }

    public double getDisbursement() {
        return disbursement;
    }

}
