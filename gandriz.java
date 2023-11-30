import java.util.Random;
import java.util.Stack;





public class gandriz {

    public static void main(String[] args) {
        int rows = 5; // Specify the number of rows
        int cols = 5; // Specify the number of columns

        boolean[][] labyrinth = generateLabyrinth(rows, cols);

        // Print the generated labyrinth
        printLabyrinth(labyrinth);
    }

    public static boolean[][] generateLabyrinth(int rows, int cols) {
        Random random = new Random();

        // Create a 2D array for the labyrinth
        boolean[][] labyrinth = new boolean[rows][cols];

        // Initialize the labyrinth with walls
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                labyrinth[i][j] = false; // Wall
            }
        }

        // Set the entrance (top left corner)
        labyrinth[0][0] = true;

        // Set the exit (bottom right corner)
        labyrinth[rows - 1][cols - 1] = true;

        // Generate path using depth-first search
        generatePath(labyrinth, 0, 0, random);

        // Ensure connectivity
        ensureConnectivity(labyrinth, random);

        // Add some additional walls while maintaining connectivity
        addWalls(labyrinth, random);

        return labyrinth;
    }

    private static void generatePath(boolean[][] labyrinth, int row, int col, Random random) {
        Stack<int[]> stack = new Stack<>();
        stack.push(new int[]{row, col});

        while (!stack.isEmpty()) {
            int[] current = stack.pop();
            row = current[0];
            col = current[1];
            labyrinth[row][col] = true; // Mark the cell as part of the path

            // Shuffle the directions
            int[] directions = {0, 1, 2, 3};
            shuffleArray(directions, random);

            // Visit neighbors in shuffled order
            for (int i = 0; i < 4; i++) {
                int newRow = row + (directions[i] == 0 ? -2 : (directions[i] == 1 ? 2 : 0));
                int newCol = col + (directions[i] == 2 ? -2 : (directions[i] == 3 ? 2 : 0));

                if (newRow >= 0 && newRow < labyrinth.length && newCol >= 0 && newCol < labyrinth[0].length && !labyrinth[newRow][newCol]) {
                    // Mark the wall between the cells as open
                    labyrinth[newRow + (directions[i] == 0 ? 1 : (directions[i] == 1 ? -1 : 0))]
                            [newCol + (directions[i] == 2 ? 1 : (directions[i] == 3 ? -1 : 0))] = true;

                    stack.push(new int[]{newRow, newCol});
                }
            }
        }
    }

    private static void ensureConnectivity(boolean[][] labyrinth, Random random) {
        int rows = labyrinth.length;
        int cols = labyrinth[0].length;

        boolean[][] visited = new boolean[rows][cols];
        Stack<int[]> stack = new Stack<>();

        stack.push(new int[]{0, 0});
        visited[0][0] = true;

        while (!stack.isEmpty()) {
            int[] current = stack.pop();
            int row = current[0];
            int col = current[1];

            int[] directions = {0, 1, 2, 3};
            shuffleArray(directions, random);

            for (int i = 0; i < 4; i++) {
                int newRow = row + (directions[i] == 0 ? -1 : (directions[i] == 1 ? 1 : 0));
                int newCol = col + (directions[i] == 2 ? -1 : (directions[i] == 3 ? 1 : 0));

                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols && !visited[newRow][newCol] && labyrinth[newRow][newCol]) {
                    stack.push(new int[]{newRow, newCol});
                    visited[newRow][newCol] = true;
                }
            }
        }

        // Open additional walls to ensure connectivity
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (!visited[i][j]) {
                    labyrinth[i][j] = true;
                }
            }
        }
    }

    private static void addWalls(boolean[][] labyrinth, Random random) {
        int rows = labyrinth.length;
        int cols = labyrinth[0].length;

        // Add some additional walls while maintaining connectivity
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (random.nextDouble() < 0.2 && labyrinth[i][j]) {
                    labyrinth[i][j] = false; // Set the cell as a wall
                }
            }
        }
    }

    private static void shuffleArray(int[] array, Random random) {
        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            // Swap array[i] and array[index]
            int temp = array[i];
            array[i] = array[index];
            array[index] = temp;
        }
    }

    public static void printLabyrinth(boolean[][] labyrinth) {
        for (int i = 0; i < labyrinth.length; i++) {
            for (int j = 0; j < labyrinth[i].length; j++) {
                if (labyrinth[i][j]) {
                    System.out.print("O"); // Entrance, exit, or open path
                } else {
                    System.out.print("X"); // Wall
                }
            }
            System.out.println();
        }
    }
}
