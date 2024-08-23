<%-- Document : chat.jsp Created on : Mar 5, 2024, 11:12:03 PM Author : End User --%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Ternet | Sign in</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <link href="assets/base.css" rel="stylesheet" />
    </head>

    <body>
        <div class="container" style="font-size: small">
            <c:if test="${!(param.id eq null)}">
            <div class="white container-fluid position-fixed start-0 top-0 pt-1">
                <div class="container p-0">
                    <p class="fw-semibold">${param.name}</p>
                </div>
            </div>
            <div class="white container-fluid position-fixed start-0 bottom-0 pt-2 pb-1">
                <c:if test="${!(chat.readOnly)}">
                <div class="container p-0">
                    <form action="chat" onsubmit="return false;">
                        <div class="d-flex justify-content-between">
                            <input type="text" placeholder="@message" class="form-control me-2" style="font-size: small;" id="content-input"/>
                            <button type="submit" onclick="sendMessage()" class="btn btn-sm"><i class="fi fi-rs-paper-plane" style="font-size: small;"></i></button>
                        </div>
                    </form>
                </div>
                </c:if>
            </div>
            </c:if>
            <div class="pt-5 pb-5">
                <div class="container p-0 pb-4">
                    <div class="row gy-2" id="frame">
                        <c:if test="${!(param.id eq null)}">
                            <c:forEach items="${requestScope.messages}" var="msg">
                                <c:if test="${!(msg.sender eq sessionScope.account.id)}">
                                    <div class="col-12">
                                        <div class="rounded-top-2 p-1 pt-0 pb-0 me-1 mb-1 d-inline-block"
                                             style="width: fit-content; background-color: #3b3b3b; color: #ffffff;">
                                            <span>${msg.sender}</span>
                                        </div>
                                        <span>${msg.sentTimeToRightFormat()}</span>
                                        <div class="rounded-2 p-1"
                                             style="width: fit-content; background-color: #f6f6f6; border-top-left-radius: unset !important;">
                                            <span>${msg.content}</span>
                                        </div>
                                    </div>
                                </c:if>
                                <c:if test="${msg.sender eq sessionScope.account.id}">
                                    <div class="col-12">
                                        <div class="d-flex justify-content-end">
                                            <div>
                                                <div class="d-flex justify-content-end">
                                                    <span class="me-1">${msg.sentTimeToRightFormat()}</span>
                                                    <div class="rounded-top-2 p-1 pt-0 pb-0 me-1 mb-1 d-inline-block" style="width: fit-content; background-color: #3b3b3b; color: #ffffff;">
                                                        <span>You</span>
                                                    </div>   
                                                </div>
                                                <div class="d-flex justify-content-end pe-1">
                                                    <div class="rounded-2 p-1 fl" style="width: fit-content; background-color: #f6f6f6; border-top-right-radius: unset !important;">
                                                        <span>${msg.content}</span>
                                                    </div>
                                                </div>   
                                            </div>
                                        </div>
                                    </div>
                                </c:if>
                            </c:forEach>
                        </c:if>
                    </div>
                </div>
            </div>
            
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
        <script src="https://accounts.google.com/gsi/client" async></script>
        <script src="assets/base.js"></script>

        <script>
                    const socket = new WebSocket('ws://localhost:8000/PRJ301_war_exploded/chat/${param.id}');
                    socket.onopen = function (event) {
                        
                    };

                    socket.onmessage = function (event) {
                        append(JSON.parse(event.data));
                    };

                    socket.onerror = function (event) {
                    };

                    socket.onclose = function (event) {
                    };
                    
                    function formatTime(date) {
                        const hours = date.getHours();
                        const ampm = hours >= 12 ? 'PM' : 'AM';
                        const formattedHours = String(hours % 12 || 12).padStart(2, '0');
                        const minutes = String(date.getMinutes()).padStart(2, '0');
                        return `${formattedHours}:${minutes} ${ampm}`;
                    }

                            const frame = document.getElementById('frame');
                            

                    function notify(p) {
                        var notification = '<div class="col-12 text-center"><span> ' + p + ' </span></div>';
                        frame.innerHTML += notification;
                        scroll();
                    }


                            function sendMessage() {
                                var contentInput = document.getElementById('content-input');
                                var content = contentInput.value.trim();
                                if (content != '') {
                                    var messageObject = {
                                        sender: '${sessionScope.account.id}',
                                        content: content,
                                        type: 0,
                                        sentTime: ''
                                    };
                                    var json = JSON.stringify(messageObject);
                                    socket.send(json);
                                    contentInput.value = '';
                                }

                            }

                            function append(data) {
                                var message;
                                if (data.sender == '${sessionScope.account.id}') {
                                    message = '<div class="col-12"><div class="d-flex justify-content-end"><div><div class="d-flex justify-content-end"><span class="me-1">' + data.sentTime + '</span><div class="rounded-top-2 p-1 pt-0 pb-0 me-1 mb-1 d-inline-block" style="width: fit-content; background-color: #3b3b3b; color: #ffffff;"><span>You</span></div>   </div><div class="d-flex justify-content-end pe-1"><div class="rounded-2 p-1 fl" style="width: fit-content; background-color: #f6f6f6; border-top-right-radius: unset !important;"><span>' + data.content + '</span></div></div>   </div></div></div>'
                                } else {
                                    message = '<div class="col-12"><div class="rounde pbne-block" style="width: fit-content; background-color: #3b3b3b; color: #ffffff;"><span>' + data.sender + '</span></div><span>' + data.sentTime + '</span><div class="rounded-2 p-1" style="width: fit-content; background-color: #f6f6f6;"><span>' + data.content + '</span></div></div>';
                                }
                                frame.innerHTML += message;
                                scroll();
                            }
                            function scroll() {
                                window.scrollTo({
                                    top: document.body.scrollHeight,
                                    behavior: 'smooth'
                                });
                            }
                            window.onload = function() {
                                window.scrollTo({
                                    top: document.body.scrollHeight,
                                    behavior: 'instant'
                                });
                            };
        </script>
    </body>

</html>