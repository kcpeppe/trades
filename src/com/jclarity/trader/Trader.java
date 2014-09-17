package com.jclarity.trader;

import com.jclarity.trader.trades.Trade;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

//todo: add listener so the GUI can get the timing result

public class Trader {

    public static void main(String[] args) {
        try {
            //
            // Create a connection to the server socket on the server application
            //
            InetAddress host = InetAddress.getLocalHost();
            Socket socket = new Socket(host.getHostName(), 4040);

            //
            // Send a message to the client application
            //
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeInt( 100);
            oos.flush();

            long start = System.nanoTime();
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            int value = ois.readInt();
            for ( int i = 0; i < value; i++) {
                Trade trade = (Trade) ois.readObject();
                System.out.println("Trade: " + trade);
            }
            System.out.println( "Run time: " + (System.nanoTime() - start));

            ois.close();
            oos.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
