import java.util.InputMismatchException;
import java.util.Scanner;

import Cards.Card;

public class Human extends Player {
    private boolean tutorialMode = false;

    public Human(String name) {
        super(name);
    }

    public Human(String name, Deck deck, boolean tutorialMode) {
        super(deck, name);
        this.tutorialMode = tutorialMode;
    }

    public void turnOnTutorial() {
        tutorialMode = true;
    }

    public Card play(Card currentCard) {
        int x = 3;
        int input;
        boolean exit = false, exit2 = false;
        Scanner sc = new Scanner(System.in);
        if (tutorialMode) {
            x = 4;
            System.out.println("============================================= YOUR TURN\n" +
                    "\t1. Attempt to Call Uno\n" +
                    "\t2. Reveal Cards\n" +
                    "\t3. Quit and Save Game" +
                    "Your Cards\n");
        } else {
            System.out.println("============================================= YOUR TURN\n" +
                    "\t1. Attempt to Call Uno\n" +
                    "\t2. Quit and Save Game\n" +
                    "Your Cards\n");
        }
        for (int i = 0; i < deck.getNumCards(); i++) {
            System.out.println("\t"+ x+ ". "+ deck[i]);
            x++;
        }
        System.out.println("\t"+ x +". Draw a Card");
        if (tutorialMode) {
            while(!exit){
            try{
                input = sc.nextInt();
                while(!(input > 0 && input < x)){
                    if(input == 1 && ){//player manager for anyone that need to calluno but didn't call uno
                        
                    }else if(input == 2){
                        //save the game
                    }else if(input == 3){
                        System.out.println("\t1. Reveal a Player"+ 
                        "\t2. Reveal Draw Pile"+
                        "\t3. Reveal Discard Pile" +
                        "\t4. Go Back"
                        );
                        while(!exit2){
                            try{
                                input = sc.nextInt();
                                while(!(input > 0 && input <= 4)){
                                    if(input == 1){

                                    }else if(input ==2){
                                        
                                    }else if(input ==3){
                                    }else if(input == 4){
                                    }
                                    
                        }
                            }catch(InputMismatchException ie){
                                System.out.println("Please enter a valid number!!");
                            }
                        }

                    }
                }
            }catch(InputMismatchException ie){
                System.out.println("Please enter a valid number!!");
            }
        }
        }else{

        }
        
        
    }
}
