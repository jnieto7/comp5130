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
    var form = document.getElementById("saveConfiguration");

    form.addEventListener("click", function (event) {
        event.preventDefault();
    });

    var url = 'Servlet';

    // the first server must not be null, the user must configure at least one
    if (document.querySelector('#server1') !== null) {
        url = url.concat('?server1=').concat(document.querySelector('#server1').value);

        if (document.querySelector('#password1') !== null) {
            url = url.concat('&password1=').concat(document.querySelector('#password1').value);
        }

        if (document.querySelector('#server2') !== null) {
            url = url.concat('&server2=').concat(document.querySelector('#server2').value);

            if (document.querySelector('#password2') !== null) {
                url = url.concat('&password2=').concat(document.querySelector('#password2').value);
            }

            if (document.querySelector('#server3') !== null) {
                url = url.concat('&server3=').concat(document.querySelector('#server3').value);

                if (document.querySelector('#password3') !== null) {
                    url = url.concat('&password3=').concat(document.querySelector('#password3').value);
                }

                if (document.querySelector('#server4') !== null) {
                    url = url.concat('&server4=').concat(document.querySelector('#server4').value);

                    if (document.querySelector('#password4') !== null) {
                        url = url.concat('&password4=').concat(document.querySelector('#password4').value);
                    }
                }
            }
        }
    }

    request = new XMLHttpRequest();
    request.open("POST", url, true);
    request.onreadystatechange = callback;

    request.send();
}

function callback() {
    if (request.readyState === 4) {
        if (request.status === 200) {
            parseMessages(request.responseText);
        }
    }
}

function parseMessages(responseXML) {

    if (responseXML !== null) {
        var parser = new DOMParser();
        var xmlDoc = parser.parseFromString(responseXML, "text/xml");
        var message = xmlDoc.getElementsByTagName("message")[0].childNodes[0].nodeValue;

        if (message === "No server configuration data was submitted. Complete the form and define a server configration.") {
            // No form data for configuration of servers was submitted
            userConfiguration.innerHTML = message;
        } else {
            var server1 = xmlDoc.getElementsByTagName("server1");

            var displayMessage;

            if (server1.length > 0) {
                displayMessage = xmlDoc.getElementsByTagName("server1")[0].childNodes[0].nodeValue;
            }

            var server2 = xmlDoc.getElementsByTagName("server2");

            if (server2.length > 0) {
                displayMessage = displayMessage.concat(" ").concat(xmlDoc.getElementsByTagName("server2")[0].childNodes[0].nodeValue);

            }
            var server3 = xmlDoc.getElementsByTagName("server3");

            if (server3.length > 0) {
                displayMessage = displayMessage.concat(" ").concat(xmlDoc.getElementsByTagName("server3")[0].childNodes[0].nodeValue);
            }

            var server4 = xmlDoc.getElementsByTagName("server4");

            if (server4.length > 0) {
                displayMessage = displayMessage.concat(" ").concat(xmlDoc.getElementsByTagName("server4")[0].childNodes[0].nodeValue);
            }

            userConfiguration.innerHTML = displayMessage + "<br><br>" + message;
        }
    }
}
