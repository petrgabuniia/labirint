import java.util.*;

public class Mock {
    private static class LabyrinthGraph {
        private Map<Integer, List<VertexInfo>> adjacencyList;

        public LabyrinthGraph() {
            this.adjacencyList = new HashMap<>();
        }

        public void addEdge(int v, int w, int steps) {
            adjacencyList.computeIfAbsent(v, k -> new ArrayList<>()).add(new VertexInfo(w, steps));
            adjacencyList.computeIfAbsent(w, k -> new ArrayList<>()).add(new VertexInfo(v, steps));
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
                {1, 1, 0, 0},
                {1, 0, 1, 1},
                {1, 1, 1, 0},
                {0, 1, 1, 1}
        };

        int[][] targetCells = {
                {2, 0},
                {3, 3}
                // Add more target cells as needed
        };

        createEdges(labyrinth, targetCells);
    }

    static void createEdges(int[][] labyrinth, int[][] targetCells) {
        int rows = labyrinth.length;
        int cols = labyrinth[0].length;

        LabyrinthGraph graph = new LabyrinthGraph();
        boolean[][] visited = new boolean[rows][cols];

        for (int[] targetCell : targetCells) {
            int targetI = targetCell[0];
            int targetJ = targetCell[1];

            dfs(0, 0, labyrinth, graph, visited, rows, cols, 0, targetI, targetJ);
        }

        // Print information about the steps to reach all target cells
        for (Map.Entry<Integer, List<Mock.LabyrinthGraph.VertexInfo>> entry : graph.adjacencyList.entrySet()) {
            int vertex = entry.getKey();
            int i = vertex / cols;
            int j = vertex % cols;

            for (Mock.LabyrinthGraph.VertexInfo vertexInfo : entry.getValue()) {
                int targetI = vertexInfo.vertex / cols;
                int targetJ = vertexInfo.vertex % cols;

                System.out.println("To reach target cell (" + targetI + ", " + targetJ + ") from (" + i + ", " + j +
                        ") you should make " + vertexInfo.steps + " steps.");
            }
        }
    }

    static boolean dfs(int i, int j, int[][] labyrinth, LabyrinthGraph graph, boolean[][] visited, int rows, int cols, int steps, int targetI, int targetJ) {
        if (i < 0 || i >= rows || j < 0 || j >= cols || visited[i][j] || labyrinth[i][j] == 0) {
            return false;
        }

        int vertex = i * cols + j;
        visited[i][j] = true;

        if (i == targetI && j == targetJ) {
            // Reached a target cell, add information to the graph
            graph.addEdge(vertex, vertex, steps);
            return true;
        }

        if (dfs(i, j + 1, labyrinth, graph, visited, rows, cols, steps + 1, targetI, targetJ) ||
                dfs(i + 1, j, labyrinth, graph, visited, rows, cols, steps + 1, targetI, targetJ)) {
            return true;
        }

        return false;
    }
}
