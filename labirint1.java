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
        boolean labirints [][] = new boolean [R][L];
        boolean filling; // true - manuāli, false - automātiski
        System.out.println("Vai vēlaties aizpildīt labirintu manuāli? (true/false)");
        filling = sc.nextBoolean();
        if (filling) {
            for (int i = 0; i < R; i++) {
                for (int j = 0; j < L; j++) {
                    System.out.println("Lūdzu, ievadiet " + i + ". rindas " + j + ". kolonnas vērtību (true/false)");
                    labirints[i][j] = sc.nextBoolean();
                }
            }
        } else {
            for (int i = 0; i < R; i++) {
                for (int j = 0; j < L; j++) {
                    labirints[i][j] = Math.random() < 0.3;
                }
            }
        }
        labirints[0][0] = true;
        labirints[R-1][L-1] = true;
        printLabyrinth(labirints);
        

        


    }

    public static void printLabyrinth(boolean[][] labyrinth) {
        for (int i = 0; i < labyrinth.length; i++) {
            for (int j = 0; j < labyrinth[i].length; j++) {
                if (labyrinth[i][j]) {
                    System.out.print("  "); // Entrance, exit, or open path
                } else {
                    System.out.print("X "); // Wall
                }
            }
            System.out.println();
        }
    }

}

        