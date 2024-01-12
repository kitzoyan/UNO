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
    public PlayerManager(String p1, String p2, String p3, String p4) {
        players[0] = new Human(p1);
        players[1] = new Cpu(p2);
        players[2] = new Cpu(p3);
        players[3] = new Cpu(p4);

        // insertion sort for sorting players alphabetically
        defaultList = players;
        int j;
        Player temp;
        for (int i = 0; i < SET_PLAYERS; i++) {
            j = i - 1;
            temp = defaultList[i];
            while (j >= 0 && temp.getName().compareTo(defaultList[j]) < 0) {
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
    public PlayerManager(String p1, Deck d1, Boolean tutorial, String p2, Deck d2, int l2, String p3, Deck d3, int l3,
            String p4,
            Deck d4, int l4, int t1, int t2, int t3, int t4) {
        players[t1] = new Human(p1, d1, tutorial);
        players[t2] = new Cpu(p2, d2, l2);
        players[t3] = new Cpu(p3, d3, l3);
        players[t4] = new Cpu(p4, d4, l4);

        // insertion sort for sorting players alphabetically
        defaultList = players;
        int j;
        Player temp;
        for (int i = 0; i < SET_PLAYERS; i++) {
            j = i - 1;
            temp = defaultList[i];
            while (j >= 0 && temp.getName().compareTo(defaultList[j]) < 0) {
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
        if (!skipped) {
            players[0] = players[1];
            players[1] = players[2];
            players[2] = players[3];
            players[3] = temp;
        } else {
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
    public void sortReverse() {
        Player temp = players[1];
        players[1] = players[3];
        players[3] = temp;
        sortNextPlayer(false);
    }

    public void drawCardforNext(int){
        
    }

}
