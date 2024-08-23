<%--
    Document   : sent
    Created on : Mar 19, 2024, 10:54:57 PM
    Author     : End User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Ternet | Failed</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link href="assets/base.css" rel="stylesheet"/>
</head>

<body>
    <div class="container">
        <div class="row d-flex justify-content-center align-items-center" style="height: 100vh;">
            <div class="col-4">
                <div class="card">
                    <div class="card-header">
                        <h6>Has sent</h6>
                    </div>
                    <div class="card-body">
                        <span>If we have an account for the username or email address you provided, we have emailed a link to reset your password.</span><br/>
                        <div class="d-flex justify-content-center">
                            <button type="button" onclick="openHyperLink('<%=request.getContextPath()%>')" class="btn btn-light">Understood</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
        <script src="assets/base.js"></script>
    </body>
</html>