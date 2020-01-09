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
 * @author  Jurre de Vries
 * @version 09-01-2020
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Stack<Room> back;
    
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
    }

    /**
     * Create all the rooms and link their exits together.
     * 09-01-2020 - Rooms added.
     */
    private void createRooms()
    {
        Room livingroom, toilet, headbedroom, hallway, corridor;
        Room basementstairs, kitchen, upstairs, downstairs, outside; 
        Room kidbedroom, babybedroom, bathroom, gameroom, basement, secretroom;
        
        // Old code
        // Room outside, theater, pub, lab, office;
      
        // create the rooms
        livingroom = new Room("the livingroom of the house");
        toilet = new Room("the toilet of the house");
        headbedroom = new Room("the bedroom of the parents");
        hallway = new Room("the corridor which connects the rooms downstairs");
        corridor = new Room("the corridor which connects the rooms upstairs");
        basementstairs = new Room("the stairs which lead to the basement");
        kitchen = new Room("the kitchen of the house");
        downstairs = new Room("the stairs on the ground floor to the first floor");
        upstairs = new Room("the stairs on the first floor to the ground floor");
        outside = new Room("the goal");
        kidbedroom = new Room("the bedroom of the kids");
        babybedroom = new Room("the bedroom of the baby");
        bathroom = new Room("the bathroom of the house");
        gameroom = new Room("the gameroom for everyone");
        basement = new Room("the underground basement");
        secretroom = new Room("a place we don't know about");
        
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
        headbedroom.setExit("east", basementstairs);
        headbedroom.setExit("west", hallway);
        kitchen.setExit("west", livingroom);
        downstairs.setExit("east", hallway);
        downstairs.setExit("up", upstairs);
        
        basementstairs.setExit("east", basement);
        basementstairs.setExit("west", headbedroom);
        
        //The basement rooms
        basement.setExit("south", secretroom);
        basement.setExit("west", basementstairs);
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
        livingroom.setExit("back", back.pop());
        toilet.setExit("back", back.pop());
        headbedroom.setExit("back", back.pop());
        hallway.setExit("back", back.pop());
        corridor.setExit("back", back.pop());
        basementstairs.setExit("back", back.pop());
        kitchen.setExit("back", back.pop());
        upstairs.setExit("back", back.pop());
        downstairs.setExit("back", back.pop()); 
        kidbedroom.setExit("back", back.pop());
        babybedroom.setExit("back", back.pop());
        bathroom.setExit("back", back.pop());
        gameroom.setExit("back", back.pop());
        basement.setExit("back", back.pop());
        secretroom.setExit("back", back.pop());

        // Define current room
        currentRoom = hallway;  // The game starts in the livingroom
        
        
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
     * Collect all the locations the player has gone to.
     */
    private void goBack()
    {
        String lastLocation;
        lastLocation = back.push();
        if(back.size() <= 0) {
            System.out.println("No history found");
        } else {
            back.pop(lastLocation);
        }
    }
}
