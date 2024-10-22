package com.example.t2;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.Date;

import jakarta.servlet.http.*;

@WebFilter("/input.jsp")
public class LoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // Cast the request to HttpServletRequest for better access to request details
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        // Log the request details
        System.out.println("Request received for input.jsp");
        System.out.println("Request URI: " + httpRequest.getRequestURI());
        System.out.println("Client IP: " + request.getRemoteAddr());
        System.out.println("Timestamp: " + new Date());

        // Continue the request to the next filter or the target servlet/jsp
        chain.doFilter(request, response);
    }
}
