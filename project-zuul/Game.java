import java.util.Stack;
/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Jurre de Vries and Rienan Poortvliet
 * @version 09-01-2020
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Stack<Room> back;
    private ArrayList<Item> stock;
    private Player player;
    
    /**
     * Run the program outside BlueJ.
     */
    public static void main(String[] args)
    {
        // Game g = new Game();
        // g.start();
    }
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        // The list used for the back-command
        back = new Stack<>();
        stock = new ArrayList<>();
        player = new Player();
    }

    /**
     * Create all the rooms and link their exits together.
     * 09-01-2020 - Rooms added.
     */
    private void createRooms()
    {
        Room livingroom, toilet, headbedroom, hallway, corridor;
        Room  kitchen, upstairs, downstairs, kidbedroom, outside; 
        Room  babybedroom, bathroom, gameroom, basement, secretroom;
        Room lowbasementstairs, highbasementstairs;
        
        // Old code
        // Room outside, theater, pub, lab, office;
      
        // create the rooms
        livingroom = new Room("at the livingroom of the house");
        toilet = new Room("at the toilet of the house");
        headbedroom = new Room("at the bedroom of the parents");
        hallway = new Room("at the corridor which connects the rooms downstairs");
        corridor = new Room("at the corridor which connects the rooms upstairs");
        lowbasementstairs = new Room("at the bottom of the stairs which lead to the basement");
        highbasementstairs = new Room("at the top of the stairs which lead to the basement");
        kitchen = new Room("at the kitchen of the house");
        downstairs = new Room("at the bottom of the stairs");
        upstairs = new Room("at the top of the stairs");
        outside = new Room("at the end of the game");
        kidbedroom = new Room("at the bedroom of the kids");
        babybedroom = new Room("at the bedroom of the baby");
        bathroom = new Room("at the bathroom of the house");
        gameroom = new Room("at the gameroom for everyone");
        basement = new Room("at the underground basement");
        secretroom = new Room("at a place we don't know about");
        
        // initialise room exits
        // The rooms downstairs
        outside.setExit("south", hallway);
        hallway.setExit("north", outside);
        hallway.setExit("east", headbedroom);
        hallway.setExit("south", livingroom);
        hallway.setExit("west", downstairs);
        livingroom.setExit("north", hallway);
        livingroom.setExit("east", kitchen);
        livingroom.setExit("west", toilet);
        toilet.setExit("east", kitchen);
        headbedroom.setExit("east", highbasementstairs);
        headbedroom.setExit("west", hallway);
        kitchen.setExit("west", livingroom);
        downstairs.setExit("east", hallway);
        downstairs.setExit("up", upstairs);
        
        // The basementstairs
        highbasementstairs.setExit("down", lowbasementstairs);
        highbasementstairs.setExit("west", headbedroom);
        lowbasementstairs.setExit("up", highbasementstairs);
        lowbasementstairs.setExit("east", basement);
        
        //The basement rooms
        basement.setExit("south", secretroom);
        basement.setExit("west", lowbasementstairs);
        secretroom.setExit("north", basement);
        
        // The rooms upstairs
        upstairs.setExit("south", corridor);
        upstairs.setExit("down", downstairs);
        corridor.setExit("north", upstairs);
        corridor.setExit("east", bathroom);
        corridor.setExit("south", kidbedroom);
        corridor.setExit("west", babybedroom);
        kidbedroom.setExit("north",corridor);
        kidbedroom.setExit("east", gameroom);
        babybedroom.setExit("east", corridor);
        bathroom.setExit("west", corridor);
        gameroom.setExit("west", kidbedroom);
        
        // Define the exit used for the back-command
        // livingroom.setExit("back", back.pop());
        // toilet.setExit("back", back.pop());
        // headbedroom.setExit("back", back.pop());
        // hallway.setExit("back", back.pop());
        // corridor.setExit("back", back.pop());
        // basementstairs.setExit("back", back.pop());
        // kitchen.setExit("back", back.pop());
        // upstairs.setExit("back", back.pop());
        // downstairs.setExit("back", back.pop()); 
        // kidbedroom.setExit("back", back.pop());
        // babybedroom.setExit("back", back.pop());
        // bathroom.setExit("back", back.pop());
        // gameroom.setExit("back", back.pop());
        // basement.setExit("back", back.pop());
        // secretroom.setExit("back", back.pop());

        // Define current room
        currentRoom = outside;  // The game starts in the livingroom
        
        
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("back")) {
            goBack();
        }
        else if (commandWord.equals("pickup")) {
            getItem(command);
        }
        else if (commandWord.equals("drop")) {
            dropItem(command);
        }
        else if (commandWord.equals("stock")) {
            printStock();
        }
        // else command not recognised.
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to in to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            // 11-01-2020 Code added <--
            back.add(currentRoom);
            // -->
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    
    /**
     * This method is used when the back command was issued.
     */
    private void goBack()
    {
        // Go to the last room you've been to
        Room oldRoom = back.pop();
        currentRoom = oldRoom;
        roomIntroduction(currentRoom, false);
    }
    
    /**
     * This method prints the description of the current room.
     * @param introductionRoom The introduction of the room.
     * @param addBack Check if back was issued.
     * 
     * 13-01-2020 Method upgraded.
     */
    private void roomIntroduction(Room introductionRoom, boolean addBack) // , Room oldRoom = null
    {
        // Print the basic description of the room
        System.out.println(introductionRoom.getLongDescription());
        if(addBack) {
            // back.push(oldRoom);
        }
    }
    
    /**
     * This method makes it possible to pick up an item.
     * @param The command that was issued.
     */
    private void getItem(Command command)
    {
        if(!command.hasSecondWord()) {
            // This will be issued when pickup wasn't specified with an item 
            System.out.println("What do you want to pick up?");
        }
        
        String item = command.getSecondWord();
        
        // The item that has to be picked up by the user
        Item pickItem = currentRoom.getItem(item);
        
        if(pickItem < 2) {
            // Nothing happens
        } else {
            stock.add(pickItem);
            currentRoom.dropItem(item);
            System.out.println("You have picked up " + item);
        }
    }
    
    /**
     * This method drops the item from the stock.
     */
    private void dropItem(Command command)
    {
        if(!command.hasSecondWord()) {
            // This will be issued when drop wasn't specified with an item 
            System.out.println("What do you want to drop?");
        }
        
        String item = command.getSecondWord();
        
        // The item that has to be dropped up by the user
        Item dropItem = currentRoom.dropItem(item);
        if(pickItem == null) {
            System.out.println("There already are no items in stock");
        } else {
            stock.remove(dropItem);
            currentRoom.setItem(new Item(item));
            System.out.println("You have dropped the following item: " + item);
        }
    }
    
    /**
     * Take a look into your stock using this method.
     */
    private void printStock()
    {
        String itemList = "";
        for(int i; i < stock.size(); i++) {
            itemList = " " + stock.get(i).getDescription() + " ";
        }
        System.out.println("These are the items you have in stock: ");
        System.out.println(itemList);
    }
}
