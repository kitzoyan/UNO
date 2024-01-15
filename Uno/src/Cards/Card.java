package Cards;

public abstract class Card {
    protected String colour;

    public Card(String colour) {
        this.colour = colour;
    }

    public abstract String getDefinition();

    public abstract boolean isValidMove(Card other);

    public abstract boolean getSkipNext();

    public abstract int getDraw();

    public abstract boolean equals(Card other);

    public String toString() {
        return colour + " ";
    }

    public String getColour() {
        return colour;
    }
}
