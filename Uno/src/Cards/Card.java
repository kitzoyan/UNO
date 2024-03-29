package Cards;
/*=============================================================================
|  Card.java                                                                  |
|-----------------------------------------------------------------------------|
|  Programmer:  Adrian Lock and Robin Yan                                     |
|  Last Modified:   Jan 19, 2024                                              |
|  Course:  ICS4U1                                                            |
|-----------------------------------------------------------------------------|
|  This is an abstract class that contains the methods for every type of card.|
|  It is responsible to set the definition of a card and check if it is valid |
|  by comparing colour and values with another card.                           |
|=============================================================================*/

public abstract class Card {
    protected String colour;

    /**
     * Creates a new card object
     * 
     * @param colour the colour of the card
     */
    public Card(String colour) {
        this.colour = colour;
    }

    public abstract String getDefinition();

    public abstract boolean isValidMove(Card other);

    public abstract boolean getSkipNext();

    public abstract int getDraw();

    public abstract boolean equals(Card other);

    public abstract String getType();

    public String toString() {
        return colour + " ";
    }

    public String getColour() {
        return colour;
    }

}
