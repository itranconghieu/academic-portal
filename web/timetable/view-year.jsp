<%--
    Document   : view-week
    Created on : Mar 4, 2024, 7:33:44 PM
    Author     : End User
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="entity.*" %>
<%@ page import="transact.*" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Calendar" %>
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
            width: 14.28%
        }
        table td {
            text-align: center;
            border-bottom: unset;
            height: 2.5rem;
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

        .border-top {
            border-top: 1.5px solid #2c2f33;
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
                        <div class="dropdown">
                            <button type="button" data-bs-toggle="dropdown" aria-expanded="false" class="white btn dropdown-toggle" style="font-size: small;">${requestScope.selectedYear + 1900}</button>
                            <div class="dropdown-menu" style="font-size: small;">
                                <c:forEach items="${requestScope.years}" var="i">
                                    <button type="button" onclick="selectYear(${requestScope.years.indexOf(i)})" class="dropdown-item <c:if test="${i eq requestScope.selectedYear}">active</c:if>">${i + 1900}</button>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>
            <%  Account account = (Account) request.getSession().getAttribute("account");
                HashMap<String, ArrayList<Attend>> sessionMap = (HashMap)request.getAttribute("sessionMap");
                Calendar calendar = (Calendar) request.getAttribute("calendar");
                for (int month = 0; month < 12; month++) {
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, 1);
                    int numDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                    int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);%>
                <div class="col-4">
                    <p class="fs-5 fw-semibold"><%=new SimpleDateFormat("MMM").format(calendar.getTime())%></p>
                    <table class="w-100">
                        <tbody>
                            <tr style="border-bottom: 1.5px solid #ffffff;">
                                <td class="column-sm">${sessionScope.lang.getString('42')}</td>
                                <td class="column-sm">${sessionScope.lang.getString('43')}</td>
                                <td class="column-sm">${sessionScope.lang.getString('44')}</td>
                                <td class="column-sm">${sessionScope.lang.getString('45')}</td>
                                <td class="column-sm">${sessionScope.lang.getString('46')}</td>
                                <td class="column-sm">${sessionScope.lang.getString('47')}</td>
                                <td class="column-sm">${sessionScope.lang.getString('48')}</td>
                            </tr>
                            <tr>
                            <%for (int d = 1; d < dayOfWeek; d++) {%>
                                <td></td>
                            <%}%>
                            <%for (int d = 1; d <= numDays; d++) {
                            calendar.set(Calendar.DAY_OF_MONTH, d);
                            String key = (new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));
                            ArrayList<Attend> as = sessionMap.get(key);
                            if (as.size() > 0) {%>
                                <td class="position-relative">
                                    <div class="dropdown dropend">
                                        <button type="button" data-bs-toggle="dropdown" aria-expanded="false" data-bs-auto-close="outside" class="white w-50 btn p-0 btn-sm" style="font-size: small;"><%=d%></button>
                                        <div class="dropdown-menu">
                                        <%for (Attend a : as) {
                                        String cid = a.getSession().getSession().getCourse().getId() + "-" + a.getSession().getSession().getNo();
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
                                        <div class="dropdown-item" style="font-size: small;">
                                            <div class="d-inline-block <%if  (present == null) {%>border-not-yet<%} else if (present) {%>border-present<%} else {%>border-absent<%}%> ps-1">
                                                <button type="button" data-bs-toggle="collapse" data-bs-target="#<%=cid%>" aria-expanded="false" aria-controls="<%=cid%>" class="btn p-0 rounded-0 item fw-semibold" style="font-size: small;"><%=a.getSession().getSession().getCourse().getId()%></button>
                                            </div>
                                            <span>${sessionScope.lang.getString('15')} <%=a.getSession().getRoom()%></span><br/>
                                            <span>on <%=a.getSession().getSlot()%></span>
                                        </div>
                                        <div class="collapse" style="font-size: small;" id="<%=cid%>">
                                            <a href="<%=a.getSession().getSession().getCourse().getResourceURL()%>" target="_blank" class="dropdown-item">${sessionScope.lang.getString('18')}</a>
                                            <a href="<%=a.getSession().getEduNextURL()%>" target="_blank" class="dropdown-item">EduNext</a>
                                            <a href="<%=a.getSession().getMeetURL()%>" target="_blank" class="dropdown-item">Meet URL</a>
                                        <c:if test="${sessionScope.account.role.have('/attendance')}">
                                            <button onclick="openHyperLink('http://localhost:8080/Assignment/attendance?course=<%=a.getSession().getSession().getCourse().getId()%>&group=<%=a.getSession().getGroupId()%>&session=<%=a.getSession().getSession().getNo()%>')" class="dropdown-item" <%if(!new Date().after(a.getSession().getStartTime())){%>disabled<%}%>>Take attendance</button>
                                        </c:if>
                                        </div>
   
                                        <%}%>
                                        </div>
                                    </div>
                                    <div class="w-100 position-absolute end-0 bottom-0" style="margin-bottom: -0.4rem;">  
                                    <%for (Attend a: as) {
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
                                        <i class="fi fi-rs-minus-small p-0 <%if  (present == null) {%>text-not-yet<%} else if (present) {%>text-present<%} else {%>text-absent<%}%>"></i>
                                    <%}%>
                                    </div>
                                    </td>
                                <%} else {%>
                                    <td><span><%=d%></span></td>
                                <%}%>
                                <%if ((d + dayOfWeek - 1) % 7 == 0 && d < numDays) {%>
                            </tr>
                                <%}%>
                            <%}%>
                        </tbody>
                    </table>
                    <form action="year" method="post" id="time-form">
                        <input type="hidden" placeholder="?" name="selected-year" value="${requestScope.years.indexOf(requestScope.selectedYear)}" id="year-input"/> 
                    </form>
                </div>
            <%}%>
            </div>
        </div>
    </div>
    <jsp:include page="../subtab.jsp"></jsp:include>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
    <script src="../assets/base.js"></script>
    <script>
        function selectYear(e) {
            document.getElementById('year-input').value = e;
            submitForm();
        }
        
        function submitForm() {
            document.getElementById('time-form').submit();
        }
    </script>
</body>

</html>