import java.util.Scanner;
import java.util.Random;

public class labirint1 {

    public static void main(String[] args) {
        int R, L;
        Scanner sc = new Scanner(System.in);
        System.out.println("Lūdzu, ievediet labirinta garumu");
        R = sc.nextInt();
        System.out.println("Lūdzu, ievediet labirinta augstumu");
        L = sc.nextInt();
        int labirints [][] = new int [R][L];
        int filling; // 1 - manuāli, 0 - automātiski
        System.out.println("Vai vēlaties aizpildīt labirintu manuāli? (1 - jā, 0 - nē)");
        filling = sc.nextInt();
        if (filling == 1)
        {
            for (int i = 0; i < R; i++)
            {
                for (int j = 0; j < L; j++)
                {
                    System.out.println("Lūdzu, ievadiet " + (i + 1) + ". rindas " + (j + 1) + ". kolonnas vērtību (1 - siena, 0 - ceļš)");
                    labirints[i][j] = sc.nextInt();
                }
            }
        }
        else
        {
            for (int i = 0; i < R; i++)
            {
                for (int j = 0; j < L; j++)
                {
                    labirints[i][j] = (int)(Math.random()*10 % 2);
                }
            }
        }
        labirints[0][0] = 0;
        labirints[R-1][L-1] = 0;
        printLabyrinth(labirints);
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
}
