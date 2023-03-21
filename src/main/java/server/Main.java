package server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.collectionManagement.CollectionManager;
import server.collectionManagement.CommandExecutor;
import server.network.NetworkConnection;
import server.xml.TicketXMLParser;


public class Main {

    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) throws Exception {
        logger.info("START");
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