

/**
 * The test class GameTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class GameTest extends junit.framework.TestCase
{
	private Game game1;

    /**
     * Default constructor for test class GameTest
     */
    public GameTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    protected void setUp()
    {
		game1 = new Game();
	}

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    protected void tearDown()
    {
    }

	public void testWelcome()
	{
	    System.out.println("\f");
		String message = game1.readMessages();
	    System.out.print(message + "> ");
		assertTrue(message.contains("Welcome"));
		assertFalse(game1.finished());
	}
	
	/**
	 * Issue a command and get the response.
	 * As a side effect, it prints its output on the terminal
	 * in the same form as a game dialog.
	 * @param cmd a command such as "help"
	 * @return the game output that results from the commmand.
	 */
	public String getResponse(String cmd)
	{
	    System.out.println(cmd);
		game1.processInput(cmd);
		String message = game1.readMessages();
	    System.out.print(message + "> ");
		return message;
	}
	
	/**
	 * Test whether a given command gets the expected response.
	 * This method only succeeds if the reply is received.
	 * As a side effect, it prints its output on the terminal
	 * in the same form as a game dialog.
	 * @param cmd a command such as "help"
	 * @param reply a portion of the expected response.
	 * It is sufficient to supply a unique word or phrase that
	 * appears in the output.
	 */
	public void testCommand(String cmd, String reply)
	{
		testCommand(cmd, reply, true);
	}
	
	/**
	 * Test whether a given command gets the expected response.
	 * This method only succeeds if the reply is received.
	 * As a side effect, it prints its output on the terminal
	 * in the same form as a game dialog.
	 * @param cmd a command such as "help"
	 * @param reply a portion of the expected response.
	 * @param match true if you want the response to match the reply, 
	 * false if you don't
	 * It is sufficient to supply a unique word or phrase that
	 * appears in the output.
	 */
	public void testCommand(String cmd, String reply, boolean match)
	{
		String message = getResponse(cmd);
		assertEquals(match, message.contains(reply));
	}

	public void testPrison()
	{
		testWelcome();
		testCommand("go north", "prison cells");
	}

	public void testNoDoor()
	{
		testWelcome();
		testCommand("go south", "no door");
	}

	public void testBye()
	{
		testWelcome();
	    testCommand("quit", "");
		assertTrue(game1.finished());
	}
	
	public void testItem()
	{
	    testWelcome();
	    testCommand("go north", "prison");
	    testCommand("go west", "torture");
	    testCommand("take torch", "torch");
	    testCommand("bag", "torch");
	}
}



