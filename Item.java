
/**
 * An item to be place in a room or in a player's bag.
 * An item has a name, a one word String; a weight,
 * represented by an int; and an info field, a longer String
 * shown when the player sees the item.
 * 
 * @author (Logan Lowder) 
 * @version (04-26-2010)
 */
public class Item
{
    private String name;
    private int weight;
    private String info;
    
    /**
     * Constructs an item.
     * @param name The name of the item (one word).
     * @param weight The weight of the item represented by an int.
     * @param info  The String you want the player to 
     * read when he/she sees the item. For example,
     * "Ye seeth a shiny ring."
     */
    public Item(String name, int weight, String info)
    {
        this.name = name;
        this.weight = weight;
        this.info = info;
    }
    
    /**
     * Returns the name of the item.
     * @return The name of the item in a String.
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Returns an items weight.
     * @return An int representing the item's weight.
     */
    public int getWeight()
    {
        return weight;
    }
    
    /**
     * Returns the info field of an item.
     * @return A String sentence to be read by the user upon
     * seeing the item.
     */
    public String getInfo()
    {
        return info;
    }


}
