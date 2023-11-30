import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



class LabyrinthGraph {
    private Map<Integer, List<Integer>> adjacencyList;

    public LabyrinthGraph() {
        adjacencyList = new HashMap<>();
    }

    public void addEdge(int vertex1, int vertex2) {
        adjacencyList.computeIfAbsent(vertex1, k -> new ArrayList<>()).add(vertex2);
        adjacencyList.computeIfAbsent(vertex2, k -> new ArrayList<>()).add(vertex1);
    }

    public Map<Integer, List<Integer>> getAdjacencyList() {
        return adjacencyList;
    }
}




public class Main
{
    public static void main(String[] args)
    {
        int rindas, kolonnas;
        Scanner sc = new Scanner(System.in);
        System.out.print("Lūdzu, ievediet labirinta garumu: ");
        rindas = sc.nextInt();
        System.out.print("Lūdzu, ievediet labirinta augstumu: ");
        kolonnas = sc.nextInt();
        int labirints [][] = new int [rindas][kolonnas];
        int choice; // 1 - manuāli, 0 - automātiski
        System.out.println("Vai vēlaties aizpildīt labirintu manuāli? (1 - jā, 0 - nē)");
        choice = sc.nextInt();
        if (choice == 1)
        {
            System.out.println("Lūdzu, aizpildiet masīvu! (" + rindas + " rindas un " + kolonnas + " kolonnas)");
            for (int i = 0; i < rindas; i++)
            {
                for (int j = 0; j < kolonnas; j++)
                {
                    //System.out.println("Lūdzu, ievadiet " + (i + 1) + ". rindas " + (j + 1) + ". kolonnas vērtību (1 - siena, 0 - ceļš)");
                    labirints[i][j] = sc.nextInt();
                }
            }
        }
        else
        {
            randomAizpilde(labirints);
        }
        labirints[0][0] = 0;
        labirints[rindas-1][kolonnas-1] = 0;
        if(choice == 0)
        {
            System.out.println();
            printLabyrinth(labirints); //tikai, ja ievada automatiksi
        }

        System.out.println();
        System.out.println("Lūdzu, izvēlieties algoritmu (1 - ??, 2 - brute force, 3 - ??)");
        choice = sc.nextInt();
        if(choice == 1)
        {
            //smth
        }
        else if(choice == 2)
        {
            bruteForce(labirints);
        }
        else if(choice == 3)
        {
            //smth
        }
        else
        {
            System.out.println("Error!");
        }
        sc.close();
    }


    public static void printLabyrinth(int[][] labyrinth)
    {
        for (int i = 0; i < labyrinth.length; i++)
        {
            for (int j = 0; j < labyrinth[i].length; j++)
            {
                if (labyrinth[i][j] == 0)
                {
                    System.out.print("0 "); // Entrance, exit, or open path
                }
                else
                {
                    System.out.print("1 "); // Wall
                }
            }
            System.out.println();
        }
    }

    public static void randomAizpilde(int[][] labyrinth)
    {
        for (int i = 0; i < labyrinth.length; i++)
        {
            for (int j = 0; j < labyrinth[i].length; j++)
            {
                labyrinth[i][j] = (int)(Math.random()*10 % 2);
            }
        }
    }

    public static void bruteForce(int[][] labyrinth)
    {
        System.out.println();
        System.out.println("Results:");
        int newLabyrinth[][] = new int[labyrinth.length+2][labyrinth[0].length+2];
        for(int i = 0; i < newLabyrinth.length; i++)
        {
            for(int j = 0; j < newLabyrinth[0].length; j++)
            {
                newLabyrinth[i][j] = 1;
            }
        }
        for(int i = 1; i < newLabyrinth.length-1; i++)
        {
            for(int j = 1; j < newLabyrinth[0].length-1; j++)
            {
                newLabyrinth[i][j] = labyrinth[i-1][j-1];
            }
        }

        int[] location = new int[6];
        location[0] = 0;
        location[1] = 0;
        location[2] = -1;
        location[3] = -1;
        location[4] = -1;
        location[5] = -1;

        boolean found = false;
        while(true)
        {
            //System.out.println(location[0] + " " + location[1]);
            if(newLabyrinth[location[0]+1+1][location[1]+1] == 0 && location[0]+1 < labyrinth.length)
            {
                location[0] += 1; //uz leju
            }
            else if(newLabyrinth[location[0]+1][location[1]+1+1] == 0 && location[1]+1 < labyrinth[location[0]].length)
            {
                location[1] += 1; //pa labi
            }
            else if(newLabyrinth[location[0]+1][location[1]-1+1] == 0 && location[1]-1 < labyrinth[location[0]].length)
            {
                location[1] -= 1; //pa kreisi
            }
            else if(newLabyrinth[location[0]-1+1][location[1]+1] == 0 && location[0]-1 < labyrinth.length)
            {
                location[0] -= 1; //uz augšu
            }
            else
            {
                break;
            }

            if(location[0] == labyrinth.length-1 && location[1] == labyrinth[0].length-1)
            {
                //System.out.println("Congrats!");
                found = true;
                location[0] = 0;
                location[1] = 0;
                break;
            }

            if(location[0] == location[4] && location[1] == location[5])
            {
                newLabyrinth[location[2]+1][location[3]+1] = 2;
                location[0] = 0;
                location[1] = 0;
                location[2] = -1;
                location[3] = -1;
                location[4] = -1;
                location[5] = -1;
            }
            else if(location[2] == -1 && location[3] == -1)
            {
                location[2] = location[0];
                location[3] = location[1];
            }
            else
            {
                location[4] = location[2];
                location[5] = location[3];

                location[2] = location[0];
                location[3] = location[1];
            }
        }

        if(found)
        {
            while(true)
            {
                System.out.print("(" + location[0] + "," + location[1] + ") ");
                if(newLabyrinth[location[0]+1+1][location[1]+1] == 0 && location[0]+1 < labyrinth.length)
                {
                    location[0] += 1; //uz leju
                }
                else if(newLabyrinth[location[0]+1][location[1]+1+1] == 0 && location[1]+1 < labyrinth[location[0]].length)
                {
                    location[1] += 1; //pa labi
                }
                else if(newLabyrinth[location[0]+1][location[1]-1+1] == 0 && location[1]-1 < labyrinth[location[0]].length)
                {
                    location[1] -= 1; //pa kreisi
                }
                else if(newLabyrinth[location[0]-1+1][location[1]+1] == 0 && location[0]-1 < labyrinth.length)
                {
                    location[0] -= 1; //uz augšu
                }

                if(location[0] == labyrinth.length-1 && location[1] == labyrinth[0].length-1)
                {
                    System.out.print("(" + location[0] + "," + location[1] + ") ");
                    break;
                }
            }
        }
        else
        {
            System.out.println("Exit not found!");
        }
        /*
        for(int i = 1; i < newLabyrinth.length-1; i++)
        {
            for(int j = 1; j < newLabyrinth[0].length-1; j++)
            {
                System.out.print(newLabyrinth[i][j] + " ");
            }
            System.out.println();
        }
        */
    }



        public static void createEdges(int[][] labyrinth)
    {
        int rows = labyrinth.length;
        int cols = labyrinth[0].length;
        LabyrinthGraph graph = new LabyrinthGraph();
        for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (labyrinth[i][j] == 1) {
                        int vertex = i * cols + j;

                        if (j + 1 < cols && labyrinth[i][j + 1] == 1) {
                            graph.addEdge(vertex, i * cols + (j + 1));
                        }
                        if (j - 1 >= 0 && labyrinth[i][j - 1] == 1) {
                            graph.addEdge(vertex, i * cols + (j - 1));
                        }
                        if (i + 1 < rows && labyrinth[i + 1][j] == 1) {
                            graph.addEdge(vertex, (i + 1) * cols + j);
                        }
                        if (i - 1 >= 0 && labyrinth[i - 1][j] == 1) {
                            graph.addEdge(vertex, (i - 1) * cols + j);
                        }
                    }
                }
            }
        Map<Integer, List<Integer>> adjacencyList = graph.getAdjacencyList();
        for (Map.Entry<Integer, List<Integer>> entry : adjacencyList.entrySet()) {
            System.out.println("Vertex " + entry.getKey() + " is connected to vertices " + entry.getValue());
        }

        
    }
}
