import java.io. *;
import java.util. *;

/**
 * Plays the Game from a Console interface (i.e., a terminal window).
 * 
 * @author William H. Hooper
 * @version 2005-04-20
 */
public class ConsoleViewer
{
    private BufferedReader reader;
    private Game game;

    /**
     * Creates a new console game.
     * @param g a Game, the engine that actually 
     * processes all the input and 
     * generates all the output.
     */
    public ConsoleViewer(Game g)
    {
        reader = new BufferedReader(
            new InputStreamReader(System.in));
        game = g;
    }

    
    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {
        // display the welcome message
        System.out.println("\f");
        String message = game.readMessages();
        sendOutput(message);
        
        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
        while (! game.finished()) {
            String input = getInput();
            game.processInput(input);
            message = game.readMessages();
            sendOutput(message);
        }
        
        sendOutput("Good Bye.\n");
    }
    
    /**
     * Prompts for input and reads one line from the console.
     * @return a single line of input.
     */
    private String getInput()
    {
        String inputLine = "";   // will hold the full input line

        do {
            System.out.print("> ");     // print prompt
            try 
            {
                inputLine = reader.readLine();
            }
            catch(java.io.IOException exc) 
            {
                System.out.println (
                    "There was an error during reading: "
                    + exc.getMessage());
            }
        } while (inputLine.length() == 0);
        
        return inputLine;
    }
    
    /**
     * Prints output in the console window.
     * @param message the message to print.
     * If the message contains embedded newlines,
     * they will appear on successive lines of the console.
     */
    private void sendOutput(String message)
    {
        System.out.print(message);
    }
}
