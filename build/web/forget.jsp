<%--
    Document   : forget
    Created on : Mar 19, 2024, 10:06:19 PM
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
                        <h6>Forget password</h6>
                    </div>
                    <div class="card-body">
                        <form action="forget" method="post">
                            <div class="form-floating">
                                <input type="email" placeholder="?" name="mail" class="form-control mb-3" id="mail" required/>
                                <label for="mail">Mail</label>
                            </div>
                            <input type="submit" value="Authorize" class="w-100 btn btn-light p-3 mb-3" id="submit-button"/>
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

