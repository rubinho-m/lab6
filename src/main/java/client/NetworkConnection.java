package client;

import common.networkStructures.Request;
import common.networkStructures.Response;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NetworkConnection {
    private final int BUFFER_SIZE = 1024 * 1024;
    private InetAddress host;
    int port;
    SocketAddress socketAddress;
    byte[] arr = {0, 1, 2, 3, 4, 5};
    SocketChannel socketChannel;

    public NetworkConnection(String address, int port) throws UnknownHostException {
        host = InetAddress.getByName(address);
        this.socketAddress = new InetSocketAddress(host, port);
    }

    public void send() throws IOException {
        socketChannel = SocketChannel.open();
        socketChannel.connect(socketAddress);
        ByteBuffer buf = ByteBuffer.wrap(arr);
        socketChannel.write(buf);
    }

    public void receive() throws IOException {
        Response response;
        ByteBuffer buf = ByteBuffer.wrap(arr);
        socketChannel.read(buf);
        for (byte j : arr) {
            System.out.println(j);
        }


//        return response;
    }
}
