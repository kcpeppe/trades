package com.jclarity.trader.datasource;

import com.jclarity.trader.trades.SerializableTrade;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;


public class BinaryTradeGenerator extends TradeGenerator {

    final public static String KEY = "binary";

    public BinaryTradeGenerator() {}

    public String getKey() { return KEY; }
    public String getName() { return "binary"; }

    public SerializableTrade generateTrade() {

        // Now we want a random weekday in August
        LocalDate startingDate = LocalDate.of(2014, Month.AUGUST, 1);
        LocalDate candidateTradeDate;
        do {
            candidateTradeDate = startingDate.plusDays(nextInt(startingDate.lengthOfMonth() - 1));    // Add a random number of days
        } while ( candidateTradeDate.getDayOfWeek() == DayOfWeek.SUNDAY || candidateTradeDate.getDayOfWeek() == DayOfWeek.SATURDAY ); // Do it again if it's a weekend
        Date tradeDate = Date.from(candidateTradeDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

        //Currencies
        String currency1 = getCurrency();
        String currency2 = getCurrencyNot( currency1);

        // A random amount from 1 to 50 million in whole millions
        BigDecimal amount1 = BigDecimal.valueOf((nextInt(50) + 1) * 1_000_000);

        // Calculate the exchange rate from the Map
        double exchangeRate = getExchangeRate(currency1, currency2);
        // Now vary the exchange rate by a random amount, Gaussian distribution with a SD of 0.5%
        exchangeRate *= (1.0 + nextGaussian()/200.0);
        exchangeRate = BigDecimal.valueOf(exchangeRate).setScale(5, BigDecimal.ROUND_UP).doubleValue();

        BigDecimal amount2 = amount1.multiply(BigDecimal.valueOf( exchangeRate));

        // Set the settlement date randomly from 7 to 4 weeks from the trade date
        LocalDate candidateSettlementDate = candidateTradeDate.plusDays(7 * (nextInt(3) + 1));
        Date settlementDate = Date.from(candidateSettlementDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

        return new SerializableTrade(
                longValue(),
                tradeDate,
                nextBoolean() ? "Buy" : "Sell",
                currency1,
                amount1,
                exchangeRate,
                currency2,
                amount2,
                settlementDate);
    }

}
