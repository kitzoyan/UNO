
/*=============================================================================
|  Player.java                                                                |
|-----------------------------------------------------------------------------|
|  Programmer:  Adrian Lock and Robin Yan                                     |
|  Last Modified:   Jan 21, 2024                                              |
|  Course:  ICS4U1                                                            |
|-----------------------------------------------------------------------------|
|  This class is the parent class of Cpu and Human class. This is an abstract |
|  class that contains the abstract method for any type of player to play     |
|  their card.                                                                |
|=============================================================================*/
import Cards.*;

public abstract class Player {
    protected String name;
    protected Deck deck;

    /**
     * Creates a new player with a name and a default empty deck
     * 
     * @param name a String representing the name of the player
     */
    public Player(String name) {
        this.name = name;
        deck = new Deck();
    }

    /**
     * Creates a new player with a name and a pre-set deck.
     * This method should be used to resume a player in a saved game.
     * 
     * @param deck the pre-set deck
     * @param name a String representing the name of the player
     */
    public Player(Deck deck, String name) {
        this.name = name;
        this.deck = deck;
    }

    public String getName() {
        return name;
    }

    public Deck getDeck() {
        return deck;
    }

    public abstract Card play(Card currentCard, String currentColour, Deck drawDeck);
}
