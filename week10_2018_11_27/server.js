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

    window.alert(servers);


    // get the form
    var form = $('#serverConfiguration');
    // Get the messages div.
//    var formMessages = $('#form-messages');

//    var url = "autocomplete?action=complete&id=" + escape(completeField.value);
    var url = $(form).attr('action');
    request = new XMLHttpRequest();
    request.open("POST", url, true);
    request.onreadystatechange = callback;

    var servers = $(form).serializeArray();

//    $('form').submit(function () {
//        console.log($(this).serializeArray());
//        return false;
//    });
//    $('form').submit(function () {
//        console.log($(this).serializeArray());
//        return false;
//    });


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

    // no matches returned
    if (responseXML === null) {
        return false;
    } else {

        var message = responseXML.getElementsByTagName("message");
        var servers = responseXML.getElementsByTagName("servers");

        userConfiguration.innerHTML = message + servers;

    }
}



