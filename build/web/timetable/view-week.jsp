<%--
    Document   : view-week
    Created on : Mar 4, 2024, 7:33:44 PM
    Author     : End User
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="entity.*" %>
<%@ page import="transact.*" %>
<%@ page import="java.helper.Week" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Date" %>
<%@page import="java.time.LocalTime" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@page import="java.time.format.DateTimeFormatter" %>
<!doctype html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Ternet | Timetable</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link href="../assets/base.css" rel="stylesheet"/>
    <style>
        .column-sm {
            width: 12.14%;
        }
        .column-md {
            width: 14.99%;
        }
        
        table td {
            border-bottom: unset;
        }
        
        table tbody tr {
            border-bottom: 1.5px solid #ffffff;
        }
        
        .dropdown-menu {
            max-height: 27vh;
            overflow-y: auto;
        }

        .border-not-yet {
            border-left: 2px solid #99aab5;
        }

        .border-present {
            border-left: 2px solid #43b581;
        }

        .border-absent {
            border-left: 2px solid #de2761;
        }

        .text-not-yet {
            color: #99aab5;
        }
        
        .text-present {
            color: #43b581;
        }

        .text-absent {
            color: #de2761;
        } 
        
        .border-left {
            border-left: 1.5px solid #2c2f33;
        }

        .border-bottom {
            border-bottom: 1.5px solid #2c2f33 !important;
        }
        
        .item {
            border: unset;
        }
        
        .item:hover {
            border: unset;
            text-decoration: underline;
        }
        
        .item:focus {
            border: unset;
            text-decoration: underline;
        }
    </style>
</head>

<body class="not-light">
    <jsp:include page="../elements/navbar.jsp"></jsp:include>
    <div class="container-fluid fs-7">
        <div class="container p-5" >
            <div class="row g-4">
                <div class="col-12">
                    <div class="w-100 text-center">
                        <h5 class="fw-semibold">${sessionScope.lang.getString('1')}</h5>
                        <p>${sessionScope.lang.getString('14')}</p>
                    </div>
                    <table class="w-100">
                        <thead>
                            <tr>
                                <th colspan="2" class="column-md">
                                    <div class="dropdown">
                                        <button type="button" data-bs-toggle="dropdown" aria-expanded="false" class="btn p-0 rounded-0 item fw-semibold dropdown-toggle" style="font-size: small;">${requestScope.selectedYear + 1900}</button>
                                        <div class="dropdown-menu" style="font-size: small;">
                                        <c:forEach items="${requestScope.years}" var="i">
                                            <button type="button" onclick="selectYear(${requestScope.years.indexOf(i)})" class="dropdown-item <c:if test="${i eq requestScope.selectedYear}">active</c:if>">${i + 1900}</button>
                                        </c:forEach>
                                        </div>
                                    </div>
                                </th>
                                <th class="column-sm">${sessionScope.lang.getString('7')}</th>
                                <th class="column-sm">${sessionScope.lang.getString('8')}</th>
                                <th class="column-sm">${sessionScope.lang.getString('9')}</th>
                                <th class="column-sm">${sessionScope.lang.getString('10')}</th>
                                <th class="column-sm">${sessionScope.lang.getString('11')}</th>
                                <th class="column-sm">${sessionScope.lang.getString('12')}</th>
                                <th class="column-sm">${sessionScope.lang.getString('13')}</th>
                            </tr>
                            <tr>
                                <th colspan="2" class="column-md">
                                    <div class="dropdown">
                                        <button type="button" data-bs-toggle="dropdown" aria-expanded="false" class="btn p-0 rounded-0 item fw-semibold dropdown-toggle" style="font-size: small;">${requestScope.selectedWeek}</button>
                                        <div class="dropdown-menu" style="font-size: small;">
                                        <c:forEach items="${requestScope.weeks}" var="i">
                                            <button type="button" onclick="selectWeek(${requestScope.weeks.indexOf(i)})" class="dropdown-item <c:if test="${i eq requestScope.selectedWeek}">active</c:if>">${i}</button>
                                        </c:forEach>
                                        </div>
                                    </div>
                                </th>
                                <%SimpleDateFormat df = new SimpleDateFormat("dd-MM");
                                    Week selectedWeek = (Week)request.getAttribute("selectedWeek");
                                for (Date date = new Date(selectedWeek.getStartDate().getTime()); !date.after(selectedWeek.getEndDate()); date.setTime(date.getTime() + 86400000)) {%>
                                    <th class="column-sm"><%=df.format(date)%></th>
                                <%}%>
                            </tr>
                        </thead>
                        <tbody>
                            <tr class="border-0">
                                <td rowspan="${requestScope.timeSlots.size() + 1}" style="transform: rotate(-90deg);">SLOT</td>
                            </tr>
                        <%HashMap<String, Attend> sessionMap = (HashMap)request.getAttribute("sessionMap");
                            ArrayList<TimeSlot> timeSlots = (ArrayList) request.getAttribute("timeSlots");
                            Account account = (Account) request.getSession().getAttribute("account");
                        for (TimeSlot i : timeSlots) {%>
                            <tr <%if (LocalTime.now().isAfter(i.getStartTime()) && LocalTime.now().isBefore(i.getEndTime())) {%>class="border-bottom"<%}%>>
                                <td>
                                    <%=i.getSlot()%><br/>
                                    <%=i%>
                                </td>
                            <%for (Date date = new Date(selectedWeek.getStartDate().getTime()); !date.after(selectedWeek.getEndDate()); date.setTime(date.getTime() + 86400000)) {
                            String key = (new SimpleDateFormat("yyyy-MM-dd").format(date)) + " " + i.getSlot();
                            Attend a = sessionMap.get(key);%>
                                <td <%if (new SimpleDateFormat("yyyy-MM-dd").format(date).equals(new SimpleDateFormat("yyyy-MM-dd").format(new Date()))) {%>class="border-left"<%}%>>
                                <%if ((account.getRole().getId() == 1 && a.getSession() != null) || (account.getRole().getId() == 2 && a.getSession().getSession() != null)) {
                                    Boolean present = null;
                                    try {
                                        Student t =  ((Student) account);
                                        present = a.isPresent();
                                        
                                    } catch (Exception e) {
                                        present = a.getSession().isTaked();
                                    }
                                    if (present == null) {
                                        if (new Date().after(new Date(a.getSession().getStartTime().getTime() + 86400000))) present = false;
                                    }%>
                                    <div class="dropdown dropend <%if  (present == null) {%>border-not-yet<%} else if (present) {%>border-present<%} else {%>border-absent<%}%> ps-1">
                                        <button type="button" data-bs-toggle="dropdown" aria-expanded="false" class="btn item p-0" style="font-size: small; font-weight: 500;"><%=a.getSession().getSession().getCourse().getId()%></button>
                                        <ul class="dropdown-menu" style="font-size: small;">
                                            <li><a href="<%=a.getSession().getSession().getCourse().getResourceURL()%>" target="_blank" class="dropdown-item">${sessionScope.lang.getString('18')}</a></li>
                                            <li><a href="<%=a.getSession().getEduNextURL()%>" target="_blank" class="dropdown-item">EduNext</a></li>
                                            <li><a href="<%=a.getSession().getMeetURL()%>" target="_blank" class="dropdown-item">Meet URL</a></li>
                                        <c:if test="${sessionScope.account.role.have('/attendance')}">
                                        <button onclick="openHyperLink('http://localhost:8080/Assignment/attendance?course=<%=a.getSession().getSession().getCourse().getId()%>&group=<%=a.getSession().getGroupId()%>&session=<%=a.getSession().getSession().getNo()%>')" class="dropdown-item" <%if(!new Date().after(a.getSession().getStartTime())){%>disabled<%}%>>Take attendance</button>
                                        </c:if>
                                            <hr class="dropdown-divider">
                                            <div class="ps-3 pe-3">
                                                <span>${sessionScope.lang.getString('19')}: <%=a.getSession().getGroupId()%></span><br/>
                                                <span>${sessionScope.lang.getString('20')}: <%=a.getSession().getSession().getNo()%></span><br/>
                                            </div>
                                            
                                        </ul>
                                    </div>
                                    <span>${sessionScope.lang.getString('15')} <%=a.getSession().getRoom()%></span>
                                <%}%>
                                </td>
                            <%}%>
                            </tr>
                        <%}%>
                        <th colspan="9">
                            <button type="button" onclick="selectWeek(${requestScope.weeks.indexOf(requestScope.selectedWeek) - 1})" class="btn p-0 item" style="font-size: small" <c:if test="${requestScope.weeks.indexOf(requestScope.selectedWeek) == 0}">disabled</c:if>>${sessionScope.lang.getString('16')}</button>
                            <button type="button" onclick="selectWeek(${requestScope.weeks.indexOf(requestScope.selectedWeek) + 1})" class="btn p-0 item float-end" style="font-size: small" <c:if test="${requestScope.weeks.indexOf(requestScope.selectedWeek) == 51}">disabled</c:if>>${sessionScope.lang.getString('17')}</button>
                        </th>
                        </tbody>
                    </table>
                        <form action="week" method="post" id="time-form">
                            <input type="hidden" placeholder="?" name="selected-week" value="${requestScope.weeks.indexOf(requestScope.selectedWeek)}" id="week-input"/> 
                            <input type="hidden" placeholder="?" name="selected-year" value="${requestScope.years.indexOf(requestScope.selectedYear)}" id="year-input"/> 
                        </form>
                </div>
            </div>
        </div>
    </div>
    <jsp:include page="../subtab.jsp"></jsp:include>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
    <script src="../assets/base.js"></script>
    <script>
        function selectWeek(e) {
            document.getElementById('week-input').value = e;
            submitForm();
        }
        
        function selectYear(e) {
            document.getElementById('week-input').value = 0;
            document.getElementById('year-input').value = e;
            submitForm();
        }
        
        function submitForm() {
            document.getElementById('time-form').submit();
        }
    </script>
</body>

</html>