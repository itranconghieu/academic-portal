<%--
    Document   : view
    Created on : Mar 6, 2024, 1:38:32 AM
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
    <jsp:include page="../elements/navbar.jsp"></jsp:include>
    <div class="container-fluid fs-7">
        <div class="container p-5" >
            <div class="row g-4">
                <div class="col-12">
                    <div class="w-100 text-center">
                        <h5 class="fw-semibold">ATTENDANCE</h5>
                        <p>For ${requestScope.session}</p>
                    </div>
                </div>
                <div class="col-12">
                    <table class="w-100">
                        <thead>
                            <tr>
                                <th class="column-sm">No</th>
                                <th class="column-md">Image</th>
                                <th class="column-md">Code</th>
                                <th class="column-md">Surname</th>
                                <th class="column-md">Middle name</th>
                                <th class="column-md">Given Name</th>
                                <th class="column-md">Attendance</th>
                                <th class="column-lg">Comment</th>
                                <th class="column-md">Modified at</th>
                                <th class="column-md">Modified by</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%ArrayList<Attend> attendList = (ArrayList) request.getAttribute("attendList");
                            for (int i = 0; i < attendList.size(); i++) {
                            Attend a = attendList.get(i);%>
                            <tr>
                                <td><%=i%>.</td>
                                <td>
                                    <img src="<%=a.getStudent().getImageURL()%>" alt="?" class="w-100"/>
                                </td>
                                <td><%=a.getStudent().getCode()%></td>
                                <td><%=a.getStudent().getSurname()%></td>
                                <td><%=a.getStudent().getMiddleName()%></td>
                                <td><%=a.getStudent().getGivenName()%></td>
                                <td<%if(a.isPresent() == null || !a.isPresent()){%> class="text-absent">Absent<%}else{%> class="text-present">Present<%}%></td>
                                <td>
                                    <input type="text" name="comment" placeholder="..."  value="<%if (a.getComment() != null){%><%=a.getComment()%><%}%>" class="w-100 form-control comment" readonly/>
                                </td>
                                <td><%=a.mofifiedTimeToString()%></td>
                                <td><%if (a.getModifier().getId() != null){%><%=a.getModifier().getId()%><%}%></td>
                        </tr>
                        <%}%>
                        </tbody>
                    </table>
                    <div class="d-flex justify-content-center mt-4">
                        <button type="button" onclick="openHyperLink('<%=request.getContextPath()%>/attendance?course=${requestScope.session.session.course.id}&group=${requestScope.session.groupId}&session=${requestScope.session.session.no}&edit=true')" class="white btn btn-light" style="font-size: small;" <c:if test="${!(requestScope.session.lecturer.id eq sessionScope.account.id) ||requestScope.timeOut}">disabled</c:if>>Edit record</button>
                    </div>
                    <c:if test="${!(requestScope.session.lecturer.id eq sessionScope.account.id)}"><p class="text-center mt-2" style="font-size: small;">No permission for taking attendance</p></c:if>   
                    <c:if test="${requestScope.timeOut}"><p class="text-center mt-2" style="font-size: small;">Please note that this record can NOT modified after 24 hours</p></c:if>   
                </div>
            </div>
        </div>
    </div>
    <jsp:include page="../subtab.jsp"></jsp:include>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
    <script src="assets/base.js"></script>
</body>

</html>