// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: https://pvs-studio.com
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.util.Date;

/**
 *
 * @author End User
 */
public class Student extends Account {
    public Major major;
    public Date startLearningDate;

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }

    public Date getStartLearningDate() {
        return startLearningDate;
    }

    public void setStartLearningDate(Date startLearningDate) {
        this.startLearningDate = startLearningDate;
    }
    
    public String getCode() {
        String id = super.getId();
        return super.getId().substring(id.length() - 8, id.length());
    }

    @Override
    public String toString() {
        return super.getSurname() + " " + super.getMiddleName() + " " + super.getGivenName();
    }
    
}
