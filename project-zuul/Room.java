import java.util.HashMap;
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
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */
public class Room 
{
    // 27-12-2019 17:39 The new code
    private String description;
    private HashMap<String, Room> exits;
    
    // 27-12-2019 16:32 The updated code 
    // private Room northExit;
    // private Room southExit;
    // private Room eastExit;
    // private Room westExit;
    
    // 27-12-2019 16:31 The old code
    // public String description;
    // public Room northExit;
    // public Room southExit;
    // public Room eastExit;
    // public Room westExit;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        
        // 27-12-2019 17:42 Added code
        exits = new HashMap<>();
    }

    /**
     * Define the exits of this room.  Every direction either leads
     * to another room or is null (no exit there).
     * @param north The north exit.
     * @param east The east east.
     * @param south The south exit.
     * @param west The west exit.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }

    /**
     * Return the exit of the direction.
     */
    public Room getExit(String direction)
    {
        return exits.get(direction);
    }
    
    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }
    
    // 27-12-2019 19:17 Replaced by method setExit
    /**
     * Define the exits of this room.  Every direction either leads
     * to another room or is null (no exit there).
     * @param north The north exit.
     * @param east The east east.
     * @param south The south exit.
     * @param west The west exit.
     */
    // public void setExits(Room north, Room east, Room south, Room west) 
    // {
        // if(north != null) {
            // exits.put("north", north);
        // }
        // if(east != null) {
            // exits.put("east", east);
        // }
        // if(south != null) {
            // exits.put("south", south);
        // }
        // if(west != null) {
            // exits.put("west", west);
        // }
    // }
}
