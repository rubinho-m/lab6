package client;

import client.io.commandParsing.CommandParser;
import common.dataStructures.ParsedString;
import common.networkStructures.Request;
import common.networkStructures.Response;
import common.structureClasses.Ticket;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class NetworkConnection {
    private final int BUFFER_SIZE = 1024 * 1024;
    private InetAddress host;
    private Response returnResponse;

    int port;
    SocketAddress socketAddress;
    SocketChannel socketChannel;

    public NetworkConnection(String address, int port) throws UnknownHostException {
        host = InetAddress.getByName(address);
        this.socketAddress = new InetSocketAddress(host, port);
    }

    public Response getReturnResponse() {
        return returnResponse;
    }

    public void connectionManage(Request request) throws Exception {

        Selector selector = Selector.open();
        socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(socketAddress);
        socketChannel.register(selector, SelectionKey.OP_CONNECT);

        loop:
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
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
                    objectOutputStream.writeObject(request);


                    ByteBuffer buf = ByteBuffer.wrap(out.toByteArray());
                    buf.rewind();

                    while (buf.hasRemaining()) {
                        socketChannel.write(buf);
                    }
                    System.out.println(request.getCommandWithArguments());
                    buf.clear();
                    out.close();
                    objectOutputStream.close();
                    socketChannel.register(selector, SelectionKey.OP_READ);
                }
                if (key.isReadable()) {
                    byte[] data = new byte[BUFFER_SIZE];
                    ByteBuffer buf = ByteBuffer.wrap(data);
                    buf.clear();
                    socketChannel.read(buf);
                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
                    ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                    Response response = (Response) objectInputStream.readObject();
                    returnResponse = response;
                    System.out.println(response.getOutput());
                    buf.clear();
                    byteArrayInputStream.close();
                    objectInputStream.close();
                    socketChannel.close();
                    break loop;

                }
                keyIterator.remove();
            }
        }
    }


}
