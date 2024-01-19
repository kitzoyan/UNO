
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
import Cards.Number;
import java.util.*;

public class Game {
    protected String name;
    protected Card currentCard = null;
    protected Card previousCard = null; // Used only to check if people drew instead of played
    protected String currentColour;
    protected final static int INIT_CARDS = 2;
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
    public Game(String gameName, String playerName, String cpu1, String cpu2, String cpu3, Deck fullDeck) {
        name = gameName;
        players = new PlayerManager(playerName, cpu1, cpu2, cpu3);
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

    public Game(String gameFile) {
        // Note: upon designing PM, you must make sure the ACTUAL CURRENT PLAYER at
        // players[0] is player[3] so that when run, will be sorted first
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
     * - applies effects on players from current card
     * - moves to next player
     * - returns a card from the player
     * - sets the current card
     * - if drawpile is all used up, substitute discardpile in
     * 
     * @return <code>true</code> if one player has 0 cards left and has won the
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
        Uno.wait(2000);

        System.out
                .println(
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
     * @return <code>true</code> if the current cart will skip the next player;
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
        } while (temporary instanceof PlusFour);
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
                    input = Math.round((float) Math.random() * 3 + 1);
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
     * @return <code>true</code> if
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

                    }
                }
            }
        }
        return found;
    }

    /**
     * Displays the GUI for user interface upon choosing to view all hidden decks.
     * This method should only be called from Human with TUTORIAL toggled on.
     * This method gives options to view public decks and other player decks.
     */
    public void revealCards() {
        // System.out.println("\t1. Reveal Cards in Draw Pile\n\t2. Reveal Cards in
        // Discard Pile\n\t3. Reveal a Player's Deck\n\t4. Return");
        int input = 0;
        boolean exit = false;
        while (!exit) {
            System.out.println(
                    "\t1. Reveal Cards in Draw Pile\n\t2. Reveal Cards in Discard Pile\n\t3. Reveal a Player's Deck\n\t4. Return");
            try {
                System.out.print("Reveal[input]: ");
                input = Integer.parseInt(sc.nextLine());
                if (input == 1) {
                    System.out.println("============================================ REVEAL DRAW PILE");
                    System.out.println(drawPile);
                } else if (input == 2) {
                    System.out.println("============================================ REVEAL DISCARD PILE");
                    System.out.println(discardPile);
                } else if (input == 3) {
                    revealPlayer();
                } else if (input == 4) {
                    return;
                } else {
                    throw new NumberFormatException("");
                }
            } catch (NumberFormatException e) {
                System.out.println("Re-enter a valid option: ");
            }
        }

    }

    /**
     * Prints the specific GUI for choosing to reveal a specific player's deck.
     * You must know who you want to reveal by name.
     */
    private void revealPlayer() {
        System.out.println("============================================ REVEAL PLAYER");
        System.out.println("Type in the name of the player you wish to reveal. Type in -1 to go back");
        String input = null;
        boolean exit = false;
        while (!exit) {
            try {
                System.out.print("Name [input]: ");
                input = sc.nextLine();
                if (Integer.parseInt(input) == -1) {
                    return;
                } else {
                    System.out.println("No name was found. Re-enter a valid option: ");
                }
            } catch (NumberFormatException e) { // This time because string input there is no false answer
                boolean found = false;
                for (int i = 0; i < players.getSetPlayers(); i++) {
                    if (players.getPlayer(i).getName().equalsIgnoreCase(input)) {
                        System.out.println("\n" + players.getPlayer(i).getDeck() + "\n");
                        return;
                    }
                }
                System.out.println("No name was found. Re-enter a valid option: ");
            }
        }

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

}
