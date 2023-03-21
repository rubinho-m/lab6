package server;

import common.exceptions.NoCommandException;
import common.exceptions.WrongCommandFormat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.collectionManagement.CollectionManager;
import server.collectionManagement.CommandExecutor;
import server.network.NetworkConnection;
import server.xml.TicketXMLParser;

import java.net.BindException;


public class Server {

    private static final Logger logger = LogManager.getLogger(Server.class);

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.out.println("Введите путь к файлу и порт");
            System.exit(1);
        }
        try {
            int port = Integer.parseInt(args[1]);
            logger.info("START");
            CollectionManager collectionManager = new CollectionManager();
            logger.info("COLLECTION MANAGER HAS BEEN SET");
            TicketXMLParser xmlParser = new TicketXMLParser(args[0]);
            collectionManager.setCollection(xmlParser.parse());
            logger.info("XML FILE HAS BEEN PARSED");
            collectionManager.setPath(args[0]);
            CommandExecutor commandExecutor = new CommandExecutor();
            commandExecutor.setCommands(collectionManager);
            NetworkConnection networkConnection = new NetworkConnection(port, collectionManager, commandExecutor);
            networkConnection.start();
        } catch (NumberFormatException e) {
            System.out.println("Порт должен быть числом");
            logger.error("PORT IS NOT INT");
            System.exit(1);
        } catch (BindException e) {
            System.out.println("Порт занят, введите другой");
            logger.error("PORT HAS ALREADY USED");
            System.exit(1);
        }

    }
}