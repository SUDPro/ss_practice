var stompClient = null;
var roomId = null;
$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    connect();
    roomId = window.location.pathname.split('/')[2];
    //     /room/1
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
            var mes = JSON.parse(message.body);
            showMessage(mes.sender.username + ": " + mes.text);
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

function addUser(){
    stompClient.send("/app/chat/" + roomId + "/addUser", {}, JSON.stringify({
        text: "Добавлен новый пользователь!"
    }));
}

function send() {
    stompClient.send("/app/chat/" + roomId + "/sendMessage", {}, JSON.stringify({
        text: $("#message").val()
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
