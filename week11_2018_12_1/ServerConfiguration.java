/*
* Jeremie Nieto
 */
package jn.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

/**
 * ServerConfiguration saves the user's server configuration.
 *
 * @author jnieto
 */
@WebServlet("/serverConfiguration")
public class ServerConfiguration extends HttpServlet {

    private ServletContext context;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.context = config.getServletContext();
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

        ArrayList<Server> servers = new ArrayList();

        String server1 = request.getParameter("server1");

        // server1 must not be null
        if (server1 != null) {

            servers.add(new Server(server1, request.getParameter("password1")));

            String server2 = request.getParameter("server2");
            if (server2 != null) {
                servers.add(new Server(request.getParameter("server2"), request.getParameter("password2")));

                String server3 = request.getParameter("server3");
                if (server3 != null) {
                    servers.add(new Server(request.getParameter("server3"), request.getParameter("password3")));

                    String server4 = request.getParameter("server4");
                    if (server4 != null) {
                        servers.add(new Server(request.getParameter("server4"), request.getParameter("password4")));
                    }
                }
            }

            // Set response content type
//        response.setContentType("text/html");
            response.setStatus(HttpServletResponse.SC_OK);

            StringBuilder sb = new StringBuilder();

            sb.append("<root>");
            sb.append("<message>");
            sb.append("Your server configuration data has been saved.");
            sb.append("</message>");

            boolean serverAdded = false;
            int i = 1;
            for (Server s : servers) {

                sb.append(String.format("<server%d>", i));
                sb.append(request.getParameter("server" + i));
                sb.append(String.format("</server%d>", i));

                i++;
                serverAdded = true;
            }

            sb.append("</root>");

            PrintWriter out = response.getWriter();

            if (serverAdded) {
                response.setContentType("text/xml");
                response.setHeader("Cache-Control", "no-cache");
                response.getWriter().write(sb.toString());
            } else {

            }
        }

    }

    @Override
    public void destroy() {
        // do nothing.
    }
}
