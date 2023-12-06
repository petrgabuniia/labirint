 import java.util.*;

public class dfs {

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

            @Override
            public boolean equals(Object obj) {
                if (this == obj) return true;
                if (obj == null || getClass() != obj.getClass()) return false;
                VertexInfo that = (VertexInfo) obj;
                return vertex == that.vertex && steps == that.steps;
            }

            @Override
            public int hashCode() {
                return Objects.hash(vertex, steps);
            }
        }
    }

    public static void main(String[] args) {
        int[][] labyrinth = {
                {1, 0, 1, 1, 1},
                {1, 0, 1, 0, 1},
                {1, 0, 1, 0, 1},
                {1, 0, 1, 0, 1},
                {1, 1, 1, 1, 1}
        };

        createEdges(labyrinth);
    }

    static void createEdges(int[][] labyrinth) {
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

        // Perform DFS to find the route to the bottom-left cell
        boolean[][] visited = new boolean[rows][cols];
        List<String> path = new ArrayList<>();
        int minSteps = dfs(0, 0, labyrinth, graph, visited, rows, cols, rows - 1, cols - 1, 0, path);

        if (minSteps == Integer.MAX_VALUE) {
            System.out.println("No path found");
        } else {
            System.out.println("Path found");
            System.out.println("Minimum steps: " + minSteps);
            System.out.println("Path: " + String.join(" -> ", path));
        }
    }

    static int dfs(int i, int j, int[][] labyrinth, LabyrinthGraph graph, boolean[][] visited, int rows, int cols, int targetI, int targetJ, int steps, List<String> path) {
        if (i < 0 || i >= rows || j < 0 || j >= cols || visited[i][j] || labyrinth[i][j] == 0) {
            return Integer.MAX_VALUE; // Return a large value for unreachable cells
        }

        int vertex = i * cols + j;
        visited[i][j] = true;

        if (i == targetI && j == targetJ) {
            // Reached the bottom-left cell
            path.add("(" + i + ", " + j + ")");
            return steps;
        }

        int minSteps = Integer.MAX_VALUE;

        // Explore all possible directions: up, down, left, right
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int[] dir : directions) {
            int nextI = i + dir[0];
            int nextJ = j + dir[1];
            int nextVertex = nextI * cols + nextJ;

            if (nextI >= 0 && nextI < rows && nextJ >= 0 && nextJ < cols && labyrinth[nextI][nextJ] == 1 && !visited[nextI][nextJ]) {
                graph.addEdge(vertex, nextVertex, steps + 1);
                List<String> newPath = new ArrayList<>(path);
                newPath.add("(" + nextI + ", " + nextJ + ")");
                int neighborSteps = dfs(nextI, nextJ, labyrinth, graph, visited, rows, cols, targetI, targetJ, steps + 1, newPath);
                if (neighborSteps < minSteps) {
                    minSteps = neighborSteps;
                    path.clear();
                    path.addAll(newPath);
                }
            }
        }

        return minSteps;
    }
}
