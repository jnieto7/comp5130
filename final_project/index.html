<!DOCTYPE html>
<html>

    <head>
        <title>Protocol Capture Utility</title>
        <link rel="stylesheet" href="style.css" type="text/css">
        <script src="http://code.jquery.com/jquery-1.9.1.js"></script>
    </head>

    <body onload="init()">
        <h1><b>TCPDUMP multi-capture</b></h1>

        <h3>Capture of a simultaneous tcpdump
            to multiple hosts</h3>

        <h2>Server Configuration</h2>

        Current Saved Configuration:
        <div id="userConfig">No configuration has been saved.</div>

        <div id="form-messages"></div>

        <form id="saveConfiguration" name="saveConfiguration">
            <p><input type="button" value="Add Server" onclick="addServers()" id="servers" name="servers"/></p>

            <script>
                var id = 0;

                // the addServers() function will create server configuration input fields for the user after clicking the "add server" button
                function addServers() {

                    id++;

                    if (id >= 5) {
                        // maximum supported number of servers has been reached
                        window.alert("You cannot add another server. The maximum supported number of servers has been reached.");
                        return;
                    }
                    var hostInput = document.createElement("input");
                    hostInput.setAttribute("type", "text");
                    hostInput.setAttribute("name", "server" + id);
                    hostInput.setAttribute("value", "");
                    hostInput.setAttribute("id", "server" + id);

                    var passwordInput = document.createElement("input");
                    passwordInput.setAttribute("type", "password");
                    passwordInput.setAttribute("name", "password" + id);
                    passwordInput.setAttribute("value", "");
                    passwordInput.setAttribute("id", "password" + id);

                    var container = document.getElementById('saveConfiguration');

                    // for the first time, add a new line between the "save server config" button and the elements created below
                    if (id === 1) {
                        container.appendChild(document.createElement("br"));
                    }

                    var text = document.createTextNode("IP Address: ");
                    container.appendChild(text);
                    container.appendChild(hostInput);

                    var text = document.createTextNode(" Password: ");
                    container.appendChild(text);
                    container.appendChild(passwordInput);

                    container.appendChild(document.createElement("br"));
                }
            </script>

            <input type="submit" name="saveServers" id="saveButton" value="Save Server Configuration" onclick="save()">
            <script>
                function save() {
                    saveConfiguration();
                }
            </script>
        </form>

        <br><br>

        <form name ="options" method="post" action="Servlet" id="options" class="options">
            <h1><b>Capture options</b></h1>

            <b>Command Line Options:</b><br><br>
            -c &lt;timeout&gt;  Length of time in seconds to run the tcpdump <input type="text" name="timeout" value="10" required><br>
            -i &lt;interface&gt; Specifies the capture interface <input type="text" name="interface" value="any" required><br>
            -s &lt;len&gt; Capture up to len bytes per packet<input type="text" name="lenBytes"><br>
            <input type="checkbox" name="file" id="file">-w &lt;write to a file&gt; Write the output to a pcap file. The filename will be capture${host}.pcap

            <br><br>
            <b>Capture Filter Primitives:</b>
            <br><br>

            host: Match IP host <input type="text" name="host"><br>
            port: Match source port<input type="text" name="port"><br>

            <br>

            <b>-p protocol</b><br><br>
            (Note: the -p protocol option cannot be used in conjuction with host/port options above)<br>

            <input type="radio" name="radio" value="arp">arp<br>
            <input type="radio" name="radio" value="ether">ether<br>
            <input type="radio" name="radio" value="icmp">icmp<br>
            <input type="radio" name="radio" value="ip">ip<br>
            <input type="radio" name="radio" value="ip6">ip6<br>
            <input type="radio" name="radio" value="link">link<br>
            <input type="radio" name="radio" value="sctp">sctp<br>
            <input type="radio" name="radio" value="tcp">tcp<br>
            <input type="radio" name="radio" value="udp">udp<br>
            <input type="radio" name="radio" value="wlan">wlan<br>

            <br>

            <input type="submit" value="Run">
            <p>Click "Run" to begin the packet captures.</p>

            <script>

                // pass the server configuration to the servlet on submit
                servers.saveConfiguration();
            </script>
        </form> 

        <script src="servers.js"></script>

    </body>
</html>
