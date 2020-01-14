/**
 * This class contains the items of the game.
 *
 * @author Jurre de Vries and Rienan Poortvliet
 * @version 13-01-2020
 */
public class Item
{
    // instance variables - vervang deze door jouw variabelen
    private String description;

    /**
     * Constructor voor objects van class Item.
     */
    public Item()
    {
        // geef de instance variables een beginwaarde
        description = new String();
    }

    /**
     * 
     * @return This method returns the description of the item.
     */
    public String getDescription()
    {
        // Return the description of the item
        return description;
    }
}
