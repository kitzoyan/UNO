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

    public static void run() {
        int input;
        Scanner sc = new Scanner(System.in);
        boolean exit = false;

        fullDeck = new Deck(CARDS);
        loadRule();
        while (!exit)
            System.out.println("=========================================== WELCOME");
        System.out.println(checkGame(SLOT1FILE));
        System.out.println(checkGame(SLOT2FILE));
        System.out.println(checkGame(SLOT3FILE) + "\n");
        System.out.println(
                "\t1. Create a new Game\n" + "\t2. Continue a game\n" + "\t3. Delete a game\n" + "\t4. Close Game");
        input = verifyInput(4);
        if (input == 1) {
            if (emptySlot() != -1) {
                createGame();
            } else {
                System.out.println("Delete a game before creating");
            }
        } else if (input == 2) {
            if (emptySlot() != -1) {
                System.out.println("There is no available game slots to load");
            } else {
                loadGame();
            }
        } else if (input == 3) {
            if (emptySlot() != -1) {
                System.out.println("There are no available game slots to delete");
            } else {
                deleteGame();
            }
        } else if (input == 4) {
            exit = true;
        }
    }

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
                        cardRules = cardRules + "\n" + reader.readLine();
                    }
                    reader.readLine();
                    Reverse.setDefinition(cardRules);
                } else if (input.equals(number)) {
                    for (int i = 0; !(input.equals("")); i++) {
                        cardRules = cardRules + "\n" + reader.readLine();
                    }
                    reader.readLine();
                    Number.setDefinition(cardRules);
                } else if (input.equals(plusFour)) {
                    for (int i = 0; !(input.equals("")); i++) {
                        cardRules = cardRules + "\n" + reader.readLine();
                    }
                    reader.readLine();
                    PlusFour.setDefinition(cardRules);
                } else if (input.equals(plusTwo)) {
                    for (int i = 0; !(input.equals("")); i++) {
                        cardRules = cardRules + "\n" + reader.readLine();
                    }
                    reader.readLine();
                    PlusTwo.setDefinition(cardRules);
                } else if (input.equals(skip)) {
                    for (int i = 0; !(input.equals("")); i++) {
                        cardRules = cardRules + "\n" + reader.readLine();
                    }
                    reader.readLine();
                    Skip.setDefinition(cardRules);
                } else if (input.equals(colourChange)) {
                    for (int i = 0; !(input.equals("")); i++) {
                        cardRules = cardRules + "\n" + reader.readLine();
                    }
                    reader.readLine();
                    ColourChange.setDefinition(cardRules);
                }
            }

        } catch (IOException ioe) {
            System.out.println("SYSTEM: (UNO) There are an error reading the game rules");
        }
    }

    private static void createGame() {
        int input;
        boolean tutorial;
        String gameName, player, cpu1, cpu2, cpu3;

        System.out.println("=========================================== NEW GAME\n" + //
                "Choose your game mode:\n" + //
                "\t1. Normal\n" + //
                "\t2. Tutorial");
        input = verifyInput(2);
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
            currentGame = new Tutorial(gameName, player, cpu1, cpu2, cpu3);
        } else {
            currentGame = new Game(gameName, player, cpu1, cpu2, cpu3);
        }
    }

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
                currentGame = new Game(SLOT1FILE);
                exit = true;
            } else if (input == 2 && !isEmpty(2)) {
                currentGame = new Game(SLOT2FILE);
                exit = true;
            } else if (input == 3 && !isEmpty(3)) {
                currentGame = new Game(SLOT3FILE);
                exit = true;
            } else {
                exit = true;
            }
        }
    }

    private static void deleteGame() {

    }

    // public boolean saveGame() {

    // }

    // public static Game getCurrentGame() {

    // }

    public static int verifyInput(int x) {
        boolean exit = false;
        int input;
        Scanner sc = new Scanner(System.in);

        while (!exit) {
            try {
                input = sc.nextInt();
                if (input > 0 && input <= x) {
                    return input;
                } else {
                    System.out.println("Enter a valid number!!");
                }
            } catch (InputMismatchException ime) {
                sc.nextLine();
                System.out.println("Enter a number within the range");
            }
        }
        return -1;
    }

    public static String verifyInput() {
        final int wordLimit = 15;
        String input;
        Scanner sc = new Scanner(System.in);
        while (true) {
            input = sc.nextLine();
            if (input.length() <= 15) {
                return input;
            } else {
                System.out.println("Enter something less than 15 characters!");
            }

        }
    }

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

    private static int emptySlot() {
        String empty = "| error";
        if (empty.equals(checkGame(SLOT1FILE))) {
            return 1;
        } else if (empty.equals(checkGame(SLOT2FILE))) {
            return 2;
        } else if (empty.equals(checkGame(SLOT2FILE))) {
            return 3;
        }
        return -1;
    }

    private static boolean isEmpty(int x) {
        String empty = "| error";
        if (x == 1 && empty.equals(checkGame(SLOT1FILE))) {
            return false;
        } else if (x == 2 && empty.equals(checkGame(SLOT2FILE))) {
            return false;
        } else if (x == 3 && empty.equals(checkGame(SLOT3FILE))) {
            return false;
        }
        return true;
    }
}
