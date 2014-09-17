package com.jclarity.trader.datasource;

import com.jclarity.trader.trades.Trade;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.LongAdder;


public abstract class TradeGenerator {

    private static Map<String, Double> currencies = null;

    static {

        currencies = new HashMap<>();

        currencies.put("GBP", 1.0);
        currencies.put("EUR", 1.2521);
        currencies.put("USD", 1.6818);
        currencies.put("AUD", 1.8061);
        currencies.put("CHF", 1.5240);
        currencies.put("JPY", 172.54);
        currencies.put("CAD", 1.8362);

    }

    private String[] currenciesArray = currencies.keySet().toArray( new String[0]);
    private LongAdder adder = new LongAdder();
    private Random randomGen = new Random();

    public TradeGenerator() {}

    public abstract String getKey();
    public abstract String getName();

    public abstract Trade generateTrade();

    public long longValue() {
        this.adder.add(1);
        return adder.longValue();
    }

    public boolean nextBoolean() {
        return randomGen.nextBoolean();
    }

    public int nextInt( int range) {
        return randomGen.nextInt( range);
    }

    public double nextGaussian() {
        return randomGen.nextGaussian();
    }

    public String getCurrency() {
        return currenciesArray[ nextInt( currencies.size())];
    }

    public String getCurrencyNot( String currency) {
        String secondCurrency;
        // Now set the second currency but loop until it's different from the first
        do {
            secondCurrency = currenciesArray[nextInt(currencies.size())];
        } while( secondCurrency != currency);
        return secondCurrency;
    }

    public double getExchangeRate( String currency1, String currency2) {
        return currencies.get(currency2) /  currencies.get( currency1);
    }
}
