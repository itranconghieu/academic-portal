/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package helper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author End User
 */
public class Week {
    private Date startDate;
    private Date endDate;
    SimpleDateFormat shortDateFormat = new SimpleDateFormat("dd-MM");

    public Week() {
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return shortDateFormat.format(startDate) + " to " + shortDateFormat.format(endDate);
    }
    
}
