package com.apptozee.drivingschool.Driver;

public class Customer {
    private String number, name, time, slot;

    public Customer() {
    }

    public Customer(String name, String number, String time, String slot) {
        this.number = "Name: " + name;
        this.name = "Mobile: " + number;
        this.time = "Days: " + time;
        this.slot = "Slot: " + slot;
    }

    public String getnumber() {
        return number;
    }

    public void setnumber(String number) {
        this.number = number;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String gettime() {
        return time;
    }

    public void settime(String time) {
        this.time = time;
    }

    public String getslot() {
        return slot;
    }

    public void setslot(String slot) {
        this.slot = slot;
    }
}
