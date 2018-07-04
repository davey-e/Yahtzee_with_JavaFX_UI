package yahtzee1.Yahtzee;

import java.util.concurrent.ThreadLocalRandom;

public class Die {

    private int value;
    private boolean holdStatus;

    public Die(){
        this.value = 0;
        this.holdStatus = false;
    }

    public int getValue() {
        return this.value;
    }

    public boolean getHoldStatus() {
        return this.holdStatus;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setHoldStatus(boolean holdStatus) {
        this.holdStatus = holdStatus;
    }

    public void rollDie() {
        if (!this.holdStatus){
            int randomNum = ThreadLocalRandom.current().nextInt(1, 7);
            this.setValue(randomNum);
        }

    }
}
