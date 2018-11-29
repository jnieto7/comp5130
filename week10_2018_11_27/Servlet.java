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

        String sourceHost = request.getParameter("sourceHost");
        String sourcePort = request.getParameter("sourcePort");

        if (sourcePort != null) {

        }

        String destHost = request.getParameter("destHost");
        String destPort = request.getParameter("destPort");

        // Set response content type
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.println("<h1>" + "running with command: " + "</h1>");
        out.println("<h1>" + "TCPDumps successfully captured!" + "</h1>");
    }

    @Override
    public void destroy() {
        // do nothing.
    }
}
