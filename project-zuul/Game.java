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
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room livingroom, toilet, headbedroom, corridor, basementstairs, kitchen, stairs, outside; 
        Room kidbedroom, babybedroom, bathroom, gameroom, basement, secretroom;
        // Room outside, theater, pub, lab, office;
              
        // create the rooms
        livingroom = new Room("the livingroom of the house");
        toilet = new Room("the toilet of the house");
        headbedroom = new Room("the bedroom of the parents");
        corridor = new Room("the corridor which connects the rooms to each other");
        basementstairs = new Room("the stairs which lead to the basement");
        kitchen = new Room("the kitchen of the house");
        stairs = new Room("the stairs from the ground floor to the first floor and the other way around");
        outside = new Room("the goal");
        kidbedroom = new Room("the bedroom of the kids");
        babybedroom = new Room("the bedroom of the baby");
        bathroom = new Room("the bathroom of the house");
        gameroom = new Room("the gameroom for everyone");
        basement = new Room("the underground basement");
        secretroom = new Room("a place we don't know about");
        // outside = new Room("outside the main entrance of the university");
        // theater = new Room("in a lecture theater");
        // pub = new Room("in the campus pub");
        // lab = new Room("in a computing lab");
        // office = new Room("in the computing admin office");
        
        // initialise room exits
        livingroom.setExit("north", corridor);
        livingroom.setExit("east", kitchen);
        livingroom.setExit("west", toilet);
        toilet.setExit("east", kitchen);
        headbedroom.setExit("east", basementstairs);
        headbedroom.setExit("west", corridor);
        corridor.setExit("north", outside);
        corridor.setExit("east", headbedroom);
        corridor.setExit("south", livingroom);
        corridor.setExit("west", stairs);
        basementstairs.setExit("east", basement);
        basementstairs.setExit("west", headbedroom);
        kitchen.setExit("west", livingroom);
        stairs.setExit("east", corridor);
        stairs.setExit("south", corridor);
        outside.setExit("south", corridor);
        kidbedroom.setExit("north",corridor);
        kidbedroom.setExit("east", gameroom);
        babybedroom.setExit("east", corridor);
        bathroom.setExit("west", corridor);
        gameroom.setExit("west", kidbedroom);
        basement.setExit("south", secretroom);
        basement.setExit("west", basementstairs);
        
        
        // livingroom.setExits(corridor, kitchen, null, toilet);
        // toilet.setExits(null, kitchen, null, null);
        // headbedroom.setExits(null, basementstairs, null, corridor);
        // corridor.setExits(outside, headbedroom, livingroom, stairs);
        // basementstairs.setExits(null, basement, null, headbedroom);
        // kitchen.setExits(null, null, null, livingroom);
        // stairs.setExits(null, corridor, corridor, null);
        // outside.setExits(null, null, corridor, null);
        // kidbedroom.setExits(corridor, gameroom, null, null);
        // babybedroom.setExits(null, corridor, null, null);
        // bathroom.setExits(null, null, null, corridor);
        // gameroom.setExits(null, null, null, kidbedroom);
        // basement.setExits(null, null, secretroom, basementstairs);
        // secretroom.setExits(basement, null, null, null);
        
        // outside.setExits(null, theater, lab, pub);
        // theater.setExits(null, null, null, outside);
        // pub.setExits(null, outside, null, null);
        // lab.setExits(outside, office, null, null);
        // office.setExits(null, null, null, lab);
        currentRoom = livingroom;  // start game outside
        // currentRoom = outside;  // start game outside
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
        printLocationInfo();
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
        System.out.println("   go quit help");
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
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
        // 27-12-2019 Added code
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            printLocationInfo();
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
     * Print the direction where the character is going to.
     * 27-12-2019 currentRoom.northExit is now currentRoom.getExit("north").
     *  - This applies to all directions.
     */
    private void printLocationInfo()
    {
        System.out.println("You are " + currentRoom.getDescription());
        System.out.print("Exits: ");
        if(currentRoom.getExit("north") != null) {
            System.out.print("north ");
        }
        if(currentRoom.getExit("east") != null) {
            System.out.print("east ");
        }
        if(currentRoom.getExit("east") != null) {
            System.out.print("south ");
        }
        if(currentRoom.getExit("west") != null) {
            System.out.print("west ");
        }
        System.out.println();
    }
    
    /**
     * Return a string with exits of the rooms included.
     * For example "Exits: north west".
     * @return A description of the present exits in the room.
     */
    //public String getExitString()
}
