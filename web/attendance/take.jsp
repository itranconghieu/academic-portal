<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%-- 
    Document   : take
    Created on : Mar 6, 2024, 1:38:28 AM
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
                width: 25%;
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
        <jsp:include page="../elements/navbar.jsp"></jsp:include>
        <c:if test="${requestScope.timeOut}">
            <c:redirect url="timetable/week"></c:redirect>
        </c:if>
            <div class="container-fluid fs-7">
                <div class="container p-5" >
                    <div class="row g-4">
                        <div class="col-12">
                            <div class="w-100 text-center">
                                <h5 class="fw-semibold">ATTENDANCE</h5>
                                <p>For ${requestScope.session}</p>
                        </div>
                        <div class="col-12">
                            <form action="attendance" method="post">
                                <table class="w-100">
                                    <thead>
                                        <tr>
                                            <th class="column-md">
                                                <div class="form-check m-0" style="padding-top: 0.1rem; padding-left: 1.6rem;">
                                                    <input type="checkbox" onchange="toggleAll()" class="form-check-input" id="trigger"/>
                                                    <label for="toggle-all"  class="form-check-label">Present</label>
                                                </div>
                                            </th>
                                            <th class="column-sm">No</th>
                                            <th class="column-md" id="image-column">Image</th>
                                            <th class="column-md">Code</th>
                                            <th class="column-md">Surname</th>
                                            <th class="column-md">Middle name</th>
                                            <th class="column-md">Given Name</th>
                                            <th class="column-lg">Comment</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <%ArrayList<Attend> attendList = (ArrayList) request.getAttribute("attendList");
                                        for (int i = 0; i < attendList.size(); i++) {
                                        Attend a = attendList.get(i);%>
                                        <tr>
                                            <td>
                                                <div class="form-check" style="padding-top: 0.1rem; padding-left: 1.6rem;">
                                                    <input type="checkbox" onchange="changeState(<%=i%>)" name="checkbox" value="true" class="form-check-input" <%if(a.isPresent() == null){ }else if(a.isPresent()) {%>checked<%}%>/>
                                                    <input type="hidden" name="attendStatus" value="<%if(a.isPresent() == null){%>false<%}else{%><%=a.isPresent()%><%}%>"/>
                                                </div>
                                            </td>
                                            <td><%=i%>.</td>
                                            <td class="image">
                                                <img src="<%=a.getStudent().getImageURL()%>" alt="?" class="w-100"/>
                                            </td>
                                            <td><%=a.getStudent().getCode()%></td>
                                            <td><%=a.getStudent().getSurname()%></td>
                                            <td><%=a.getStudent().getMiddleName()%></td>
                                            <td><%=a.getStudent().getGivenName()%></td>
                                            <td>
                                                <input type="text" name="comment" placeholder="..."  value="<%if (a.getComment() != null){%><%=a.getComment()%><%}%>" class="w-100 form-control comment"/>
                                            </td>

                                        </tr>
                                        <%}%>
                                    </tbody>
                                </table>
                                <div class="d-flex justify-content-center mt-4">
                                    <input type="submit" value="Save changes" class="white btn btn-light" style="font-size: small;"/>
                                </div>
                            </form>     
                        </div>

                    </div>
                </div>
            </div>
            <jsp:include page="../subtab.jsp"></jsp:include>
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
                    integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
            crossorigin="anonymous"></script>
            <script src="assets/base.js"></script>
            <script>
                                                        var trigger = document.getElementById('trigger');
                                                        var checkboxs = document.getElementsByName('checkbox');
                                                        var attendStatus = document.getElementsByName('attendStatus');
                                                        function toggleAll() {
                                                            for (var i = 0; i < checkboxs.length; i++) {
                                                                checkboxs[i].checked = trigger.checked;
                                                                if (trigger.checked) {
                                                                    attendStatus[i].value = true;
                                                                } else {
                                                                    attendStatus[i].value = false;
                                                                }
                                                            }
                                                        }


                                                        function changeState(i) {
                                                            let current = attendStatus[i].value;
                                                            if (current == 'false') {
                                                                attendStatus[i].value = true;
                                                            } else {
                                                                attendStatus[i].value = !current;
                                                            }
                                                            allIsCheked();
                                                        }

                                                        function allIsCheked() {
                                                            var checked = true;
                                                            for (var i = 0; i < attendStatus.length; i++) {
                                                                let current = attendStatus[i].value;
                                                                if (current == 'false') {
                                                                    checked = false;

                                                                    break;
                                                                }
                                                            }
                                                            if (checked == true)
                                                                trigger.checked = true;
                                                            else
                                                                trigger.checked = false;
                                                        }
                                                        allIsCheked();

                                                        var images = document.getElementsByClassName("image");
                                                        function hideImage() {
                                                            for (var i = 0; i < images.length; i++) {
                                                                images[i].classList.add("d-none");
                                                            }
                                                        }
            </script>
    </body>

</html>