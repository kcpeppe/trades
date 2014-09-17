package com.jclarity.trader.trades;


import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: John Davies (John.Davies@C24.biz)
 * Date: 03/09/2014
 * Time: 11:04
 */


public class BinaryTrade implements Trade, Serializable {

    private Long id;
    private Date tradeDate;
    private String buySell;
    private String currency1;
    private BigDecimal amount1;
    private Double exchangeRate;
    private String currency2;
    private BigDecimal amount2;
    private Date settlementDate;

    public BinaryTrade(Long id, Date tradeDate, String buySell, String currency1, BigDecimal amount1, Double exchangeRate, String currency2, BigDecimal amount2, Date settlementDate) {
        this.id = id;
        this.tradeDate = tradeDate;
        this.buySell = buySell;
        this.currency1 = currency1;
        this.amount1 = amount1;
        this.exchangeRate = exchangeRate;
        this.amount2 = amount2;
        this.currency2 = currency2;
        this.settlementDate = settlementDate;
    }

    public Long getId() { return id; }
    public Date getTradeDate() { return tradeDate; }
    public String getBuySell() { return buySell; }
    public String getCurrency1() { return currency1; }
    public BigDecimal getAmount1() { return amount1; }
    public Double getExchangeRate() { return exchangeRate; }
    public String getCurrency2() { return currency2; }
    public BigDecimal getAmount2() { return amount2; }
    public Date getSettlementDate() { return settlementDate; }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        StringBuilder sb = new StringBuilder();

        sb.append(id).append(',');
        sb.append(sdf.format(tradeDate)).append(',');
        sb.append(buySell).append(',');
        sb.append(currency1).append(',');
        sb.append(amount1.setScale(2, BigDecimal.ROUND_HALF_DOWN)).append(',');
        sb.append(exchangeRate).append(',');
        sb.append(currency2).append(',');
        sb.append(amount2.setScale(2, BigDecimal.ROUND_HALF_DOWN)).append(',');
        sb.append(sdf.format(settlementDate));

        return sb.toString();
    }

}
