/* 
 * servers.js
 * 
 * Server configuration processor
 */

var request;
var userConfiguration;

function init() {
    userConfiguration = document.getElementById("userConfig");
}

function saveConfiguration() {
    // get the form
    var form = document.getElementById("saveConfiguration");
    // Get the messages div.
//    var formMessages = $('#form-messages');

    form.addEventListener("click", function (event) {
        event.preventDefault();
    });

    var url = form.getAttribute("action");
    window.alert(url);

    request = new XMLHttpRequest();
    request.open("POST", "serverConfiguration", true);
    request.onreadystatechange = callback;

    var servers = $(form).serializeArray();

    request.send(servers);
}

function callback() {
    if (request.readyState === 4) {
        if (request.status === 200) {
            parseMessages(request.responseText);
        }
    }
}


function parseMessages(responseXML) {

    window.alert("CALLED");


    // no matches returned
    if (responseXML === null) {
        return false;
    } else {

        var message = responseXML.getElementsByTagName("message");
        var servers = responseXML.getElementsByTagName("server1");

        window.alert(servers);


        userConfiguration.innerHTML = message + servers;
    }
}
