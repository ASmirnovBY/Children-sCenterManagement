package com.smirnovsoft.centerclientapp.data;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

public class Employee {

    private long ID;
    private String firstName;
    private String lastName;
    private Position position;
    private String positionStr;
    private double salary;
    private String phone;
    private LocalDate birth;
    private LocalDate startWork;


    public Employee(long ID, String firstName, String lastName, String positionStr,
                    double salary, String phone, LocalDate birth, LocalDate startWork) {
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.phone = phone;
        this.birth = birth;
        this.startWork = startWork;
        this.positionStr = positionStr;
    }

    public Employee(long ID, String firstName, String lastName, double salary) {
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
    }

    public String getPosition() {
        return positionStr;
    }

    public void setPosition(String positionStr) {
        this.positionStr = positionStr;
    }

    public double getSalary() {
        return salary;
    }

    public long getId() {
        return ID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    public LocalDate getStartWork() {
        return startWork;
    }

    public void setStartWork(LocalDate startWork) {
        this.startWork = startWork;
    }

    private int age(LocalDate birth) {
            this.birth = birth;
            LocalDate now = LocalDate.now();
            int age = Period.between(birth, now).getYears();

        return age;
    }

    private int generateId(LocalDate birth) {
        this.birth = birth;
        LocalDate now = LocalDate.now();
        int generate = Period.between(birth, now).getYears()
                + Period.between(birth,now).getMonths()
                + Period.between(birth,now).getDays();

        return generate;
    }

    public long getID() {
        return ID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return ID == employee.ID &&
                Double.compare(employee.salary, salary) == 0 &&
                Objects.equals(firstName, employee.firstName) &&
                Objects.equals(lastName, employee.lastName) &&
                position == employee.position &&
                Objects.equals(positionStr, employee.positionStr) &&
                Objects.equals(phone, employee.phone) &&
                Objects.equals(birth, employee.birth) &&
                Objects.equals(startWork, employee.startWork);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, firstName, lastName, position, positionStr, salary, phone, birth, startWork);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "ID=" + ID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", position=" + position +
                ", positionStr='" + positionStr + '\'' +
                ", salary=" + salary +
                ", phone='" + phone + '\'' +
                ", birth=" + birth +
                ", startWork=" + startWork +
                '}';
    }
}
