package org.example.servlets;

import java.util.ArrayList;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ShelterServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");

        ServletContext context = getServletContext();
        List<Animal> animals = (List<Animal>) context.getAttribute("animals");
        if (animals == null) {
            animals = new ArrayList<>();
            context.setAttribute("animals", animals);
        }

        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>Shelter</h1>");
        for (Animal animal : animals) {
            out.println("<p>" + animal.getName() + " - " + animal.getBreed() + " - " + animal.getAge() + " years old</p>");
        }
        out.println("</body></html>");
    }
}