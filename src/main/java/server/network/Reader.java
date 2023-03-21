package server.network;

import common.commandParsing.CommandParser;
import common.dataStructures.ParsedString;
import common.networkStructures.Request;
import common.networkStructures.Response;
import common.structureClasses.Ticket;
import server.collectionManagement.CommandExecutor;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.*;

public class Reader {
    private ServerSocket serverSocket;
    private CommandExecutor commandExecutor;
    private Set<String> serverCommands = new HashSet<>() {{
        add("save");
        add("exit");
    }};

    public Reader(ServerSocket serverSocket, CommandExecutor commandExecutor) {
        this.serverSocket = serverSocket;
        this.commandExecutor = commandExecutor;
    }

    public void read() throws Exception {
        serverSocket.setSoTimeout(500);
        while (true) {
            try {
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

                        Handler handler = new Handler(commandExecutor, outputStream, false);
                        handler.handleCommand(parsedString);
                    }
                } catch (Exception e) {
                    System.out.println("Client disconnected: " + e.getMessage());
                }
            } catch (SocketTimeoutException e){
                if (System.in.available() > 0) {
                    Scanner scanner = new Scanner(System.in);
                    CommandParser commandParser = new CommandParser();
                    ParsedString<ArrayList<String>, Ticket> parsedString = commandParser.readCommand(scanner, true);
                    if (serverCommands.contains(parsedString.getArray().get(0))) {
                        Handler handler = new Handler(commandExecutor, true);
                        handler.handleCommand(parsedString);
                    }
                }
            }


        }
    }
}