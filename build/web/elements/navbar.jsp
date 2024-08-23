<%--
    Document   : navbar
    Created on : Mar 4, 2024, 6:52:29 PM
    Author     : End User
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="entity.Student" %>
<%@ page import="entity.Account" %>
<%@ page import="handler.CookieHandler" %>
    <div class="navbar navbar-expand-lg bg-body-tertiary" style="background-color: #ffffff !important">
        <div class="container-fluid p-2">
            <div class="d-flex justify-content-start">
                <div class="dropdown">
                    <form onsubmit="search(); return false;">
                        <div class="input-group">
                            <div class="white input-group-text">
                                <i class="fi fi-rs-search"></i>
                            </div>
                            <input type="text" placeholder="${sessionScope.lang.getString('38')}" name="pattern" class="form-control" id="search-input" <c:if test="${!(sessionScope.account.role.have('/search'))}">disabled</c:if> onfocus="openDropdown()"/>
                        </div>
                    </form>
                    <div class="w-100 dropdown-menu mt-2" style="font-size: small;" id="dropdown-menu">
                        <div class="position-absolute end-0 top-0 p-2">
                            <button type="button" onclick="closeDropdown()" class="btn btn-sm float-end"><i class="fi fi-rs-cross"></i></button>
                        </div>
                        <div class="ps-3 pe-3">
                            <span class="fw-semibold fs-6">RECENTLY</span>
                        </div>
                        <hr class="dropdown-divider"/>
                        <div id="response">
                            <div class="text-center fw-semibold">No result</div>
                        </div>
                    </div>
                </div>
                <div class="dropdown ms-2">
                    <button type="button" data-bs-toggle="dropdown" aria-expanded="false" class="white btn fw-semibold me-2 dropdown-toggle">${sessionScope.lang.getString('1')}</button>
                    <div class="dropdown-menu mt-2">
                        <button type="button" onclick="openHyperLink('<%=request.getContextPath()%>/timetable/week')" class="btn dropdown-item">${sessionScope.lang.getString('40')}</button>
                        <button type="button" onclick="openHyperLink('<%=request.getContextPath()%>/timetable/year')" class="btn dropdown-item">${sessionScope.lang.getString('41')}</button>
                    </div>
                </div>
                <% try {
                Account account = (Student) request.getSession().getAttribute("account");%>
                <button type="button" onclick="openHyperLink('<%=request.getContextPath()%>/transcript')" class="white btn fw-semibold me-2">${sessionScope.lang.getString('2')}</button>
                <div class="dropdown">
                    <button type="button" data-bs-toggle="dropdown" aria-expanded="false" class="white btn btn-light font-prompt fw-semibold dropdown-toggle">${sessionScope.lang.getString('3')}</button>
                    <div class="dropdown-menu mt-2">
                        <button type="button" onclick="openHyperLink('<%=request.getContextPath()%>/attendance-reports')" class="dropdown-item">${sessionScope.lang.getString('4')}</button>
                        <button type="button" onclick="openHyperLink('<%=request.getContextPath()%>/move-out-class')" class="dropdown-item">${sessionScope.lang.getString('39')}</button>
                    </div>
                </div>
                <%} catch (Exception e) {}%>
            </div>
            <div class="d-flex justify-content-end">
                <div class="dropdown d-inline-block">
                    <button type="button" data-bs-toggle="dropdown" aria-expanded="false" class="white btn me-2"><i class="fi fi-rs-globe"></i></button>
                    <div class="dropdown-menu mt-2 dropdown-menu-end">
                        <button type="button" onclick="selectLocale('en')" class="dropdown-item">
                            <img src="<%=request.getContextPath()%>/assets/images/flags/united-states.png" alt="" style="width: 1.2rem;"/>
                            English
                        </button>
                        <button type="button" onclick="selectLocale('vi')" class="dropdown-item">
                            <img src="<%=request.getContextPath()%>/assets/images/flags/vietnam.png" alt="" style="width: 1.2rem;"/>
                            Tiếng Việt
                        </button>
                    </div>
                    <form action="<%=request.getContextPath()%>/language" method="post" id="lang-form">
                        <input type="hidden" name="locale" id="locale-input"/>
                        <input type="hidden" name="redirectURL" id="url-input"/>
                    </form>
                </div>
                <button type="button" onclick="openSubtab();" class="btn me-2"><i class="fi fi-rs-comment-alt-middle"></i></button>
                <div class="dropdown d-inline-block">
                    <button type="button" data-bs-toggle="dropdown" aria-expanded="false" class="btn btn-light fw-semibold me-2 text-start" style="min-width: 8rem;">${sessionScope.account.id}</button>
                    <div class="dropdown-menu dropdown-menu-end mt-2">
                        <button type="button" onclick="openHyperLink('<%=request.getContextPath()%>/account')" class="dropdown-item">${sessionScope.lang.getString('5')}</button>
                        <button type="button" onclick="openHyperLink('<%=request.getContextPath()%>/logout')" class="dropdown-item">${sessionScope.lang.getString('6')}</button>
                    </div>
                </div>
            </div>
            <script>
                window.onload = function() {
                    document.getElementById('url-input').value = window.location.href;
                };
                
                function openDropdown() {
                    document.getElementById('dropdown-menu').classList.add('d-block');
                }
                
                function closeDropdown() {
                    document.getElementById('dropdown-menu').classList.remove('d-block');
                }
                
                function search() {
                    var httpRequest = new XMLHttpRequest();
                    httpRequest.onreadystatechange = function() {
                        var response = document.getElementById('response');
                        if (this.readyState === 4 && this.status === 200) {
                            response.innerHTML = this.responseText;
                        }
                    };
                    var searchInput = document.getElementById('search-input');
                    httpRequest.open('get', '<%=request.getContextPath()%>/search?pattern=' + searchInput.value, true);
                    httpRequest.send();
                }
                
                function selectLocale(p) {
                    var langForm = document.getElementById('lang-form');
                    var localeInput = document.getElementById('locale-input');
                    localeInput.value = p;
                    langForm.submit();
                }
            </script>
        </div>
    </div>
