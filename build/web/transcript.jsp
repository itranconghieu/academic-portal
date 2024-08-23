<%--
    Document   : transcript
    Created on : Mar 6, 2024, 6:21:46 PM
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
            width: 20%;
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
        
        .comment {
            border: unset;
            background-color: unset;
            font-size: small;
        }
        
        .comment:focus {
            box-shadow: unset;
            background-color: unset;
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
                        <h5 class="fw-semibold">${sessionScope.lang.getString('2')}</h5>
                        <p>${sessionScope.lang.getString('29')}</p>
                    </div>
                </div>
            <c:if test="${param.course eq null}">
                <div class="col-12">
                    <table class="w-100">
                        <thead>
                            <tr>
                                <th class="column-sm">TermNo</th>
                                <th class="column-md">${sessionScope.lang.getString('22')}</th>
                                <th class="column-md">${sessionScope.lang.getString('23')}</th>
                                <th class="column-lg">${sessionScope.lang.getString('24')}</th>
                                <th class="column-lg">${sessionScope.lang.getString('25')}</th>
                                <th class="column-sm">${sessionScope.lang.getString('26')}</th>
                                <th class="column-md">${sessionScope.lang.getString('27')}</th>
                                <th class="column-md">${sessionScope.lang.getString('28')}</th>
                            </tr>
                        </thead>
                        <tbody> 
                        <c:forEach items="${requestScope.curriculum.courseList}" var="c">
                        <tr>
                            <td>${requestScope.curriculum.courseMap.get(c.id)}</td>
                            <td>${requestScope.courseMap.get(c.id).semester}</td>
                            <td>${c.id}</td>
                            <td>${c.prerequisite}</td>
                            <td>${c.name}</td>
                            <td>${c.credit}</td>
                        <c:if test="${requestScope.courseMap.get(c.id).state eq null}">
                            <td></td>
                            <td></td>
                        </c:if>
                        <c:if test="${!(requestScope.courseMap.get(c.id).state eq null)}">
                            <td><button type="button" onclick="openHyperLink('?student=${requestScope.student.id}&semester=${requestScope.courseMap.get(c.id).semester.id}&course=${c.id}')" class="btn item p-0" style="font-size: small;"><i class="fi fi-rs-arrow-up-right" style="font-size: x-small;"></i> <c:if test="${!(requestScope.courseMap.get(c.id).average eq null)}">${requestScope.courseMap.get(c.id).average}</c:if></button></td>
                            <td>${requestScope.courseMap.get(c.id).state}</td>
                        </c:if>
                        </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:if>
            <c:if test="${!(param.course eq null)}">
                <div class="col-12">
                    <button type="button" onclick="openHyperLink('transcript')" class="btn item p-0 fw-semibold" style="font-size: small;">Transcript</button>
                    <button type="button" class="btn item p-0 disable fw-semibold" style="font-size: small;" disabled>/ ${param.course}</button>
                </div>
                <div class="col-6">
                    <table class="w-100">
                        <thead>
                        <c:forEach items="${requestScope.courseEnrollList}" var="cr">
                            <tr>
                                <th><button type="button" onclick="openHyperLink('?student=${requestScope.student.id}&semester=${param.semester}&course=${cr.course.id}')" class="btn item p-0 fw-semibold" style="font-size: small;">${cr.course}</button></th>
                            </tr>
                        </c:forEach> 
                        </thead>
                    </table>
                </div>
                <div class="col-6">
                    <table class="w-100">
                        <thead>
                            <th class="column-lg">Grade Category</th>
                            <th class="column-lg">Grade Item</th>
                            <th class="column-md">Weight</th>
                            <th class="column-md">Value</th>
                            <th class="column-lg">Comment</th>
                        </thead>
                        <tbody>
                        <% HashMap<String, ArrayList<GradeItem>> itemMap = (HashMap) request.getAttribute("itemMap");
                        HashMap<String, Grade> gradeMap = (HashMap) request.getAttribute("gradeMap");
                        for (String category : itemMap.keySet()) {
                            ArrayList<GradeItem> items = itemMap.get(category);
                            int totalWeight = 0;
                            float totalValue = 0;
                            boolean completed = true;%>
                            <tr>
                                <td rowspan="<%=items.size() + 2%>"><%=category%></td>
                            </tr>
                            <%for (GradeItem item : items) {
                            Grade grade = gradeMap.get(request.getParameter("course") + "/" + item.getId());
                            totalWeight += item.getWeight();
                            totalValue += grade.getValue();%>
                            <tr>
                               <td><%=item.getName()%></td>
                               <td><%=item.getWeight()%></td>
                            <%if (grade.getExam() != null) {%>
                               <td><%=grade.getValue()%></td>
                               <td>
                                <%if (grade.getNote() != null) {%>
                                   <input type="text" name="comment" placeholder="..."  value="<%=grade.getNote()%>" class="w-100 form-control comment p-0" readonly/>
                                <%}%>
                               </td>
                            <%} else {
                                completed = false;
                            }%>
                            </tr>
                            <%}%>
                            <tr>
                                <td>Total</td>
                                <td><%=totalWeight%></td>
                                <%if (completed) {%>
                                <td><%=totalValue/items.size()%></td>
                                <%}%>
                                <td></td>
                            </tr>
                        <%}%>
                            <tr>
                                <th colspan="1">State</th>
                                <th colspan="1">${requestScope.courseEnroll.state}</th>
                                <th colspan="2">Average</th>
                                <th colspan="1"><c:if test="${!(requestScope.courseEnroll.average eq null)}">${requestScope.courseEnroll.average}</c:if></th>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </c:if>
            </div>
        </div>
    </div>
    <jsp:include page="subtab.jsp"></jsp:include>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
    <script src="assets/base.js"></script>
</body>

</html>