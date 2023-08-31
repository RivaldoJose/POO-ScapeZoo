
import javax.swing.JOptionPane;

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
    private Room lastRoom;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }

    public void printLocationInfo() {
        System.out.println(currentRoom.getLongDescription());
    }

    private void look()
    {
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room leoes, girafas, aves, macacos, cobras, elefantes, praca_de_alimentacao;
      
        // create the rooms
        praca_de_alimentacao = new Room("Praça de alimentação");
        leoes = new Room("Jaula dos leões");
        girafas = new Room("Jaula das girafas");
        aves = new Room("Jaula das aves");
        macacos = new Room("Jaula dos macacos");
        cobras = new Room("Jaula das cobras");
        elefantes = new Room("Jaula dos elefantes");
        
        // initialise room exits
        leoes.setExits(null, girafas, null, null);
        girafas.setExits(null, aves, praca_de_alimentacao, leoes);
        aves.setExits(null, null, null, girafas);
        macacos.setExits(praca_de_alimentacao, cobras, elefantes, null);
        cobras.setExits(null, null, null, macacos);
        elefantes.setExits(macacos, null, null, null);
        praca_de_alimentacao.setExits(girafas, null, macacos, null);


        praca_de_alimentacao.addItem("Bandeja de comida", "300g");
        praca_de_alimentacao.addItem("Sorvete", "100g");
        currentRoom = praca_de_alimentacao;  // start game outside
        lastRoom = praca_de_alimentacao;
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
        System.out.println("Obrigado pelo jogo! Até mais o/");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Bem vindo ao ScapeZoo!");
        System.out.println("ScapeZoo é um jogo inacreditavelmente tenso e repleto de aventuras.");
        System.out.println("Digite 'help' se você precisa de ajuda.");
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
            System.out.println("Não entendi o que você quis dizer...");
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
        } else if (commandWord.equals("look")) {
            look();
        } else if (commandWord.equals("alimentar")) {
            System.out.println("Você tentou alimentar os animais mas o guardinha interviu... Ele está de olho \u00AC_\u00AC");
        } else if (commandWord.equals("back")) {
            Room currentRoomTemp = currentRoom;

            currentRoom = lastRoom;
            lastRoom = currentRoomTemp;

            printLocationInfo();
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
        JOptionPane.showMessageDialog(null, "Você está perdido. Você está sozinho. Você vagueia \ndentro do zoológico (\u25CF'\u25E1'\u25CF)\n\nSeus comandos são:\n" + parser.showCommands());;
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Ir pra onde?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);


        if (nextRoom == null) {
            System.out.println("Não tem nenhuma porta!");
        }
        else {
            lastRoom = currentRoom;
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
            System.out.println("Sair de quê?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
