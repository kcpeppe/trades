package biz.c24.io.trade;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * User: John Davies (John.Davies@C24.biz)
 * Date: 15/09/2014
 * Time: 17:34
 */
public class TradeUtils {
    private static Map<String, Double> currencyRates = null;
    private static LongAdder adder = new LongAdder();

    public static List<ImmutableTrade> createTrades(int numTrades) {
        Stream<ImmutableTrade> tradeStream = Stream.generate(() -> {
            return createRandomTrade(new BinaryTrade());
        });

        System.out.printf("Creating %,.1f million trades...", numTrades / 1e6);

        List<ImmutableTrade> immutableTrades = tradeStream
                .limit(numTrades)
                .collect(Collectors.toCollection(ArrayList::new));

        System.out.println("Done\n");

        return immutableTrades;
    }

    public static ImmutableTrade createRandomTrade( MutableTrade mutableTrade) {
        if( currencyRates == null ) {
            currencyRates = new HashMap<>(7);

            currencyRates.put("GBP", 1.0);
            currencyRates.put("EUR", 1.2521);
            currencyRates.put("USD", 1.6818);
            currencyRates.put("AUD", 1.8061);
            currencyRates.put("CHF", 1.5240);
            currencyRates.put("JPY", 172.54);
            currencyRates.put("CAD", 1.8362);
        }

        Random randomGen = new Random();

        // Try out the new Java 8 Longadder
        adder.add(1);
        mutableTrade.setId(adder.longValue());

        // Now we want a random weekday in August
        LocalDate startingDate = LocalDate.of(2014, Month.AUGUST, 1);
        LocalDate tradeDate;
        do {
            tradeDate = startingDate.plusDays(randomGen.nextInt(startingDate.lengthOfMonth()-1));    // Add a random number of days
        } while ( tradeDate.getDayOfWeek() == DayOfWeek.SUNDAY || tradeDate.getDayOfWeek() == DayOfWeek.SATURDAY ); // Do it again if it's a weekend
        mutableTrade.setTradeDate( Date.from(tradeDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));

        // Random Buy/Sell
        String temp = "BuySell";
        mutableTrade.setBuySell(randomGen.nextBoolean() ? temp.substring(0,3) : temp.substring(3,7));

        // Get an array of currencyRates from the Map
        String[] currencyArray = currencyRates.keySet().toArray(new String[0]);

        // Pick one of the currencyRates by random
        mutableTrade.setCurrency1(currencyArray[randomGen.nextInt(currencyArray.length)].substring(0,3));

        // Now set the second currency but loop until it's different from the first
        do {
            mutableTrade.setCurrency2(currencyArray[randomGen.nextInt(currencyArray.length)].substring(0,3));
        } while( mutableTrade.getCurrency2().equals( mutableTrade.getCurrency1() ));

        // A random amount from 1 to 50 million in whole millions
        mutableTrade.setAmount1(BigDecimal.valueOf((randomGen.nextInt(50) + 1) * 1_000_000));

        // Calculate the exchange rate from the Map
        double rate = currencyRates.get(mutableTrade.getCurrency2()) / currencyRates.get(mutableTrade.getCurrency1());

        // Now vary the exchange rate by a random amount, Gaussian distribution with a SD of 0.5%
        rate *= (1.0 + randomGen.nextGaussian()/200.0);
        rate = BigDecimal.valueOf(rate).setScale(5, BigDecimal.ROUND_UP).doubleValue();
        mutableTrade.setExchangeRate(rate);
        mutableTrade.setAmount2(mutableTrade.getAmount1().multiply(BigDecimal.valueOf(mutableTrade.getExchangeRate())));

        // Set the settlement date randomly from 7 to 4 weeks from the trade date
        LocalDate settmentDate = tradeDate.plusDays(7 * (randomGen.nextInt(3) + 1));
        mutableTrade.setSettlementDate( Date.from(settmentDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()) );

        return mutableTrade;
    }
}
