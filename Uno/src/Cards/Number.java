package Cards;

public class Number extends Card {
    private static String definition;
    private static final boolean SKIPNEXT = false;
    private static final int DRAW = 0;
    private int number;

    public Number(String colour, int number) {
        super(colour);
        this.number = number;
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
     * @return <code>true</code> if the implicit card has the same number,
     *         or the same colour as the previous card
     */
    public boolean isValidMove(Card other) {
        if (other.colour.equals(colour)) {
            return true;
        } else if (other instanceof Number) {
            if (((Number) other).number == number) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        return super.toString() + number;
    }

    public boolean equals(Card other) {
        if (other == null) {
            return false;
        }
        if (colour.equals(other.colour)) {
            if (other instanceof Number) {
                if (((Number) other).number == number) {
                    return true;
                }
            }
        }
        return false;
    }

    public int getNumber() {
        return number;
    }
}
