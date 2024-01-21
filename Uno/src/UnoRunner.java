
/*=============================================================================
|  UnoRunner.java                                                             |
|-----------------------------------------------------------------------------|
|  Programmer:  Adrian Lock and Robin Yan                                     |
|  Last Modified:   Jan 21, 2024                                              |
|  Course:  ICS4U1                                                            |
|-----------------------------------------------------------------------------|
|  This class will run the Uno game by calling Uno.run() inside its main      |
|  method.                                                                    |
|=============================================================================*/
import java.util.*;
import Cards.*;

public class UnoRunner {
    public final static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Uno.run();
    }
}
