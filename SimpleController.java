import java.util.*;
/**
 * Embedded Game controller for the BlueJ environment.
 * 
 * @author William H. Hooper
 * @version 2005-04-20
 */
public class SimpleController
{
    Game game;
    ConsoleViewer viewer;
    
    public static void main(String[] args)
    {
        SimpleController sc = new SimpleController();
        sc.play();
    }
    
    /**
     * Begins a new game from the console.
     */
    public void play()
    {
        game = new Game();
        viewer = new ConsoleViewer(game);
        viewer.play();
    }
}
