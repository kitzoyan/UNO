package Cards;
/*=============================================================================
|  ColourChange.java                                                          |
|-----------------------------------------------------------------------------|
|  Programmer:  Adrian Lock and Robin Yan                                     |
|  Last Modified:   Jan 19, 2024                                              |
|  Course:  ICS4U1                                                            |
|-----------------------------------------------------------------------------|
|  This class is a child card class that extends the class Card. All the      |
|  cards that is a Color change card falls into this category, having a       |
|  default definition and isValidMove.                                        |
|=============================================================================*/

public class ColourChange extends Card {
    private static String definition;
    private static final boolean SKIPNEXT = false;
    private static final int DRAW = 0;
    private static final String DEFAULT_COLOUR = "black";

    /**
     * Creates a new Colour Change card with a default black colour
     */
    public ColourChange() {
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
        return "wild change colour";
    }

    /**
     * @param other the card being compared
     * @return <code>true</code> if the both cards are the same colour and type
     */
    public boolean equals(Card other) {
        if (other == null) {
            return false;
        }
        return (other instanceof ColourChange);
    }

    public String getType() {
        return "C";
    }
}
