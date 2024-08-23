package test;

import entity.Session;
import entity.TimeSlot;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DBContextTest {
    private final db.DBContext dbContext = new db.DBContext();

    public DBContextTest() throws SQLException, ClassNotFoundException {
    }

    private String generateString(int size) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < size; i++) {
            str.append('a');
        }
        return str.toString();
    }

    @Test
    public void insertRequest_UTCID01() throws SQLException {
        Assert.assertEquals(true, dbContext.insertRequest(1, "SE1840", "im tried"));
    }

    @Test
    public void insertRequest_UTCID02() throws SQLException {
         Assert.assertEquals(false,  dbContext.insertRequest(1, "SE1840", null));
    }

    @Test
    public void insertRequest_UTCID03() throws SQLException {
        Assert.assertEquals(false,  dbContext.insertRequest(1, "SE1840", this.generateString(257)));
    }

    @Test
    public void insertRequest_UTCID04() throws SQLException {
        Assert.assertEquals(false, dbContext.insertRequest(1, "SE1817", "im tried"));
    }

    @Test
    public void insertRequest_UTCID05() throws SQLException {
        Assert.assertEquals(false, dbContext.insertRequest(1, "SE1800", "im tried"));
    }

    @Test
    public void insertRequest_UTCID06() throws SQLException {
        Assert.assertEquals(false, dbContext.insertRequest(1, "SE170482", "im tried"));
    }

    @Test
    public void insertRequest_UTCID07() throws SQLException {
        Assert.assertEquals(false, dbContext.insertRequest(1, null, "im tried"));
    }

    @Test
    public void insertRequest_UTCID08() throws SQLException {
        Assert.assertEquals(false, dbContext.insertRequest(7, "SE1840", "im  tried"));
    }

    @Test
    public void insertRequest_UTCID09() throws SQLException {
        Assert.assertEquals(false, dbContext.insertRequest(0, "SE1840", "im tried"));
    }

    @Test
    public void updateSession_UTCID01() throws SQLException {
        transact.Session session = new transact.Session();
        session.setGroupId("SE1817");
        Session s = new Session();
        entity.Course course = new entity.Course();
        course.setId("PRJ301");
        s.setCourse(course);
        s.setNo(1);
        session.setSession(s);
        session.setTakeStatus(true);
        Assert.assertEquals(true, dbContext.updateSession(session));
    }

    @Test
    public void updateSession_UTCID02() throws SQLException {
        transact.Session session = new transact.Session();
        session.setGroupId("SE1817");
        Session s = new Session();
        entity.Course course = new entity.Course();
        course.setId("PRJ301");
        s.setCourse(course);
        s.setNo(1);
        session.setSession(s);
        session.setTakeStatus(false);
        Assert.assertEquals(true, dbContext.updateSession(session));
    }

    @Test
    public void updateSession_UTCID03() throws SQLException {
        transact.Session session = new transact.Session();
        session.setGroupId("SE1817");
        Session s = new Session();
        entity.Course course = new entity.Course();
        course.setId("PRJ301");
        s.setCourse(course);
        s.setNo(11);
        session.setSession(s);
        session.setTakeStatus(true);
        Assert.assertEquals(false, dbContext.updateSession(session));
    }

    @Test
    public void updateSession_UTCID04() throws SQLException {
        transact.Session session = new transact.Session();
        session.setGroupId("SE1817");
        Session s = new Session();
        entity.Course course = new entity.Course();
        course.setId("PRJ301");
        s.setCourse(course);
        s.setNo(0);
        session.setSession(s);
        session.setTakeStatus(true);
        Assert.assertEquals(false, dbContext.updateSession(session));
    }

    @Test
    public void updateSession_UTCID05() throws SQLException {
        transact.Session session = new transact.Session();
        session.setGroupId("SE1817");
        Session s = new Session();
        entity.Course course = new entity.Course();
        course.setId("SWT301");
        s.setCourse(course);
        s.setNo(7);
        session.setSession(s);
        session.setTakeStatus(true);
        Assert.assertEquals(false, dbContext.updateSession(session));
    }

    @Test
    public void updateSession_UTCID06() throws SQLException {
        transact.Session session = new transact.Session();
        session.setGroupId("SE1817");
        Session s = new Session();
        entity.Course course = new entity.Course();
        course.setId("SWT300c");
        s.setCourse(course);
        s.setNo(7);
        session.setSession(s);
        session.setTakeStatus(true);
        Assert.assertEquals(false, dbContext.updateSession(session));
    }

    @Test
    public void updateSession_UTCID07() throws SQLException {
        transact.Session session = new transact.Session();
        session.setGroupId("SE1817");
        Session s = new Session();
        entity.Course course = new entity.Course();
        course.setId(null);
        s.setCourse(course);
        s.setNo(7);
        session.setSession(s);
        session.setTakeStatus(true);
        Assert.assertEquals(false, dbContext.updateSession(session));

    }

    @Test
    public void updateSession_UTCID08() throws SQLException {
        transact.Session session = new transact.Session();
        session.setGroupId("SE1800");
        Session s = new Session();
        entity.Course course = new entity.Course();
        course.setId("PRJ301");
        s.setCourse(course);
        s.setNo(7);
        session.setSession(s);
        session.setTakeStatus(true);
        Assert.assertEquals(false, dbContext.updateSession(session));
    }

    @Test
    public void updateSession_UTCID09() throws SQLException {
        transact.Session session = new transact.Session();
        session.setGroupId("SE170482");
        Session s = new Session();
        entity.Course course = new entity.Course();
        course.setId("PRJ301");
        s.setCourse(course);
        s.setNo(7);
        session.setSession(s);
        session.setTakeStatus(true);
        Assert.assertEquals(false, dbContext.updateSession(session));
    }

    @Test
    public void updateSession_UTCID10() throws SQLException {
        transact.Session session = new transact.Session();
        session.setGroupId(null);
        Session s = new Session();
        entity.Course course = new entity.Course();
        course.setId("PRJ301");
        s.setCourse(course);
        s.setNo(7);
        session.setSession(s);
        session.setTakeStatus(true);
        Assert.assertEquals(false, dbContext.updateSession(session));
    }

    @Test
    public void getCourseEnroll_UTCID01() throws SQLException, ParseException {
        entity.Student student = new entity.Student();
        student.setId("hieutche170482");
        entity.Course course = new entity.Course();
        course.setId("PRJ301");

        Assert.assertEquals(1, dbContext.getCourseEnroll(student, course).getId());
    }


    @Test
    public void getCourseEnroll_UTCID02() throws SQLException, ParseException {
        entity.Student student = new entity.Student();
        student.setId("hieutche170482");
        entity.Course course = new entity.Course();
        course.setId("PRF192");

        Assert.assertEquals(0, dbContext.getCourseEnroll(student, course).getId());
    }

    @Test
    public void getCourseEnroll_UTCID03() throws SQLException, ParseException {
        entity.Student student = new entity.Student();
        student.setId("hieutche170482");
        entity.Course course = new entity.Course();
        course.setId("SWT301");

        Assert.assertEquals(0, dbContext.getCourseEnroll(student, course).getId());
    }

    @Test
    public void getCourseEnroll_UTCID04() throws SQLException, ParseException {
        entity.Student student = new entity.Student();
        student.setId("hieutche170482");
        entity.Course course = new entity.Course();
        course.setId("SWT301c");

        Assert.assertEquals(0, dbContext.getCourseEnroll(student, course).getId());
    }

    @Test
    public void getCourseEnroll_UTCID05() throws SQLException, ParseException {
        entity.Student student = new entity.Student();
        student.setId("hieutche170482");
        entity.Course course = new entity.Course();
        course.setId(null);

        Assert.assertEquals(0, dbContext.getCourseEnroll(student, course).getId());
    }

    @Test
    public void getCourseEnroll_UTCID06() throws SQLException, ParseException {
        entity.Student student = new entity.Student();
        student.setId("tranconghieu");
        entity.Course course = new entity.Course();
        course.setId("PRJ301");

        Assert.assertEquals(0, dbContext.getCourseEnroll(student, course).getId());
    }

    @Test
    public void getCourseEnroll_UTCID07() throws SQLException, ParseException {
        entity.Student student = new entity.Student();
        student.setId(this.generateString(33));
        entity.Course course = new entity.Course();
        course.setId("PRJ301");

        Assert.assertEquals(0, dbContext.getCourseEnroll(student, course).getId());
    }

    @Test
    public void getCourseEnroll_UTCID08() throws SQLException, ParseException {
        entity.Student student = new entity.Student();
        student.setId(null);
        entity.Course course = new entity.Course();
        course.setId("PRJ301");

        Assert.assertEquals(0, dbContext.getCourseEnroll(student, course).getId());
    }

    @Test
    public void insertCourseEnroll_UTCID01() throws SQLException {
        dbContext.deleteCourseEnroll("HaiNTHE170492", "PRJ301", 5);
        Assert.assertEquals(true,
                dbContext.insertCourseEnroll("HaiNTHE170492", "PRJ301", "SE1817", 5));
    }

    @Test
    public void insertCourseEnroll_UTCID02() throws SQLException {
        dbContext.deleteCourseEnroll("HaiNTHE170492", "PRJ301", 5);
        Assert.assertEquals(false,
                dbContext.insertCourseEnroll("HaiNTHE170492", "PRJ301", "SE1817", 1));
    }

    @Test
    public void insertCourseEnroll_UTCID03() throws SQLException {
        dbContext.deleteCourseEnroll("HaiNTHE170492", "PRJ301", 5);
        Assert.assertEquals(false,
                dbContext.insertCourseEnroll("HaiNTHE170492", "PRJ301", "SE1817", -1));
    }

    @Test
    public void insertCourseEnroll_UTCID04() throws SQLException {
        dbContext.deleteCourseEnroll("HaiNTHE170492", "PRJ301", 5);
        Assert.assertEquals(false,
                dbContext.insertCourseEnroll("HaiNTHE170492", "PRJ301", "SE1800", 5));
    }

    @Test
    public void insertCourseEnroll_UTCID05() throws SQLException {
        dbContext.deleteCourseEnroll("HaiNTHE170492", "PRJ301", 5);
        Assert.assertEquals(false,
                dbContext.insertCourseEnroll("HaiNTHE170492", "PRJ301", "SE170482", 5));
    }

    @Test
    public void insertCourseEnroll_UTCID06() throws SQLException {
        dbContext.deleteCourseEnroll("HaiNTHE170492", "PRJ301", 5);
        Assert.assertEquals(false,
                dbContext.insertCourseEnroll("HaiNTHE170492", "PRJ301", null, 5));
    }

    @Test
    public void insertCourseEnroll_UTCID07() throws SQLException {
        dbContext.deleteCourseEnroll("HaiNTHE170492", "PRJ301", 5);
        Assert.assertEquals(false,
                dbContext.insertCourseEnroll("HaiNTHE170492", "SWT301", "SE1817", 5));
    }

    @Test
    public void insertCourseEnroll_UTCID08() throws SQLException {
        dbContext.deleteCourseEnroll("HaiNTHE170492", "PRJ301", 5);
        Assert.assertEquals(false,
                dbContext.insertCourseEnroll("HaiNTHE170492", "PRJ301", "SE170482", 5));
    }

    @Test
    public void insertCourseEnroll_UTCID09() throws SQLException {
        dbContext.deleteCourseEnroll("HaiNTHE170492", "PRJ301", 5);
        Assert.assertEquals(false,
                dbContext.insertCourseEnroll("HaiNTHE170492", "PRJ301", null, 5));
    }

    @Test
    public void insertCourseEnroll_UTCID10() throws SQLException {
        Assert.assertEquals(false,
                dbContext.insertCourseEnroll("HieuTCHE17082", "PRJ301", "SE1817", 5));
    }

    @Test
    public void insertCourseEnroll_UTCID11() throws SQLException {
        Assert.assertEquals(false,
                dbContext.insertCourseEnroll(this.generateString(33), "PRJ301", "SE1817", 5));
    }

    @Test
    public void insertCourseEnroll_UTCID12() throws SQLException {
        Assert.assertEquals(false,
                dbContext.insertCourseEnroll("tranconghieu", "PRJ301", "SE1817", 5));
    }

    @Test
    public void insertCourseEnroll_UTCID13() throws SQLException {
        Assert.assertEquals(false,
                dbContext.insertCourseEnroll(null, "PRJ301", "SE1817", 5));
    }

    @Test
    public void getClass_UTCID01() throws SQLException {
        entity.Semester semester = new entity.Semester();
        semester.setId(5);
        entity.Course course = new entity.Course();
        course.setId("PRJ301");
        ArrayList<String> expected = new ArrayList<>();
        expected.add("SE1817");
        expected.add("SE1840");
        Assert.assertEquals(2, dbContext.getClass(semester, course).size());
    }

    @Test
    public void getClass_UTCID02() throws SQLException {
        entity.Semester semester = new entity.Semester();
        semester.setId(5);
        entity.Course course = new entity.Course();
        course.setId("SWT301");
        Assert.assertEquals(0, dbContext.getClass(semester, course).size());
    }

    @Test
    public void getClass_UTCID03() throws SQLException {
        entity.Semester semester = new entity.Semester();
        semester.setId(5);
        entity.Course course = new entity.Course();
        course.setId("SWT301c");
        Assert.assertEquals(0, dbContext.getClass(semester, course).size());
    }

    @Test
    public void getClass_UTCID04() throws SQLException {
        entity.Semester semester = new entity.Semester();
        semester.setId(5);
        entity.Course course = new entity.Course();
        course.setId(this.generateString(7));
        Assert.assertEquals(0, dbContext.getClass(semester, course).size());
    }

    @Test
    public void getClass_UTCID05() throws SQLException {
        entity.Semester semester = new entity.Semester();
        semester.setId(5);
        entity.Course course = new entity.Course();
        course.setId(null);
        Assert.assertEquals(0, dbContext.getClass(semester, course).size());
    }

    @Test
    public void getClass_UTCID06() throws SQLException {
        entity.Semester semester = new entity.Semester();
        semester.setId(1);
        entity.Course course = new entity.Course();
        course.setId("PRJ301");
        Assert.assertEquals(0, dbContext.getClass(semester, course).size());
    }

    @Test
    public void getClass_UTCID07() throws SQLException {
        entity.Semester semester = new entity.Semester();
        semester.setId(7);
        entity.Course course = new entity.Course();
        course.setId("PRJ301");
        Assert.assertEquals(0, dbContext.getClass(semester, course).size());
    }

    @Test
    public void getClass_UTCID08() throws SQLException {
        entity.Semester semester = new entity.Semester();
        semester.setId(-1);
        entity.Course course = new entity.Course();
        course.setId("PRJ301");
        Assert.assertEquals(0, dbContext.getClass(semester, course).size());
    }

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Test
    public void getAttend_UTCID01() throws SQLException, ParseException {
        entity.Student student = new entity.Student();
        student.setId("hieutche170482");
        Date startDate = dateFormat.parse("2024-05-27");
        entity.TimeSlot slot = new TimeSlot();
        slot.setSlot(3);
        Assert.assertEquals(1,
                dbContext.getAttend(student, startDate, slot).getEnrollId());
    }

    @Test
    public void getAttend_UTCID02() throws SQLException, ParseException {
        entity.Student student = new entity.Student();
        student.setId("hieutche170482");
        Date startDate = dateFormat.parse("2024-05-27");
        entity.TimeSlot slot = new TimeSlot();
        slot.setSlot(5);
        Assert.assertEquals(0,
                dbContext.getAttend(student, startDate, slot).getEnrollId());
    }

    @Test
    public void getAttend_UTCID03() throws SQLException, ParseException {
        entity.Student student = new entity.Student();
        student.setId("hieutche170482");
        Date startDate = dateFormat.parse("2024-05-27");
        entity.TimeSlot slot = new TimeSlot();
        slot.setSlot(7);
        Assert.assertEquals(0,
                dbContext.getAttend(student, startDate, slot).getEnrollId());
    }

    @Test
    public void getAttend_UTCID04() throws SQLException, ParseException {
        entity.Student student = new entity.Student();
        student.setId("hieutche170482");
        Date startDate = dateFormat.parse("2024-05-27");
        entity.TimeSlot slot = new TimeSlot();
        slot.setSlot(-1);
        Assert.assertEquals(0,
                dbContext.getAttend(student, startDate, slot).getEnrollId());
    }

    @Test
    public void getAttend_UTCID05() throws SQLException, ParseException {
        entity.Student student = new entity.Student();
        student.setId("hieutche170482");
        Date startDate = dateFormat.parse("2024-05-28");
        entity.TimeSlot slot = new TimeSlot();
        slot.setSlot(3);
        Assert.assertEquals(0,
                dbContext.getAttend(student, startDate, slot).getEnrollId());
    }

    @Test
    public void getAttend_UTCID06() throws SQLException, ParseException {
        entity.Student student = new entity.Student();
        student.setId("hieutche170482");
        Date startDate = null;
        entity.TimeSlot slot = new TimeSlot();
        slot.setSlot(3);
        Assert.assertEquals(0,
                dbContext.getAttend(student, startDate, slot).getEnrollId());
    }

    @Test
    public void getAttend_UTCID07() throws SQLException, ParseException {
        entity.Student student = new entity.Student();
        student.setId("hainthe170492");
        Date startDate =  dateFormat.parse("2024-05-27");
        entity.TimeSlot slot = new TimeSlot();
        slot.setSlot(3);
        Assert.assertEquals(0,
                dbContext.getAttend(student, startDate, slot).getEnrollId());
    }

    @Test
    public void getAttend_UTCID09() throws SQLException, ParseException {
        entity.Student student = new entity.Student();
        student.setId("tranconghieu");
        Date startDate =  dateFormat.parse("2024-05-27");
        entity.TimeSlot slot = new TimeSlot();
        slot.setSlot(3);
        Assert.assertEquals(0,
                dbContext.getAttend(student, startDate, slot).getEnrollId());
    }

    @Test
    public void getAttend_UTCID10() throws SQLException, ParseException {
        entity.Student student = new entity.Student();
        student.setId(this.generateString(33));
        Date startDate =  dateFormat.parse("2024-05-27");
        entity.TimeSlot slot = new TimeSlot();
        slot.setSlot(3);
        Assert.assertEquals(0,
                dbContext.getAttend(student, startDate, slot).getEnrollId());
    }

    @Test
    public void getAttend_UTCID11() throws SQLException, ParseException {
        entity.Student student = new entity.Student();
        student.setId(null);
        Date startDate =  dateFormat.parse("2024-05-27");
        entity.TimeSlot slot = new TimeSlot();
        slot.setSlot(3);
        Assert.assertEquals(0,
                dbContext.getAttend(student, startDate, slot).getEnrollId());
    }

    @Test
    public void getAttends_UTCID01() throws SQLException, ParseException {
        transact.Session session = new transact.Session();
        entity.Course course = new entity.Course();
        course.setId("PRJ301");
        session.setGroupId("SE1817");
        Session inner = new Session();
        inner.setCourse(course);
        inner.setNo(7);
        session.setSession(inner);

        Assert.assertEquals(1,
                dbContext.getAttends(session).size());
    }

    @Test
    public void getAttends_UTCID02() throws SQLException, ParseException {
        transact.Session session = new transact.Session();
        entity.Course course = new entity.Course();
        course.setId("PRJ301");
        session.setGroupId("SE1817");
        Session inner = new Session();
        inner.setCourse(course);
        inner.setNo(11);
        session.setSession(inner);

        Assert.assertEquals(0,
                dbContext.getAttends(session).size());
    }

    @Test
    public void getAttends_UTCID03() throws SQLException, ParseException {
        transact.Session session = new transact.Session();
        entity.Course course = new entity.Course();
        course.setId("PRJ301");
        session.setGroupId("SE1817");
        Session inner = new Session();
        inner.setCourse(course);
        inner.setNo(0);
        session.setSession(inner);

        Assert.assertEquals(0,
                dbContext.getAttends(session).size());
    }

    @Test
    public void getAttends_UTCID04() throws SQLException, ParseException {
        transact.Session session = new transact.Session();
        entity.Course course = new entity.Course();
        course.setId("PRJ301");
        session.setGroupId("SE1840");
        Session inner = new Session();
        inner.setCourse(course);
        inner.setNo(7);
        session.setSession(inner);

        Assert.assertEquals(0,
                dbContext.getAttends(session).size());
    }

    @Test
    public void getAttends_UTCID05() throws SQLException, ParseException {
        transact.Session session = new transact.Session();
        entity.Course course = new entity.Course();
        course.setId("PRJ301");
        session.setGroupId("SE1800");
        Session inner = new Session();
        inner.setCourse(course);
        inner.setNo(7);
        session.setSession(inner);

        Assert.assertEquals(0,
                dbContext.getAttends(session).size());
    }

    @Test
    public void getAttends_UTCID06() throws SQLException, ParseException {
        transact.Session session = new transact.Session();
        entity.Course course = new entity.Course();
        course.setId("PRJ301");
        session.setGroupId("SE170492");
        Session inner = new Session();
        inner.setCourse(course);
        inner.setNo(7);
        session.setSession(inner);

        Assert.assertEquals(0,
                dbContext.getAttends(session).size());
    }

    @Test
    public void getAttends_UTCID07() throws SQLException, ParseException {
        transact.Session session = new transact.Session();
        entity.Course course = new entity.Course();
        course.setId("PRJ301");
        session.setGroupId(null);
        Session inner = new Session();
        inner.setCourse(course);
        inner.setNo(7);
        session.setSession(inner);

        Assert.assertEquals(0,
                dbContext.getAttends(session).size());
    }

    @Test
    public void getAttends_UTCID08() throws SQLException, ParseException {
        transact.Session session = new transact.Session();
        entity.Course course = new entity.Course();
        course.setId("SWT301");
        session.setGroupId("SE1817");
        Session inner = new Session();
        inner.setCourse(course);
        inner.setNo(7);
        session.setSession(inner);

        Assert.assertEquals(0,
                dbContext.getAttends(session).size());
    }

    @Test
    public void getAttends_UTCID09() throws SQLException, ParseException {
        transact.Session session = new transact.Session();
        entity.Course course = new entity.Course();
        course.setId("SWT301c");
        session.setGroupId("SE1817");
        Session inner = new Session();
        inner.setCourse(course);
        inner.setNo(7);
        session.setSession(inner);

        Assert.assertEquals(0,
                dbContext.getAttends(session).size());
    }

    @Test
    public void getAttends_UTCID10() throws SQLException, ParseException {
        transact.Session session = new transact.Session();
        entity.Course course = new entity.Course();
        course.setId(null);
        session.setGroupId("SE1817");
        Session inner = new Session();
        inner.setCourse(course);
        inner.setNo(7);
        session.setSession(inner);

        Assert.assertEquals(0,
                dbContext.getAttends(session).size());
    }

        @Test
        public void updateAttends_UTCID01() throws SQLException {
            ArrayList<transact.Attend> arrayList = new ArrayList<>();
            transact.Attend attend = new transact.Attend();
            attend.setEnrollId(1);
            Session inner = new Session();
            inner.setNo(7);
            transact.Session session = new transact.Session();
            session.setSession(inner);
            attend.setSession(session);
            entity.Lecturer modifier = new entity.Lecturer();
            modifier.setId("sonnt5");
            attend.setModifier(modifier);
            attend.setAttendStatus(false);
            attend.setComment("Not yet");
            arrayList.add(attend);
            Assert.assertEquals(true, dbContext.updateAttends(arrayList));
        }

        @Test
        public void updateAttends_UTCID02() throws SQLException {
            ArrayList<transact.Attend> arrayList = new ArrayList<>();
            transact.Attend attend = new transact.Attend();
            attend.setEnrollId(1);
            Session inner = new Session();
            inner.setNo(7);
            transact.Session session = new transact.Session();
            session.setSession(inner);
            attend.setSession(session);
            entity.Lecturer modifier = new entity.Lecturer();
            modifier.setId("sonnt5");
            attend.setModifier(modifier);
            attend.setAttendStatus(true);
            attend.setComment(null);
            arrayList.add(attend);
            Assert.assertEquals(true, dbContext.updateAttends(arrayList));
        }

        @Test
        public void updateAttends_UTCID03() throws SQLException {
            ArrayList<transact.Attend> arrayList = new ArrayList<>();
            transact.Attend attend = new transact.Attend();
            attend.setEnrollId(1);
            Session inner = new Session();
            inner.setNo(7);
            transact.Session session = new transact.Session();
            session.setSession(inner);
            attend.setSession(session);
            entity.Lecturer modifier = new entity.Lecturer();
            modifier.setId("hieutche170482");
            attend.setModifier(modifier);
            attend.setAttendStatus(true);
            attend.setComment(null);
            arrayList.add(attend);
            Assert.assertEquals(false, dbContext.updateAttends(arrayList));
        }

        @Test
        public void updateAttends_UTCID04() throws SQLException {
            ArrayList<transact.Attend> arrayList = new ArrayList<>();
            transact.Attend attend = new transact.Attend();
            attend.setEnrollId(1);
            Session inner = new Session();
            inner.setNo(7);
            transact.Session session = new transact.Session();
            session.setSession(inner);
            attend.setSession(session);
            entity.Lecturer modifier = new entity.Lecturer();
            modifier.setId(null);
            attend.setModifier(modifier);
            attend.setAttendStatus(true);
            attend.setComment(null);
            arrayList.add(attend);
            Assert.assertEquals(false, dbContext.updateAttends(arrayList));
        }

        @Test
        public void updateAttends_UTCID05() throws SQLException {
            ArrayList<transact.Attend> arrayList = new ArrayList<>();
            transact.Attend attend = new transact.Attend();
            attend.setEnrollId(1);
            Session inner = new Session();
            inner.setNo(7);
            transact.Session session = new transact.Session();
            session.setSession(inner);
            attend.setSession(session);
            entity.Lecturer modifier = new entity.Lecturer();
            modifier.setId("sonnt5");
            attend.setModifier(modifier);
            attend.setAttendStatus(true);
            attend.setComment(this.generateString(65));
            arrayList.add(attend);
            Assert.assertEquals(false, dbContext.updateAttends(arrayList));
        }

        @Test
        public void updateAttends_UTCID06() throws SQLException {
            ArrayList<transact.Attend> arrayList = new ArrayList<>();
            transact.Attend attend = new transact.Attend();
            attend.setEnrollId(1);
            Session inner = new Session();
            inner.setNo(7);
            transact.Session session = new transact.Session();
            session.setSession(inner);
            attend.setSession(session);
            entity.Lecturer modifier = new entity.Lecturer();
            modifier.setId("sonnt5");
            attend.setModifier(modifier);
            attend.setAttendStatus(null);
            attend.setComment(null);
            arrayList.add(attend);
            Assert.assertEquals(true, dbContext.updateAttends(arrayList));
        }

        @Test
        public void updateAttends_UTCID07() throws SQLException {
            ArrayList<transact.Attend> arrayList = new ArrayList<>();
            transact.Attend attend = new transact.Attend();
            attend.setEnrollId(1);
            Session inner = new Session();
            inner.setNo(11);
            transact.Session session = new transact.Session();
            session.setSession(inner);
            attend.setSession(session);
            entity.Lecturer modifier = new entity.Lecturer();
            modifier.setId("sonnt5");
            attend.setModifier(modifier);
            attend.setAttendStatus(true);
            attend.setComment(null);
            arrayList.add(attend);
            Assert.assertEquals(false, dbContext.updateAttends(arrayList));
        }

        @Test
        public void updateAttends_UTCID08() throws SQLException {
            ArrayList<transact.Attend> arrayList = new ArrayList<>();
            transact.Attend attend = new transact.Attend();
            attend.setEnrollId(1);
            Session inner = new Session();
            inner.setNo(0);
            transact.Session session = new transact.Session();
            session.setSession(inner);
            attend.setSession(session);
            entity.Lecturer modifier = new entity.Lecturer();
            modifier.setId("sonnt5");
            attend.setModifier(modifier);
            attend.setAttendStatus(true);
            attend.setComment(null);
            arrayList.add(attend);
            Assert.assertEquals(false, dbContext.updateAttends(arrayList));
        }

        @Test
        public void updateAttends_UTCID09() throws SQLException {
            ArrayList<transact.Attend> arrayList = new ArrayList<>();
            transact.Attend attend = new transact.Attend();
            attend.setEnrollId(2);
            Session inner = new Session();
            inner.setNo(7);
            transact.Session session = new transact.Session();
            session.setSession(inner);
            attend.setSession(session);
            entity.Lecturer modifier = new entity.Lecturer();
            modifier.setId("sonnt5");
            attend.setModifier(modifier);
            attend.setAttendStatus(true);
            attend.setComment(null);
            arrayList.add(attend);
            Assert.assertEquals(false, dbContext.updateAttends(arrayList));
        }

        @Test
        public void updateAttends_UTCID10() throws SQLException {
            ArrayList<transact.Attend> arrayList = new ArrayList<>();
            transact.Attend attend = new transact.Attend();
            attend.setEnrollId(7);
            Session inner = new Session();
            inner.setNo(7);
            transact.Session session = new transact.Session();
            session.setSession(inner);
            attend.setSession(session);
            entity.Lecturer modifier = new entity.Lecturer();
            modifier.setId("sonnt5");
            attend.setModifier(modifier);
            attend.setAttendStatus(true);
            attend.setComment(null);
            arrayList.add(attend);
            Assert.assertEquals(false, dbContext.updateAttends(arrayList));
        }

        @Test
        public void updateAttends_UTCID11() throws SQLException {
            ArrayList<transact.Attend> arrayList = new ArrayList<>();
            transact.Attend attend = new transact.Attend();
            attend.setEnrollId(-1);
            Session inner = new Session();
            inner.setNo(7);
            transact.Session session = new transact.Session();
            session.setSession(inner);
            attend.setSession(session);
            entity.Lecturer modifier = new entity.Lecturer();
            modifier.setId("sonnt5");
            attend.setModifier(modifier);
            attend.setAttendStatus(true);
            attend.setComment(null);
            arrayList.add(attend);
            Assert.assertEquals(false, dbContext.updateAttends(arrayList));
        }

    @Test
    public void getSession_1_UTCID01() throws SQLException, ParseException {
        Assert.assertEquals(true,
                dbContext.getSession("SE1817", "PRJ301", 7) != null);
    }

    @Test
    public void getSession_1_UTCID02() throws SQLException, ParseException {
        Assert.assertEquals(false,
                dbContext.getSession("SE1817", "PRJ301", 11).getSession() != null);
    }

    @Test
    public void getSession_1_UTCID03() throws SQLException, ParseException {
        Assert.assertEquals(false,
                dbContext.getSession("SE1817", "PRJ301", 0).getSession() != null);
    }

    @Test
    public void getSession_1_UTCID04() throws SQLException, ParseException {
        Assert.assertEquals(false,
                dbContext.getSession("SE1817", "SWT301", 1).getSession() != null);
    }

    @Test
    public void getSession_1_UTCID05() throws SQLException, ParseException {
        Assert.assertEquals(false,
                dbContext.getSession("SE1817", "SWT301c", 1).getSession() != null);
    }

    @Test
    public void getSession_1_UTCID06() throws SQLException, ParseException {
        Assert.assertEquals(false,
                dbContext.getSession("SE1817", null, 1).getSession() != null);
    }

    @Test
    public void getSession_1_UTCID07() throws SQLException, ParseException {
        Assert.assertEquals(false,
                dbContext.getSession("SE1840", "PRJ301", 1).getSession() != null);
    }

    @Test
    public void getSession_1_UTCID08() throws SQLException, ParseException {
        Assert.assertEquals(false,
                dbContext.getSession("SE1800", "PRJ301", 1).getSession() != null);
    }

    @Test
    public void getSession_1_UTCID09() throws SQLException, ParseException {
        Assert.assertEquals(false,
                dbContext.getSession("SE170482", "PRJ301", 1).getSession() != null);
    }

    @Test
    public void getSession_1_UTCID10() throws SQLException, ParseException {
        Assert.assertEquals(false,
                dbContext.getSession(null, "PRJ301", 1).getSession() != null);
    }

    @Test
    public void getSession_2_UTCID01() throws SQLException, ParseException {
        entity.Lecturer lecturer = new entity.Lecturer();
        lecturer.setId("sonnt5");
        Date startDate = dateFormat.parse("2024-5-27");
        TimeSlot slot = new TimeSlot();
        slot.setSlot(3);
        Assert.assertEquals(true,
                dbContext.getSession(lecturer, startDate, slot).getSession() != null);
    }

    @Test
    public void getSession_2_UTCID02() throws SQLException, ParseException {
        entity.Lecturer lecturer = new entity.Lecturer();
        lecturer.setId("sonnt5");
        Date startDate = dateFormat.parse("2024-5-27");
        TimeSlot slot = new TimeSlot();
        slot.setSlot(5);
        Assert.assertEquals(false,
                dbContext.getSession(lecturer, startDate, slot).getSession() != null);
    }

    @Test
    public void getSession_2_UTCID03() throws SQLException, ParseException {
        entity.Lecturer lecturer = new entity.Lecturer();
        lecturer.setId("sonnt5");
        Date startDate = dateFormat.parse("2024-5-27");
        TimeSlot slot = new TimeSlot();
        slot.setSlot(7);
        Assert.assertEquals(false,
                dbContext.getSession(lecturer, startDate, slot).getSession() != null);
    }

    @Test
    public void getSession_2_UTCID04() throws SQLException, ParseException {
        entity.Lecturer lecturer = new entity.Lecturer();
        lecturer.setId("sonnt5");
        Date startDate = dateFormat.parse("2024-5-27");
        TimeSlot slot = new TimeSlot();
        slot.setSlot(-1);
        Assert.assertEquals(false,
                dbContext.getSession(lecturer, startDate, slot).getSession() != null);
    }

    @Test
    public void getSession_2_UTCID05() throws SQLException, ParseException {
        entity.Lecturer lecturer = new entity.Lecturer();
        lecturer.setId("sonnt5");
        Date startDate = dateFormat.parse("2024-5-28");
        TimeSlot slot = new TimeSlot();
        slot.setSlot(3);
        Assert.assertEquals(false,
                dbContext.getSession(lecturer, startDate, slot).getSession() != null);
    }

    @Test
    public void getSession_2_UTCID06() throws SQLException, ParseException {
        entity.Lecturer lecturer = new entity.Lecturer();
        lecturer.setId("sonnt5");
        Date startDate = null;
        TimeSlot slot = new TimeSlot();
        slot.setSlot(3);
        Assert.assertEquals(false,
                dbContext.getSession(lecturer, startDate, slot).getSession() != null);
    }

    @Test
    public void getSession_2_UTCID07() throws SQLException, ParseException {
        entity.Lecturer lecturer = new entity.Lecturer();
        lecturer.setId("lindtt43");
        Date startDate = dateFormat.parse("2024-5-27");
        TimeSlot slot = new TimeSlot();
        slot.setSlot(3);
        Assert.assertEquals(false,
                dbContext.getSession(lecturer, startDate, slot).getSession() != null);
    }

    @Test
    public void getSession_2_UTCID08() throws SQLException, ParseException {
        entity.Lecturer lecturer = new entity.Lecturer();
        lecturer.setId("hieutc");
        Date startDate = dateFormat.parse("2024-5-27");
        TimeSlot slot = new TimeSlot();
        slot.setSlot(3);
        Assert.assertEquals(false,
                dbContext.getSession(lecturer, startDate, slot).getSession() != null);
    }

    @Test
    public void getSession_2_UTCID09() throws SQLException, ParseException {
        entity.Lecturer lecturer = new entity.Lecturer();
        lecturer.setId(this.generateString(33));
        Date startDate = dateFormat.parse("2024-5-27");
        TimeSlot slot = new TimeSlot();
        slot.setSlot(3);
        Assert.assertEquals(false,
                dbContext.getSession(lecturer, startDate, slot).getSession() != null);
    }

    @Test
    public void getSession_2_UTCID10() throws SQLException, ParseException {
        entity.Lecturer lecturer = new entity.Lecturer();
        lecturer.setId(null);
        Date startDate = dateFormat.parse("2024-5-27");
        TimeSlot slot = new TimeSlot();
        slot.setSlot(3);
        Assert.assertEquals(false,
                dbContext.getSession(lecturer, startDate, slot).getSession() != null);
    }


    @Test
    public void getGrade_UTCID01() throws SQLException, ClassNotFoundException, ParseException {
        entity.GradeItem gradeItem = new entity.GradeItem();
        gradeItem.setId("PT1");
        Assert.assertEquals(true,
                dbContext.getGrade(1, gradeItem) != null);
    }

    @Test
    public void getGrade_UTCID02() throws SQLException, ClassNotFoundException, ParseException {
        entity.GradeItem gradeItem = new entity.GradeItem();
        gradeItem.setId("PT1Re");
        Assert.assertEquals(false,
                dbContext.getGrade(1, gradeItem).getExam() != null);
    }

    @Test
    public void getGrade_UTCID03() throws SQLException, ClassNotFoundException, ParseException {
        entity.GradeItem gradeItem = new entity.GradeItem();
        gradeItem.setId("Assignment");
        Assert.assertEquals(false,
                dbContext.getGrade(1, gradeItem).getExam() != null);
    }

    @Test
    public void getGrade_UTCID04() throws SQLException, ClassNotFoundException, ParseException {
        entity.GradeItem gradeItem = new entity.GradeItem();
        gradeItem.setId(null);
        Assert.assertEquals(false,
                dbContext.getGrade(1, gradeItem).getExam() != null);
    }

    @Test
    public void getGrade_UTCID05() throws SQLException, ClassNotFoundException, ParseException {
        entity.GradeItem gradeItem = new entity.GradeItem();
        gradeItem.setId("PT1");
        Assert.assertEquals(false,
                dbContext.getGrade(2, gradeItem).getExam() != null);
    }

    @Test
    public void getGrade_UTCID06() throws SQLException, ClassNotFoundException, ParseException {
        entity.GradeItem gradeItem = new entity.GradeItem();
        gradeItem.setId("PT1");
        Assert.assertEquals(false,
                dbContext.getGrade(5, gradeItem).getExam() != null);
    }

    @Test
    public void getGrade_UTCID07() throws SQLException, ClassNotFoundException, ParseException {
        entity.GradeItem gradeItem = new entity.GradeItem();
        gradeItem.setId("PT1");
        Assert.assertEquals(false,
                dbContext.getGrade(-1, gradeItem).getExam() != null);
    }

    @Test
    public void deleteCourseEnroll_UTCID01() throws SQLException {
        Assert.assertEquals(true,
                dbContext.deleteCourseEnroll("HaiNTHE170492", "PRJ301",5));

    }

    @Test
    public void deleteCourseEnroll_UTCID02() throws SQLException {
        Assert.assertEquals(false,
                dbContext.deleteCourseEnroll("HaiNTHE170492", "PRJ301",1));
    }

    @Test
    public void deleteCourseEnroll_UTCID03() throws SQLException {
        Assert.assertThrows(IllegalArgumentException.class,
                () -> dbContext.deleteCourseEnroll("HaiNTHE170492", "PRJ301",-1));
    }

    @Test
    public void deleteCourseEnroll_UTCID04() throws SQLException {
        Assert.assertEquals(false, dbContext.deleteCourseEnroll("HaiNTHE170492", "PRF192",5));
    }

    @Test
    public void deleteCourseEnroll_UTCID05() throws SQLException {
        Assert.assertEquals(false, dbContext.deleteCourseEnroll("HaiNTHE170492", "SWT302c",5));
    }

    @Test
    public void deleteCourseEnroll_UTCID06() throws SQLException {
         Assert.assertThrows(IllegalArgumentException.class,
                () -> dbContext.deleteCourseEnroll("HaiNTHE170492", null,5));
    }

    @Test
    public void deleteCourseEnroll_UTCID07() throws SQLException {
        Assert.assertEquals(false, dbContext.deleteCourseEnroll("HieuHieuHieu", "PRJ301",5));
    }

    @Test
    public void deleteCourseEnroll_UTCID08() throws SQLException {
        Assert.assertEquals(false, dbContext.deleteCourseEnroll(this.generateString(33), "PRJ301",5));
    }

    @Test
    public void deleteCourseEnroll_UTCID09W() throws SQLException {
         Assert.assertThrows(IllegalArgumentException.class,
                () -> dbContext.deleteCourseEnroll(null, "PRJ301",5));
    }

}