import java.util.*;

public class ScoreSheet {

    private LinkedHashMap<ScoreLine, Integer> scores;
    private int upperSectionScore;
    private int upperSectionBonus;
    private int lowerSectionScore;
    private int totalScore;


    public ScoreSheet(){
        this.scores = new LinkedHashMap<>();
        this.setupScoresHashMap();
        this.upperSectionScore = 0;
        this.upperSectionBonus = 0;
        this.lowerSectionScore = 0;
        this.totalScore = 0;
    }

    private void setupScoresHashMap(){
        this.scores.put(ScoreLine.ONES, null);
        this.scores.put(ScoreLine.TWOS, null);
        this.scores.put(ScoreLine.THREES, null);
        this.scores.put(ScoreLine.FOURS, null);
        this.scores.put(ScoreLine.FIVES, null);
        this.scores.put(ScoreLine.SIXES, null);
        this.scores.put(ScoreLine.THREEOAK, null);
        this.scores.put(ScoreLine.FOUROAK, null);
        this.scores.put(ScoreLine.FH, null);
        this.scores.put(ScoreLine.SMALLSTR, null);
        this.scores.put(ScoreLine.LARGESTR, null);
        this.scores.put(ScoreLine.YAHTZEE, null);
        this.scores.put(ScoreLine.CHANCE, null);
    }

    public LinkedHashMap<ScoreLine, Integer> getScores() {
        return this.scores;
    }

    public int getSingleScore(ScoreLine scoreLine) {
        return this.scores.get(scoreLine);
    }

    public int getUpperSectionScore() {
        return this.upperSectionScore;
    }

    public int getUpperSectionBonus() {
        return this.upperSectionBonus;
    }

    public int getLowerSectionScore() {
        return lowerSectionScore;
    }

    public int getTotalScore() {
        return this.totalScore;
    }

    // This is where the rules of applying a score to a specific scoreline will be enforced, so logic could get messy, but will be a good opportunity for refactoring
    //Logic pseudocode (for upper section only):
    // If scoreLine has not yet been set (i.e. current score == 0) then
    //      check which scoreLine the player wants to assign the score to (case)
    //          iterate through each of the dice in the ArrayList and for each one where the die value == scoreLine value add the die value to a totalizer
    //      add the totalizer score to the scores hashmap
    //
    // the int that this method now returns is a status indicator for the result of this method.
    // 0 = score successfully assigned to the ScoreLine as selected by the Player
    // 1 = no dice match the selected ScoreLine, so score has been set to 0
    // 2 = score has already been assigned to the ScoreLine, so can't be assigned again
    //
    public int setSingleScore(ScoreLine scoreLine, ArrayList<Die> dice) {
        int numberOfDice = dice.size();
        int score = 0;
        if (this.scores.get(scoreLine) == null){
            if (scoreLine.getScoreLineValue() >= 1 && scoreLine.getScoreLineValue() <= 6) {
                for (int i = 0; i < numberOfDice; i++) {
                    int diceValue = dice.get(i).getValue();
                    if (diceValue == scoreLine.getScoreLineValue()) {
                        score += diceValue;
                    }
                }
            } else if (scoreLine.getScoreLineValue() == 7){ //3 of a Kind

                ArrayList<Integer> dieValuesCount = this.countOfDieValues(dice);
                boolean isCorrectMatch = false;
                for (int i = 0; i < 6; i++){
                    if (dieValuesCount.get(i) >= 3){
                        isCorrectMatch = true;
                    }
                }

                if (isCorrectMatch){
                    for (int i = 0; i < numberOfDice; i++) {
                        int diceValue = dice.get(i).getValue();
                        score += diceValue;
                    }
                }

            } else if (scoreLine.getScoreLineValue() == 8){ //4 of a Kind

                ArrayList<Integer> dieValuesCount = this.countOfDieValues(dice);
                boolean isCorrectMatch = false;
                for (int i = 0; i < 6; i++){
                    if (dieValuesCount.get(i) >= 4){
                        isCorrectMatch = true;
                    }
                }

                if (isCorrectMatch){
                    for (int i = 0; i < numberOfDice; i++) {
                        int diceValue = dice.get(i).getValue();
                        score += diceValue;
                    }
                }

            } else if (scoreLine.getScoreLineValue() == 9){ //Full House

                ArrayList<Integer> dieValuesCount = this.countOfDieValues(dice);
                boolean threeDiceMatch = false;
                boolean twoDiceMatch = false;
                for (int i = 0; i < 6; i++){
                    if (dieValuesCount.get(i) == 3){
                        threeDiceMatch = true;
                    }
                }
                for (int i = 0; i < 6; i++){
                    if (dieValuesCount.get(i) == 2){
                        twoDiceMatch = true;
                    }
                }

                if (threeDiceMatch && twoDiceMatch){
                    score = 25;
                    }

            } else if (scoreLine.getScoreLineValue() == 10) { //Small Straight

                boolean isCorrectMatch = false;

                //Remove duplicate values to avoid sets of dice that would falsely give a no match result
                //e.g. 12334
                Set<Die> diceNoDuplicates = new HashSet<>();
                diceNoDuplicates.addAll(dice);
                dice.clear();
                dice.addAll(diceNoDuplicates);

                Collections.sort(dice, new Comparator<Die>() {

                    public int compare(Die die1, Die die2) {
                        return die1.getValue() - die2.getValue();
                    }
                });

                if (dice.size() >= 4) {

                    if (dice.get(0).getValue() == 1 &&
                            dice.get(1).getValue() == 2 &&
                            dice.get(2).getValue() == 3 &&
                            dice.get(3).getValue() == 4) {
                        isCorrectMatch = true;
                    }
                    if (dice.get(0).getValue() == 2 &&
                            dice.get(1).getValue() == 3 &&
                            dice.get(2).getValue() == 4 &&
                            dice.get(3).getValue() == 5) {
                        isCorrectMatch = true;
                    }
                    if (dice.get(0).getValue() == 3 &&
                            dice.get(1).getValue() == 4 &&
                            dice.get(2).getValue() == 5 &&
                            dice.get(3).getValue() == 6) {
                        isCorrectMatch = true;
                    }
                }

                if (dice.size() == 5) {
                    if (dice.get(1).getValue() == 1 &&
                            dice.get(2).getValue() == 2 &&
                            dice.get(3).getValue() == 3 &&
                            dice.get(4).getValue() == 4) {
                        isCorrectMatch = true;
                    }
                    if (dice.get(1).getValue() == 2 &&
                            dice.get(2).getValue() == 3 &&
                            dice.get(3).getValue() == 4 &&
                            dice.get(4).getValue() == 5) {
                        isCorrectMatch = true;
                    }
                    if (dice.get(1).getValue() == 3 &&
                            dice.get(2).getValue() == 4 &&
                            dice.get(3).getValue() == 5 &&
                            dice.get(4).getValue() == 6) {
                        isCorrectMatch = true;
                    }
                    //A large straight is also valid for a small straight
                    if (dice.get(0).getValue() == 1 &&
                            dice.get(1).getValue() == 2 &&
                            dice.get(2).getValue() == 3 &&
                            dice.get(3).getValue() == 4 &&
                            dice.get(4).getValue() == 5) {
                        isCorrectMatch = true;
                    }
                    if (dice.get(0).getValue() == 2 &&
                            dice.get(1).getValue() == 3 &&
                            dice.get(2).getValue() == 4 &&
                            dice.get(3).getValue() == 5 &&
                            dice.get(4).getValue() == 6) {
                        isCorrectMatch = true;
                    }
                }

                if (isCorrectMatch){
                    score = 30;
                }

            } else if (scoreLine.getScoreLineValue() == 11){ //Large Straight

                boolean isCorrectMatch = false;
                Collections.sort(dice, new Comparator<Die>() {

                    public int compare(Die die1, Die die2) {
                        return die1.getValue() - die2.getValue();
                    }
                });

                if (dice.get(0).getValue() == 1 &&
                        dice.get(1).getValue() == 2 &&
                        dice.get(2).getValue() == 3 &&
                        dice.get(3).getValue() == 4 &&
                        dice.get(4).getValue() == 5){
                    isCorrectMatch = true;
                }
                if (dice.get(0).getValue() == 2 &&
                        dice.get(1).getValue() == 3 &&
                        dice.get(2).getValue() == 4 &&
                        dice.get(3).getValue() == 5 &&
                        dice.get(4).getValue() == 6){
                    isCorrectMatch = true;
                }

                if (isCorrectMatch){
                    score = 40;
                }

            } else if (scoreLine.getScoreLineValue() == 12){ //Yahtzee

                ArrayList<Integer> dieValuesCount = this.countOfDieValues(dice);
                boolean isCorrectMatch = false;
                for (int i = 0; i < 6; i++){
                    if (dieValuesCount.get(i) == 5){
                        isCorrectMatch = true;
                    }
                }

                if (isCorrectMatch){
                    score = 50;
                }

            }else if (scoreLine.getScoreLineValue() == 13){ //Chance
                for (int i = 0; i < numberOfDice; i++) {
                    int diceValue = dice.get(i).getValue();
                    score += diceValue;
                }
            }
            if (score > 0){
                this.scores.put(scoreLine, score);
                return 0;
            } else {
                this.scores.put(scoreLine, 0);
                return 1;
            }
        }
        return 2;
    }

    public void setUpperSectionScore(int score) {
        this.upperSectionScore = score;
    }

    public void setTotalScore(int score) {
        this.totalScore = score;
    }

    public void calculateUpperSectionScore() {
        this.upperSectionScore += scores.get(ScoreLine.ONES);
        this.upperSectionScore += scores.get(ScoreLine.TWOS);
        this.upperSectionScore += scores.get(ScoreLine.THREES);
        this.upperSectionScore += scores.get(ScoreLine.FOURS);
        this.upperSectionScore += scores.get(ScoreLine.FIVES);
        this.upperSectionScore += scores.get(ScoreLine.SIXES);
        if (this.upperSectionScore >= 63 ){
            this.upperSectionBonus = 35;
        }
    }

    public void calculateLowerSectionScore() {
        this.lowerSectionScore += scores.get(ScoreLine.THREEOAK);
        this.lowerSectionScore += scores.get(ScoreLine.FOUROAK);
        this.lowerSectionScore += scores.get(ScoreLine.FH);
        this.lowerSectionScore += scores.get(ScoreLine.SMALLSTR);
        this.lowerSectionScore += scores.get(ScoreLine.LARGESTR);
        this.lowerSectionScore += scores.get(ScoreLine.YAHTZEE);
        this.lowerSectionScore += scores.get(ScoreLine.CHANCE);
    }

    public void calculateTotalScore() {
        this.totalScore = this.upperSectionScore + this.upperSectionBonus + this.lowerSectionScore;
    }

    public ArrayList<Integer> countOfDieValues(ArrayList<Die> dice){
        int numberOfDice = dice.size();
        int one = 0;
        int two = 0;
        int three = 0;
        int four = 0;
        int five = 0;
        int six = 0;
        ArrayList<Integer> dieValuesCount = new ArrayList<>();

        for (int i = 0; i <numberOfDice; i++){

            switch(dice.get(i).getValue()){
                case 1: one++;
                break;
                case 2: two++;
                break;
                case 3: three++;
                break;
                case 4: four++;
                break;
                case 5: five++;
                break;
                case 6: six++;
                break;
            }
        }
        dieValuesCount.addAll(Arrays.asList(one, two, three, four, five, six));
        return dieValuesCount;
    }


}
