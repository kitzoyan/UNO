
/*=============================================================================
|  Player.java                                                                |
|-----------------------------------------------------------------------------|
|  Programmer:  Adrian Lock and Robin Yan                                     |
|  Last Modified:   Jan 18, 2024                                              |
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
     * Class Constructor
     * This class create a new player with the name and new deck
     * 
     * @param name a String representing the name of the player
     */
    public Player(String name) {
        this.name = name;
        deck = new Deck();
    }

    /**
     * Class Constructor
     * This class load a player with the given name and new deck
     * 
     * @param name a String representing the name of the player
     */
    public Player(Deck deck, String name) {
        this.name = name;
        this.deck = deck;
    }

    public String getName() {
        return name;
    }

<<<<<<< HEAD
    public Deck getDeck() {
        return deck;
    }

    public abstract Card play(Card currentCard, String currentColour, Deck fullDeck);
=======
    public abstract Card play(Card currentCard, String currentColour, Deck drawDeck);
>>>>>>> Game---Robin
}
