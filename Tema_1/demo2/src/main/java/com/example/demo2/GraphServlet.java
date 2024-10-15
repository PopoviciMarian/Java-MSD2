package com.example.demo2;

import java.io.*;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Random;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;


@WebServlet(name = "GraphServlet", value = "/GraphServlet")
public class GraphServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        logRequestDetails(request);

        // Get parameters from the request
        int numVertices = Integer.parseInt(request.getParameter("numVertices"));
        int numEdges = Integer.parseInt(request.getParameter("numEdges"));
        boolean jsonFormat = Boolean.parseBoolean(request.getParameter("json"));

        // Create a 2D array for the adjacency matrix
        int[][] adjacencyMatrix = new int[numVertices][numVertices];

        // Generate a random graph with the given number of edges
        generateRandomGraph(adjacencyMatrix, numVertices, numEdges);
        if(jsonFormat) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            // return adjacencyMatrix matrix
            writeJsonResponse(response.getWriter(), adjacencyMatrix, numVertices);
            return; // End the response here

        }

        // Set content type
        response.setContentType("text/html");

        // Write the response
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>Adjacency Matrix</h1>");
        out.println("<table border='1'>");

        // Create table header
        out.println("<tr>");
        for (int i = 0; i <= numVertices; i++) {
            if (i == 0) {
                out.println("<th></th>");  // Top-left empty corner
            } else {
                out.println("<th>V" + i + "</th>");  // Vertex headers
            }
        }
        out.println("</tr>");

        // Populate the table with the adjacency matrix
        for (int i = 0; i < numVertices; i++) {
            out.println("<tr>");
            out.println("<th>V" + (i + 1) + "</th>");  // Row headers
            for (int j = 0; j < numVertices; j++) {
                out.println("<td>" + adjacencyMatrix[i][j] + "</td>");
            }
            out.println("</tr>");
        }

        out.println("</table>");
        out.println("</body></html>");
    }

    // Method to generate a random graph
    private void generateRandomGraph(int[][] matrix, int numVertices, int numEdges) {
        Random random = new Random();
        int edgeCount = 0;

        while (edgeCount < numEdges) {
            int i = random.nextInt(numVertices);
            int j = random.nextInt(numVertices);
            // To avoid self-loops and repeated edges
            if (i != j && matrix[i][j] == 0) {
                matrix[i][j] = 1;
                matrix[j][i] = 1; // Since the graph is undirected
                edgeCount++;
            }
        }
    }

    private void logRequestDetails(HttpServletRequest request) {
        String httpMethod = request.getMethod();

        String clientIp = request.getRemoteAddr();

        String userAgent = request.getHeader("User-Agent");

        Enumeration<Locale> locales = request.getLocales();
        StringBuilder languages = new StringBuilder();
        while (locales.hasMoreElements()) {
            languages.append(locales.nextElement().toString()).append(" ");
        }

        String numVertices = request.getParameter("numVertices");
        String numEdges = request.getParameter("numEdges");

        // Log the information
        String logMessage = String.format("HTTP Method: %s, Client IP: %s, User-Agent: %s, Languages: %s, numVertices: %s, numEdges: %s",
                httpMethod, clientIp, userAgent, languages.toString().trim(), numVertices, numEdges);

        // Use the servlet's log method to log the message
        getServletContext().log(logMessage);
    }

    private void writeJsonResponse(PrintWriter out, int[][] adjacencyMatrix, int numVertices) {
        out.println("{");
        out.println("\"adjacencyMatrix\": [");
        for (int i = 0; i < numVertices; i++) {
            out.print("[");
            for (int j = 0; j < numVertices; j++) {
                out.print(adjacencyMatrix[i][j]);
                if (j < numVertices - 1) {
                    out.print(", ");
                }
            }
            out.print("]");
            if (i < numVertices - 1) {
                out.println(",");
            } else {
                out.println();
            }
        }
        out.println("]");
        out.println("}");
    }
}