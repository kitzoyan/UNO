
/*=============================================================================
|  Uno.java                                                                   |
|-----------------------------------------------------------------------------|
|  Programmer:  Adrian Lock and Robin Yan                                     |
|  Last Modified:   Jan 18, 2024                                              |
|  Course:  ICS4U1                                                            |
|-----------------------------------------------------------------------------|
|  This class acts like a game manager for any uno games being run (Uno       |
|  itself, the pack of cards, cannot be instantiated) and it will keep track  |
|  of MAXIMUM three game slots. When this class is run, it will first check if|
|  the slots are empty or if there is data stored in it. It will initiate any |
|  game objects respectively. Then the class will print a menu, allowing the  |
|  user to either create a new game, load a game or delete a game, read rules,|
|  and print customer service information. When making a new game, the class  |
|  will prompt you for the game type you want to create (normal or tutorial). |
|  You can also load a  game from the text file and resume from there.        |
|  Note:                                                                      |
|  making a new game does not automatically save it to a txt file, it exists  |
|  within the computer’s system. The save must be manual. As well, saving a   |
|  game will return back to Uno.java, and Uno will be the one saving the game |
|  information.                                                               |
|=============================================================================*/
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import Cards.*;
import Cards.Number;

public class Uno {
    private final static String SLOT1FILE = "slot1.txt";
    private final static String SLOT2FILE = "slot2.txt";
    private final static String SLOT3FILE = "slot3.txt";
    private final static String GAMERULES = "rules.txt";
    private final static String CARDS = "cards.txt";

    private static Deck fullDeck;
    private static String gameRules = "GameRules:";
    private static Game currentGame;
    private static String currentSlot = null;

    /**
     * Runs the main loop allowing user to create game, delete game and loading a
     * game. This will further call other methods containing their own loops.
     */
    public static void run() {
        int input;
        boolean exit = false;

        fullDeck = new Deck(CARDS);
        loadRule();
        while (!exit) {
            System.out.println("=========================================== WELCOME");
            System.out.println("Slot #1 " + checkGame(SLOT1FILE));
            System.out.println("Slot #2 " + checkGame(SLOT2FILE));
            System.out.println("Slot #3 " + checkGame(SLOT3FILE) + "\n");
            System.out.println(
                    "\t1. Create a new Game\n" + "\t2. Continue a game\n" + "\t3. Delete a game\n"
                            + "\t4. Program Details\n" + "\t5. How to play\n" + "\t6. Close Game");
            input = verifyInput(6); // make sure the input is within 4
            if (input == 1) { // create a game
                if (emptySlot() != null) {
                    createGame(); // will create the game and run it
                } else {
                    System.out.println("Delete a game before making another");
                    Uno.wait(2000);
                }
            } else if (input == 2) { // loading a game

                loadGame(); // create the game object and run it

            } else if (input == 3) {
                // deleting a game
                deleteGame();
            } else if (input == 4) {
                programDescription();
            } else if (input == 5) {
                printRules();
            } else if (input == 6) {
                exit = true;
            }
        }

    }

    /**
     * Creates a new game and displays the interface for it. Upon choosing to play a
     * tutorial or normal game, the method will then call the game's own run loop.
     * Users will be asked to provide a name for the game, their player name, and 3
     * other CPU names.
     */
    private static void createGame() {
        int input, difficulty;
        boolean tutorial;
        String gameName, player, cpu1, cpu2, cpu3;
        System.out.println("=========================================== NEW GAME\n" + //
                "Choose your game mode:\n" + //
                "\t1. Normal\n" + //
                "\t2. Tutorial");

        input = verifyInput(2); // make sure the input is within 2
        if (input == 1) {
            tutorial = false;
        } else {
            tutorial = true;
        }
        System.out.print("Enter the name of the game: ");
        gameName = verifyInput();
        System.out.print("Enter your name: ");
        player = verifyInput();
        System.out.print("CPU #1: ");
        cpu1 = verifyInput();
        System.out.print("CPU #2: ");
        cpu2 = verifyInput();
        System.out.print("CPU #3: ");
        cpu3 = verifyInput();
        System.out.println("Enter difficulty(1-3): ");
        difficulty = verifyInput(3);

        // Depending on what is chosen, make a new game
        if (tutorial) {
            currentGame = new Tutorial(gameName, player, cpu1, cpu2, cpu3, fullDeck, difficulty);
            currentGame.run();
        } else {
            currentGame = new Game(gameName, player, cpu1, cpu2, cpu3, fullDeck, difficulty);
            currentGame.run();
        }
    }

    /**
     * Displays the interface to load a game that was saved in txt. Players are able
     * to choose
     * the game slot to be loaded.
     */
    private static void loadGame() {
        boolean exit = false;
        int input;

        while (!exit) {
            System.out.println("============================================ LOAD GAME\n" + //
                    "Choose a game slot to resume:\n" + //
                    "\t1. Slot #1\n" + //
                    "\t2. Slot #2\n" + //
                    "\t3. Slot #3\n" + //
                    "\t4. Go Back");

            input = verifyInput(4);
            if (input == 1 && !isEmpty(1)) {
                createGameObject(SLOT1FILE);
                currentSlot = SLOT1FILE;
                currentGame.run();
                exit = true;
            } else if (input == 2 && !isEmpty(2)) {
                createGameObject(SLOT2FILE);
                currentSlot = SLOT2FILE;
                currentGame.run();
                exit = true;
            } else if (input == 3 && !isEmpty(3)) {
                createGameObject(SLOT2FILE);
                currentSlot = SLOT3FILE;
                currentGame.run();
                exit = true;
            } else if (input == 4) {
                exit = true; // go back to the main menu
            } else {
                System.out.println("The game slot you entered is empty");
                wait(2000);
            }
        }
    }

    /**
     * Deletes saved Game files. You cannot make any new games if all 3 slots are
     * filled.
     */
    private static void deleteGame() {
        boolean exit = false;
        int input;

        while (!exit) {
            System.out.println("============================================ Delete GAME\n" + //
                    "Choose a game slot to delete:\n" + //
                    "\t1. Slot #1\n" + //
                    "\t2. Slot #2\n" + //
                    "\t3. Slot #3\n" + //
                    "\t4. Go Back");

            input = verifyInput(4);
            if (input == 1 && !isEmpty(1)) {
                deleteGameFile(SLOT1FILE);
                exit = true;
            } else if (input == 2 && !isEmpty(2)) {
                deleteGameFile(SLOT2FILE);
                exit = true;
            } else if (input == 3 && !isEmpty(3)) {
                deleteGameFile(SLOT3FILE);
                exit = true;
            } else if (input == 4) {
                exit = true; // go back to the main menu
            } else {
                System.out.println("The game slot you entered is empty");
            }

        }
    }

    /**
     * Saves game by writing the current date, game name, current color, current
     * card, discard pile's deck and all players deck onto txt. This method is
     * called by the Human play() interface.
     */
    public static void saveGame() {
        // generate the current date
        Date currentDate = new Date();
        BufferedWriter writer;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String currentDateTime = dateFormat.format(currentDate);

        String currentColor = currentGame.getCurrentColour();
        Card currentCard = currentGame.getCurrentCard();
        PlayerManager players = currentGame.getPlayers();
        Deck drawPile = currentGame.getDrawPile();
        Deck discardPile = currentGame.getDiscardPile();

        try {
            if (currentSlot != null) { // if the game is resumed, it will save to the old slot
                writer = new BufferedWriter(new FileWriter(currentSlot));
            } else {
                writer = new BufferedWriter(new FileWriter(emptySlot()));
            }
            writer.write(currentDateTime + "\n");
            writer.write(currentGame.getName() + "\n\n");
            if (currentGame instanceof Tutorial) {
                writer.write("tutorial\n");
            } else {
                writer.write("normal\n");
            }

            currentCard = currentGame.getCurrentCard();
            writer.write(currentCard.getColour() + "\n" + currentCard.getType() + "\n");
            writer.write(currentColor + "\n");

            writer.write("\ndiscard\n");
            for (int i = 0; i < discardPile.getNumCards(); i++) {
                writer.write(discardPile.getCard(i).getColour() + "\n" + discardPile.getCard(i).getType() + "\n");
            }
            writer.write("\n");

            Player temp;
            Deck deck;
            int difficulty;

            for (int i = 0; i < 4; i++) {
                temp = players.getPlayer(i);
                deck = temp.getDeck();

                writer.write(temp.getName());
                if (temp instanceof Human) {
                    writer.write("\nhuman");
                } else {
                    writer.write("\ncpu\n");
                    difficulty = ((Cpu) temp).getDifficulty();
                    writer.write("" + difficulty);
                    writer.write("\n" + ((Cpu) temp).getNeedUno());
                    writer.write("\n" + ((Cpu) temp).getCalledUno());

                }
                writer.write("\n" + i + "\n");
                for (int j = 0; j < deck.getNumCards(); j++) {
                    writer.write(deck.getCard(j).getColour() + "\n" + deck.getCard(j).getType() + "\n");
                }
                writer.write("\n");
            }
            writer.close();
            System.out.println("Game Saved");
        } catch (IOException ioe) {
            System.out.println("SYSTEM: (UNO) There are an error saving the game");
        }
    }

    /**
     * Returns current game object
     * 
     * @return the Game object being played currently
     */
    public static Game getCurrentGame() {
        return currentGame;
    }

    /**
     * Verifies the input in the given range of int. This is to reduce repetitive
     * programming of the same logic.
     * 
     * @param x the maximum option to pick from (0 < choice <= x)
     * @return the verfied input
     */
    public static int verifyInput(int x) {
        boolean exit = false;
        int input;
        Scanner sc = new Scanner(System.in);
        while (!exit) {
            try {
                System.out.print("[input]: ");
                input = Integer.parseInt(sc.nextLine());
                if (input > 0 && input <= x) {
                    return input;
                } else {
                    System.out.println("Enter a valid number!!");
                }
            } catch (NumberFormatException ime) {
                System.out.println("Enter a number within the range");
            }
        }
        return -1;
    }

    /**
     * Deletes game by overwriting the file with nothing.
     * 
     * @param fileName
     */
    private static void deleteGameFile(String fileName) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false));
        } catch (IOException ioe) {
            System.out.println("SYSTEM: (UNO) There are an error deleting the game file");

        }
    }

    /**
     * Loads the game rules and sets definitions of each card from the files.
     */
    private static void loadRule() {
        String reverse = "R";
        String number = "N";
        String plusFour = "F";
        String plusTwo = "T";
        String skip = "S";
        String colourChange = "C";
        String input, cardRules;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(GAMERULES));

            input = reader.readLine();
            for (int i = 0; i < Integer.parseInt(input); i++) {
                gameRules = gameRules + "\n\t" + (i + 1) + ". " + reader.readLine();
            }
            reader.readLine();
            input = reader.readLine();
            while (input != null) {
                cardRules = "";
                if (input.equals(reverse)) {
                    input = reader.readLine();
                    while (input != null && !(input.equals(""))) {
                        cardRules = cardRules + "\n" + input;
                        input = reader.readLine();
                    }
                    Reverse.setDefinition(cardRules);
                } else if (input.equals(number)) {
                    input = reader.readLine();
                    while (input != null && !(input.equals(""))) {
                        cardRules = cardRules + "\n" + input;
                        input = reader.readLine();
                    }
                    Number.setDefinition(cardRules);
                } else if (input.equals(plusFour)) {
                    input = reader.readLine();
                    while (input != null && !(input.equals(""))) {
                        cardRules = cardRules + "\n" + input;
                        input = reader.readLine();
                    }
                    PlusFour.setDefinition(cardRules);
                } else if (input.equals(plusTwo)) {
                    input = reader.readLine();
                    while (input != null && !(input.equals(""))) {
                        cardRules = cardRules + "\n" + input;
                        input = reader.readLine();
                    }
                    PlusTwo.setDefinition(cardRules);
                } else if (input.equals(skip)) {
                    input = reader.readLine();
                    while (input != null && !(input.equals(""))) {
                        cardRules = cardRules + "\n" + input;
                        input = reader.readLine();
                    }
                    Skip.setDefinition(cardRules);
                } else if (input.equals(colourChange)) {
                    input = reader.readLine();
                    while (input != null && !(input.equals(""))) {
                        cardRules = cardRules + "\n" + input;
                        input = reader.readLine();
                    }
                    ColourChange.setDefinition(cardRules);
                } else {
                    input = reader.readLine();
                }
            }
            reader.close();
        } catch (IOException ioe) {
            System.out.println("SYSTEM: (UNO) There are an error reading the game rules");
        }
    }

    /**
     * Loads a tutorial or normal Game based on what is saved in the txt file.
     * This is called by the loadGame() method
     * 
     * @param fileName a String representing the name of the slot file
     */
    private static void createGameObject(String fileName) {
        currentSlot = null;
        if (isTutorial(fileName)) {
            currentGame = new Tutorial(fileName, fullDeck);
        } else {
            currentGame = new Game(fileName, fullDeck);
        }
    }

    /**
     * Verifies a String input. If it exceeds the word limit, it will prompt the
     * user
     * to re-enter.
     * 
     * @return the String that is the valid input
     */
    public static String verifyInput() {
        final int WORDLIMIT = 15;
        String input;
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("[input]: ");
            input = sc.nextLine();
            if (input.length() <= WORDLIMIT) {
                return input;
            } else {
                System.out.println("Enter something less than 15 characters!");
            }

        }
    }

    /**
     * Reads the status of a game in a txt. If it is not empty it will return in
     * | Date | Game name format, otherwise | Empty
     * 
     * @param file a String that is the name of the file
     * @return the status of the game, example like | 2023/12/25 | Chrimtmas game
     */
    private static String checkGame(String file) {
        String input;
        String date;
        String name;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            date = reader.readLine();
            if (date == null) {
                reader.close();
                return "| empty";
            } else {
                name = reader.readLine();
                reader.close();
                return "| " + date + " | " + name;
            }
        } catch (IOException ioe) {
            System.out.println("SYSTEM: (UNO) There are an error checking if empty");
        }
        return "| error";
    }

    /**
     * Finds the first empty game slot to which a game can be saved to.
     * Return null if there are no available game slots.
     * 
     * @return return the fileName of the slot which is empty
     */
    private static String emptySlot() {
        String empty = "| empty";
        if (empty.equals(checkGame(SLOT1FILE))) {
            return SLOT1FILE;
        } else if (empty.equals(checkGame(SLOT2FILE))) {
            return SLOT2FILE;
        } else if (empty.equals(checkGame(SLOT3FILE))) {
            return SLOT3FILE;
        }
        return null;
    }

    /**
     * Checks if the game file is a tutorial. This method is used in loadGame
     * method, will
     * first check if the file is empty then will read until the tutorial line
     * 
     * @param x a String representing the game slot the user want to check
     * @return an boolean indicating if the game file is tutorial
     */
    private static boolean isTutorial(String fileName) {
        if ("| empty".equals(checkGame(fileName))) { // check is empty
            return false;
        } else {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(fileName));
                reader.readLine();
                reader.readLine();
                reader.readLine();
                if (reader.readLine().equals("tutorial")) {
                    reader.close();
                    return true;
                } else {
                    reader.close();
                    return false;
                }
            } catch (IOException ioe) {
                System.out.println("SYSTEM: (UNO) There are an error checking is tutorial");
            }
            return false;
        }
    }

    /**
     * Checks if the specified game slot is empty, only enter an int between 1 and 3
     * 
     * @param x an int representing the game slot the user want to check
     * @return an boolean indicating if the game file is empty
     */
    private static boolean isEmpty(int x) {
        String empty = "| empty";
        if (x == 1 && !empty.equals(checkGame(SLOT1FILE))) {
            return false;
        } else if (x == 2 && !empty.equals(checkGame(SLOT2FILE))) {
            return false;
        } else if (x == 3 && !empty.equals(checkGame(SLOT3FILE))) {
            return false;
        }
        return true;
    }

    /**
     * Prints out the version details, contributors and statistics
     */
    private static void programDescription() {
        System.out.println("\n" +
                "Title: YUNO UNO\n" +
                "Version: Beta 1.1\n" +
                "\n" +
                "Contributors:\n" +
                "\n" +
                "Lead Developer: Adrian Lock and Robin Yan\n" + //
                "Supervisor: Ms. Lam\n" +
                "\n" +
                "Statistics:\n" +
                "\n" +
                "Download Count: Join the growing community of Uno average enjoyer. With 10000+ downloads and counting.\n"
                +
                "\n" +
                "Average Rating: YUNO UNO has received glowing reviews among students, boasting an impressive average rating of 4.99 stars on app stores.\n"
                +
                "\n" +
                "If you encounter any issues and bugs, please reach out to adrianyhlock@gmail.com or kitzoyan2@gmail.com\n");
        wait(2000);
    }

    /**
     * Stalls the program so users can have time to read interface
     * 
     * @param msec time in milisecond
     */
    public static void wait(int msec) {
        try {
            Thread.sleep(msec);
        } catch (InterruptedException e) {
            System.out.println("SYSTEM: (Game) Thread was interputed");
        }
    }

    /**
     * Print out the basic game rules of uno
     */
    private static void printRules() {
        Scanner sc = new Scanner(System.in);
        System.out.println(gameRules);
        System.out.print("Press enter to continue");
        sc.nextLine();
    }
}
