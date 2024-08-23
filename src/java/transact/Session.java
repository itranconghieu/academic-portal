// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: https://pvs-studio.com
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package transact;

import entity.Lecturer;
import entity.Room;
import entity.TimeSlot;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 *
 * @author End User
 */
public class Session {
    private String groupId;
    private entity.Session session;
    private Lecturer lecturer;
    private Boolean takeStatus;
    private Room room;
    private TimeSlot slot;
    private Date startDate;
    private String eduNextURL;
    private String meetURL;

    public Session() {
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public entity.Session getSession() {
        return session;
    }

    public void setSession(entity.Session session) {
        this.session = session;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public Boolean isTaked() {
        return takeStatus;
    }

    public void setTakeStatus(Boolean takeStatus) {
        this.takeStatus = takeStatus;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public TimeSlot getSlot() {
        return slot;
    }

    public void setSlot(TimeSlot slot) {
        this.slot = slot;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getEduNextURL() {
        return eduNextURL;
    }

    public void setEduNextURL(String eduNextURL) {
        this.eduNextURL = eduNextURL;
    }

    public String getMeetURL() {
        return meetURL;
    }

    public void setMeetURL(String meetURL) {
        this.meetURL = meetURL;
    }
    
    public Date getStartTime() throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(new SimpleDateFormat("yyyy-MM-dd").format(this.getStartDate())
         + " " + this.getSlot().getStartTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
    }
    public String startDateToString() {
        return new SimpleDateFormat("EEE, MMMM dd").format(this.startDate);
    }
    
    public String slotToString() {
        return this.slot.getStartTime()  + " to " + this.slot.getEndTime();
    }
    @Override
    public String toString() {
        return this.groupId + ", " + this.session.getCourse().getId() + " on " + this.startDateToString() + " from " + this.slotToString();
    }
    
}
