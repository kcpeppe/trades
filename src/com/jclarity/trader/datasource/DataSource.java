package com.jclarity.trader.datasource;

import com.jclarity.trader.trades.Trade;

import java.util.HashMap;

public class DataSource {

    private HashMap< String, Trade[]> tradeSets = new HashMap<>();

    public DataSource() {
    }

    public void generate( TradeGenerator tradeGenerator, int numberOfTrades) {
        Trade[] trades = new Trade[ numberOfTrades];
        for ( int index = 0; index < trades.length; index++) {
            trades[ index] = tradeGenerator.generateTrade();
        }
        tradeSets.put( tradeGenerator.getKey(), trades);
    }

    public Trade[] getTrades( String key) {
        return tradeSets.get( key);
    }
}
