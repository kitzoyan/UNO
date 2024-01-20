
/*=============================================================================
|  Cpu.java                                                                   |
|-----------------------------------------------------------------------------|
|  Programmer:  Adrian Lock and Robin Yan                                     |
|  Last Modified:   Jan 18, 2024                                              |
|  Course:  ICS4U1                                                            |
|-----------------------------------------------------------------------------|
|  This class is the child class of the Player class. This class contains all |
|  the logic for a Cpu to operate. With the difficulty level, we can select   |
|  the amount of logic statements to turn on, increasing or decreasing the    |
|  level of “intelligence”. The Cpu has a 50% chance to call Uno.             |
|=============================================================================*/
import java.util.Random;
import Cards.*;

public class Cpu extends Player {
    private int difficulty = 3;
    private boolean calledUno = false;
    private boolean needUno = false;
    private int focusColourNum = 0; // 0 = red, 1 = yellow, 2 = green, 3 = blue

    public Cpu(String name) {
        super(name);
    }

    public Cpu(String name, Deck deck) {
        super(deck, name);
<<<<<<< HEAD
=======
        this.difficulty = difficultly;
>>>>>>> RevealCards-Robin
    }

    public Cpu(String name, Deck deck, int difficulty, boolean calledUno, boolean needUno) {
        super(deck, name);
        this.difficulty = difficulty;
        this.calledUno = calledUno;
        this.needUno = needUno;
    }

    public void setDifficulty(int n) {
        this.difficulty = difficulty;
    }

    /**
     * This method will decide which card the CPU will play according to the
     * difficulty, assume Game wil call uno for the cpu
     * 
     * @return the Card that is valid to play, this method assumes game will be one
     *         transferring the card from deck to deck
     */
    public Card play(Card currentCard, String currentColour, Deck drawDeck) {
        return playWithDifficulty(currentCard, currentColour, drawDeck);
    }

    /**
     * Plays cards according to a mathematical algorithm of logic. Plays the first
     * instance of a valid card found in its deck (searching from reverse). If no
     * valid cards, draw a card.
     * 
     * <pre>
     * Difficulty 1 - If wild card drawn, randomly choose colour
     * Difficulty 2 - If wild card drawn, choose colour with has most cards
     * </pre>
     * 
     * @param currentCard the current card of the game
     * @param current     the current colour of the game
     * @param drawDeck
     * @return a card. Return null if no card was played.
     */
    private Card playWithDifficulty(Card currentCard, String currentColour, Deck drawDeck) {
        int red = deck.searchColourCards("red");
        int yellow = deck.searchColourCards("yellow");
        int green = deck.searchColourCards("green");
        int blue = deck.searchColourCards("blue");

        // Focus on a colour that has the most cards
        if (difficulty > 1) {
            int highestColour = Math.max(green, (Math.max(red, Math.max(yellow, blue))));
            focusColourNum = 0;
            if (yellow == highestColour) {
                focusColourNum = 1;
            } else if (green == highestColour) {
                focusColourNum = 2;
            } else if (blue == highestColour) {
                focusColourNum = 3;
            }
        } else {
            focusColourNum = Math.round((float) Math.random() * 3);
        }

        // Find any valid cards to play, avoiding wild cards
        Card temp = null;
        int lastWild = -1;
        for (int i = 0; i < deck.getNumCards(); i++) {
            temp = deck.getCard(i);
            if (temp.isValidMove(currentCard) || temp.getColour().equals(currentColour)) {
                if (!Uno.getCurrentGame().CpuPanic() && difficulty < 3) {
                    if (!temp.getColour().equals("black")) {
                        return temp;
                    } else {
                        lastWild = i;
                    }

                    // If panicking
                } else {
                    if (temp.getColour().equals("black")) {
                        lastWild = i;
                    }
                }

            }
        }

        // Now for wild cards, last resort, only if not panic
        if (lastWild != -1) {
            // If difficulty > 2, play +4 cards strategically over ColourChange
            if (difficulty > 2) {
                if (Uno.getCurrentGame().CpuPanic()) {
                    return deck.getCard(deck.searchSpecificCard("black", "PlusFour"));
                } else {
                    return deck.getCard(deck.searchSpecificCard("black", "ColourChange"));
                }
            } else {
                return deck.getCard(lastWild); // Should be wildcard
            }
        }

        // Just find any card that works at this point if no wild cards
        for (int i = 0; i < deck.getNumCards(); i++) {
            temp = deck.getCard(i);
            if (temp.isValidMove(currentCard) || temp.getColour().equals(currentColour)) {
                return temp;
            }
        }

        // If there were absolutely no valid cards to play
        Card newDrawn = drawDeck.drawRandom();
        deck.moveCard(drawDeck, newDrawn);
        System.out.println(name + " drew a card");
        if (newDrawn.isValidMove(currentCard) || newDrawn.getColour().equals(currentColour)) {
            // callUno();
            return newDrawn;
        }
        return null;
    }

    /**
     * Has a 50% chance to call UNO
     */
    public void callUno() {
        if (deck.getNumCards() > 1) {
            calledUno = false;
            needUno = false;
            return;
        } else if (deck.getNumCards() == 1) {
            needUno = true;
            int rand = Math.round((float) Math.random());
            if (rand == 1) {
                System.out.println("\n" + name + " called Uno\n");
                this.calledUno = true;
            }
        } else {
            System.out.println("SYSTEM: (Cpu) The game should now end!");
        }
    }

    public boolean getCalledUno() {
        return calledUno;
    }

    public boolean getNeedUno() {
        return needUno;
    }

    public int getFocusColourNum() {
        return focusColourNum;
    }

}
