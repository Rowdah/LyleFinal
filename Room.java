import java.util.HashMap;
import java.util.Set;
import java.util.ArrayList;
/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits. The exits are made by the setExit
 * method. 
 * 
 * @author  Logan Lowder (orginally based off code 
 * by Michael Kolling and David J. Barnes)
 * @version April 26, 2010
 */
public class Room 
{
    private String description;
    private HashMap<String, Room> map;
    private ArrayList<Item> items;
    private boolean locked;
    private boolean isDark;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        map = new HashMap<String, Room>();
        items = new ArrayList<Item>();
        //Logan
        locked = false;
        isDark = false;
    }
    
    /**
     * Makes a room locked, so the player cannot
     * enter without a key.
     */
    //Logan
    public void lockRoom()
    {
        locked = true;
    }
    
    /**
     * Checks if a room is locked.
     * @return Returns true if locked.
     */
    //Logan
    public boolean checkIfLocked()
    {
        return locked;
    }
    
    /**
     * Makes a room dark, so the player cannot
     * see without the torch.
     */
    //Logan
    public void makeDark()
    {
        isDark = true;
    }
    
    /**
     * Makes a room not dark, so the player can
     * see.
     */
    //Logan
    public void makeNotDark()
    {
        isDark = false;
    }
    
    /**
     * Checks if a room is dark.
     * @return Returns true if the room is dark.
     */
    //Logan
    public boolean checkIfDark()
    {
        return isDark;
    }
    
    /**
     * Sets up whatever exit to add to the room.
     * @param direction The direction in which the exit
     * is located.
     * @param neighbor The room which lies through that
     * exit.
     */
    public void setExit(String direction, Room neighbor)
    {
        map.put(direction, neighbor);
    }    

    /**
     * Gets an the exit in a direction for that room.
     * @param direction The direction to search for an exit.
     * @return A room if an exit in that direction exists,
     * otherwise returns null. 
     */
    public Room getExit(String direction)
    {
        return map.get(direction);
    }
    
    /**
     * Returns a description of the room's exits,
     * for example, "Exits: north west".
     * @return A description of the available exits.
     */
    public String getExitString()
    {
        String exitString = "Exits:";
        Set<String> keys = map.keySet();
        for(String exit : keys) {
            exitString += " " + exit;
        }
        return exitString;
    }

    /**
     * Used to find the description of the room.
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }
    
    /**
     * Return a long description of this room, of the form:
     *      You are in the kitchen.
     *      Exits: north west  
     * @return A description of the room, including exits. 
     */
    public String getLongDescription()
    {
//         if(isDark) {
//             return "Ye cannot see anything. 'Tis too dark.";
//         }
        if(items.size() == 0) {
            return "Ye" + description + "\n" + getExitString();
        }
        else {
            return "Ye" + description + "\n" + getItemInfo() + getExitString();
        }
    }
    
    /**
     * Creates an item and places it into the room.
     * @param name The name of the item.
     * @param itemWeight The weight of the item represented
     * by an int.
     * @param itemInfo The String you want the player to read
     * when he/she sees the item. For example, "Ye seeth, a shiny
     * ring."
     */
    public void placeItem(String name, int itemWeight, String itemInfo)
    {
        Item item = new Item(name, itemWeight, itemInfo);
        items.add(item);
    }
    
    /**
     * Takes an existing item and adds and adds it to the room.
     * @param item The item to add to the room.
     */
    public void placeItem(Item item)
    {
        items.add(item);
    }
    
    /**
     * Returns an item if it is currently in the room.
     * @param itemName The name of the item you wish to
     * get from the room.
     * @return Returns the item if it is in the room, otherwise
     * returns null. 
     */
    //Class getItem method
    public Item getItem(String itemName)
    {
        for(Item item : items) {
            if(item.getName().toLowerCase().equals(itemName.toLowerCase())) {
                return item;
            }
        }
        return null;
    }
    
    /**
     * Removes an item from the room.
     * @param name The name of the item to remove.
     */
    //class
    public void removeItem(Item name) 
    {
        items.remove(name);
    }
    
    /**
     * Gets the info from every item in the room.
     */
    private String getItemInfo()
    {
        String itemInfo = "";
        for(int i=0; i < items.size(); i++)
        {
            itemInfo += items.get(i).getInfo() + "\n";
        }
        return itemInfo;
    }
    
    /**
     * Tells whether or not a certain item is in this room.
     * @param name The name of the item to check for.
     * @return Returns true if the item is in the room, false
     * if it isn't. 
     */
    public boolean hasItem(String name)
    {
        for(Item item : items) {
            if(item.getName().toLowerCase().contains(name.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
    
}
