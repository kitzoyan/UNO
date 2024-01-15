import java.io.*;

import Cards.*;

public class Uno {
    private final static String SLOT1FILE = "slot1.txt";
    private final static String SLOT2FILE = "slot2.txt";
    private final static String SLOT3FILE = "slot3.txt";
    private final static String GAMERULES = "rules.txt";
    private final static String CARDS = "cards.txt";

    private static Deck[] fullDeck;
    private static String gameRules;
    private static Game currentGame;

    // private static Game slot1;
    // private static Game slot2;
    // private static Game slot3;

    public static void run() {

    }

    private void loadRule() {

    }

    private void readCards(){
        fullDeck = new Deck[108];
        for
    }

    private void createGame() {

    }

    private void loadGame() {

    }

    private void deleteGame() {

    }

    public boolean saveGame() {

    }

    public static Game getCurrentGame() {

    }

    private boolean checkEmpty(String file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            if (reader.readLine() == null) {
                reader.close();
                return true;
            } else {
                reader.close();
                return false;
            }
        } catch (IOException ioe) {
            System.out.println("SYSTEM: (UNO) There are an error reading the file");
        }
    }
}
