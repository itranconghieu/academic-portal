<%--
    Document   : change-password
    Created on : Mar 19, 2024, 11:10:07 PM
    Author     : End User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Ternet | Forget password</title>
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
                        <h6>Change password</h6>
                    </div>
                    <div class="card-body">
                        <form action="change" method="post" class="mt-4">
                            <input type="hidden" name="username" value="${param.user}"/>
                            <input type="hidden" name="authCode" value="${param.authoCode}"/>
                            <div class="form-floating">
                                <input type="password" placeholder="" name="newPassword" class="form-control mb-3" id="new-password" required/>
                                <label for="new-password">New password</label>
                            </div>
                            <div class="form-floating">
                                <input type="password" placeholder="" name="confirmPassword" class="form-control mb-3" id="confirm-password" required/>
                                <label for="confirm-password">Confirm password</label>
                            </div>

                            <div class="d-flex justify-content-end">
                                <input type="submit" value="Save change" class="btn btn-light"/>
                            </div>
                        </form>
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