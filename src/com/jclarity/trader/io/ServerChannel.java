package com.jclarity.trader.io;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerChannel {

    volatile private ServerSocket serverSocket;
    volatile private Socket socket = null;

    public ServerChannel(int port) throws IOException {
        this.serverSocket = new ServerSocket( port);
    }

    public void accept() throws IOException {
        socket = this.serverSocket.accept();
    }

    protected Socket getSocket() {
        return socket;
    }

    public InputStream getInputStream() throws IOException {
        return getSocket().getInputStream();
    }

    public OutputStream getOutputStream() throws IOException {
        return getSocket().getOutputStream();
    }

    public void close() throws IOException {
        serverSocket.close();
    }
}
