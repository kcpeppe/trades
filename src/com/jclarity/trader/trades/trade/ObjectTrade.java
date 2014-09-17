package biz.c24.io.trade;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

/**
 * User: John Davies (John.Davies@C24.biz)
 * Date: 03/09/2014
 * Time: 11:04
 */

public class ObjectTrade extends BasicTrade implements Serializable {
    private long id;
    private Date tradeDate;
    private String buySell;
    private String currency1;
    private BigDecimal amount1;
    private double exchangeRate;
    private String currency2;
    private BigDecimal amount2;
    private Date settlementDate;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public Date getTradeDate() {
        return tradeDate;
    }

    @Override
    public void setTradeDate(Date tradeDate) {
        this.tradeDate = tradeDate;
    }

    @Override
    public String getBuySell() {
        return buySell;
    }

    @Override
    public void setBuySell(String buySell) {
        this.buySell = buySell;
    }

    @Override
    public String getCurrency1() {
        return currency1;
    }

    @Override
    public void setCurrency1(String currency1) {
        this.currency1 = currency1;
    }

    @Override
    public BigDecimal getAmount1() {
        return amount1;
    }

    @Override
    public void setAmount1(BigDecimal amount1) {
        this.amount1 = amount1;
    }

    @Override
    public double getExchangeRate() {
        return exchangeRate;
    }

    @Override
    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    @Override
    public String getCurrency2() {
        return currency2;
    }

    @Override
    public void setCurrency2(String currency2) {
        this.currency2 = currency2;
    }

    @Override
    public BigDecimal getAmount2() {
        return amount2;
    }

    @Override
    public void setAmount2(BigDecimal amount2) {
        this.amount2 = amount2;
    }

    @Override
    public Date getSettlementDate() {
        return settlementDate;
    }

    @Override
    public void setSettlementDate(Date settlementDate) {
        this.settlementDate = settlementDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ObjectTrade)) return false;

        ObjectTrade that = (ObjectTrade) o;

        if (Double.compare(that.exchangeRate, exchangeRate) != 0) return false;
        if (id != that.id) return false;
        if (amount1 != null ? !amount1.equals(that.amount1) : that.amount1 != null) return false;
        if (amount2 != null ? !amount2.equals(that.amount2) : that.amount2 != null) return false;
        if (buySell != null ? !buySell.equals(that.buySell) : that.buySell != null) return false;
        if (currency1 != null ? !currency1.equals(that.currency1) : that.currency1 != null) return false;
        if (currency2 != null ? !currency2.equals(that.currency2) : that.currency2 != null) return false;
        if (settlementDate != null ? !settlementDate.equals(that.settlementDate) : that.settlementDate != null)
            return false;
        if (tradeDate != null ? !tradeDate.equals(that.tradeDate) : that.tradeDate != null) return false;

        return true;
    }
}
