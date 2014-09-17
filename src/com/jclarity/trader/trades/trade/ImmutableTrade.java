package biz.c24.io.trade;

import java.math.BigDecimal;
import java.util.Date;

/**
 * User: John Davies (John.Davies@C24.biz)
 * Date: 03/09/2014
 * Time: 13:13
 */
public interface ImmutableTrade {
    public long getId();
    public Date getTradeDate();
    public String getBuySell();
    public String getCurrency1();
    public BigDecimal getAmount1();
    public double getExchangeRate();
    public String getCurrency2();
    public BigDecimal getAmount2();
    public Date getSettlementDate();
}
