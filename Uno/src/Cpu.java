
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
     * difficulty
     * 1: will play the first card that is valid
     * 2: will play the color that has the most card
     */
    public Card play(Card currentCard) {

        if (deck.getNumCards() > 1) {
            calledUno = false;
            needUno = false;
        }
        for (int i = 0; i < deck.getNumCards(); i++) {
            if (deck[i].isValidMove(currentCard)) {
                System.out.println("============================================" + name + "'s turn");
                System.out.println(name + " played " + deck[i]);
                System.out.println(deck.getNumCards() + "cards in hand.");
                if (deck.getNumCards() == 1) {
                    calledUno();
                }
                return deck[i];
            }
        }
        // draw card if it reaches
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
                    if (deck[i].isValidMove(currentCard)) {
                        return deck[i];
                    }
                }

            } else if (difficultly == 2) {
                int yellow = deck.searcColourCards("yellow");
                int blue = deck.searchColourCards("blue");
                int red = deck.searchColourCards("red");
                int green = deck.searchColourCards("green");

                if (red > blue && red > green && red > yellow) {
                    for (int i = 0; i < deck.getNumCards(); i++) {
                        if (deck[i].isValidMove(currentCard) && deck[i].getColor().equals("red")) {
                            return deck[i];
                        }
                    }
                } else if (green > red && green > blue && green > yellow) {
                    for (int i = 0; i < deck.getNumCards(); i++) {
                        if (deck[i].isValidMove(currentCard) && deck[i].getColor().equals("green")) {
                            return deck[i];
                        }
                    }
                } else if (blue > red && blue > green && blue > yellow) {
                    for (int i = 0; i < deck.getNumCards(); i++) {
                        if (deck[i].isValidMove(currentCard) && deck[i].getColor().equals("blue")) {
                            return deck[i];
                        }
                    }
                } else if (yellow > blue && yellow > green && yellow > red) {
                    for (int i = 0; i < deck.getNumCards(); i++) {
                        if (deck[i].isValidMove(currentCard) && deck[i].getColor().equals("yellow")) {
                            return deck[i];
                        }
                    }
                }

                // this will run when there is no valid move for the best color
                for (int i = 0; i < deck.getNumCards(); i++) {
                    if (deck[i].isValidMove(currentCard)) {
                        return deck[i];
                    }
                }

            } else if (difficultly == 3) {
                int yellow = deck.searcColourCards("yellow");
                int blue = deck.searchColourCards("blue");
                int red = deck.searchColourCards("red");
                int green = deck.searchColourCards("green");

                if (red > blue && red > green && red > yellow) {
                    for (int i = 0; i < deck.getNumCards(); i++) {
                        if (deck[i].isValidMove(currentCard) && deck[i].getColor().equals("red")) {
                            return deck[i];
                        }
                    }
                } else if (green > red && green > blue && green > yellow) {
                    for (int i = 0; i < deck.getNumCards(); i++) {
                        if (deck[i].isValidMove(currentCard) && deck[i].getColor().equals("green")) {
                            return deck[i];
                        }
                    }
                } else if (blue > red && blue > green && blue > yellow) {
                    for (int i = 0; i < deck.getNumCards(); i++) {
                        if (deck[i].isValidMove(currentCard) && deck[i].getColor().equals("blue")) {
                            return deck[i];
                        }
                    }
                } else if (yellow > blue && yellow > green && yellow > red) {
                    for (int i = 0; i < deck.getNumCards(); i++) {
                        if (deck[i].isValidMove(currentCard) && deck[i].getColor().equals("yellow")) {
                            return deck[i];
                        }
                    }
                } else {
                    for (int i = 0; i < deck.getNumCards(); i++) {
                        if (deck[i].isValidMove(currentCard)) {
                            return deck[i];
                        }
                    }
                }
            }

        // draw card if it reaches
    }

    public void callUno() {
        Random rand = new Random();
        if (rand.nextInt(1) == 0) {
            this.calledUno = true;
        }
    }

}
