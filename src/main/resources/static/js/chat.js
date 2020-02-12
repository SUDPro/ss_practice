var stompClient = null;
var roomId = null;
$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    connect();
    roomId = window.location.pathname.split('/')[2];
    $("#sendMessage").click(function () {
        send();
    });
    loadMessages();
});

function connect() {
    var socket = new SockJS('/messages');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/' + roomId, function (message) {
            showMessage(message.body);
        });
    });
}

function loadMessages() {
    $.ajax({
        url: "/api/get-messages",
        data: {
            roomId: roomId
        },
        method: "get",
        success: function (messages) {
            for(var i = 0; i < messages.length; i ++) {
                showMessage(messages[i].sender.username + ": " + messages[i].text)
            }
        }
    });
}

function send() {
    stompClient.send("/app/chat", {}, JSON.stringify({
        text: $("#message").val(),
        roomId: Number(roomId),
    }));
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}

function showMessage(message) {
    $("#messages").append("<tr><td>" + message + "</td></tr>");
}
