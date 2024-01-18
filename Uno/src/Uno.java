import java.io.*;
import java.util.InputMismatchException;
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

    // private static Game slot1;
    // private static Game slot2;
    // private static Game slot3;

    /**
     * Runs the main loop, allowing user to create game, delete game and loading a
     * game
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
                    "\t1. Create a new Game\n" + "\t2. Continue a game\n" + "\t3. Delete a game\n" + "\t4. Close Game");
            input = verifyInput(4); // make sure the input is within 4
            if (input == 1) { // create a game
                if (emptySlot() != -1) {
                    createGame(); // will create the game and run it
                } else {
                    System.out.println("Delete a game before creating");
                }
            } else if (input == 2) { // loading a game

                loadGame(); // create the game object and run it

            } else if (input == 3) {
                // deleting a game
                deleteGame();
            } else if (input == 4) {
                exit = true;
            }
        }

    }

    /**
     * Create the game
     */
    private static void createGame() {
        int input;
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

        if (tutorial) {
            currentGame = new Tutorial(gameName, player, cpu1, cpu2, cpu3, fullDeck);
        } else {
            currentGame = new Game(gameName, player, cpu1, cpu2, cpu3, fullDeck);
        }
        currentGame.run(); // run the game
        return;
    }

    /**
     * Loads a game, able to choose the game slot want to load,
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
                currentGame.run();
                exit = true;
            } else if (input == 2 && !isEmpty(2)) {
                createGameObject(SLOT2FILE);
                currentGame.run();
                exit = true;
            } else if (input == 3 && !isEmpty(3)) {
                createGameObject(SLOT2FILE);
                currentGame.run();
                exit = true;
            } else if (input == 4) {
                exit = true; // go back to the main menu
            } else {
                System.out.println("The game slot you entered is empty");
            }
        }
    }

    /**
     * Deletes Game file
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

    // public static boolean saveGame() {

    // }

    /**
     * Returns current game object
     * 
     * @return the Game object
     */
    public static Game getCurrentGame() {
        return currentGame;
    }

    /**
     * verifies the input in the given range of int
     * 
     * @param x specified game slot
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
     * Deletes game by rewriting the file. Delete
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
     * Loads the gamerule and definitions of each card from the files
     * Saves to the game rule string, update it to the static variable in each cards
     * method
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
                gameRules = gameRules + "\n" + reader.readLine();
            }
            reader.readLine();
            input = reader.readLine();
            while (input != null) {
                cardRules = "";
                if (input.equals(reverse)) {
                    for (int i = 0; !(input.equals("")); i++) {
                        input = reader.readLine();
                        cardRules = cardRules + "\n" + input;
                    }
                    reader.readLine();
                    Reverse.setDefinition(cardRules);
                } else if (input.equals(number)) {
                    for (int i = 0; !(input.equals("")); i++) {
                        input = reader.readLine();
                        cardRules = cardRules + "\n" + input;
                    }
                    reader.readLine();
                    Number.setDefinition(cardRules);
                } else if (input.equals(plusFour)) {
                    for (int i = 0; !(input.equals("")); i++) {
                        input = reader.readLine();
                        cardRules = cardRules + "\n" + input;
                    }
                    reader.readLine();
                    PlusFour.setDefinition(cardRules);
                } else if (input.equals(plusTwo)) {
                    for (int i = 0; !(input.equals("")); i++) {
                        input = reader.readLine();
                        cardRules = cardRules + "\n" + input;
                    }
                    reader.readLine();
                    PlusTwo.setDefinition(cardRules);
                } else if (input.equals(skip)) {
                    for (int i = 0; !(input.equals("")); i++) {
                        input = reader.readLine();
                        cardRules = cardRules + "\n" + input;
                    }
                    reader.readLine();
                    Skip.setDefinition(cardRules);
                } else if (input.equals(colourChange)) {
                    for (int i = 0; !(input.equals("")); i++) {
                        input = reader.readLine();
                        cardRules = cardRules + "\n" + input;
                    }
                    reader.readLine();
                    ColourChange.setDefinition(cardRules);
                } else {
                    input = reader.readLine();
                }
            }

        } catch (IOException ioe) {
            System.out.println("SYSTEM: (UNO) There are an error reading the game rules");
        }
    }

    private static void createGameObject(String fileName) {
        if (isTutorial(fileName)) {
            currentGame = new Tutorial(fileName);
        } else {
            currentGame = new Game(fileName);
        }
    }

    /**
     * Verifies String input, if it exceeds the word limit. It will prompt the user
     * agin
     * 
     * @return the String that is the valid input
     */
    public static String verifyInput() {
        final int wordLimit = 15;
        String input;
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("[input]: ");
            input = sc.nextLine();
            if (input.length() <= 15) {
                return input;
            } else {
                System.out.println("Enter something less than 15 characters!");
            }

        }
    }

    /**
     * Return the game status, if it is not empty it will return in
     * | Date | Game name format
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
     * Checks if the first empty game slot, this is use when creating game and
     * saving game. Return -1 if there are no available game slots
     * 
     * @return return the index of the slot which is empty
     */
    private static int emptySlot() {
        String empty = "| empty";
        if (empty.equals(checkGame(SLOT1FILE))) {
            return 1;
        } else if (empty.equals(checkGame(SLOT2FILE))) {
            return 2;
        } else if (empty.equals(checkGame(SLOT2FILE))) {
            return 3;
        }
        return -1;
    }

    /**
     * Checks if the game file is in tutorial mode, uses in loadGame method, will
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
}
