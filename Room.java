import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 */

public class Room 
{
    private String description;
    private ArrayList<Item> item;
    private HashMap<String, Room> exits;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<>();
        item = new ArrayList<>();
    }

    public void addItem(String itemDescription, String itemWeight) {
        Item iTemp = new Item(itemDescription, itemWeight);

        item.add(iTemp);
    }

    public String showItems() {
        if (item.isEmpty()) {
            return "Esta sala não possui itens";
        }
        String allItems = "Itens da sala:\n\n";
        for (Item item2 : item) {
            allItems += item2.getItemDetails() + "\n";
        }
        return allItems;
    }

    /**
     * Define the exits of this room.  Every direction either leads
     * to another room or is null (no exit there).
     * @param north The north exit.
     * @param east The east east.
     * @param south The south exit.
     * @param west The west exit.
     */
    public void setExits(Room north, Room east, Room south, Room west) 
    {
        if(north != null) {
            exits.put("north", north);
        }
        if(east != null) {
            exits.put("east", east);
        }
        if(south != null) {
            exits.put("south", south);
        }
        if(west != null) {
            exits.put("west", west);
        }
    }


    /**
     * Return a long description of this room, of the form:
     * You are in the kitchen.
     * Exits: north west
     * @return A description of the room, including exits.
     */
    public String getLongDescription()
    {
        return "Você está no(a) " + description + ".\n" + getExitString() + "\n\n" + showItems();
    }

    /**
     * Return a description of the room’s exits,
     * for example, "Exits: north west".
     * @return A description of the available exits.
     */
    public String getExitString() {
        String returnString = "Saídas:";
        Set<String> keys = exits.keySet();

        for (String exit : keys) {
            returnString += " " + exit;
        }

        return returnString;
    }

    public Room getExit(String direction) {
        return exits.get(direction);
    }
}
