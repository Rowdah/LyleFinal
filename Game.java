import java.util.*;
/**
 *  <p>
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * <p>
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * <p>
 *  This main class creates and initialises many others others: 
 *  it creates all rooms, 
 *  and creates the parser to process commands. 
 *  It also evaluates and
 *  executes the commands that the parser returns.
 * <p>
 *  Revised 2005-04-20 by William H. Hooper: 
 *  The game now receives all input via the processInput() method,
 *  and collects all output into the String messages.
 *  These changes allow the Game to be played by a test method,
 *  by a controller embedded in BlueJ,
 *  from the console,
 *  from an applet, etc.
 * 
 * @author  Logan Lowder (orginally based off code 
 * by Michael Kolling and David J. Barnes)
 * @version April 25, 2010
 */

public class Game 
{
    private Parser parser;
//    private Room currentRoom;
    private String messages;
    private boolean wantToQuit;
    //7.23
    private Stack<Room> stack;
//     private ArrayList<Item> playerItems;
    //7.29
    private final static Player player = new Player();
    
    /****************************************************************
     * Revisions 2005-04-20 by William H. Hooper
     * The following methods were deleted from the class:
     * 
     *      play() - Game play is now directed from the interface.
     *      getInput() - getting user input is now the job of 
     *      
     * The following public methods were added to the class:
     * 
     *      processInput() - sends output to the game
     *      readMessages() returns all the messages ready to be sent
     *      finished() - tells the calling interface 
     *          when the game is over.
     *      
     *      print(), println(), - these methods replace System.out.* 
     *          methods, and redirect output to a message string.
     *          The calling interface reads the messages spooled and 
     *          controls how they are displayed.
     ****************************************************************/
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        messages = "";
        wantToQuit = false;
        printWelcome();
        //7.23
        stack = new Stack<Room>();
//         playerItems = new ArrayList<Item>();
    }    
    
    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room entrance, prison, torture, armory, quarters, storage,
            diningHall, artRoom, atrium, library, stairwell, chambers;

        entrance = new Room(" are outside the fortress entrance.");
        prison = new Room(" are in a room full of prison cells. Luckily ye aren't in one.");
        torture = new Room(" are in what appears to be a torture chamber.");
        armory = new Room(" are in an armory with many rusted weapons.");
        quarters = new Room(" are in a room that looks like simple living quarters.");
        storage = new Room(" are in an ancient storage room. Ye seeth a ladder leading up above.");
        diningHall = new Room(" arrive in an ancient dining hall, strewn with many a cobbweb.");
        artRoom = new Room(" are in an art room full of paintings. The gastly figures make ye wet yeself.");
        atrium = new Room(" are in the atrium of yon fortress. Ye seeth the trap door in the ground. Yon fortress door has magically sealed.");
        library = new Room(" are in a library with many ancient tomes.");
        stairwell = new Room(" seeth a winding stairwell that seems to continue forever.");
        chambers = new Room("Ye are in yon wizard's chambers!");
        
        //Exits
        entrance.setExit("north", prison);
        
        prison.setExit("north", quarters);
        prison.setExit("east", armory);
        prison.setExit("west", torture);
        
        torture.setExit("east", prison);
        
        armory.setExit("north", storage);
        armory.setExit("west", prison);
        
        quarters.setExit("south", prison);
        quarters.setExit("east", storage);
        
        storage.setExit("south", armory);
        storage.setExit("west", quarters);
        storage.setExit("up", diningHall);
        
        diningHall.setExit("south", artRoom);
        diningHall.setExit("down", storage);
        
        artRoom.setExit("north", diningHall);
        artRoom.setExit("west", atrium);
        
        atrium.setExit("east", artRoom);
        atrium.setExit("west", library);
        atrium.setExit("north", stairwell);
        
        stairwell.setExit("south", atrium);
        stairwell.setExit("up", chambers);
        
        library.setExit("east", atrium);
        
        //Items
        armory.placeItem("sword", 10, "Ye seeth a single sword without rust, lying on yonder ground.");
        armory.placeItem("key", 1, "Ye seeth a shiny key on yonder ground.");
        torture.placeItem("torch", 2, "Ye seeth a torch that seems to be lit magically.");
        quarters.placeItem("scroll", 1, "Ye seeth an olde scroll lying on yonder ground.");
        artRoom.placeItem("bag", 0, "Ye seeth a bag much larger than thine own.");
        library.placeItem("book", 3, "Ye seeth a book on yonder shelf sticking out.");
        
        //Locked rooms
        torture.lockRoom();
        
        //Dark rooms
        storage.makeDark();
        diningHall.makeDark();
        artRoom.makeDark();
        library.makeDark();
        
        player.setCurrentRoom(entrance);
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        println("Welcome to the land of Lyle");
        println("Ye are a courageous villager, sick of the monotony of shoveling dung and such...");
        println("The legends of old speakth of a hidden treasure, deep within yon fortress...");
        println("None have ventured into the depths of yon abandoned fortress in many a winter,");
        println("and many lost their lives before then.");
        println("Do ye have the courage to findth it?");
        println("Type 'help' if ye needth help.");
        println();
        printLocationInfo();
    }
    
    
    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        println("Ye are at yon fortress. The treasure awaith... maybe...");
        println("Doest thou havest not the courage to continueth?");
        println("Thine command words art:");
        print (parser.getCommands());
        println();
    }

    private void printLocationInfo()
    {
        if(player.getCurrentRoom().checkIfDark() && !player.hasItem("torch")) {
            println("Ye cannot see anything. 'Tis too dark.");
            println();
            return;
        }
        if(player.getCurrentRoom().getDescription().contains("chambers")) {
            println(player.getCurrentRoom().getDescription());
            println("Yon wizard speaks in a thundering voice:");
            println("How dare ye enterth my sacred fortress?!");
            println("Ye shall die for thine transgressions!");
            println();
            return;
        }
        else {
        println(player.getCurrentRoom().getLongDescription());
        println();
        }
    }

    /**
     * Given a command, process (that is: execute) the command.
     * If this command ends the game, true is returned, otherwise false is
     * returned.
     */
    private void processCommand(Command command) 
    {
        String commandWord = command.getCommandWord();
        if(player.getCurrentRoom().getDescription().contains("chambers") 
                && !commandWord.equals("read")) {
            println();
            println("Yon wizard of yon dungeon uses firey-bolt!");
            println("Yon wizard's firey-bolt deals 12409 dmg.");
            println("'Tis super effective.");
            println("Ye art dead.");
            println();
            println("Thank ye for playing.");
            wantToQuit = true;
            return;
        }
        if(player.getCurrentRoom().getDescription().contains("chambers") && commandWord.equals("read")
                    && command.getSecondWord().equals("book")) {
            println();
            println("Yon wizard of yon dungeon shoot a firey-bolt at ye,");
            println("but ye narrowly dodge it.");
            println("As yon wizard is powering up his mighty staff,");
            println("ye read an incantation from the book:");
            println("Smidgen pidgeon, orc's cork, devil's lazy eye!");
            println("Yon wizard screams in torment and falls to the ground!");
            println();
            println("Ye have defeated yon wizard of yon dungeon and return to the");
            println("people of Lyle victorious, and prized with yon dungeon's");
            println("hidden treasure.");
            println("Well done!");
            wantToQuit = true;
            return;
        }
        println();
        if(command.isUnknown()) {
            println("I don't know what ye meanth...");
            println();
            return;
        }
        if (commandWord.equals("help"))
            printHelp();
        else if (commandWord.equals("go"))
            goRoom(command);
        else if (commandWord.equals("quit"))
            quit(command);
        else if (commandWord.equals("look"))
            look();
        else if (commandWord.equals("sit")) {
            println("Ye sit on the ground... What else did ye think would happen?");
            println();
            return;
        }
        //7.23
        else if (commandWord.equals("back"))
            back();
        //Logan
        else if (commandWord.equals("read"))
            read(command);
         //Class
        else if (commandWord.equals("take"))
            take(command);
        else if (commandWord.equals("drop"))
            drop(command);
        else if (commandWord.equals("bag"))
            bag();
        //Logan
        else if (commandWord.equals("burn"))
            burn(command);
        else
            println(commandWord + " is available in our premium version");
    }

    // implementations of user commands:

    
    /** 
     * Try to go to one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            println("Go where?");
            println();
            return;
        }

        String direction = command.getSecondWord();
        Room nextRoom = player.getCurrentRoom().getExit(direction);
        
        if (nextRoom == null) {
            println("There is no door hither!");
        }
        //Logan
        else if(player.getCurrentRoom().getDescription().contains("entrance")) {
            println("As ye enter, ye fall through a trap door in the floor thou didst not see.");
            player.setCurrentRoom(nextRoom);
            printLocationInfo();
        }
        else if(nextRoom.checkIfLocked() && !player.hasItem("key"))
        {
            println("This door is locked.");
            println();
            return;
        }
        else if(nextRoom.checkIfLocked() && player.hasItem("key"))
        {
            stack.push(player.getCurrentRoom());
            player.setCurrentRoom(nextRoom);
            println("Ye unlocked yon door with ye key.");
            printLocationInfo();
        }
        else {
            //7.23
            stack.push(player.getCurrentRoom());
            player.setCurrentRoom(nextRoom);
            printLocationInfo();
        }
    }
    
    //Class
    private void take(Command command)
    {
        if(!command.hasSecondWord()) {
            println("Take what?");
            println();
            return;
        }
        
        String name = command.getSecondWord();
        Item thing = player.getCurrentRoom().getItem(name);
        
        if(thing == null) {
            println("There is no " + name + " hither.");
            println();
            return;
        }
        if(name.equals("bag")) {
            player.setMaxWeight(12);
            player.getCurrentRoom().removeItem(thing);
            println("Ye can now hold more stuff!");
            println();
            return;
        }
        if(player.canCarryItem(thing)) {
            player.pickUpItem(thing);
            player.getCurrentRoom().removeItem(thing);
            println("Ye picked up " + name + ".");
            println();
            return;
        }
        else {
            println("Ye cannot carry that much weight!");
            println();
            return;
        }
    }
    
    private void drop(Command command)
    {
        if(!command.hasSecondWord()) {
            println("Drop what?");
            println();
            return;
        }
        
        String itemName = command.getSecondWord();
        Item thing = player.getItem(itemName);
        
        if(!player.hasItem(itemName)) {
            println("Ye don't have that item to drop.");
            println();
            return;
        }
        player.dropItem(thing);
        println("Ye dropped " + itemName + ".");
        println();
        return;
    }
    
    //Class
    private void bag() 
    {
        println("Ye look into ye olde bag...");
        String itemInfo = "";
        ArrayList<Item> listOfPlayerItems = player.getPlayerItems();
        for(Item item : listOfPlayerItems) {
            itemInfo += item.getName() + " ";
        }
        println("Ye seeth: " + itemInfo);
        println();
    }
    
    private void look()
    {
        printLocationInfo();
    }
    
    //7.23
    private void back()
    {
        if(!stack.empty()) {
            player.setCurrentRoom((Room) stack.pop());
            printLocationInfo();
        }
        else {
            println("Ye cannot go back any more!");
            println();
            return;
        }
    }
    
    //Logan
    private void read(Command command)
    {
        if(!command.hasSecondWord()) {
            println("Read what?");
            println();
            return;
        }
        if(!command.getSecondWord().equals("scroll") && 
                    !command.getSecondWord().equals("book")) {
            println("Ye cannot read that.");
            println();
            return;
        }
//         if(!player.hasItem("scroll") && !player.getCurrentRoom().hasItem("scroll")) {
//             println("Read what?");
//             println();
//             return;
//         }
        if(command.getSecondWord().equals("scroll") && 
                (player.hasItem("scroll") || player.getCurrentRoom().hasItem("scroll"))) {
            println("Ye olde scroll reads:");
            println("Ye who wandereth hither beware.");
            println("There is no hidden treasure within yon fortress.");
            println("I mean... what hidden treasure?");
            println("Death surely awaits ye!");
            println("- Yon Wizard of yon fortress");
            println();
        }
        if(command.getSecondWord().equals("book") && 
                (player.hasItem("book") || player.getCurrentRoom().hasItem("book"))) {
            println("Ye see many spells and incantations.");
            println();
        }
    }
    
    //Logan
    private void burn(Command command)
    {
        String thing = command.getSecondWord();
        
        if(!player.hasItem("torch")) {
            println("Ye have nothing with which to burneth!");
            println();
            return;
        }
        if(!command.hasSecondWord()) {
            println("Burn what?");
            println();
            return;
        }
        if(!thing.equals("scroll") && !thing.equals("book")) {
            println("Ye cannot burninate that...");
            println();
            return;
        }
        if(thing.equals("scroll")) {
            if(!player.hasItem("scroll") && !player.getCurrentRoom().hasItem("scroll")) {
                println("There is no scroll hither...");
                println();
                return;
            }
            if(player.hasItem("scroll")) {
                Item itemToDelete = player.getItem("scroll");
                player.dropItem(itemToDelete);
                player.getCurrentRoom().removeItem(itemToDelete);
                println("Ye burninated ye olde scroll!!!");
                println();
                return;
            }
            if(player.getCurrentRoom().hasItem("scroll")) {
                Item itemToDelete = player.getCurrentRoom().getItem("scroll");
                player.getCurrentRoom().removeItem(itemToDelete);
                println("Ye burninated ye olde scroll!!!");
                println();
                return;
            }
        }
        if(!player.hasItem("book") && !player.getCurrentRoom().hasItem("book")) {
            println("There is no book hither...");
            println();
            return;
        }
        if(player.hasItem("book")) {
            Item itemToDelete = player.getItem("book");
            player.dropItem(itemToDelete);
            player.getCurrentRoom().removeItem(itemToDelete);
            println("Ye burninated yon book!!!");
            println();
            return;
        }
        if(player.getCurrentRoom().hasItem("book")) {
            Item itemToDelete = player.getCurrentRoom().getItem("book");
            player.getCurrentRoom().removeItem(itemToDelete);
            println("Ye burninated yon book!!!");
            println();
            return;
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game. Return true, if this command
     * quits the game, false otherwise.
     */
    private void quit(Command command) 
    {
        if(command.hasSecondWord()) {
            println("Quit what?");
            wantToQuit = false;
        }
        else {
            println("Thank ye for playing.  ");
            wantToQuit = true;  // signal that we want to quit
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    /****************************************************************
     * Methods added 2005-04-20 by William H. Hooper
     ****************************************************************/
    
    /**
     * process commands or queries to the game
     * @param input user-supplied input
     */
    public void processInput(String input)
    {
        Command command = parser.getCommand(input);
        processCommand(command);
    }
    
    /**
     * clear and return the output messages
     * @return current contents of the messages.
     */
    public String readMessages()
    {
        String oldMessages = messages;
        messages = "";
        return oldMessages;
    }
    
    /**
     * @return true when the game is over.
     */
    public boolean finished()
    {
        return wantToQuit;
    }

    /**
     * add a message to the output list.
     * @param message the string to be displayed
     */
    private void print(String message)
    {
        messages += message;
    }
    
    /**
     * add a line to the output list.
     */
    private void println()
    {
        messages += "\n";
    }
    
    /**
     * add a message to the output list, 
     * followed by newline.
     * @param message the string to be displayed
     * @see readMessages
     */
    private void println(String message)
    {
        messages += message + "\n";
    }
}
