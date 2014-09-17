package biz.c24.io.trade;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * User: John Davies (John.Davies@C24.biz)
 * Date: 14/09/2014
 * Time: 01:25
 */

public abstract class BasicTrade implements MutableTrade {
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    public String toString() {
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

        StringBuffer sb = new StringBuffer();

        sb.append(getId()).append(',');
        sb.append(sdf.format(getTradeDate())).append(',');
        sb.append(getBuySell()).append(',');
        sb.append(getCurrency1()).append(',');
        sb.append(getAmount1().setScale(2, BigDecimal.ROUND_HALF_DOWN)).append(',');
        sb.append(getExchangeRate()).append(',');
        sb.append(getCurrency2()).append(',');
        sb.append(getAmount2().setScale(2, BigDecimal.ROUND_HALF_DOWN)).append(',');
        sb.append(sdf.format(getSettlementDate()));

        return sb.toString();
    }

    public BasicTrade parse( String line ) throws ParseException {
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

        String[] fields = line.split(",");
        setId(Long.parseLong(fields[0]));
        setTradeDate(sdf.parse(fields[1]));
        setBuySell(fields[2]);
        setCurrency1(fields[3]);
        setAmount1(new BigDecimal(fields[4]));
        setExchangeRate(Double.parseDouble(fields[5]));
        setCurrency2(fields[6]);
        setAmount2(new BigDecimal(fields[7]));
        setSettlementDate(sdf.parse(fields[8]));

        return this;
    }
}
