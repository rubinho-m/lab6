package server.network;

import common.dataStructures.ParsedString;
import common.networkStructures.Request;
import common.networkStructures.Response;
import common.structureClasses.Ticket;
import server.collectionManagement.CollectionManager;
import server.collectionManagement.CommandExecutor;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

public class NetworkConnection {
    private final int BUFFER_SIZE = 1024 * 1024;
    private Socket clientSocket;
    private ServerSocket serverSocket;
    private int port;
    private CollectionManager collectionManager;
    private CommandExecutor commandExecutor;
    private Request request;
    private Response response;

    public NetworkConnection(int port, CollectionManager collectionManager, CommandExecutor commandExecutor) throws IOException {
        this.port = port;
        this.collectionManager = collectionManager;
        this.commandExecutor = commandExecutor;

    }

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server started on port " + port);
        Reader reader = new Reader(serverSocket, commandExecutor);
        reader.read();


    }


}
//    public Request receive() throws IOException, ClassNotFoundException {
//        Request request;
//
//        InputStream inputStream = clientSocket.getInputStream();
//
//        byte[] arr = new byte[BUFFER_SIZE];
//        int bytesRead = inputStream.read(arr);
//
//        byteArrayInputStream = new ByteArrayInputStream(arr, 0, bytesRead);
//        ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(byteArrayInputStream.readAllBytes()));
//
//        request = (Request) objectInputStream.readObject();
//        return request;
//    }
//
//    public void send(Response response, InetAddress host) throws IOException {
//        OutputStream outputStream = clientSocket.getOutputStream();
//        byteArrayOutputStream = new ByteArrayOutputStream();
//        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
//        objectOutputStream.writeObject(response);
//        byte[] newArray = byteArrayOutputStream.toByteArray();
//        outputStream.write(newArray);
//
//    }
