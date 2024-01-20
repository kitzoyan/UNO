
/*=============================================================================
|  Cpu.java                                                                   |
|-----------------------------------------------------------------------------|
|  Programmer:  Adrian Lock and Robin Yan                                     |
|  Last Modified:   Jan 19, 2024                                              |
|  Course:  ICS4U1                                                            |
|-----------------------------------------------------------------------------|
|  This class is the child class of the Player class. This class contains all |
|  the logic for a Cpu to operate. With the difficulty level, we can select   |
|  the amount of logic statements to turn on, increasing or decreasing the    |
|  level of “intelligence” when it plays cards. The Cpu has a 50% chance to   |
|  call Uno.                                                                  |
|=============================================================================*/
import java.util.Random;
import Cards.*;

public class Cpu extends Player {
    private int difficulty = 3;
    private boolean calledUno = false;
    private boolean needUno = false;
    private int focusColourNum = 0; // 0 = red, 1 = yellow, 2 = green, 3 = blue

    /**
     * Creates a new Cpu with name and difficulty level with an empty deck
     * 
     * @param name
     * @param difficulty
     */
    public Cpu(String name, int difficulty) {
        super(name);
        this.difficulty = difficulty;
    }

    /**
     * Creates a new Cpu with a name, difficulty, and preset deck
     * 
     * @param name
     * @param deck
     * @param difficulty
     */
    public Cpu(String name, Deck deck, int difficulty) {
        super(deck, name);
        this.difficulty = difficulty;
    }

    /**
     * Creates a new Cpu based off of the following parameters. This should be used
     * when loading game.
     * 
     * @param name       the name of Cpu
     * @param deck       the preset deck for the Cpu
     * @param difficulty the difficulty level
     * @param calledUno  if the Cpu called Uno
     * @param needUno    if the Cpu needs to call Uno
     */
    public Cpu(String name, Deck deck, int difficulty, boolean calledUno, boolean needUno) {
        super(deck, name);
        this.difficulty = difficulty;
        this.calledUno = calledUno;
        this.needUno = needUno;
    }

    public void setDifficulty(int n) {
        this.difficulty = difficulty;
    }

    public int getDifficulty() {
        return difficulty;
    }

    /**
     * Decides which card the CPU will play according to the difficulty. Assume
     * Game will callUno for the cpu
     * 
     * @param currentCard   the current card (previous card) played
     * @param currentColour the current colour of the game
     * @param drawDeck      the public draw pile
     * @return a Card that is valid to play. This method assumes Game will be one
     *         transferring the card from player deck to discard pile. Returns null
     *         if CPU drew a card and did not play any.
     */
    public Card play(Card currentCard, String currentColour, Deck drawDeck) {
        return playWithDifficulty(currentCard, currentColour, drawDeck);
    }

    /**
     * Determines the right choice for cards according to a mathematical algorithm
     * of logic.
     * 
     * <pre>
     * Difficulty 1 - Play the first instance of a valid card. If wild card drawn, randomly choose colour
     * Difficulty 2 - Same with above, but choose colour for wild card based on colour set containg the most cards
     * Difficulty 3 - If others players approach "Uno," Cpu will prioritize using wild cards (+4 if owned) to prevent others from winning
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

                // If the cpu is difficulty 3 is not panicing
                if ((!Uno.getCurrentGame().CpuPanic() && difficulty > 2) || difficulty < 3) {
                    if (!temp.getColour().equals("black")) {
                        return temp;
                    } else {
                        lastWild = i;
                    }
                    // If the Cpu is difficulty 3 and is panicing
                } else {
                    lastWild = i;
                }
            }
        }
        // Wild cards are last resort except during panic for CPU > 2
        if (lastWild != -1) {
            return deck.getCard(lastWild); // Play the last wild card(usually +4)
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
            return newDrawn;
        }
        return null;
    }

    /**
     * Determines whether CPU will call Uno if they have 1 card left. There is a 50%
     * chance to call UNO, meaning if they don't they are punishable.
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
