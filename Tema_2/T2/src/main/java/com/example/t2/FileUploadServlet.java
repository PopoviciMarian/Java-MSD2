package com.example.t2;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.graph4j.Graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import org.graph4j.*;

@MultipartConfig
@WebServlet(name = "FileUploadServlet", value = "/FileUploadServlet")
public class FileUploadServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        // Get the reCAPTCHA response token from the request
        String gRecaptchaResponse = request.getParameter("g-recaptcha-response");

        // Verify the reCAPTCHA response
        boolean isCaptchaVerified = verifyCaptcha(gRecaptchaResponse);

        System.out.println("isCaptchaValid = " + isCaptchaVerified);


        if (!isCaptchaVerified) {
            // If CAPTCHA verification fails, return an error response
            response.getWriter().write("CAPTCHA verification failed. Please try again.");
            return;
        }



        // Get the uploaded file part
        Part filePart = request.getPart("file");

        // Initialize a collection to hold the file lines
        List<String> lines = new ArrayList<>();

        // Read the uploaded file content
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(filePart.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }


        Graph graph = parseFileDimacsGraph(lines);
        Map<Edge,Integer> edgeColors = greedyEdgeColoring(graph);

        // Store the lines into session
        HttpSession session = request.getSession();
        session.setAttribute("fileLines", lines);
        session.setAttribute("edgeColors",edgeColors);


        // Redirect to the result.jsp to display the shuffled lines
        response.sendRedirect("result.jsp");
    }

    private boolean verifyCaptcha(String gRecaptchaResponse) throws IOException {
        if (gRecaptchaResponse == null || gRecaptchaResponse.isEmpty()) {
            return false;
        }

        // Prepare verification request to Google's reCAPTCHA service
        String verifyUrl = "https://www.google.com/recaptcha/api/siteverify";
        URL url = new URL(verifyUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        String postParams = "secret=" + "TOKEN" + "&response=" + gRecaptchaResponse;
        OutputStream os = connection.getOutputStream();
        os.write(postParams.getBytes());
        os.flush();
        os.close();

        // Read the verification response
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // Parse the JSON response from Google
        JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();
        return jsonObject.get("success").getAsBoolean();
    }

    private Graph parseFileDimacsGraph(List<String> lines) {
        int vertices = 0;
        List<int[]> edges = new ArrayList<>();

        for (String line : lines) {
            if (line.startsWith("c")){
                continue;
            }
            else if (line.startsWith("p")) {
                String[] parts = line.split(" ");
                vertices = Integer.parseInt(parts[2]);
            }
            else if (line.startsWith("e")) {
                String[] parts = line.split(" ");
                int u = Integer.parseInt(parts[1]) - 1;
                int v = Integer.parseInt(parts[2]) - 1;
                edges.add(new int[]{u, v});
            }
        }

        Graph graph = GraphBuilder.numVertices(vertices).buildGraph();
        for (int[] edge : edges) {
            graph.addEdge(edge[0], edge[1]);
        }

        return graph;
    }

    private Map<Edge, Integer> greedyEdgeColoring(Graph graph){
        Map<Edge, Integer> edgeColors = new HashMap<>();

        boolean[] available = new boolean[graph.edges().length];
        edgeColors.put(graph.edges()[0], 0);

        for(int i = 1; i< graph.edges().length; i++){

            Edge currentEdge = graph.edges()[i];
            Arrays.fill(available, false);

            //check adjacent edges for same color
            for (Edge adjacentEdge : graph.edgesOf(currentEdge.source())) {
                if (edgeColors.containsKey(adjacentEdge)) {
                    available[edgeColors.get(adjacentEdge)] = true;
                }
            }
            for (Edge adjacentEdge : graph.edgesOf(currentEdge.target())) {
                if (edgeColors.containsKey(adjacentEdge)) {
                    available[edgeColors.get(adjacentEdge)] = true;
                }
            }

            int color;
            for (color = 0; color < graph.edges().length; color++) {
                if (available[color] == false) {
                    break;
                }
            }
            edgeColors.put(currentEdge, color);
        }
        return edgeColors;

    }
}