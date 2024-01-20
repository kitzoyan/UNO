package Cards;
/*=============================================================================
|  PlusFour.java                                                              |
|-----------------------------------------------------------------------------|
|  Programmer:  Adrian Lock and Robin Yan                                     |
|  Last Modified:   Jan 19, 2024                                              |
|  Course:  ICS4U1                                                            |
|-----------------------------------------------------------------------------|
|  This class is a child card class that extends the class Card. All cards    |
|  that are Plus four cards fall into this category, having a default         |
|  definition and isValidMove.                                                |
|=============================================================================*/

public class PlusFour extends Card {
    private static String definition;
    private static final boolean SKIPNEXT = true;
    private static final int DRAW = 4;
    private static final String DEFAULT_COLOUR = "black";

    /**
     * Creates a new Plus Four Card. Default colour is black
     */
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
     * @param other the explicit card that the implicit is being played on top of
     * @return <code>true</code> at all times
     */
    public boolean isValidMove(Card other) {
        return true;
    }

    public String toString() {
        return "wild +4";
    }

    /**
     * @param other the card being compared
     * @return <code>true</code> if the both cards are the same colour and type
     */
    public boolean equals(Card other) {
        if (other == null) {
            return false;
        }
        return (other instanceof PlusFour);
    }

    public String getType() {
        return "F";
    }
}
