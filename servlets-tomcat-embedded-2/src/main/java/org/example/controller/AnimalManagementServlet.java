package org.example.controller;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "animalManagementServlet", value = "/manage-animals")
public class AnimalManagementServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>Animal Management</h1>");

        // Form for adding an animal
        out.println("<form action='manage-animals' method='POST'>");
        out.println("Name: <input type='text' name='name'><br>");
        out.println("Age: <input type='number' name='age'><br>");
        out.println("Breed: <input type='text' name='breed'><br>");
        out.println("<input type='submit' name='action' value='Add Animal'>");
        out.println("</form>");

        // Button to adopt an animal
        out.println("<form action='manage-animals' method='POST'>");
        out.println("<input type='submit' name='action' value='Adopt'>");
        out.println("</form>");

        out.println("</body></html>");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");
        ServletContext context = getServletContext();
        List<Animal> animals = (List<Animal>) context.getAttribute("animals");
        PrintWriter out = response.getWriter();

        if ("Add Animal".equals(action)) {
            // Retrieve form data
            String name = request.getParameter("name");
            String ageStr = request.getParameter("age");
            String breed = request.getParameter("breed");

            // Validate input
            String validationError = validateInput(name, ageStr, breed);

            if (validationError.isEmpty()) {
                int age = Integer.parseInt(ageStr);

                if (animals == null) {
                    animals = new ArrayList<>();
                    context.setAttribute("animals", animals);
                }

                // Add a new animal
                animals.add(new Animal(name, age, breed));

                // Redirect back to the management page
                response.sendRedirect("manage-animals");

//                final RequestDispatcher dispatcher = request.getRequestDispatcher("manage-animals");
//                dispatcher.forward(request, response);
            } else {
                // Handle validation error
                out.println("<html><body>");
                out.println("<h1>Error: " + validationError + "</h1>");
                out.println("<p><a href='manage-animals'>Go Back</a></p>");
                out.println("</body></html>");
            }
        } else if ("Adopt".equals(action)) {
            if (animals != null && !animals.isEmpty()) {
                Random random = new Random();
                int index = random.nextInt(animals.size());
                Animal adoptedAnimal = animals.remove(index);

                // Display adoption message
                out.println("<html><body>");
                out.println("<h1>You adopted " + adoptedAnimal.getName() + "!</h1>");
                out.println("<p><a href='manage-animals'>Go Back</a></p>");
                out.println("</body></html>");
            } else {
                out.println("<html><body>");
                out.println("<h1>No animals available for adoption.</h1>");
                out.println("<p><a href='manage-animals'>Go Back</a></p>");
                out.println("</body></html>");
            }
        }
    }

    private String validateInput(String name, String ageStr, String breed) {
        if (name == null || name.trim().isEmpty()) {
            return "Name is required.";
        }
        if (breed == null || breed.trim().isEmpty()) {
            return "Breed is required.";
        }
        try {
            int age = Integer.parseInt(ageStr);
            if (age < 0) {
                return "Age cannot be negative.";
            }
        } catch (NumberFormatException e) {
            return "Age is required and should be an integer";
        }
        return "";
    }
}