// package dip107;

import java.util.Scanner;
// import java.util.Random;
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

public class Main {
    public static void main(String[] args) {
        int rindas, kolonnas;

        Scanner sc = new Scanner(System.in);
        System.out.print("Row count: ");
        rindas = sc.nextInt();
        System.out.print("Column count: ");
        kolonnas = sc.nextInt();

        int labirints [][] = new int [rindas][kolonnas];
        int choice; // 1 - automātiski, 0 - manuāli
        System.out.println("Auto fill maze (1 - yes, 0 - no)? ");
        choice = sc.nextInt();

        if (choice == 0)
        {
            System.out.println("Input maze! (" + rindas + " rows un " + kolonnas + " columns)");
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

        if(choice == 1)
        {
            System.out.println();
            printLabyrinth(labirints); //tikai, ja ievada automatiksi
        }

        System.out.println();
        System.out.println("Method number (1 - brute force, 2 - ??, 3 - right hand rule): ");
        choice = sc.nextInt();
        sc.close();

        switch(choice)
        {
            case 1:
                String resultPath = bruteForce(labirints);
                System.out.println(resultPath);
                break;
            case 2:
                //smth
                break;
            case 3:
                String path = followRightHandRule(labirints);
                System.out.println("Results:");
                System.out.println(path);
                break;
            default:
                System.out.println("Error!");
                break;
        }
    }


    public static void printLabyrinth(int[][] labyrinth)
    {
        for (int i = 0; i < labyrinth.length; i++)
        {
            for (int j = 0; j < labyrinth[i].length; j++)
            {
                if (labyrinth[i][j] == 0) //System.out.print(labyrinth[i][j]);
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
                labyrinth[i][j] = (int)(Math.random()*10 % 2); //aizpilda ar 0 vai 1 pēc nejaušības
            }
        }
    }

    public static String followRightHandRule(int[][] labyrinth) {
        String path = "";
        int newLabyrinth[][] = new int[labyrinth.length+2][labyrinth[0].length+2];

        for(int i = 0; i < newLabyrinth.length; i++) {
            for(int j = 0; j < newLabyrinth[0].length; j++) {
                if (i == 0 || j == 0 || i == newLabyrinth.length-1 || j == newLabyrinth[0].length-1) {
                    newLabyrinth[i][j] = 1;
                }
                else {
                    newLabyrinth[i][j] = labyrinth[i-1][j-1];
                }
            }
        }

        int[] location = new int[2];
        location[0] = 1;
        location[1] = 1;

        int[][] directions = {
            {1, 0}, // Down // check left
            {0, 1}, // Right // check down
            {-1, 0}, // Up // check right
            {0, -1}, // Left // check up
        };
        int mode = 0;
        if (newLabyrinth[2][1] == 1){
            mode = 1;
        }

        int counter = 0;

        while (counter < 2) {
            int currentX = location[0];
            int currentY = location[1];

            // Check if the current position is the exit
            if (currentX == newLabyrinth.length - 2 && currentY == newLabyrinth[0].length - 2) {
                path = path + "(" + (currentX-1) + "," + (currentY-1) + ") ";
                break;
            }

            if (currentX == 1 && currentY == 1) {
                counter++;
                if (counter == 2) {
                    path = "Exit not found!";
                    break;
                }
            }

            if(mode == 0) {
                if (newLabyrinth[currentX + directions[3][0]][currentY + directions[3][1]] == 0) {
                    location[0] += directions[3][0];
                    location[1] += directions[3][1];
                    path = path + "(" + (currentX-1) + "," + (currentY-1) + ") ";
                    mode = 3;
                }
                else if(newLabyrinth[currentX + directions[mode][0]][currentY + directions[mode][1]] == 0) {
                    location[0] += directions[mode][0];
                    location[1] += directions[mode][1];
                    path = path + "(" + (currentX-1) + "," + (currentY-1) + ") ";
                }
                else{
                    mode = (mode + 1)%4;
                }
            }
            else {
                if (newLabyrinth[currentX + directions[mode-1][0]][currentY + directions[mode-1][1]] == 0) {
                    location[0] += directions[mode-1][0];
                    location[1] += directions[mode-1][1];
                    path = path + "(" + (currentX-1) + "," + (currentY-1) + ") ";
                    mode -= 1;
                }
                else if(newLabyrinth[currentX + directions[mode][0]][currentY + directions[mode][1]] == 0) {
                    location[0] += directions[mode][0];
                    location[1] += directions[mode][1];
                    path = path + "(" + (currentX-1) + "," + (currentY-1) + ") ";
                }
                else{
                    mode = (mode + 1)%4;
                }
            }
            
        }

        // for (int i = 0; i < newLabyrinth.length; i++) {
        //     for (int j = 0; j < newLabyrinth[0].length; j++) {
        //         System.out.print(newLabyrinth[i][j] + " ");
        //     }
        //     System.out.println();
        // }


        return path;
    }

    public static String bruteForce(int[][] labyrinth)
    {
        String resultPath = "";
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

        ArrayList<Integer> path = new ArrayList<Integer>();
        int counter = 0;

        int[] location = new int[6];
        location[0] = 0;
        location[1] = 0;
        location[2] = -1;
        location[3] = -1;
        location[4] = -1;
        location[5] = -1;

        boolean found = false, wasContinue = false;
        String lastMove = "";
        while(true)
        {
            if(!wasContinue)
            {
                counter += 2;
                path.add(location[0]);
                path.add(location[1]);
                //System.out.println(path);
            }
            else
            {
                wasContinue = false;
            }

            if(lastMove.equals("left") && newLabyrinth[location[0]+1][location[1]-1+1] == 0) //left 0
            {
                location[1] -= 1; //pa kreisi
            }
            else if(lastMove.equals("left") && newLabyrinth[location[0]+1][location[1]-1+1] != 0) //left 1
            {
                lastMove = "wasLeft";
                wasContinue = true;
                continue;
            }
            else if(lastMove.equals("up") && newLabyrinth[location[0]-1+1][location[1]+1] == 0) //up 0
            {
                location[0] -= 1; //uz augšu
            }
            else if(lastMove.equals("up") && newLabyrinth[location[0]-1+1][location[1]+1] != 0) //up 1
            {
                lastMove = "wasUp";
                wasContinue = true;
                continue;
            }
            else
            {
                if(newLabyrinth[location[0]+1+1][location[1]+1] == 0) //down 0
                {
                    if(lastMove.equals("wasUp"))
                    {
                        if(newLabyrinth[location[0]+1][location[1]+1+1] == 0) //right 0
                        {
                            for(int i = 0; i < counter; i += 2)
                            {
                                if(location[0] == path.get(i) && location[1]+1 == path.get(i + 1) && newLabyrinth[location[0]+1][location[1]-1+1] == 0) //left 0, right was
                                {
                                    location[1] -= 1; //pa kreisi
                                    lastMove = "left";
                                    wasContinue = true;
                                    break;
                                }
                            }

                            if(!wasContinue)
                            {
                                location[1] += 1; //pa labi
                                lastMove = "";
                            }
                            else
                            {
                                wasContinue = false;
                            }
                        }
                        else if(newLabyrinth[location[0]+1][location[1]-1+1] == 0) //left 0
                        {
                            location[1] -= 1; //pa kreisi
                            lastMove = "left";
                        }
                        else
                        {
                            location[0] += 1; //uz leju
                            lastMove = "";
                        }
                    }
                    else
                    {
                        for(int i = 0; i < counter; i += 2)
                        {
                            if(location[0]+1 == path.get(i) && location[1] == path.get(i+1)) //down was
                            {
                                wasContinue = true;
                                break;
                            }
                        }
                        if(wasContinue)
                        {
                            if(newLabyrinth[location[0]-1+1][location[1]+1] == 0) //up 0
                            {
                                location[0] -= 1; //uz augšu
                                lastMove = "up";
                            }
                            else
                            {
                                location[0] += 1; //uz leju
                                lastMove = "";
                            }
                            wasContinue = false;
                        }
                        else
                        {
                            location[0] += 1; //uz leju
                            lastMove = "";
                        }
                    }
                }
                else if(newLabyrinth[location[0]+1][location[1]+1+1] == 0) //right 0
                {
                    if(lastMove.equals("wasLeft"))
                    {
                        if(newLabyrinth[location[0]-1+1][location[1]+1] == 0) //up 0
                        {
                            location[0] -= 1; //uz augšu
                            lastMove = "up";
                        }
                        else
                        {
                            location[1] += 1; //pa labi
                            lastMove = "";
                        }
                    }
                    else
                    {
                        for(int i = 0; i < counter; i += 2)
                        {
                            if(location[0] == path.get(i) && location[1]+1 == path.get(i + 1) && newLabyrinth[location[0]+1][location[1]-1+1] == 0) //left 0, right was
                            {
                                location[1] -= 1; //pa kreisi
                                lastMove = "left";
                                wasContinue = true;
                                break;
                            }
                        }

                        if(!wasContinue)
                        {
                            location[1] += 1; //pa labi
                            lastMove = "";
                        }
                        else
                        {
                            wasContinue = false;
                        }
                    }
                }
                else if(newLabyrinth[location[0]+1][location[1]-1+1] == 0) //left 0
                {
                    if(location[0] == location[4] && location[1]-1 == location[5] && newLabyrinth[location[0]-1+1][location[1]+1] == 0) //up 0
                    {
                        location[0] -= 1; //uz augšu
                        lastMove = "up";
                    }
                    else
                    {
                        location[1] -= 1; //pa kreisi
                        lastMove = "left";
                    }
                }
                else if(newLabyrinth[location[0]-1+1][location[1]+1] == 0) //up 0
                {
                    location[0] -= 1; //uz augšu
                    lastMove = "up";
                }
                else
                {
                    break;
                }
            }

            if(location[0] == labyrinth.length-1 && location[1] == labyrinth[0].length-1)
            {
                found = true;
                break;
            }

            for(int i = 0; i < counter; i += 2)
            {
                if(location[0] == path.get(i) && location[1] == path.get(i+1))
                {
                    newLabyrinth[location[2]+1][location[3]+1] = 2;
                    location[0] = 0;
                    location[1] = 0;
                    location[2] = -1;
                    location[3] = -1;
                    location[4] = -1;
                    location[5] = -1;
                    lastMove = "";
                    path.clear();
                    counter = 0;
                    wasContinue = true;
                    break;
                }
            }

            if(!wasContinue)
            {
                if(location[0] == location[4] && location[1] == location[5])
                {
                    newLabyrinth[location[2]+1][location[3]+1] = 2;
                    location[0] = 0;
                    location[1] = 0;
                    location[2] = -1;
                    location[3] = -1;
                    location[4] = -1;
                    location[5] = -1;
                    lastMove = "";
                    path.clear();
                    counter = 0;
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
            else
            {
                wasContinue = false;
            }
        }

        if(found)
        {
            //System.out.println("Congrats!");
            for(int i = 0; i < counter; i += 2)
            {
                resultPath += "(" + path.get(i) + "," + path.get(i+1) + ") ";
            }
            resultPath += "(" + (labyrinth.length - 1) + "," + (labyrinth[0].length - 1) + ")";
        }
        else
        {
            //System.out.println("Exit not found!");
            resultPath = "Exit not found!";
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
        return resultPath;
    }

    static void createEdges(int[][] labyrinth)
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
