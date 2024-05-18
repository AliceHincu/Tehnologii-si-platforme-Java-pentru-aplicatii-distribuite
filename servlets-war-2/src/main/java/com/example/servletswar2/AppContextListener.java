package com.example.servletswar2;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import java.util.ArrayList;
import java.util.List;

@WebListener
//application context data persists for the life of the web application
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        List<Animal> animals = new ArrayList<>();
        animals.add(new Animal("Max", 3, "Labrador"));
        animals.add(new Animal("Bella", 2, "Beagle"));
        animals.add(new Animal("Charlie", 4, "Bulldog"));
        animals.add(new Animal("Lucy", 5, "Poodle"));
        animals.add(new Animal("Daisy", 1, "Boxer"));

        ServletContext context = sce.getServletContext();
        context.setAttribute("animals", animals);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Cleanup code if necessary
    }
}