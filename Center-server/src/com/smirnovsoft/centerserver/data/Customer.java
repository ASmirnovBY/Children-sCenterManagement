package com.smirnovsoft.centerserver.data;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

public class Customer {
    private long ID;
    private String childFirstName;
    private String childLastName;
    private LocalDate childBirth;
    private int age;
    private String parentName;
    private String parentMobilePhone;


    public Customer(long ID, String childFirstName, String childLastName, String parentName,
                    String parentMobilePhone, LocalDate childBirth) {
        this.ID = ID;
        this.childFirstName = childFirstName;
        this.childLastName = childLastName;
        this.parentName = parentName;
        this.parentMobilePhone = parentMobilePhone;
        this.childBirth = childBirth;
        this.age = getAge();
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getChildFirstName() {
        return childFirstName;
    }

    public void setChildFirstName(String childFirstName) {
        this.childFirstName = childFirstName;
    }

    public String getChildLastName() {
        return childLastName;
    }

    public void setChildLastName(String childLastName) {
        this.childLastName = childLastName;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentMobilePhone() {
        return parentMobilePhone;
    }

    public void setParentMobilePhone(String parentMobilePhone) {
        this.parentMobilePhone = parentMobilePhone;
    }

    public LocalDate getChildBirth() {
        return childBirth;
    }

    public void setChildBirth(LocalDate childBirth) {
        this.childBirth = childBirth;
    }

    public int getAge() {
        LocalDate now = LocalDate.now();
        int age = Period.between(childBirth, now).getYears();

        return age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return ID == customer.ID &&
                age == customer.age &&
                Objects.equals(childFirstName, customer.childFirstName) &&
                Objects.equals(childLastName, customer.childLastName) &&
                Objects.equals(childBirth, customer.childBirth) &&
                Objects.equals(parentName, customer.parentName) &&
                Objects.equals(parentMobilePhone, customer.parentMobilePhone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, childFirstName, childLastName, childBirth, age, parentName, parentMobilePhone);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "ID=" + ID +
                ", childFirstName='" + childFirstName + '\'' +
                ", childLastName='" + childLastName + '\'' +
                ", age=" + age +
                '}';
    }

}
