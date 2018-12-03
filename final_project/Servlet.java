/*
* Jeremie Nieto
 */
package jn.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Right now this is a test Servlet that is not fully implemented
 *
 * @author jnieto
 */
public class Servlet extends HttpServlet {

    private final ArrayList<Server> servers = new ArrayList();

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

            String writeToFIle = request.getParameter("file");
            if (writeToFIle != null && !writeToFIle.isEmpty()) {
                // deal with the rest of the filename later when running command
                command.append(" -w ").append("/tmp/capture");
            }

            ArrayList<String> results = new ArrayList();
            ArrayList<Throwable> errors = null;

            if (!servers.isEmpty()) {

                for (Server s : this.servers) {
                    try {
                        // add the host back in if writing to a file
                        if (writeToFIle != null && writeToFIle.equalsIgnoreCase("on")) {
                            command.append("_").append(s.getHost()).append(".pcap");
                        }
                        results.add("host: " + s.getHost() + "<br>" + s.executeSSHCommand(command.toString()).concat("<br><br>==============================<br><br>="));

                        // if writing to a file copy it back to the current directory
                        if (writeToFIle != null && writeToFIle.equalsIgnoreCase("on")) {
                            String copy = "scp " + s.getUser() + "@" + s.getHost() + ":/tmp/capture_" + s.getHost() + ".pcap /tmp/";
                            s.executeSSHCommand(copy);
                        }
                    } catch (Throwable th) {
                        // don't allow one error to hurt the others
                        errors = new ArrayList();
                        errors.add(th);
                    }
                }
            }

            response.setContentType("text/html");

            PrintWriter out = response.getWriter();
            out.println("<h2>" + "Network Traffic captured:" + "</h2>");
            out.println("<h3>" + Arrays.deepToString(results.toArray()) + "</h3>");

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
