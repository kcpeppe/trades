package com.jclarity.trader;

import com.jclarity.trader.datasource.BinaryTradeGenerator;
import com.jclarity.trader.datasource.DataSource;
import com.jclarity.trader.datasource.SerializableTradeGenerator;
import com.jclarity.trader.datasource.TradeGenerator;
import com.jclarity.trader.io.BinaryTradeStream;
import com.jclarity.trader.io.SerializableTradeStream;
import com.jclarity.trader.io.ServerChannel;
import com.jclarity.trader.io.TradeStream;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

//todo: add timing on the client
//todo: fix TradeSource GUI
//todo: fix Trader GUI


public class TradeSource implements Runnable {

    private DataSource tradesDataSource;
    TradeStream stream;
    Executor acceptThread;
    volatile boolean running = false;

    public TradeSource() {
        tradesDataSource = new DataSource();
        acceptThread = Executors.newSingleThreadExecutor();
    }

    private void setTradeStream( TradeStream tradeStream) {
        this.stream = tradeStream;
    }

    public void initializeDataSource( TradeGenerator generator, int numberOfTradesToGenerate) {
        tradesDataSource.generate( generator, numberOfTradesToGenerate);
    }

    public void execute(TradeStream stream) throws IOException {
        this.stream = stream;
        acceptThread.execute(this);
    }

    public void run() {
        running = true;
        while ( running) {
            try {
                connect();
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
                ioe.printStackTrace();
            }
        }
    }

    private void connect() throws IOException {
        System.out.println("Waiting for connection....");
        stream.accept();
        stream.write(tradesDataSource);
    }

    public void shutdown() throws IOException {
        this.running = false;
        stream.close();
    }

    public static void main(String[] args) throws Exception {
        TradeSource tradeSource = new TradeSource();

        System.out.println( "Generating " + args[0] + " Serializable trades");
        tradeSource.initializeDataSource( new SerializableTradeGenerator(), Integer.parseInt( args[1]));
        System.out.println("Generating " + args[0] + " Binary trades");
        tradeSource.initializeDataSource( new BinaryTradeGenerator(), Integer.parseInt( args[1]));
        System.out.println("Done.....");

        if ( args[0].equals("serial")) {
            tradeSource.setTradeStream(new SerializableTradeStream(new ServerChannel(4040)));
            tradeSource.connect();
        }
        else if ( args[0].equals("binary")) {
            tradeSource.setTradeStream(new BinaryTradeStream(new ServerChannel(4040)));
            tradeSource.connect();
        }
        else
            System.out.println("Unknown: " + args[0]);
    }
}
