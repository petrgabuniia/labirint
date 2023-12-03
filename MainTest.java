import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


public class MainTest {

    @Test //Tomass
    public void testBruteForce1() {
        int[][] labyrinth = {
            {0, 0, 0},
            {1, 1, 0}
        };
        
        assertEquals("(0,0) (0,1) (0,2) (1,2)", Main.bruteForce(labyrinth)); // expected value, actual value
    }

    @Test //Tomass
    public void testBruteForce2() {
        int[][] labyrinth = {
            {0, 0, 0, 0},
            {1, 1, 0, 1},
            {1, 1, 0, 0},
            {1, 1, 1, 0}
        };
        
        assertEquals("(0,0) (0,1) (0,2) (1,2) (2,2) (2,3) (3,3)", Main.bruteForce(labyrinth)); 
    }

    @Test //Tomass
    public void testBruteForce3() {
        int[][] labyrinth = {
            {0, 0, 0, 0},
            {1, 1, 1, 0},
            {0, 1, 0, 0},
            {0, 0, 0, 1},
            {1, 0, 1, 0}
        };
        
        assertEquals("Exit not found!", Main.bruteForce(labyrinth)); 
    }



    @Test //Tomass
    public void testBruteForce4() {
        int[][] labyrinth = {
            {0, 1, 0, 0, 0},
            {0, 1, 0, 1, 0},
            {0, 0, 0, 1, 0},
            {1, 0, 0, 1, 0},
            {0, 0, 1, 1, 0}
        };
        
        assertNotEquals("Exit not found!", Main.bruteForce(labyrinth)); 
    }
}
