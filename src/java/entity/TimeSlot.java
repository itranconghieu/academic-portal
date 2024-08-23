// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: https://pvs-studio.com
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author End User
 */
public class TimeSlot {
    private final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
    private int slot;
    private LocalTime startTime;
    private LocalTime endTime;

    public TimeSlot() {
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
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

    @Override
    public String toString() {
        return startTime.format(timeFormat) + " - " + endTime.format(timeFormat);
    }
}
