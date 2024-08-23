// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: https://pvs-studio.com
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package transact;

import entity.Lecturer;
import entity.Student;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author End User
 */
public class Attend {
    private int enrollId;
    private Student student;
    private transact.Session session;
    private Boolean attendStatus;
    private String comment;
    private Date modifiedTime;
    private Lecturer modifier;

    public Attend() {
        this.attendStatus = null;
    }

    public int getEnrollId() {
        return enrollId;
    }

    public void setEnrollId(int enrollId) {
        this.enrollId = enrollId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Boolean isPresent() {
        return attendStatus;
    }

    public void setAttendStatus(Boolean attendStatus) {
        this.attendStatus = attendStatus;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public Lecturer getModifier() {
        return modifier;
    }

    public void setModifier(Lecturer modifier) {
        this.modifier = modifier;
    }
    
    public String mofifiedTimeToString() {
        if (this.modifiedTime != null) {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(modifiedTime);
        }
        return "";
    }
}
