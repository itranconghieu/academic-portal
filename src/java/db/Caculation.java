// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: https://pvs-studio.com
package db;


import entity.Semester;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.sql.*;
import transact.Attend;
import transact.CourseEnroll;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author End User
 */
public class Caculation {
    public static void main(String[] args) throws ClassNotFoundException, SQLException, ParseException {
        Date now = new Date();
        Semester currentSemester = new DBContext().getSemester(now);
        ArrayList<CourseEnroll> enrollList = new DBContext().getCourseEnrolls(currentSemester.getId());
        for (CourseEnroll enroll : enrollList) {
            ArrayList<Attend> attendList = new DBContext().getAttends(enroll.getId());
            int absentSession = 0;
            for (Attend attend : attendList) {
                if (attend.isPresent() == null || !attend.isPresent()) {
                    absentSession ++;
                }
            }
            if (attendList.get(attendList.size() - 1).getSession().getStartDate().before(now)) {
                boolean failAttend = absentSession > attendList.size() * 0.2;
                boolean failCriteria = false;
                ArrayList<Grade> gradeList = new GradeDBContext().getGrades(enroll.getId());
                float average = 0;
                for (Grade grade : gradeList) {
                    if ( grade.getCriteria() == null || (grade.getCriteria() != null && grade.getValue() >= grade.getCriteria())) {
                        average += (float) (grade.getValue() * (grade.getWeight() / 100.0));
                    } else {
                        failCriteria = true;
                    }
                }
                String status = !failAttend ? (failCriteria ? "Not passed" : "Passed") : "Not passed";
                new GradeDBContext().updateCourseEnroll(enroll.getId(), average, status);
            }
        }
    }

    public static class GradeDBContext extends Open {
        GradeDBContext() throws ClassNotFoundException, SQLException {
            
        }
        
        ArrayList<Grade> getGrades(int enrollId) throws SQLException  {
            String sql = "SELECT [weight], [value], criteria\n"
                    + "FROM CourseGrades\n"
                    + "LEFT JOIN CourseEnroll\n"
                    + "ON CourseGrades.courseId = CourseEnroll.courseId\n"
                    + "LEFT JOIN Exam\n"
                    + "ON CourseGrades.courseId = Exam.courseId AND CourseGrades.itemId = Exam.itemId\n"
                    + "LEFT JOIN Grades\n"
                    + "ON CourseEnroll.enrollId = Grades.enrollId AND Exam.examCode = Grades.examCode\n"
                    + "WHERE CourseEnroll.enrollId = ?";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setInt(1, enrollId);
            ArrayList<Grade> gradeList = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Grade g = new Grade();
                g.setWeight(rs.getInt("weight"));
                g.setValue(rs.getFloat("value"));
                if (rs.getString("criteria") != null) {
                   g.setCriteria(rs.getFloat("criteria")); 
                }
                gradeList.add(g);
            }
            return gradeList;
        }
        
        void updateCourseEnroll(int enrollId, float average, String status) throws SQLException {
            String sql = "UPDATE CourseEnroll\n"
                    + "SET average = ?, state = ?\n"
                    + "WHERE enrollId = ?;";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setFloat(1, average);
            ps.setString(2, status);
            ps.setInt(3, enrollId);
            ps.executeUpdate();
        }
        
    }
    
    static class Grade {
        int weight;
        float value;
        Float criteria;
        
        public Grade() {
            
        }
        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public float getValue() {
            return value;
        }

        public void setValue(float value) {
            this.value = value;
        }

        public Float getCriteria() {
            return criteria;
        }

        public void setCriteria(Float criteria) {
            this.criteria = criteria;
        }   
    }
}
