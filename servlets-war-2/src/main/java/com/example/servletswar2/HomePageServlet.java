package com.example.servletswar2;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "homeServlet", value = "/home")
public class HomePageServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Home Page";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        // Add links to other servlets
        out.println("<p><a href='manage-animals'>Manage animals</a></p>");
        out.println("<p><a href='shelter'>Shelter</a></p>");
        out.println("</body></html>");
    }

    public void destroy() {
    }
}