<%--
    Document   : account.jsp
    Created on : Mar 5, 2024, 7:05:17 PM
    Author     : End User
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Ternet | Account</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link href="assets/base.css" rel="stylesheet"/>
</head>

<body>
    <div class="container">
        <div class="row d-flex justify-content-center align-items-center" style="height: 100vh;">
            <div class="col-10">
                <div class="card">
                    <div class="card-body p-3 position-relative">
                        <div class="position-absolute end-0 top-0 p-2">
                            <button type="button" onclick="openHyperLink('<%=request.getContextPath()%>/timetable/week')" class="btn white"><i class="fi fi-rs-cross"></i></button>
                        </div>
                        <div class="position-absolute start-0 bottom-0 p-2">
                            <a href="" target="_blank" class="white btn">@Disclaimer</a>
                        </div>
                        <div class="row g-4">
                            <div class="col-5 border-end">
                                <p class="fs-6 fw-semibold">ACCOUNT MANAGERMENT</p>
                                <p>By using our service, this mean that you agree with our account policies and terms about end-user's informations.</p>
                            </div>  
                            <div class="col-7 ps-5 pe-5">
                                <div class="mb-4">
                                    <span class="fs-6 fw-semibold">CHANGE PASSWORD</span><br/>
                                    <form action="account" method="post" class="mt-4">
                                        <div class="form-floating">
                                            <input type="password" placeholder="" name="currentPassword" class="form-control mb-3" id="current-password" required/>
                                            <label for="current-password">Current password</label>
                                        </div>
                                        <div class="form-floating">
                                            <input type="password" placeholder="" name="newPassword" class="form-control mb-3" id="new-password" required/>
                                            <label for="new-password">New password</label>
                                        </div>
                                        <div class="form-floating">
                                            <input type="password" placeholder="" name="confirmPassword" class="form-control mb-3" id="confirm-password" required/>
                                            <label for="confirm-password">Confirm password</label>
                                        </div>
                                        <c:if test="${requestScope.changed}"><p class="text-center">The password has been changed</p></c:if>
                                        <c:if test="${requestScope.uncorrected}"><p class="text-center">The password is uncorrected</p></c:if>
                                        <div class="d-flex justify-content-end">
                                            <input type="submit" value="Save changes" class="btn btn-light"/>
                                        </div>
                                    </form>
                                    
                                </div>
                                <hr>
                                <div class="mb-4">
                                    <span class="fs-6 fw-semibold">ACCOUNT CONNECTION</span><br/>
                                    <span style="font-size: small;">Please note that the google account is already currently only.</span><br/>
                                <c:if test="${!(sessionScope.account.googleId eq null)}">
                                    <div role="group" class="btn-group mt-4">
                                        <button type="button" class="btn btn-light p-2">
                                            <img src="https://www.shareicon.net/data/512x512/2016/07/10/119930_google_512x512.png" alt="?" class="me-2" style="width: 1rem;"/>
                                            Google
                                        </button>
                                        <button type="btn" onclick="openHyperLink('?remove-google-account-connection=true')" class="btn btn-light"><i class="fi fi-rs-minus-small"></i></button>
                                    </div>
                                </c:if>
                                <c:if test="${sessionScope.account.googleId eq null}">
                                    <div class="mt-4">
                                        <div id="g_id_onload" data-client_id="444175066040-vfraie2ohc68jei5v58b1il2kppqm0o4.apps.googleusercontent.com"
                                            data-context="signin" data-ux_mode="redirect"
                                            data-login_uri="<%=request.getContextPath()%>/google-connection" data-auto_prompt="false">
                                        </div>
                                        <div class="g_id_signin" data-type="standard" data-shape="rectangular" data-theme="outline" data-text="continue_with"
                                            data-size="large" data-logo_alignment="left">
                                        </div>
                                    </div>
                                </c:if>
                                </div>
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
