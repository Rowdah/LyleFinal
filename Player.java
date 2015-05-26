import java.util.ArrayList;
/**
 * A player in the game, Lyle. A player has a currentRoom,
 * the room it's in; a maxWeight, the int amount of weight
 * it can carry; and an ArrayList of items. 
 * 
 * @author (Logan Lowder) 
 * @version (04-26-2010)
 */
public class Player
{
    private Room currentRoom;
    private int maxWeight;
    private ArrayList<Item> playerItems;
    
    /**
     * Contructs a player.
     */
    public Player()
    {
        playerItems = new ArrayList<Item>();
        maxWeight = 5;
    }
    
    /**
     * Returns what room the player is currently in... duh.
     * @return The room the player is in.
     */
    public Room getCurrentRoom()
    {
        return currentRoom;
    }
    
    /**
     * Sets a new room for the player to be in.
     * @param newRoom The new room you want the player
     * to be in.
     */
    public void setCurrentRoom(Room newRoom)
    {
        currentRoom = newRoom;
    }
    
    /**
     * Returns an ArrayList of all the items the player
     * is carrying. 
     * @return The player's playerItems ArrayList.
     */
    public ArrayList getPlayerItems()
    {
        return playerItems;
    }
    
    /**
     * Adds an item to the player's list of items.
     * @param item The item to add to the player's bag.
     */
    public void pickUpItem(Item item)
    {
        playerItems.add(item);
    }
    
    /**
     * Drops a certain item from the players bag.
     * @param item The item to drop.
     */
    public void dropItem(Item item)
    {
        Item tempItem = item;
        playerItems.remove(item);
        currentRoom.placeItem(tempItem);
    }
    
    /**
     * Checks whether or not the new item-to-add's 
     * weight plus the current weight the player is
     * carrying is over the player's max carrying weight.
     * @param item The item to weigh.
     * @return Returns true if the player can carry this item,
     * false if the player cannot. 
     */
    public boolean canCarryItem(Item item)
    {
        int itemWeight = item.getWeight();
        int bagWeight = getBagWeight();
        if(bagWeight + itemWeight > maxWeight) {
            return false;
        }
        else {
            return true;
        }
    }
    
    private int getBagWeight()
    {
        int bagWeight = 0;
        for(Item item : playerItems) {
            bagWeight += item.getWeight();
        }
        return bagWeight;
    }
    
    /**
     * Sets a new max carrying weight for the player.
     * @param newMaxWeight The new max carrying weight.
     */
    public void setMaxWeight(int newMaxWeight)
    {
        maxWeight = newMaxWeight;
    }
    
    /**
     * Checks whether the player has a certain item.
     * @param name A name of type String that should
     * match the name of the item to check for. 
     * @return Returns true if the player has this item, false
     * otherwise.
     */
    public boolean hasItem(String name)
    {
        for(Item item : playerItems) {
            if(item.getName().toLowerCase().contains(name.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Gets an item from the player.
     * @param itemName A String that should match the
     * name of the item.
     * @return Returns an item if itemName matches the name
     * of an item the player has. Otherwise returns null.
     */
    public Item getItem(String itemName)
    {
        for(Item item : playerItems) {
            if(item.getName().toLowerCase().equals(itemName.toLowerCase())) {
                return item;
            }
        }
        return null;
    }
    
    
    
    
    
    
    
    
    
}