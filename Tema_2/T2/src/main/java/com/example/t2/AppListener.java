package com.example.t2;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        ServletContext context = sce.getServletContext();

        String prelude = context.getInitParameter("prelude");
        String coda = context.getInitParameter("coda");

        context.setAttribute("prelude", prelude);
        context.setAttribute("coda", coda);

        System.out.println("Application started. Prelude: " + prelude + ", Coda: " + coda);
    }
}