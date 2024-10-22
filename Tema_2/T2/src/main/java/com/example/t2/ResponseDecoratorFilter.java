package com.example.t2;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebFilter("/*")
public class ResponseDecoratorFilter  implements Filter {


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {


        ServletContext context = request.getServletContext();
        String prelude = (String) context.getAttribute("prelude");
        String coda = (String) context.getAttribute("coda");


        PrintWriter out = response.getWriter();
        out.println(prelude);
        chain.doFilter(request, response);
        out.println(coda);
    }


}