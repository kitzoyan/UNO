
/*=============================================================================
|  PlayerManager.java                                                         |
|-----------------------------------------------------------------------------|
|  Programmer:  Adrian Lock and Robin Yan                                     |
|  Last Modified:   Jan 18, 2024                                              |
|  Course:  ICS4U1                                                            |
|-----------------------------------------------------------------------------|
|  This class is the class that manages the player objects. This class acts as|
|  the rotation/order of players. The order of players can change depending on|
|  any effects executed from the current card from Game class.                |
|=============================================================================*/
import java.io.ObjectInputStream.GetField;

public class PlayerManager {
    private final int SET_PLAYERS = 4;
    private int nextDraw;
    private Player[] players = new Player[SET_PLAYERS];
    private Player[] defaultList = new Player[SET_PLAYERS];

    /**
     * Class Constructor
     * This Constructor is for creating a new game, when you create a new game. This
     * method will run, using the default difficulty, empty deck
     * 
     * @param p1 a String represents the name of the human
     * @param p2 a String represents the name of the cpu
     * @param p3 a String represents the name of the cpu
     * @param p4 a String represents the name of the cpu
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
     * Class Constructor
     * This constructor is for loading a game from the previous game file, using the
     * saved difficulty level and deck
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
     * This method sort the list and move the next player to the first index, if
     * this is true, it will call its self one more time to skip the next player
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
     * *the method will just switch the second and fourth player, thus reverting the
     * rotation. Again, current player is player[0], but instead of next being i.e.
     * normally player[1] Bob, it will be Joe in replacement of Bob in position
     * player[1], and Bob is moved to player[3], being the fourth player.
     * 
     */
    public void reverseOrder() {
        int halfIndex = (SET_PLAYERS % 2 == 0 ? SET_PLAYERS / 2 : SET_PLAYERS / 2 + 1);
        for (int i = 1; i < halfIndex; i++) {
            Player temp = players[i];
            players[i] = players[SET_PLAYERS - i];
            players[SET_PLAYERS - i] = temp;
        }

        System.out.println("The order was reversed");
    }

    /**
     * Draws cards for the next player affected by such
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
     * This program returns the current player in the list
     * 
     * @return a Player object representing the current player
     */
    public Player getCurrentPlayer() {
        return players[0];
    }

    /**
     * Index must not be larger than the number of players or less than 0.
     * Out of bounds values will be changed to 0. This method should only be used to
     * set up the game.
     * 
     * @param position the position of the player of interest
     * @return the player of the specified index
     */
    public Player getPlayer(int position) {
        if (position < SET_PLAYERS || position > -1)
            return players[position];
        return players[0];
    }

    public Player searchPlayer(String name) {
        int bottom = 0, top = SET_PLAYERS - 1, index = -1, middle;
        boolean found = false;
        while (players[bottom].getName().compareTo(players[top].getName()) <= 0 && !found) {
            middle = (bottom + top) / 2;
            if (name.equals(players[middle].getName())) {
                found = true;
                return players[middle];
            } else if (name.compareTo(players[middle].getName()) > 0) {
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
