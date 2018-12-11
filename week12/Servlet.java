/*
* Jeremie Nieto
 */
package jn.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

/**
 * Right now this is a test Servlet that is not fully implemented
 *
 * @author jnieto
 */
@WebServlet("/processOptions")
public class Servlet extends HttpServlet {

    @Override
    public void init() throws ServletException {

    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Set response content type
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.println("<h1>" + "No Content" + "</h1>");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String captureInterface = request.getParameter("interface");

        StringBuilder command = new StringBuilder("/usr/sbin/tcpdump -i ").append(captureInterface);

        String lenBytes = request.getParameter("lenBytes");
        if (lenBytes != null) {
            command.append(" -s ").append(lenBytes);
        }

        String host = request.getParameter("host");
        if (host != null) {
            command.append(" host ").append(host);
        }

        String port = request.getParameter("port");
        if (port != null) {
            command.append(" port ").append(port);
        }
        
//        ServerCommand.executeSSHCommand(port, port, command.toString());

        // Set response content type
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.println("<h1>" + "Running tcpdump network packet captures with command: " + command.toString() + "</h1>");
        out.println("<h1>" + "TCPDumps successfully captured!" + "</h1>");
    }

    @Override
    public void destroy() {
        // do nothing.
    }
}
