
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
    private int difficultly = 4;
    private boolean calledUno = false;
    private boolean needUno = false;

    public Cpu(String name) {
        super(name);
    }

    public Cpu(String name, Deck deck, int difficultly) {
        super(deck, name);
        this.difficultly = difficultly;
    }

    public Cpu(String name, Deck deck, int difficultly, boolean calledUno, boolean needUno) {
        super(deck, name);
        this.difficultly = difficultly;
        this.calledUno = calledUno;
        this.needUno = needUno;
    }

    public void setDifficulty(int n) {
        this.difficultly = difficultly;
    }

    /**
     * This method will decide which card the CPU will play according to the
     * difficulty, assume Game wil call uno for the cpu
     * 
     * @return the Card that is valid to play, this method assumes game will be one
     *         transferring the card from deck to deck
     */
    public Card play(Card currentCard, String currentColour, Deck drawDeck) {
        return playWithNoDifficulty(currentCard, currentColour, drawDeck);
    }

    /**
     * Plays a the first instance of a valid card found in its deck. If no valid
     * cards, draw a card
     * 
     * @param currentCard
     * @param drawDeck
     * @return a card. Return null if no card was played.
     */
    private Card playWithNoDifficulty(Card currentCard, String currentColour, Deck drawDeck) {
        for (int i = 0; i < deck.getNumCards(); i++) {
            System.out.println("CPU is thinking of plaing... " + currentColour + " " + deck.getCard(i).getColour());
            if (deck.getCard(i).isValidMove(currentCard) || deck.getCard(i).getColour().equals(currentColour)) {
                callUno();
                return deck.getCard(i);
            }
        }
        Card newDrawn = drawDeck.drawRandom();
        deck.moveCard(drawDeck, newDrawn);
        System.out.println("Cpu drew a card. " + currentColour + " " + newDrawn.getColour());
        if (newDrawn.isValidMove(currentCard) || newDrawn.getColour().equals(currentColour)) {
            return newDrawn;
        }
        return null;
    }

    /**
     * Plays cards according to a mathematical algorithm of logic. This method is
     * for future update.
     * 
     * @param currentCard
     * @return
     * @deprecated
     */
    private Card playWithDifficulty(Card currentCard) {
        if (difficultly > 4)

            if (difficultly == 1) {
                for (int i = 0; i < deck.getNumCards(); i++) {
                    if (deck.getCard(i).isValidMove(currentCard)) {
                        return deck.getCard(i);
                    }
                }

            } else if (difficultly == 2) {
                int yellow = deck.searchColourCards("yellow");
                int blue = deck.searchColourCards("blue");
                int red = deck.searchColourCards("red");
                int green = deck.searchColourCards("green");

                if (red > blue && red > green && red > yellow) {
                    for (int i = 0; i < deck.getNumCards(); i++) {
                        if (deck.getCard(i).isValidMove(currentCard) && deck.getCard(i).getColour().equals("red")) {
                            return deck.getCard(i);
                        }
                    }
                } else if (green > red && green > blue && green > yellow) {
                    for (int i = 0; i < deck.getNumCards(); i++) {
                        if (deck.getCard(i).isValidMove(currentCard) && deck.getCard(i).getColour().equals("green")) {
                            return deck.getCard(i);
                        }
                    }
                } else if (blue > red && blue > green && blue > yellow) {
                    for (int i = 0; i < deck.getNumCards(); i++) {
                        if (deck.getCard(i).isValidMove(currentCard) && deck.getCard(i).getColour().equals("blue")) {
                            return deck.getCard(i);
                        }
                    }
                } else if (yellow > blue && yellow > green && yellow > red) {
                    for (int i = 0; i < deck.getNumCards(); i++) {
                        if (deck.getCard(i).isValidMove(currentCard) && deck.getCard(i).getColour().equals("yellow")) {
                            return deck.getCard(i);
                        }
                    }
                }

                // this will run when there is no valid move for the best color
                for (int i = 0; i < deck.getNumCards(); i++) {
                    if (deck.getCard(i).isValidMove(currentCard)) {
                        return deck.getCard(i);
                    }
                }

            } else if (difficultly == 3) {
                int yellow = deck.searchColourCards("yellow");
                int blue = deck.searchColourCards("blue");
                int red = deck.searchColourCards("red");
                int green = deck.searchColourCards("green");

                if (red > blue && red > green && red > yellow) {
                    for (int i = 0; i < deck.getNumCards(); i++) {
                        if (deck.getCard(i).isValidMove(currentCard) && deck.getCard(i).getColour().equals("red")) {
                            return deck.getCard(i);
                        }
                    }
                } else if (green > red && green > blue && green > yellow) {
                    for (int i = 0; i < deck.getNumCards(); i++) {
                        if (deck.getCard(i).isValidMove(currentCard) && deck.getCard(i).getColour().equals("green")) {
                            return deck.getCard(i);
                        }
                    }
                } else if (blue > red && blue > green && blue > yellow) {
                    for (int i = 0; i < deck.getNumCards(); i++) {
                        if (deck.getCard(i).isValidMove(currentCard) && deck.getCard(i).getColour().equals("blue")) {
                            return deck.getCard(i);
                        }
                    }
                } else if (yellow > blue && yellow > green && yellow > red) {
                    for (int i = 0; i < deck.getNumCards(); i++) {
                        if (deck.getCard(i).isValidMove(currentCard) && deck.getCard(i).getColour().equals("yellow")) {
                            return deck.getCard(i);
                        }
                    }
                } else {
                    for (int i = 0; i < deck.getNumCards(); i++) {
                        if (deck.getCard(i).isValidMove(currentCard)) {
                            return deck.getCard(i);
                        }
                    }
                }
            }

        // draw card if it reaches
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
            Random rand = new Random();
            if (rand.nextInt(1) == 0) {
                System.out.println(name + "called Uno");
                this.calledUno = true;
            }
        } else {
            System.out.println("SYSTEM: (Cpu) The game should have ended!");
        }
    }

    public boolean getCalledUno() {
        return calledUno;
    }

    public boolean getNeedUno() {
        return needUno;
    }

}
