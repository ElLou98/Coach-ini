/* globals wsUrl: true */
(function () {
    'use strict';

    var _receiver = document.getElementById('ws-content-receiver');
    var _notifReceiver = document.getElementById('ws-notif-receiver');
    var ws = new WebSocket('ws://' + wsUrl);

    var _textInput = document.getElementById('ws-content-to-send');
    var _textSender = document.getElementById('ws-send-content');
    var enterKeyCode = 13;


    var userName = tempMe;

    var defaultChannel = tempChannel;
    var botName = 'ChatBot';

    var addMessageToChannel = function(message) {

        var _message = JSON.parse(message);
        var liClass = 'message' + (_message.user === userName ? ' mine' : '');

        // Check message class
        if (_message.hasOwnProperty('messageClass')) {
            liClass += ' ' + _message.messageClass;
        }

        if (_message.user !== 'ChatBot')
        {
            _receiver.innerHTML += '<li class="' + liClass + '">' +
                '<strong>' + _message.user + '</strong>: ' + _message.message +
                '</li>';
        }
        else
        {
            _notifReceiver.innerHTML += '<li class="' + liClass + '">' +
                '<strong>' + _message.user + '</strong>: ' + _message.message +
                '</li>';
        }
        // Update messages list


        //_receiver.innerHTML += '<div class="message">' + message+ '</div>';

        //var test =JSON.parse(message);
        //var html="<div class"+
        //   "='border-1 bg-dark'>"+test.message+"</div>";

        //_receiver.innerHTML += '<span class="message">' + html+ '</span>';
    };

    var botMessageToGeneral = function (message) {
        return addMessageToChannel(JSON.stringify({
            action: 'message',
            channel: defaultChannel,
            user: botName,
            message: message,
            me:userName,
            friend:friend
        }));
    };

    ws.onopen = function () {
        ws.send(JSON.stringify({
            action: 'subscribe',
            channel: defaultChannel,
            user: userName,
            me:userName,
            friend:friend,
        }));
    };

    ws.onmessage = function (event) {
        addMessageToChannel(event.data);
    };

    ws.onclose = function () {
        botMessageToGeneral('Connexion fermée');
    };

    ws.onerror = function () {
        botMessageToGeneral('Une erreur a survenu!');
    };

    var sendTextInputContent = function () {
        // Get text input content
        var content = _textInput.value;
        // Send it to WS
            ws.send(JSON.stringify({
                action: 'message',
                user: userName,
                message: content,
                channel: defaultChannel,
                me:userName,
                friend:friend,
            }));

            // Reset input
            _textInput.value = '';


    };

    _textSender.onclick = sendTextInputContent;
   // _textInput.onkeyup = function(e) {
        // Check for Enter key
   //     if (e.keyCode === enterKeyCode) {
   //         sendTextInputContent();
   //     }
   // };



})();