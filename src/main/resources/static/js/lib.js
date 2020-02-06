var messageArea = document.querySelector('#messageArea');
var socket;

function connect() {
    socket = new WebSocket("ws://localhost:8080/chat");
    socket.onopen = function () {
        alert("Соединение установлено.");
        document.getElementById("connectButton").style.visibility = "hidden";
        document.getElementById("sendButton").style.visibility = "visible";
        document.getElementById("closeConnection").style.visibility = "visible";
    };
    socket.onmessage = function (event) {
        var message = JSON.parse(event.data);
       drawMessage(message)
    };

    $.ajax({
        url: "/api/get-all-messages",
        success: function(data){
            for (var i = 0; i < data.length; i++) {
                var message = {
                    text: data[i].text,
                    from: data[i].sender.login
                };
                drawMessage(message);
            }
        }
    });
}

function drawMessage(message){
    if (message.text != "") {
        var messageElement = document.createElement('li');
        messageElement.classList.add('chat-message');
        var usernameElement = document.createElement('strong');
        usernameElement.classList.add('nickname');
        var usernameText = document.createTextNode(message.from + " : ");
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);


        var textElement = document.createElement('span');
        var messageText = document.createTextNode(message.text);
        textElement.appendChild(messageText);

        messageElement.appendChild(textElement);

        messageArea.appendChild(messageElement);
        messageArea.scrollTop = messageArea.scrollHeight;
    }
}

function closeConnection() {
    socket.onclose = function (event) {
        alert("Cоединение закрыто");
        document.getElementById("connectButton").style.visibility = "visible";
        document.getElementById("sendButton").style.visibility = "hidden";
        document.getElementById("closeConnection").style.visibility = "hidden";

    };
    socket.close();
}

function sendMessage() {
    var text = document.getElementById("message").value;
    var message = {
        text: text
    };
    console.log(message);
    socket.send(JSON.stringify(message));
}
