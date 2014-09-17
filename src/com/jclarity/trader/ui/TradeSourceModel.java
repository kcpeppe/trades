package com.jclarity.trader.ui;


import com.jclarity.trader.TradeSource;
import com.jclarity.trader.datasource.BinaryTradeGenerator;
import com.jclarity.trader.datasource.SerializableTradeGenerator;
import com.jclarity.trader.io.BinaryTradeStream;
import com.jclarity.trader.io.SerializableTradeStream;
import com.jclarity.trader.io.ServerChannel;
import com.jclarity.trader.io.TradeStream;

import java.io.IOException;

public class TradeSourceModel {

    private TradeSource tradeSource;
    private TradeStream stream = null;
    private int port;

    public TradeSourceModel( int port, int numberOfTrades) {
        this.port = port;
        tradeSource = new TradeSource();
        tradeSource.initializeDataSource( new SerializableTradeGenerator(), numberOfTrades);
        tradeSource.initializeDataSource( new BinaryTradeGenerator(), numberOfTrades);
    }

    private void close() {
        if ( stream != null) {
            try {
                stream.close();
            } catch( IOException ioe) {
                System.out.println("Error: " + ioe.getMessage());
                ioe.printStackTrace();
            }
        }
    }

    public void switchSerializableTradeType( boolean serialSelected) {
        this.close();
        try {
            if ( serialSelected)
                stream = new SerializableTradeStream(new ServerChannel(4040));
            else 
                stream = new BinaryTradeStream(new ServerChannel(4040));
        } catch (IOException ioe) {
            System.out.println("Error: " + ioe.getMessage());
            ioe.printStackTrace();
        }
    }

    public void execute() {
        try {
            tradeSource.execute( stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        try {
            tradeSource.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
