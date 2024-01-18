
/*=============================================================================
|  Human.java                                                                 |
|-----------------------------------------------------------------------------|
|  Programmer:  Adrian Lock and Robin Yan                                     |
|  Last Modified:   Jan 18, 2024                                              |
|  Course:  ICS4U1                                                            |
|-----------------------------------------------------------------------------|
|  This class is the child class of the Player class. This is a class contains|
|  all the methods that require for a player, including the player interface. |
|  This class has two modes, tutorial and normal.                             |
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
     * Make a new Human player. This constructor assumes that whoever calls it must
     * already have a pre made deck.
     * 
     * @param name
     * @param d            the deck being passed in
     * @param tutorialMode
     */
    public Human(String name, Deck d, boolean tutorialMode) {
        super(d, name);
        this.tutorialMode = tutorialMode;
    }

    /**
     * Changes tutorial mode set or not set
     */
    public void toggleTutorial() {
        tutorialMode = !tutorialMode;
    }

    /**
     * Runs the menu GUI and the user selection logic when it's their turn
     * 
     * @param currentCard the last card played into the game
     * @param drawDeck    the public draw Deck
     * @return a card the user chose. Returns null if no card was played.
     */
    public Card play(Card currentCard, String currentColour, Deck drawDeck) {
        Scanner sc = new Scanner(System.in);
        int input = 0;
        deck.superSort();
        // System.out.println(deck.getNumCards());

        // Print your choices
        String reveal = (tutorialMode ? "\t4. Reveal Cards\n" : "");
        System.out.printf("\t1. Attempt to Call Uno\n\t2. Play\n\t3. Quit and Save Game\n%s", reveal);
        boolean exit = false;
        Card chosen = null;

        // Run the user choice option loop
        while (!exit) {
            try {
                System.out.print("[input]: ");
                input = Integer.parseInt(sc.nextLine()); // Call Uno
                if (input == 1) {
                    exit = true;
                    // Call game from static uno
                } else if (input == 2) { // Play Cards
                    chosen = selectCards(sc, currentCard, currentColour, drawDeck);
                    exit = true;
                } else if (input == 3) { // Save Game
                    exit = true;
                    // Call game from static uno
                } else if (input == 4 && tutorialMode) { // Reveal cards
                    exit = true;
                    // Call game from static uno
                } else {
                    throw new NumberFormatException("");
                }
            } catch (NumberFormatException e) {
                // You either entered a number out of range or you entered a non int
                System.out.println("Re-enter a valid option: ");
            }
        }

        if (chosen == null) {
            // System.out.println("You drew a card.");
        } else {
            System.out.println("You played: " + chosen);
        }

        return chosen;

    }

    /**
     * Displays the GUI for card selection
     * 
     * @param sc       the Scanner being used in the class
     * @param drawDeck the public draw Deck
     * @return a card chose. Return null if no card played: card was drawn.
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
        // System.out.println(numChoices);

        // Run user card selection loop
        boolean exit = false;
        while (!exit) {
            try {
                System.out.print("[input]: ");
                input = Integer.parseInt(sc.nextLine());
                if (input > 1 && input < numChoices) {
                    Card chosen = deck.getCard(input - 2);
                    // System.out.println(chosen.getColour().equals(currentColour));
                    // System.out.println(chosen.getColour());
                    // System.out.println(currentColour);
                    if (!printDefinition(sc, chosen)) {

                        // If you choose to use this card, and it's valid, play it. Otherwise, try again
                        if (chosen.isValidMove(currentCard) || chosen.getColour().equals(currentColour)) {
                            return chosen;
                        } else {
                            System.out.println("You cannot play this card. Try choosing another");
                            throw new NumberFormatException("");
                        }
                    } else {
                        System.out.println("Enter another card to play");
                    }
                } else if (input == 1) {
                    return drawCardFromPile(sc, currentCard, currentColour, drawDeck);
                } else {
                    throw new NumberFormatException("");
                }
            } catch (NumberFormatException e) {
                // System.out.println(e);
                System.out.println("Re-enter a valid option");
            }
        }

        System.out.println("SYSTEM: (Human) The code was not supposed to reach here");
        return null; // null means no card played. By theory, code should never run here
    }

    /**
     * Displays the choices to either explain the card or play the card upon
     * selecting a given card
     * 
     * @param sc     the Scanner of the class
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
                System.out.print("[input]: ");
                input = Integer.parseInt(sc.nextLine());
                if (input == 1) {
                    System.out.println(toKnow.getDefinition());
                    // System.out.println("Select another card");
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
     * @param drawDeck
     * @param currentCard
     * @return a Card only if it can be played. If the card cannot be played, null
     *         be returned
     */
    private Card drawCardFromPile(Scanner sc, Card currentCard, String currentColour, Deck drawDeck) {
        Card newDrawn = drawDeck.drawRandom();
        System.out.println("You drew: " + newDrawn);
        deck.moveCard(drawDeck, newDrawn);
        if (newDrawn.isValidMove(currentCard) || newDrawn.getColour().equals(currentColour)) {
            System.out.println("This card is valid to play. Do you wish to play it?\n\t1. Yes\n\t2. No");
            boolean exit = false;
            int input = 0;

            // Run user loop
            while (!exit) {
                try {
                    System.out.print("[input]: ");
                    input = Integer.parseInt(sc.nextLine());
                    if (input == 1) {
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
            System.out.println("This card cannot be played and has been stored in your deck");
        }
        return null;
    }

    /**
     * @deprecated
     */
    private void adriansInitialVersion() {
        Card currentCard = null;
        Scanner sc = null;
        Deck drawDeck = null;

        int x = 3;
        int input;
        boolean exit = false, exit2 = false;
        if (tutorialMode) {

            x = 4;
            System.out.println("============================================= YOUR TURN (Tutorial)\n" +
                    "\t1. Attempt to Call Uno\n" +
                    "\t2. Reveal Cards\n" +
                    "\t3. Quit and Save Game" +
                    "Your Cards\n");
        } else {
            System.out.println("============================================= YOUR TURN\n" +
                    "\t1. Attempt to Call Uno\n" +
                    "\t2. Quit and Save Game\n" +
                    "Your Cards\n");
        }
        for (int i = 0; i < deck.getNumCards(); i++) {
            System.out.println("\t" + x + ". " + deck.getCard(i));
            x++;
        }
        System.out.println("\t" + x + ". Draw a Card");
        if (tutorialMode) {
            while (!exit) {
                exit2 = false;
                try {
                    input = sc.nextInt();
                    while (!(input > 0 && input <= x)) {
                        if (input == 1) {// player manager for anyone that need to calluno but didn't call uno

                        } else if (input == 2) {
                            // save the game
                        } else if (input == 3) {
                            System.out.println("\t1. Reveal a Player" +
                                    "\t2. Reveal Draw Pile" +
                                    "\t3. Reveal Discard Pile" +
                                    "\t4. Go Back");
                            while (!exit2) {
                                try {
                                    input = sc.nextInt();
                                    while (!(input > 0 && input <= 4)) {
                                        if (input == 1) {

                                        } else if (input == 2) {

                                        } else if (input == 3) {
                                        } else if (input == 4) {
                                            exit2 = true;
                                        }

                                    }
                                } catch (InputMismatchException ie) {
                                    System.out.println("Please enter a valid number!!");
                                }
                            }

                        } else if (input > 3 && input < x) {
                            while (!exit2) {
                                System.out.println("\t1. Play Card" +
                                        "\t2. Explain Definition" + "\t3. Go back");
                                try {
                                    input = sc.nextInt();
                                    if (input == 1) {
                                        if (deck.getCard(input - 1).isValidMove(currentCard)) {
                                            exit2 = true;
                                            exit = true;
                                            System.out.println("You played" + deck.getCard(input - 1));
                                            System.out.println(deck.getNumCards() + "cards in hand.");
                                            if (deck.getNumCards() == 1) {
                                                System.out.println("You played" + deck.getCard(input - 1));
                                                System.out.println("1 Card in hand.\nYou called Uno");
                                            }

                                        } else {
                                            System.out.println("You cannot play this card");
                                        }
                                    } else if (input == 2) {
                                        System.out.println(
                                                deck.getCard(input - 1) + ":\n"
                                                        + deck.getCard(input - 1).getDefinition());
                                    } else if (input == 3) {
                                        exit2 = true;
                                    } else {
                                        System.out.println("This is not a valid number");
                                    }
                                } catch (InputMismatchException ie) {
                                    System.out.println("Please enter a valid number!!");
                                }
                            }
                        } else if (input == x) {
                            System.out.println("You couldn't go.\nYou drew a Card");
                            System.out.println(deck.getNumCards() + "cards in hand.");
                            exit = true;
                        } else {
                            System.out.println("Enter a number within the range.");
                        }

                    }
                } catch (InputMismatchException ie) {
                    System.out.println("Please enter a valid number!!");
                }
            }
        } else {
            while (!exit) {
                exit2 = false;
                try {
                    input = sc.nextInt();
                    while ((input > 0 && input <= x) && !exit) {
                        if (input == 1) {// player manager for anyone that need to calluno but didn't call uno

                        } else if (input == 2) {
                            // save the game
                        } else if (input > 2 && input < x) {
                            System.out.println("\t1. Play Card" +
                                    "\t2. Explain Definition");
                            while (!exit2) {
                                try {
                                    input = sc.nextInt();
                                    if (input == 1) {
                                        if (deck.getCard(input - 1).isValidMove(currentCard)) {
                                            exit = true;
                                            exit2 = true;
                                            System.out.println("You played" + deck.getCard(input - 1));
                                            System.out.println(deck.getNumCards() + "cards in hand.");
                                            if (deck.getNumCards() == 1) {
                                                System.out.println("You played" + deck.getCard(input - 1));
                                                System.out.println("1 Card in hand.\nYou called Uno");
                                            }
                                        } else {
                                            System.out.println("You cannot play this card.");
                                        }
                                    } else if (input == 2) {
                                        System.out.println(
                                                deck.getCard(input - 1) + ":\n"
                                                        + deck.getCard(input - 1).getDefinition());
                                    } else {
                                        System.out.println("This is not a valid number");
                                    }
                                } catch (InputMismatchException ie) {
                                    System.out.println("Please enter a valid number!!");
                                }
                            }
                        } else if (input == x) {
                            System.out.println("You couldn't go.\nYou drew a Card");
                            deck.moveCard(drawDeck, drawDeck.drawRandom());
                            System.out.println(deck.getNumCards() + "cards in hand.");
                            exit = true;
                        } else {
                            System.out.println("Enter a number within the range.");
                        }
                    }
                } catch (InputMismatchException ie) {
                    System.out.println("Please enter a valid number!!");
                }
            }
        }
    }
}
