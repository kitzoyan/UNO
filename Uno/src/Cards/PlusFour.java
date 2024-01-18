package Cards;
/*=============================================================================
|  PlusFour.java                                                              |
|-----------------------------------------------------------------------------|
|  Programmer:  Adrian Lock and Robin Yan                                     |
|  Last Modified:   Jan 18, 2024                                              |
|  Course:  ICS4U1                                                            |
|-----------------------------------------------------------------------------|
|  This class is the child card extends from the class Card. All the cards    |
|  that is a Plus four card falls into this category, having a default        |
|  definition and isValidMove.                                                |
|=============================================================================*/

public class PlusFour extends Card {
    private static String definition;
    private static final boolean SKIPNEXT = true;
    private static final int DRAW = 4;
    private static final String DEFAULT_COLOUR = "black";

    public PlusFour() {
        super(DEFAULT_COLOUR);
    }

    public String getDefinition() {
        return colour + ": " + definition;
    }

    public static void setDefinition(String d) {
        definition = d;
    }

    public int getDraw() {
        return DRAW;
    }

    public boolean getSkipNext() {
        return SKIPNEXT;
    }

    /**
     * @return <code>true</code> at all times
     */
    public boolean isValidMove(Card other) {
        return true;
    }

    public String toString() {
        return "wild +4";
    }

    public boolean equals(Card other) {
        if (other == null) {
            return false;
        }
        return (other instanceof PlusFour);
    }
}
