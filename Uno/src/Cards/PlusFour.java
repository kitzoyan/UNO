package Cards;

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
