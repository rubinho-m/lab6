package client;

import common.networkStructures.Request;
import common.networkStructures.Response;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

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

    public void connectionManage() throws IOException {
        socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        Selector selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_CONNECT);
        socketChannel.connect(socketAddress);

        Thread keyboardThread = new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                if (scanner.hasNextLine()) {
                    String input = scanner.nextLine();
                    System.out.println(input + " урааа!!!");
                }
            }
        });
        keyboardThread.start();

        while (true) {
            selector.select();
            Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                if (key.isConnectable()) {
                    if (socketChannel.finishConnect()) {
                        System.out.println("Подключен к серверу");
                        socketChannel.register(selector, SelectionKey.OP_WRITE);
                    }
                }
                if (key.isWritable()) {
                    ByteBuffer buf = ByteBuffer.wrap(arr);
                    socketChannel.write(buf);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                }
                if (key.isReadable()) {
                    ByteBuffer buf = ByteBuffer.wrap(arr);
                    socketChannel.read(buf);
                    for (byte j : arr) {
                        System.out.println(j);
                    }
                    socketChannel.register(selector, SelectionKey.OP_WRITE);
                }
                keyIterator.remove();
            }
        }
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

    }
}
