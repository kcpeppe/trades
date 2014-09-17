/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jclarity.trader.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 * @author kirk
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    TextField port;
    @FXML
    TextField numTrades;
    @FXML
    RadioButton serial;

    @FXML
    Button shutdown;

    @FXML
    Button execute;

    private TradeSourceModel tradeSourceModel;

    @FXML
    private void handleExecuteButtonAction(ActionEvent event) {
        tradeSourceModel = new TradeSourceModel(Integer.parseInt(port.getText()), Integer.parseInt(numTrades.getText()));        
        tradeSourceModel.switchSerializableTradeType(serial.isSelected());
        tradeSourceModel.execute();
        execute.setDisable( true);
        shutdown.setDisable( false);
    }

    @FXML
    private void handleShutdownButtonAction(ActionEvent event) {
       tradeSourceModel.shutdown();
       execute.setDisable( false);
       shutdown.setDisable( true);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        shutdown.setDisable( true);
    }

}
