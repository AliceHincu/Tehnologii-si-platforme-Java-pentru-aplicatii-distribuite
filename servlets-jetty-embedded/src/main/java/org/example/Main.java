package org.example;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.example.servlets.AnimalManagementServlet;
import org.example.servlets.AppContextListener;
import org.example.servlets.HomePageServlet;
import org.example.servlets.ShelterServlet;

public class Main {

    public static void main(String[] args) throws Exception{
        Server server = new Server(8080);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        // Add servlets
        context.addServlet(new ServletHolder(new HomePageServlet()), "/home");
        context.addServlet(new ServletHolder(new ShelterServlet()), "/shelter");
        context.addServlet(new ServletHolder(new AnimalManagementServlet()), "/manage-animals");

        // Registering the context listener
        context.addEventListener(new AppContextListener());

        server.start();
        server.join();
    }
}
