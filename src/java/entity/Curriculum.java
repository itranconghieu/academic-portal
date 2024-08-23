/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author End User
 */
public class Curriculum {
    private Major major;
    private ArrayList<Course> courseList;
    private HashMap<String, Integer> courseMap;

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }

    public ArrayList<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(ArrayList<Course> courseList) {
        this.courseList = courseList;
    }

    public HashMap<String, Integer> getCourseMap() {
        return courseMap;
    }

    public void setCourseMap(HashMap<String, Integer> courseMap) {
        this.courseMap = courseMap;
    }
    
    
}
