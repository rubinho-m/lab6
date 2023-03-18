package server;

import server.collectionManagement.CollectionManager;
import server.collectionManagement.CommandExecutor;
import server.network.NetworkConnection;
import server.xml.TicketXMLParser;

public class Main {
    public static void main(String[] args) throws Exception {
        CollectionManager collectionManager = new CollectionManager();
        TicketXMLParser xmlParser = new TicketXMLParser(args[0]);
        collectionManager.setCollection(xmlParser.parse());
        collectionManager.setPath(args[0]);
        CommandExecutor commandExecutor = new CommandExecutor();
        commandExecutor.setCommands(collectionManager);
        NetworkConnection networkConnection = new NetworkConnection(28, collectionManager, commandExecutor);
        networkConnection.start();
    }
}