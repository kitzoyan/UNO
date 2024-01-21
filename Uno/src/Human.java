
/*=============================================================================
|  Human.java                                                                 |
|-----------------------------------------------------------------------------|
|  Programmer:  Adrian Lock and Robin Yan                                     |
|  Last Modified:   Jan 21, 2024                                              |
|  Course:  ICS4U1                                                            |
|-----------------------------------------------------------------------------|
|  This class is the child class of the Player class. This class contains all |
|  the methods required for a player, including the play() interface.         |
|  A Human Player keeps track of whether they are in tutorial mode or not.    |
|=============================================================================*/
import java.util.InputMismatchException;
import java.util.Scanner;

import Cards.Card;

public class Human extends Player {
    private boolean tutorialMode = false;

    /**
     * Makes a new Human with a name. Deck size is set default to 0.
     * 
     * @param name
     */
    public Human(String name) {
        super(name);
    }

    /**
     * Makes a new Human player. This constructor assumes that whoever calls it must
     * already have a pre-made deck.
     * 
     * @param name the name of the player
     * @param d    the deck being passed in
     */
    public Human(String name, Deck d) {
        super(d, name);
    }

    /**
     * Changes tutorial mode set or not set
     */
    public void toggleTutorial() {
        tutorialMode = !tutorialMode;
    }

    /**
     * Displays the menu interface for the Human Player's turn and runs other
     * methods based on the users selection
     * 
     * @param currentCard   the last card played into the game
     * @param currentColour the current colour of the game
     * @param drawDeck      the public draw Deck
     * @return a card the user chose. Returns null if no card was played.
     */
    public Card play(Card currentCard, String currentColour, Deck drawDeck) {
        Scanner sc = new Scanner(System.in);
        int input = 0;
        deck.superSort();
        String reveal = (tutorialMode ? "\t4. Reveal Cards\n" : "");
        boolean exit = false;
        Card chosen = null;

        // Run the user choice option loop
        while (!exit) {
            System.out.printf("\t1. Attempt to Call Uno\n\t2. Play\n\t3. Quit and Save Game\n%s", reveal);
            try {
                System.out.print("Turn [input]: ");
                input = Integer.parseInt(sc.nextLine()); // Call Uno
                if (input == 1) {
                    boolean valid = Uno.getCurrentGame().catchUno();
                    // If you called wrong, you draw two cards
                    if (!valid) {
                        System.out.println("\nNo players required \"Uno\"\nYour call was invalid so you drew 2\n");
                        deck.moveCard(drawDeck, drawDeck.drawRandom());
                        deck.moveCard(drawDeck, drawDeck.drawRandom());
                    }
                } else if (input == 2) { // Play Cards
                    chosen = selectCards(sc, currentCard, currentColour, drawDeck);
                    exit = true;
                } else if (input == 3) { // Save Game
                    Uno.saveGame();
                    Uno.getCurrentGame().toggleGameSaved();
                    exit = true;
                } else if (input == 4 && tutorialMode) { // Reveal cards
                    ((Tutorial) Uno.getCurrentGame()).revealCards();
                } else {
                    throw new NumberFormatException("");
                }
            } catch (NumberFormatException e) {
                // You either entered a number out of range or you entered a non int
                System.out.println("Re-enter a valid option: ");
            }
        }

        if (chosen != null) {
            if (deck.getNumCards() == 2) {
                System.out.println("You called Uno");
            }
        }

        return chosen;

    }

    /**
     * Displays the interface and runs logic for card selection
     * 
     * @param sc            the Scanner being used in the play() method
     * @param currentCard
     * @param currentColour
     * @param drawDeck      the public draw Deck
     * @return a card the user chose. Return null if no card played: card was drawn.
     */
    private Card selectCards(Scanner sc, Card currentCard, String currentColour, Deck drawDeck) {
        int input = 0;
        System.out.println("\t1. Draw a Random Card");
        int numChoices = 2;

        // Prints all card choices
        while (numChoices - 2 < deck.getNumCards()) {
            System.out.printf("\t%d. " + deck.getCard(numChoices - 2) + "\n", numChoices);
            numChoices++;
        }

        // Run user card selection loop
        boolean exit = false;
        while (!exit) {
            try {
                System.out.print("Card [input]: ");
                input = Integer.parseInt(sc.nextLine());
                if (input > 1 && input < numChoices) {
                    Card chosen = deck.getCard(input - 2);

                    // If you choose to play, or if you choose to read the card's definition
                    if (!printDefinition(sc, chosen)) {

                        // If you choose to use this card, and it's valid, play it. Otherwise, try again
                        if (chosen.isValidMove(currentCard) || chosen.getColour().equals(currentColour)) {
                            System.out.println();
                            return chosen;
                        } else {
                            System.out.println("You cannot play this card. Try choosing another");
                            throw new NumberFormatException("");
                        }
                    } else {
                        System.out.println("Enter another card to play");
                    }
                    // Draw a new card, return null if the new card is unplayable
                } else if (input == 1) {
                    return drawCardFromPile(sc, currentCard, currentColour, drawDeck);
                } else {
                    throw new NumberFormatException("");
                }
            } catch (NumberFormatException e) {
                System.out.println("Re-enter a valid option");
            }
        }

        System.out.println("SYSTEM: (Human) The code was not supposed to reach here");
        return null; // null means no card played. By theory, code should never run here
    }

    /**
     * Displays the choices to either explain the card or play the card upon
     * selecting a card
     * 
     * @param sc     the Scanner of the play() method
     * @param toKnow the Card in question
     * @return <code>true</code> if the user chose explain instead of use
     */
    private boolean printDefinition(Scanner sc, Card toKnow) {
        System.out.println("\t1. Explain\n\t2. Use");
        int input = 0;
        boolean exit = false;

        // Run user loop
        while (!exit) {
            try {
                System.out.print("Function [input]: ");
                input = Integer.parseInt(sc.nextLine());
                if (input == 1) {
                    System.out.println(toKnow.getDefinition());
                    return true;
                } else if (input == 2) {
                    exit = true;
                } else {
                    throw new NumberFormatException("");
                }
            } catch (NumberFormatException e) {
                System.out.println("Re-enter a valid option");
            }
        }
        return false;
    }

    /**
     * Draws a random Card from the public draw pile upon the user's request.
     * 
     * @param sc
     * @param currentCard
     * @param currentColour
     * @param drawDeck
     * @return a Card only if it can be played. If the card cannot be played, null
     *         is returned.
     */
    private Card drawCardFromPile(Scanner sc, Card currentCard, String currentColour, Deck drawDeck) {
        Card newDrawn = drawDeck.drawRandom();
        System.out.println("You drew: " + newDrawn);
        deck.moveCard(drawDeck, newDrawn);
        Uno.wait(1000);

        if (newDrawn.isValidMove(currentCard) || newDrawn.getColour().equals(currentColour)) {
            System.out.println("This card is valid to play. Do you wish to play it?\n\t1. Yes\n\t2. No");
            boolean exit = false;
            int input = 0;

            // Run user loop
            while (!exit) {
                try {
                    System.out.print("Drawn [input]: ");
                    input = Integer.parseInt(sc.nextLine());
                    if (input == 1) {
                        System.out.println();
                        return newDrawn;
                    } else if (input == 2) {
                        exit = true;
                    } else {
                        throw new NumberFormatException("");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Re-enter a valid option");
                }
            }
        } else {
            System.out.println("This card cannot be played and has been stored in your deck\n");
        }
        Uno.wait(1000);
        return null;
    }
}
