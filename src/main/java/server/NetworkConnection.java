package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

public class NetworkConnection {
    private final int BUFFER_SIZE = 1024 * 1024;
    ServerSocket serverSocket;
    ServerSocketChannel serverSocketChannel;
    Selector selector;
    Socket clientSocket;
    byte[] arr = new byte[6];
    int len = arr.length;

    public NetworkConnection(int port) throws IOException {
        serverSocket = new ServerSocket(port);

    }

    public void receive() throws IOException {
        clientSocket = serverSocket.accept();
        InputStream inputStream = clientSocket.getInputStream();
        int bytesRead = inputStream.read(arr);
        for (byte j : arr) {
            System.out.println(j);
        }

    }

    public void send() throws IOException {
        OutputStream outputStream = clientSocket.getOutputStream();
        for (int j = 0; j < len; j++) {
            arr[j] *= 2;
        }
        outputStream.write(arr);

    }
}
