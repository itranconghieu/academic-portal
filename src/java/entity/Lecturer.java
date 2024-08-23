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
public class Lecturer extends entity.Account {
    private Department department;
    private Date startWorkingDate;

    public Lecturer() {
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }


    public Date getStartWorkingDate() {
        return startWorkingDate;
    }

    public void setStartWorkingDate(Date startWorkingDate) {
        this.startWorkingDate = startWorkingDate;
    }

    
    
}
