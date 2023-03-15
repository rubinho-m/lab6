package server;

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
//    public void start() throws IOException {
//        try {
//            serverSocket = new ServerSocket(port);
//            System.out.println("Server started on port " + port);
//
//            while (true) {
//                Socket clientSocket = serverSocket.accept();
//                System.out.println(clientSocket.getLocalAddress());
//                System.out.println("New client connected: " + clientSocket.getInetAddress().getHostAddress());
//                while (true) {
//                    try {
//                        ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
//                        request = (Request) objectInputStream.readObject();
//
//                        ArrayList<String> commandWithArguments = request.getCommandWithArguments();
//                        System.out.println(commandWithArguments);
//                        Ticket ticket = (Ticket) request.getTicket();
//                        ParsedString<ArrayList<String>, Ticket> parsedString = new ParsedString<>(commandWithArguments, ticket);
//                        Response response = commandExecutor.execute(parsedString);
//
//                        OutputStream outputStream = clientSocket.getOutputStream();
//                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
//                        objectOutputStream.writeObject(response);
//                        byte[] newArray = byteArrayOutputStream.toByteArray();
//                        outputStream.write(newArray);
//
//                        System.out.println("Sent response: " + response);
//                    } catch (Exception e){
//                        System.out.println(e);
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            stop();
//        }
//    }
//
//    public void stop() {
//        try {
//            if (serverSocket != null) {
//                serverSocket.close();
//            }
//            System.out.println("Server stopped.");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    public void handleCommand(){

    }
    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server started on port " + port);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("New client connected: " + clientSocket.getInetAddress().getHostAddress());

            try (InputStream inputStream = clientSocket.getInputStream();
                 OutputStream outputStream = clientSocket.getOutputStream();
                 ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {

                while (true) {
                    Request request = (Request) objectInputStream.readObject();
                    ArrayList<String> commandWithArguments = request.getCommandWithArguments();
                    System.out.println(commandWithArguments);
                    Ticket ticket = (Ticket) request.getTicket();
                    ParsedString<ArrayList<String>, Ticket> parsedString = new ParsedString<>(commandWithArguments, ticket);
                    Response response = commandExecutor.execute(parsedString);

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                    objectOutputStream.writeObject(response);
                    byte[] newArray = byteArrayOutputStream.toByteArray();
                    outputStream.write(newArray);
                    outputStream.flush();
                }
            } catch (Exception e) {
                System.out.println("Client disconnected: " + e.getMessage());
            }
        }
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

