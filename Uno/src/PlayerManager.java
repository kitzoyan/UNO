
/*=============================================================================
|  PlayerManager.java                                                         |
|-----------------------------------------------------------------------------|
|  Programmer:  Adrian Lock and Robin Yan                                     |
|  Last Modified:   Jan 21, 2024                                              |
|  Course:  ICS4U1                                                            |
|-----------------------------------------------------------------------------|
|  This class is the class that manages the player objects. This class acts as|
|  the rotation/order of players, and the order can change depending on       |
|  any effects executed from the current card from Game class.                |
|=============================================================================*/
import java.io.ObjectInputStream.GetField;

public class PlayerManager {
    private final int SET_PLAYERS = 4;
    private Player[] players = new Player[SET_PLAYERS];
    private Player[] defaultList = new Player[SET_PLAYERS];

    /**
     * Creates a new PlayerManger. This method is called when a new game is made.
     * This method will create the Player objects for the game
     * 
     * @param p1         a String represents the name of the human
     * @param p2         a String represents the name of the cpu
     * @param p3         a String represents the name of the cpu
     * @param p4         a String represents the name of the cpu
     * @param difficulty a int representing the difficulty level of CPU's
     */
    public PlayerManager(String p1, String p2, String p3, String p4, int difficulty) {
        players[0] = new Human(p1);
        players[1] = new Cpu(p2, difficulty);
        players[2] = new Cpu(p3, difficulty);
        players[3] = new Cpu(p4, difficulty);

        // insertion sort for sorting players alphabetically
        defaultList[0] = players[0];
        defaultList[1] = players[1];
        defaultList[2] = players[2];
        defaultList[3] = players[3];
        int j;
        Player temp;
        for (int i = 0; i < SET_PLAYERS; i++) {
            j = i - 1;
            temp = defaultList[i];
            while (j >= 0 && temp.getName().compareTo(defaultList[j].getName()) < 0) {
                defaultList[j + 1] = defaultList[j];
                j--;
            }
            defaultList[j + 1] = temp;
        }
    }

    /**
     * Creates a PlayerManager which resumes a game from the loaded information of
     * a save.
     * 
     * @param p1       a String represents the name of the human
     * @param d1       a Deck containing the card of human
     * @param tutorial a boolean that indicate if tutorial mode is activated
     * @param p2       a String represents the name of the cpu
     * @param d2       a Deck containing the card of cpu1
     * @param l2       an int representing the difficult level
     * @param p3       a String represents the name of the cpu
     * @param d3       a Deck containing the card of cpu2
     * @param l3       an int representing the difficult level
     * @param p4       a String represents the name of the cpu
     * @param d4       a Deck containing the card of cpu 3
     * @param l4       an int representing the difficult level
     */
    public PlayerManager(Player p1, Player p2, Player p3, Player p4, int t1, int t2, int t3, int t4) {
        players[0] = p1;
        players[1] = p2;
        players[2] = p3;
        players[3] = p4;

        // insertion sort for sorting players alphabetically
        defaultList[0] = players[0];
        defaultList[1] = players[1];
        defaultList[2] = players[2];
        defaultList[3] = players[3];
        int j;
        Player temp;
        for (int i = 0; i < SET_PLAYERS; i++) {
            j = i - 1;
            temp = defaultList[i];
            while (j >= 0 && temp.getName().compareTo(defaultList[j].getName()) < 0) {
                defaultList[j + 1] = defaultList[j];
                j--;
            }
            defaultList[j + 1] = temp;
        }
    }

    /**
     * Sorts the player list to the next-in-line player. If a skip card was played,
     * it will call its self one more time to skip to the next next player
     * 
     * @param skipped a boolean indicating if the next player is skipped
     */
    public void sortNextPlayer(boolean skipped) {
        Player temp = players[0];
        players[0] = players[1];
        players[1] = players[2];
        players[2] = players[3];
        players[3] = temp;
        if (skipped) {
            sortNextPlayer(false);
        }

    }

    /**
     * Reverses the order of the list excluding player[0], who is still the current
     * player. The method flips player[i] and player[SET_PLAYERS-i] where i = 1 and
     * increases until SET_PLAYER/2.
     * 
     */
    public void reverseOrder() {
        // If SET_PLAYERS ever needed to be a non final variable...
        int halfIndex = (SET_PLAYERS % 2 == 0 ? SET_PLAYERS / 2 : SET_PLAYERS / 2 + 1);
        for (int i = 1; i < halfIndex; i++) {
            Player temp = players[i];
            players[i] = players[SET_PLAYERS - i];
            players[SET_PLAYERS - i] = temp;
        }

        System.out.println("The order was reversed");
    }

    /**
     * Draws cards for the next player affected by +2 and +4 cards
     * 
     * @param cardsForNext the amount of cards the next player must draw
     * @param drawDeck     the public draw Deck
     */
    public void drawCardforNext(int cardsForNext, Deck drawDeck) {
        for (int i = 0; i < cardsForNext; i++) {
            players[1].getDeck().moveCard(drawDeck, drawDeck.drawRandom());
        }
        System.out.println(players[1].getName() + " drew " + cardsForNext);
    }

    /**
     * @return a Player object representing the current player
     */
    public Player getCurrentPlayer() {
        return players[0];
    }

    /**
     * Returns a player. The given index must not be larger than the number of
     * players-1 or less than 0. Out of bound values will be changed to 0. This
     * method should only be upon saving the game, loading the game, and accessing
     * player decks in Tutorial.
     * 
     * @param position the position of the player of interest in the list
     * @return the player of the specified index
     */
    public Player getPlayer(int position) {
        if (position < SET_PLAYERS || position > -1)
            return players[position];
        return players[0];
    }

    /**
     * Searches the player of a specified name. This method uses binary search
     * to search the default list alphabetically.
     * 
     * @param name
     * @return a Player of the matching name. Return null if not found.
     */
    public Player searchPlayer(String name) {
        int bottom = 0, top = SET_PLAYERS - 1, middle;
        boolean found = false;
        while (defaultList[bottom].getName().compareTo(defaultList[top].getName()) <= 0 && !found) {
            middle = (bottom + top) / 2;
            if (name.equals(defaultList[middle].getName())) {
                found = true;
                return defaultList[middle];
            } else if (name.compareTo(defaultList[middle].getName()) > 0) {
                bottom = middle + 1;
            } else {
                top = middle - 1;
            }
        }
        return null;
    }

    /**
     * Returns the Human player inside the list
     * 
     * @return a Player object
     */
    public Player searchHuman() {
        for (int i = 0; i < SET_PLAYERS; i++) {
            if (players[i] instanceof Human) {
                return players[i];
            }
        }
        return null;
    }

    public int getSetPlayers() {
        return SET_PLAYERS;
    }

}
