// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: https://pvs-studio.com
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package transact;

import entity.Course;
import entity.Student;
import java.util.Date;

/**
 *
 * @author End User
 */
public class Request {
    private int id;
    private CourseEnroll courseEnroll;
    private String toClass;
    private String purpose;
    private Boolean state;
    private String response;
    private Date sendTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CourseEnroll getCourseEnroll() {
        return courseEnroll;
    }

    public void setCourseEnroll(CourseEnroll courseEnroll) {
        this.courseEnroll = courseEnroll;
    }

    public String getToClass() {
        return toClass;
    }

    public void setToClass(String toClass) {
        this.toClass = toClass;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }


    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }
    
    
    
}
