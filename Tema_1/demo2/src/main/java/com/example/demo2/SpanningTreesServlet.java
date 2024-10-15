package com.example.demo2;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.graph4j.Graph;
import org.graph4j.GraphBuilder;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "SpanningTreesServlet", value = "/SpanningTreesServlet")
public class SpanningTreesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");

        // Parse parameters
        int order = Integer.parseInt(request.getParameter("order"));
        int k = Integer.parseInt(request.getParameter("k"));

        int[][] graph = new int[order][order];
        for (int i = 0; i < order; i++) {
            for (int j = i + 1; j < order; j++) {
                graph[i][j] = i + j + 2; // edge weight is the sum of vertex indices + 2 (since 1-based)
            }
        }

       // List<String> spanningTrees = generateKSpanningTrees(graph, order, k);


    }


}
