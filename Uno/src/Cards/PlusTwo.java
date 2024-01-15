package Cards;

public class PlusTwo extends Card {
    private static String definition;
    private static final boolean SKIPNEXT = true;
    private static final int DRAW = 2;

    public PlusTwo(String colour) {
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
     * @return <code>true</code> if the implicit card has the same colour,
     *         or if the explicit is also a PlusTwo
     */
    public boolean isValidMove(Card other) {
        return (other instanceof PlusTwo || other.colour.equals(colour));
    }

    public String toString() {
        return super.toString() + "+2";
    }

    public boolean equals(Card other) {
        if (other == null) {
            return false;
        }
        return (other.colour.equals(colour) && other instanceof PlusTwo);
    }
}
