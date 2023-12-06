import java.util.*;

public class mock {

    private static class LabyrinthGraph {
        private Map<Integer, Set<VertexInfo>> adjacencyList;

        public LabyrinthGraph() {
            this.adjacencyList = new HashMap<>();
        }

        public void addEdge(int v, int w, int steps) {
            adjacencyList.computeIfAbsent(v, k -> new HashSet<>()).add(new VertexInfo(w, steps));
        }

        private static class VertexInfo {
            private int vertex;
            private int steps;

            public VertexInfo(int vertex, int steps) {
                this.vertex = vertex;
                this.steps = steps;
            }
        }
    }

    public static void main(String[] args) {
        int[][] labyrinth = {
                {1, 0, 1, 1, 1},
                {1, 0, 1, 0, 1},
                {1, 0, 1, 0, 1},
                {1, 0, 1, 1, 0},
                {1, 1, 1, 0, 1}
        };

        findShortestPath(labyrinth);
    }

    static void findShortestPath(int[][] labyrinth) {
        int rows = labyrinth.length;
        int cols = labyrinth[0].length;

        LabyrinthGraph graph = new LabyrinthGraph();

        // Add edges between adjacent cells
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (labyrinth[i][j] == 1) {
                    if (j + 1 < cols && labyrinth[i][j + 1] == 1) {
                        int vertex1 = i * cols + j;
                        int vertex2 = i * cols + (j + 1);
                        graph.addEdge(vertex1, vertex2, 1);
                        graph.addEdge(vertex2, vertex1, 1);
                    }
                    if (i + 1 < rows && labyrinth[i + 1][j] == 1) {
                        int vertex1 = i * cols + j;
                        int vertex2 = (i + 1) * cols + j;
                        graph.addEdge(vertex1, vertex2, 1);
                        graph.addEdge(vertex2, vertex1, 1);
                    }
                }
            }
        }

        
        int[] distances = new int[rows * cols];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[0] = 0;

        int[] prevVertices = new int[rows * cols];
        Arrays.fill(prevVertices, -1);

        PriorityQueue<Integer> queue = new PriorityQueue<>(Comparator.comparingInt(vertex -> distances[vertex]));
        queue.offer(0);

        while (!queue.isEmpty()) {
            int currentVertex = queue.poll();

            for (LabyrinthGraph.VertexInfo neighborInfo : graph.adjacencyList.getOrDefault(currentVertex, Collections.emptySet())) {
                int neighbor = neighborInfo.vertex;
                int weight = neighborInfo.steps;

                if (distances[currentVertex] + weight < distances[neighbor]) {
                    distances[neighbor] = distances[currentVertex] + weight;
                    prevVertices[neighbor] = currentVertex;
                    queue.offer(neighbor);
                }
            }
        }

        printShortestPath(prevVertices, rows, cols, distances[rows * cols - 1]);
    }

    static void printShortestPath(int[] prevVertices, int rows, int cols, int shortestPathLength) {
        List<String> path = new ArrayList<>();
        int currentVertex = rows * cols - 1;

        while (currentVertex != -1) {
            int i = currentVertex / cols;
            int j = currentVertex % cols;
            path.add("(" + i + ", " + j + ")");
            currentVertex = prevVertices[currentVertex];
        }

        Collections.reverse(path);

        if (shortestPathLength == Integer.MAX_VALUE) {
            System.out.println("No path found");
            return;
        }
        System.out.println("Shortest Path found");
        System.out.println("Length of the Shortest Path: " + shortestPathLength);
        System.out.println("Path: " + String.join(" -> ", path));
    }
}
