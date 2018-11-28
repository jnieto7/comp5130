/*
* Jeremie Nieto
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Right now this is a test Servlet that is not fully implemented
 * 
 * @author jnieto
 */
public class Servlet extends HttpServlet {

    private final String message = "TCPDUMP EXECUTOR";

    @Override
    public void init() throws ServletException {

    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Set response content type
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.println("<h1>" + this.message + "</h1>");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Set response content type
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.println("<h1>" + this.message + "</h1>");
    }

    @Override
    public void destroy() {
        // do nothing.
    }
}
