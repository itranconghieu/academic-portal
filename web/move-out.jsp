<%--
    Document   : move-out
    Created on : Mar 8, 2024, 2:30:22 PM
    Author     : End User
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="entity.*" %>
<%@ page import="transact.*" %>
<%@ page import="java.util.HashMap" %>
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
    <title>Ternet | Move out class</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link href="assets/base.css" rel="stylesheet"/>
    <style>
        .column-sm {
            width: 15%;
        }
        
        .column-md {
            width: 20%;
        }
        
        .item:hover {
            border: unset;
            text-decoration: underline;
        }
        
        .item:focus {
            border: unset;
            text-decoration: underline;
        }
        
        input {
            border: unset;
            background-color: unset;
            font-size: small;
        }

        input:focus {
            outline: unset;
            box-shadow: unset;
            background-color: unset;
        }
        
        textarea {
            background-color: unset;
            border: unset;
            border-bottom: 1.5px solid #ffffff;
            min-height: 2rem;
        }
        
        textarea:focus {
            outline: unset;
        }
        
        .text-green {
            color: #43b581;
        }

        .text-red {
            color: #de2761;
        } 
    </style>
</head>

<body class="not-light">
    <jsp:include page="elements/navbar.jsp"></jsp:include>
    <div class="container-fluid fs-7">
        <div class="container p-5" >
            <div class="row g-4 d-flex justify-content-center">
                <div class="col-10">
                    <div class="w-100 text-center">
                        <h5 class="fw-semibold">MOVE OUT CLASS</h5>
                    </div>
                    <table class="w-100 text-start">
                        <thead>
                            <tr>
                                <th class="w-50">
                                    <div class="dropdown">
                                        <button type="button" data-bs-toggle="dropdown" aria-expanded="false" class="btn p-0 rounded-0 item fw-semibold border-0 dropdown-toggle" style="font-size: small;">Course: ${requestScope.selectedEnroll.course}</button>
                                        <div class="dropdown-menu">
                                    <c:forEach items="${requestScope.enrollList}" var="i">
                                        <button type="button" onclick="selectCourse(${requestScope.enrollList.indexOf(i)})" class="dropdown-item" style="font-size: small;">${i.course}</button>
                                    </c:forEach>   
                                        </div>
                                    </div>
                                </th>
                                <th colspan="2" class="w-25">
                                    <button type="button" class="btn p-0 rounded-0 item fw-semibold border-0" style="font-size: small;" disabled>From class: ${requestScope.selectedEnroll.groupId}</button>
                                </th>
                                <th colspan="2" class="w-25">
                                    <div class="dropdown">
                                        <button type="button" data-bs-toggle="dropdown" aria-expanded="false" class="btn p-0 rounded-0 item fw-semibold border-0 dropdown-toggle" style="font-size: small;">To class: ${requestScope.classAfter}</button>
                                        <div class="dropdown-menu">
                                        <c:forEach items="${requestScope.classList}" var="c">
                                            <button type="button" onclick="selectClass('${c}')" class="dropdown-item" style="font-size: small;" <c:if test="${c eq requestScope.selectedEnroll.groupId}">disabled</c:if>>${c}</button>
                                        </c:forEach>
                                        </div>
                                    </div>
                                </th>
                            </tr>
                            <tr>
                                <th>
                                </th>
                                <th style="width: 10%;">No</th>
                                <th style="width: 15%;">Date</th>
                                <th style="width: 12.49%;">Slot</th>
                                <th style="width: 12.49%;">Room</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td rowspan="${requestScope.sessionList.size() + 1}">
                                <c:if test="${requestScope.sessionList.size() > 0}">
                                    <div class="w-100">
                                    <form action="send-request" method="post" id="request-form">
                                        <div>
                                            <label>Student RollNumber:</label>
                                            <input type="text" value="${sessionScope.account.getCode()}" readonly/>
                                        </div>
                                        <div>
                                            <label>Student name:</label>
                                            <input type="text" value="${sessionScope.account}" readonly/>
                                        </div>
                                        <input type="hidden" name="enroll" value="${requestScope.selectedEnroll.id}"/>
                                        <input type="hidden" name="classAfter" value="${requestScope.classAfter}"/>
                                        <textarea name="reason" placeholder="@reason" value="reason" class="w-100">
                                        </textarea>
                                        <div class="d-flex justify-content-center mt-4">
                                            <input type="submit" class="white btn btn-light" style="font-size: small;" value="Send request">
                                        </div>
                                    </form>
                                    </div>
                                </c:if>
                                </td>
                            </tr>
                        <c:forEach items="${requestScope.sessionList}" var="i">
                            <tr>
                                <td>${i.session.no}</td>
                                <td>${i.startDateToString()}</td>
                                <td>${i.slotToString()}</td>
                                <td>${i.room}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <form action="move-out-class" method="post" id="class-form">
                        <input type="hidden" name="course" value="${requestScope.enrollList.indexOf(requestScope.selectedEnroll)}"" id="course-input"/>   
                        <input type="hidden" name="class" id="class-input"/>        
                    </form>
                    <table class="w-100">
                        <thead>
                            <tr>
                                <th class="column-sm">Course code</th>
                                <th class="column-sm">From class</th>
                                <th class="column-sm">To class</th>
                                <th class="column-md">Purpose</th>
                                <th class="column-sm">Status</th>
                                <th class="column-md">Response</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${requestScope.requestList}" var="r">
                                <tr>
                                    <td>${r.courseEnroll.course.id}</td>
                                    <td>${r.courseEnroll.groupId}</td>
                                    <td>${r.toClass}</td>
                                    <td><input value="${r.purpose}" readonly></td>
                                    <td class="fw-semibold <c:if test="${r.state eq null}"> text-green">Processing</c:if>
                                    <c:if test="${r.state}"> text-green">Approved</c:if>
                                    <c:if test="${!(r.state eq null) && !r.state}"> text-red">Rejected</c:if></td>
                                    <td>${r.response}</td>
                                </tr>
                            </c:forEach>
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
        function selectCourse(p) {
            var courseInput = document.getElementById("course-input");
            courseInput.value = p;
            submitForm();
        }
        
        function selectClass(p) {
            var classInput = document.getElementById("class-input");
            classInput.value = p;
            submitForm();
        }
        
        function submitForm() {
            document.getElementById('class-form').submit();
        }
        
        function sendRequest() {
            document.getElementById('request-form').submit();
        }
    </script>
</body>

</html>