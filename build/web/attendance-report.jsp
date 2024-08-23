<%--
    Document   : report
    Created on : Mar 6, 2024, 1:09:39 PM
    Author     : End User
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="entity.*" %>
<%@ page import="transact.*" %>
<%@ page import="java.helper.Week" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>
<!doctype html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Ternet | Attendance</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link href="assets/base.css" rel="stylesheet"/>
    <style>
        .column-sm {
            width: 5%;
        }
        
        .column-md {
            width: 10%;
        }
        
        .column-lg {
            width: 15%;
        }
        
        .comment {
            border: unset;
            background-color: unset;
            font-size: small;
        }
        
        .comment:focus {
            box-shadow: unset;
            background-color: unset;
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
    </style>
</head>

<body class="not-light">
    <jsp:include page="elements/navbar.jsp"></jsp:include>
    <div class="container-fluid fs-7">
        <div class="container p-5" >
            <div class="row g-4">
                <div class="col-12">
                    <div class="w-100 text-center">
                        <h5 class="fw-semibold">${sessionScope.lang.getString('4')}</h5>
                        <div role="group" class="btn-group">
                            <div role="group" class="btn-group">
                                <button type="button" data-bs-toggle="dropdown" aria-expanded="false" class="white btn dropdown-toggle" style="font-size: small;">${requestScope.selectedSemester}</button>
                                <div class="dropdown-menu" style="font-size: small;">
                                <c:forEach items="${requestScope.semesterList}" var="s">
                                    <button type="button" onclick="selectSemester(${requestScope.semesterList.indexOf(s)})" class="dropdown-item" style="font-size: small;">${s}</button>
                                </c:forEach>
                                </div>
                            </div>
                            <div role="group" class="btn-group">
                                <button type="button" data-bs-toggle="dropdown" aria-expanded="false" class="white btn dropdown-toggle" style="font-size: small;">${requestScope.selectedEnroll.course}</button>
                                <div class="dropdown-menu">
                                <c:forEach items="${requestScope.enrollList}" var="cr">
                                    <button type="button" onclick="selectCourse(${requestScope.enrollList.indexOf(cr)})" class="dropdown-item" style="font-size: small;">${cr.course}</button>
                                </c:forEach>
                                </div>
                            </div>
                        </div>
                        <form action="attendance-reports" method="post" id="course-form">
                            <input type="hidden" name="semester" value="${requestScope.semesterList.indexOf(requestScope.selectedSemester)}" placeholder="?" id="semester-input">
                            <input type="hidden" name="course" value="${requestScope.enrollList.indexOf(requestScope.selectedEnroll)}" placeholder="?" id="course-input">
                        </form>
                    </div>
                </div>
                <div class="col-12">
                    <table class="w-100">
                        <thead>
                            <tr>
                                <th class="column-sm">No</th>
                                <th class="column-lg">${sessionScope.lang.getString('30')}</th>
                                <th class="column-md">${sessionScope.lang.getString('31')}</th>
                                <th class="column-md">${sessionScope.lang.getString('32')}</th>
                                <th class="column-lg">${sessionScope.lang.getString('33')}</th>
                                <th class="column-md">${sessionScope.lang.getString('34')}</th>
                                <th class="column-md">${sessionScope.lang.getString('35')}</th>
                                <th class="column-md">${sessionScope.lang.getString('36')}</th>
                                <th class="column-lg">${sessionScope.lang.getString('37')}</th>
                            </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${requestScope.attendList}" var="a"> 
                            <tr>
                                <td>${a.session.session.no}</td>
                                <td>
                                    <div class="col-11 text-truncate">
                                        <span>${a.session.session.description}</span>
                                    </div>
                                </td>
                                <td>${a.session.groupId}</td>
                                <td>${a.session.lecturer.id}</td>
                                <td>${a.session.startDateToString()}</td>
                                <td>${a.session.slotToString()}</td>
                                <td>${a.session.room}</td>
                                <td<c:if test="${(((a.isPresent() eq null) and requestScope.now.getTime() >= (a.getSession().getStartTime().getTime() + 86400000)) or (!(a.isPresent() eq null) and !(a.isPresent())))}"> class="text-absent">Absent</c:if><c:if test="${a.isPresent() eq null and requestScope.now.getTime() <= (a.getSession().getStartTime().getTime() + 86400000)}"> class="text-not-yet">Not yet</c:if><c:if test="${!(a.isPresent() eq null) and (a.isPresent())}"> class="text-present">Present</c:if></td>
                                <td>
                                    <input type="text" name="comment" placeholder="..."  value="${a.comment}" class="w-100 form-control comment" readonly/>
                                </td> 
                            </tr>
                        </c:forEach>    
                            <tr>
                                <th colspan="7">Absent</th>
                                <th colspan="2">${requestScope.absentSession}%</th>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <jsp:include page="subtab.jsp"></jsp:include>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
    <script src="assets/base.js"></script>
    <script>
        function selectSemester(p) {
            document.getElementById('semester-input').value = p;
            submitForm();
        }
        
        function selectCourse(p) {
            document.getElementById('course-input').value = p;
            submitForm();
        }
        
        function submitForm() {
            document.getElementById('course-form').submit();
        }
    </script>
</body>

</html>