package server.network;

import common.dataStructures.ParsedString;
import common.networkStructures.Request;
import common.networkStructures.Response;
import common.structureClasses.Ticket;
import server.collectionManagement.CommandExecutor;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Reader {
    private ServerSocket serverSocket;
    private CommandExecutor commandExecutor;

    public Reader(ServerSocket serverSocket, CommandExecutor commandExecutor) {
        this.serverSocket = serverSocket;
        this.commandExecutor = commandExecutor;
    }

    public void read() throws IOException {
        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("New client connected: " + clientSocket.getInetAddress().getHostAddress());

            try (InputStream inputStream = clientSocket.getInputStream();
                 OutputStream outputStream = clientSocket.getOutputStream();
                 ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {

                while (true) {
                    Request request = (Request) objectInputStream.readObject();
                    ArrayList<String> commandWithArguments = request.getCommandWithArguments();
                    Ticket ticket = (Ticket) request.getTicket();
                    ParsedString<ArrayList<String>, Ticket> parsedString = new ParsedString<>(commandWithArguments, ticket);

                    Handler handler = new Handler(commandExecutor, outputStream);
                    handler.handleCommand(parsedString);
                }
            } catch (Exception e) {
                System.out.println("Client disconnected: " + e.getMessage());
            }
        }
    }
}
