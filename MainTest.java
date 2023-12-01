import org.junit.Test;
import static org.junit.Assert.assertEquals;




public class MainTest {

    @Test //Tomass
    public void testBruteForce1() {
        Main main = new Main();
        int[][] labyrinth = {
            {0, 0, 0},
            {1, 1, 0}
        };
        
        assertEquals("(0,0) (0,1) (0,2) (1,2)", main.bruteForceTest(labyrinth)); // expected value, actual value
    }

    @Test //Tomass
    public void testBruteForce2() { 
        Main main = new Main();
        int[][] labyrinth = {
            {0, 0, 0, 0},
            {1, 1, 0, 1},
            {1, 1, 0, 0},
            {1, 1, 1, 0}
        };
        
        assertEquals("(0,0) (0,1) (0,2) (1,2) (2,2) (2,3) (3,3)", main.bruteForceTest(labyrinth)); 
    }

    @Test //Tomass
    public void testBruteForce3() { 
        Main main = new Main();
        int[][] labyrinth = {
            {0, 0, 0, 0},
            {1, 1, 1, 0},
            {0, 1, 0, 0},
            {0, 0, 0, 1},
            {1, 0, 1, 0}
        };
        
        assertEquals("Exit not found!", main.bruteForceTest(labyrinth)); 
    }

    @Test //Tomass
    public void testBruteForce4() { 
        Main main = new Main();
        int[][] labyrinth = {
            {0, 1, 0, 0, 0},
            {0, 1, 0, 1, 0},
            {0, 0, 0, 1, 0},
            {1, 0, 0, 1, 0},
            {0, 0, 1, 1, 0}
        };
        
        assertEquals("(0,0) (1,0) (2,0) (2,1) (2,2) (1,2) (0,2) (0,3) (0,4) (1,4) (2,4) (3,4) (4,4)", main.bruteForceTest(labyrinth)); 
    }
}