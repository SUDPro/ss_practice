var stompClient = null;
var roomId = null;
$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    connect();
    roomId = window.location.pathname.replace("/", '');
    $("#sendMessage").click(function () {
        send();
    });
    loadMessages();
});

function connect() {
    // создаем объект SockJs
    var socket = new SockJS('/messages');
    // создаем stomp-клиент поверх sockjs
    stompClient = Stomp.over(socket);
    // при подключении
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        // подписываемся на url
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
                showMessage(messages[i].sender.name + ": " + messages[i].text)
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
