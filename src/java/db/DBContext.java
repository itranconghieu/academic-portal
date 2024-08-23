// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: https://pvs-studio.com
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package db;

import entity.Course;
import entity.CourseGrade;
import entity.Curriculum;
import entity.Department;
import entity.Exam;
import entity.GradeCategory;
import entity.GradeItem;
import entity.Lecturer;
import entity.Major;
import transact.Request;
import entity.Room;
import entity.Semester;
import entity.Student;
import entity.TimeSlot;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import transact.Attend;
import transact.CourseEnroll;
import transact.Grade;

/**
 *
 * @author End User
 */
public class DBContext extends Open {
    public DBContext() throws ClassNotFoundException, SQLException {
        
    }
    
    public String  getUsername(String mail) throws SQLException {
        String sql = "select username\n"
                + "from (select studentId as username, email\n"
                + "from Student\n"
                + "union all\n"
                + "select lecturerId, email\n"
                + "from Lecturer) as #temp\n"
                + "where email = ?;";
        PreparedStatement p = cn.prepareStatement(sql);
        p.setString(1, mail);
        ResultSet rs = p.executeQuery();
        if (rs.next()) {
            return rs.getString("username");
        } return null;
    }
    
    private final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    public ArrayList<String> getClass(Semester semester, Course course) throws SQLException {
        String sql = "SELECT groupId\n"
                + "FROM Class\n"
                + "WHERE semesterId = ? AND courseId = ?\n"
                + "ORDER BY groupId ASC;";
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setInt(1, semester.getId());
        ps.setString(2, course.getId());
        ArrayList<String> classList = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            classList.add(rs.getString("groupId"));
        }
        return classList;
    }
    
    public Semester getSemester(Date date) throws SQLException, ParseException {
        String sql = "SELECT [semesterId]\n"
                + "  FROM [dbo].[Semester]\n"
                + "WHERE ? BETWEEN startDate AND endDate";
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1, df.format(date));
        Semester semester = new Semester();
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            semester = this.getSemester(rs.getInt("semesterId"));
        }
        return semester;
    }
    
    public Semester getSemester(int id) throws SQLException, ParseException {
        String sql = "SELECT [semesterId]\n"
                + "      ,[range]\n"
                + "      ,[year]\n"
                + "      ,[startDate]\n"
                + "      ,[endDate]\n"
                + "  FROM [dbo].[Semester]"
                + "WHERE semesterId = ?;";
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setInt(1, id);
        Semester s = new Semester();
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            s.setId(id);
            s.setRange(rs.getString("range"));
            s.setYear(rs.getInt("year"));
            s.setStartDate(df.parse(rs.getString("startDate")));
            s.setEndDate(df.parse(rs.getString("endDate")));
        }
        return s;
    }
    
    public ArrayList<Semester> getSemesters(String studentId) throws SQLException, ParseException {
        String sql = "SELECT DISTINCT semesterId\n"
                + "FROM CourseEnroll\n"
                + "WHERE studentId = ?;";
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1, studentId);
        ArrayList<Semester> semesterList = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Semester s = this.getSemester(rs.getInt("semesterId"));
            semesterList.add(s);
        }
        return semesterList;
    }

    public Curriculum getCurriculum(Major major) throws SQLException {
        String sql = "SELECT [majorId]\n"
                + "      ,[courseId]\n"
                + "      ,[termNo]\n"
                + "  FROM [dbo].[Curriculum]\n"
                + "WHERE majorId = ?\n"
                + "ORDER BY termNo ASC;";
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1, major.getId());
        Curriculum curriculum = new Curriculum();
        ResultSet rs = ps.executeQuery();
        curriculum.setMajor(major);
        ArrayList<Course> courseList = new ArrayList<>(); 
        HashMap<String, Integer> courseMap = new HashMap<>();
        while (rs.next()) {
            Course c = this.getCourse(rs.getString("courseId"));
            courseList.add(c);
            courseMap.put(c.getId(), rs.getInt("termNo"));
        }
        curriculum.setCourseList(courseList);
        curriculum.setCourseMap(courseMap);
        return curriculum;
    }
    
    public Major getMajor(String id) throws SQLException {
        String sql = "SELECT [majorId]\n"
                + "      ,[name]\n"
                + "  FROM [dbo].[Major]\n"
                + "WHERE majorId = ?;";
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1, id);
        Major m = new Major();
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            m.setId(id);
            m.setName(rs.getString("name"));
        }
        return m;
    }
    
    public Department getDepartment(String id) throws SQLException {
        String sql = "SELECT [departmentId]\n"
                + "      ,[deanId]\n"
                + "      ,[name]\n"
                + "  FROM [dbo].[Department]\n"
                + "WHERE departmentId = ?";
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1, id);
        Department d = new Department();
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            d.setId(id);
            d.setName(rs.getString("name"));
        }
        return null;
    }
    private final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
    public ArrayList<TimeSlot> getTimeSlots() throws SQLException {
        String sql = "SELECT [slot]\n"
                + "      ,[startTime]\n"
                + "      ,[endTime]\n"
                + "  FROM [dbo].[TimeSlot];";
        PreparedStatement ps = cn.prepareStatement(sql);
        ArrayList<TimeSlot> tss = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            TimeSlot ts = new TimeSlot();
            ts.setSlot(rs.getInt("slot"));
            ts.setStartTime(LocalTime.parse(rs.getString("startTime")
                    .substring(0, 8), timeFormat));
            ts.setEndTime(LocalTime.parse(rs.getString("endTime")
                    .substring(0, 8), timeFormat));
            tss.add(ts);
        }
        return tss;
    }
    public TimeSlot getTimeSlot(int slot) throws SQLException {
        String sql = "SELECT [slot]\n"
                + "      ,[startTime]\n"
                + "      ,[endTime]\n"
                + "  FROM [dbo].[TimeSlot]\n"
                + "WHERE slot = ?\n";
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setInt(1, slot);
        TimeSlot ts = new TimeSlot();
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            ts.setSlot(slot);
            ts.setStartTime(LocalTime.parse(rs.getString("startTime")
                    .substring(0, 8), timeFormat));
            ts.setEndTime(LocalTime.parse(rs.getString("endTime")
                    .substring(0, 8), timeFormat));
        }
        return ts;
    }
    
    public Room getRoom(String id) throws SQLException {
        String sql = "SELECT [roomId]\n"
                + "      ,[building]\n"
                + "      ,[roomNumber]\n"
                + "      ,[note]\n"
                + "  FROM [dbo].[Room]\n"
                + "WHERE roomId = ?;";
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1, id);
        Room r = new Room();
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            r.setBuilding(rs.getString("building"));
            r.setRoomNumber(rs.getInt("roomNumber"));
            r.setNote(rs.getString("note"));
        }
        return r;
    }
    
    public Course getCourse(String id) throws SQLException {
        String sql = "SELECT [courseId]\n"
                + "      ,[departmentId]\n"
                + "      ,[name]\n"
                + "      ,[note]\n"
                + "      ,[objective]\n"
                + "      ,[resourceURL]\n"
                + "      ,[prerequisite]\n"
                + "      ,[credit]\n"
                + "  FROM [dbo].[Course]\n"
                + "WHERE courseId = ?;";
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1, id);
        Course c = new Course();
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            c.setId(id);
            c.setDepartment(this.getDepartment(rs.getString("departmentId")));
            c.setName(rs.getString("name"));
            c.setNote(rs.getString("note"));
            c.setObjective(rs.getString("objective"));
            c.setPrerequisite(rs.getString("prerequisite"));
            c.setResourceURL(rs.getString("resourceURL"));
            c.setCredit(rs.getInt("credit"));
        }
        return c;
    }
    
    public Lecturer getLecturer(String id) throws SQLException, ParseException {
        String sql = "SELECT [lecturerId]\n"
                + "      ,[departmentId]\n"
                + "      ,[surname]\n"
                + "      ,[middleName]\n"
                + "      ,[givenName]\n"
                + "      ,[dateOfBirth]\n"
                + "      ,[gender]\n"
                + "      ,[address]\n"
                + "      ,[imageURL]\n"
                + "      ,[email]\n"
                + "      ,[recognizeDate]\n"
                + "  FROM [dbo].[Lecturer]\n"
                + "WHERE lecturerId = ?;";
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1, id);
        Lecturer l = new Lecturer();
        ResultSet rs = ps.executeQuery();
                if (rs.next()) {
            l.setId(id);
            Department d = this.getDepartment(rs.getString("departmentId"));
            l.setDepartment(d);
            l.setSurname(rs.getString("surname"));
            l.setMiddleName(rs.getString("middleName"));
            l.setGivenName(rs.getString("givenName"));
            l.setDateOfBirth(df.parse(rs.getString("dateOfBirth")));
            l.setGender(rs.getBoolean("gender"));
            l.setAddress(rs.getString("address"));
            l.setImageURL(rs.getString("imageURL"));
            l.setEmail(rs.getString("email"));
            l.setRecognizeDate(df.parse(rs.getString("recognizeDate")));
        }
        return l;
    }
    
    public Student getStudent(String id) throws SQLException, ParseException {
        String sql = "SELECT [studentId]\n"
                + "      ,[majorId]\n"
                + "      ,[surname]\n"
                + "      ,[middleName]\n"
                + "      ,[givenName]\n"
                + "      ,[dateOfBirth]\n"
                + "      ,[gender]\n"
                + "      ,[address]\n"
                + "      ,[imageURL]\n"
                + "      ,[email]\n"
                + "      ,[recognizeDate]\n"
                + "  FROM [dbo].[Student]"
                + "WHERE studentId = ?;";
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1, id);
        Student s = new Student();
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            s.setId(id);
            Major m = this.getMajor(rs.getString("majorId"));
            s.setMajor(m);
            s.setSurname(rs.getString("surname"));
            s.setMiddleName(rs.getString("middleName"));
            s.setGivenName(rs.getString("givenName"));
            s.setDateOfBirth(df.parse(rs.getString("dateOfBirth")));
            s.setGender(rs.getBoolean("gender"));
            s.setAddress(rs.getString("address"));
            s.setImageURL(rs.getString("imageURL"));
            s.setEmail(rs.getString("email"));
            s.setRecognizeDate(df.parse(rs.getString("recognizeDate")));
        }
        return s;
    }
    public ArrayList<Student> getStudents(String pattern) throws SQLException, ParseException {
        String sql = "SELECT DISTINCT studentId, string\n"
                + "FROM (SELECT Student.StudentId, Student.studentId + ' ' + Student.surname + ' ' + Student.middleName + ' ' + Student.givenName + ' ' + CourseEnroll.groupId AS string\n"
                + "FROM Student\n"
                + "JOIN CourseEnroll\n"
                + "ON Student.studentId = CourseEnroll.studentId) AS #temp\n"
                + "WHERE string LIKE '%' + ? + '%'";
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1, pattern);
        ArrayList<Student> studentList = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Student student = this.getStudent(rs.getString("studentId"));
            studentList.add(student);
        }
        return studentList;
    }
    
    public entity.Session getSession(Course course, int no) throws SQLException {
        String sql = "SELECT [courseId]\n"
                + "      ,[sessionNo]\n"
                + "      ,[description]\n"
                + "  FROM [dbo].[Session]\n"
                + "WHERE courseId = ? AND sessionNo = ?;";
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1, course.getId());
        ps.setInt(2, no);
        entity.Session s = new entity.Session();
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            s.setCourse(course);
            s.setNo(no);
            s.setDescription(rs.getString("description"));
        }
        return s;
    }
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public ArrayList<transact.Session> getSessions(String groupId, String courseId) throws SQLException, ParseException {
        String sql = "SELECT [groupId]\n"
                + "      ,[courseId]\n"
                + "      ,[sessionNo]\n"
                + "  FROM [dbo].[Sessions]\n"
                + "WHERE groupId = ? AND courseId = ?;";
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1, groupId);
        ps.setString(2, courseId);
        ArrayList<transact.Session> ss = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            transact.Session ts = this.getSession(groupId, courseId, rs.getInt("sessionNo"));
            ss.add(ts);
        }
        return ss;
    }
    
    public transact.Session getSession(String groupId, String courseId, int sessionNo) throws SQLException, ParseException {
        String sql = "SELECT [Sessions].[groupId]\n"
                + "      ,[Sessions].[courseId]\n"
                + "      ,[sessionNo]\n"
                + "      ,[Sessions].[lecturerId]\n"
                + "      ,[takeStatus]\n"
                + "      ,[roomId]\n"
                + "      ,[slot]\n"
                + "      ,[startDate]\n"
                + "      ,[eduNextURL]\n"
                + "      ,[meetURL]\n"
                + "  FROM [dbo].[Sessions]\n"
                + "  JOIN [Class]\n"
                + "  ON [Sessions].[courseId] = [Class].[courseId] AND [Sessions].[groupId] = [Class].[groupId]\n"
                + "WHERE [Sessions].groupId =  ? AND [Sessions].courseId = ? AND sessionNo = ?;";
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1, groupId);
        ps.setString(2, courseId);
        ps.setInt(3, sessionNo);
        transact.Session s = new transact.Session();
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            s.setGroupId(rs.getString(("groupId")));
            Course c = this.getCourse(rs.getString("courseId"));
            entity.Session es = this.getSession(c, rs.getInt("sessionNo"));
            s.setSession(es);
            Lecturer l = this.getLecturer(rs.getString("lecturerId"));
            s.setLecturer(l);
            if (rs.getString("takeStatus") != null) {
                s.setTakeStatus(rs.getBoolean("takeStatus"));
            }
            TimeSlot t = this.getTimeSlot(rs.getInt("slot"));
            s.setSlot(t);
            Room r = this.getRoom(rs.getString("roomId"));
            s.setRoom(r);
            s.setStartDate(dateFormat.parse(rs.getString("startDate")));
            s.setEduNextURL(rs.getString("eduNextURL"));
            s.setMeetURL(rs.getString("meetURL"));
        }
        return s;
    }
    public transact.Session getSession(Lecturer lecturer, java.util.Date startDate, TimeSlot slot) throws SQLException, ParseException {
        String sql = "SELECT [groupId]\n"
                + "      ,[courseId]\n"
                + "      ,[sessionNo]\n"
                + "      ,[lecturerId]\n"
                + "  FROM [dbo].[Sessions]\n"
                + "WHERE lecturerId = ? AND slot = ? AND startDate = ?;";
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1, lecturer.getId());
        ps.setInt(2, slot.getSlot());
        ps.setString(3, dateFormat.format(startDate));
        transact.Session s = new transact.Session();
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            s.setLecturer(lecturer);
            s = this.getSession(rs.getString("groupId"), rs.getString("courseId"), rs.getInt("sessionNo"));
        }
        return s;
    }
    
    public ArrayList<transact.Session> getSession(Lecturer lecturer, java.util.Date startDate) throws SQLException, ParseException {
        String sql = "SELECT [groupId]\n"
                + "      ,[courseId]\n"
                + "      ,[sessionNo]\n"
                + "      ,[lecturerId]\n"
                + "  FROM [dbo].[Sessions]\n"
                + "WHERE lecturerId = ? AND startDate = ?\n"
                + "ORDER BY slot ASC;";
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1, lecturer.getId());
        ps.setString(2, dateFormat.format(startDate));
        ArrayList<transact.Session> sessionList = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            transact.Session s = new transact.Session();
            s.setLecturer(lecturer);
            s = this.getSession(rs.getString("groupId"), rs.getString("courseId"), rs.getInt("sessionNo"));
            sessionList.add(s);
        }
        return sessionList;
    }
    
    public Attend getAttend(Student student, java.util.Date startDate, TimeSlot slot) throws SQLException, ParseException {
        String sql = "SELECT Attends.[enrollId]\n"
                + "		, CourseEnroll.courseId\n"
                + "      ,Attends.[groupId]\n"
                + "      ,Attends.[sessionNo]\n"
                + "      ,[attendStatus]\n"
                + "      ,[comment]\n"
                + "      ,[modifiedTime]\n"
                + "      ,[modifiedBy]\n"
                + "  FROM [dbo].[Attends]\n"
                + "  JOIN CourseEnroll\n"
                + "  ON Attends.enrollId = CourseEnroll.enrollId\n"
                + "  JOIN [Sessions]\n"
                + "  ON CourseEnroll.courseId = [Sessions].courseId AND Attends.groupId = [Sessions].groupId AND Attends.sessionNo = [Sessions].sessionNo\n"
                + "WHERE studentId = ? AND slot = ? AND startDate = ?;";
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1, student.getId());
        ps.setInt(2, slot.getSlot());
        ps.setString(3, dateFormat.format(startDate));
        Attend a = new Attend();
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            a.setEnrollId(rs.getInt("enrollId"));
            a.setStudent(student);
            transact.Session s = this.getSession(rs.getString("groupId"),
                    rs.getString("courseId"), rs.getInt("sessionNo"));
            a.setSession(s);
            if (rs.getString("attendStatus") != null) {
                a.setAttendStatus(rs.getBoolean("attendStatus"));
            }
            a.setComment(rs.getString("comment"));
            if (rs.getString("modifiedTime") != null) {
                a.setModifiedTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .parse(rs.getString("modifiedTime")));
            }
            Lecturer taker = new Lecturer();
            taker.setId(rs.getString("modifiedBy"));
            a.setModifier(taker);
        }
        return a;
    }
    
    public ArrayList<Attend> getAttends(Student student, java.util.Date startDate) throws SQLException, ParseException {
        String sql = "SELECT Attends.[enrollId]\n"
                + "		, CourseEnroll.courseId\n"
                + "      ,Attends.[groupId]\n"
                + "      ,Attends.[sessionNo]\n"
                + "      ,[attendStatus]\n"
                + "      ,[comment]\n"
                + "      ,[modifiedTime]\n"
                + "      ,[modifiedBy]\n"
                + "  FROM [dbo].[Attends]\n"
                + "  JOIN CourseEnroll\n"
                + "  ON Attends.enrollId = CourseEnroll.enrollId\n"
                + "  JOIN [Sessions]\n"
                + "  ON CourseEnroll.courseId = [Sessions].courseId AND Attends.groupId = [Sessions].groupId AND Attends.sessionNo = [Sessions].sessionNo\n"
                + "WHERE studentId = ? AND startDate = ?\n"
                + "ORDER BY slot ASC;";
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1, student.getId());
        ps.setString(2, dateFormat.format(startDate));
        ArrayList<Attend> attendList = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Attend a = new Attend();
            a.setEnrollId(rs.getInt("enrollId"));
            a.setStudent(student);
            transact.Session s = this.getSession(rs.getString("groupId"),
                    rs.getString("courseId"), rs.getInt("sessionNo"));
            a.setSession(s);
            if (rs.getString("attendStatus") != null) {
                a.setAttendStatus(rs.getBoolean("attendStatus"));
            }
            a.setComment(rs.getString("comment"));
            if (rs.getString("modifiedTime") != null) {
                a.setModifiedTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .parse(rs.getString("modifiedTime")));
            }
            Lecturer taker = new Lecturer();
            taker.setId(rs.getString("modifiedBy"));
            a.setModifier(taker);
            attendList.add(a);
        }
        return attendList;
    }
    
    public ArrayList<Attend> getAttends(int enrollId) throws SQLException, ParseException {
        String sql = "SELECT Attends.[enrollId]\n"
                + "      ,studentId"
                + "      , CourseEnroll.courseId\n"
                + "      ,Attends.[groupId]\n"
                + "      ,Attends.[sessionNo]\n"
                + "      ,[attendStatus]\n"
                + "      ,[comment]\n"
                + "      ,[modifiedTime]\n"
                + "      ,[modifiedBy]\n"
                + "  FROM [dbo].[Attends]\n"
                + "  JOIN CourseEnroll\n"
                + "  ON Attends.enrollId = CourseEnroll.enrollId\n"
                + "  JOIN [Sessions]\n"
                + "  ON CourseEnroll.courseId = [Sessions].courseId AND Attends.groupId = [Sessions].groupId AND Attends.sessionNo = [Sessions].sessionNo\n"
                + "WHERE Attends.enrollId = ?;";
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setInt(1, enrollId);
        ArrayList<Attend> attendList = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Attend a = new Attend();
            a.setEnrollId(rs.getInt("enrollId"));
            a.setStudent(this.getStudent("studentId"));
            transact.Session s = this.getSession(rs.getString("groupId"),
                    rs.getString("courseId"), rs.getInt("sessionNo"));
            a.setSession(s);
            if (rs.getString("attendStatus") != null) {
                a.setAttendStatus(rs.getBoolean("attendStatus"));
            }
            a.setComment(rs.getString("comment"));
            if (rs.getString("modifiedTime") != null) {
                a.setModifiedTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .parse(rs.getString("modifiedTime")));
            }
            Lecturer taker = new Lecturer();
            taker.setId(rs.getString("modifiedBy"));
            a.setModifier(taker);
            attendList.add(a);
        }
        attendList.sort(new Comparator<Attend>() {
            @Override
            public int compare(Attend o1, Attend o2) {
                return o1.getSession().getSession().getNo() - o2.getSession().getSession().getNo();
            }
        });
        return attendList;
    }
        
    public ArrayList<Attend> getAttends(transact.Session session) throws SQLException, ParseException {
        String sql = "SELECT Attends.[enrollId]"
                + "      ,studentId\n"
                + "		, CourseEnroll.courseId\n"
                + "      ,Attends.[groupId]\n"
                + "      ,Attends.[sessionNo]\n"
                + "      ,[attendStatus]\n"
                + "      ,[comment]\n"
                + "      ,[modifiedTime]\n"
                + "      ,[modifiedBy]\n"
                + "  FROM [dbo].[Attends]\n"
                + "  JOIN CourseEnroll\n"
                + "  ON Attends.enrollId = CourseEnroll.enrollId\n"
                + "  JOIN [Sessions]\n"
                + "  ON CourseEnroll.courseId = [Sessions].courseId AND Attends.groupId = [Sessions].groupId AND Attends.sessionNo = [Sessions].sessionNo\n"
                + "WHERE CourseEnroll.courseId = ? AND Attends.[groupId]= ? AND Attends.[sessionNo] = ?\n"
                + "ORDER BY studentId ASC;";
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1, session.getSession().getCourse().getId());
        ps.setString(2, session.getGroupId());
        ps.setInt(3, session.getSession().getNo());
        ArrayList<Attend> as = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Attend a = new Attend();
            a.setEnrollId(rs.getInt("enrollId"));
            Student s = this.getStudent(rs.getString("studentId"));
            a.setStudent(s);
            a.setSession(session);
            // Not yet;
            if (rs.getString("attendStatus") != null) {
                a.setAttendStatus(rs.getBoolean("attendStatus"));
            }
            a.setComment(rs.getString("comment"));
            if (rs.getString("modifiedTime") != null) {
                a.setModifiedTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .parse(rs.getString("modifiedTime")));
            }
            
            Lecturer taker = new Lecturer();
            taker.setId(rs.getString("modifiedBy"));
            a.setModifier(taker);
            as.add(a);
        }
        return as;
    }
    
    public Grade getGrade(int enrollId, GradeItem gradeItem) throws SQLException, ParseException, ClassNotFoundException {
        String sql = "SELECT Exam.examCode, studentId, CourseEnroll.courseId, startTime, duration, [value], note\n"
                + "FROM Grades\n"
                + "JOIN CourseEnroll\n"
                + "ON Grades.enrollId = CourseEnroll.enrollId\n"
                + "JOIN Exam\n"
                + "ON Grades.examCode = Exam.examCode\n"
                + "WHERE Grades.enrollId = ? AND itemId = ?;";
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setInt(1, enrollId);
        ps.setString(2, gradeItem.getId());
        Grade grade = new Grade();
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            Student student = new DBContext().getStudent(rs.getString("studentId"));
            grade.setStudent(student);
            Exam exam = new Exam();
            exam.setExamCode(rs.getString("examCode"));
            Course course = new DBContext().getCourse("courseId");
            exam.setCourse(course);
            exam.setGradeItem(gradeItem);
            exam.setStartTime(df.parse(rs.getString("startTime")));
            exam.setDuration((LocalTime.parse(rs.getString("duration")
                    .substring(0, 8), timeFormat)));
            grade.setExam(exam);
            grade.setValue(rs.getFloat("value"));
            grade.setNote(rs.getString("note"));
        }
        return grade;
    }
    
    public GradeCategory getGradeCategory(String id) throws SQLException {
        String sql = "SELECT [categoryId]\n"
                + "      ,[name]\n"
                + "      ,[categoryDescription]\n"
                + "  FROM [dbo].[GradeCategory]\n"
                + "WHERE categoryId = ?;";
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1, id);
        GradeCategory gradeCategory = new GradeCategory();
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            gradeCategory.setId(id);
            gradeCategory.setName(rs.getString("name"));
            gradeCategory.setDescription(rs.getString("categoryDescription"));
        }
        return gradeCategory;
    }
    
    public GradeItem getGradeItem(String id) throws SQLException {
        String sql = "SELECT [itemId]\n"
                + "      ,[categoryId]\n"
                + "      ,[itemName]\n"
                + "  FROM [dbo].[GradeItem]\n"
                + "WHERE itemId = ?;";
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1, id);
        GradeItem gradeItem = new GradeItem();
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            gradeItem.setId(id);
            GradeCategory category = this.getGradeCategory(rs.getString("categoryId"));
            gradeItem.setCategory(category);
            gradeItem.setName(rs.getString("itemName"));
        }
        return gradeItem;
    }
    
    public CourseGrade getCourseGrade(Course course) throws SQLException {
        String sql = "SELECT [courseId]\n"
                + "      ,[itemId]\n"
                + "      ,[weight]\n"
                + "      ,[criteria]\n"
                + "  FROM [dbo].[CourseGrades]\n"
                + "WHERE courseId = ?;";
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1, course.getId());
        CourseGrade courseGrade = new CourseGrade();
        courseGrade.setCourse(course);
        ArrayList<GradeItem> items = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            GradeItem gradeItem = this.getGradeItem(rs.getString("itemId"));
            gradeItem.setWeight(rs.getInt("weight"));
            gradeItem.setCriteria(rs.getFloat("criteria"));
            items.add(gradeItem);
        }
        courseGrade.setGradeItems(items);
        return courseGrade;
    }

    public HashMap<Integer, ArrayList<CourseEnroll>> getCourseEnrolls(String studentId) throws SQLException, ParseException {
        String sql = "SELECT enrollId, semesterId\n"
                + "FROM CourseEnroll\n"
                + "WHERE studentId = ?;";
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1, studentId);
        HashMap<Integer, ArrayList<CourseEnroll>> semesterMap = new HashMap<>();
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int semesterId = rs.getInt("semesterId");
            CourseEnroll courseEnroll = this.getCourseEnroll(rs.getInt("enrollId"));
            ArrayList<CourseEnroll> courseEnrollList = semesterMap.get(semesterId);
            if (courseEnrollList == null) {
                courseEnrollList = new ArrayList<>();
                semesterMap.put(semesterId, courseEnrollList);
            }
            courseEnrollList.add(courseEnroll);
        }
        return semesterMap;    
    }
    
    public CourseEnroll getCourseEnroll(Student student, Course course) throws SQLException, ParseException {
        String sql = "SELECT TOP(1) enrollId\n"
                + "  FROM [dbo].[CourseEnroll]\n"
                + "WHERE studentId = ? AND courseId = ?\n"
                + "ORDER BY enrollId DESC;";
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1, student.getId());
        ps.setString(2, course.getId());
        CourseEnroll courseEnroll = new CourseEnroll();
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {  
            courseEnroll = this.getCourseEnroll(rs.getInt("enrollId"));
        }
        return courseEnroll;
    }
    
    public ArrayList<CourseEnroll> getCourseEnrolls(int semesterId) throws SQLException, ParseException {
        String sql = "SELECT enrollId\n"
                + "FROM CourseEnroll\n"
                + "WHERE semesterId = ?;";
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setInt(1, semesterId);
        ArrayList<CourseEnroll> enrollList = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            CourseEnroll enroll = this.getCourseEnroll(rs.getInt("enrollId"));
            enrollList.add(enroll);
        }
        return enrollList;
    }
    
    public CourseEnroll getCourseEnroll(int enrollId) throws SQLException, ParseException {
        String sql = "SELECT enrollId"
                + "      , studentId\n"
                + "      ,[courseId]\n"
                + "      ,[groupId]\n"
                + "      ,[semesterId]\n"
                + "      ,[average]\n"
                + "      ,[state]\n"
                + "  FROM [dbo].[CourseEnroll]\n"
                + "WHERE enrollId = ?;";
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setInt(1, enrollId);
        CourseEnroll courseEnroll = new CourseEnroll();
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {  
            courseEnroll.setId(rs.getInt("enrollId"));
            courseEnroll.setStudent(this.getStudent(rs.getString("studentId")));
            courseEnroll.setCourse(this.getCourse(rs.getString("courseId")));
            courseEnroll.setGroupId(rs.getString("groupId"));
            Semester semester = this.getSemester(rs.getInt("semesterId"));
            if (rs.getString("average") != null) {
                courseEnroll.setAverage(rs.getFloat("average"));
            }
            courseEnroll.setState(rs.getString("state"));
            courseEnroll.setSemester(semester);
        }
        return courseEnroll;
    }
    public ArrayList<Request> getRequests(String studentId) throws SQLException, ParseException {
        String sql = "SELECT [requestId]\n"
                + "  FROM [dbo].[Request]"
                + "JOIN CourseEnroll\n"
                + "ON CourseEnroll.enrollId = Request.enrollId\n"
                + "WHERE studentId = ?;";
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1, studentId);
        ArrayList<Request> requestList = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Request request = this.getRequest(rs.getInt("requestId"));
            requestList.add(request);
        }
        return requestList;
    }
    
    public Request getRequest(int id) throws SQLException, ParseException {
        String sql = "SELECT [requestId]\n"
                + "      ,[enrollId]\n"
                + "      ,[classAfter]\n"
                + "      ,[purpose]\n"
                + "      ,[requestState]\n"
                + "      ,[response]\n"
                + "      ,[staffId]\n"
                + "      ,[sendTime]\n"
                + "  FROM [dbo].[Request]\n"
                + "WHERE requestId = ?;";
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setInt(1, id);
        Request request = new Request();
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            request.setId(id);
            request.setCourseEnroll(this.getCourseEnroll(rs.getInt("enrollId")));
            request.setToClass(rs.getString("classAfter"));
            request.setPurpose(rs.getString("purpose"));
            if (rs.getString("requestState") != null) {
                request.setState(rs.getBoolean("requestState"));
            }
            request.setResponse(rs.getString("response"));
            request.setSendTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS").parse(rs.getString("sendTime")));
        }
        return request;
    }
    
    public boolean insertCourseEnroll(String studentId, String courseId, String groupId, int semesterId) throws SQLException {
        String sql = "INSERT INTO [dbo].[CourseEnroll]\n"
                + "           ([studentId]\n"
                + "           ,[courseId]\n"
                + "           ,[groupId]\n"
                + "           ,[semesterId])\n"
                + "     VALUES\n"
                + "           (?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?)";
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1, studentId);
        ps.setString(2, courseId);
        ps.setString(3, groupId);
        ps.setInt(4, semesterId);
        int rowAffected = ps.executeUpdate();
        return rowAffected > 0;
    }

    public boolean updateSession(transact.Session session) throws SQLException {
        String sql = "UPDATE [dbo].[Sessions]\n"
                + "   SET [takeStatus] = ?\n"
                + " WHERE groupId = ? AND courseId = ? AND sessionNo = ?";
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setBoolean(1, true);
        ps.setString(2, session.getGroupId());
        ps.setString(3, session.getSession().getCourse().getId());
        ps.setInt(4, session.getSession().getNo());
        int affectedRow = ps.executeUpdate();
        return affectedRow > 0;
    }
    
    public boolean updateAttends(ArrayList<Attend> attendList) throws SQLException {
        String updateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
        int rowsAffected = 0;
        for (Attend a : attendList) {
            String sql = "UPDATE [dbo].[Attends]\n"
                    + "   SET [attendStatus] = ?\n"
                    + "      ,[comment] = ?\n"
                    + "      ,[modifiedTime] = ?\n"
                    + "      ,[modifiedBy] = ?\n"
                    + " WHERE enrollId = ? AND sessionNo = ?";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setBoolean(1, a.isPresent());
            ps.setString(2, a.getComment());
            ps.setString(3, updateTime);
            ps.setString(4, a.getModifier().getId());
            ps.setInt(5, a.getEnrollId());
            ps.setInt(6, a.getSession().getSession().getNo());
            rowsAffected += ps.executeUpdate();
        }
        return rowsAffected > 0;
    }
    
    public boolean insertRequest(int enrollId, String classAfter, String reason) throws SQLException {
        String sql = "INSERT INTO [dbo].[Request]\n"
                + "           ( enrollId\n"
                + "           ,[classAfter]\n"
                + "           ,[purpose])\n"
                + "     VALUES\n"
                + "           (?\n"
                + "           ,?\n"
                + "           ,?);";
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setInt(1, enrollId);
        ps.setString(2, classAfter);
        ps.setString(3, reason);
        int affectedRow = ps.executeUpdate();
        return affectedRow > 0;

    }

    public boolean deleteCourseEnroll(String studentId, String courseId, int semesterId) throws SQLException {
        if (studentId == null || courseId == null) {
            throw new IllegalArgumentException("studentId and courseId cannot be null");
        }

        if (semesterId <= 0) {
            throw new IllegalArgumentException("semesterId cannot be less than 1");
        }

        String sql = "DELETE FROM [dbo].[CourseEnroll]\n" +
                "where studentId = ? and courseId = ? and semesterId = ?;";
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1, studentId);
        ps.setString(2, courseId);
        ps.setInt(3, semesterId);
        int affectedRow = ps.executeUpdate();
        return affectedRow > 0;
    }
    
    
}
