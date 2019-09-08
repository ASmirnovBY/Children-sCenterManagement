package com.smirnovsoft.centerserver.data;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

public class Employee {

    private long id;
    private String firstName;
    private String lastName;
    private String position;
    private double salary;
    private String phone;
    private LocalDate birth;
    private LocalDate startWork;

    public Employee(long id, String firstName, String lastName, String position,
                    double salary, String phone, LocalDate birth, LocalDate startWork) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.salary = salary;
        this.phone = phone;
        this.birth = birth;
        this.startWork = startWork;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPosition() {
        return position;
    }

    public double getSalary() {
        return salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id &&
                Objects.equals(firstName, employee.firstName) &&
                Objects.equals(lastName, employee.lastName) &&
                Objects.equals(phone, employee.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, phone);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", position='" + position + '\'' +
                ", salary=" + salary +
                ", phone='" + phone + '\'' +
                ", birth=" + birth +
                ", startWork=" + startWork +
                '}';
    }


}
