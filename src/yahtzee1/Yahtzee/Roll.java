import java.util.ArrayList;

public class Roll {

    public static void rollDice(ArrayList<Die> dice){
        int numberOfDice = dice.size();
        for(int i = 0; i < numberOfDice; i++){
            dice.get(i).rollDie();
        }
    }

    public static void holdDice(ArrayList<Die> dice, ArrayList<Boolean> diceToHold) {
        int numberOfDice = dice.size();
        int numberOfDiceToHold = diceToHold.size();
        if( numberOfDice == numberOfDiceToHold){
            for(int i = 0; i < numberOfDice; i++){
                dice.get(i).setHoldStatus(diceToHold.get(i));
            }
        }
    }
}
