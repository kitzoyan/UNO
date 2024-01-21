
/*=============================================================================
|  Game.java                                                                  |
|-----------------------------------------------------------------------------|
|  Programmer:  Adrian Lock and Robin Yan                                     |
|  Last Modified:   Jan 18, 2024                                              |
|  Course:  ICS4U1                                                            |
|-----------------------------------------------------------------------------|
|  This class controls the game loop of each game. You can make a new default |
|  game or load a game with a file. Inside this class, it contains all the    |
|  methods to keep track of the game progress, like current color, current    |
|  card, order of players (PlayerManager), drawing card for a player          |
|  (PlayerManager) and tracking if the game has ended (access player decks    |
|  through PlayerManager). This class can also be extended into different     |
|  game mode, like tutorial mode.                                             |
|=============================================================================*/
import Cards.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Game {
    protected String name;
    protected Card currentCard = null;
    protected Card previousCard = null; // Used only to check if people drew instead of played
    protected String currentColour;
    protected final static int INIT_CARDS = 7;
    protected PlayerManager players;
    protected Deck drawPile;
    protected Deck discardPile;
    private Scanner sc = new Scanner(System.in);

    private boolean gameSaved = false;

    /**
     * Creates a new Game object with the following parameters
     * 
     * @param gameName   the name of the game
     * @param playerName the name of the only human player
     * @param cpu1       the name for a cpu
     * @param cpu2       the name for a cpu
     * @param cpu3       the name for a cpu
     * @param fullDeck   the full Deck from Uno
     */
    public Game(String gameName, String playerName, String cpu1, String cpu2, String cpu3, Deck fullDeck,
            int difficulty) {
        name = gameName;
        players = new PlayerManager(playerName, cpu1, cpu2, cpu3, difficulty);
        drawPile = new Deck(fullDeck);
        discardPile = new Deck();

        // Draws 7 cards for all
        for (int j = 0; j < players.getSetPlayers(); j++) {
            for (int i = 0; i < INIT_CARDS; i++) {
                players.getPlayer(j).getDeck().moveCard(drawPile, drawPile.drawRandom());
            }
        }
        System.out.println("============================================ RUNNING NEW GAME");
        // The players are altered slightly because the first line in run will sort
        System.out.printf("Welcome, the order: %s, %s, %s, and %s\n", players.getPlayer(1).getName(),
                players.getPlayer(2).getName(),
                players.getPlayer(3).getName(),
                players.getPlayer(0).getName());

        System.out.println("Each player has recieved " + INIT_CARDS + " cards\n");
        System.out.println("Starting player: " + players.getPlayer(1).getName());
        // Draws initial first card
        drawFirstCard();
        System.out.println("The first card: " + currentCard);
        System.out.println("Current colour: " + currentColour);
        // run();
    }

    /**
     * Class Constructor
     * Creates the game object using the full deck and game file
     * 
     * @param gameFile
     * @param fullDeck
     */
    public Game(String gameFile, Deck fullDeck) {
        String input, color, type, player;
        int difficulty = 3;
        boolean needUno, calledUno;
        String[] names = new String[4];
        Deck[] decks = new Deck[4];
        int[] order = new int[4];
        Player[] temp = new Player[4];
        drawPile = new Deck(fullDeck);
        discardPile = new Deck();

        // Note: upon designing PM, you must make sure the ACTUAL CURRENT PLAYER at
        // players[0] is player[3] so that when run, will be sorted first
        try {
            BufferedReader reader = new BufferedReader(new FileReader(gameFile));
            input = reader.readLine();
            name = reader.readLine();

            reader.readLine(); // skip the empty line
            reader.readLine(); // skip the game mode line

            color = reader.readLine();
            type = reader.readLine();
            currentCard = returnNewCard(color, type);
            currentColour = reader.readLine();

            reader.readLine();
            reader.readLine();
            input = reader.readLine();
            for (int i = 0; !input.equals(""); i++) {
                color = input;
                type = reader.readLine();
                discardPile.moveCard(drawPile, returnNewCard(color, type));
                input = reader.readLine();
            }

            for (int i = 0; i < 4; i++) {
                needUno = false;
                calledUno = false;
                names[i] = reader.readLine();
                player = reader.readLine();
                if (player.equals("cpu")) {
                    difficulty = Integer.parseInt(reader.readLine());
                    if (reader.readLine().equals("true"))
                        needUno = true;
                    if (reader.readLine().equals("true"))
                        calledUno = true;
                }
                order[i] = Integer.parseInt(reader.readLine());
                input = reader.readLine();
                decks[i] = new Deck();
                while (!input.equals("")) {
                    color = input;
                    type = reader.readLine();
                    decks[i].moveCard(drawPile, returnNewCard(color, type));
                    input = reader.readLine();
                }
                if (player.equals("human")) {
                    temp[i] = new Human(names[i], decks[i]);
                } else {
                    temp[i] = new Cpu(names[i], decks[i], difficulty, calledUno, needUno);

                }
            }
            players = new PlayerManager(temp[3], temp[0], temp[1], temp[2], order[0], order[1], order[2], order[3]);
            System.out.printf("Welcome, the order: %s, %s, %s, and %s\n", players.getPlayer(1).getName(),
                    players.getPlayer(2).getName(),
                    players.getPlayer(3).getName(),
                    players.getPlayer(0).getName());
            System.out.println("Starting player: " + players.getPlayer(1).getName());
            System.out.println("The current card: " + currentCard);
            System.out.println("Current colour: " + currentColour);
            reader.close();
        } catch (IOException ioe) {
            System.out.println("SYSTEM: (UNO) There are an error loading the game");
        }
    }

    /**
     * Run the game loop. Prints all welcoming messages.
     */
    public void run() {
        gameSaved = false;
        boolean exit = false;
        while (!exit) {
            exit = turn();
        }
        if (!gameSaved) {
            System.out.println("============================================ END");
            System.out.println(players.getCurrentPlayer().getName() + " was the winner. Thanks for playing!");
            Uno.wait(3000);
        }
        return;
    }

    /**
     * Runs a single turn which:
     * 
     * <pre>
     * - applies effects on players from current card
     * - moves to next player
     * - returns a card from the player
     * - sets the current card
     * - if drawpile is all used up, substitute discardpile in
     * </pre>
     * 
     * @return returns true if one player has 0 cards left and has won the
     *         game, or user chose to save game
     */
    private boolean turn() {
        // If all cards used
        if (drawPile.isEmpty()) {
            System.out.println("Draw pile is empty. Substituting discard pile as draw pile");
            drawPile = discardPile;
            discardPile = new Deck();
        }

        // Check if the previous card is the same identity by address as the the
        // current,
        // if such, do NOT APPLY the card
        boolean toSkip = (currentCard == previousCard ? false : applyCurrentCard());

        if (toSkip) {
            System.out.println(players.getPlayer(1).getName() + " was skipped");
        }
        players.sortNextPlayer(toSkip);

        // Delay the output so players can read
        Uno.wait(2500);

        System.out.println(
                "============================================ "
                        + players.getCurrentPlayer().getName().toUpperCase()
                        + "'s TURN");
        Card chosen = players.getCurrentPlayer().play(currentCard, currentColour, drawPile);
        if (gameSaved) {
            return true; // exit the loop if the game is saved
        }
        previousCard = currentCard;
        setCurrentCard(chosen, players.getCurrentPlayer() instanceof Cpu, players.getCurrentPlayer().getDeck());
        if (chosen == null) {
            System.out.println("Current card: " + currentCard);
        } else {
            System.out.println(players.getCurrentPlayer().getName() + " played: " + currentCard);
            if (players.getCurrentPlayer() instanceof Cpu) {
                ((Cpu) players.getCurrentPlayer()).callUno();
            }
        }
        System.out.println("Current colour: " + currentColour);
        System.out.println("\nCard(s) left: " + players.getCurrentPlayer().getDeck().getNumCards());

        return (players.getCurrentPlayer().getDeck().isEmpty());

    }

    /**
     * Applies effects of the current card on the player order or upcoming player
     * 
     * @return returns true if the current cart will skip the next player;
     */
    private boolean applyCurrentCard() {
        if (currentCard instanceof PlusFour) {
            players.drawCardforNext(((PlusFour) currentCard).getDraw(), drawPile);
        } else if (currentCard instanceof PlusTwo) {
            players.drawCardforNext(((PlusTwo) currentCard).getDraw(), drawPile);
        } else if (currentCard instanceof Reverse) {
            players.reverseOrder();
        }
        return currentCard.getSkipNext();
    }

    /**
     * Draw a random first card, and move it to the discard pile.
     * The first card cannot be a plus four card.
     */
    private void drawFirstCard() {
        Card temporary = null;
        do {
            temporary = drawPile.drawRandom();
            // temporary = drawPile.getCard(drawPile.searchSpecificCard("red", "Reverse"));
        } while (temporary instanceof PlusFour);

        // Because before start, the real 1st player is currently 2nd in list, waiting
        // to be sorted in run()
        // Must sort now so when reverse is applied, it changes to 4th instead of 3rd
        if (temporary instanceof Reverse) {
            players.sortNextPlayer(false);
        }
        setCurrentCard(temporary, players.getCurrentPlayer() instanceof Cpu, drawPile);
    }

    /**
     * Sets the given card as the current card, and sets the current colour. If the
     * card is a wild card, a GUI will pop up for colour selection for the player.
     * If
     * the card is null, meaning no card was played, keep the status of the old
     * card.
     * 
     * @param c     the Card to be made current
     * @param isCpu a boolean indicating whether the current player is a cpu or not
     * @param donor the donating Deck of which the card is coming from
     */
    private void setCurrentCard(Card c, boolean isCpu, Deck donor) {
        // if a palyer even played a card at all
        if (c != null) {
            currentCard = c;
            currentColour = currentCard.getColour();
            discardPile.moveCard(donor, currentCard);
            // System.out.println(currentCard + " got moved to discard pile");

            if (currentColour.equals("black")) {
                // System.out.println("\nDISCARD PILE" + discardPile + "\n");
                boolean exit = false;
                int input = 0;

                // If you are a player...
                if (!isCpu) {
                    System.out.println("Choose a colour:\n\t1. Red\n\t2. Yellow\n\t3. Green\n\t4. Blue");
                    while (!exit) {
                        try {
                            System.out.print("Colour [input]: ");
                            input = Integer.parseInt(sc.nextLine());
                            if (input > 0 && input < 5) {
                                System.out.println();
                                exit = true;
                            } else {
                                throw new NumberFormatException("");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Re-enter a valid option");
                        }
                    }
                } else {
                    input = ((Cpu) players.getCurrentPlayer()).getFocusColourNum() + 1;
                }

                // Based on the choices
                if (input == 1) {
                    currentColour = "red";
                } else if (input == 2) {
                    currentColour = "yellow";
                } else if (input == 3) {
                    currentColour = "green";
                } else if (input == 4) {
                    currentColour = "blue";
                } else {
                    System.out.println("SYSTEM: (Game) This was not supposed to get here!");
                }

            }
        }

    }

    /**
     * Searches through all CPU players and checks for anyone who needs Uno has not
     * called it. This method should only be called by Human
     * 
     * @return a boolean indicates if the cpu need uno
     */
    public boolean catchUno() {
        boolean found = false;
        for (int i = 0; i < players.getSetPlayers(); i++) {
            if (players.getPlayer(i) instanceof Cpu) {
                Cpu c = (Cpu) players.getPlayer(i);

                // If "Uno" is required
                if (c.getNeedUno()) {
                    // But it was not called
                    if (!c.getCalledUno()) {
                        found = true;
                        System.out.println("\n" + c.getName() + " forgot to call Uno and drew 2\n");
                        // Draw 2 random cards as punishment
                        c.getDeck().moveCard(drawPile, drawPile.drawRandom());
                        c.getDeck().moveCard(drawPile, drawPile.drawRandom());
                        c.callUno();
                        Uno.wait(1000);

                    }
                }
            }
        }
        return found;
    }

    public PlayerManager getPlayers() {
        return players;
    }

    public String getName() {
        return name;
    }

    public String getCurrentColour() {
        return currentColour;
    }

    public Card getCurrentCard() {
        return currentCard;
    }

    public Deck getDrawPile() {
        return drawPile;
    }

    public Deck getDiscardPile() {
        return discardPile;
    }

    public String gameType() {
        return "normal";
    }

    public void toggleGameSaved() {
        gameSaved = !gameSaved;
    }

    /**
     * Translate the text inside the text file into a card
     */
    private Card returnNewCard(String color, String type) {
        return drawPile.getCard(drawPile.searchSpecificCard(color, type));
    }

    /**
     * Searches through all players and checks if anyone is approaching 0 cards.
     * 
     * @return return true if someone has a card below 4
     */
    public boolean CpuPanic() {
        int minCards = players.getPlayer(1).getDeck().getNumCards();
        // for (int i = 2; i < players.getSetPlayers(); i++) {
        // if (players.getPlayer(i).getDeck().getNumCards() < minCards) {
        // minCards = players.getPlayer(i).getDeck().getNumCards();
        // }
        // }
        return (minCards < 4);
    }

}
