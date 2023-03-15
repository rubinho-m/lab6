package server;

import common.dataStructures.ParsedString;
import common.networkStructures.Request;
import common.networkStructures.Response;
import common.structureClasses.Ticket;
import server.collectionManagement.CollectionManager;
import server.collectionManagement.CommandExecutor;
import server.xml.TicketXMLParser;

import javax.xml.bind.UnmarshalException;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception {
        CollectionManager collectionManager = new CollectionManager();
        TicketXMLParser xmlParser = new TicketXMLParser(args[0]);
        collectionManager.setCollection(xmlParser.parse());
        collectionManager.setPath(args[0]);
        CommandExecutor commandExecutor = new CommandExecutor();
        commandExecutor.setCommands(collectionManager);
        NetworkConnection networkConnection = new NetworkConnection(2828, collectionManager, commandExecutor);
        networkConnection.start();

//
//        while (true){
//            RequesArrayList<String> commandWithArguments = request.getCommandWithArguments();
////            System.out.println(commandWithArguments);
////            Ticket ticket = (Ticket) request.getTicket();
////            ParsedString<ArrayList<String>, Ticket> parsedString = new ParsedString<>(commandWithArguments, ticket);
////            Response response = commandExecutor.execute(parsedString);t request = networkConnection.receive();
//
//            networkConnection.send(response, request.getHost());
//        }
    }
}
