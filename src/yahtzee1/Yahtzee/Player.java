import java.util.ArrayList;
import java.util.Arrays;

public class Player {

    private String name;
    private ArrayList<Die> dice;
    private ScoreSheet scoreSheet;
    private int rollCount;


    public Player(String name){
        this.name = name;
        this.dice = new ArrayList<>();
        setupDice();
        this.scoreSheet = new ScoreSheet();
        this.rollCount = 0;
    }


    private void setupDice(){
        Die die1 = new Die();
        Die die2 = new Die();
        Die die3 = new Die();
        Die die4 = new Die();
        Die die5 = new Die();
        this.dice.addAll(Arrays.asList(die1, die2, die3, die4, die5));
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<Die> getDice() {
        return this.dice;
    }

    public ScoreSheet getScoreSheet() {
        return this.scoreSheet;
    }

    public int getRollCount() {
        return this.rollCount;
    }

    public void playTurn() {
        Roll.rollDice(this.dice);
        this.rollCount += 1;
    }

    public void playTurn(ArrayList<Boolean> diceToHold) {
        Roll.holdDice(this.dice, diceToHold);
        Roll.rollDice(this.dice);
        this.rollCount += 1;
    }

    public int setScore(ScoreLine scoreline){
        int setScoreStatus = this.scoreSheet.setSingleScore(scoreline, this.dice);
        this.rollCount = 0;
        return setScoreStatus;
    }

    public void resetDiceHoldStatus() {
        for (int i = 0; i < 5; i++){
            this.getDice().get(i).setHoldStatus(false);
        }
    }
}
