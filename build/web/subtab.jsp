<%--
    Document   : subtab
    Created on : Mar 4, 2024, 9:29:39 PM
    Author     : End User
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="position-fixed end-0 bottom-0 me-5 d-none" id="subtab">
    <div class="white rounded-top-4 border p-2" style="width: 37rem;">
        <div class="row">
            <div class="col-7 border-end" style="max-height: 16rem; font-size: small;">
                <div class="w-100">
                    <c:if test="${sessionScope.chats == null}">
                        <p>Empty</p>
                    </c:if>
                    <c:if test="${sessionScope.chats != null}">
                        <iframe class="w-100 overflow-y-hidden" style="height: 15rem;" id="iframe"></iframe>
                    </c:if>
                </div>
            </div>
            <div class="col-5">
                <div class="white p-1">
                   <button type="button" onclick="closeSubtab()" class="btn position-absolute end-0 top-0 mt-2 me-2" style="font-size: small;"><i class="fi fi-rs-cross"></i></button>
                    <p>${sessionScope.lang.getString('21')}</p> 
                </div>
                <div class="overflow-y-auto" style="max-height: 16rem;" id="chats">
                    <c:forEach items="${sessionScope.chats}" var="chat">
                        <button type="button" onclick="selectChat(${chat.id}, '${chat.course.id}');" class="white w-100 btn btn-light text-start mb-2" style="font-size: small;"id="${chat.id}">
                            <span class="fw-semibold">${chat.course.id}</span><br/>
                            <span>${sessionScope.lang.getString('19')}: ${chat.groupId}</span>
                        </button> 
                    </c:forEach>     
                </div>
            </div>
        </div>
    </div>
    </div>
    <script>
        var chats = document.getElementById('chats').getElementsByTagName('button');
        function selectChat(p1, p2) {
            for (var i = 0; i < chats.length; i++) {
                chats[i].classList.add('white');
            }
            var chatButton = document.getElementById(p1);
            chatButton.classList.remove('white');
            var iframe = document.getElementById('iframe');
            iframe.src = '<%=request.getContextPath()%>chat?id=' + p1 +'&name=' + p2;
            scroll();
        }
        
        function openSubtab() {
            var subtab = document.getElementById('subtab');
            if (subtab.classList.contains('d-none')) {
                subtab.classList.remove('d-none');
            } else {
                subtab.classList.add('d-none');
            }
        }
        
        function closeSubtab() {
            var subtab = document.getElementById('subtab');
            subtab.classList.add('d-none');
        }
    </script>