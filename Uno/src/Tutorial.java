
/*=============================================================================
|  Tutorial.java                                                              |
|-----------------------------------------------------------------------------|
|  Programmer:  Adrian Lock and Robin Yan                                     |
|  Last Modified:   Jan 18, 2024                                              |
|  Course:  ICS4U1                                                            |
|-----------------------------------------------------------------------------|
|  This class is an extension of Game. The user is able to learn how the game |
|  works by being able to view other people’s hand, to look through discard & |
|  draw piles. This game mode is designed for the new players who wish to     |
|  learn how to play without the need of a real person to help.               |
|=============================================================================*/
import java.util.*;

public class Tutorial extends Game {
    private Scanner sc = new Scanner(System.in);

    /**
     * Creates a new tutorial game which turns on tutorial mode in Human player
     * 
     * @param gameName   a String representing the name of the game
     * @param playerName a String representing the name of the player
     * @param cpu1       a String representing the name of cpu 1
     * @param cpu2       a String representing the name of cpu 2
     * @param cpu3       a String representing the name of cpu 3
     * @param fullDeck   the full public Deck from Uno
     * @param difficulty the difficulty level of CPU's
     */

    public Tutorial(String gameName, String playerName, String cpu1, String cpu2, String cpu3, Deck fullDeck,
            int difficulty) {
        super(gameName, playerName, cpu1, cpu2, cpu3, fullDeck, difficulty);
        Human temp = (Human) (players.searchPlayer(playerName));
        temp.toggleTutorial();
    }

    /**
     * Creates a new tutorial game based on the information found in the txt file
     * provided. This constructor assumes the txt has been validated in Uno as a
     * Tutorial type.
     *
     * @param fileName the specified file name that stores the game file
     * @param fullDeck the full public Deck from Uno
     */
    public Tutorial(String fileName, Deck fullDeck) {
        super(fileName, fullDeck);
        Human temp = (Human) (players.searchHuman());
        temp.toggleTutorial();

    }

    /**
     * Returns the number of cards in the draw pile and discard pile
     * 
     * @param colour the specified color to search for
     * @param type   the specified type to search for
     * @return an int representing the number of the specified cards
     */
    public int searchPublicDraw(String colour, String type) { // don't know about the deck
        return drawPile.searchSpecificCard(colour, type) + discardPile.searchSpecificCard(colour, type);
    }

    /**
     * Displays the user interface upon choosing to view all hidden decks.
     * This method should only be called from Human with TUTORIAL toggled on.
     * This method gives options to view public decks and other player decks.
     */
    public void revealCards() {
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
                    exit = true;
                } else {
                    throw new NumberFormatException("");
                }
            } catch (NumberFormatException e) {
                System.out.println("Re-enter a valid option: ");
            }
        }

    }

    /**
     * Prints the interface for choosing to reveal a specific player's deck.
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
                    exit = true;
                } else {
                    System.out.println("No name was found. Re-enter a valid option: ");
                }
            } catch (NumberFormatException e) { // If the input is not a number...
                boolean found = false;
                for (int i = 0; i < players.getSetPlayers(); i++) {
                    if (players.getPlayer(i).getName().equalsIgnoreCase(input)) {
                        System.out.println("\n" + players.getPlayer(i).getDeck() + "\n");
                        found = true;
                        exit = true;
                    }
                }
                if (!found) {
                    System.out.println("No name was found. Re-enter a valid option: ");

                }
            }
        }

    }

    /**
     * Returns the game type for saving the game file
     */
    public String gameType() {
        return "tutorial";
    }
}
