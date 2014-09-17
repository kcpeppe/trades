package com.jclarity.trader.io;

import com.jclarity.trader.datasource.DataSource;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class TradeStream {

    private ServerChannel channel;

    public TradeStream( ServerChannel channel) throws IOException {
        this.channel = channel;
    }

    public void accept() throws IOException {
        channel.accept();
    }

    public InputStream getInputStream() throws IOException {
        return channel.getInputStream();
    }

    public OutputStream getOutputStream() throws IOException {
        return channel.getOutputStream();
    }

    public abstract void write( DataSource dataSource) throws IOException;

    public void close() throws IOException {
        this.channel.close();
    }

}
