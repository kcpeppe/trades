package biz.c24.io.trade;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

/**
 * User: John Davies (John.Davies@C24.biz)
 * Date: 03/09/2014
 * Time: 11:04
 */

public class BinaryTrade extends BasicTrade implements Externalizable {

//    private long id;              -->  8
//    private Date tradeDate;       -->  2
//    private String buySell;       -->  1
//    private String currency1;     -->  1
//    private BigDecimal amount1;   -->  8
//    private double exchangeRate;  -->  8
//    private String currency2;     -->  1
//    private BigDecimal amount2;   -->  8
//    private Date settlementDate;  -->  2
//    Total                         --> 39

    private byte[] data;

    private static String currencies[] = { "", "AUD", "CAD", "CHF", "EUR", "GBP", "JPY", "USD" };

    public BinaryTrade() {
        data = new byte[39];
    }

    public byte[] getData() {
        return data;
    }

    public BinaryTrade( byte[] data ) {
        this.data = Arrays.copyOfRange(data, 0, 39);
    }

    @Override
    public void setId( long id) {
        long2BytesFromOffset(id, 0);
    }

    @Override
    public long getId() {
        return longFromBytesFromOffset(0);
    }

    @Override
    public Date getTradeDate() {
        long date = wordFromBytesFromOffset(8);
        date *= 86_400_000L; // milliseconds in a day
        return new Date(date);
    }

    @Override
    public void setTradeDate(Date tradeDate) {
        long date = tradeDate.getTime();
        date /= 86_400_000L; // milliseconds in a day

        data[8] = (byte)(date >>> 8);
        data[9] = (byte)(date);
    }

    @Override
    public String getBuySell() {
        return data[10] == 0 ? "Buy" : "Sell";
    }

    @Override
    public void setBuySell(String buySell) {
        data[10] = buySell.charAt(0) == 'B' ? (byte) 0 : 1; // Not the safest but good for a test
    }

    @Override
    public String getCurrency1() {
        return currencies[data[11]];
    }

    @Override
    public void setCurrency1(String currency1) {
        data[11] = 0;
        for(int i = 0; i < currencies.length; i++)
            if( currency1.equals(currencies[i])) {
                data[11] = (byte) i;
                return;
            }
    }

    @Override
    public BigDecimal getAmount1() {
        long value = longFromBytesFromOffset(12);
        BigDecimal bigDecimal = new BigDecimal(value);
        return bigDecimal.divide(new BigDecimal(100));
    }

    @Override
    public void setAmount1(BigDecimal amount1) {
        BigDecimal times100 = amount1.multiply(new BigDecimal(100));
        long value = times100.longValue();
        long2BytesFromOffset(value, 12);
    }

    @Override
    public double getExchangeRate() {

        long value = longFromBytesFromOffset(20);
        return Double.longBitsToDouble(value);
    }

    @Override
    public void setExchangeRate(double exchangeRate) {
        long value = Double.doubleToLongBits(exchangeRate);
        long2BytesFromOffset(value, 20);
    }

    @Override
    public String getCurrency2() {
        return currencies[data[28]];
    }

    @Override
    public void setCurrency2(String currency1) {
        data[28] = 0;
        for(int i = 0; i < currencies.length; i++)
            if( currency1.equals(currencies[i])) {
                data[28] = (byte) i;
                return;
            }
    }

    @Override
    public BigDecimal getAmount2() {
        long value = longFromBytesFromOffset(29);
        BigDecimal bigDecimal = new BigDecimal(value);
        return bigDecimal.divide(new BigDecimal(100));
    }

    @Override
    public void setAmount2(BigDecimal amount2) {
        BigDecimal times100 = amount2.multiply(new BigDecimal(100));
        long value = times100.longValue();
        long2BytesFromOffset(value, 29);
    }

    @Override
    public Date getSettlementDate() {
        long date = wordFromBytesFromOffset(37);
        date *= 86_400_000L; // milliseconds in a day
        return new Date(date);
    }

    @Override
    public void setSettlementDate(Date settlementDate) {
        long date = settlementDate.getTime();
        date /= 86_400_000L; // milliseconds in a day

        data[37] = (byte)(date >>> 8);
        data[38] = (byte)(date);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.write(data);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        in.read(data);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BinaryTrade)) return false;

        BinaryTrade that = (BinaryTrade) o;

        if (!Arrays.equals(data, that.data)) return false;

        return true;
    }

    private void long2BytesFromOffset(long value, int offset) {
        data[offset] = (byte)(value >>> 56);
        data[offset+1] = (byte)(value >>> 48);
        data[offset+2] = (byte)(value >>> 40);
        data[offset+3] = (byte)(value >>> 32);
        data[offset+4] = (byte)(value >>> 24);
        data[offset+5] = (byte)(value >>> 16);
        data[offset+6] = (byte)(value >>>  8);
        data[offset+7] = (byte)(value);
    }

    private long longFromBytesFromOffset( int offset ) {
        long val = 0;
        for (int i = 0; i < 8; i++)
            val  = (val << 8) + (data[offset+i] & 0xff);
        return val;
    }

    private long wordFromBytesFromOffset( int offset ) {
        long val = 0;
        for (int i = 0; i < 2; i++)
            val  = (val << 8) + (data[offset+i] & 0xff);
        return val;
    }
}
