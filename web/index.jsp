<%--
    Document   : sign-in
    Created on : Mar 4, 2024, 4:22:31 PM
    Author     : End User
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Ternet | Sign in</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link href="assets/base.css" rel="stylesheet"/>
</head>
<c:if test="${!(sessionScope.account eq null)}">
    <c:redirect url="http://localhost:8000/PRJ301_war_exploded/timetable/week"/>
</c:if>
<body style="background-image: url(''); background-position: center; background-repeat: no-repeat; background-size: cover">
    <div class="container">
        <div class="row d-flex justify-content-end align-items-center" style="height: 100vh;">
            <div class="col-5">
                <div class="card" style="min-height: 60vh;">
                    <div class="card-header">
                        <h6 class="fw-6 fw-semibold">SIGN IN</h6>
                    </div>
                    <div class="card-body position-relative">
                        <div class="position-absolute start-0 bottom-0 p-2">
                            <a href="" target="_blank" class="white btn">@Disclaimer</a>
                        </div>
                        <form action="sign-in" method="post">
                            <div class="form-floating">
                                <input type="text" placeholder="?" onchange="unlockSubmitButton()" name="username" class="form-control mb-3" id="username-input" required/>
                                <label for="username">Username</label>
                            </div>
                            <div class="form-floating">
                                <input type="password" placeholder="?" onchange="unlockSubmitButton()" name="password" class="form-control mb-4" id="password-input" required/>
                                <label for="paswword">Password</label>
                            </div>
                            <button type="button" onclick="openHyperLink('<%=request.getContextPath()%>/forget')" class="btn float-end p-0 mb-2">Forget password</button>
                            <input type="submit" value="Login" class="w-100 btn btn-light p-3 mb-3" id="submit-button"/>
                            <c:if test="${requestScope.loginFailed}"><p class="text-center">Invalid username or password</p></c:if>
                            <c:if test="${requestScope.noMatch}"><p class="text-center">The google account is not connected with any our account</p></c:if>
                        </form>
                        <div class="d-flex justify-content-center">
                            <div id="g_id_onload" data-client_id="444175066040-vfraie2ohc68jei5v58b1il2kppqm0o4.apps.googleusercontent.com"
                                data-context="signin" data-ux_mode="redirect" data-login_uri="<%=request.getContextPath()%>/google-authentication"
                                data-auto_prompt="false">
                            </div>
                            <div class="g_id_signin" data-type="standard" data-shape="pill" data-theme="outline" data-text="signin_with"
                                data-size="large" data-logo_alignment="left">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
    <script src="https://accounts.google.com/gsi/client" async></script>
    <script src="assets/base.js"></script>
</body>

</html>