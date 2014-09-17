package com.jclarity.trader.io;

import com.jclarity.trader.datasource.DataSource;
import com.jclarity.trader.datasource.SerializableTradeGenerator;
import com.jclarity.trader.trades.Trade;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializableTradeStream extends TradeStream {

    public SerializableTradeStream( ServerChannel channel) throws IOException {
        super( channel);
    }

    public void write( DataSource dataSource) throws IOException {
        write(dataSource.getTrades(SerializableTradeGenerator.KEY));
    }

    public void write( Trade[] trades) {
        try {

            //
            // Read a message sent by client application
            //
            ObjectInputStream ois = new ObjectInputStream(getInputStream());
            int requestedNumberOfTrades = ois.readInt();
            System.out.println("sending " + requestedNumberOfTrades + " Serialized trades");
            //
            // Send a response information to the client application
            //
            ObjectOutputStream oos = new ObjectOutputStream(getOutputStream());
            oos.writeInt( requestedNumberOfTrades);
            oos.flush();
            for ( int count = 0; count < requestedNumberOfTrades; count++) {
                oos.writeObject( trades[ count]);
                oos.flush();
            }

            ois.close();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
