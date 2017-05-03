<%--
  Created by IntelliJ IDEA.
  User: 47
  Date: 2017/4/23
  Time: 16:02
  To change this template use File | Settings | File Templates.
--%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>ChatRoom</title>
    <link href="../dict/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="../dict/jquery-1.11.2.min.js"></script>
    <script type="text/javascript" src="../dict/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="../dict/jquery.json.min.js"></script>
    <script type="text/javascript">
        var wsocket;    // Websocket connection
        var userName;   // User's name
        var textarea;   // Chat area
        var wsconsole;  // Websocket console area
        var userlist;   // User list area
        /* Connect to the Websocket endpoint
         * Set a callback for incoming messages */
        function connect() {
            textarea = document.getElementById("textarea");
            wsconsole = document.getElementById("wsconsole");
            userlist = document.getElementById("userlist");
            wsocket = new WebSocket("ws://localhost:8080/websocketbot");
            wsocket.onmessage = onMessage;
            document.getElementById("name").focus();
            document.getElementById("consolediv").style.visibility = 'hidden';
        }
        /* Callback function for incoming messages
         * evt.data contains the message
         * Update the chat area, user's area and Websocket console */
        function onMessage(evt) {
            var line = "";
            /* Parse the message into a JavaScript object */
            var msg = JSON.parse(evt.data);
            if (msg.type === "chat") {
                line = msg.name + ": ";
                if (msg.target.length > 0)
                    line += "@" + msg.target + " ";
                line += msg.message + "\n";
                /* Update the chat area */
                textarea.value += "" + line;
            } else if (msg.type === "info") {
                line = "[--" + msg.info + "--]\n";
                /* Update the chat area */
                textarea.value += "" + line;
            } else if (msg.type === "users") {
                line = "Users:\n";
                for (var i=0; i < msg.userlist.length; i++)
                    line += "-" + msg.userlist[i] + "\n";
                /* Update the user list area */
                userlist.value = line;
            }
            textarea.scrollTop = 999999;
            /* Update the Websocket console area */
            wsconsole.value += "-> " +  evt.data + "\n";
            wsconsole.scrollTop = 999999;
        }
        /* Send a join message to the server */
        function sendJoin() {
            var input = document.getElementById("input");
            var name = document.getElementById("name");
            var join = document.getElementById("join");
            var jsonstr;
            if (name.value.length > 0) {
                /* Create a message as a JavaScript object */
                var joinMsg = {};
                joinMsg.type = "join";
                joinMsg.name = name.value;
                /* Convert the message to JSON */
                jsonstr = JSON.stringify(joinMsg);
                /* Send the JSON text */
                wsocket.send(jsonstr);
                /* Disable join controls */
                name.disabled = true;
                join.disabled = true;
                input.disabled = false;
                userName = name.value;
                /* Update the Websocket console area */
                wsconsole.value += "<- " + jsonstr + "\n";
                wsconsole.scrollTop = 999999;
            }
        }
        /* Send a chat message to the server (press ENTER on the input area) */
        function sendMessage(evt) {
            var input = document.getElementById("input");
            var jsonstr;
            var msgstr;
            if (evt.keyCode === 13 && input.value.length > 0) {
                /* Create a chat message as a JavaScript object */
                var chatMsg = {};
                chatMsg.type = "chat";
                chatMsg.name = userName;
                msgstr = input.value;
                chatMsg.target = getTarget(msgstr.replace(/,/g, ""));
                chatMsg.message = cleanTarget(msgstr);
                chatMsg.message = chatMsg.message.replace(/(\r\n|\n|\r)/gm,"");
                /* Convert the object to JSON */
                jsonstr = JSON.stringify(chatMsg);
                /* Send the message as JSON text */
                wsocket.send(jsonstr);
                input.value = "";
                /* Update the Websocket console area */
                wsconsole.value += "<- " + jsonstr + "\n";
                wsconsole.scrollTop = 999999;
            }
        }
        /* Send a join message if the user presses ENTER in the name area */
        function checkJoin(evt) {
            var name = document.getElementById("name");
            var input = document.getElementById("input");
            if (evt.keyCode === 13 && name.value.length > 0) {
                sendJoin();
                input.focus();
            }
        }
        /* Get the @User (target) for a message */
        function getTarget(str) {
            var arr = str.split(" ");
            var target = "";
            for (var i=0; i<arr.length; i++) {
                if (arr[i].charAt(0) === '@') {
                    target = arr[i].substring(1,arr[i].length);
                    target = target.replace(/(\r\n|\n|\r)/gm,"");
                }
            }
            return target;
        }
        /* Remove the @User (target) from a message */
        function cleanTarget(str) {
            var arr = str.split(" ");
            var cleanstr = "";
            for (var i=0; i<arr.length; i++) {
                if (arr[i].charAt(0) !== '@')
                    cleanstr += arr[i] + " ";
            }
            return cleanstr.substring(0,cleanstr.length-1);
        }
        /* Show or hide the WebSocket console */
        function showHideConsole() {
            var chkbox = document.getElementById("showhideconsole");
            var consolediv = document.getElementById("consolediv");
            if (chkbox.checked)
                consolediv.style.visibility = 'visible';
            else
                consolediv.style.visibility = 'hidden';
        }
        /* Call connect() when the page first loads */
        window.addEventListener("load", connect, false);
    </script>
</head>
<body>
<%@include file="../template/UserNavbar.jsp" %>
<br>
<br>
<!-- Page Content -->
<div class="container" style="padding-top:70px;">
    <div class="input-group">
        Your name:<input id="name" type="text" class="input-medium" onclick="checkJoin(event);">
        <button type="button" class="btn btn-primary" id="join" onclick="sendJoin()">Join!</button>
    </div>
    <textarea id="input" cols="90" rows="1" disabled="true"
              onkeyup="sendMessage(event);"></textarea>
    <div class="row">

        <div class="col-md-9" id="content">
            <textarea class="form-group" id="textarea" cols="90" rows="20" readonly="true"></textarea>
        </div>
        <div class="col-md-3" >
            <textarea  class="form-group" id="userlist" cols="30" rows="20" readonly="true"></textarea>
        </div>
    </div>
    <input id="showhideconsole" type="checkbox" onclick="showHideConsole();"/>
    Show WebSocket console<br/>
    <div id="consolediv">
        <textarea id="wsconsole" cols="80" rows="8" readonly="true"
                                   style="font-size:8pt;"></textarea>
    </div>
</div>
<!--h1>WebsocketBot</h1>
Your name: <input id="name" type="text" size="20" maxlength="20" onkeyup="checkJoin(event);"/>
<input type="submit" id="join" value="Join!" onclick="sendJoin();"/><br/>
    <textarea id="input" cols="70" rows="1" disabled="true"
              onkeyup="sendMessage(event);"></textarea><br/>
<textarea id="textarea" cols="70" rows="20" readonly="true"></textarea>
<textarea id="userlist" cols="20" rows="20" readonly="true"></textarea>
<br/><br/><br/>
<input id="showhideconsole" type="checkbox" onclick="showHideConsole();"/>
Show WebSocket console<br/>
<div id="consolediv"><textarea id="wsconsole" cols="80" rows="8" readonly="true"
                               style="font-size:8pt;"></textarea></div-->
</body>
</html>