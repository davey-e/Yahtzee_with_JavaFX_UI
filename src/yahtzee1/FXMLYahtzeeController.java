/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yahtzee1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import yahtzee1.Yahtzee.Die;

/**
 *
 * @author Dave
 */
public class FXMLYahtzeeController{
    
    @FXML
    private Label label;
    
    @FXML
    private void actionRollDice(ActionEvent event) {
        
        Die die1 = new Die();
        die1.rollDie();
        Integer dieValue = die1.getValue();
        label.setText(dieValue.toString());
    }
      
    
}
