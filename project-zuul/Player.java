import java.util.ArrayList;
/**
 * This class is the main class of the player you are while playing this 
 * game.
 *
 * @author Jurre de Vries
 * @version 09-01-2020
 */
public class Player
{
    // instance variables - vervang deze door jouw variabelen
    private ArrayList<String> items;
    
    /**
     * Constructor voor objects van class Player.
     */
    public Player()
    {
        // geef de instance variables een beginwaarde
        items = new ArrayList<>();
    }
    
    /**
     * This method adds the item to your stock if it can be added.
     */
    public void addItem(String item)
    {
        if(items.size() < 2) {
            items.add(item);
            System.out.println("Item is added to your stock.");
        } else {
            System.out.println("Your stock is full.");
        }
    }
}
