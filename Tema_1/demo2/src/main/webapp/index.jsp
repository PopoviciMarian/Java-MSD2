<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Graph Adjacency Matrix</title>
</head>
<body>
<h1>Generate Random Graph</h1>
<form action="GraphServlet" method="get">
  <label for="numVertices">Number of Vertices:</label>
  <input type="number" id="numVertices" name="numVertices" required><br><br>

  <label for="numEdges">Number of Edges:</label>
  <input type="number" id="numEdges" name="numEdges" required><br><br>

  <input type="submit" value="Generate Graph">
</form>
</body>
</html>