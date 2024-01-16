
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
    public Card play(Card currentCard) {

        for (int i = 0; i < deck.getNumCards(); i++) {
            if (deck.getCard(i).isValidMove(currentCard)) {
                // System.out.println("============================================" + name +
                // "'s turn");
                // System.out.println(name + " played " + deck.getCard(i));
                // System.out.println(deck.getNumCards() + "cards in hand.");
                callUno();
                return deck.getCard(i);
            }
        }
        Card drawnCard = drawRandom;
        if (drawnCard.isValidMove(currentCard)) {
            return drawnCard;
        }

        deck.addCard(drawnCard);
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
    public Card playWithDifficulty(Card currentCard) {
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
    }

    /**
     * Has a 50% chance to call UNO
     */
    public void callUno() {
        if (deck.getNumCards() > 1) {
            calledUno = false;
            needUno = false;
        } else {
            Random rand = new Random();
            if (rand.nextInt(1) == 0) {
                System.out.println(name + "called Uno");
                this.calledUno = true;
            }
        }
    }

}
