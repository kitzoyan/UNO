/*=============================================================================
|  Tutorial.java                                                              |
|-----------------------------------------------------------------------------|
|  Programmer:  Adrian Lock and Robin Yan                                     |
|  Last Modified:   Jan 18, 2024                                              |
|  Course:  ICS4U1                                                            |
|-----------------------------------------------------------------------------|
|  This class is an extension of Game. There are a set of additional features,|
|  explicit explaining of each card’s function when they are played, being    |
|  able to look at other people’s hand, being able to look through discard and|
|  draw piles. This game mode is designed for the new players who wish to     |
|  learn how to play without the need of a real person to help.               |
|=============================================================================*/
public class Tutorial extends Game {

    /**
     * Class Constructor
     * Uses to create a new tutorial game, turns on tutorial mode in Human player
     * 
     * @param gameName   a String representing the name of the game
     * @param playerName a String representing the name of the player
     * @param cpu1       a String representing the name of cpu 1
     * @param cpu2       a String representing the name of cpu 2
     * @param cpu3       a String representing the name of cpu 3
     */

    public Tutorial(String gameName, String playerName, String cpu1, String cpu2, String cpu3, Deck fullDeck) {
        super(gameName, playerName, cpu1, cpu2, cpu3, fullDeck);
        Human temp = (Human) (players.searchPlayer(playerName));
        temp.toggleTutorial();
    }

    /**
     * Class Constructor
     * Uses to create a new tutorial game, turns on tutorial mode in Human player
     *
     * @param fileName the specified file name that stores the game file
     */
    public Tutorial(String fileName, Deck fullDeck) {
        super(fileName, fullDeck);
        Human temp = (Human) (players.searchHuman());
        temp.toggleTutorial();

    }

    /**
     * Reveals the hand of the specified player, returns true if found, false if not
     * exist
     * 
     * @param name the specified name to search for
     * @return a boolean indicating if the player has found
     */
    public boolean revealPlayer(String name) {
        Player temp = players.searchPlayer(name);
        if (temp != null) {
            System.out.println(temp.getDeck());
            return true;
        } else {
            return false;
        }
    }

    /**
     * Reveals the discard pile
     */
    public void revealDiscard() {
        System.out.println("Cards in discard Pile: " + discardPile);

    }

    /**
     * Reveals the draw pile
     */
    public void revealDraw() {
        System.out.println("Cards in draw Pile: " + drawPile);
    }

    /**
     * Returns the number of cards in the draw pile and discard pile
     * 
     * @param colour the specified color to search for
     * @param type   the specified type to search for
     * @return an int representing the number of the specified cards
     */
    public int searchPublicDraw(String colour, String type) { // don't know about the deck
        return drawPile.searchSpecificCard(colour, type) + discardPile.searchSpecificCard(colour, type);
    }

    public String gameType() {
        return "tutorial";
    }
}
