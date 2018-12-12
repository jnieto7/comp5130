/*
* Jeremie Nieto
 */
package jn.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * The Servlet handles the requests from the webpage. It is responsible for
 * handling the ServerConfiguration Ajax Request and the TCPDump capture.
 *
 * @author jnieto
 */
public class Servlet extends HttpServlet {

    private final CopyOnWriteArrayList<Server> servers = new CopyOnWriteArrayList();

    @Override
    public void init() throws ServletException {

    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // keep track whether or not this is a request to save the server configuration
        // or to execute the tcpdump
        boolean savingConfigRequest = false;

        // Process saving the serverConfiguration
        String server1 = request.getParameter("server1");

        // server1 must not be null
        if (server1 != null && !server1.isEmpty()) {

            savingConfigRequest = true;

            this.servers.add(new Server(server1, request.getParameter("password1")));

            String server2 = request.getParameter("server2");
            if (server2 != null && !server2.isEmpty()) {
                this.servers.add(new Server(request.getParameter("server2"), request.getParameter("password2")));

                String server3 = request.getParameter("server3");
                if (server3 != null && !server3.isEmpty()) {
                    this.servers.add(new Server(request.getParameter("server3"), request.getParameter("password3")));

                    String server4 = request.getParameter("server4");
                    if (server4 != null && !server4.isEmpty()) {
                        this.servers.add(new Server(request.getParameter("server4"), request.getParameter("password4")));
                    }
                }
            }

            response.setStatus(HttpServletResponse.SC_OK);

            StringBuilder sb = new StringBuilder();

            sb.append("<root>");
            sb.append("<message>");
            sb.append("Your server configuration data has been saved.");
            sb.append("</message>");

            // return XML server IP address data to javascript 
            int i = 1;
            for (Server s : this.servers) {
                sb.append(String.format("<server%d>", i));
                sb.append(request.getParameter("server" + i));
                sb.append(String.format("</server%d>", i));

                i++;
            }

            sb.append("</root>");

            response.setContentType("text/xml");
            response.setHeader("Cache-Control", "no-cache");
            response.getWriter().write(sb.toString());
        }

        if (!savingConfigRequest) {

            String captureInterface = request.getParameter("interface");

            StringBuilder command = new StringBuilder("/usr/sbin/tcpdump -vvv -c ").append(request.getParameter("timeout")).append(" -i ")
                    .append(captureInterface);

            String radio = request.getParameter("radio");
            if (radio != null && !radio.isEmpty()) {
                command.append(" -p ").append(radio);
            }

            String lenBytes = request.getParameter("lenBytes");
            if (lenBytes != null && !lenBytes.isEmpty()) {
                command.append(" -s ").append(lenBytes);
            }

            String host = request.getParameter("host");
            if (host != null && !host.isEmpty()) {
                command.append(" host ").append(host);
            }

            String port = request.getParameter("port");
            if (port != null && !port.isEmpty()) {
                command.append(" port ").append(port);
            }

            String file = request.getParameter("file");
            boolean writeToFile = file != null && file.equalsIgnoreCase("on");

            if (writeToFile) {
                // deal with the rest of the filename later when running command
                command.append(" -w ").append("/tmp/capture");
            }

            ArrayList<String> results = new ArrayList();
            ArrayList<Throwable> errors = null;

            if (!this.servers.isEmpty()) {

                for (Server s : this.servers) {
                    try {
                        // add the host back in if writing to a file
                        if (writeToFile) {
                            command.append("_").append(s.getHost()).append(".pcap");
                        }
                        s.setCommandType(Server.CommandType.SSH);
                        s.setCommand(command.toString());
                        // Start the commands on another thread so that they are run at the same time
                        s.start();
                    } catch (Throwable th) {
                        // don't allow one error to hurt the others
                        errors = new ArrayList();
                        errors.add(th);
                    }
                }

                for (Server s : this.servers) {
                    try {
                        s.join();
                    } catch (InterruptedException ex) {

                    }
                    results.add("host: " + s.getHost() + "<br><br>" + s.getResults().concat("<br><br>============================<br><br>="));

                    // if writing to a file copy it back to the current directory
                    if (writeToFile) {
                        String copy = "scp " + s.getUser() + "@" + s.getHost() + ":/tmp/capture_" + s.getHost() + ".pcap /tmp/";
                        s.setCommand(copy);
                        s.setCommandType(Server.CommandType.LOCAL);
                        try {
                            s.executeLocalCommand(copy);
                        } catch (InterruptedException ex) {
                            if (errors == null) {
                                errors = new ArrayList();
                            }

                            errors.add(ex);
                        }
                    }
                }
            }

            response.setContentType("text/html");

            PrintWriter out = response.getWriter();

            if (writeToFile) {
                out.println("<h3>" + "Pcap Files saved to /tmp/ directory." + "</h3>");
            } else {
                out.println("<h3>" + "Network Traffic captured:" + "</h3>");

                for (String s : results) {
                    out.println("<h4>" + s + "</h4>");
                    out.println("<br><br>");
                }
            }

            if (errors != null) {
                out.println("<h2>" + "The following errors occurred:" + "</h2>");
                for (Throwable t : errors) {
                    out.println("<h3>" + t.getMessage() != null ? t.getMessage() : "unknown error" + "</h3>");
                }
            }
        }
    }

    @Override
    public void destroy() {
        // do nothing.
    }
}
