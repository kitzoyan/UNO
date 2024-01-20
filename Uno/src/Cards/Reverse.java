package Cards;
/*=============================================================================
|  Reverse.java                                                               |
|-----------------------------------------------------------------------------|
|  Programmer:  Adrian Lock and Robin Yan                                     |
|  Last Modified:   Jan 19, 2024                                              |
|  Course:  ICS4U1                                                            |
|-----------------------------------------------------------------------------|
|  This class extends from the class Card. All Reverse cards fall into this   |
|  category, having a default definition and isValidMove method.              |
|=============================================================================*/

public class Reverse extends Card {
    private static String definition;
    private static final boolean SKIPNEXT = false;
    private static final int DRAW = 0;

    /**
     * Creates a new Reverse Card
     * 
     * @param colour
     */
    public Reverse(String colour) {
        super(colour);
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
     * @return <code>true</code> if the implicit card has the same colour,
     *         or if the explicit is also a Reverse
     */
    public boolean isValidMove(Card other) {
        return (other instanceof Reverse || other.colour.equals(colour));
    }

    public String toString() {
        return super.toString() + "Reverse";
    }

    /**
     * @param other the card being compared
     * @return <code>true</code> if the both cards are the same colour and type
     */
    public boolean equals(Card other) {
        if (other == null) {
            return false;
        }
        return (other.colour.equals(colour) && other instanceof Reverse);
    }

    public String getType() {
        return "R";
    }
}
