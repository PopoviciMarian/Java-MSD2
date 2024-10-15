import requests

def invoke_servlet(num_vertices, num_edges): # type: ignore
    
    base_url = "http://localhost:8080/demo2_war_exploded/GraphServlet"
    
    params = { # type: ignore
        'numVertices': num_vertices,
        'numEdges': num_edges,
        "json": "true"
    }
    try:
        response = requests.get(base_url, params=params) # type: ignore
        if response.status_code == 200:
            print("Response from the servlet:")
            print(response.text)
        else:
            print(f"Error: Received status code {response.status_code}")
    
    except requests.exceptions.RequestException as e:
        print(f"An error occurred: {e}")

def main():
    num_vertices = int(input("Enter the number of vertices: "))
    num_edges = int(input("Enter the number of edges: "))
    invoke_servlet(num_vertices, num_edges)

if __name__ == "__main__":
    main()
